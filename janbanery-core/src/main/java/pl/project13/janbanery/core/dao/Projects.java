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

import pl.project13.janbanery.exceptions.EntityNotFoundException;
import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.Workspace;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Projects {

  // queries ------------------------------------------------------------------

  /**
   * Fetches a list of all {@link Project}s inside of the current {@link Workspace}.
   * If you want to access all Projects you have access to, try {@link #allAcrossWorkspaces()} instead.
   * <p/>
   * You may also use it like this to query a specific workspace:
   * <pre>
   *   janbanery.usingWorkspace("janbanery").projects().all();
   * </pre>
   *
   * @return
   * @throws ServerCommunicationException
   */
  List<Project> all() throws ServerCommunicationException ;

  /**
   * Fetches absolutely all {@link Project}s you have access to - across all Workspaces.
   * If you're only interested in one workspace, try {@link #all()}.
   *
   * @return
   * @throws ServerCommunicationException
   */
  List<Project> allAcrossWorkspaces() throws ServerCommunicationException;

  Project byId(Long id) throws EntityNotFoundException, ServerCommunicationException;

  Project current();

  // commands -----------------------------------------------------------------
  // no commands on projects are supported by Kanbanery v1.2
}
