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

  When value() default When.OnCreateOrUpdate;

  /**
   * Describes when the annotated field should be required to be set
   */
  public enum When {
    Always,
    OnCreate,
    OnUpdate,
    OnCreateOrUpdate
  }
}
