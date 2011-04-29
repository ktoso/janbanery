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

import pl.project13.janbanery.exceptions.kanbanery.invalidentity.*;

/**
 * This exception is mapping an 422 response code from the Kanbanery API.
 * <blockquote>
 * If you're trying to create or modify any resource and some parameters have invalid values
 * or some required parameters are missing the API returns 422 (Unprocessable entity) HTTP
 * status and error description in the requested format (JSON or XML).
 * </blockquote>
 *
 * @author Konrad Malawski
 */
public class InvalidEntityKanbaneryException extends KanbaneryException {

  private static final long serialVersionUID  = -5929890856952121819L;
  public static final  int  MAPPED_ERROR_CODE = 422;

  public InvalidEntityKanbaneryException(String message) {
    super(message);
  }

  public InvalidEntityKanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public InvalidEntityKanbaneryException(Throwable cause) {
    super(cause);
  }

  /**
   * May be used as "Exception factory" to determine the "best" exception to be thrown
   * for such JSON response. For example we may throw "TaskAlreadyInFirstColumnException"
   * if the JSON contains a message informing us about this.
   *
   * @param response the JSON server response, containing a error messages
   * @return the "best" exception to be thrown for such an response JSON, based on it's error messages
   */
  public static KanbaneryException mostSpecializedException(String response) {
    if (response == null || "".equals(response)) {
      return new InvalidEntityKanbaneryException(response);
    } else if (TaskAlreadyInFirstColumnException.isBestExceptionFor(response)) {
      return new TaskAlreadyInFirstColumnException(response);
    } else if (TaskAlreadyInLastColumnException.isBestExceptionFor(response)) {
      return new TaskAlreadyInLastColumnException(response);
    } else if (PositionExceedsNumberOfTasksInColumnException.isBestExceptionFor(response)) {
      return new PositionExceedsNumberOfTasksInColumnException(response);
    } else if (CanOnlyIceBoxTaskFromFirstColumnException.isBestExceptionFor(response)) {
      return new CanOnlyIceBoxTaskFromFirstColumnException(response);
    } else if (CanOnlyArchiveFromLastColumn.isBestExceptionFor(response)) {
      return new CanOnlyArchiveFromLastColumn(response);
    } else {
      return new InvalidEntityKanbaneryException(response);
    }
  }
}
