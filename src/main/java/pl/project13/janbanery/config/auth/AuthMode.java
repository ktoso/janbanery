package pl.project13.janbanery.config.auth;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.resources.User;

/**
 * Common interface for all methods allowing authorization into the Kanbanery API.
 * For example, via API Key or plain user/password method.
 * //todo rename this interface to something better
 *
 * @author Konrad Malawski
 */
public interface AuthMode {

  AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder);

  String encodeUserPassword(String user, String password);

  /**
   * Identify if the passed in user is the "current" user.
   *
   * @param user user to check if it's "us"
   * @return true if the user is "us", false otherwise
   */
  boolean isCurrentUser(User user);
}
