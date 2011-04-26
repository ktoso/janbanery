package pl.project13.janbanery.core.flow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.core.dao.IceBox;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public class IceBoxFlowImpl implements IceBoxFlow {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Tasks  tasks;
  private IceBox iceBox;
  private Task   task;

  public IceBoxFlowImpl(Tasks tasks, IceBox iceBox, Task task) {
    this.tasks = tasks;
    this.iceBox = iceBox;
    this.task = task;
  }

  @Override
  public TaskMoveFlow moveToBoard() throws IOException {
    return tasks.move(task).toBoard();
  }

  @Override
  public void delete() throws IOException {
    tasks.delete(task);
  }

  @Override
  public IceBoxFlow update(Task newValues) throws IOException {
    Task updatedTask = tasks.update(task, newValues).get();
    return new IceBoxFlowImpl(tasks, iceBox, updatedTask);
  }

  @Override
  public Task get() {
    return task;
  }
}
