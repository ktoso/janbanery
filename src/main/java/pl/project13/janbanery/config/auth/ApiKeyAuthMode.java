package pl.project13.janbanery.config.auth;

import com.ning.http.client.AsyncHttpClient;
import sun.misc.BASE64Encoder;

/**
 * The safe and best method to use for API calls
 *
 * @author Konrad Malawski
 */
public class ApiKeyAuthMode implements AuthMode {

  public final String API_TOKEN_HEADER = "X-Kanbanery-ApiToken";

  private final String authData;

  public ApiKeyAuthMode(String authData) {
    this.authData = authData;
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    return requestBuilder.addHeader(API_TOKEN_HEADER, authData);
  }

  public String encodeUserPassword(String user, String password) {
    byte[] logon = String.format("%s:%s", user, password).getBytes();
    return new BASE64Encoder().encode(logon);
  }
}
