package pl.project13.janbanery.core.dao;

import com.google.gson.Gson;
import com.ning.http.client.AsyncHttpClient;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.core.RestClient;
import pl.project13.janbanery.encoders.ReflectionsBodyGenerator;
import pl.project13.janbanery.resources.Workspace;
import pl.project13.janbanery.test.TestConstants;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class WorkspacesImplTest {

  Workspaces workspaces;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(TestConstants.VALID_CONF_FILE_LOCATION);

    Gson gson = GsonFactory.create();
    AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    ReflectionsBodyGenerator encodedBodyGenerator = new ReflectionsBodyGenerator();
    RestClient restClient = new RestClient(conf, gson, asyncHttpClient, encodedBodyGenerator);

    workspaces = new WorkspacesImpl(conf, restClient);
  }

  @Test
  public void shouldFindAll() throws Exception {
    // when
    List<Workspace> all = workspaces.all();

    // then
    assertThat(all).isNotNull();
    assertThat(all).isNotEmpty();
    Workspace firstWorkspace = all.get(0);
    assertThat(firstWorkspace.getId()).isPositive();
    assertThat(firstWorkspace.getName()).isNotEmpty();
  }

  @Test
  public void shouldFindByName() throws Exception {
    // given
    String givenWorkspaceName = TestConstants.EXISTING_WORKSPACE;

    // when
    Workspace workspace = workspaces.byName(givenWorkspaceName);

    // then
    assertThat(workspace).isNotNull();
    assertThat(workspace.getId()).isPositive();
    assertThat(workspace.getName()).isEqualTo(givenWorkspaceName);
  }
}
