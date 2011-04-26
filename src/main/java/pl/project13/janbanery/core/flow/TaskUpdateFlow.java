package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.*;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface TaskUpdateFlow extends KanbaneryFlow<Task> {

  TaskUpdateFlow title(String title) throws IOException;

  TaskUpdateFlow description(String description) throws IOException;

  TaskUpdateFlow assignTo(User user) throws IOException;

  /**
   * Move the task to the specified priority in the column.
   * This number is 1 based and counting from the top, so "`" is "on the top".
   *
   * @param positionInColumn 1 based number where this task should be moved in the column
   * @return
   * @throws IOException
   */
  TaskUpdateFlow position(Integer positionInColumn) throws IOException;

  TaskUpdateFlow priority(Priority priority) throws IOException;

  TaskUpdateFlow type(TaskType taskType) throws IOException;

  TaskUpdateFlow estimate(Estimate estimate) throws IOException;

  // go to other flows ----------------

  TaskMoveFlow move();

  TaskMarkFlow mark();

}
