package pl.project13.janbanery.config.auth;

import com.ning.http.client.AsyncHttpClient;
import sun.misc.BASE64Encoder;

/**
 * @author Konrad Malawski
 */
public class UserPassAuthMode implements AuthMode {

  private String authData;

  public UserPassAuthMode(String authData) {
    this.authData = authData;
  }

  /**
   * Creates an instance of UserPassAuthMode using the given username and password,
   * these will be encrypted right away using an BaseEncoder.
   *
   * @param user     username to be used in this plain auth method
   * @param password password for this user
   */
  public UserPassAuthMode(String user, String password) {
    this.authData = encodeUserPassword(user, password);
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    return requestBuilder.addHeader("Authorization", "Basic " + authData);
  }

  public String encodeUserPassword(String user, String password) {
    byte[] logon = String.format("%s:%s", user, password).getBytes();
    return new BASE64Encoder().encode(logon);
  }
}
