package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.exceptions.NotYetImplementedException;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSubscription;
import pl.project13.janbanery.resources.Workspace;

/**
 * @author Konrad Malawski
 */
public class SubscriptionsImpl implements Subscriptions {

  private Configuration conf;
  private RestClient    restClient;

  private Workspace currentWorkspace;

  public SubscriptionsImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean exists(Task task) {
    throw new NotYetImplementedException(); // todo
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskSubscription subscribe(Task task) {
    throw new NotYetImplementedException(); // todo
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskSubscription unsubscribe(Task task) {
    throw new NotYetImplementedException(); // todo
  }

  public Subscriptions using(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;
    return this;
  }
}
