import java.awt.Color;
import java.awt.event.ActionEvent;
//import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

public class ToolBar extends JToolBar{
	static final long serialVersionUID = -1L;
	
	private JTabbedPane TPane;
	JTabbedPane tabbedPaneRight;
	private PicturePanel tabPicture;
	
	Action newAction = new AbstractAction("New Picture", null) {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e)
		{
			tabPicture = new PicturePanel(tabbedPaneRight);
			
			TabbedPanelLeft.picturePanelList.add(tabPicture);

			int tabCount = TPane.getTabCount();
			String title = "New Picture" + String.valueOf(tabCount);
			
			TPane.addTab(title, tabPicture);
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
		add(Box.createHorizontalGlue());
		setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		add(newAction);
	}

}
