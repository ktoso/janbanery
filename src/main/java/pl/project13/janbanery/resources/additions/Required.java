package pl.project13.janbanery.resources.additions;

import com.google.common.annotations.GwtCompatible;

import java.lang.annotation.*;

/**
 * Just a marker annotation to carry the information from the Kanbanery API description right into our resource classes.
 * A field with this annotation should always be set when working with such entities.
 *
 * @author Konrad Malawski
 */
@Inherited
@Documented
@GwtCompatible
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Required {
  /**
   * Optionally instead of this field the field named in this annotation may be set.
   * For example you can set typeId XOR typeName when creating a task.
   */
  String alternativeTo() default "";
}
