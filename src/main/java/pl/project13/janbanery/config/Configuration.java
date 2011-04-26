package pl.project13.janbanery.config;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.config.auth.AuthMode;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.User;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public interface Configuration {

  AuthMode getAuthMode();

  void forceUserPassAuthMode(String user, String password);

  void forceKeyAuthMode(String apiKey);

  AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder);

  String getApiUrl(String workspaceName, Long projectId);

  /**
   * Checks if the passed in user is the user currently being used by Janbanery,
   * the actual check may vary depending on used AuthMode (by apiKey or email etc).
   *
   * @param user user to check if it's "us"
   * @return true if the user is "us", false otherwise
   */
  boolean isCurrentUser(User user);

  // api urls

  String getApiUrl();

  String getApiUrl(String workspaceName);

  String getApiUrl(String workspaceName, String resourcesId, Long id);

  String getApiUrl(String workspaceName, Long projectId, String resourceDotJson);
}
