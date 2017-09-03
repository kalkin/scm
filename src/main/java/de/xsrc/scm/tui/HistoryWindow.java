package de.xsrc.scm.tui;

import java.util.Objects;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.BasicWindow;

import de.xsrc.scm.History;
import de.xsrc.scm.HistoryEntry;

/**
 * This window list the HistoryEntries similar to tig. It uses the
 * {@link com.googlecode.lanterna.gui2.BasicWindow} to display contents.
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class HistoryWindow extends BasicWindow {

    private final History history;

    public HistoryWindow(final History history) {
	super("History");
	Objects.requireNonNull(history);
	this.history = history;
	final TerminalSize terminalSize = getDecoratedSize();
	int maxRows = 300;
	final ActionListBox actionListBox = new ActionListBox(terminalSize);
	for (final HistoryEntry entry : this.history) {
	    if (maxRows <= 0) {
		break;
	    }
	    final int level = 1;
	    final String logmsg = String.format("%" + level + "s", entry.getMessage());
	    actionListBox.addItem(logmsg, (Runnable) () -> {});
	    maxRows--;
	}
	this.setComponent(actionListBox);
    }

}
