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

import pl.project13.janbanery.core.flow.MembershipFlow;
import pl.project13.janbanery.core.flow.MembershipUpdateFlow;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.ProjectMembership;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Memberships extends MembershipsOf {

  // commands -----------------------------------------------------------------

  MembershipFlow create(Project project, ProjectMembership membership) throws IOException;

  MembershipFlow update(Project project, ProjectMembership membership, ProjectMembership newValues) throws IOException;

  void delete(Project project, ProjectMembership membership);

  void deleteAll(Project project) throws IOException;

  // queries ------------------------------------------------------------------

  MembershipFlow byId(Project project, Long id) throws IOException;

  MembershipUpdateFlow update(ProjectMembership membership) throws IOException;

  List<ProjectMembership> all(Project project) throws IOException;

}
