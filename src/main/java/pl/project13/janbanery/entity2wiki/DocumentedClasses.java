package pl.project13.janbanery.entity2wiki;

import pl.project13.janbanery.resources.*;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Konrad Malawski
 */
public class DocumentedClasses {

  public static List<Class<?>> resources() {
    List<Class<?>> classes = newArrayList();
    classes.add(Column.class);
    classes.add(Estimate.class);
    classes.add(Issue.class);
    classes.add(Project.class);
    classes.add(ProjectMembership.class);
    classes.add(SubTask.class);
    classes.add(Task.class);
    classes.add(TaskComments.class);
    classes.add(TaskSubscription.class);
    classes.add(TaskType.class);
    classes.add(User.class);
    classes.add(Workspace.class);
    return classes;
  }

  public static List<Class<?>> enums() {
    List<Class<?>> enums = newArrayList();
    enums.add(Priority.class);
    enums.add(Permission.class);
    enums.add(TaskLocation.class);
    return enums;
  }
}
