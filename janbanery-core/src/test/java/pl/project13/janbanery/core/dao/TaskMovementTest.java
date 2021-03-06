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
import org.junit.Ignore;
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
    janbanery = new JanbaneryFactory().connectUsing(conf).toWorkspace(EXISTING_WORKSPACE);
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
    TaskMoveFlow moveOne = taskFlow.move();
    Task prev = moveOne.get();

    // when
    Task after = moveOne.toNextColumn().get();

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
    TaskMoveFlow moveOne = taskFlow.move();
    Task prev = moveOne.get();

    // when
    moveOne.toPreviousColumn();

    // then, should have thrown
  }

  @Test(expected = TaskAlreadyInLastColumnException.class)
  public void shouldThrowWhenForcedToMoveRightWhenOnLastColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);
    taskFlow.move().to(janbanery.columns().last());

    // when
    for (int i = 0; i < 10; i++) {
      taskFlow.move().toNextColumn(); // fail here
    }

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
  public void shouldMoveToPreviousColumnFromLastColumn() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);
    Column lastColumn = janbanery.columns().last();
    Column beforeLastColumn = janbanery.columns().before(lastColumn);

    Task taskInLastColumn = taskFlow.move().toLastColumn().get();

    // when
    Task taskMovedToPreLastColumn = janbanery.tasks()
                                             .move(taskInLastColumn)
                                             .toPreviousColumn()
                                             .get();

    // then
    assertThat(taskMovedToPreLastColumn.getColumnId()).isEqualTo(beforeLastColumn.getId());
  }

  @Test
  public void shouldMoveToIceBox() throws Exception {
    // given
    TaskFlow taskFlow = TestEntityHelper.createTestTaskFlow(janbanery);

    // when
    TaskMoveFlow taskMoveOneFlow = taskFlow.move().toIceBox().move();

    // then
    Task taskInIceBox = taskMoveOneFlow.get();
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

  @Ignore("Test needs to be fixed")
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
