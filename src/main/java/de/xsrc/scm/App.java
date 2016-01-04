package de.xsrc.scm;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Border;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Interactable;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.listener.WindowListener;
import com.googlecode.lanterna.input.Key;

import de.xsrc.scm.tui.HistoryWindow;
import de.xsrc.scm.tui.Theme;

/**
 * A simple git log viewer which also displays logs from submodules.
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class App {

  private static final Logger LOG = LoggerFactory.getLogger(App.class);

  public static void main(final String[] args) throws IOException {
    final GUIScreen textGUI = TerminalFacade.createGUIScreen();
    textGUI.setTheme(new Theme());

    try {
      final History history = new History(System.getProperty("user.dir"));
      final Window window = new HistoryWindow(history);
      window.setSoloWindow(true);
      window.setBorder(new Border.Bevel(false));
      window.addWindowListener(new WindowListener() {

        @Override
        public void onFocusChanged(final Window window, final Interactable fromComponent,
            final Interactable toComponent) {
          // TODO Auto-generated method stub

        }

        @Override
        public void onUnhandledKeyboardInteraction(final Window window, final Key key) {
          if (key != null) {
            System.out.println(key.toString());
            textGUI.getScreen().stopScreen();
            System.exit(0);
          }
        }

        @Override
        public void onWindowClosed(final Window window) {
          // TODO Auto-generated method stub

        }

        @Override
        public void onWindowInvalidated(final Window window) {
          // TODO Auto-generated method stub

        }

        @Override
        public void onWindowShown(final Window window) {
          // TODO Auto-generated method stub

        }
      });
      textGUI.getScreen().startScreen();

      textGUI.showWindow(window, GUIScreen.Position.FULL_SCREEN);
    } catch (final IOException e) {
      LOG.error("Current dir is not git dir");
      System.exit(-1);
    }
  }
}
