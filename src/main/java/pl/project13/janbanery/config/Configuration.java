package pl.project13.janbanery.config;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public interface Configuration {

  String getApiKey();

  void forceAuthMode(AuthMode authMode);

  AuthMode getAuthMode();

  String getApiUrl();
}
