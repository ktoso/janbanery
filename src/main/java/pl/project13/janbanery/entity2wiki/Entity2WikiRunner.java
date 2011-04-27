package pl.project13.janbanery.entity2wiki;

import com.google.common.collect.Multimap;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;
import org.reflections.Reflections;
import org.reflections.scanners.TypeElementsScanner;
import pl.project13.janbanery.config.reflections.ReflectionsFactory;
import pl.project13.janbanery.resources.*;
import pl.project13.janbanery.resources.additions.TaskLocation;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Lists.newArrayList;

/**
 * Simple sub project which is able to create valid GitHub Wiki (GitHub Favoured Markdown)
 * from entity classes - in our case, Resource classes :-)
 *
 * @author Konrad Malawski
 */
public class Entity2WikiRunner {

  private JavaDocBuilder           javaDocBuilder = new JavaDocBuilder();
  private Reflections              reflections    = new ReflectionsFactory().create();
  private Multimap<String, String> typeElements   = reflections.getStore().get(TypeElementsScanner.class);

  private Collection<Class<?>> classes;
  private final String N = "\n";
  private final String B = "**";

  public Entity2WikiRunner(Collection<Class<?>> classes, String sourceRootLocation) {
    this.classes = classes;

    javaDocBuilder.addSourceTree(new File(sourceRootLocation));
  }

  public static void main(String... args) throws NoSuchFieldException {
    if (args.length != 1) {
      System.out.println("1st and only parameter MUST be the PATH to the source root of the classes to be scanned :-)");
      System.exit(1);
    }

    String sourceRootLocation = args[0];

    List<Class<?>> enums = enums();
    List<Class<?>> classes = allResources();

    System.out.println("## Entities\n");
    Collection<Class<?>> settableClasses = filter(classes, new NotReadOnlyClassPredicate());
    new Entity2WikiRunner(settableClasses, sourceRootLocation).writeWikiTextTo(System.out);

    System.out.println("## ReadOnly Entities\n");
    Collection<Class<?>> readOnlyClasses = filter(classes, new ReadOnlyClassPredicate());
    new Entity2WikiRunner(readOnlyClasses, sourceRootLocation).writeWikiTextTo(System.out);

    System.out.println("## Enums\n");
    new Entity2WikiRunner(enums, sourceRootLocation).writeWikiTextTo(System.out);
  }

  @SuppressWarnings({"unchecked"})
  private static List<Class<?>> allResources() {
    return newArrayList(Column.class,
                        Estimate.class,
                        Issue.class,
                        Permission.class,
                        Project.class,
                        ProjectMembership.class,
                        SubTask.class,
                        Task.class,
                        TaskComments.class,
                        TaskSubscription.class,
                        TaskType.class,
                        User.class,
                        Workspace.class);
  }

  private static List<Class<?>> enums() {
    List<Class<?>> enums = newArrayList();
    enums.add(Priority.class);
    enums.add(Permission.class);
    enums.add(TaskLocation.class);
    return enums;
  }

  private void writeWikiTextTo(PrintStream out) throws NoSuchFieldException {
    for (Class<?> clazz : classes) {
      String wikiParagraphAboutClass = class2Wiki(clazz);
      out.println(wikiParagraphAboutClass);
    }
  }

  private String class2Wiki(Class<?> clazz) throws NoSuchFieldException {
    String className = clazz.getName();

    // header
    StringBuilder out = new StringBuilder("### ").append(clazz.getSimpleName()).append(N);

    // class comment
    out.append(javaDoc(className)).append(N);

    out.append(N);

    // field descriptions
    for (Field field : clazz.getDeclaredFields()) {
      String fieldName = field.getName();

      out.append("* ")
         .append(field.getType().getSimpleName()).append(" ")
         .append(B).append(fieldName).append(B)
         .append(" - ").append(javaDoc(className, fieldName))
         .append(N);
    }

    return out.toString();
  }

  private String javaDoc(String clazz) {
    JavaClass javaClass = javaDocBuilder.getClassByName(clazz);
    return javaClass.getComment();
  }

  private String javaDoc(String clazz, String field) {
    JavaClass javaClass = javaDocBuilder.getClassByName(clazz);
    JavaField[] fields = javaClass.getFields();

    for (JavaField javaField : fields) {
      if (javaField.getName().equals(field)) {
        return javaField.getComment();
      }
    }

    return "";
  }

}
