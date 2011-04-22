package pl.project13.janbanery.config;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Date: 4/21/11
 *
 * @author Konrad Malawski
 */
public class GsonFactory {

  public static Gson create() {
    return new GsonBuilder()
        .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
        .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
        .setPrettyPrinting()
        .create();
  }

  public static class DateTimeSerializer implements JsonSerializer<DateTime> {
    DateTimeFormatter dateTimeFormatter = ISODateTimeFormat.dateTime();

    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(dateTimeFormatter.print(src));
    }
  }

  public static class DateTimeDeserializer implements JsonDeserializer<DateTime> {
    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ");

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      String dateString = json.getAsString();

      return deserializeIso8601DateTime(dateString);
    }

    @VisibleForTesting DateTime deserializeIso8601DateTime(String dateString) {
      return dateTimeFormatter.parseDateTime(dateString);
    }
  }
}
