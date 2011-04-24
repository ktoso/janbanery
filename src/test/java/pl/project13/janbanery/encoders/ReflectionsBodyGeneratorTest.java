package pl.project13.janbanery.encoders;

import org.junit.Test;
import pl.project13.janbanery.resources.Task;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class ReflectionsBodyGeneratorTest {

  ReflectionsBodyGenerator reflectionsBodyGenerator = new ReflectionsBodyGenerator();

  @Test
  public void testAsString() throws Exception {
    // given
    String title = "Title";
    String taskTypeName = "Bug";
    Task task = new Task(title, taskTypeName);

    // when
    String encodedString = reflectionsBodyGenerator.asString(task);

    // then
    assertThat(encodedString).isEqualTo("tasks[title]=Title&tasks[task_type_name]=Bug"); // todo
  }
}
