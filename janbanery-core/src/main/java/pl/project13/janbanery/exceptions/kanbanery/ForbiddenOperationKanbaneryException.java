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

package pl.project13.janbanery.exceptions.kanbanery;

/**
 * This exception is mapping an 403 response from the Kanbanery API.
 * <blockquote>
 * If the role assigned to user accessing the API doesn't allow them to perform the requested
 * action upon the requested resource the API returns403 (Forbidden) HTTP status.
 * </blockquote>
 *
 * @author Konrad Malawski
 */
public class ForbiddenOperationKanbaneryException extends KanbaneryException {

  private static final long serialVersionUID = -7400474381727697473L;
  public static final int MAPPED_ERROR_CODE = 403;

  public ForbiddenOperationKanbaneryException() {
    super();
  }

  public ForbiddenOperationKanbaneryException(String message) {
    super(message);
  }

  public ForbiddenOperationKanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public ForbiddenOperationKanbaneryException(Throwable cause) {
    super(cause);
  }
}
