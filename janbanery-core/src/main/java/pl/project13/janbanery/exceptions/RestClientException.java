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

/**
 * Thrown when the {@link pl.project13.janbanery.core.rest.AsyncHttpClientRestClient} encounters any problems
 * while fetching the server response, note that Kanbanery error response codes are mapped as subclasses of
 * {@link pl.project13.janbanery.exceptions.kanbanery.KanbaneryException}.
 * This exception signals problems with network or interruption of a Future waiting etc.
 *
 * @author Konrad Malawski
 */
public class RestClientException extends RuntimeException {

  private static final long serialVersionUID = 7601400151619606970L;

  public RestClientException() {
  }

  public RestClientException(String message) {
    super(message);
  }

  public RestClientException(String message, Throwable cause) {
    super(message, cause);
  }

  public RestClientException(Throwable cause) {
    super(cause);
  }
}
