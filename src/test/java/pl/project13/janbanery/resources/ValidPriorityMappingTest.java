package pl.project13.janbanery.resources;

import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pl.project13.janbanery.config.gson.GsonFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
@RunWith(Parameterized.class)
public class ValidPriorityMappingTest {

  private Gson gson = GsonFactory.create();

  private String   json;
  private Priority targetPriority;

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    String json = "{\"priority\":%d}";
    return Arrays.asList(new Object[][]{
        {
            format(json, 0),
            Priority.LOW
        },
        {
            format(json, 1),
            Priority.MEDIUM
        },
        {
            format(json, 2),
            Priority.HIGH
        },
    });
  }

  public ValidPriorityMappingTest(String json, Priority targetPriority) {
    // given
    this.json = json;
    this.targetPriority = targetPriority;
  }

  @Test
  public void shouldParseJsonProperly() throws Exception {
    // when
    SampleWithPriority sampleWithPriority = gson.fromJson(json, SampleWithPriority.class);

    // then
    Priority priority = sampleWithPriority.getPriority();
    assertThat(priority).isInstanceOf(Priority.class);
    assertThat(priority).isEqualTo(targetPriority);
  }

  @Test
  public void shouldWriteJsonProperly() throws Exception {
    // when
    String generatedJson = gson.toJson(new SampleWithPriority(targetPriority));

    // then
    assertThat(generatedJson).isEqualTo(json);
  }

  private static class SampleWithPriority implements Serializable {
    Priority priority;

    public SampleWithPriority() {
    }

    public SampleWithPriority(Priority priority) {
      this.priority = priority;
    }

    public Priority getPriority() {
      return priority;
    }

    public void setPriority(Priority priority) {
      this.priority = priority;
    }
  }
}
