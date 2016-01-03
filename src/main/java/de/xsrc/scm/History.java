package de.xsrc.scm;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  private static final Logger LOG = LoggerFactory.getLogger(History.class);

  private final RevWalk walker;

  public History(final Repository repo) {
    this.walker = new RevWalk(repo);
    try {
      this.walker.markStart(this.walker.parseCommit(repo.resolve("HEAD")));
    } catch (RevisionSyntaxException | IOException e) {
      LOG.error("RevWalk could not mark start " + repo.getDirectory().getAbsolutePath());
      e.printStackTrace();
    }
  }

  /**
   * Generate History from a given git project path
   *
   * @param uri local or remote path to a git repo
   * @throws IOException
   */
  public History(final String uri) throws IOException {
    this(Git.open(new File(uri)).getRepository());
  }

  @Override
  public Iterator<HistoryEntry> iterator() {
    return new HistoryIterator(this.walker);
  }

}
