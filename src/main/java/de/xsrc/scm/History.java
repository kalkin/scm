package de.xsrc.scm;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * This is an {@link Iterable} which is used by the UI for iterating over {@link HistoryEntry}s
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class History implements Iterable<HistoryEntry> {

  private final class HistoryIterator implements Iterator<HistoryEntry> {

    private final Iterator<RevCommit> iterator;

    public HistoryIterator(final RevWalk walker) {
      this.iterator = walker.iterator();
    }

    @Override
    public boolean hasNext() {
      return this.iterator.hasNext();
    }

    @Override
    public HistoryEntry next() {
      return new HistoryEntry(this.iterator.next());
    }

  }

  private final RevWalk walker;

  /**
   * Generate History from a given git project path
   *
   * @param uri local or remote path to a git repo
   * @throws IOException
   */
  public History(final String uri) throws IOException {
    final Git git = Git.open(new File(uri));
    final Repository repo = git.getRepository();
    this.walker = new RevWalk(repo);
    this.walker.markStart(this.walker.parseCommit(repo.resolve("HEAD")));
  }

  @Override
  public Iterator<HistoryEntry> iterator() {
    return new HistoryIterator(this.walker);
  }

}
