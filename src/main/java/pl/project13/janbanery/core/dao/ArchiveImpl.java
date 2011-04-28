package pl.project13.janbanery.core.dao;

import com.sun.corba.se.spi.orbutil.threadpool.Work;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class ArchiveImpl implements Archive {
  private Tasks tasks;
  private Configuration conf;
  private RestClient restClient;
  private Workspace  currentWorkspace;
  private Project currentProject;

  public ArchiveImpl(Tasks tasks, Configuration conf, RestClient restClient) {
    this.tasks = tasks;
    this.conf = conf;
    this.restClient = restClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Task> all() throws IOException {
    String url = getArchiveUrl();

    return restClient.doGet(url, GsonTypeTokens.LIST_TASK);
  }


  /**
   * {@inheritDoc}
   */
  @Override public boolean contains(Task task) throws IOException {
    String url = getArchiveUrl();

    return all().contains(task);
  }

  private String getArchiveUrl() {
    return conf.getApiUrl(currentWorkspace.getName(), currentProject.getId(), "archive");
  }

  public Archive using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}
