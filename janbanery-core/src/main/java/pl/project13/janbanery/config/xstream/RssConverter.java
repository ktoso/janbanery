package pl.project13.janbanery.config.xstream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import pl.project13.janbanery.resources.xml.RssResource;

import java.io.InputStream;
import java.io.OutputStream;

public class RssConverter {

  private XStream xStream;

  public RssConverter(XStream xStream) {
    this.xStream = xStream;

    xStream.alias("rss", RssResource.Rss.class);
    xStream.alias("item", RssResource.Item.class);
    xStream.addImplicitCollection(RssResource.Channel.class, "items");

    xStream.omitField(RssResource.Channel.class, "pubDate");
    xStream.omitField(RssResource.Channel.class, "guid");
//    xStream.omitField(RssResource.Item.class, "guid");

    xStream.useAttributeFor("version", RssResource.Version.class);

    xStream.registerConverter(new VersionConverter());
    xStream.registerConverter(new GuidConverter());
    xStream.registerConverter(new DateTimeConverter());
  }

  public RssConverter() {
    this(new XStream(new DomDriver()));
  }

  public RssResource.Rss fromXml(InputStream fileInputStream) {
    return (RssResource.Rss) xStream.fromXML(fileInputStream);
  }

  public void toXml(RssResource.Rss rss, OutputStream stream) {
    xStream.toXML(rss, stream);
  }
}