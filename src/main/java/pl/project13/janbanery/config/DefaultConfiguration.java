package pl.project13.janbanery.config;

import com.ning.http.client.AsyncHttpClient;
import sun.misc.BASE64Encoder;

import static pl.project13.janbanery.config.AuthMode.API_KEY_MODE;
import static pl.project13.janbanery.config.AuthMode.USER_AND_PASS_MODE;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class DefaultConfiguration implements Configuration {

  private AuthMode authMode         = API_KEY_MODE;
  private String   API_TOKEN_HEADER = "X-Kanbanery-ApiToken";
  private String   apiKey           = "";
  private String   encodedLogon     = "";

  public DefaultConfiguration(String apiKey) {
    forceKeyAuthMode(apiKey);
  }

  public DefaultConfiguration(String user, String password) {
    forceUserPassAuthMode(user, password);
  }

  public DefaultConfiguration() {
  }

  @Override
  public void forceUserPassAuthMode(String user, String password) {
    authMode = USER_AND_PASS_MODE;
    this.encodedLogon = encodeUserPassword(user, password);
  }

  @Override
  public void forceKeyAuthMode(String apiKey) {
    authMode = API_KEY_MODE;
    this.apiKey = apiKey;
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    // todo could be refactored into this enum hmhm
    switch (authMode) {
      case USER_AND_PASS_MODE:
        return requestBuilder.addHeader("Authorization", "Basic " + encodedLogon);
      case API_KEY_MODE:
        return requestBuilder.addHeader(API_TOKEN_HEADER, getApiKey());
      default:
        throw new UnsupportedOperationException("Could not authorize request, unknown mode: '" + authMode + "'");
    }
  }

  @Override
  public String getApiKey() {
    return apiKey;
  }

  @Override
  public AuthMode getAuthMode() {
    return authMode;
  }

  @Override
  public String getApiUrl() {
    return "https://kanbanery.com/api/v1/user/";
  }

  private String encodeUserPassword(String user, String password) {
    byte[] logon = String.format("%s:%s", user, password).getBytes();
    return new BASE64Encoder().encode(logon);
  }

}
