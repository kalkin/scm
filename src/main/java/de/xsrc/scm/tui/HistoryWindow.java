package de.xsrc.scm.tui;

import java.util.Objects;

import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.ActionListBox;
import com.googlecode.lanterna.terminal.TerminalSize;

import de.xsrc.scm.History;
import de.xsrc.scm.HistoryEntry;

/**
 * This window list the HistoryEntries similar to tig. It uses the
 * {@link com.googlecode.lanterna.gui.Window} to display contents.
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class HistoryWindow extends Window {

  private final History history;

  public HistoryWindow(final History history) {
    super("History");
    Objects.requireNonNull(history);
    this.history = history;
  }

  /**
   * When visible figure out how many rows we have and fill them with content
   *
   * @see com.googlecode.lanterna.gui.Window#onVisible()
   */
  @Override
  protected void onVisible() {
    final TerminalSize terminalSize = getOwner().getScreen().getTerminalSize();
    int maxRows = 300;
    final ActionListBox actionListBox = new ActionListBox(terminalSize);
    for (final HistoryEntry entry : this.history) {
      if (maxRows <= 0) {
        break;
      } else {
        final int level = 1;
        final String logmsg = String.format("%" + level + "s", entry.getMessage());
        actionListBox.addAction(logmsg, () -> System.out.println(logmsg));
        maxRows--;
      }
    }
    addComponent(actionListBox);
  }
}
