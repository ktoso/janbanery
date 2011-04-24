package pl.project13.janbanery.encoders.utils;

import com.google.common.base.Predicate;
import pl.project13.janbanery.resources.KanbaneryResource;

/**
 * @author Konrad Malawski
 */
public class SettableAndNotNulFieldsPredicate<T extends KanbaneryResource> implements Predicate<String> {

  private T entity;

  private ReflectionHelper reflectionHelper = new ReflectionHelper();

  public SettableAndNotNulFieldsPredicate(T entity) {
    this.entity = entity;
  }

  @Override
  public boolean apply(String fieldName) {
    Object fieldValue = reflectionHelper.getFieldValueOrNull(entity, fieldName);

    return fieldValue != null;
  }
}
