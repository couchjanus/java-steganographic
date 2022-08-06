
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelLeft extends JPanel {
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public TabbedPanelLeft() {
		super();
		setLayout(new BorderLayout());
		TabToolsPanel topPanel = new TabToolsPanel(tabbedPane);
		
		PicturePanel tabPicture = new PicturePanel();
		add(topPanel, BorderLayout.NORTH);
		
		tabbedPane.addTab("Open Picture", tabPicture);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		add(tabbedPane);
		    
	}

}
