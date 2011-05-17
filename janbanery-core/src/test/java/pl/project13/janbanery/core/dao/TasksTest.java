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
import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyArchiveFromLastColumnException;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestConstants;
import pl.project13.janbanery.test.TestEntityHelper;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.*;

/**
 * @author Konrad Malawski
 */
public class TasksTest {

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
  public void shouldCreateTaskOnBoard() throws Exception {
    // given
    Task bug = new Task.Builder(TASK_TITLE, janbanery.taskTypes().any())
        .description("A task I have created using the Janbanery library")
        .priority(Priority.MEDIUM)
        .build();

    // when
    TaskFlow taskFlow = janbanery.tasks().create(bug);
    Task newTask = taskFlow.get();

    // then, should have created the task
    List<Task> all = janbanery.tasks().all();
    assertThat(all).onProperty("id").contains(newTask.getId());
  }

  @Test
  public void shouldDeleteTask() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    List<Task> beforeDelete = janbanery.tasks().allByTitle(TestConstants.TASK_TITLE);
    Long deletedTaskId = task.getId();

    // when
    janbanery.tasks().delete(task);

    // then
    List<Task> afterDelete = janbanery.tasks().allByTitle(TASK_TITLE);

    assertThat(afterDelete.size()).isEqualTo(beforeDelete.size() - 1);
    assertThat(afterDelete).onProperty("id").excludes(deletedTaskId);
  }

  @Test
  public void shouldFindByTaskId() throws Exception {
    // given
    Task testTask = TestEntityHelper.createTestTask(janbanery);
    Task createdTask = janbanery.tasks().create(testTask).get();
    Long createdTaskId = createdTask.getId();

    // when
    Task foundTask = janbanery.tasks().byId(createdTaskId);

    // then
    assertThat(foundTask.getId()).isEqualTo(createdTaskId);
  }

  @Test
  public void shouldArchiveTaskFromLastColumn() throws Exception {
    // given
    TaskFlow taskFlow = janbanery.tasks().create(TestEntityHelper.createTestTask(janbanery));
    Task taskInLastColumn = taskFlow.move().toLastColumn().get();

    // when
    janbanery.tasks().move(taskInLastColumn).toArchive();

    // then
    Task taskMovedToArchive = janbanery.tasks().byId(taskInLastColumn.getId());

    assertThat(taskMovedToArchive.isArchived()).isTrue();
  }

  @Test(expected = CanOnlyArchiveFromLastColumnException.class)
  public void shouldNotArchiveTaskFromNotLastColumn() throws Exception {
    // given
    TaskFlow taskFlow = janbanery.tasks().create(TestEntityHelper.createTestTask(janbanery));
    Task taskInLastColumn = taskFlow.get();

    // when
    janbanery.tasks().move(taskInLastColumn).toArchive();

    // then, should have thrown
  }

}
