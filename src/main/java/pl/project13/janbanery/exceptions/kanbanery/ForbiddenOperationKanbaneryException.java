package pl.project13.janbanery.exceptions.kanbanery;

/**
 * This exception is mapping an 403 response from the Kanbanery API.
 * <blockquote>
 * If the role assigned to user accessing the API doesn't allow them to perform the requested
 * action upon the requested resource the API returns403 (Forbidden) HTTP status.
 * </blockquote>
 *
 * @author Konrad Malawski
 */
public class ForbiddenOperationKanbaneryException extends KanbaneryException {

  private static final long serialVersionUID  = -7400474381727697473L;
  public static final  int  MAPPED_ERROR_CODE = 403;

  public ForbiddenOperationKanbaneryException() {
    super();
  }

  public ForbiddenOperationKanbaneryException(String message) {
    super(message);
  }

  public ForbiddenOperationKanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public ForbiddenOperationKanbaneryException(Throwable cause) {
    super(cause);
  }
}
