package pl.project13.janbanery.config.auth;

import com.ning.http.client.AsyncHttpClient;

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
}
