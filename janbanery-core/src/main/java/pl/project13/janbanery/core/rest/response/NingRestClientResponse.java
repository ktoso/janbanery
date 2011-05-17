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

package pl.project13.janbanery.core.rest.response;

import com.ning.http.client.Response;

import java.io.IOException;


/**
 * @author Konrad Malawski
 */
public class NingRestClientResponse implements RestClientResponse {

  private Response response;

  public NingRestClientResponse(Response response) {
    this.response = response;
  }

  @Override
  public Integer getStatusCode() {
    return response.getStatusCode();
  }

  @Override
  public String getStatusText() {
    return response.getStatusText();
  }

  @Override
  public String getResponseBody() {
    String responseBody = null;
    try {
      responseBody = response.getResponseBody();
    } catch (IOException ignore) {
      // ignore it
    }
    return responseBody;
  }
}
