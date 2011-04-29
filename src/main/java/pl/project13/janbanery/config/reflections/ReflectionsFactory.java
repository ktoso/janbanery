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

package pl.project13.janbanery.config.reflections;

import com.google.common.base.Predicate;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.TypeElementsScanner;
import org.reflections.scanners.TypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.reflections.vfs.Vfs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Konrad Malawski
 */
public class ReflectionsFactory {

  private Logger log = LoggerFactory.getLogger(getClass());

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

    // fixme start bugfix hacking -----------------------------------------
    List<URL> clazzUrls = newArrayList(getBaseScanUrl());

    // this is a bugfix for running in "plain maven", not an IDE, weird I know...
//    List<Vfs.UrlType> defaultUrlTypes = findVfsUrlTypes(clazzUrls);
//    for (Vfs.UrlType defaultUrlType : defaultUrlTypes) {
//      Vfs.addDefaultURLTypes(defaultUrlType);
//    }
    // fixme end of bugfix hacking ----------------------------------------

    printScanPaths(clazzUrls);

    ConfigurationBuilder configuration = new ConfigurationBuilder()
        .filterInputsBy(filterKanbaneryResources)
        .setUrls(clazzUrls)
        .setScanners(new TypesScanner(),
//                     new SubTypesScanner(), // not needed for now
                     new TypeElementsScanner(),
                     new FieldAnnotationsScanner()
                    );

    return new Reflections(configuration);
  }

  private URL getBaseScanUrl() {
    URL urlForClass = ClasspathHelper.getUrlForClass(KanbaneryResource.class);

    String uselessPathPart = "pl/project13/janbanery/resources";
    if (urlForClass.getPath().contains(uselessPathPart)) {
      log.info("Apply path fix ({} -> ...)", urlForClass.getPath());
      try {
        String path = urlForClass.getPath();
        int from = path.indexOf(uselessPathPart);
        path = path.substring(0, from);
        urlForClass = new URL("file://" + path);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }
    return urlForClass;
  }

  private void printScanPaths(List<URL> clazzUrls) {
    log.info("----------------- will scan classes in -----------------------");
    for (URL clazzUrl : clazzUrls) {
      log.info(clazzUrl.toString());
    }
    log.info("--------------------------------------------------------------");
  }

  private List<Vfs.UrlType> findVfsUrlTypes(List<URL> clazzUrls) {
    List<Vfs.UrlType> defaultUrlTypesList = newArrayList();
    for (URL clazzUrl : clazzUrls) {
      List<Vfs.UrlType> defaultUrlTypes = Vfs.getDefaultUrlTypes();
      for (Vfs.UrlType defaultUrlType : defaultUrlTypes) {
        if (defaultUrlType.matches(clazzUrl)) {
          defaultUrlTypesList.add(defaultUrlType);
        }
      }
    }
    return defaultUrlTypesList;
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
