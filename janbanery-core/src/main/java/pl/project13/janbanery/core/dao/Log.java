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
import pl.project13.janbanery.resources.xml.ProjectLogEntry;

import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Log {

  List<ProjectLogEntry> all() throws ServerCommunicationException;

  ProjectLogEntry lastOne() throws ServerCommunicationException;

  List<ProjectLogEntry> last(Integer numberOfActions) throws ServerCommunicationException;

  List<ProjectLogEntry> fresherThan(ProjectLogEntry entry) throws ServerCommunicationException;
}
