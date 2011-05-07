/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.project13.janbanery.core.rest;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.encoders.FormUrlEncodedBodyGenerator;
import pl.project13.janbanery.exceptions.NotFoundKanbaneryException;
import pl.project13.janbanery.exceptions.RestClientException;
import pl.project13.janbanery.exceptions.kanbanery.*;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;

/**
 * A simple Facade to {@link AsyncHttpClient}.
 * <p/>
 * Used to encapsulate all the logic going on when we call a RESTful webservice via the
 * AsyncHttpClient. It's great but there's too many lines involved in doing a GET and parsing
 * it back into our OOP World.
 *
 * @author Konrad Malawski
 */
public class AsyncHttpClientRestClient implements RestClient {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private Gson gson;
  private AsyncHttpClient asyncHttpClient;
  private FormUrlEncodedBodyGenerator encodedBodyGenerator;

  public AsyncHttpClientRestClient(Configuration conf, Gson gson, AsyncHttpClient asyncHttpClient, FormUrlEncodedBodyGenerator encodedBodyGenerator) {
    this.conf = conf;
    this.gson = gson;
    this.asyncHttpClient = asyncHttpClient;
    this.encodedBodyGenerator = encodedBodyGenerator;
  }

  @Override
  public void verifyResponseCode(Response response) throws KanbaneryException {
    Integer statusCode = response.getStatusCode();

    switch (statusCode) {
      case DeleteFailedKanbaneryException.MAPPED_ERROR_CODE:
        throw new DeleteFailedKanbaneryException(errorMessageFrom(response));
      case ForbiddenOperationKanbaneryException.MAPPED_ERROR_CODE:
        throw new ForbiddenOperationKanbaneryException(errorMessageFrom(response));
      case InternalServerErrorKanbaneryException.MAPPED_ERROR_CODE:
        throw new InternalServerErrorKanbaneryException(errorMessageFrom(response));
      case InvalidEntityKanbaneryException.MAPPED_ERROR_CODE:
        throw InvalidEntityKanbaneryException.mostSpecializedException(errorMessageFrom(response));
      case UnauthorizedKanbaneryException.MAPPED_ERROR_CODE:
        throw new UnauthorizedKanbaneryException(errorMessageFrom(response));
      case NotFoundKanbaneryException.MAPPED_ERROR_CODE:
        throw new NotFoundKanbaneryException(errorMessageFrom(response));
    }

    if (statusCode > 400) {
      throw new KanbaneryException(format("Unexpected response code '%d' and message: '%s'.", statusCode, response.getStatusText()));
    }
  }

  private static String errorMessageFrom(Response response) {
    StringBuilder sb = new StringBuilder().append(response.getStatusCode()).append(" - ").append(response.getStatusText()).append("\n");
    try {
      sb.append(response.getResponseBody());
    } catch (IOException ignored) {
      // ok, so no response could be fetched
      sb.append("[EXCEPTION WHILE GETTING RESPONSE BODY]");
    }

    return sb.toString();
  }

