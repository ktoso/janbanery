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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.resources.KanbaneryEnumResource;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.lang.reflect.Field;

/**
 * Minor reflection utils such as getting a fields value with etc.
 * It;s only being used while creating the POST/PUT String for Kanbanery from an
 * {@link KanbaneryResource} object when using the {@link pl.project13.janbanery.encoders.ReflectionBodyGenerator}.
 *
 * @author Konrad Malawski
 */
class ReflectionHelper {

  private Logger log = LoggerFactory.getLogger(getClass());

  public Object getFieldValueRepresentationOrNull(Object entity, Field field) {
    Object fieldValue = null;

    try {
      fieldValue = getFieldValueRepresentation(entity, field);
    } catch (NullPointerException ignored) {
      // ok, no problem (the value was null seemingly)
    }

    //todo I'm not that happy with this...
    if (fieldValue != null && fieldValue.getClass().isEnum()) {
      KanbaneryEnumResource resource = (KanbaneryEnumResource) fieldValue;
      fieldValue = resource.jsonRepresentation();
    }

    return fieldValue;
  }

  public Object getFieldValueRepresentation(Object entity, Field field) {
    Object value = null;
    try {
      field.setAccessible(true);
      value = field.get(entity);
    } catch (NullPointerException ignored) {
      // ignore it
    } catch (IllegalAccessException ignored) {
      // ignore it
    }

    if (value != null) {
      log.debug(entity.getClass().getSimpleName() + "." + field.getName() + " = " + value);
    }
    return value;
  }
}
