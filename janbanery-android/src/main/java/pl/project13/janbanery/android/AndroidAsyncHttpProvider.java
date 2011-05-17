package pl.project13.janbanery.android;

import com.ning.http.client.*;
import com.ning.http.client.providers.jdk.JDKAsyncHttpProviderConfig;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import pl.project13.janbanery.exceptions.ServerCommunicationException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Konrad Malawski
 */
public class AndroidAsyncHttpProvider implements AsyncHttpProvider<HttpURLConnection> {

  private final AsyncHttpClientConfig config;

  private final AtomicBoolean closed = new AtomicBoolean(false);

  private final static int MAX_BUFFERED_BYTES = 8192;

  private final AtomicInteger maxConnections = new AtomicInteger();

  private boolean bufferResponseInMemory = false;

  public AndroidAsyncHttpProvider(AsyncHttpClientConfig config) {
    this.config = config;

    AsyncHttpProviderConfig<?, ?> providerConfig = config.getAsyncHttpProviderConfig();
    if (providerConfig != null && AndroidAsyncHttpProviderConfig.class.isAssignableFrom(providerConfig.getClass())) {
      configure(AndroidAsyncHttpProviderConfig.class.cast(providerConfig));
    }
  }

  private void configure(AndroidAsyncHttpProviderConfig providerConfig) {
    for (Map.Entry<String, String> e : providerConfig.propertiesSet()) {
      System.setProperty(e.getKey(), e.getValue());
    }

    if (providerConfig.getProperty(JDKAsyncHttpProviderConfig.FORCE_RESPONSE_BUFFERING) != null) {
      bufferResponseInMemory = true;
    }
  }

  /**
   * Execute the request and invoke the {@link AsyncHandler} when the response arrive.
   *
   * @param handler an instance of {@link AsyncHandler}
   * @return a {@link ListenableFuture} of Type T.
   * @throws ServerCommunicationException
   */
  @Override
  public <T> ListenableFuture<T> execute(Request request, AsyncHandler<T> handler) throws IOException {
    if (closed.get()) {
      throw new IOException("Closed");
    }

    if (config.getMaxTotalConnections() > -1 && (maxConnections.get() + 1) > config.getMaxTotalConnections()) {
      throw new IOException(String.format("Too many connections %s", config.getMaxTotalConnections()));
    }

    HttpClient httpClient = getHttpClient();

    HttpUriRequest httpUriRequest = translateRequest(request);
    httpClient.execute(httpUriRequest);


    HttpURLConnection urlConnection = createUrlConnection();

    // todo implementation

    throw new UnsupportedOperationException("Not implemented yet");
  }

  private HttpURLConnection createUrlConnection() {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  private HttpUriRequest translateRequest(Request request) {
    String method = request.getMethod();
    String url = request.getUrl();

    HttpUriRequest uriRequest = null;

    if (method.equalsIgnoreCase(Method.GET)) {
      uriRequest = new HttpPost(url);
    } // todo rest of the methods

    return uriRequest;
  }

  /**
   * Prepare a {@link Response}
   *
   * @param status    {@link HttpResponseStatus}
   * @param headers   {@link HttpResponseHeaders}
   * @param bodyParts list of {@link HttpResponseBodyPart}
   * @return a {@link Response}
   */
  @Override
  public Response prepareResponse(HttpResponseStatus status, HttpResponseHeaders headers, Collection<HttpResponseBodyPart> bodyParts) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * Close the current underlying TCP/HTTP connection.
   */
  @Override
  public void close() {
    closed.set(true);
  }

  public DefaultHttpClient getHttpClient() {
    DefaultHttpClient ret;

    //sets up parameters
    HttpParams params = new BasicHttpParams();
    HttpProtocolParams.setVersion(params, new ProtocolVersion("HTTP", 1, 1));
    HttpProtocolParams.setContentCharset(params, "utf-8");
    params.setBooleanParameter("http.protocol.expect-continue", false);

    //registers schemes for both http and https
    SchemeRegistry registry = new SchemeRegistry();
    registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
    sslSocketFactory.setHostnameVerifier(new AllowAllHostnameVerifier());
    registry.register(new Scheme("https", sslSocketFactory, 443));

    ThreadSafeClientConnManager manager = new ThreadSafeClientConnManager(params, registry);
    ret = new DefaultHttpClient(manager, params);
    return ret;
  }

  static class Method {
    static String POST = "POST";
    static String GET = "GET";
    static String DELETE = "DELETE";
    static String PUT = "PUT";
    static String HEAD = "HEAD";

  }
}
