import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

public class ToolBar extends JToolBar{
	private JTabbedPane TPane;
	JTabbedPane tabbedPaneRight;
	private PicturePanel currentTabPicturel;
	
	public PicturePanel getCurrentTabPicturel(){
		return currentTabPicturel;
	}
	
	Action newAction = new AbstractAction("New Picture", null) {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			currentTabPicturel = new PicturePanel(tabbedPaneRight);
//			currentTabPicture = tabPanel;
			int tabCount = TPane.getTabCount();
			String title = "New Picture" + String.valueOf(tabCount);
			
			TPane.addTab(title, currentTabPicturel);
			Coords.setX1(0);
			Coords.setX2(0);
			Coords.setY1(0);
			Coords.setY2(0);
			TPane.setSelectedIndex(TPane.indexOfTab(title));
			int index = TPane.indexOfTab(title);
			TPane.setTabComponentAt(index, new ButtonTabComponent(TPane));
		}
	};
	public ToolBar(JTabbedPane TPane, JTabbedPane tabbedPaneRight) {
		super();
		this.TPane = TPane;
		this.tabbedPaneRight = tabbedPaneRight;
//		this.currentTabPicturel = currentTabPicturel;
		
		add(Box.createHorizontalGlue());
		setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		add(newAction);
	}

}
