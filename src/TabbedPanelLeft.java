
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.event.*;


public class TabbedPanelLeft extends JPanel {
	static final long serialVersionUID = -1L; 
	JTabbedPane tabbedPaneLeft = new JTabbedPane();
	
	private static int index;
	PicturePanel currentTabPicture;
	
	PicturePanel tabPicture;
	
	public static int getIndex() {
		return index;
	}
	
	public PicturePanel getCurrentTabPicture() {
		return currentTabPicture;
	}
	
	public PicturePanel getPicturePanel() {
		return tabPicture;
	}
	
	
	public TabbedPanelLeft getTabbedPane() {
		return this;
	}
	
	JTabbedPane tabbedPaneRight;
	
	public TabbedPanelLeft(JTabbedPane tabbedPaneRight) {
		super();
		this.tabbedPaneRight = tabbedPaneRight;
		
		setLayout(new BorderLayout());
		
		tabPicture = new PicturePanel(tabbedPaneRight);
		
//		currentTabPicture = new PicturePanel(tabbedPaneRight);
		
//		currentTabPicture = tabPicture;
		
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
				System.out.println("getScrollPanel(): " + tabPicture.getScrollPanel().getPictureScrollPane());
//				BufferedImage image = ImageUtils.loadImage(ImgList.images.get(index));
//				System.out.println("Tabbed left: " + tabbedPaneLeft.getComponentAt(index));
		        if(tabbedPaneLeft.getTabCount() == ImgList.images.size()) {
		        	Coords.setX1(0);
			        Coords.setY1(0);
					Coords.setX2(ImgList.width.get(index));
					Coords.setY2(ImgList.height.get(index));	
					
					currentTabPicture = toolBar.getCurrentTabPicturel();
		        }
		        tabbedPaneRight.setSelectedIndex(0);
			}
			
		};
		tabbedPaneLeft.addChangeListener(changeListener);
		tabbedPaneLeft.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		add(tabbedPaneLeft, BorderLayout.CENTER);

		
	}

}
