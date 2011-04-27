package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.exceptions.NotYetImplementedException;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSubscription;

/**
 * @author Konrad Malawski
 */
public class SubscriptionsImpl implements Subscriptions {

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
}
