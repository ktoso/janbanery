package pl.project13.janbanery.resources.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * <pre>
 * <rss version="2.0" xmlns:dc="http://purl.org/dc/elements/1.1/">
 *   <channel>
 *     <title>Activity feed for project janbaneryProject</title>
 *     <link>https://janbanery.kanbanery.com/projects/3882/log.rss?api_token=7c8415b7fb37d287dcd74c44c6f055fa43d91f59</link>
 *     <description>Feed containing all recent activities in project janbaneryProject</description>
 *     <language>en-us</language>
 *     <item>
 *       <guid isPermaLink="false">https://janbanery.kanbanery.com/projects/3882/log</guid>
 *       <pubDate>Tue, 17 May 2011 14:34:43 +0000</pubDate>
 *       <title>Konrad Malawski has removed column 'New Name'</title>
 *       <description>Konrad Malawski has removed column 'New Name'</description>
 *     </item>
 *   </chanel>
 * </rss>
 * </pre>
 *
 * @author Konrad Malawski
 */
public class ProjectLog {

  private String title;
  private String link; // better -> url
  private String description;
  private String language; // better -> locale

  @XStreamImplicit(itemFieldName = "item")
  private List<ProjectLogEntry> items = newArrayList();

  public ProjectLog() {
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

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public List<ProjectLogEntry> getItems() {
    return items;
  }

  public void setItems(List<ProjectLogEntry> items) {
    this.items = items;
  }

  public static ProjectLog from(RssResource.Rss rss) {
    ProjectLog projectLog = new ProjectLog();

    projectLog.description = rss.getDescription();
    projectLog.link = rss.getLink();

    List<ProjectLogEntry> logItems = projectLog.getItems();
    for (RssResource.Item item : rss.getChannel().getItems()) {
      ProjectLogEntry entry = ProjectLogEntry.from(item);
      logItems.add(entry);
    }

    return projectLog;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ProjectLog that = (ProjectLog) o;

    if (description != null ? !description.equals(that.description) : that.description != null) {
      return false;
    }
    if (language != null ? !language.equals(that.language) : that.language != null) {
      return false;
    }
    if (link != null ? !link.equals(that.link) : that.link != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (link != null ? link.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (language != null ? language.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ProjectLog{" +
        "title='" + title + '\'' +
        ", link='" + link + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}

