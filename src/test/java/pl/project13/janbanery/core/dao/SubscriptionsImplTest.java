/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.project13.janbanery.core.dao;

import org.junit.After;
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
    janbanery = new JanbaneryFactory().connectUsing(conf).toWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    TestEntityHelper.deleteTestTask(janbanery);

    janbanery.close();
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
