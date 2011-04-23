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
}
