package pl.project13.janbanery.exceptions;

import pl.project13.janbanery.exceptions.kanbanery.KanbaneryException;

/**
 * @author Konrad Malawski
 */
public class NotFoundKanbaneryException extends KanbaneryException {

  private static final long serialVersionUID = -4937370912628470085L;

  public static final  int  MAPPED_ERROR_CODE = 404;

  public NotFoundKanbaneryException() {
  }

  public NotFoundKanbaneryException(String message) {
    super(message);
  }

  public NotFoundKanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public NotFoundKanbaneryException(Throwable cause) {
    super(cause);
  }
}
