
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyboardFocusManager;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import javax.accessibility.Accessible;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.TransferHandler;


class TransferActionListener implements ActionListener, PropertyChangeListener {
  private JComponent focusOwner = null;

  public TransferActionListener() {
    KeyboardFocusManager manager = KeyboardFocusManager
        .getCurrentKeyboardFocusManager();
    manager.addPropertyChangeListener("permanentFocusOwner", this);
  }

  public void propertyChange(PropertyChangeEvent e) {
    Object o = e.getNewValue();
    if (o instanceof JComponent) {
      focusOwner = (JComponent) o;
    } else {
      focusOwner = null;
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (focusOwner == null)
      return;
    String action = (String) e.getActionCommand();
    Action a = focusOwner.getActionMap().get(action);
    if (a != null) {
      a.actionPerformed(new ActionEvent(focusOwner,
          ActionEvent.ACTION_PERFORMED, null));
    }
  }
}
