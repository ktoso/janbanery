package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Columns;
import pl.project13.janbanery.core.dao.ColumnsImpl;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class TaskMarkFlowImpl implements TaskMarkFlow {

  private Columns columns;
  private Tasks tasks;
  private Task  task;

  public TaskMarkFlowImpl(Tasks tasks, Columns columns, Task task) {
    this.tasks = tasks;
    this.columns = columns;
    this.task = task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow asReadyToPull() throws IOException {
    TaskFlow taskMoveFlow = tasks.markAsReadyToPull(task);
    task = taskMoveFlow.get();

    return new TaskFlowImpl(tasks, columns, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow asNotReadyToPull() throws IOException {
    TaskFlow taskMoveFlow = tasks.markAsNotReadyToPull(task);
    task = taskMoveFlow.get();

    return new TaskFlowImpl(tasks, columns, task);
  }

  @Override
  public Task get() {
    return task;
  }
}
