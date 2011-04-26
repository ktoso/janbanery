package pl.project13.janbanery.core.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Estimate;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskType;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class TaskUpdateFlowImpl implements TaskUpdateFlow {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Tasks tasks;
  private Task  task;

  public TaskUpdateFlowImpl(Tasks tasks, Task task) {
    this.tasks = tasks;
    this.task = task;
  }

  @Override
  public TaskUpdateFlow type(TaskType taskType) throws IOException {
    Task commandObject = new Task();
    commandObject.setTaskTypeId(taskType.getId());

    task = tasks.update(task, commandObject).get();

    return this;
  }

  @Override
  public TaskUpdateFlow estimate(Estimate estimate) throws IOException {
    Task commandObject = new Task();
    commandObject.setTaskTypeId(estimate.getId());

    task = tasks.update(task, commandObject).get();

    return this;
  }

  @Override
  public Task get() {
    return task;
  }
}
