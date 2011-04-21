package pl.project13.janbanery.config;

import static pl.project13.janbanery.config.AuthMode.API_KEY_MODE;
import static pl.project13.janbanery.config.AuthMode.USER_AND_PASS_MODE;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class DefaultConfiguration implements Configuration {

  private String   apiKey   = "";
  private AuthMode authMode = USER_AND_PASS_MODE;

  public DefaultConfiguration(String apiKey) {
    this.apiKey = apiKey;
  }

  @Override
  public String getApiKey() {
    return apiKey;
  }

  @Override
  public void forceAuthMode(AuthMode authMode) {
    if (isChangeToApiKeyMode(authMode)) {
      // todo check for api key etc
      this.authMode = authMode;
    } else if (isChangeToUserAndPassMode(authMode)) {
      // todo check username is set etc
      this.authMode = authMode;
    }
  }

  @Override
  public AuthMode getAuthMode() {
    return authMode;
  }

  @Override
  public String getApiUrl() {
    return "https://kanbanery.com/api/v1/user/";
  }

  private boolean isChangeToUserAndPassMode(AuthMode newMode) {
    return authMode == API_KEY_MODE && newMode == USER_AND_PASS_MODE;
  }

  private boolean isChangeToApiKeyMode(AuthMode newMode) {
    return authMode == USER_AND_PASS_MODE && newMode == API_KEY_MODE;
  }
}
