package pl.project13.janbanery.exceptions;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class NoSuchPriorityException extends RuntimeException {
  public NoSuchPriorityException() {
  }

  public NoSuchPriorityException(String message) {
    super(message);
  }

  public NoSuchPriorityException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchPriorityException(Throwable cause) {
    super(cause);
  }
}
