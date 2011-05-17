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

package pl.project13.janbanery.core.flow;

import pl.project13.janbanery.core.dao.Memberships;
import pl.project13.janbanery.resources.Permission;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.ProjectMembership;

/**
 * @author Konrad Malawski
 */
public class MembershipUpdateFlowImpl implements MembershipUpdateFlow {

  private Memberships memberships;

  private ProjectMembership membership;
  private Project project;


  public MembershipUpdateFlowImpl(Project project, Memberships memberships, ProjectMembership membership) {
    this.project = project;
    this.memberships = memberships;
    this.membership = membership;
  }

  @Override
  public MembershipFlow permission(Permission permission) {
    ProjectMembership commandObject = new ProjectMembership();
    commandObject.setPermission(permission);

    membership = memberships.update(project, membership, commandObject).get();

    return new MembershipFlowImpl(memberships, project, membership);
  }

  @Override
  public ProjectMembership get() {
    return membership;
  }
}
