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

package pl.project13.janbanery.encoders.utils;

import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.resources.Task;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class FieldNameToArrayNotationWithValueFunctionTest {

  FieldNameToArrayNotationWithValueFunction<Task> function;

  @Before
  public void setUp() throws Exception {
    function = new FieldNameToArrayNotationWithValueFunction<Task>(null);
  }

  @Test
  public void testCamelCaseToUnderscore() throws Exception {
    // given
    String fieldName = "accountId";

    // when
    String underscoredName = function.camelCaseToUnderscore(fieldName);

    // then
    assertThat(underscoredName).isEqualTo("account_id");
  }
}
