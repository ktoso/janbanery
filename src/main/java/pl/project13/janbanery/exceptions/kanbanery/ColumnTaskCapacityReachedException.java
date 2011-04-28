package pl.project13.janbanery.exceptions.kanbanery;

import pl.project13.janbanery.resources.Column;

/**
 * Thrown if you try to move a task into a {@link Column} which already has reached
 * it's WIP limit ({@link Column#capacity}).
 *
 * @author Konrad Malawski
 */
public class ColumnTaskCapacityReachedException extends InvalidEntityKanbaneryException {

  private static final long serialVersionUID = -6848225663205864947L;

  public ColumnTaskCapacityReachedException(String message) {
    super(message);
  }

  public ColumnTaskCapacityReachedException(String message, Throwable cause) {
    super(message, cause);
  }

  public ColumnTaskCapacityReachedException(Throwable cause) {
    super(cause);
  }

  public static boolean isBestExceptionFor(String response) {
    return response.contains("exceeds number of tasks in the column");
  }
}
