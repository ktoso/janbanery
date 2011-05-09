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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.primitives.Chars.asList;

/**
 * @author Konrad Malawski
 */
public class FieldNameToArrayNotationWithValueFunction<T extends KanbaneryResource> implements Function<Field, String> {

  private final String fieldRepresentation = "%s[%s]=%s";

  private final T entity;

  private final ReflectionHelper reflectionHelper = new ReflectionHelper();

  public FieldNameToArrayNotationWithValueFunction(T entity) {
    this.entity = entity;
  }

  @Override
  public String apply(Field field) {
    String fieldName = field.getName();
    String resourceId = entity.getResourceId();

    String underscoredFieldName = camelCaseToUnderscore(fieldName);
    Object value = reflectionHelper.getFieldValueRepresentationOrNull(entity, field);

    return asEncodedFieldRepresentation(resourceId, underscoredFieldName, value);
  }

  private String asEncodedFieldRepresentation(String resourceId, String underscoredFieldName, Object value) {
    try {
      value = URLEncoder.encode(value.toString(), Charsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return String.format(fieldRepresentation, resourceId, underscoredFieldName, value);
  }

  @VisibleForTesting
  String camelCaseToUnderscore(String fieldName) {
    List<Character> chars = newArrayList(asList(fieldName.toCharArray()));

    for (int i = 0; i < chars.size(); i++) {
      Character aChar = chars.get(i);
      if (Character.isUpperCase(aChar)) {
        chars.remove(i);
        chars.add(i, Character.toLowerCase(aChar));
        chars.add(i, '_');
      }
    }

    return Joiner.on("").join(chars);
  }

}
