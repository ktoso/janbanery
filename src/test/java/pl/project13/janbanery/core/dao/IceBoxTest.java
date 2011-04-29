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
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestEntityHelper;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.*;

/**
 * @author Konrad Malawski
 */
public class IceBoxTest {

  Janbanery janbanery;
  IceBox    iceBox;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);

    iceBox = janbanery.iceBox();
  }

  @After
  public void tearDown() throws Exception {
    TestEntityHelper.deleteTestTask(janbanery);
  }

  @Test
  public void shouldMoveToBoard() throws Exception {
    // given
    Task story = TestEntityHelper.createTestTask(janbanery);

    // when
    Task movedTask = iceBox.create(story).moveToBoard().get();

    // then
    Column firstColumn = janbanery.columns().first();
    List<Task> tasksInFirstColumn = janbanery.tasks().allIn(firstColumn);

    assertThat(tasksInFirstColumn).contains(movedTask);
  }

  @Test
  public void shouldCreateAndDelete() throws Exception {
    // given
    Task story = new Task.Builder(TASK_TITLE)
        .taskType(janbanery.taskTypes().any())
        .description("A task I have created using the Janbanery library")
        .priority(Priority.LOW)
        .build();

    // when
    Task task = iceBox.create(story).get();
    iceBox.delete(task);

    // then
    assertThat(janbanery.tasks().all()).excludes(task);
  }
}
