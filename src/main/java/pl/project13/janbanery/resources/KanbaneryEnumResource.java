package pl.project13.janbanery.resources;

import pl.project13.janbanery.encoders.ReflectionsBodyGenerator;

/**
 * This interface is used to ensure all enums will be able to be represented
 * via the automatic {@link ReflectionsBodyGenerator}
 *
 * @author Konrad Malawski
 */
public interface KanbaneryEnumResource {
  String jsonRepresentation();
}
