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

package pl.project13.janbanery.core.flow.batch;

import pl.project13.janbanery.core.dao.Memberships;
import pl.project13.janbanery.core.flow.MembershipFlow;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.ProjectMembership;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class MembershipsFlowImpl implements MembershipsFlow {

  private Memberships memberships;

  private Project project;

  public MembershipsFlowImpl(Memberships memberships, Project project) {
    this.memberships = memberships;
    this.project = project;
  }

  @Override
  public MembershipFlow create(ProjectMembership projectMembership) throws IOException {
    return memberships.create(project, projectMembership);
  }

  @Override
  public List<ProjectMembership> all() throws IOException {
    return memberships.all(project);
  }

  @Override
  public void delete(ProjectMembership membership) throws IOException {
    memberships.delete(project, membership);
  }

  @Override
  public void deleteAll() throws IOException {
    memberships.deleteAll(project);
  }

  @Override
  public MembershipFlow update(ProjectMembership membership, ProjectMembership newValues) throws IOException {
    return memberships.update(project, membership, newValues);
  }

  @Override
  public MembershipFlow byId(Long id) throws IOException {
    return memberships.byId(project, id);
  }

}
