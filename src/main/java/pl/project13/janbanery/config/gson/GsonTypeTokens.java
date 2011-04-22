package pl.project13.janbanery.config.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class GsonTypeTokens {

  public static final Type LIST_WORKSPACES = new TypeToken<List<Workspace>>() {}.getType();

  public static final Class<User> USER = User.class; // todo hm, should I keep these for the sake of "one style"?
}
