package pl.project13.janbanery.resources.additions;

import com.google.common.annotations.GwtCompatible;

import java.lang.annotation.*;

/**
 * @author Konrad Malawski
 */
@Documented
@GwtCompatible
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface Settable {
  On value(); //default On.CreateOrUpdate;
}
