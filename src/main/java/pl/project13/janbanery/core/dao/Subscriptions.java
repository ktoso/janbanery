package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSubscription;

import java.io.IOException;

/**
 * @author Konrad Malawski
 */
public interface Subscriptions {

  // commands -----------------------------------------------------------------

  TaskSubscription subscribe(Task task) throws IOException;

  void unsubscribe(Task task);

  // queries ------------------------------------------------------------------

  Boolean isSubscribedTo(Task task) throws IOException;

}
