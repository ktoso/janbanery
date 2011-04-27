package pl.project13.janbanery.encoders.utils;

import pl.project13.janbanery.encoders.ReflectionsBodyGenerator;
import pl.project13.janbanery.resources.KanbaneryEnumResource;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.lang.reflect.Field;

/**
 * Minor reflection utils such as getting a fields value with etc.
 * It;s only being used while creating the POST/PUT String for Kanbanery from an
 * {@link KanbaneryResource} object when using the {@link ReflectionsBodyGenerator}.
 *
 * @author Konrad Malawski
 */
class ReflectionHelper {

  Object getFieldValueRepresentation(Object entity, String fieldName) {
    Object fieldValue = null;

    try {
      Class clazz = entity.getClass();
      Field field = clazz.getDeclaredField(fieldName);
      field.setAccessible(true);

      if (field.getType().isEnum()) {
        KanbaneryEnumResource enumResource = (KanbaneryEnumResource) field.get(entity);
        fieldValue = enumResource.jsonRepresentation();
      } else {
        fieldValue = field.get(entity);
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    }

    return fieldValue;
  }

  public Object getFieldValueRepresentationOrNull(Object entity, String fieldName) {
    Object fieldValue = null;

    try {
      fieldValue = getFieldValueRepresentation(entity, fieldName);
    } catch (NullPointerException ignored) {
      // ok, no problem (the value was null seemingly)
    }

    return fieldValue;
  }
}