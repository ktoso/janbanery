package pl.project13.janbanery.config;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.config.auth.ApiKeyAuthMode;
import pl.project13.janbanery.config.auth.AuthMode;
import pl.project13.janbanery.config.auth.NoAuthMode;
import pl.project13.janbanery.config.auth.UserPassAuthMode;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;

import static java.lang.String.format;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class DefaultConfiguration implements Configuration {

  protected AuthMode authMode = new NoAuthMode();

  protected DefaultConfiguration() {
    // only for use of subclasses, other usages should create a valid Configuration instance right away
  }

  public DefaultConfiguration(String apiKey) {
    forceKeyAuthMode(apiKey);
  }

  public DefaultConfiguration(String user, String password) {
    forceUserPassAuthMode(user, password);
  }

  @Override
  public void forceUserPassAuthMode(String user, String password) {
    authMode = new UserPassAuthMode(user, password);
  }

  @Override
  public void forceKeyAuthMode(String apiKey) {
    authMode = new ApiKeyAuthMode(apiKey);
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    return authMode.authorize(requestBuilder);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCurrentUser(User user) {
    return authMode.isCurrentUser(user);
  }

  @Override
  public AuthMode getAuthMode() {
    return authMode;
  }

  @Override
  public String getApiUrl() {
    return "https://kanbanery.com/api/v1/";
  }

  @Override
  public String getApiUrl(String workspaceName) {
    return format("https://%s.kanbanery.com/api/v1/", workspaceName);
  }

  @Override
  public String getApiUrl(String workspaceName, Task task) {
    return format("https://%s.kanbanery.com/api/v1/tasks/%d.json", workspaceName, task.getId());
  }

  /**
   * Returns URL setup with workspace and projectId to start calling the API on it.
   * The URL looks like: https://janbanery.kanbanery.com/api/v1/projects/34242/
   *
   * @param workspaceName the workspace name to use for the url
   * @param projectId the project id to use for the url
   * @return the properly set up url to begin calling Kanbanery API on it
   */
  @Override
  public String getApiUrl(String workspaceName, Long projectId) {
    return format("https://%s.kanbanery.com/api/v1/projects/%s/", workspaceName, projectId);
  }

}
