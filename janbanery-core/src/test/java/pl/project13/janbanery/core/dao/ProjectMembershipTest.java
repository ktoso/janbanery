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
import org.junit.Ignore;
import org.junit.Test;
import pl.project13.janbanery.config.PropertiesConfiguration;
import pl.project13.janbanery.core.Janbanery;
import pl.project13.janbanery.core.JanbaneryFactory;
import pl.project13.janbanery.core.flow.MembershipFlow;
import pl.project13.janbanery.core.flow.batch.MembershipsFlow;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.MaximumNumbersOfCollaboratorsReachedException;
import pl.project13.janbanery.exceptions.kanbanery.invalidentity.ProjectOwnerCanNotBeGivenProjectMembership;
import pl.project13.janbanery.resources.Permission;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.ProjectMembership;
import pl.project13.janbanery.resources.Workspace;

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
    janbanery = new JanbaneryFactory().connectUsing(conf).toWorkspace(EXISTING_WORKSPACE);
  }

  @After
  public void tearDown() throws Exception {
    try {
      shouldDeleteAllMemberships();
    } catch (Exception ignore) {
      // ok, don't mind
    }

    janbanery.close();
  }

  @Test
  public void shouldCreateNewMembership() throws Exception {
    // given
    shouldDeleteAllMemberships();
    ProjectMembership projectMembership = new ProjectMembership("ktoso@project13.pl", Permission.VIEWER);


    // when
    MembershipFlow membershipFlow = janbanery.memberships().ofCurrentProject().create(projectMembership);

    // then
    ProjectMembership membership = membershipFlow.get();

    assertThat(membership).isNotNull();
  }

  @Test(expected = ProjectOwnerCanNotBeGivenProjectMembership.class)
  public void shouldThrowOnAddingOwner() throws Exception {
    // given
    shouldDeleteAllMemberships();
    ProjectMembership projectMembership = new ProjectMembership("kmalawski@project13.pl", Permission.VIEWER);

    // when
    MembershipsFlow membershipsFlow = janbanery.memberships().ofCurrentProject();
    membershipsFlow.create(projectMembership);

    // then, should have thrown
  }

  @Ignore("See comment bellow, I got an unlimited account, thus this test will never throw")
  @Test(expected = MaximumNumbersOfCollaboratorsReachedException.class)
  public void shouldThrowOnExceededUsersInProjectLimit() throws Exception {
    // given
    shouldDeleteAllMemberships();

    // when
    MembershipsFlow membershipsFlow = janbanery.memberships().ofCurrentProject();
    // todo this test makes no sense if you have an unlimited account....

    //noinspection InfiniteLoopStatement
    for (int i = 0; i < 10; i++) {
      String email = String.format("testaccount%d@project13.pl", i);
      membershipsFlow.create(new ProjectMembership(email, Permission.VIEWER));
    }

    // then, should have thrown
  }

  @Test
  public void shouldChangeUsersPermissions() throws Exception {
    // given
    shouldDeleteAllMemberships();
    Permission initialPermission = Permission.VIEWER;
    ProjectMembership projectMembership = new ProjectMembership("ktoso@project13.pl", initialPermission);

    MembershipFlow membershipFlow = janbanery.memberships().ofCurrentProject().create(projectMembership);

    // when
    Permission permissionAfterUpdate = Permission.MANAGER;
    membershipFlow = membershipFlow.update().permission(permissionAfterUpdate);
    // remember to keep updating the reference if you won't be using it as "pure fluent" api

    // then
    ProjectMembership membership = membershipFlow.get();

    assertThat(membership).isNotNull();
    assertThat(membership.getPermission()).isEqualTo(permissionAfterUpdate);
  }

  @Test
  public void shouldDeleteAllMemberships() throws Exception {
    // given
    MembershipsFlow membershipsFlow = janbanery.memberships().ofCurrentProject();

    // when
    membershipsFlow.deleteAll();

    // then
    List<ProjectMembership> allMembersAfterClean = membershipsFlow.all();

    assertThat(allMembersAfterClean).isEmpty();
  }

  @Test
  public void shouldFetchMembershipsInDifferentWorkspaces() throws Exception {
    // given
    assert janbanery.projects().allAcrossWorkspaces().size() > 1 : "This test assumes you have more than one project";

    // when
    Project firstProject = janbanery.usingWorkspace("janbanery").projects().all().get(0);
    Project otherProject = janbanery.usingWorkspace("sckrk").projects().all().get(0);

    // then
    assertThat(firstProject).isNotEqualTo(otherProject);
  }

  @Test
  public void shouldThrowOnProjectNotInThisWorkspace() throws Exception {
    // given
    Project janbaneryProject = janbanery.projects().current();

    // when
    List<ProjectMembership> projectMemberships = janbanery.usingWorkspace("sckrk").memberships().of(janbaneryProject).all();// sckrk does NOT have janbaneryProject

    // then
    assertThat(projectMemberships).isEmpty();
    // because this project is not in this workspace
    // Kanbanery does return an empty array, so do we
  }

  @Test
  public void shouldFetchOnByHandSetupWorkspaceAndProject() throws Exception {
    // given
    Workspace janbaneryWorkspace = janbanery.workspaces().current();
    Project janbaneryProject = janbanery.projects().current();

    // when
    List<ProjectMembership> all = janbanery.usingWorkspace(janbaneryWorkspace.getName())
                                           .memberships().of(janbaneryProject)
                                           .all();// this is a proper project / workspace pair

    // then
    // if it didn't throw it's ok
    assertThat(all).isNotNull();
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
