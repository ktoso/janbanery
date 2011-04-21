package pl.project13.janbanery.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import pl.project13.janbanery.config.AuthMode;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.GsonFactory;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class JanbaneryImpl implements Tasks {

  private Configuration conf;

  private AsyncHttpClient asyncHttpClient;
  private Gson            gson;

  public JanbaneryImpl(Configuration conf) {
    this.conf = conf;
    this.gson = GsonFactory.create();
    this.asyncHttpClient = new AsyncHttpClient();
  }

  public JanbaneryImpl(Configuration conf, AsyncHttpClient asyncHttpClient, Gson gson) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
    this.gson = gson;
  }

  @Override
  public List<Task> findAll() {
    return null;
  }

  public AuthMode getAuthMode() {
    return conf.getAuthMode();
  }

  public List<Workspace> findAllWorkspaces() throws IOException, ExecutionException, InterruptedException {
    Future<Response> futureResponse = asyncHttpClient
        .prepareGet(conf.getApiUrl() + "workspaces.json")
        .addHeader("X-Kanbanery-ApiToken", conf.getApiKey())
        .execute();

    Response response = futureResponse.get();
    String responseBody = response.getResponseBody();
    System.out.println(responseBody);

    List<Workspace> workspaces = gson.fromJson(responseBody, new TypeToken<List<Workspace>>() {
    }.getType());

    return workspaces;
  }
}