  @Override
  public Response doPost(String url, KanbaneryResource resource) throws IOException {
    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.preparePost(url);
    authorize(requestBuilder);

    String requestBody = encodedBodyGenerator.asString(resource);
    log.info("Generated request body is: '{}'", requestBody);
    setFormUrlEncodedBody(requestBuilder, requestBody);

    Response response = execute(requestBuilder);

    verifyResponseCode(response);

    if (log.isDebugEnabled()) {
      log.debug("Got response for creating resource: {}", response.getResponseBody());
    }

    return response;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T doPost(String url, KanbaneryResource resource, Class<?> returnType) throws IOException {
    Response response = doPost(url, resource);
    String responseBody = response.getResponseBody();

    return (T) gson.fromJson(responseBody, returnType);
  }

  /**
   * Perform a plain GET call onto the passed URL.
   * It will be authorized using {@link Configuration#authorize(com.ning.http.client.AsyncHttpClient.BoundRequestBuilder)}
   * method.
   *
   * @param url url to call
   * @return the Response object containing the server response to our call
   */
  @Override
  public Response doGet(String url) {
    log.info("Calling GET on: " + url);

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.prepareGet(url);
    authorize(requestBuilder);

    Response response = execute(requestBuilder);

    verifyResponseCode(response);

    return response;
  }

  /**
   * Delegate to {@link AsyncHttpClientRestClient#doGet(String)} and also use {@link Gson} to parse the returned json
   * into the requested object
   *
   * @param url        url to call
   * @param returnType taskType to parse the returned json into
   * @param <T>        the return taskType, should match returnType
   * @return the KanbaneryResource created by parsing the retrieved json
   * @throws IOException if the response body could not be fetched
   */
  @Override
  @SuppressWarnings("unchecked")
  public <T> T doGet(String url, Type returnType) throws IOException {
    Response response = doGet(url);
    String responseBody = response.getResponseBody();

    return (T) gson.fromJson(responseBody, returnType);
  }

  @Override
  public Response doDelete(String url) {
    log.info("Calling DELETE on: " + url);

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.prepareDelete(url);
    authorize(requestBuilder);

    Response response = execute(requestBuilder);

    verifyResponseCode(response);

    return response;
  }

  @Override
  public Response doPut(String url, String requestBody) {
    log.info("Calling PUT on: '" + url + "', with data: " + requestBody);

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.preparePut(url);
    authorize(requestBuilder);

    setFormUrlEncodedBody(requestBuilder, requestBody);

    Response response = execute(requestBuilder);

    verifyResponseCode(response);

    return response;
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public <T> T doPut(String url, String requestBody, Class<?> returnType) throws IOException {
    Response response = doPut(url, requestBody);
    String responseBody = response.getResponseBody();
    return (T) gson.fromJson(responseBody, returnType);
  }

  @Override
  @SuppressWarnings({"unchecked"})
  public <T> T doPut(String url, String requestBody, Type returnType) throws IOException {
    Response response = doPut(url, requestBody);
    String responseBody = response.getResponseBody();
    return (T) gson.fromJson(responseBody, returnType);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T doPut(String url, KanbaneryResource requestObject, Class<?> returnType) throws IOException {
    String requestBody = encodedBodyGenerator.asString(requestObject);
    Response response = doPut(url, requestBody);
    String responseBody = response.getResponseBody();
    return (T) gson.fromJson(responseBody, returnType);
  }

  @Override
  public void authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    conf.authorize(requestBuilder);
  }

  @Override
  public void setFormUrlEncodedBody(AsyncHttpClient.BoundRequestBuilder requestBuilder, String requestBody) {
    requestBuilder.setBody(requestBody);
    requestBuilder.setHeader("Content-Type", "application/x-www-form-urlencoded");
  }


  /**
   * Execute and throw RestClientException exceptions if the request could not be executed.
   *
   * @param requestBuilder the request to be executed()
   * @return return the response fetched from the server
   * @throws RestClientException if the response could not be fetched
   */
  @Override
  public Response execute(AsyncHttpClient.BoundRequestBuilder requestBuilder) throws RestClientException {
    Response response;

    try {
      ListenableFuture<Response> futureResponse = requestBuilder.execute();
      response = futureResponse.get();

      if (log.isDebugEnabled()) {
        // the if is here so that we don't call the getResponseBody() when we're not going to print it
        log.debug("Got response body: {}", response.getResponseBody());
      }
    } catch (InterruptedException e) {
      throw new RestClientException("Interrupted while waiting for server response", e);
    } catch (ExecutionException e) {
      throw new RestClientException("Tried to retrieve result from aborted action.", e);
    } catch (IOException e) {
      throw new RestClientException("Encountered IOException while executing REST request.", e);
    }

    return response;
  }

  /**
   * If is very important that you call this method after you're finished working with kanbanery.
   * It will close all underlying threads and free a lot of memory used by the RestClient.
   */
  @Override
  public void close() {
    asyncHttpClient.close();
  }
}
