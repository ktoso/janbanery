package pl.project13.janbanery.core.flow;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.additions.TaskLocation;

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

  public TaskMoveFlow toIceBox() {
    return to(TaskLocation.ICEBOX);
  }

  public TaskMoveFlow toNextColumn() {
    return to(TaskLocation.NEXT);
  }

  public TaskMoveFlow toPreviousColumn() {
    return to(TaskLocation.PREVIOUS);
  }

  public TaskMoveFlow toArchive() {
    return to(TaskLocation.ARCHIVE);
  }

  public TaskMoveFlow to(TaskLocation location) {

    //todo opakować AsyncHttpClient

    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient.preparePut(conf.getApiUrl() + "tasks.json");
    conf.authorize(requestBuilder);

    requestBuilder.addParameter("column", location.jsonName());

    requestBuilder.execute()

    return this; //todo implement me
  }


}
