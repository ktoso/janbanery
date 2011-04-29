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

package pl.project13.janbanery.config;

import com.ning.http.client.AsyncHttpClient;
import pl.project13.janbanery.config.auth.ApiKeyAuthMode;
import pl.project13.janbanery.config.auth.AuthMode;
import pl.project13.janbanery.config.auth.NoAuthMode;
import pl.project13.janbanery.config.auth.UserPassAuthMode;
import pl.project13.janbanery.resources.User;

import static java.lang.String.format;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class DefaultConfiguration implements Configuration {

  protected AuthMode authMode = new NoAuthMode();

  protected DefaultConfiguration() {
    // only for use of subclasses, other usages should create a valid Configuration instance right away
  }

  public DefaultConfiguration(String apiKey) {
    forceKeyAuthMode(apiKey);
  }

  public DefaultConfiguration(String user, String password) {
    forceUserPassAuthMode(user, password);
  }

  @Override
  public void forceUserPassAuthMode(String user, String password) {
    authMode = new UserPassAuthMode(user, password);
  }

  @Override
  public void forceKeyAuthMode(String apiKey) {
    authMode = new ApiKeyAuthMode(apiKey);
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    return authMode.authorize(requestBuilder);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCurrentUser(User user) {
    return authMode.isCurrentUser(user);
  }

  @Override
  public AuthMode getAuthMode() {
    return authMode;
  }

  @Override
  public String getApiUrl() {
    return "https://kanbanery.com/api/v1/";
  }

  @Override
  public String getApiUrl(String workspaceName) {
    return format("https://%s.kanbanery.com/api/v1/", workspaceName);
  }

  @Override
  public String getApiUrl(String workspaceName, String resourcesId, Long id) {
    return format("https://%s.kanbanery.com/api/v1/%s/%d.json", workspaceName, resourcesId, id);
  }

  @Override
  public String getApiUrl(String workspaceName, Long projectId, String resourceDotJson) {
    return format("https://%s.kanbanery.com/api/v1/projects/%s/%s.json", workspaceName, projectId, resourceDotJson);
  }

  @Override
  public String getApiUrl(String workspaceName, Long projectId, String resource, String resourceDotJson) {
    return format("https://%s.kanbanery.com/api/v1/projects/%d/%s/%s.json", workspaceName, projectId, resource, resourceDotJson);
  }

  @Override
  public String getApiUrl(String workspaceName, Long projectId) {
    return format("https://%s.kanbanery.com/api/v1/projects/%s/", workspaceName, projectId);
  }

}
