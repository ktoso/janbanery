package pl.project13.janbanery.resources.additions;

import com.google.common.annotations.GwtCompatible;

import java.lang.annotation.*;

/**
 * Just a marker annotation to carry the information from the Kanbanery API description right into our resource classes.
 * Classes or fields annotated with this annotation will not be modifiable by our API, they're exposed as readonly.
 *
 * @author Konrad Malawski
 */
@Documented
@GwtCompatible
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface ReadOnly {
}
