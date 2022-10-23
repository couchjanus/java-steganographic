
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.event.*;


public class TabbedPanelLeft extends JPanel {
	JTabbedPane tabbedPane = new JTabbedPane();
	
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
	
	public TabbedPanelLeft() {
		super();
		
		setLayout(new BorderLayout());

		ToolBar toolBar = new ToolBar(tabbedPane);
		add(toolBar, BorderLayout.PAGE_START);
		
		tabPicture = new PicturePanel();
		
		String title = "New Picture 0";
		tabbedPane.addTab(title, tabPicture);
		index = tabbedPane.indexOfTab(title);
		tabbedPane.setSelectedIndex(index);
		
		tabbedPane.setTabComponentAt(index, new ButtonTabComponent(tabbedPane));
		
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				index = sourceTabbedPane.getSelectedIndex();
				System.out.println("New Tabbed index: " + index);
				
				BufferedImage image = ImageUtils.loadImage(ImgList.images.get(index));
		        
		        Coords.setX1(0);
				Coords.setX2(image.getWidth());
				Coords.setY1(0);
				Coords.setY2(image.getHeight());
				
//			

			}
		};
		tabbedPane.addChangeListener(changeListener);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		add(tabbedPane, BorderLayout.CENTER);

		
	}

}
