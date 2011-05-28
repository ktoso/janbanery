package pl.project13.janbanery.android.rest;

import android.util.Log;
import com.google.common.base.Charsets;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.methods.*;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import pl.project13.janbanery.android.rest.response.ResponseFromHttpResponse;
import pl.project13.janbanery.android.util.Strings;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.auth.Header;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.core.rest.response.RestClientResponse;
import pl.project13.janbanery.encoders.FormUrlEncodedBodyGenerator;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

/**
 * @author Konrad Malawski
 */
@SuppressWarnings({"unchecked"})
public class AndroidCompatibleRestClient extends RestClient {

  public final String TAG = "AndroidRestClient";

  private Configuration conf;
  private Gson gson;
  private DefaultHttpClient httpClient;
  private FormUrlEncodedBodyGenerator encodedBodyGenerator;

  public AndroidCompatibleRestClient() {
    this.httpClient = getClient();
  }

  @Override
  public void init(Configuration configuration, Gson gson, FormUrlEncodedBodyGenerator bodyGenerator) {
    this.conf = configuration;
    this.gson = gson;
    this.encodedBodyGenerator = bodyGenerator;
  }

  public DefaultHttpClient getClient() {
    DefaultHttpClient ret;

    //sets up parameters
    HttpParams params = new BasicHttpParams();
    HttpProtocolParams.setVersion(params, new ProtocolVersion("HTTP", 1, 1));
    HttpProtocolParams.setContentCharset(params, "UTF-8");
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

  private void authorize(HttpRequestBase request) {
    Header authHeader = conf.getAuthProvider().getAuthHeader();
    request.addHeader(authHeader.getKey(), authHeader.getValue());
  }

  @Override
  public RestClientResponse doPost(String url, KanbaneryResource resource) throws ServerCommunicationException {
    HttpPost request = new HttpPost(url);

    authorize(request);

    String requestBody = encodedBodyGenerator.asString(resource);
    Log.i(TAG, "Generated request body is: '" + requestBody + "'");
    setFormUrlEncodedBody(request, requestBody);

    HttpResponse httpResponse = executeRequest(request);

    ResponseFromHttpResponse response = new ResponseFromHttpResponse(httpResponse);

    verifyResponseCode(response);

    return response;
  }

  @Override
  public <T> T doPost(String url, KanbaneryResource resource, Class<?> type) throws ServerCommunicationException {
    RestClientResponse response = doPost(url, resource);

    return (T) gson.fromJson(response.getResponseBody(), type);
  }

  @Override
  public RestClientResponse doGet(String url) throws ServerCommunicationException {
    HttpGet request = new HttpGet(url);

    authorize(request);

    HttpResponse httpResponse;
    httpResponse = executeRequest(request);
    ResponseFromHttpResponse response = new ResponseFromHttpResponse(httpResponse);

    verifyResponseCode(response);

    return response;
  }

  @Override
  public <T> T doGet(String url, Type type) throws ServerCommunicationException {
    RestClientResponse response = doGet(url);

    return (T) gson.fromJson(response.getResponseBody(), type);
  }

  @Override
  public RestClientResponse doDelete(String url) throws ServerCommunicationException {
    HttpGet request = new HttpGet(url);

    authorize(request);

    HttpResponse httpResponse = executeRequest(request);
    ResponseFromHttpResponse response = new ResponseFromHttpResponse(httpResponse);

    verifyResponseCode(response);

    return response;
  }

  @Override
  public RestClientResponse doPut(String url, String requestBody) throws ServerCommunicationException {
    HttpPut request = new HttpPut(url);

    authorize(request);

    System.out.println(requestBody);
    Log.i(TAG, "Generated request body is: '" + requestBody + "'");
    setFormUrlEncodedBody(request, requestBody);

    HttpResponse httpResponse = executeRequest(request);
    ResponseFromHttpResponse response = new ResponseFromHttpResponse(httpResponse);

    verifyResponseCode(response);

    return response;
  }

  @Override
  public <T> T doPut(String url, String requestBody, Class<?> type) throws ServerCommunicationException {
    RestClientResponse response = doPut(url, requestBody);

    return (T) gson.fromJson(response.getResponseBody(), type);
  }

  @Override
  public <T> T doPut(String url, String requestBody, Type type) throws ServerCommunicationException {
    RestClientResponse response = doPut(url, requestBody);

    return (T) gson.fromJson(response.getResponseBody(), type);
  }

  @Override
  public <T> T doPut(String url, KanbaneryResource resource, Class<?> type) throws ServerCommunicationException {
    RestClientResponse response = doPut(url, encodedBodyGenerator.asString(resource));

    return (T) gson.fromJson(response.getResponseBody(), type);
  }

  private void setFormUrlEncodedBody(HttpEntityEnclosingRequestBase request, String requestBody) {
    BasicHttpEntity entity = new BasicHttpEntity();

    try {
      byte[] contentBytes = requestBody.getBytes(Charsets.UTF_8.name());

      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(contentBytes);
      System.out.println(">>>>>>>" + Strings.toString(byteArrayInputStream));

      entity.setContent(new ByteArrayInputStream(contentBytes));
      entity.setContentLength(contentBytes.length);
      entity.setContentEncoding(Charsets.UTF_8.name());
      entity.setChunked(false);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Unable to set request body.", e);
    }

    request.setEntity(entity);
  }

  private HttpResponse executeRequest(HttpRequestBase request) {
    request.setHeader("Content-Type", "application/x-www-form-urlencoded");

    HttpResponse httpResponse;
    try {
      httpResponse = httpClient.execute(request);
    } catch (IOException e) {
      throw new ServerCommunicationException(e);
    }
    return httpResponse;
  }

  @Override
  public void close() {
    httpClient.clearRequestInterceptors();
    httpClient.clearResponseInterceptors();
  }

}
