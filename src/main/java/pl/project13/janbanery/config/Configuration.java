package pl.project13.janbanery.config;

import com.ning.http.client.AsyncHttpClient;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public interface Configuration {

  String getApiKey();

  AuthMode getAuthMode();

  String getApiUrl();

  void forceUserPassAuthMode(String user, String password);

  void forceKeyAuthMode(String apiKey);

  AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder);
}
