/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
@Retention(RetentionPolicy.RUNTIME)
public @interface Required {
  /**
   * Optionally instead of this field the field named in this annotation may be set.
   * For example you can set typeId XOR typeName when creating a task.
   */
  String alternativeTo() default "";

  /**
   * A short explanation how this field may be used etc
   */
  String comment() default "";
}
