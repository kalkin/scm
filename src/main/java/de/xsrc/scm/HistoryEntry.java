package de.xsrc.scm;

import java.util.Objects;

import org.eclipse.jgit.revwalk.RevCommit;

/**
 * A HistoryEntry is just the short git message and a level. The level is 0 if this is a root
 * projects history entry. If this history entry belongs to a submodule used by the root project the
 * level would be 1. If the submodule would have a nested submodule, the level of the nested one
 * would be 2, and so on...
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class HistoryEntry {
  private final RevCommit commit;

  private int level = 0;

  protected HistoryEntry(final RevCommit commit) {
    this.commit = commit;
  }

  protected HistoryEntry(final RevCommit commit, final int level) {
    Objects.requireNonNull(commit);
    Objects.requireNonNull(level);
    this.commit = commit;
    this.level = level;
  }

  public int getDate() {
    return this.commit.getCommitTime();
  }

  /**
   * @return the submodule level
   */
  public int getLevel() {
    return this.level;
  }

  /**
   * @return git short message
   */
  public String getMessage() {
    return this.commit.getShortMessage();
  }


  public void setLevel(final int level) {
    this.level = level;
  }
}
