package pl.project13.janbanery.resources;

import com.google.gson.Gson;
import org.junit.Test;
import pl.project13.janbanery.config.gson.GsonFactory;

import java.io.Serializable;

/**
 * @author Konrad Malawski
 */
public class InvalidIncomingJsonTest {

  private Gson gson = GsonFactory.create();

  // sadly Gson only throws such an exception, nothing more detailed (details are in reason)
  @Test(expected = Exception.class)
  public void shouldThrowDueToNoSuchPriority() throws Exception {
    // given
    String json = "{priority: 22}";
    // when
    SampleWithPriority sampleWithPriority = gson.fromJson(json, SampleWithPriority.class);

    // then, deserializer should throw
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
