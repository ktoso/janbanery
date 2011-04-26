package pl.project13.janbanery.exceptions.kanbanery;

import pl.project13.janbanery.exceptions.kanbanery.KanbaneryException;

/**
 * @author Konrad Malawski
 */
public class CanNotDeleteNotEmptyColumnException extends KanbaneryException {
  private static final long serialVersionUID = -672905745028613743L;

  public CanNotDeleteNotEmptyColumnException() {
  }

  public CanNotDeleteNotEmptyColumnException(String message) {
    super(message);
  }

  public CanNotDeleteNotEmptyColumnException(String message, Throwable cause) {
    super(message, cause);
  }

  public CanNotDeleteNotEmptyColumnException(Throwable cause) {
    super(cause);
  }
}
