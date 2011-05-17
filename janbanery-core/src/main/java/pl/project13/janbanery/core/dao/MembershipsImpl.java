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
import pl.project13.janbanery.core.flow.MembershipFlow;
import pl.project13.janbanery.core.flow.MembershipFlowImpl;
import pl.project13.janbanery.core.flow.MembershipUpdateFlow;
import pl.project13.janbanery.core.flow.MembershipUpdateFlowImpl;
import pl.project13.janbanery.core.flow.batch.MembershipsFlow;
import pl.project13.janbanery.core.flow.batch.MembershipsFlowImpl;
import pl.project13.janbanery.core.rest.RestClient;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.ProjectMembership;
import pl.project13.janbanery.resources.Workspace;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public class MembershipsImpl implements Memberships {

  private Configuration conf;
  private RestClient restClient;

  private Workspace currentWorkspace;
  private Project currentProject;

  public MembershipsImpl(Configuration conf, RestClient restClient) {
    this.conf = conf;
    this.restClient = restClient;
  }

  @Override
  public MembershipUpdateFlow update(ProjectMembership membership) throws ServerCommunicationException {
    return new MembershipUpdateFlowImpl(currentProject, this, membership);
  }

  @Override
  public MembershipFlow update(Project project, ProjectMembership membership, ProjectMembership newValues) {
    String url = getMembershipUrl(project, membership);

    ProjectMembership updatedMembership = restClient.doPut(url, newValues, GsonTypeTokens.PROJECT_MEMBERSHIP);

    return new MembershipFlowImpl(this, project, updatedMembership);
  }

  @Override
  public MembershipFlow create(Project project, ProjectMembership membership) {
    String url = getMembershipsUrl(project);

    ProjectMembership createdMembership = restClient.doPost(url, membership, GsonTypeTokens.PROJECT_MEMBERSHIP);

    return new MembershipFlowImpl(this, project, createdMembership);
  }

  @Override
  public void delete(Project project, ProjectMembership membership) {
    String url = getMembershipUrl(project, membership);

    restClient.doDelete(url);
  }

  @Override
  public void deleteAll(Project project) {
    List<ProjectMembership> all = all(project);

    for (ProjectMembership projectMembership : all) {
      delete(project, projectMembership);
    }
  }

  @Override
  public List<ProjectMembership> all(Project project) {
    String url = getMembershipsUrl(project);

    List<ProjectMembership> memberships = restClient.doGet(url, GsonTypeTokens.LIST_PROJECT_MEMBERSHIP);

    return memberships;
  }

  @Override
  public MembershipFlow byId(Project project, Long id) {
    String url = getMembershipUrl(project.getId(), id);

    ProjectMembership projectMembership = restClient.doGet(url, GsonTypeTokens.PROJECT_MEMBERSHIP);

    return new MembershipFlowImpl(this, project, projectMembership);
  }

  private String getMembershipsUrl(Project project) {
    return conf.getApiUrl(currentWorkspace, "projects", project.getId(), "memberships");
  }

  private String getMembershipUrl(Project project, ProjectMembership membership) {
    return conf.getApiUrl(currentWorkspace, "projects", project.getId(), "memberships", membership.getId());
  }

  private String getMembershipUrl(Long projectId, Long membershipId) {
    return conf.getApiUrl(currentWorkspace, "projects", projectId, "memberships", membershipId);
  }

  public Memberships using(Workspace currentWorkspace, Project currentProject) {
    this.currentWorkspace = currentWorkspace;
    this.currentProject = currentProject;

    return this;
  }

  @Override
  public MembershipsFlow of(Project project) {
    return new MembershipsFlowImpl(this, project);
  }

  @Override
  public MembershipsFlow ofCurrentProject() {
    return of(currentProject);
  }
}
