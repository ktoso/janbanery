package pl.project13.janbanery.config;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Date: 4/21/11
 *
 * @author Konrad Malawski
 */
public class GsonFactory {
  public static Gson create() {
    return new GsonBuilder()
        .setPrettyPrinting()
        .create();
  }
}
