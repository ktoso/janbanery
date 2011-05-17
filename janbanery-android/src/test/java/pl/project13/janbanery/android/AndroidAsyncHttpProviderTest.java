package pl.project13.janbanery.android;

import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.ListenableFuture;
import com.ning.http.client.Response;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski <konrad.malawski@project13.pl>
 */
public class AndroidAsyncHttpProviderTest {


  private AsyncHttpClient asyncHttpClient;

  @Before
  public void setUp() throws Exception {
    AsyncHttpClientConfig config = new AsyncHttpClientConfig.Builder().build();
    AndroidAsyncHttpProvider provider = new AndroidAsyncHttpProvider(config);
    asyncHttpClient = new AsyncHttpClient(provider);
  }

  @Test
  public void shouldGet() throws Exception {
    // given
    AsyncHttpClient.BoundRequestBuilder preparedGet = asyncHttpClient.prepareGet("http://google.com");

    // when
    ListenableFuture<Response> listenableFuture = preparedGet.execute();

    // then
    Response response = listenableFuture.get();
    assertThat(response.getStatusCode()).isEqualTo(200);
  }
}
