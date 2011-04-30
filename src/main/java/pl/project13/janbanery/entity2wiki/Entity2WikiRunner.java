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

package pl.project13.janbanery.entity2wiki;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaField;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static java.lang.String.format;

/**
 * Simple sub project which is able to create valid GitHub Wiki (GitHub Favoured Markdown)
 * from entity classes - in our case, Resource classes :-)
 *
 * @author Konrad Malawski
 */
public class Entity2WikiRunner {

  private JavaDocBuilder       javaDocBuilder;
  private Collection<Class<?>> classes;

  private final String N = "\n";
  private final String B = "**";

  public Entity2WikiRunner(File sourceRootLocation) {
    javaDocBuilder = new JavaDocBuilder();
    javaDocBuilder.addSourceTree(sourceRootLocation);
  }

  public static void main(String... args) throws NoSuchFieldException {
    validateArgs(args);

    PrintStream out = System.out;
    File sourceRootLocation = new File(args[0]);

    List<Class<?>> enums = DocumentedClasses.enums();
    List<Class<?>> classes = DocumentedClasses.resources();


    Entity2WikiRunner entity2Wiki = new Entity2WikiRunner(sourceRootLocation);

    entity2Wiki.withClasses(classes).writeSection(out, "Entities", new NotReadOnlyClassPredicate());
    entity2Wiki.withClasses(classes).writeSection(out, "ReadOnly Entities", new ReadOnlyClassPredicate());

    entity2Wiki.withClasses(enums).writeSection(out, "Enums");
  }

  private static void validateArgs(String[] args) {
    if (args.length != 1) {
      System.out.println("1st and only parameter MUST be the PATH to the source root of the classes to be scanned :-)");
      System.exit(1);
    }
  }

  public void writeSection(PrintStream out, String title) throws NoSuchFieldException {
    writeSection(out, title, null);
  }

  public void writeSection(PrintStream out, String title, Predicate filter) throws NoSuchFieldException {
    out.println(format("## %s", title));

    Collection<Class<?>> classesToPrint = classes;
    if (filter != null) {
      //noinspection unchecked
      classesToPrint = filter(classes, filter);
    }
    writeWikiTextTo(classesToPrint, out);
  }

  private void writeWikiTextTo(Collection<Class<?>> classesToParse, PrintStream out) throws NoSuchFieldException {
    for (Class<?> clazz : classesToParse) {
      String wikiParagraphAboutClass = class2Wiki(clazz);
      out.println(wikiParagraphAboutClass);
    }
  }

  private String class2Wiki(Class<?> clazz) throws NoSuchFieldException {
    StringBuilder out = new StringBuilder();

    String className = clazz.getName();

    // header
    classHeader(clazz, out);

    // class comment
    out.append(javaDoc(className)).append(N);
    out.append(N);

    // field descriptions
    out.append(strong("Fields summary: ")).append(N);
    out.append(N);

    for (Field field : clazz.getDeclaredFields()) {
      String fieldName = field.getName();

      out.append("* ")
         .append(field.getType().getSimpleName()).append(" ")
         .append(strong(fieldName))
         .append(" - ").append(javaDoc(className, fieldName))
         .append(N);
    }

    return out.toString();
  }

  private void classHeader(Class<?> clazz, StringBuilder out) {
    String name = clazz.getSimpleName();
    out.append("### ").append(hashLink(name)).append(N);
    out.append(N);
  }

  private String javaDoc(String fullClassName) {
    JavaClass javaClass = javaDocBuilder.getClassByName(fullClassName);
    return markdownize(javaClass.getComment());
  }

  private String javaDoc(String fullClassName, String field) {
    JavaClass javaClass = javaDocBuilder.getClassByName(fullClassName);
    JavaField[] fields = javaClass.getFields();

    for (JavaField javaField : fields) {
      if (javaField.getName().equals(field)) {
        return markdownize(javaField.getComment());
      }
    }

    return "";
  }

  private String strong(String string) {
    return format("**%s**", string.trim());
  }

  private String hashLink(String name) {
    return format("<a href=\"#%s\" name=\"%s\">%s</a>", name, name, name);
  }

  @VisibleForTesting String markdownize(String comment) {
    if (comment == null) {
      return "";
    }

    comment = comment.replaceAll("\\{@link (\\w+)\\}", "<a href=\"#$1\">$1</a>");

    return comment;
  }

  public Entity2WikiRunner withClasses(Collection<Class<?>> classes) {
    this.classes = classes;
    return this;
  }
}
