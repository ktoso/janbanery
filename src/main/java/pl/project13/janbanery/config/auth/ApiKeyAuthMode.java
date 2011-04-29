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
import sun.misc.BASE64Encoder;

/**
 * The safe and best method to use for API calls
 *
 * @author Konrad Malawski
 */
public class ApiKeyAuthMode implements AuthMode {

  public final String API_TOKEN_HEADER = "X-Kanbanery-ApiToken";

  private final String authData;

  public ApiKeyAuthMode(String authData) {
    this.authData = authData;
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    return requestBuilder.addHeader(API_TOKEN_HEADER, authData);
  }

  public String encodeUserPassword(String user, String password) {
    byte[] logon = String.format("%s:%s", user, password).getBytes();
    return new BASE64Encoder().encode(logon);
  }

  /**
   * Matches the passed in user against the currently used API KEY
   *
   * @param user user to be checked if it's "me"
   * @return true if the user is "us", false otherwise
   */
  @Override
  public boolean isCurrentUser(User user) {
    return authData.equals(user.getApiToken());
  }
}
