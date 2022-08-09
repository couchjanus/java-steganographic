
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelLeft extends JPanel {
	JTabbedPane tabbedPane = new JTabbedPane();
	
	public TabbedPanelLeft() {
		super();
		setLayout(new BorderLayout());
//		TabToolsPanel topPanel = new TabToolsPanel(tabbedPane);
		ToolBar toolBar = new ToolBar(tabbedPane);
		
		PicturePanel tabPicture = new PicturePanel();
		add(toolBar, BorderLayout.PAGE_START);
		
		
		String title = "New Picture 0";
		tabbedPane.addTab(title, tabPicture);
		int index = tabbedPane.indexOfTab(title);
		tabbedPane.setSelectedIndex(index);
		
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		add(tabbedPane, BorderLayout.CENTER);
		    
	}

}
