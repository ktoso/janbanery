package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public interface TaskFlow extends KanbaneryFlow<Task> {

  /**
   * Get the task passed into this flow.
   * If you, for example, just created a task this method will return the fully populated Task object,
   * (along with owner and creation  dates information etc).
   *
   * @return the previously created Task (used by this flow)
   */
  Task get();

  /**
   * If you just queried for a Task, you may delete it right away using this method.
   *
   * @throws IOException
   * @throws ExecutionException
   * @throws InterruptedException
   */
  void delete() throws IOException;

  /**
   * @return
   */
  TaskMarkFlow mark();

  /**
   * @return
   */
  TaskMoveFlow move();
}
