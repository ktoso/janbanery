package pl.project13.janbanery.core.dao;

import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.resources.Estimate;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class EstimatesTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);
  }

  @Test
  public void testAll() throws Exception {
    // when
    List<Estimate> estimates = janbanery.estimates().all();

    // then
    assertThat(estimates).isNotNull();
    assertThat(estimates).isNotEmpty();
  }

  @Test
  public void testAllIn() throws Exception {

  }

  @Test
  public void testById() throws Exception {
    // given
    Long id = janbanery.estimates().all().get(0).getId();

    // when
    Estimate estimate = janbanery.estimates().byId(id);

    // then
    assertThat(estimate).isNotNull();
    assertThat(estimate.getId()).isEqualTo(id);
  }
}
