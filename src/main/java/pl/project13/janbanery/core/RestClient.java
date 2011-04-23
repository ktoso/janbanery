package pl.project13.janbanery.core;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import pl.project13.janbanery.config.Configuration;
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

  private Configuration   conf;
  private AsyncHttpClient asyncHttpClient;

  public RestClient(Configuration conf, AsyncHttpClient asyncHttpClient) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
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
    } finally {
//      asyncHttpClient.close();
    }

    verifyResponseCode(response);

    return response;
  }

  // todo un static me!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  public static void verifyResponseCode(Response response) throws KanbaneryException {
    Integer statusCode = response.getStatusCode();

    switch (statusCode) {
      case DeleteFailedKanbaneryException.MAPPED_ERROR_CODE:
        throw new DeleteFailedKanbaneryException(response.toString());
      case ForbiddenOperationKanbaneryException.MAPPED_ERROR_CODE:
        throw new ForbiddenOperationKanbaneryException(response.toString());
      case InternalServerErrorKanbaneryException.MAPPED_ERROR_CODE:
        throw new InternalServerErrorKanbaneryException(response.toString());
      case InvalidEntityKanbaneryException.MAPPED_ERROR_CODE:
        throw new InvalidEntityKanbaneryException(response.toString());
      case UnauthorizedKanbaneryException.MAPPED_ERROR_CODE:
        throw new UnauthorizedKanbaneryException(response.toString());
    }

    if (statusCode > 400) {
      throw new KanbaneryException(format("Unexpected response code '%d' and message: '%s'.", statusCode, response.getStatusText()));
    }


  }
}
