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

package pl.project13.janbanery.encoders;

import org.junit.Test;
import pl.project13.janbanery.resources.Task;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class ReflectionBodyGeneratorTest {

  ReflectionBodyGenerator reflectionBodyGenerator = new ReflectionBodyGenerator();

  @Test
  public void testAsString() throws Exception {
    // given
    String title = "Title";
    String taskTypeName = "Bug";
    Task task = new Task(title, taskTypeName);

    // when
    String encodedString = reflectionBodyGenerator.asString(task);

    // then
    // (order of this items may vary, that's why contains() and not isEqualTo())
    assertThat(encodedString).contains("task[title]=Title");
    assertThat(encodedString).contains("&");
    assertThat(encodedString).contains("task[task_type_name]=Bug");
  }
}
