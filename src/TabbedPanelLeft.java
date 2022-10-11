
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;


public class TabbedPanelLeft extends JPanel {
	JTabbedPane tabbedPane = new JTabbedPane();
	ArrayList<String> imageList;
	private final Coords coords;
	private static int index;
	
	public static int getIndex() {
		return index;
	}
	public TabbedPanelLeft(ArrayList<String> imageList, Coords coords) {
		super();
		this.coords = coords;
		this.imageList = imageList;
		setLayout(new BorderLayout());
//		TabToolsPanel topPanel = new TabToolsPanel(tabbedPane);
		ToolBar toolBar = new ToolBar(tabbedPane, imageList, coords);
		add(toolBar, BorderLayout.PAGE_START);
		
		PicturePanel tabPicture = new PicturePanel(imageList, coords);
		
		String title = "New Picture 0";
		tabbedPane.addTab(title, tabPicture);
		index = tabbedPane.indexOfTab(title);
		tabbedPane.setSelectedIndex(index);
		
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane, imageList));
		
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				index = sourceTabbedPane.getSelectedIndex();
				System.out.println("Tabbed index: " + index);
				System.out.println("imageList: " + imageList);
			}
		};
		tabbedPane.addChangeListener(changeListener);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		add(tabbedPane, BorderLayout.CENTER);
//		if(imageList.size() != 0) {
//			System.out.println(imageList.get(index));
//		}
		
	}

}
