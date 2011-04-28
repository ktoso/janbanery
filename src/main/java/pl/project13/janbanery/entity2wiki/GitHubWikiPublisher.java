package pl.project13.janbanery.entity2wiki;

import com.google.common.io.Files;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RepositoryBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * @author Konrad Malawski
 */
public class GitHubWikiPublisher {

  Git git;

  public static void main(String... args) throws IOException, InvalidRemoteException {
    if(args.length != 1){
      System.out.println("You must pass in the clone URL of your GitHub wiki.");
      System.exit(1);
    }

    String cloneUrl = args[0];
    new GitHubWikiPublisher(cloneUrl).pushToWiki("Entities", "Blabla");
  }

  public GitHubWikiPublisher(String wikiRepositoryUrl) throws IOException, InvalidRemoteException {
    File tempDir = Files.createTempDir();

    git = Git.init().setDirectory(tempDir).call();
    Collection<Ref> advertisedRefs = git.fetch().setRemote(wikiRepositoryUrl).call().getAdvertisedRefs();
    for (Ref advertisedRef : advertisedRefs) {
      System.out.println(advertisedRef);
    }
  }

  public boolean pushToWiki(String pageName, String contents) throws IOException {
    return false;
  }


}
