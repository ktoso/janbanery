package pl.project13.janbanery.core;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.config.AuthMode;
import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.resources.Task;

import java.util.List;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class JanbaneryImpl implements Tasks {

  private Configuration   conf;
  private AsyncHttpClient asyncHttpClient;

  public JanbaneryImpl(Configuration conf) {
    this.conf = conf;
    this.asyncHttpClient = new AsyncHttpClient();
  }

  public JanbaneryImpl(Configuration conf, AsyncHttpClient asyncHttpClient) {
    this.conf = conf;
    this.asyncHttpClient = asyncHttpClient;
  }

  @Override
  public List<Task> findAll() {
    return null;
  }

  public AuthMode getAuthMode() {
    return conf.getAuthMode();
  }
}
