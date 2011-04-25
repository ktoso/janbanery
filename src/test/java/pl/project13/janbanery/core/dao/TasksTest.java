package pl.project13.janbanery.core.dao;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.resources.Priority;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.test.TestConstants;

import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class TasksTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    // dependencies
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    Gson gson = GsonFactory.create();
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    janbanery = new JanbaneryFactory().connectUsing(conf);
  }

  @Test
  public void shouldCreateTaskOnBoard() throws Exception {
    // given
    janbanery.usingWorkspace(TestConstants.EXISTING_WORKSPACE);

    Task bug = new Task.Builder("JanbaneryTask")
        .taskType(janbanery.taskTypes().any())
        .description("A task I have created using the Janbanery library")
        .priority(Priority.MEDIUM)
        .build();

    // when
    janbanery.tasks().create(bug);

    // then, should have created the task
    // todo add assertions
  }

}
