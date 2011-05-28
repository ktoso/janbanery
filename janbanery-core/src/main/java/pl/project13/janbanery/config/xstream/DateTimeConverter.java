package pl.project13.janbanery.config.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import pl.project13.janbanery.config.gson.GsonFactory;

import java.util.Locale;

/**
 * @author Konrad Malawski
 */
public class DateTimeConverter extends AbstractSingleValueConverter {

  // Tue, 17 May 2011 14:34:43 +0000
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("E, dd MMM yyyy HH:mm:ss ZZ")
                                                                           .withLocale(Locale.ENGLISH);

  @Override
  public boolean canConvert(Class type) {
    return type.equals(DateTime.class);
  }

  @Override
  public Object fromString(String dateString) {
    return dateTimeFormatter.parseDateTime(dateString);
  }
}
