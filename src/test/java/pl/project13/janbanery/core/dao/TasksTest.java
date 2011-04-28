package pl.project13.janbanery.core.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.flow.TaskFlow;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
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
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    TestEntityHelper.deleteTestTask(janbanery);
  }

  @Test
  public void shouldCreateTaskOnBoard() throws Exception {
    // given
    Task bug = new Task.Builder(TASK_TITLE)
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
    List<Task> beforeDelete = janbanery.tasks().byTitle(TASK_TITLE);
    Task task = beforeDelete.get(0);
    Long deletedTaskId = task.getId();

    // when
    janbanery.tasks().delete(task);

    // then
    List<Task> afterDelete = janbanery.tasks().byTitle(TASK_TITLE);

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
    janbanery.tasks().archive(taskInLastColumn); // todo maybe not void?

    // then
    Task taskMovedToArchive = janbanery.tasks().byId(taskInLastColumn.getId());

    assertThat(taskMovedToArchive.getColumnId()).isNull(); // archive has no column
  }

  @Test
  public void shouldNotArchiveTaskFromNotLastColumn() throws Exception {

  }

}
