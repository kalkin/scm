/**
 *
 */
package de.xsrc.scm.tui;

import com.googlecode.lanterna.terminal.Terminal.Color;

/**
 * This is our default Theme.
 *
 * @author Bahtiar `kalkin-` Gadimov <bahtiar@gadimov.de>
 *
 */
public class Theme extends com.googlecode.lanterna.gui.Theme {
  private static final Definition DEFAULT = new Definition(Color.WHITE, Color.DEFAULT, false);
  private static final Definition SELECTED = new Definition(Color.WHITE, Color.BLUE, false, true);

  public Theme() {
    setDefinition(Category.DIALOG_AREA, DEFAULT);
    setDefinition(Category.SCREEN_BACKGROUND, new Definition(Color.CYAN, Color.BLUE, true));
    setDefinition(Category.SHADOW, new Definition(Color.BLACK, Color.BLACK, true));
    setDefinition(Category.BORDER, new Definition(Color.BLACK, Color.WHITE, true));
    setDefinition(Category.RAISED_BORDER, new Definition(Color.WHITE, Color.WHITE, true));
    setDefinition(Category.BUTTON_LABEL_ACTIVE, new Definition(Color.YELLOW, Color.BLUE, true));
    setDefinition(Category.BUTTON_LABEL_INACTIVE, new Definition(Color.BLACK, Color.WHITE, true));
    setDefinition(Category.BUTTON_ACTIVE, SELECTED);
    setDefinition(Category.BUTTON_INACTIVE, DEFAULT);
    setDefinition(Category.LIST_ITEM, DEFAULT);
    setDefinition(Category.LIST_ITEM_SELECTED, SELECTED);
    setDefinition(Category.CHECKBOX, DEFAULT);
    setDefinition(Category.CHECKBOX_SELECTED, SELECTED);
    setDefinition(Category.TEXTBOX, SELECTED);
    setDefinition(Category.TEXTBOX_FOCUSED,
        new Definition(Color.WHITE, Color.BLACK, true));
    setDefinition(Category.PROGRESS_BAR_COMPLETED, new Definition(Color.GREEN, Color.BLACK, false));
    setDefinition(Category.PROGRESS_BAR_REMAINING, new Definition(Color.RED, Color.BLACK, false));
  }
}
