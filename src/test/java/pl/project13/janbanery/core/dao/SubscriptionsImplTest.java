package pl.project13.janbanery.core.dao;

import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskSubscription;
import pl.project13.janbanery.test.TestEntityHelper;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.Fail.fail;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class SubscriptionsImplTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);
  }

  @Test
  public void shouldSubscribeToTask() throws Exception {
    // given
    Subscriptions subscriptions = janbanery.subscriptions();
    Task testTask = TestEntityHelper.createTestTask(janbanery);
    Task task = janbanery.tasks().create(testTask).get();

    // when
    TaskSubscription taskSubscription = subscriptions.subscribe(task);

    // then
    Boolean isSubscribed = subscriptions.isSubscribedTo(task);

    assertThat(isSubscribed).isTrue();
  }

  @Test
  public void shouldUnsubscribeFromTask() throws Exception {
    // given
    Subscriptions subscriptions = janbanery.subscriptions();
    Task testTask = TestEntityHelper.createTestTask(janbanery);
    Task task = janbanery.tasks().create(testTask).get();

    // when
    subscriptions.subscribe(task);
    subscriptions.unsubscribe(task);

    // then
    Boolean isSubscribed = subscriptions.isSubscribedTo(task);

    assertThat(isSubscribed).isFalse();
  }
}
