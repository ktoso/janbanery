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

package pl.project13.janbanery.core.dao;

import pl.project13.janbanery.config.Configuration;
import pl.project13.janbanery.config.gson.GsonTypeTokens;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.exceptions.WorkspaceNotFoundException;
import pl.project13.janbanery.resources.Workspace;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public class WorkspacesImpl implements Workspaces {

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;

  public WorkspacesImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public List<Workspace> all() {
    String url = conf.getApiUrl() + "user/workspaces.json";
    return restClient.doGet(url, GsonTypeTokens.LIST_WORKSPACE);
  }

  @Override
  public Workspace byName(String name) throws WorkspaceNotFoundException {
    List<Workspace> allWorkspaces = all();
    for (Workspace workspace : allWorkspaces) {
      if (workspace.getName().equals(name)) {
        return workspace;
      }
    }
    throw new WorkspaceNotFoundException("Could not find workspace named '" + name + "'");
  }

  @Override
  public Workspace current() {
    if (currentWorkspace == null) {
      throw new WorkspaceNotFoundException("Janbanery has no current workspace! That's very bad and weird - you may be doing something weird.");
    }

    return currentWorkspace;
  }

  public Workspaces using(Workspace currentWorkspace) {
    this.currentWorkspace = currentWorkspace;

    return this;
  }
}
