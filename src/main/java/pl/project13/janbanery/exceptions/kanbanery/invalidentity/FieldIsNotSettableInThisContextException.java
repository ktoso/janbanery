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

package pl.project13.janbanery.exceptions.kanbanery.invalidentity;

import pl.project13.janbanery.exceptions.kanbanery.InvalidEntityKanbaneryException;

/**
 * @author Konrad Malawski
 */
public class FieldIsNotSettableInThisContextException extends InvalidEntityKanbaneryException {

  private static final long serialVersionUID = -7584719747113079409L;

  public FieldIsNotSettableInThisContextException(String message) {
    super(message);
  }

  public FieldIsNotSettableInThisContextException(String message, Throwable cause) {
    super(message, cause);
  }

  public FieldIsNotSettableInThisContextException(Throwable cause) {
    super(cause);
  }

  public static boolean isBestExceptionFor(String response) {
    return response.contains("is read-only");
  }
}
