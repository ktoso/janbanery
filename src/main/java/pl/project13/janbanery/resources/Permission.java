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

package pl.project13.janbanery.resources;

import pl.project13.janbanery.exceptions.NoSuchPermissionException;

/**
 * Permission is the Role that can be taken by a {@link User}.
 * Such permission is {@link Project} specific, and can be retrieved from {@link ProjectMembership}.
 *
 * @author Konrad Malawski
 */
public enum Permission implements KanbaneryEnumResource {
  /**
   * A manager role (can do anything)
   */
  MANAGER("manager"),

  /**
   * A member role (can do less than anything...?)
   */
  MEMBER("member"),

  /**
   * A viewer, readonly role. It can not change or create resources.
   */
  VIEWER("viewer");

  private String jsonName;

  Permission(String jsonName) {
    this.jsonName = jsonName;
  }

  @Override
  public String jsonRepresentation() {
    return jsonName;
  }

  public static Permission fromJsonName(String jsonName) {
    for (Permission permission : Permission.values()) {
      if (permission.jsonName.equals(jsonName)) {
        return permission;
      }
    }

    throw new NoSuchPermissionException("Tried to create permission for jsonName = " + jsonName);
  }
}
