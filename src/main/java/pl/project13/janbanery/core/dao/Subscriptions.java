package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSubscription;

/**
 * @author Konrad Malawski
 */
public interface Subscriptions {

  Boolean exists(Task task);

  TaskSubscription subscribe(Task task);

  TaskSubscription unsubscribe(Task task);

}
