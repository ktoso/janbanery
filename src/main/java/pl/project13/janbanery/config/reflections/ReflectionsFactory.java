package pl.project13.janbanery.config.reflections;

import com.google.common.base.Predicate;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.scanners.TypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.net.URL;
import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Konrad Malawski
 */
public class ReflectionsFactory {

  private String resourcesPackage = "pl.project13.janbanery.resources";

  public ReflectionsFactory() {
  }

  public ReflectionsFactory(String resourcesPackage) {
    this.resourcesPackage = resourcesPackage;
  }

  public Reflections create() {
    Predicate<String> filterKanbaneryResources = new FilterBuilder()
        .include(allIn(resourcesPackage))
        .exclude(allIn(resourcesPackage, "additions"));
    ArrayList<URL> clazzUrls = newArrayList(ClasspathHelper.getUrlForClass(KanbaneryResource.class));

    ConfigurationBuilder configuration = new ConfigurationBuilder()
        .filterInputsBy(filterKanbaneryResources)
        .setUrls(clazzUrls)
        .setScanners(new TypesScanner(),
                     new SubTypesScanner(),
                     new TypeElementsScanner(),
                     new FieldAnnotationsScanner()
                    );

    return new Reflections(configuration);
  }

  public String getResourcesPackage() {
    return resourcesPackage;
  }

  public void setResourcesPackage(String resourcesPackage) {
    this.resourcesPackage = resourcesPackage;
  }

  private String allIn(String rootPackage, String subPackage) {
    return allIn(rootPackage + "." + subPackage);
  }

  private String allIn(String rootPackage) {
    return rootPackage + ".*";
  }
}
