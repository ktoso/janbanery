package pl.project13.janbanery.resources.additions;

/**
 * Describes when the annotated field should be required to be set.
 * This should be used only for the sake of the @Required(value = On) annotation
 *
 * @author Konrad Malawski
 */
public enum On {
  Create,
  Update,
  CreateOrUpdate
}
