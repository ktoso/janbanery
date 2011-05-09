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

package pl.project13.janbanery.config.auth;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.resources.User;

/**
 * Common interface for all methods allowing authorization into the Kanbanery API.
 * For example, via API Key or plain user/password method.
 *
 * @author Konrad Malawski
 */
public interface AuthProvider {

  AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder);

  String encodeUserPassword(String user, String password);

  /**
   * Identify if the passed in user is the "current" user.
   *
   * @param user user to check if it's "us"
   * @return true if the user is "us", false otherwise
   */
  boolean isCurrentUser(User user);

  Header getAuthHeader();
}
