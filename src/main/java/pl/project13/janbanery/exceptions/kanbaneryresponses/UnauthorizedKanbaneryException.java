package pl.project13.janbanery.exceptions.kanbaneryresponses;

/**
 * This exception is mapping an 401 response from the Kanbanery API.
 * <blockquote>If the API token is invalid or is missing the API returns 401 (Unauthorized) HTTP status.</blockquote>
 *
 * @author Konrad Malawski
 */
public class UnauthorizedKanbaneryException extends KanbaneryException {

  private static final long serialVersionUID  = -2697280407763407943L;
  public static final  int  MAPPED_ERROR_CODE = 401;

  public UnauthorizedKanbaneryException() {
  }

  public UnauthorizedKanbaneryException(String message) {
    super(message);
  }

  public UnauthorizedKanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnauthorizedKanbaneryException(Throwable cause) {
    super(cause);
  }
}
