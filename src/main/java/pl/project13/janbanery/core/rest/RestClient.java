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

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import pl.project13.janbanery.exceptions.NotFoundKanbaneryException;
import pl.project13.janbanery.exceptions.RestClientException;
import pl.project13.janbanery.exceptions.kanbanery.*;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.io.IOException;
import java.lang.reflect.Type;

import static java.lang.String.format;

/**
 * @author Konrad Malawski
 */
public abstract class RestClient {

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

  private String errorMessageFrom(Response response) {
    StringBuilder sb = new StringBuilder().append(response.getStatusCode()).append(" - ").append(response.getStatusText()).append("\n");
    try {
      sb.append(response.getResponseBody());
    } catch (IOException ignored) {
      // ok, so no response could be fetched
      sb.append("[EXCEPTION WHILE GETTING RESPONSE BODY]");
    }

    return sb.toString();
  }

  public abstract Response doPost(String url, KanbaneryResource resource) throws IOException;

  public abstract <T> T doPost(String url, KanbaneryResource resource, Class<?> returnType) throws IOException;

  public abstract Response doGet(String url);

  public abstract <T> T doGet(String url, Type returnType) throws IOException;

  public abstract Response doDelete(String url);

  public abstract Response doPut(String url, String requestBody);

  public abstract <T> T doPut(String url, String requestBody, Class<?> returnType) throws IOException;

  public abstract <T> T doPut(String url, String requestBody, Type returnType) throws IOException;

  public abstract <T> T doPut(String url, KanbaneryResource requestObject, Class<?> returnType) throws IOException;

  public abstract void authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder);

  public abstract void setFormUrlEncodedBody(AsyncHttpClient.BoundRequestBuilder requestBuilder, String requestBody);

  public abstract Response execute(AsyncHttpClient.BoundRequestBuilder requestBuilder) throws RestClientException;

  public abstract void close();
}
