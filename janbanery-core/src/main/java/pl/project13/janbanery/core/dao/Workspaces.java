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

import pl.project13.janbanery.exceptions.ServerCommunicationException;
import pl.project13.janbanery.exceptions.WorkspaceNotFoundException;
import pl.project13.janbanery.resources.Workspace;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Workspaces {

  /**
   * Queries for all {@link Workspace}s that you have access to.
   *
   * @return all workspaces you have access to
   * @throws ServerCommunicationException
   */
  List<Workspace> all() throws ServerCommunicationException;

  /**
   * @param name
   * @return
   * @throws ServerCommunicationException
   * @throws WorkspaceNotFoundException
   */
  Workspace byName(String name) throws ServerCommunicationException, WorkspaceNotFoundException;

  Workspace current();
}
