package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public interface TaskMarkFlow extends KanbaneryFlow<Task> {

  /**
   *
   * @return
   * @throws IOException
   */
  public TaskFlow asReadyToPull() throws IOException;

  /**
   *
   * @return
   * @throws IOException
   */
  public TaskFlow asNotReadyToPull() throws IOException;
}
