package pl.project13.janbanery.config.auth;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.resources.User;
import sun.misc.BASE64Encoder;

/**
 * Just a stub class used for in between when there is no auth method setup yet.
 * Will most probably exist only for a few seconds to be replaced with a real AuthMode implementation.
 *
 * @author Konrad Malawski
 */
public class NoAuthMode implements AuthMode {
  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    throw new UnsupportedOperationException("No authorization method was setup. Can not connect to kanbanery without using any auth method.");
  }

  public String encodeUserPassword(String user, String password) {
    byte[] logon = String.format("%s:%s", user, password).getBytes();
    return new BASE64Encoder().encode(logon);
  }

  @Override
  public boolean isCurrentUser(User user) {
    return false;
  }
}
