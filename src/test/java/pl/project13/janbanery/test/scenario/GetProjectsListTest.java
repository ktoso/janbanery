package pl.project13.janbanery.test.scenario;

import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;
import pl.project13.janbanery.test.TestConstants;

import java.util.Collection;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class GetProjectsListTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    JanbaneryFactory janbaneryFactory = new JanbaneryFactory();
    PropertiesConfiguration configuration = new PropertiesConfiguration(TestConstants.VALID_CONF_FILE_LOCATION);

    janbanery = janbaneryFactory.connectUsing(configuration);
  }

  @Test
  public void shouldGetAllWorkspaces() throws Exception {
    // when
    List<Workspace> allWorkspaces = janbanery.workspaces();

    // then
    assertThat(allWorkspaces).isNotEmpty();
    Collection<Project> projectsInFirstWorkspace = allWorkspaces.get(0).getProjects();
    assertThat(projectsInFirstWorkspace).isNotEmpty();
  }
}
