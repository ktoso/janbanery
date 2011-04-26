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
    TaskMoveFlow taskMoveFlow = tasks.markReadyToPull(task);
    this.task = taskMoveFlow.get();

    return new TaskFlowImpl(tasks, task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskFlow asNotReadyToPull() throws IOException {
    TaskMoveFlow taskMoveFlow = tasks.markNotReadyToPull(task);
    this.task = taskMoveFlow.get();

    return new TaskFlowImpl(tasks, task);
  }

  @Override
  public Task get() {
    return task;
  }
}
