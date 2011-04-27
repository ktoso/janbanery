package pl.project13.janbanery.entity2wiki;

import com.google.common.base.Predicate;
import pl.project13.janbanery.resources.additions.ReadOnly;

/**
 * @author Konrad Malawski
 */
public class NotReadOnlyClassPredicate implements Predicate<Class<?>> {

  @Override
  public boolean apply(Class<?> clazz) {
    return !clazz.isAnnotationPresent(ReadOnly.class);
  }
}
