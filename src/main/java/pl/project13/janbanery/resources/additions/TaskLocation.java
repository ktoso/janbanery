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

import pl.project13.janbanery.resources.Task;

/**
 * A helper class to specify a {@link Task}s location in Kanbanery.
 * NEXT and PREVIOUS are only used when performing updates.
 *
 * @author Konrad Malawski
 */
public enum TaskLocation {
  NEXT("next_column"),
  PREVIOUS("prev_column"),

  BOARD("board"),
  ICEBOX("icebox"),
  ARCHIVE("archive");

  private String requestBody;

  TaskLocation(String requestBody) {
    this.requestBody = requestBody;
  }

  public String requestBody() {
    return String.format("task[location]=%s", requestBody);
  }
}
