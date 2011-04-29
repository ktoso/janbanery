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

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import pl.project13.janbanery.resources.Priority;

import java.lang.reflect.Type;

/**
 * Date: 4/21/11
 *
 * @author Konrad Malawski
 */
public class GsonFactory {

  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssZZ");

  public static Gson create() {
    return new GsonBuilder()
        .registerTypeAdapter(DateTime.class, new DateTimeSerializer())
        .registerTypeAdapter(DateTime.class, new DateTimeDeserializer())
        .registerTypeAdapter(Priority.class, new PrioritySerializer())
        .registerTypeAdapter(Priority.class, new PriorityDeserializer())
//        .setPrettyPrinting()
        .create();
  }

  /* Serializers and Deserializers */

  public static class DateTimeSerializer implements JsonSerializer<DateTime> {

    @Override
    public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(dateTimeFormatter.print(src));
    }
  }

  public static class DateTimeDeserializer implements JsonDeserializer<DateTime> {

    @Override
    public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      String dateString = json.getAsString();

      return deserializeIso8601DateTime(dateString);
    }

    @VisibleForTesting DateTime deserializeIso8601DateTime(String dateString) {
      return dateTimeFormatter.parseDateTime(dateString);
    }
  }

  public static class PrioritySerializer implements JsonSerializer<Priority> {
    @Override
    public JsonElement serialize(Priority priority, Type typeOfSrc, JsonSerializationContext context) {
      return new JsonPrimitive(priority.id());
    }
  }

  public static class PriorityDeserializer implements JsonDeserializer<Priority> {
    @Override
    public Priority deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
      int id = json.getAsInt();
      return Priority.fromPriorityId(id);
    }
  }
}
