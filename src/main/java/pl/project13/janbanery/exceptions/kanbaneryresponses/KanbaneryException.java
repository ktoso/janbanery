package pl.project13.janbanery.exceptions.kanbaneryresponses;

/**
 * This exception can be thrown if Kanbanery responds in an unexpected way,
 * which we cannot map to any other exception.
 *
 * @author Konrad Malawski
 */
public class KanbaneryException extends RuntimeException {

  private static final long serialVersionUID = -600128530560346354L;

  public KanbaneryException() {
  }

  public KanbaneryException(String message) {
    super(message);
  }

  public KanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public KanbaneryException(Throwable cause) {
    super(cause);
  }
}
