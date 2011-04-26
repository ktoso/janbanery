package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.resources.Estimate;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class EstimatesImpl implements Estimates {

  private Configuration conf;
  private RestClient    restClient;

  private Workspace currentWorkspace;
  private Project   currentProject;

  public EstimatesImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Estimate> all() throws IOException {
    String url = getDefaultUrl();
    return restClient.doGet(url, GsonTypeTokens.LIST_ESTIMATE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Estimate> allIn(Project project) throws IOException {
    String url = getDefaultUrl(project);
    return restClient.doGet(url, GsonTypeTokens.LIST_ESTIMATE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Estimate byId(Long id) throws IOException {
    String url = getEstimateUrl(id);
    return restClient.doGet(url, GsonTypeTokens.ESTIMATE);
  }

  private String getEstimateUrl(Long id) {
    return conf.getApiUrl(currentWorkspace.getName(), "estimates", id);
  }

  private String getDefaultUrl() {
    return getDefaultUrl(currentProject);
  }

  private String getDefaultUrl(Project project) {
    return conf.getApiUrl(currentWorkspace.getName(), project.getId(), "estimates");
  }

  public Estimates using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}
