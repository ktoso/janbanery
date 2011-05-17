package pl.project13.janbanery.android.rest.response;

import org.apache.http.HttpResponse;
import pl.project13.janbanery.android.util.Strings;
import pl.project13.janbanery.core.rest.response.RestClientResponse;


import java.io.InputStream;

/**
 * @author Konrad Malawski
 */
public class ResponseFromHttpResponse implements RestClientResponse {

  private HttpResponse response;

  public ResponseFromHttpResponse(HttpResponse response) {
    this.response = response;
  }

  @Override
  public Integer getStatusCode() {
    return response.getStatusLine().getStatusCode();
  }

  @Override
  public String getStatusText() {
    return response.getStatusLine().getReasonPhrase();
  }

  @Override
  public String getResponseBody() {
    InputStream content;

    try {
      content = response.getEntity().getContent();
    } catch (IOException e) {
      return null;
    }

    return Strings.toString(content);
  }
}