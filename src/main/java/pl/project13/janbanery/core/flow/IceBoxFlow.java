package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.Task;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface IceBoxFlow extends KanbaneryFlow<Task> {
  /**
   * Move this task to the kanban board of your project.
   *
   * @return the TaskMoveFlow, so you may move the task to some other column right away
   * @throws IOException if the server response could not be fetched
   */
  TaskMoveFlow moveToBoard() throws IOException;

  /**
   * Delete this task from the icebox
   *
   * @throws IOException if the server response could not be fetched
   */
  void delete() throws IOException;

  /**
   * You may update this task with a better description etc.
   * The updated entity is the entity currently being used by this flow,
   * not the passed in value, it's id will be ignored.
   *
   * @param newValues the object with the new values to copy into our underlying task
   * @return a IceBoxFlow instance for other task manipulations
   * @throws IOException if the server response could not be fetched
   */
  IceBoxFlow update(Task newValues) throws IOException;
}
