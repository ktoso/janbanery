package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.exceptions.NotFoundKanbaneryException;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSubscription;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;

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
  public Boolean isSubscribedTo(Task task) throws IOException {
    String url = getDefaultUrl(task);
    TaskSubscription subscription;

    try {
      subscription = restClient.doGet(url, GsonTypeTokens.TASK_SUBSCRIPTION);
    } catch (NotFoundKanbaneryException e) {
      // that's ok, the resource didn't exist so we're "not subscribed"
      return false;
    }

    return subscription.getTaskId() != null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TaskSubscription subscribe(Task task) throws IOException {
    String url = getDefaultUrl(task);
    TaskSubscription commandObject = new TaskSubscription(); // empty object is fine, all data is contained in URL

    return restClient.doPost(url, commandObject, GsonTypeTokens.TASK_SUBSCRIPTION);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unsubscribe(Task task) {
    String url = getDefaultUrl(task);

    restClient.doDelete(url);
  }

  private String getDefaultUrl(Task task) {
    return conf.getApiUrl(currentWorkspace.getName()) + "tasks/" + task.getId() + "/subscription.json";
  }

  public Subscriptions using(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;
    return this;
  }
}
