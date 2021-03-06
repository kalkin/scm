package de.xsrc.scm;

//Migrate to lanter3
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.WindowListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import de.xsrc.scm.tui.HistoryWindow;

/**
 * A simple git log viewer which also displays logs from submodules.
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class App {

    private static final class MyWindowListener implements WindowListener {
	private final TerminalScreen screen;

	MyWindowListener(TerminalScreen screen) {
	    this.screen = screen;
	}

	@Override
	public void onInput(Window basePane, KeyStroke keyStroke, AtomicBoolean deliverEvent) {
	    LOG.debug(keyStroke.toString());
	    if (keyStroke.getKeyType() == KeyType.Character) {
		switch (keyStroke.getCharacter().charValue()) {
		case 'q':
		    try {
			this.screen.stopScreen();
		    } catch (IOException e) {
			e.printStackTrace();
			System.out.println("IO Error on exit. Why?");
		    }
		    System.exit(0);
		    break;

		default:
		    break;
		}

	    }
	}

	@Override
	public void onUnhandledInput(Window basePane, KeyStroke keyStroke, AtomicBoolean hasBeenHandled) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onResized(Window window, TerminalSize oldSize, TerminalSize newSize) {
	    // TODO Auto-generated method stub

	}

	@Override
	public void onMoved(Window window, TerminalPosition oldPosition, TerminalPosition newPosition) {
	    // TODO Auto-generated method stub

	}
    }

    static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static void main(final String[] args) throws IOException {
	final History history = new History(System.getProperty("user.dir"));

	final Window window = new HistoryWindow(history);
	final List<Window.Hint> hints = Arrays.asList(Window.Hint.FULL_SCREEN);
	window.setHints(hints);

	final DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();

	try (final Terminal terminal = terminalFactory.createTerminal();
		final TerminalScreen screen = new TerminalScreen(terminal)) {
	    WindowBasedTextGUI gui = new MultiWindowTextGUI(screen);

	    window.addWindowListener(new MyWindowListener(screen));
	    screen.startScreen();

	    gui.addWindow(window);
	    window.waitUntilClosed();
	    screen.close();
	    terminal.close();
	}

	System.exit(1);
    }
}
