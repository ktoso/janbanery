package pl.project13.janbanery.resources.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.joda.time.DateTime;
import pl.project13.janbanery.config.xstream.GuidConverter;

/**
 * <pre>
 *     <item>
 *       <guid isPermaLink="false">https://janbanery.kanbanery.com/projects/3882/log</guid>
 *       <pubDate>Tue, 17 May 2011 14:34:43 +0000</pubDate>
 *       <title>Konrad Malawski has removed column 'New Name'</title>
 *       <description>Konrad Malawski has removed column 'New Name'</description>
 *     </item>
 * </pre>
 *
 * @author Konrad Malawski
 */
public class ProjectLogEntry {

  private RssResource.Guid guid;
  private DateTime pubDate;
  private String title;
  private String description;

  public ProjectLogEntry() {
  }

  public ProjectLogEntry(String title, String description, DateTime pubDate) {
    this.title = title;
    this.description = description;
    this.pubDate = pubDate;
  }

  public RssResource.Guid getGuid() {
    return guid;
  }

  public void setGuid(RssResource.Guid guid) {
    this.guid = guid;
  }

  public DateTime getPubDate() {
    return pubDate;
  }

  public void setPubDate(DateTime pubDate) {
    this.pubDate = pubDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static ProjectLogEntry from(RssResource.Item item) {
    ProjectLogEntry entry = new ProjectLogEntry();
    entry.description = item.getDescription();
    entry.pubDate = item.getPubDate();
    entry.title = item.getTitle();
    entry.guid = item.getGuid();

    return entry;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ProjectLogEntry that = (ProjectLogEntry) o;

    if (description != null ? !description.equals(that.description) : that.description != null) {
      return false;
    }
    if (guid != null ? !guid.equals(that.guid) : that.guid != null) {
      return false;
    }
    if (pubDate != null ? !pubDate.equals(that.pubDate) : that.pubDate != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = guid != null ? guid.hashCode() : 0;
    result = 31 * result + (pubDate != null ? pubDate.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ProjectLogEntry{" +
        "guid=" + guid +
        ", pubDate=" + pubDate +
        ", title='" + title + '\'' +
        '}';
  }
}
