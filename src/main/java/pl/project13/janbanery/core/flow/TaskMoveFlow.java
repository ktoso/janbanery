package pl.project13.janbanery.core.flow;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * This flow enables the API to fluently move tasks around the Kanban board, making it as fun and powerful as possible.
 *
 * @author Konrad Malawski
 */
public class TaskMoveFlow implements KanbaneryFlow {

  private Configuration   conf;
  private AsyncHttpClient asyncHttpClient;
  private Task            task;

  public TaskMoveFlow(Configuration conf, AsyncHttpClient asyncHttpClient, Task task) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
    this.task = task;
  }

  public TaskMoveFlow toIceBox() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.ICEBOX);
  }

  public TaskMoveFlow toNextColumn() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.NEXT);
  }

  public TaskMoveFlow toPreviousColumn() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.PREVIOUS);
  }

  public TaskMoveFlow toArchive() throws IOException, ExecutionException, InterruptedException {
    return to(TaskLocation.ARCHIVE);
  }

  public TaskMoveFlow to(TaskLocation location) throws IOException, ExecutionException, InterruptedException {

    //todo use RestClient instead of AsyncHttpClient, there is too much boiler plate code here

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient
        .preparePut(conf.getApiUrl() + "tasks.json");
    conf.authorize(requestBuilder);

    requestBuilder.addParameter("column", location.jsonName());

    ListenableFuture<Response> futureResponse = requestBuilder.execute();

    Response response = futureResponse.get();
//    asyncHttpClient.close();

    return this; //todo implement me
  }


}
