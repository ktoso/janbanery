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

package pl.project13.janbanery.exceptions;

import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

/**
 * Thrown if you try to <strong>access</strong> or <strong>use</strong> a {@link Project}
 * that does not exist in that particular {@link Workspace}
 *
 * @author Konrad Malawski
 */
public class ProjectNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 949827205009771235L;

  public ProjectNotFoundException() {
  }

  public ProjectNotFoundException(String message) {
    super(message);
  }

  public ProjectNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ProjectNotFoundException(Throwable cause) {
    super(cause);
  }
}
