package pl.project13.janbanery.encoders;

import pl.project13.janbanery.resources.KanbaneryResource;

/**
 * @author Konrad Malawski
 */
public interface FormUrlEncodedBodyGenerator {
  <T extends KanbaneryResource> String asString(T entity);
}
