package pl.project13.janbanery.exceptions.kanbanery;

/**
 * @author Konrad Malawski
 */
public class TaskAlreadyInLastColumnException extends InvalidEntityKanbaneryException implements TaskMovementException {

  private static final long serialVersionUID = -4279952997167094945L;

  public TaskAlreadyInLastColumnException(String message) {
    super(message);
  }

  public TaskAlreadyInLastColumnException(String message, Throwable cause) {
    super(message, cause);
  }

  public TaskAlreadyInLastColumnException(Throwable cause) {
    super(cause);
  }

  public static boolean isBestExceptionFor(String response) {
    return response.contains("task is already in last column");
  }
}
