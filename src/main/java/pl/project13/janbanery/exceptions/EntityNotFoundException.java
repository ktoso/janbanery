package pl.project13.janbanery.exceptions;

/**
 * @author Konrad Malawski
 */
public class EntityNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 5485914495966609677L;

  public EntityNotFoundException() {
  }

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public EntityNotFoundException(Throwable cause) {
    super(cause);
  }
}
