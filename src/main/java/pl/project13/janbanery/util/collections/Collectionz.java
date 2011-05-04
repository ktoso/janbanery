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

package pl.project13.janbanery.util.collections;

import pl.project13.janbanery.exceptions.EntityNotFoundException;

import java.util.Collection;

/**
 * @author Konrad Malawski
 */
public class Collectionz {

  public static <T> T findOrThrow(Collection<T> collection, Criteria<T> criteria) {
    return findOrThrow(collection, "Could not find entity with using " + criteria, criteria);
  }

  public static <T> T findOrThrow(Collection<T> collection, String notFoundExceptionMessage, Criteria<T> criteria) {
    for (T item : collection) {
      if (criteria.matches(item)) {
        return item;
      }
    }

    throw new EntityNotFoundException(notFoundExceptionMessage);
  }

  public static interface Criteria<T> {
    public boolean matches(T item);
  }
}
