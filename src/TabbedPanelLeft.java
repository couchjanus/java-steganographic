
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.event.*;


public class TabbedPanelLeft extends JPanel {
	static final long serialVersionUID = -1L; 
	JTabbedPane tabbedPaneLeft = new JTabbedPane();
	
	private static int index;
	PicturePanel tabPicture;
	
	public static int getIndex() {
		return index;
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

		ToolBar toolBar = new ToolBar(tabbedPaneLeft, tabbedPaneRight);
		add(toolBar, BorderLayout.PAGE_START);
		
		tabPicture = new PicturePanel(tabbedPaneRight);
		
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
				
//				BufferedImage image = ImageUtils.loadImage(ImgList.images.get(index));
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
