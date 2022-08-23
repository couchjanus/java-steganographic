import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TabbedPanelRight extends JPanel {
	
	JTabbedPane tabbedPane = new JTabbedPane();
	ArrayList<String> imageList;
	
	public TabbedPanelRight(ArrayList<String> imageList) {
	  super();
	  setLayout(new BorderLayout());
	  this.imageList = imageList;
	  
	  RightToolbar toolBar = new RightToolbar(tabbedPane, imageList);
	  
	  add(toolBar, BorderLayout.PAGE_START);
	  
      JComponent panel1 = makeTextPanel("Main");
      tabbedPane.addTab("Main", null, panel1,
              "Does nothing");
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
       
      JComponent panel2 = makeTextPanel("Histogram");
      tabbedPane.addTab("Histogram", null, panel2,
              "Does Histogram as much nothing");
      tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
       
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
