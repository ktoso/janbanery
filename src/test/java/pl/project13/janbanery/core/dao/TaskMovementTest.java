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
import pl.project13.janbanery.exceptions.kanbanery.TaskAlreadyInFirstColumnException;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.*;

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
    Task task = janbanery.tasks().byTitle(TASK_TITLE).get(0);
    janbanery.tasks().delete(task);
  }

  @Test
  public void shouldMoveTaskToNextColumn() throws Exception {
    // given
    TaskFlow taskFlow = createSampleTask();
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
    TaskFlow taskFlow = createSampleTask();
    TaskMoveFlow move = taskFlow.move();
    Task prev = move.get();

    // when
    Task after = move.toNextColumn()
                     .toPreviousColumn()
                     .get();

    // then
    assertThat(after).isNotEqualTo(prev); // it has changed (moved_at etc)
    assertThat(after.getColumnId()).isEqualTo(prev.getColumnId()); // it's the same column
  }

  @Test(expected = TaskAlreadyInFirstColumnException.class)
  public void shouldThrowWhenForcedToMoveLeftWhenOnFirstColumn() throws Exception {
    // given
    TaskFlow taskFlow = createSampleTask();
    TaskMoveFlow move = taskFlow.move();
    Task prev = move.get();


    // when
    move.toPreviousColumn();

    // then, should have thrown
  }

  @Ignore("Kanbanery fails in such situations with an 500 Internal Server Error, it's a bug on their end IMO")
  @Test(expected = TaskAlreadyInFirstColumnException.class)
  public void shouldThrowWhenForcedToMoveRightWhenOnLastColumn() throws Exception {
    // given
    TaskFlow taskFlow = createSampleTask();
    TaskMoveFlow move = taskFlow.move();
    Task prev = move.get();

    // when
    move.toNextColumn();
    move.toNextColumn();
    move.toNextColumn();
    move.toNextColumn(); //fail here
    move.toNextColumn();
    move.toNextColumn();
    // hopefully already "outside of range" already ;-)

    // then, should have thrown
  }

  private TaskFlow createSampleTask() throws IOException, ExecutionException, InterruptedException {
    Task story = new Task.Builder(TASK_TITLE)
        .taskType(janbanery.taskTypes().any())
        .description("A task I have created using the Janbanery library")
        .priority(Priority.MEDIUM)
        .build();

    return janbanery.tasks().create(story);
  }
}
