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
 * If an 500 Internal Server Error would happen anyhow on Kanbanery,
 * this exception will be thrown. It is advised to wait a minute or two before
 * issuing any further requests - just an idea though.
 *
 * @author Konrad Malawski
 */
public class KanbaneryException extends RuntimeException {

  private static final long serialVersionUID  = -600128530560346354L;
  public static final  int  MAPPED_ERROR_CODE = 500;

  public KanbaneryException() {
  }

  public KanbaneryException(String message) {
    super(message);
  }

  public KanbaneryException(String message, Throwable cause) {
    super(message, cause);
  }

  public KanbaneryException(Throwable cause) {
    super(cause);
  }
}
