/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    gson.fromJson(json, SampleWithPriority.class);

    // then, deserializer should throw
  }

  /**
   * Class only used for easy serialization/deserialization testing
   */
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
