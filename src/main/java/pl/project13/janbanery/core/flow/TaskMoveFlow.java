package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface TaskMoveFlow extends KanbaneryFlow<Task> {

  TaskMoveFlow toNextColumn() throws IOException;

  TaskMoveFlow toPreviousColumn() throws IOException;

  TaskMoveFlow to(TaskLocation location) throws IOException;

  TaskMoveFlow to(Column column) throws IOException;

  /**
   * Move this task to the project's <strong>icebox</strong>.
   * <strong>Requirement</strong>: The task MUST be in the FIRST column.
   *
   * @return a TaskMoveFlow instance to allow further task operations
   * @throws IOException if unable to fetch the server response
   */
  TaskMoveFlow toIceBox() throws IOException;

  /**
   * Move this task to the project's <strong>archive</strong>.
   * <strong>Requirement</strong>: The task MUST be in the LAST column.
   *
   * @return a TaskMoveFlow instance to allow further task operations
   * @throws IOException if unable to fetch the server response
   */
  TaskMoveFlow toArchive() throws IOException;

  /**
   * Move this task to the project's kanban <strong>board</strong>.
   * <strong>Requirement</strong>: The task MUST be in the iceBox OR archive.
   *
   * @return a TaskMoveFlow instance to allow further task operations
   * @throws IOException if unable to fetch the server response
   */
  TaskMoveFlow toBoard() throws IOException;
}
