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
import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.resources.Estimate;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class EstimatesImpl implements Estimates {

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;
  private Project currentProject;

  public EstimatesImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public List<Estimate> all() throws IOException {
    String url = getDefaultUrl();
    return restClient.doGet(url, GsonTypeTokens.LIST_ESTIMATE);
  }

  @Override
  public Estimate byId(Long id) throws IOException {
    String url = getEstimateUrl(id);
    return restClient.doGet(url, GsonTypeTokens.ESTIMATE);
  }

  @Override
  public Estimate any() throws IOException {
    List<Estimate> estimates = all();

    if (estimates.size() == 0) {
      throw new EntityNotFoundException("Could not find any task types.");
    }

    return estimates.get(0);
  }

  private String getEstimateUrl(Long id) {
    return conf.getApiUrl(currentWorkspace, "estimates", id);
  }

  private String getDefaultUrl() {
    return getDefaultUrl(currentProject);
  }

  private String getDefaultUrl(Project project) {
    return conf.getApiUrl(currentWorkspace, project.getId(), "estimates");
  }

  public Estimates using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;
    return this;
  }
}
