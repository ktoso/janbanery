package pl.project13.janbanery.config.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.SingleValueConverter;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import pl.project13.janbanery.resources.xml.ProjectLogEntry;
import pl.project13.janbanery.resources.xml.RssResource;

/**
 * @author Konrad Malawski
 */
public class GuidConverter extends AbstractSingleValueConverter {
  @Override
  public boolean canConvert(Class type) {
    return type.equals(RssResource.Guid.class);
  }

  @Override
  public Object fromString(String str) {
    RssResource.Guid guid = new RssResource.Guid();
    guid.setUrl(str);

    return guid;
  }
}
