package pl.project13.janbanery.exceptions;

/**
 * Thrown when the {@link pl.project13.janbanery.core.RestClient} encounters any problems
 * while fetching the server response, note that Kanbanery error response codes are mapped as subclasses of
 * {@link pl.project13.janbanery.exceptions.kanbanery.KanbaneryException}.
 * This exception signals problems with network or interruption of a Future waiting etc.
 *
 * @author Konrad Malawski
 */
public class RestClientException extends RuntimeException {

  private static final long serialVersionUID = 7601400151619606970L;

  public RestClientException() {
  }

  public RestClientException(String message) {
    super(message);
  }

  public RestClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public RestClientException(Throwable cause) {
    super(cause);
  }
}
