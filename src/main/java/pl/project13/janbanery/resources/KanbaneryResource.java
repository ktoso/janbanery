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

import com.google.gson.annotations.SerializedName;
import org.joda.time.DateTime;
import pl.project13.janbanery.resources.additions.ReadOnly;

/**
 * Date: 4/20/11
 *
 * @author Konrad Malawski
 */
public abstract class KanbaneryResource implements Resource {

  /**
   * Creation time
   */
  @ReadOnly
  @SerializedName("created_at")
  protected DateTime createdAt;

  /**
   * Last update time
   */
  @ReadOnly
  @SerializedName("updated_at")
  protected DateTime updatedAt;

  /**
   * It's not really useful for Janbanery apps (hey, we're Java, we got types!), but may be used for debugging etc...
   * <p/>
   * Type of this resource, set to "User". Only for JSON responses.
   * In XML responses node name is "user".
   */
  @ReadOnly
  protected String type;

  protected KanbaneryResource() {
  }

  /**
   * Defines how the resource is named in the API POST/PUT calls.
   * For example for an Task entity it will be "task" but may vary for other entities and not be so straight forward.
   * It will be used to create a "task[field]=value&[...]" notation of the object.
   *
   * @return the resource identifier of this resource (in API call urls)
   */
  public abstract String getResourceId();

  public DateTime getCreatedAt() {
    return createdAt;
  }

  public DateTime getUpdatedAt() {
    return updatedAt;
  }

  public String getType() {
    return type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    KanbaneryResource that = (KanbaneryResource) o;

    if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) {
      return false;
    }
    if (type != null ? !type.equals(that.type) : that.type != null) {
      return false;
    }
    if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = createdAt != null ? createdAt.hashCode() : 0;
    result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("KanbaneryResource");
    sb.append("{createdAt=").append(createdAt);
    sb.append(", updatedAt=").append(updatedAt);
    sb.append(", taskType='").append(type).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
