package pl.project13.janbanery.config.gson;

import com.google.gson.reflect.TypeToken;
import pl.project13.janbanery.resources.*;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class GsonTypeTokens {

  // type tokens for generic types

  public static final Type LIST_WORKSPACE = new TypeToken<List<Workspace>>() {
  }.getType();
  public static final Type LIST_USER      = new TypeToken<List<User>>() {
  }.getType();
  public static final Type LIST_TASK_TYPE = new TypeToken<List<TaskType>>() {
  }.getType();
  public static final Type LIST_TASK      = new TypeToken<List<Task>>() {
  }.getType();
  public static final Type LIST_COLUMN    = new TypeToken<List<Column>>() {
  }.getType();


  // simple types

  public static final Class<User>             USER              = User.class;
  public static final Class<Task>             TASK              = Task.class;
  public static final Class<TaskSubscription> TASK_SUBSCRIPTION = TaskSubscription.class;
  public static final Class<Column>           COLUMN            = Column.class;
}
