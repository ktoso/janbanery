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

package pl.project13.janbanery.encoders.utils;

import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.resources.KanbaneryResource;
import pl.project13.janbanery.resources.additions.ReadOnly;
import pl.project13.janbanery.resources.additions.Required;
import pl.project13.janbanery.resources.additions.Settable;

import java.lang.reflect.Field;

/**
 * @author Konrad Malawski
 */
public class SettableNotNullFieldsPredicate<T extends KanbaneryResource> implements Predicate<Field> {

  private Logger log = LoggerFactory.getLogger(getClass());

  private T entity;

  private ReflectionHelper reflectionHelper = new ReflectionHelper();

  public SettableNotNullFieldsPredicate(T entity) {
    this.entity = entity;
  }

  @Override
  public boolean apply(Field field) {
    if (field.isAnnotationPresent(ReadOnly.class)) {
      return false;
    } else {
      boolean isSettable = field.isAnnotationPresent(Settable.class)
          || field.isAnnotationPresent(Required.class);
      boolean isNotNull = null != reflectionHelper.getFieldValueRepresentation(entity, field);
      return isSettable && isNotNull;
    }
  }

}
