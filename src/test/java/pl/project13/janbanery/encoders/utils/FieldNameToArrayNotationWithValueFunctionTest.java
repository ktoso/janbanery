package pl.project13.janbanery.encoders.utils;

import org.junit.Before;
import org.junit.Test;
import pl.project13.janbanery.resources.Task;
import sun.rmi.runtime.NewThreadAction;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Konrad Malawski
 */
public class FieldNameToArrayNotationWithValueFunctionTest {

  FieldNameToArrayNotationWithValueFunction<Task> function;

  @Before
  public void setUp() throws Exception {
    function = new FieldNameToArrayNotationWithValueFunction<Task>(null);
  }

  @Test
  public void testCamelCaseToUnderscore() throws Exception {
    // given
    String fieldName = "accountId";

    // when
    String underscoredName = function.camelCaseToUnderscore(fieldName);

    // then
    assertThat(underscoredName).isEqualTo("account_id");
  }
}
