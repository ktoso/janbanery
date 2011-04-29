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
 * Just a stub class used for in between when there is no auth method setup yet.
 * Will most probably exist only for a few seconds to be replaced with a real AuthMode implementation.
 *
 * @author Konrad Malawski
 */
public class NoAuthMode implements AuthMode {
  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    throw new UnsupportedOperationException("No authorization method was setup. Can not connect to kanbanery without using any auth method.");
  }

  public String encodeUserPassword(String user, String password) {
    byte[] logon = String.format("%s:%s", user, password).getBytes();
    return new BASE64Encoder().encode(logon);
  }

  @Override
  public boolean isCurrentUser(User user) {
    return false;
  }
}
