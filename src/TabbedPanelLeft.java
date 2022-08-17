
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class TabbedPanelLeft extends JPanel {
	JTabbedPane tabbedPane = new JTabbedPane();
	ArrayList<String> imageList;
	
	public TabbedPanelLeft(ArrayList<String> imageList) {
		super();
		this.imageList = imageList;
		setLayout(new BorderLayout());
//		TabToolsPanel topPanel = new TabToolsPanel(tabbedPane);
		ToolBar toolBar = new ToolBar(tabbedPane, imageList);
		add(toolBar, BorderLayout.PAGE_START);
		
		PicturePanel tabPicture = new PicturePanel(imageList);
		
		
		
		String title = "New Picture 0";
		tabbedPane.addTab(title, tabPicture);
		int index = tabbedPane.indexOfTab(title);
		tabbedPane.setSelectedIndex(index);
		
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		add(tabbedPane, BorderLayout.CENTER);
		if(imageList.size() != 0) {
			System.out.println(imageList.get(index));
		}
		
	}

}
