package pl.project13.janbanery.resources;

import com.google.gson.Gson;
import org.junit.Test;
import pl.project13.janbanery.config.gson.GsonFactory;

import java.io.Serializable;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class PriorityMappingTest {

  private Gson gson = GsonFactory.create();

  @Test
  public void shouldConvertValidPriority() throws Exception {
    // given
    String sampleWithPriorityJson = "{priority : 1}";

    // when
    SampleWithPriority sampleWithPriority = gson.fromJson(sampleWithPriorityJson, SampleWithPriority.class);

    // then
    Priority priority = sampleWithPriority.getPriority();
    assertThat(priority).isInstanceOf(Priority.class);
    assertThat(priority).isEqualTo(Priority.LOW);
  }

  private static class SampleWithPriority implements Serializable {
    Priority priority;

    SampleWithPriority() {
    }

    public Priority getPriority() {
      return priority;
    }

    public void setPriority(Priority priority) {
      this.priority = priority;
    }
  }
}
