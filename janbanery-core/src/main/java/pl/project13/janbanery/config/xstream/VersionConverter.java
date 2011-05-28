package pl.project13.janbanery.config.xstream;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import pl.project13.janbanery.resources.xml.ProjectLogRss;

/**
 * @author Konrad Malawski
 */
public class VersionConverter extends AbstractSingleValueConverter {
    public boolean canConvert(Class type) {
        return type.equals(ProjectLogRss.Version.class);
    }
    public String toString(Object obj) {
        return obj == null ? null : ((ProjectLogRss.Version) obj).getValue();
    }
    public Object fromString(String str) {
        return new ProjectLogRss.Version(str);
    }
}
