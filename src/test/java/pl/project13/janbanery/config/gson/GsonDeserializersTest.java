package pl.project13.janbanery.config.gson;

import org.joda.time.DateTime;
import org.junit.Test;
import pl.project13.janbanery.config.gson.GsonFactory;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class GsonDeserializersTest {

  final GsonFactory.DateTimeDeserializer dateTimeDeserializer = new GsonFactory.DateTimeDeserializer();

  @Test
  public void shouldParseIso8601DateTime() throws Exception {
    // given
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
