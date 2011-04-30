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
import pl.project13.janbanery.core.flow.SubTasksFlow;
import pl.project13.janbanery.resources.SubTask;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestEntityHelper;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class SubTasksTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    TestEntityHelper.deleteTestTask(janbanery);
  }

  @Test
  public void shouldCreateSubTask() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    SubTasksFlow subTasksFlow = janbanery.subTasks().of(task);

    // when
    SubTask createdSubTask = subTasksFlow.create(TestEntityHelper.createTestSubTask()).get();

    // then
    List<SubTask> allSubtasksOfTask = subTasksFlow.all();

    assertThat(allSubtasksOfTask).contains(createdSubTask);
  }

  @Test
  public void shouldDeleteSubTask() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    SubTasksFlow subTasksFlow = janbanery.subTasks().of(task);

    // when
    SubTask createdSubTask = subTasksFlow.create(TestEntityHelper.createTestSubTask()).get();
    subTasksFlow.delete(createdSubTask);

    // then
    List<SubTask> allSubtasksOfTask = subTasksFlow.all();

    assertThat(allSubtasksOfTask).excludes(createdSubTask);
  }

  @Test
  public void shouldMarkTaskAsCompleted() throws Exception {
    // given
    Task task = TestEntityHelper.createTestTaskFlow(janbanery).get();
    SubTasksFlow subTasksFlow = janbanery.subTasks().of(task);

    // when
    SubTask createdSubTask = subTasksFlow.create(TestEntityHelper.createTestSubTask()).get();
    List<SubTask> allSubtasksOfTask = subTasksFlow.all();

    assertThat(allSubtasksOfTask).excludes(createdSubTask);
  }

}
