import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelRight extends JPanel {
	
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public TabbedPanelRight() {
	  super();
	  setLayout(new BorderLayout());
      JComponent panel1 = makeTextPanel("Panel #1");
      tabbedPane.addTab("Tab 1", null, panel1,
              "Does nothing");
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
       
      JComponent panel2 = makeTextPanel("Panel #2");
      tabbedPane.addTab("Tab 2", null, panel2,
              "Does twice as much nothing");
      tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
       
      JComponent panel3 = makeTextPanel("Panel #3");
      tabbedPane.addTab("Tab 3", null, panel3,
              "Still does nothing");
      tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
       
      JComponent panel4 = makeTextPanel(
              "Panel #4 (has a preferred size of 410 x 50).");
      panel4.setPreferredSize(new Dimension(410, 50));
      tabbedPane.addTab("Tab 4", null, panel4,
              "Does nothing at all");
      tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
      tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      
      add(tabbedPane);
	}
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
}
