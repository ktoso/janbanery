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

import pl.project13.janbanery.resources.Project;
import pl.project13.janbanery.resources.User;

import java.io.IOException;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public interface Users {
  /**
   * Fetches the currently being used user.
   * The "me" user, one might say.
   *
   * @return the currently being used user for all API calls
   * @throws IOException if the response body could not be fetched
   */
  User current() throws IOException;

  List<User> all() throws IOException;

  List<User> allInProject(Project project) throws IOException;

  User nobody() throws IOException;
}
