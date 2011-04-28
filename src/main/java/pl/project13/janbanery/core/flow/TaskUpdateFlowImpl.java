package pl.project13.janbanery.core.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.core.dao.Columns;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.*;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class TaskUpdateFlowImpl implements TaskUpdateFlow {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Tasks   tasks;
  private Columns columns;
  private Task    task;

  public TaskUpdateFlowImpl(Tasks tasks, Columns columns, Task task) {
    this.tasks = tasks;
    this.columns = columns;
    this.task = task;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow title(String title) throws IOException {
    Task commandObject = new Task();
    commandObject.setTitle(title);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow description(String description) throws IOException {
    Task commandObject = new Task();
    commandObject.setDescription(description);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow assignTo(User user) throws IOException {
    Task commandObject = new Task();
    commandObject.setOwnerId(user.getId());

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow position(Integer positionInColumn) throws IOException {
    Task commandObject = new Task();
    commandObject.setPosition(positionInColumn);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow priority(Priority priority) throws IOException {
    Task commandObject = new Task();
    commandObject.setPriority(priority);

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow type(TaskType taskType) throws IOException {
    Task commandObject = new Task();
    commandObject.setTaskTypeId(taskType.getId());

    task = tasks.update(task, commandObject).get();

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskUpdateFlow estimate(Estimate estimate) throws IOException {
    Task commandObject = new Task();
    commandObject.setEstimateId(estimate.getId());

    task = tasks.update(task, commandObject).get();

    return this;
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
