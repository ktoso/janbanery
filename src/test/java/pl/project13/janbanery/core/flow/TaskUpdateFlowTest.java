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

package pl.project13.janbanery.core.flow;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.resources.*;
import pl.project13.janbanery.test.TestEntityHelper;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class TaskUpdateFlowTest {

  private static Janbanery janbanery;
  private static Tasks     tasks;
  private static Task      task;

  @BeforeClass
  public static void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);

    tasks = janbanery.tasks();
    task = TestEntityHelper.createTestTaskFlow(janbanery).get();
  }

  @AfterClass
  public static void tearDown() throws IOException {
    TestEntityHelper.deleteTestTask(janbanery);

    janbanery.close();
  }

  @Test
  public void testTitle() throws Exception {
    // given
    String newValue = "New Title";

    // when
    Task updatedTask = tasks.update(task).title(newValue).get();

    // then
    Task foundTask = tasks.byId(updatedTask.getId());

    assertThat(updatedTask).isEqualTo(foundTask);
    assertThat(foundTask.getTitle()).isEqualTo(newValue);
  }

  @Test
  @SuppressWarnings({"deprecation"})
  public void testDeadline() throws Exception {
    // given
    Date newValue = new DateTime().plusDays(2).toDate();

    // when
    Task updatedTask = tasks.update(task).deadline(newValue).get();

    // then
    Task foundTask = tasks.byId(updatedTask.getId());

    assertThat(updatedTask).isEqualTo(foundTask);
    assertThat(foundTask.getDeadline().getYear()).isEqualTo(newValue.getYear());
    assertThat(foundTask.getDeadline().getMonth()).isEqualTo(newValue.getMonth());
    assertThat(foundTask.getDeadline().getDay()).isEqualTo(newValue.getDay());
  }

  @Test
  public void testDescription() throws Exception {
    // given
    String newValue = "New Description";

    // when
    Task updatedTask = tasks.update(task).description(newValue).get();

    // then
    Task foundTask = tasks.byId(updatedTask.getId());

    assertThat(updatedTask).isEqualTo(foundTask);
    assertThat(foundTask.getDescription()).isEqualTo(newValue);
  }

  @Test
  public void testAssignTo() throws Exception {
    // given
    User currentUser = janbanery.users().current();

    // when
    Task updatedTask = tasks.update(task).assignTo(currentUser).get();

    // then
    Task foundTask = tasks.byId(updatedTask.getId());

    assertThat(updatedTask).isEqualTo(foundTask);
    assertThat(foundTask.getOwnerId()).isEqualTo(currentUser.getId());
  }

  @Test
  public void testPosition() throws Exception {
    // given
    Integer newValue = 1;

    // when
    Task updatedTask = tasks.update(task).position(newValue).get();

    // then
    Task foundTask = tasks.byId(updatedTask.getId());

    assertThat(updatedTask).isEqualTo(foundTask);
    assertThat(foundTask.getPosition()).isEqualTo(newValue);
  }

  @Test
  public void testPriority() throws Exception {
    // given
    Priority newValue = Priority.LOW;

    // when
    Task updatedTask = tasks.update(task).priority(newValue).get();

    // then
    Task foundTask = tasks.byId(updatedTask.getId());

    assertThat(updatedTask).isEqualTo(foundTask);
    assertThat(foundTask.getPriority()).isEqualTo(newValue);
  }

  @Test
  public void testType() throws Exception {
    // given
    List<TaskType> taskTypes = janbanery.taskTypes().all();
    Collections.shuffle(taskTypes);
    TaskType newValue = taskTypes.get(0);

    // when
    Task updatedTask = tasks.update(task).type(newValue).get();

    // then
    Task foundTask = tasks.byId(updatedTask.getId());

    assertThat(updatedTask).isEqualTo(foundTask);
    assertThat(foundTask.getTaskTypeId()).isEqualTo(newValue.getId());
  }

  @Test
  public void testEstimate() throws Exception {
    // given
    List<Estimate> estimates = janbanery.estimates().all();
    Collections.shuffle(estimates);
    Estimate newValue = estimates.get(0);

    // when
    Task updatedTask = tasks.update(task).estimate(newValue).get();

    // then
    Task foundTask = tasks.byId(updatedTask.getId());

    assertThat(updatedTask).isEqualTo(foundTask);
    assertThat(foundTask.getEstimateId()).isEqualTo(newValue.getId());
  }
}
