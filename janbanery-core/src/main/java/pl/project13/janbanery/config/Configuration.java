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
import pl.project13.janbanery.config.auth.AuthProvider;
import pl.project13.janbanery.resources.User;
import pl.project13.janbanery.resources.Workspace;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public interface Configuration {

  AuthProvider getAuthProvider();

  void forceUserPassAuthMode(String user, String password);

  void forceKeyAuthMode(String apiKey);

  AsyncHttpClient.BoundRequestBuilder authorize(AsyncHttpClient.BoundRequestBuilder requestBuilder);

  /**
   * Returns URL setup with workspace and projectId to start calling the API on it.
   * The URL looks like: https://janbanery.kanbanery.com/api/v1/projects/34242/
   *
   * @param workspace the workspace to use for the url
   * @param projectId the project id to use for the url
   * @return the properly set up url to begin calling Kanbanery API on it
   */
  String getApiUrl(Workspace workspace, Long projectId);

  /**
   * Checks if the passed in user is the user currently being used by Janbanery,
   * the actual check may vary depending on used AuthProvider (by apiKey or email etc).
   *
   * @param user user to check if it's "us"
   * @return true if the user is "us", false otherwise
   */
  boolean isCurrentUser(User user);

  // api urls

  String getApiUrl();

  String getApiUrl(Workspace workspace);

  String getApiUrl(Workspace workspace, String resourcesId, Long id);

  String getApiUrl(Workspace workspace, Long projectId, String resourceDotJson);

  String getApiUrl(Workspace workspace, Long projectId, String resource, String resourceDotJson);

  String getApiUrl(Workspace workspace, String resourcesId, Long resourceId, String resourceDotJson);

  // todo refactor this as VarArgs...
  String getApiUrl(Workspace currentWorkspace, String resourcesId, Long currentProject, String memberships, Long resourceDotJson);
}
