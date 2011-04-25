package pl.project13.janbanery.config.gson;

import com.google.gson.reflect.TypeToken;
import pl.project13.janbanery.resources.Task;
import pl.project13.janbanery.resources.TaskType;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class GsonTypeTokens {

  // type tokens for generic types

  public static final Type LIST_WORKSPACES = new TypeToken<List<Workspace>>() {
  }.getType();
  public static final Type LIST_USER       = new TypeToken<List<User>>() {
  }.getType();
  public static final Type LIST_TASK_TYPE  = new TypeToken<List<TaskType>>() {
  }.getType();
  public static final Type LIST_TASK       = new TypeToken<List<Task>>() {
  }.getType();

  // simple types

  public static final Class<User> USER = User.class;
  public static final Class<Task> TASK = Task.class;
}
