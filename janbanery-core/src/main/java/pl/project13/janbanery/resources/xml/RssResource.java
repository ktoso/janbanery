package pl.project13.janbanery.resources.xml;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * POJOs used to map any RSS resource using XStream,
 * then use some mapper to get the relevant data out to your Domain objects.
 *
 * @author Konrad Malawski
 */
public class RssResource {

  static public class Rss {

    private Version version;
    private Channel channel;
    private String description;
    private String link;

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

    public String getDescription() {
      return description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public void setLink(String link) {
      this.link = link;
    }

    public String getLink() {
      return link;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Rss rss = (Rss) o;

      if (channel != null ? !channel.equals(rss.channel) : rss.channel != null) {
        return false;
      }
      if (description != null ? !description.equals(rss.description) : rss.description != null) {
        return false;
      }
      if (link != null ? !link.equals(rss.link) : rss.link != null) {
        return false;
      }
      if (version != null ? !version.equals(rss.version) : rss.version != null) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int result = version != null ? version.hashCode() : 0;
      result = 31 * result + (channel != null ? channel.hashCode() : 0);
      result = 31 * result + (description != null ? description.hashCode() : 0);
      result = 31 * result + (link != null ? link.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "Rss{" +
          "version=" + version +
          ", description='" + description + '\'' +
          ", link='" + link + '\'' +
          '}';
    }
  }

  static public class Channel {

    private String title;
    private String link;
    private String description;
    private String language;
    private String webMaster;
    private DateTime pubDate;
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

    public DateTime getPubDate() {
      return pubDate;
    }

    public void setPubDate(DateTime pubDate) {
      this.pubDate = pubDate;
    }

    public List<Item> getItems() {
      return items;
    }

    public void setItems(List<Item> items) {
      this.items = items;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Channel channel = (Channel) o;

      if (description != null ? !description.equals(channel.description) : channel.description != null) {
        return false;
      }
      if (link != null ? !link.equals(channel.link) : channel.link != null) {
        return false;
      }
      if (pubDate != null ? !pubDate.equals(channel.pubDate) : channel.pubDate != null) {
        return false;
      }
      if (title != null ? !title.equals(channel.title) : channel.title != null) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int result = title != null ? title.hashCode() : 0;
      result = 31 * result + (link != null ? link.hashCode() : 0);
      result = 31 * result + (description != null ? description.hashCode() : 0);
      result = 31 * result + (pubDate != null ? pubDate.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "Channel{" +
          "title='" + title + '\'' +
          ", link='" + link + '\'' +
          ", description='" + description + '\'' +
          ", pubDate=" + pubDate +
          '}';
    }
  }

  static public class Item {

    private Guid guid;
    private String title;
    private String link;
    private String description;
    private DateTime pubDate;

    public Guid getGuid() {
      return guid;
    }

    public void setGuid(Guid guid) {
      this.guid = guid;
    }

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

    public void setPubDate(DateTime pubDate) {
      this.pubDate = pubDate;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public DateTime getPubDate() {
      return pubDate;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Item item = (Item) o;

      if (description != null ? !description.equals(item.description) : item.description != null) {
        return false;
      }
      if (guid != null ? !guid.equals(item.guid) : item.guid != null) {
        return false;
      }
      if (link != null ? !link.equals(item.link) : item.link != null) {
        return false;
      }
      if (pubDate != null ? !pubDate.equals(item.pubDate) : item.pubDate != null) {
        return false;
      }
      if (title != null ? !title.equals(item.title) : item.title != null) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int result = guid != null ? guid.hashCode() : 0;
      result = 31 * result + (title != null ? title.hashCode() : 0);
      result = 31 * result + (link != null ? link.hashCode() : 0);
      result = 31 * result + (description != null ? description.hashCode() : 0);
      result = 31 * result + (pubDate != null ? pubDate.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "Item{" +
          "guid=" + guid +
          ", title='" + title + '\'' +
          ", link='" + link + '\'' +
          ", description='" + description + '\'' +
          ", pubDate=" + pubDate +
          '}';
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

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Version version = (Version) o;

      if (value != null ? !value.equals(version.value) : version.value != null) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      return value != null ? value.hashCode() : 0;
    }

    @Override
    public String toString() {
      return "Version{" +
          "value='" + value + '\'' +
          '}';
    }
  }

  public static class Guid {

    private boolean permaLink = false;
    private String url;

    public Guid() {
    }

    public boolean isPermaLink() {
      return permaLink;
    }

    public void setPermaLink(boolean permaLink) {
      this.permaLink = permaLink;
    }

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }

      Guid guid = (Guid) o;

      if (permaLink != guid.permaLink) {
        return false;
      }
      if (url != null ? !url.equals(guid.url) : guid.url != null) {
        return false;
      }

      return true;
    }

    @Override
    public int hashCode() {
      int result = (permaLink ? 1 : 0);
      result = 31 * result + (url != null ? url.hashCode() : 0);
      return result;
    }

    @Override
    public String toString() {
      return "Guid{" +
          "url='" + url + '\'' +
          '}';
    }
  }

}
