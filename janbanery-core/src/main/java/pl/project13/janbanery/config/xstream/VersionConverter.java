package pl.project13.janbanery.config.xstream;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import pl.project13.janbanery.resources.xml.RssResource;

/**
 * @author Konrad Malawski
 */
public class VersionConverter extends AbstractSingleValueConverter {

  @Override
  public boolean canConvert(Class type) {
    return type.equals(RssResource.Version.class);
  }

  @Override
  public String toString(Object obj) {
    return obj == null ? null : ((RssResource.Version) obj).getValue();
  }

  @Override
  public Object fromString(String str) {
    return new RssResource.Version(str);
  }
}
