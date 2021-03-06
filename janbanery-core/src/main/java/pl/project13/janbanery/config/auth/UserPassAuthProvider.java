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
 * @author Konrad Malawski
 */
public class UserPassAuthProvider implements AuthProvider {

  private String userEmail;
  private String authData;

  /**
   * Creates an instance of UserPassAuthProvider using the given username and password,
   * these will be encrypted right away using an BaseEncoder.
   *
   * @param userEmail username to be used in this plain auth method
   * @param password  password for this userEmail
   */
  public UserPassAuthProvider(String userEmail, String password) {
    this.userEmail = userEmail;
    this.authData = encodeUserPassword(userEmail, password);
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    Header header = getAuthHeader();
    return requestBuilder.addHeader(header.getKey(), header.getValue());
  }

  @Override
  public Header getAuthHeader() {
    return new Header("Authorization", "Basic " + authData);
  }

  public String encodeUserPassword(String user, String password) {
    byte[] logon = String.format("%s:%s", user, password).getBytes();
    return String.valueOf(Base64Coder.encode(logon));
  }

  @Override
  public boolean isCurrentUser(User user) {
    return userEmail.equals(user.getEmail());
  }
}
