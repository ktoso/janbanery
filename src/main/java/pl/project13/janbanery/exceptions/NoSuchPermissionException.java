package pl.project13.janbanery.exceptions;

/**
 * Thrown if you try to parse an {@link pl.project13.janbanery.resources.Permission}
 * but the jsonName of it is not valid.
 *
 * @author Konrad Malawski
 */
public class NoSuchPermissionException extends RuntimeException {
  public NoSuchPermissionException() {
  }

  public NoSuchPermissionException(String message) {
    super(message);
  }

  public NoSuchPermissionException(String message, Throwable cause) {
    super(message, cause);
  }

  public NoSuchPermissionException(Throwable cause) {
    super(cause);
  }
}
