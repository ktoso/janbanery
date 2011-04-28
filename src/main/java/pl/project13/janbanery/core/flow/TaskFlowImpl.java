package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Columns;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;

/**
 * This flow may be used to perform actions on a fetched or
 *
 * @author Konrad Malawski
 */
public class TaskFlowImpl implements TaskFlow {

  private Columns columns;
  private Tasks tasks;
  private Task  task;

  public TaskFlowImpl(Tasks tasks, Columns columns, Task task) {
    this.tasks = tasks;
    this.columns = columns;
    this.task = task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete() throws IOException {
    tasks.delete(task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMoveFlow move() {
    return new TaskMoveFlowImpl(tasks, columns, task);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public TaskMarkFlow mark() {
    return new TaskMarkFlowImpl(tasks, columns, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Task get() {
    return task;
  }
}
