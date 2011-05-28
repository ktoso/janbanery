package pl.project13.janbanery.resources.xml;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * POJOs used to map the RSS res
 * @author Konrad Malawski
 */
public class RssResource {

  static public class Rss {
    Version version;
    Channel channel;

    public Version getVersion() {
      return version;
    }

    public void setVersion(Version version) {
      this.version = version;
    }

    public Channel getChannel() {
      return channel;
    }

    public void setChannel(Channel channel) {
      this.channel = channel;
    }
  }

  static public class Channel {

    private String title;
    private String link;
    private String description;
    private String language;
    private String webMaster;
    private String pubDate;
    private List<Item> items = newArrayList();

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getLanguage() {
      return language;
    }

    public void setLanguage(String language) {
      this.language = language;
    }

    public String getWebMaster() {
      return webMaster;
    }

    public void setWebMaster(String webMaster) {
      this.webMaster = webMaster;
    }

    public String getPubDate() {
      return pubDate;
    }

    public void setPubDate(String pubDate) {
      this.pubDate = pubDate;
    }

    public List getItems() {
      return items;
    }

    public void setItems(List items) {
      this.items = items;
    }
  }

  static public class Item {

    private String title;
    private String link;
    private String description;

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getLink() {
      return link;
    }

    public void setLink(String link) {
      this.link = link;
    }

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }
  }

  static public class Version {
    private String value;

    public Version(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

}
