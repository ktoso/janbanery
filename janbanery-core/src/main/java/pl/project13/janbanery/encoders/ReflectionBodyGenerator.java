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

package pl.project13.janbanery.encoders;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.encoders.utils.FieldNameToArrayNotationWithValueFunction;
import pl.project13.janbanery.encoders.utils.SettableNotNullFieldsPredicate;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.lang.reflect.Field;
import java.util.Collection;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Konrad Malawski
 */
public class ReflectionBodyGenerator implements FormUrlEncodedBodyGenerator {

  private Logger log = LoggerFactory.getLogger(getClass());

  public ReflectionBodyGenerator() {
  }

  @Override
  public <T extends KanbaneryResource> String asString(T entity) {
    String resourceId = entity.getResourceId();
    log.info("Preparing form encoded data for: {}", resourceId);

    Collection<Field> fields = getAllFields(entity);
    log.info("{} has {} fields in total.", resourceId, fields.size());

    Collection<Field> fieldsToBeSet = filter(fields, new SettableNotNullFieldsPredicate<T>(entity));
    log.info("{} has {} fields to be set (not null and @Settable)", resourceId, fieldsToBeSet.size());

    Collection<String> fieldsWithValues = transform(fieldsToBeSet, new FieldNameToArrayNotationWithValueFunction<T>(entity));
    log.info("Prepared array notation of data for {}", resourceId);

    return joinDataParts(fieldsWithValues);
  }

  private <T extends KanbaneryResource> Collection<Field> getAllFields(T entity) {
    Class<? extends KanbaneryResource> clazz = entity.getClass();

    return newArrayList(clazz.getDeclaredFields());
  }

  @VisibleForTesting
  String joinDataParts(Collection<String> formData) {
    return Joiner.on("&").join(formData);
  }

}
