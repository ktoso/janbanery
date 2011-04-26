package pl.project13.janbanery.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.core.flow.IceBoxFlow;
import pl.project13.janbanery.core.flow.IceBoxFlowImpl;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class IceBoxImpl implements IceBox {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Tasks         tasks;
  private Configuration conf;
  private RestClient    restClient;

  private Workspace currentWorkspace;
  private Project   currentProject;

  public IceBoxImpl(Tasks tasks, Configuration conf, RestClient restClient) {
    this.tasks = tasks;
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public IceBoxFlow create(Task task) throws IOException {
    String url = getDefaultUrl();

    Task newTask = restClient.doPost(url, task, GsonTypeTokens.TASK);

    return new IceBoxFlowImpl(tasks, this, newTask);
  }

  @Override
  public void delete(Task task) throws IOException {
    tasks.delete(task);
  }

  @Override
  public List<Task> all() throws IOException {
    String url = getDefaultUrl();

    return restClient.doGet(url, GsonTypeTokens.LIST_TASK);
  }

  private String getDefaultUrl() {
    return conf.getApiUrl(currentWorkspace.getName(), currentProject.getId()) + "icebox/tasks.json";
  }

  public IceBox using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}
