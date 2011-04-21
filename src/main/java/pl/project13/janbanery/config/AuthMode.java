package pl.project13.janbanery.config;

/**
 * @author Konrad Malawski
 */
public enum AuthMode {
  /**
   * The safe and best method to use for API calls
   */
  API_KEY_MODE,

  /**
   * The not so good method using plain text username and password
   */
  USER_AND_PASS_MODE

}
