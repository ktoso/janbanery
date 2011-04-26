package pl.project13.janbanery.core;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.core.dao.TasksImpl;
import pl.project13.janbanery.encoders.FormUrlEncodedBodyGenerator;
import pl.project13.janbanery.exceptions.RestClientException;
import pl.project13.janbanery.exceptions.kanbanery.*;
import pl.project13.janbanery.resources.Task;

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
public class RestClient {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration               conf;
  private Gson                        gson;
  private AsyncHttpClient             asyncHttpClient;
  private FormUrlEncodedBodyGenerator encodedBodyGenerator;

  public RestClient(Configuration conf, Gson gson, AsyncHttpClient asyncHttpClient, FormUrlEncodedBodyGenerator encodedBodyGenerator) {
    this.conf = conf;
    this.gson = gson;
    this.asyncHttpClient = asyncHttpClient;
    this.encodedBodyGenerator = encodedBodyGenerator;
  }

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
        throw new InvalidEntityKanbaneryException(errorMessageFrom(response));
      case UnauthorizedKanbaneryException.MAPPED_ERROR_CODE:
        throw new UnauthorizedKanbaneryException(errorMessageFrom(response));
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

  public String doPost(Task task, String url, TasksImpl tasks) throws IOException, InterruptedException, ExecutionException {
    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.preparePost(url);
    authorize(requestBuilder);

    String requestBody = encodedBodyGenerator.asString(task);
    log.info("Generated request body is: '{}'", requestBody);
    requestBuilder.setBody(requestBody);

    ListenableFuture<Response> futureResponse = requestBuilder.execute();
    Response response = futureResponse.get();
    String responseBody = response.getResponseBody();

    verifyResponseCode(response);

    log.debug("Got response for creating task: {}", responseBody);
    return responseBody;
  }

  /**
   * Perform a plain GET call onto the passed URL.
   * It will be authorized using {@link Configuration#authorize(com.ning.http.client.AsyncHttpClient.BoundRequestBuilder)}
   * method.
   *
   * @param url url to call
   * @return the Response object containing the server response to our call
   */
  public Response doGet(String url) {
    log.info("Calling GET on: " + url);

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.prepareGet(url);
    authorize(requestBuilder);

    Response response = execute(requestBuilder);

    verifyResponseCode(response);

    return response;
  }

  /**
   * Delegate to {@link RestClient#doGet(String)} and also use {@link Gson} to parse the returned json
   * into the requested object
   *
   * @param url        url to call
   * @param returnType type to parse the returned json into
   * @param <T>        the return type, should match returnType
   * @return the KanbaneryResource created by parsing the retrieved json
   * @throws IOException if the response body could not be fetched
   */
  public <T> T doGet(String url, Type returnType) throws IOException {
    Response response = doGet(url);
    String responseBody = response.getResponseBody();

    return gson.fromJson(responseBody, returnType);
  }

  private void authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    conf.authorize(requestBuilder);
  }

  private Response execute(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
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
}
