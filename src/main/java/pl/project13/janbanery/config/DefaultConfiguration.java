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
import pl.project13.janbanery.config.auth.*;
import pl.project13.janbanery.config.auth.ApiKeyAuthProvider;
import pl.project13.janbanery.config.auth.AuthProvider;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

import static java.lang.String.format;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public class DefaultConfiguration implements Configuration {

  protected AuthProvider authProvider = new NoAuthProvider();

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
    authProvider = new UserPassAuthProvider(user, password);
  }

  @Override
  public void forceKeyAuthMode(String apiKey) {
    authProvider = new ApiKeyAuthProvider(apiKey);
  }

  @Override
  public AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder) {
    return authProvider.authorize(requestBuilder);
  }

  @Override
  public boolean isCurrentUser(User user) {
    return authProvider.isCurrentUser(user);
  }

  @Override
  public AuthProvider getAuthProvider() {
    return authProvider;
  }

  @Override
  public String getApiUrl() {
    return "https://kanbanery.com/api/v1/";
  }

  @Override
  public String getApiUrl(Workspace workspace) {
    String workspaceName = workspace.getName();
    return format("https://%s.kanbanery.com/api/v1/", workspaceName);
  }

  @Override
  public String getApiUrl(Workspace workspace, String resourcesId, Long id) {
    String workspaceName = workspace.getName();
    return format("https://%s.kanbanery.com/api/v1/%s/%d.json", workspaceName, resourcesId, id);
  }

  @Override
  public String getApiUrl(Workspace workspace, Long projectId, String resourceDotJson) {
    String workspaceName = workspace.getName();
    return format("https://%s.kanbanery.com/api/v1/projects/%s/%s.json", workspaceName, projectId, resourceDotJson);
  }

  @Override
  public String getApiUrl(Workspace workspace, Long projectId, String resource, String resourceDotJson) {
    String workspaceName = workspace.getName();
    return format("https://%s.kanbanery.com/api/v1/projects/%d/%s/%s.json", workspaceName, projectId, resource, resourceDotJson);
  }

  @Override
  public String getApiUrl(Workspace workspace, String resourcesId, Long id, String resourceDotJson) {
    String workspaceName = workspace.getName();
    return format("https://%s.kanbanery.com/api/v1/%s/%d/%s.json", workspaceName, resourcesId, id, resourceDotJson);
  }

  @Override
  public String getApiUrl(Workspace workspace, Long projectId) {
    String workspaceName = workspace.getName();
    return format("https://%s.kanbanery.com/api/v1/projects/%s/", workspaceName, projectId);
  }

}
