package pl.project13.janbanery.core;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.encoders.FormUrlEncodedBodyGenerator;
import pl.project13.janbanery.exceptions.RestClientException;
import pl.project13.janbanery.exceptions.kanbanery.*;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static java.lang.String.format;

/**
 * Used to encapsulate all the logic going on when we call a RESTful webservice via the
 * AsyncHttpClient. It's great but there's too many lines involved in doing a GET and parsing
 * it back into our OOP World. This is what this class does - it is a simple Facade to {@link AsyncHttpClient}.
 *
 * @author Konrad Malawski
 */
public class RestClient {

  // todo replace in place HttpClient calls with this facade

  private Configuration               conf;
  private AsyncHttpClient             asyncHttpClient;
  private FormUrlEncodedBodyGenerator encodedBodyGenerator;

  public RestClient(Configuration conf, AsyncHttpClient asyncHttpClient, FormUrlEncodedBodyGenerator encodedBodyGenerator) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
    this.encodedBodyGenerator = encodedBodyGenerator;
  }

  public Response doGet(String resource) {
    // todo implement me

    Response response;

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient
        .prepareGet(conf.getApiUrl() + resource);

    conf.authorize(requestBuilder);

//    requestBuilder.addParameter("column", location.jsonName());

    try {

      ListenableFuture<Response> futureResponse = requestBuilder.execute();

      response = futureResponse.get();
    } catch (InterruptedException e) {
      throw new RestClientException("Interrupted while waiting for server response", e);
    } catch (ExecutionException e) {
      throw new RestClientException("Tried to retrieve result from aborted action.", e);
    } catch (IOException e) {
      throw new RestClientException("Encountered IOException while executing REST request.", e);
    }

    verifyResponseCode(response);

    return response;
  }

  // todo un static me!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  public static void verifyResponseCode(Response response) throws KanbaneryException {
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
}
