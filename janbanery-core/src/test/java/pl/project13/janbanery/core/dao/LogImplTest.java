package pl.project13.janbanery.core.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.resources.xml.ProjectLogEntry;
import pl.project13.janbanery.test.TestEntityHelper;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class LogImplTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf).toWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    TestEntityHelper.deleteTestTask(janbanery);

    janbanery.close();
  }

  @Test
  public void shouldFetchAllAvailable() throws Exception {
    // given
    Log log = janbanery.log();

    // when
    List<ProjectLogEntry> all = log.all();

    // then
    assertValidProjectLogEntries(all);
    assertThat(all.size()).isEqualTo(100);
  }

  @Test
  public void shouldFetch10FreshEntries() throws Exception {
    // given
    Log log = janbanery.log();

    int entriesToFetch = 10;
    List<ProjectLogEntry> allItems = log.all();

    // when
    List<ProjectLogEntry> lastTenItems = log.last(entriesToFetch);

    // then
    assertValidProjectLogEntries(lastTenItems);
    assertThat(lastTenItems.size()).isEqualTo(entriesToFetch);

    // they should be on top of the actions list, "the freshest"
    assertThat(allItems).startsWith(lastTenItems);
  }

  private void assertValidProjectLogEntries(List<ProjectLogEntry> all) {
    assertThat(all).isNotEmpty();

    ProjectLogEntry logEntry = all.get(0);
    assertThat(logEntry.getGuid()).isNotNull();
    assertThat(logEntry.getGuid().getUrl()).startsWith("http");
    assertThat(logEntry.getPubDate()).isNotNull();
    assertThat(logEntry.getDescription()).isNotNull();
    assertThat(logEntry.getTitle()).isNotNull();
  }
}
