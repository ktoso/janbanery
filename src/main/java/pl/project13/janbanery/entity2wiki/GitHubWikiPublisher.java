/*
 * Copyright 2011 Konrad Malawski <konrad.malawski@project13.pl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.project13.janbanery.entity2wiki;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.*;
import org.eclipse.jgit.errors.UnmergedPathException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

/**
 * @author Konrad Malawski
 */
public class GitHubWikiPublisher {

  Logger log = LoggerFactory.getLogger(getClass());

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
    log.info("Cloning repository: {}", wikiRepositoryUrl);
    File tempDir = Files.createTempDir();

    git = Git.cloneRepository()
             .setDirectory(tempDir)
             .setURI(wikiRepositoryUrl)
             .call();

    log.info("Done cloning repository.");
  }

  private void checkoutRemoteMaster() throws IOException, RefAlreadyExistsException, RefNotFoundException, InvalidRefNameException {
    log.info("Checking out remotes/origin/master -> master branch");
    ObjectId masterObjectId = git.getRepository().getRef("remotes/origin/master").getObjectId();
    RevWalk walk = new RevWalk(git.getRepository());
    RevCommit commit = walk.parseCommit(masterObjectId);

    git.checkout()
       .setCreateBranch(true)
       .setStartPoint(commit)
       .setName("master")
       .call();

    log.info("Done checking out branch.");
  }

  public void pushToWiki(String pageName, String contents) throws IOException,
      NoFilepatternException, NoHeadException, NoMessageException,
      ConcurrentRefUpdateException, WrongRepositoryStateException, InvalidRemoteException {

    addNewContent(pageName, contents);
    commitNewContent();
    pushNewContent();
    cleanupWorkDir();

    log.info("Done pushing new contents to github wiki.");
  }

  private void cleanupWorkDir() throws IOException {
    log.info("Cleaning up work directory...");

    File workTree = git.getRepository().getWorkTree();
    Files.deleteRecursively(workTree);
  }

  private void pushNewContent() throws InvalidRemoteException {
    log.info("Pushing changes to remote...");

    git.push().call();
  }

  private void commitNewContent() throws NoHeadException, NoMessageException, UnmergedPathException, ConcurrentRefUpdateException, WrongRepositoryStateException {
    log.info("Committing new data...");

    String name = "Git Hub Wiki Publisher";
    String email = "konrad.malawski@java.pl";
    git.commit()
       .setAuthor(name, email)
       .setCommitter(name, email)
       .setMessage("Automatic documentation update")
       .call();
  }

  private void addNewContent(String pageName, String contents) throws IOException, NoFilepatternException {
    log.info("Adding new content to files...");

    File workTree = git.getRepository().getWorkTree();
    String fileName = pageName + ".md";
    File pageFile = new File(workTree, fileName);

    Files.touch(pageFile);

    Files.write(contents, pageFile, Charsets.UTF_8);

    log.info("Running git add {}", fileName);
    git.add().addFilepattern(fileName).call();
  }

}
