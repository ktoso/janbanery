package pl.project13.janbanery.core.dao;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.test.TestConstants;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class TaskTypesTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    // dependencies
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    Gson gson = GsonFactory.create();
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(TestConstants.EXISTING_WORKSPACE);
  }

  @Test
  public void shouldFetchAllTaskTypes() throws Exception {
    // given
    TaskTypes taskTypes = janbanery.taskTypes();

    // when
    List<TaskType> allTaskTypes = taskTypes.all();

    // then
    assertThat(allTaskTypes).isNotNull();
    assertThat(allTaskTypes).isNotEmpty();
  }

  @Test
  public void shouldFetchAnyTaskType() throws Exception {
    // given
    TaskTypes taskTypes = janbanery.taskTypes();

    // when
    TaskType taskType = taskTypes.any();

    // then
    assertThat(taskType).isNotNull();
    assertThat(taskType.getName()).isIn("Bug", "Chore", "Story");
  }

}
