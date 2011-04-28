package pl.project13.janbanery.entity2wiki;

import org.junit.Test;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class Entity2WikiRunnerTest {

  Entity2WikiRunner entity2WikiRunner = new Entity2WikiRunner(new File(""));

  @Test
  public void testName() throws Exception {
    String markdownized = entity2WikiRunner.markdownize("{@link SomeClass}");

    assertThat(markdownized).isEqualTo("<a href=\"#Clazz\">Clazz</a>");
  }
}
