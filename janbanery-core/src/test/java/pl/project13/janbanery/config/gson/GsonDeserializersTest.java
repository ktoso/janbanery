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

package pl.project13.janbanery.config.gson;

import org.joda.time.DateTime;
import org.junit.Test;
import pl.project13.janbanery.config.gson.GsonFactory;
import pl.project13.janbanery.resources.Permission;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class GsonDeserializersTest {

  @Test
  public void shouldParseIso8601DateTime() throws Exception {
    // given
    GsonFactory.DateTimeDeserializer dateTimeDeserializer = new GsonFactory.DateTimeDeserializer();
    String dateString = "2010-12-15T01:25:10+00:00"; // as returned by kanbanery

    // when
    DateTime dateTime = dateTimeDeserializer.deserializeIso8601DateTime(dateString);

    // then
    assertThat(dateTime.year().get()).isEqualTo(2010);
    assertThat(dateTime.monthOfYear().get()).isEqualTo(12);
    assertThat(dateTime.dayOfMonth().get()).isEqualTo(15);

    assertThat(dateTime.hourOfDay().get()).isEqualTo(2);
    assertThat(dateTime.minuteOfHour().get()).isEqualTo(25);
    assertThat(dateTime.secondOfMinute().get()).isEqualTo(10);
  }
}
