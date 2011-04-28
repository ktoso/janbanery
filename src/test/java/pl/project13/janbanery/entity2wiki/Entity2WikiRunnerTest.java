package pl.project13.janbanery.entity2wiki;

import com.google.common.collect.Lists;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class Entity2WikiRunnerTest {

  Entity2WikiRunner entity2WikiRunner = new Entity2WikiRunner(Lists.<Class<?>>newArrayList(), "");

  @Test
  public void testName() throws Exception {
    String markdownized = entity2WikiRunner.markdownize("{@link SomeClass}");

    assertThat(markdownized).isEqualTo("<a href=\"#Clazz\">Clazz</a>");
  }
}
