package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.exceptions.WorkspaceNotFoundException;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class WorkspacesImpl implements Workspaces {

  private Configuration conf;
  private RestClient    restClient;

  public WorkspacesImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Workspace> all() throws IOException {
    String url = conf.getApiUrl() + "user/workspaces.json";
    return restClient.doGet(url, GsonTypeTokens.LIST_WORKSPACE);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Workspace byName(String name) throws IOException, WorkspaceNotFoundException {
    List<Workspace> allWorkspaces = all();
    for (Workspace workspace : allWorkspaces) {
      if (workspace.getName().equals(name)) {
        return workspace;
      }
    }
    throw new WorkspaceNotFoundException("Could not find workspace named '" + name + "'");
  }

}
