package pl.project13.janbanery.exceptions;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class InvalidApiKeyException extends RuntimeException {
  public InvalidApiKeyException() {
  }

  public InvalidApiKeyException(String message) {
    super(message);
  }

  public InvalidApiKeyException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidApiKeyException(Throwable cause) {
    super(cause);
  }
}
