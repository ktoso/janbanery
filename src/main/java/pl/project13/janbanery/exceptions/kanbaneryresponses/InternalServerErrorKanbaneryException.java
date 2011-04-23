package pl.project13.janbanery.exceptions.kanbaneryresponses;

/**
 * @author Konrad Malawski
 */
public class InternalServerErrorKanbaneryException extends KanbaneryException {
  private static final long serialVersionUID = 1878141281487347145L;

  public InternalServerErrorKanbaneryException() {
    super();
  }

  public InternalServerErrorKanbaneryException(String message) {
    super(message);
  }

  public InternalServerErrorKanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public InternalServerErrorKanbaneryException(Throwable cause) {
    super(cause);
  }
}
