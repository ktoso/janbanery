package pl.project13.janbanery.encoders;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Multimap;
import org.reflections.Reflections;
import org.reflections.scanners.TypeElementsScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.project13.janbanery.config.reflections.ReflectionsFactory;
import pl.project13.janbanery.encoders.utils.FieldNameToArrayNotationWithValueFunction;
import pl.project13.janbanery.encoders.utils.SettableAndNotNulFieldsPredicate;
import pl.project13.janbanery.resources.KanbaneryResource;

import java.util.Collection;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

/**
 * @author Konrad Malawski
 */
public class ReflectionsBodyGenerator implements FormUrlEncodedBodyGenerator {

  private Logger log = LoggerFactory.getLogger(getClass());

  private Reflections reflections;

  public ReflectionsBodyGenerator() {
    reflections = new ReflectionsFactory().create();
  }

  public ReflectionsBodyGenerator(Reflections reflections) {
    this.reflections = reflections;
  }

  @Override
  public <T extends KanbaneryResource> String asString(T entity) {
    String resourceId = entity.getResourceId();
    log.info("Preparing form encoded data for: {}", resourceId);

    Collection<String> fields = getAllFields(entity);
    log.info("{} has {} fields in total.", resourceId, fields.size());

    Collection<String> fieldsToBeSet = filter(fields, new SettableAndNotNulFieldsPredicate<T>(entity));
    log.info("{} has {} fields to be set (not null and @Settable)", resourceId, fieldsToBeSet.size());

    Collection<String> fieldsWithValues = transform(fieldsToBeSet, new FieldNameToArrayNotationWithValueFunction<T>(entity));
    log.info("Prepared array notation of data for {}", resourceId);

    return joinDataParts(fieldsWithValues);
  }

  @VisibleForTesting <T extends KanbaneryResource> Collection<String> getAllFields(T entity) {
    Multimap<String, String> typeElements = reflections.getStore().get(TypeElementsScanner.class);

    Collection<String> allFields = typeElements.get(entity.getClass().getCanonicalName());
    return filter(allFields, new NotAMethodPredicate());
  }

  @VisibleForTesting String joinDataParts(Collection<String> formData) {
    return Joiner.on("&").join(formData);
  }


  private static class NotAMethodPredicate implements Predicate<String> {
    @Override public boolean apply(String input) {
      return !input.contains("("); // it's not a method
    }
  }

}
