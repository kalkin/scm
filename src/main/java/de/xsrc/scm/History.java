package de.xsrc.scm;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

/**
 * This is an {@link Iterable} which is used by the UI for iterating over {@link HistoryEntry}s
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class History implements Iterable<HistoryEntry> {

  private final Iterator<HistoryEntry> iterator;

  /**
   * Generate History from a given git project path
   *
   * @param uri local or remote path to a git repo
   * @throws IOException
   */
  public History(final String uri) throws IOException {
    final Git git = Git.open(new File(uri));
    final Repository repo = git.getRepository();
    this.iterator = new HistoryIterator(repo);

  }

  @Override
  public Iterator<HistoryEntry> iterator() {
    return this.iterator;
  }

}
