package pl.project13.janbanery.exceptions.kanbanery;

/**
 * This exception is mapping an 422 response code from the Kanbanery API.
 * <blockquote>
 * If you're trying to create or modify any resource and some parameters have invalid values
 * or some required parameters are missing the API returns 422 (Unprocessable entity) HTTP
 * status and error description in the requested format (JSON or XML).
 * </blockquote>
 *
 * @author Konrad Malawski
 */
public class InvalidEntityKanbaneryException extends KanbaneryException {

  private static final long serialVersionUID  = -5929890856952121819L;
  public static final  int  MAPPED_ERROR_CODE = 422;

  public InvalidEntityKanbaneryException(String message) {
    super(message);
  }

  public InvalidEntityKanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidEntityKanbaneryException(Throwable cause) {
    super(cause);
  }

  /**
   * May be used as "Exception factory" to determine the "best" exception to be thrown
   * for such JSON response. For example we may throw "TaskAlreadyInFirstColumnException"
   * if the JSON contains a message informing us about this.
   *
   * @param response the JSON server response, containing a error messages
   * @return the "best" exception to be thrown for such an response JSON, based on it's error messages
   */
  public static KanbaneryException mostSpecializedException(String response) {
    if (response == null || "".equals(response)) {
      return new InvalidEntityKanbaneryException(response);
    } else if (TaskAlreadyInFirstColumnException.isBestExceptionFor(response)) {
      return new TaskAlreadyInFirstColumnException(response);
    } else if (TaskAlreadyInLastColumnException.isBestExceptionFor(response)) {
      return new TaskAlreadyInLastColumnException(response);
    } else if(PositionExceedsNumberOfTasksInColumnException.isBestExceptionFor(response)){
      return new PositionExceedsNumberOfTasksInColumnException(response);
    } else {
      return new InvalidEntityKanbaneryException(response);
    }
  }
}
