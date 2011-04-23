package pl.project13.janbanery.exceptions.kanbaneryresponses;

/**
 * This exception is mapping an 409 response code from the Kanbanery API.
 * <blockquote>
 * If a resource removal (DELETE) request fails for some reason
 * the API will return 409 (Conflict) HTTP status.
 * </blockquote>
 *
 * @author Konrad Malawski
 */
public class DeleteFailedKanbaneryException extends KanbaneryException {

  private static final long serialVersionUID  = 3373392157247136394L;
  public static final  int  MAPPED_ERROR_CODE = 409;

  public DeleteFailedKanbaneryException() {
  }

  public DeleteFailedKanbaneryException(String message) {
    super(message);
  }

  public DeleteFailedKanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public DeleteFailedKanbaneryException(Throwable cause) {
    super(cause);
  }
}
