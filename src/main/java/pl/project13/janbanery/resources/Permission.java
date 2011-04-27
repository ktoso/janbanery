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
