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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.flow.MembershipFlow;
import pl.project13.janbanery.resources.*;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static pl.project13.janbanery.test.TestConstants.EXISTING_WORKSPACE;
import static pl.project13.janbanery.test.TestConstants.VALID_CONF_FILE_LOCATION;

/**
 * @author Konrad Malawski
 */
public class ProjectMembershipTest {

  Janbanery janbanery;

  @Before
  public void setUp() throws Exception {
    PropertiesConfiguration conf = new PropertiesConfiguration(VALID_CONF_FILE_LOCATION);
    janbanery = new JanbaneryFactory().connectUsing(conf);
    janbanery.usingWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    shouldDeleteAllMemberships();

    janbanery.close();
  }

  @Test
  public void shouldCreateNewMembership() throws Exception {
    // given
    shouldDeleteAllMemberships();
    Project current = janbanery.projects().current();
    ProjectMembership projectMembership = new ProjectMembership.Builder().email("ktoso@project13.pl")
                                                                         .permission(Permission.VIEWER)
                                                                         .build();
    // when
    MembershipFlow membershipFlow = janbanery.memberships().ofCurrentProject().create(projectMembership);

    // then
    ProjectMembership membership = membershipFlow.get();

    assertThat(membership).isNotNull();
  }

  @Test
  public void shouldDeleteAllMemberships() throws Exception {
    // given
    List<ProjectMembership> all = janbanery.memberships().ofCurrentProject().all();

    // when
    for (ProjectMembership membership : all) {
      janbanery.memberships().ofCurrentProject().delete(membership);
    }

    // then
    List<ProjectMembership> allMembersAfterClean = janbanery.memberships().ofCurrentProject().all();

    assertThat(allMembersAfterClean).isEmpty();
  }

  @Test
  public void shouldFetchAllFromCurrentProject() throws Exception {
    //given
    User current = janbanery.users().current();

    // when
    List<ProjectMembership> members = janbanery.memberships().ofCurrentProject().all();

    // then
    assertThat(members).isNotEmpty();
    String currentProject = janbanery.projects().current().getName();
    janbanery.usingProject(currentProject);
    assertThat(members).onProperty("email").containsExactly(current.getEmail());
  }

  @Test
  public void shouldFetchMembershipsInDifferentWorkspaces() throws Exception {
    // given
    assert janbanery.projects().all().size() > 1 : "This test assumes you have more than one project";

    // when
    Project firstProject = janbanery.usingWorkspace("janbanery").projects().all().get(0);
    Project otherProject = janbanery.usingWorkspace("sckrk").projects().all().get(0);

    // then
    assertThat(firstProject).isNotEqualTo(otherProject);
  }

  @Test
  public void shouldFetchMembershipFromCurrentProjectById() throws Exception {
    // given
    assert janbanery.projects().all().size() > 1 : "This test assumes you have more than one project";

    Workspace firstWorkspace = janbanery.workspaces().all().get(0);
    Workspace secondWorkspace = janbanery.workspaces().all().get(1);

    Project currentProject = janbanery.usingWorkspace(firstWorkspace.getName()).projects().all().get(0);
    Project otherProject = janbanery.usingWorkspace(secondWorkspace.getName()).projects().all().get(0);

    List<ProjectMembership> allMembersOfCurrent = janbanery.memberships().of(currentProject).all();
    ProjectMembership anyOneFromCurrent = allMembersOfCurrent.get(0);

    // when
    Long membershipId = anyOneFromCurrent.getId();
    ProjectMembership foundMember = janbanery.memberships().ofCurrentProject().byId(membershipId).get();

    // then
    assertThat(foundMember).isEqualTo(anyOneFromCurrent);
  }

  @Test
  public void shouldThrowOnCrossProjectByIdFetch() throws Exception {
    // given
    List<Project> allProjects = janbanery.projects().all();
    assert allProjects.size() > 1 : "This test assumes you have more than one project";
    Project current = (Project) allProjects;

    // when
    janbanery.memberships().of(current).all().get(0);

    // then

  }

  @Test
  public void shouldThrowWhenCreatingNewMemberAndAlreadyOverMaxMembersCount() throws Exception {
    // given
    Project project = janbanery.projects().current();

    // when
    ProjectMembership membership = new ProjectMembership("ktoso@project13.pl", Permission.MEMBER);
    ProjectMembership newMember = janbanery.memberships().ofCurrentProject().create(membership).get();

    // then
    List<ProjectMembership> all = janbanery.memberships().ofCurrentProject().all();
    assertThat(all).contains(newMember);
  }
}
