package pl.project13.janbanery.resources.additions;

import com.google.common.annotations.GwtCompatible;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Just a marker annotation to carry the information from the Kanbanery API description right into our resource classes.
 * A field with this annotation should always be set when working with such entities.
 *
 * @author Konrad Malawski
 */
@Documented
@GwtCompatible
@Retention(RetentionPolicy.SOURCE)
public @interface Required {
}
