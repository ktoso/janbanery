package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This flow may be used to perform actions on a fetched or
 *
 * @author Konrad Malawski
 */
public class TaskFlowImpl implements TaskFlow {

  private Tasks tasks;
  private Task  task;

  public TaskFlowImpl(Tasks tasks, Task task) {
    this.tasks = tasks;
    this.task = task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete() throws IOException, ExecutionException, InterruptedException {
    tasks.delete(task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow move() {
    return new TaskMoveFlowImpl(tasks, task);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMarkFlow mark() {
    return new TaskMarkFlowImpl(tasks, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Task get() {
    return task;
  }
}
