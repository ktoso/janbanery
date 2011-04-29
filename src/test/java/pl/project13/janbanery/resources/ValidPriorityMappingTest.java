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
