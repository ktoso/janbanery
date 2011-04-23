package pl.project13.janbanery.exceptions.kanbanery;

/**
 * If an 500 Internal Server Error would happen anyhow on Kanbanery,
 * this exception will be thrown. It is advised to wait a minute or two before
 * issuing any further requests - just an idea though.
 *
 * @author Konrad Malawski
 */
public class KanbaneryException extends RuntimeException {

  private static final long serialVersionUID  = -600128530560346354L;
  public static final  int  MAPPED_ERROR_CODE = 500;

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
