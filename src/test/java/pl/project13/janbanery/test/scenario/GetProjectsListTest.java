package pl.project13.janbanery.test.scenario;

import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.JanbaneryImpl;
import pl.project13.janbanery.test.TestConstants;

/**
 * @author Konrad Malawski
 */
public class GetProjectsListTest {

  JanbaneryImpl janbanery;

  @Before
  public void setUp() throws Exception {
    JanbaneryFactory janbaneryFactory = new JanbaneryFactory();
    PropertiesConfiguration configuration = new PropertiesConfiguration(TestConstants.VALID_CONF_FILE_LOCATION);

    janbanery = janbaneryFactory.connectUsing(configuration);
  }

  @Test
  public void shouldGetAllWorkspaces() throws Exception {
    janbanery.findAllWorkspaces(); // todo refactor me
  }
}
