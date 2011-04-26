package pl.project13.janbanery.exceptions.kanbanery;

/**
 * @author Konrad Malawski
 */
public class TaskAlreadyInFirstColumnException extends InvalidEntityKanbaneryException implements TaskMovementException {

  private static final long serialVersionUID = 124950722383696241L;

  public TaskAlreadyInFirstColumnException(String message) {
    super(message);
  }

  public TaskAlreadyInFirstColumnException(String message, Throwable cause) {
    super(message, cause);
  }

  public TaskAlreadyInFirstColumnException(Throwable cause) {
    super(cause);
  }

  public static boolean isBestExceptionFor(String response) {
    return response.contains("task is already in first column");
  }
}
