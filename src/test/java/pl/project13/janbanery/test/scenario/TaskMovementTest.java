package pl.project13.janbanery.test.scenario;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.dao.Tasks;
import pl.project13.janbanery.core.dao.TasksImpl;
import pl.project13.janbanery.resources.Workspace;

import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;

/**
 * @author Konrad Malawski
 */
public class TaskMovementTest {

  Tasks     tasks;
  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    Gson gson = GsonFactory.create();
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    tasks = new TasksImpl(gson, asyncHttpClient);

    janbanery = new JanbaneryFactory().connectUsing(new PropertiesConfiguration());
  }

  @Test
  public void shouldMoveTaskToNextColumn() throws Exception {
    Workspace workspace = janbanery.usingWorkspace(EXISTING_WORKSPACE);

    janbanery.tasks().
  }
}
