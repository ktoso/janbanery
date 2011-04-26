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
    // (order of this items may vary, that's why contains() and not isEqualTo())
    assertThat(encodedString).contains("task[title]=Title");
    assertThat(encodedString).contains("&");
    assertThat(encodedString).contains("task[task_type_name]=Bug");
  }
}
