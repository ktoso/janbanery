package pl.project13.janbanery.config.gson;

import com.google.gson.reflect.TypeToken;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class GsonTypeTokens {

  public static final Type LIST_WORKSPACES = new TypeToken<List<Workspace>>() {}.getType();
  public static final Type LIST_USER       = new TypeToken<List<User>>() {}.getType();

  public static final Class<User> USER     = User.class; // todo hm, should I keep these for the sake of "one style"?
}
