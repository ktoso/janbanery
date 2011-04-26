package pl.project13.janbanery.core.dao;

import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestConstants;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class TasksTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
  }

  @Test
  public void shouldCreateTaskOnBoard() throws Exception {
    // given
    janbanery.usingWorkspace(TestConstants.EXISTING_WORKSPACE);

    Task bug = new Task.Builder(TestConstants.TASK_TITLE)
        .taskType(janbanery.taskTypes().any())
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
    janbanery.usingWorkspace(TestConstants.EXISTING_WORKSPACE);
    List<Task> tasks = janbanery.tasks().byTitle(TestConstants.TASK_TITLE);

    // when
    janbanery.tasks().move()

    // then

  }
}
