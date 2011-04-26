package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Tasks;
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
    return to(TaskLocation.NEXT);
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
