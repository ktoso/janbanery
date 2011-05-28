package pl.project13.janbanery.config.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import org.joda.time.DateTime;

/**
 * @author Konrad Malawski
 */
public class DateTimeConverter extends AbstractSingleValueConverter {
  @Override
  public boolean canConvert(Class type) {
    return type.equals(DateTime.class);
  }

  @Override
  public Object fromString(String str) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }
}
