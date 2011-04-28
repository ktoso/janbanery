package pl.project13.janbanery.entity2wiki;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * @author Konrad Malawski
 */
public class GitHubWikiPublisher {

  Git git;

  public static void main(String... args) throws Exception {
    validateArgs(args);

    int i = 0;
    String cloneUrl = args[i++];
    String pageName = args[i++];
    String sourceRootLocationString = args[i++];

    File tempFile = File.createTempFile("gitHubWikiPublisher", ".md.tmp");
    PrintStream out = new PrintStream(tempFile);

    File sourceRootLocation = new File(sourceRootLocationString);
    Entity2WikiRunner entity2Wiki = new Entity2WikiRunner(sourceRootLocation);

    entity2Wiki.withClasses(DocumentedClasses.resources()).writeSection(out, "Entities", new NotReadOnlyClassPredicate());
    entity2Wiki.withClasses(DocumentedClasses.resources()).writeSection(out, "ReadOnly Entities", new ReadOnlyClassPredicate());
    entity2Wiki.withClasses(DocumentedClasses.enums()).writeSection(out, "Enums");

    String newContent = Joiner.on('\n').join(Files.readLines(tempFile, Charsets.UTF_8));

    new GitHubWikiPublisher(cloneUrl).pushToWiki(pageName, newContent);
  }

  private static void validateArgs(String[] args) {
    if (args.length != 3) {
      System.out.println("You must pass in:");
      System.out.println("1) cloneUrl");
      System.out.println("2) pageName");
      System.out.println("3) sourceRootLocation");
      System.exit(1);
    }
  }

  public GitHubWikiPublisher(String wikiRepositoryUrl) throws IOException, InvalidRemoteException, InvalidRefNameException, RefNotFoundException, RefAlreadyExistsException {
    cloneRepository(wikiRepositoryUrl);
    checkoutRemoteMaster();
  }

  private void cloneRepository(String wikiRepositoryUrl) {
    File tempDir = Files.createTempDir();

    git = Git.cloneRepository()
             .setDirectory(tempDir)
             .setURI(wikiRepositoryUrl)
             .call();
  }

  private void checkoutRemoteMaster() throws IOException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException {
    ObjectId masterObjectId = git.getRepository().getRef("remotes/origin/master").getObjectId();
    RevWalk walk = new RevWalk(git.getRepository());
    RevCommit commit = walk.parseCommit(masterObjectId);

    git.checkout()
       .setCreateBranch(true)
        .setStartPoint(commit)
       .setName("master")
       .call();
  }

  public void pushToWiki(String pageName, String contents) throws IOException,
      NoFilepatternException, NoHeadException, NoMessageException,
      ConcurrentRefUpdateException, WrongRepositoryStateException, InvalidRemoteException {

    addNewContent(pageName, contents);
    commitNewContent();
    pushNewContent();
  }

  private void pushNewContent() throws InvalidRemoteException {
    git.push().call();
  }

  private void commitNewContent() throws NoHeadException, NoMessageException, UnmergedPathException, ConcurrentRefUpdateException, WrongRepositoryStateException {
    git.commit()
       .setAuthor("Git Hub Wiki Publisher", "konrad.malawski@java.pl")
       .setCommitter("Git Hub Wiki Publisher", "konrad.malawski@java.pl")
       .setMessage("Automatic documentation update")
       .call();
  }

  private void addNewContent(String pageName, String contents) throws IOException, NoFilepatternException {
    File workTree = git.getRepository().getWorkTree();
    String fileName = pageName + ".md";
    File pageFile = new File(workTree, fileName);

    Files.touch(pageFile);

    Files.write(contents, pageFile, Charsets.UTF_8);

    git.add().addFilepattern(fileName).call();
  }

}
