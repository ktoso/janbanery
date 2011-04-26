package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class TaskMarkFlowImpl implements TaskMarkFlow {

  private Tasks tasks;
  private Task  task;

  public TaskMarkFlowImpl(Tasks tasks, Task task) {
    this.tasks = tasks;
    this.task = task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow asReadyToPull() throws IOException {
    TaskFlow taskMoveFlow = tasks.markAsReadyToPull(task);
    task = taskMoveFlow.get();

    return new TaskFlowImpl(tasks, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow asNotReadyToPull() throws IOException {
    TaskFlow taskMoveFlow = tasks.markAsNotReadyToPull(task);
    task = taskMoveFlow.get();

    return new TaskFlowImpl(tasks, task);
  }

  @Override
  public Task get() {
    return task;
  }
}
