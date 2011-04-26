package pl.project13.janbanery.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Konrad Malawski
 */
public class TaskTypesImpl implements TaskTypes {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;

  private Workspace currentWorkspace;
  private Project   currentProject;

  private RestClient restClient;

  public TaskTypesImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public List<TaskType> all() throws ExecutionException, InterruptedException, IOException {
    String url = getDefaultGetUrl();
    return restClient.doGet(url, GsonTypeTokens.LIST_TASK_TYPE);
  }

  @Override
  public TaskType any() throws IOException, ExecutionException, InterruptedException {
    return all().get(0);
  }

  /**
   * Set this instance to fallback to using the passed in workspace, when none other is passed explicitly.
   * Using this once means you don't have to pass this workspace instance around any longer when performing
   * actions on these tasks.
   *
   * @param currentWorkspace the workspace to be used in all consequent requests
   * @param currentProject   the project to be used in all consequent requests
   * @return the same instance of {@link TasksImpl} as before, but setup to use the currentWorkspace
   */
  public TaskTypesImpl using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }

  private String getDefaultGetUrl() {
    return conf.getApiUrl(currentWorkspace.getName(), currentProject.getId()) + "task_types.json";
  }
}
