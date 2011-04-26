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
    List<Task> tasks = janbanery.tasks().byTitle(TASK_TITLE);
    if (tasks.size() > 0) {
      Task task = tasks.get(0);
      janbanery.tasks().delete(task);
    }
  }

  @Test
  public void shouldMoveToBoard() throws Exception {
    // given
    Task story = new Task.Builder(TASK_TITLE)
        .taskType(janbanery.taskTypes().any())
        .description("A task I have created using the Janbanery library")
        .priority(Priority.LOW)
        .build();

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
    Task movedTask = iceBox.create(story).;

    // then
    Column firstColumn = janbanery.columns().first();
    List<Task> tasksInFirstColumn = janbanery.tasks().allIn(firstColumn);

    assertThat(tasksInFirstColumn).contains(movedTask);
  }
}
