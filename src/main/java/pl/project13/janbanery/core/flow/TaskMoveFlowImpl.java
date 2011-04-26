package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.exceptions.kanbanery.InternalServerErrorKanbaneryException;
import pl.project13.janbanery.exceptions.kanbanery.TaskAlreadyInLastColumnException;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This flow enables the API to fluently move tasks around the Kanban board, making it as fun and powerful as possible.
 *
 * @author Konrad Malawski
 */
public class TaskMoveFlowImpl implements TaskMoveFlow {

  private Tasks tasks;
  private Task  task;

  public TaskMoveFlowImpl(Tasks tasks, Task task) {
    this.tasks = tasks;
    this.task = task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow toIceBox() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.ICEBOX);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow toBoard() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.BOARD);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow toNextColumn() throws IOException, ExecutionException, InterruptedException {
    TaskMoveFlow moveFlow;
    try {
      moveFlow = to(TaskLocation.NEXT);
    } catch (InternalServerErrorKanbaneryException e) { // fixme this is a bug workaround
      // kanbanery does handle "ArrayIndexOutOfBounds" right for movement to the left,
      // but for movement to the right it fails and throws a 500 Internal Server Error...
      throw new TaskAlreadyInLastColumnException("{position: 'task is already in last column'}"); // todo this is a kanbanery bug workaround
    }
    return moveFlow;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow toPreviousColumn() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.PREVIOUS);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow toArchive() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.ARCHIVE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow to(TaskLocation location) throws IOException, ExecutionException, InterruptedException {
    TaskFlow move = tasks.move(task, location);

    task = move.get();
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Task get() {
    return task;
  }
}
