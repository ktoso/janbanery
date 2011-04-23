package pl.project13.janbanery.config;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.config.auth.ApiKeyAuthMode;
import pl.project13.janbanery.config.auth.AuthMode;
import pl.project13.janbanery.config.auth.NoAuthMode;
import pl.project13.janbanery.config.auth.UserPassAuthMode;

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

  @Override
  public AuthMode getAuthMode() {
    return authMode;
  }

  @Override
  public String getApiUrl() {
    return "https://kanbanery.com/api/v1/user/";
  }

}
