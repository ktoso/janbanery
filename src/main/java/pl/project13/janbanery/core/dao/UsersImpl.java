package pl.project13.janbanery.core.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class UsersImpl implements Users {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Configuration conf;
  private RestClient    restClient;

  private Workspace currentWorkspace;
  private Project   currentProject;

  public UsersImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public User current() throws IOException {
    String url = getMyUserUrl();

    return restClient.doGet(url, GsonTypeTokens.USER);
  }

  @Override
  public List<User> all() throws IOException {
    String url = getProjectUsersUrl();
    return restClient.doGet(url, GsonTypeTokens.LIST_USER);
  }

  @Override
  public List<User> allInProject(Project project) throws IOException {
    String url = getProjectUsersUrl(project);
    return restClient.doGet(url, GsonTypeTokens.LIST_USER);
  }

  @Override
  public User nobody() throws IOException {
    List<User> users = all();
    for (User user : users) {
      if (user.getFirstName().equals("nobody")) {
        return user;
      }
    }

    throw new EntityNotFoundException("Could not find 'nobody user'");
  }

  private String getMyUserUrl() {
    return conf.getApiUrl() + "user.json";
  }

  public String getProjectUsersUrl() {
    return getProjectUsersUrl(currentProject);
  }

  private String getProjectUsersUrl(Project project) {
    return conf.getApiUrl(currentWorkspace.getName(), project.getId(), "users.json");
  }

  public Users using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}

