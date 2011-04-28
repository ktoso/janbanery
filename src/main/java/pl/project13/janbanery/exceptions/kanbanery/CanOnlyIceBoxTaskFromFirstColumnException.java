package pl.project13.janbanery.exceptions.kanbanery;

/**
 * @author Konrad Malawski
 */
public class CanOnlyIceBoxTaskFromFirstColumnException extends InvalidEntityKanbaneryException {

  private static final long serialVersionUID = -5386706711170069973L;

  public CanOnlyIceBoxTaskFromFirstColumnException(String message) {
    super(message);
  }

  public CanOnlyIceBoxTaskFromFirstColumnException(String message, Throwable cause) {
    super(message, cause);
  }

  public CanOnlyIceBoxTaskFromFirstColumnException(Throwable cause) {
    super(cause);
  }

  public static boolean isBestExceptionFor(String response) {
    return response.contains("tasks can be put to icebox only from first column");
  }
}
