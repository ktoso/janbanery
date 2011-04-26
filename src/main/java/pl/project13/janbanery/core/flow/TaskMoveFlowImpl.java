package pl.project13.janbanery.core.flow;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This flow enables the API to fluently move tasks around the Kanban board, making it as fun and powerful as possible.
 *
 * @author Konrad Malawski
 */
public class TaskMoveFlowImpl implements TaskMoveFlow {

  private Tasks tasks;

  private Task task;

  public TaskMoveFlowImpl(Tasks tasks, Task task) {
    this.tasks = tasks;
    this.task = task;
  }

  @Override
  public TaskMoveFlow readyToPull() throws IOException, ExecutionException, InterruptedException {
    return tasks.markReadyToPull(task);
  }

  @Override
  public TaskMoveFlow notReadyToPull() throws IOException, ExecutionException, InterruptedException {
    return tasks.markNotReadyToPull(task);
  }

  @Override
  public TaskMoveFlow toIceBox() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.ICEBOX);
  }

  @Override
  public TaskMoveFlow toNextColumn() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.NEXT);
  }

  @Override
  public TaskMoveFlow toPreviousColumn() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.PREVIOUS);
  }

  @Override
  public TaskMoveFlow toArchive() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.ARCHIVE);
  }

  @Override
  public TaskMoveFlow to(TaskLocation location) throws IOException, ExecutionException, InterruptedException {
    tasks.move(task, location);
    return this;
  }


}
