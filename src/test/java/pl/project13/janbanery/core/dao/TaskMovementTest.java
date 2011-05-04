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
import pl.project13.janbanery.core.flow.TaskMoveFlow;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyArchiveFromLastColumnException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.CanOnlyIceBoxTaskFromFirstColumnException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.TaskAlreadyInFirstColumnException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.TaskAlreadyInLastColumnException;
import pl.project13.janbanery.resources.Column;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestEntityHelper;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class TaskMovementTest {

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

    janbanery.close();
  }

  @Test
  public void shouldMoveTaskToNextColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);
    TaskMoveFlow move = taskFlow.move();
    Task prev = move.get();

    // when
    Task after = move.toNextColumn().get();

    // then
    assertThat(after).isNotEqualTo(prev); // it has changed (moved_at etc)
    assertThat(after.getColumnId()).isNotEqualTo(prev.getColumnId()); // it moved
  }

  @Test
  public void shouldMoveTaskNextAndPrevToRemainInSameColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);
    Task prev = taskFlow.move().get();

    // when
    Task after = taskFlow.move().toNextColumn()
                         .move().toPreviousColumn()
                         .get();

    // then
    assertThat(after).isNotEqualTo(prev); // it has changed (moved_at etc)
    assertThat(after.getColumnId()).isEqualTo(prev.getColumnId()); // it's the same column
  }

  @Test(expected = TaskAlreadyInFirstColumnException.class)
  public void shouldThrowWhenForcedToMoveLeftWhenOnFirstColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);
    TaskMoveFlow move = taskFlow.move();
    Task prev = move.get();

    // when
    move.toPreviousColumn();

    // then, should have thrown
  }

  @Test(expected = TaskAlreadyInLastColumnException.class)
  public void shouldThrowWhenForcedToMoveRightWhenOnLastColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);
    TaskMoveFlow move = taskFlow.move();
    Task prev = move.get();

    // when
    move.toNextColumn();
    move.toNextColumn();
    move.toNextColumn();
    move.toNextColumn(); //fail here
    move.toNextColumn();
    move.toNextColumn();

    // then, should have thrown
  }

  @Test
  public void shouldMoveToLastColumn() throws Exception {
    // given
    TaskFlow jumpFlow = TestEntityHelper.createTestTaskFlow(janbanery);
    TaskFlow manualFlow = TestEntityHelper.createTestTaskFlow(janbanery);

    // when
    jumpFlow = jumpFlow.move().toLastColumn();

    try {
      //noinspection InfiniteLoopStatement
      while (true) {
        manualFlow = manualFlow.move().toNextColumn();
      }
    } catch (TaskAlreadyInLastColumnException ignore) {
      // that's ok
    }

    // then
    Long jumpedToColumnId = jumpFlow.get().getColumnId();
    Long manuallyMovedToColumnId = manualFlow.get().getColumnId();

    assertThat(jumpedToColumnId).isEqualTo(manuallyMovedToColumnId);

    Column jumpedToColumn = janbanery.columns().byId(jumpedToColumnId);
    assertThat(jumpedToColumn).isEqualTo(janbanery.columns().last());
  }

  @Test
  public void shouldMoveToIceBox() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);

    // when
    TaskMoveFlow taskMoveFlow = taskFlow.move().toIceBox().move();

    // then
    Task taskInIceBox = taskMoveFlow.get();
    List<Task> tasksInIceBox = janbanery.iceBox().all();

    assertThat(tasksInIceBox).contains(taskInIceBox);
  }

  @Test(expected = CanOnlyIceBoxTaskFromFirstColumnException.class)
  public void shouldNotMoveToIceBoxFromNotFirstColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);

    // when
    taskFlow.move().toNextColumn()
            .move().toIceBox();

    // then, should have thrown
  }

  @Test
  public void shouldArchiveProperlyFromLastColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);

    // when
    taskFlow = taskFlow.move().toLastColumn()
                       .move().toArchive();

    // then
    Task task = taskFlow.get();
    assertThat(task.isArchived()).isTrue();
  }

  @Test(expected = CanOnlyArchiveFromLastColumnException.class)
  public void shouldNotArchiveIfNotInLastColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);

    // when
    taskFlow.move().toArchive();

    // then, should have thrown
  }
}
