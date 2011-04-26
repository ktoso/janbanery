package pl.project13.janbanery.encoders.utils;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.primitives.Chars.asList;

/**
 * @author Konrad Malawski
 */
public class FieldNameToArrayNotationWithValueFunction<T extends KanbaneryResource> implements Function<String, String> {

  private final String fieldRepresentation = "%s[%s]=%s";

  private final T entity;

  private final ReflectionHelper reflectionHelper = new ReflectionHelper();

  public FieldNameToArrayNotationWithValueFunction(T entity) {
    this.entity = entity;
  }

  @Override
  public String apply(String fieldName) {
    String resourceId = entity.getResourceId();
    String underscoredFieldName = camelCaseToUnderscore(fieldName);
    Object value = reflectionHelper.getFieldValueRepresentationOrNull(entity, fieldName);

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

  @VisibleForTesting String camelCaseToUnderscore(String fieldName) {
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
