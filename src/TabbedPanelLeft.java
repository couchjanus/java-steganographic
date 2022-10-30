
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import javax.swing.event.*;


public class TabbedPanelLeft extends JPanel {
	static final long serialVersionUID = -1L; 
	JTabbedPane tabbedPaneLeft = new JTabbedPane();
	JTabbedPane tabbedPaneRight;
	
	public static ArrayList<PicturePanel> picturePanelList = new ArrayList<PicturePanel>();
	private static int index = 0;
	
	public static int getIndex() {
		return index;
	}
	
	public PicturePanel getPicturePanel() {
		return picturePanelList.get(index);
	}
	
	public TabbedPanelLeft(JTabbedPane tabbedPaneRight) {
		super();
		this.tabbedPaneRight = tabbedPaneRight;
		
		setLayout(new BorderLayout());
		
		PicturePanel tabPicture = new PicturePanel(tabbedPaneRight);
		picturePanelList.add(tabPicture);
				
		ToolBar toolBar = new ToolBar(tabbedPaneLeft, tabbedPaneRight);
		
		add(toolBar, BorderLayout.PAGE_START);
		
		String title = "New Picture 0";
		tabbedPaneLeft.addTab(title, tabPicture);
		
		index = tabbedPaneLeft.indexOfTab(title);
		tabbedPaneLeft.setSelectedIndex(index);
		
		tabbedPaneLeft.setTabComponentAt(index, new ButtonTabComponent(tabbedPaneLeft));
		
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				index = sourceTabbedPane.getSelectedIndex();
				System.out.println("New Tabbed index: " + index);
				
		        if(tabbedPaneLeft.getTabCount() == ImgList.images.size()) {
		        	Coords.setX1(0);
			        Coords.setY1(0);
					Coords.setX2(ImgList.width.get(index));
					Coords.setY2(ImgList.height.get(index));	
		        }
		        tabbedPaneRight.setSelectedIndex(0);
			}
			
		};
		tabbedPaneLeft.addChangeListener(changeListener);
		tabbedPaneLeft.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		add(tabbedPaneLeft, BorderLayout.CENTER);

		
	}

}
