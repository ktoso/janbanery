package pl.project13.janbanery.core;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.auth.AuthMode;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.dao.*;
import pl.project13.janbanery.encoders.ReflectionsBodyGenerator;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.exceptions.ProjectNotFoundException;
import pl.project13.janbanery.resources.Project;
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
public class Janbanery {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration   conf;
  private AsyncHttpClient asyncHttpClient;
  private Gson            gson;

  private Workspace currentWorkspace;
  private Project   currentProject;
  private ReflectionsBodyGenerator bodyGenerator = new ReflectionsBodyGenerator();

  public Janbanery(Configuration conf, AsyncHttpClient asyncHttpClient, Gson gson) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
    this.gson = gson;
  }

  public AuthMode getAuthMode() {
    return conf.getAuthMode();
  }

  public List<Workspace> workspaces() throws IOException, ExecutionException, InterruptedException {
    AsyncHttpClient.BoundRequestBuilder requestBuilder = asyncHttpClient
        .prepareGet(conf.getApiUrl() + "workspaces.json");
    conf.authorize(requestBuilder);

    Future<Response> futureResponse = requestBuilder.execute();

    Response response = futureResponse.get();
//    asyncHttpClient.close();

    String responseBody = response.getResponseBody();
    log.info("Fetched response: {}", responseBody);

    List<Workspace> workspaces = gson.fromJson(responseBody, GsonTypeTokens.LIST_WORKSPACE);
    assert workspaces != null : "Workspaces should not be null here";
    return workspaces;
  }

  public Workspace workspace(String name) throws IOException, ExecutionException, InterruptedException {
    for (Workspace workspace : workspaces()) {
      if (workspace.getName().equals(name)) {
        return workspace;
      }
    }
    throw new EntityNotFoundException("Could not find workspace with name");
  }

  /**
   * Delegates to {@link Janbanery#workspace(String)} and keeps this workspace as the default one.
   * This method does also setup a default project if there is one. If you want to select the {@link Project}
   * you'll be working on, please get the return value of this method, and then call {@link Janbanery#using(Project)}.
   * From now on, all calls requiring a workspace will use this workspace and it's first present project,
   * you may change the default workspace at anytime by calling this method again.
   *
   * @param name the name of the workspace to be used
   * @return the fetched workspace
   * @throws IOException
   * @throws ExecutionException
   * @throws InterruptedException
   */
  public Workspace usingWorkspace(String name) throws IOException, ExecutionException, InterruptedException {
    Workspace workspace = workspace(name);

    Project firstProject = workspace.getProjects().get(0);
    using(firstProject);

    return using(workspace);
  }

  /**
   * Setup the used {@link Project} by it's name.
   *
   * @param name the name of the project you want to use (it should be from the current {@link Workspace}
   * @return the now being used project
   * @throws IOException
   * @throws ExecutionException
   * @throws InterruptedException
   * @throws ProjectNotFoundException will be thrown if no project with such name exists in the current workspace
   */
  public Project usingProject(String name) throws IOException, ExecutionException, InterruptedException, ProjectNotFoundException {
    return this.usingProject(currentWorkspace, name);
  }

  public Project usingProject(Workspace workspace, String name) throws IOException, ExecutionException, InterruptedException, ProjectNotFoundException {
    for (Project project : workspace.getProjects()) {
      if (project.getName().equals(name)) {
        return using(project);
      }
    }
    throw new ProjectNotFoundException("The workspace '" + workspace.getName() + "' has no project named '" + name + "'.");
  }

  private Workspace using(Workspace workspace) {
    currentWorkspace = workspace;
    return currentWorkspace;
  }

  private Project using(Project project) {
    currentProject = project;
    return currentProject;
  }

  /* return initially setup instances of dao objects */

  public Tasks tasks() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);
    return new TasksImpl(conf, restClient, gson).using(currentWorkspace, currentProject);
  }

  public TaskTypes taskTypes() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);
    return new TaskTypesImpl(conf, restClient).using(currentWorkspace, currentProject);
  }

  public Users users() {
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, bodyGenerator);
    return new UsersImpl(conf, restClient).using(currentWorkspace);
  }
}
