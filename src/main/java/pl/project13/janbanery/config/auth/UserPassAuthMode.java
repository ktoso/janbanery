package pl.project13.janbanery.config.auth;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.resources.User;
import sun.misc.BASE64Encoder;

/**
 * @author Konrad Malawski
 */
public class UserPassAuthMode implements AuthMode {

  private String userEmail;
  private String authData;

  /**
   * Creates an instance of UserPassAuthMode using the given username and password,
   * these will be encrypted right away using an BaseEncoder.
   *
   * @param userEmail     username to be used in this plain auth method
   * @param password password for this userEmail
   */
  public UserPassAuthMode(String userEmail, String password) {
    this.userEmail = userEmail;
    this.authData = encodeUserPassword(userEmail, password);
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    return requestBuilder.addHeader("Authorization", "Basic " + authData);
  }

  public String encodeUserPassword(String user, String password) {
    byte[] logon = String.format("%s:%s", user, password).getBytes();
    return new BASE64Encoder().encode(logon);
  }

  @Override
  public boolean isCurrentUser(User user) {
    return userEmail.equals(user.getEmail());
  }
}
