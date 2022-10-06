import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;

public class ToolBar extends JToolBar{
	private JTabbedPane TPane;
	ArrayList<String> imageList;
	
	Action newAction = new AbstractAction("New Picture", null) {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			PicturePanel tabPanel = new PicturePanel(imageList);
			int tabCount = TPane.getTabCount();
			String title = "New Picture" + String.valueOf(tabCount);
			
			TPane.addTab(title, tabPanel);
			TPane.setSelectedIndex(TPane.indexOfTab(title));
			int index = TPane.indexOfTab(title);
			TPane.setTabComponentAt(index, new ButtonTabComponent(TPane, imageList));
		}
	};
	public ToolBar(JTabbedPane TPane, ArrayList<String> imageList) {
		super();
		this.TPane = TPane;
		this.imageList = imageList;
		add(Box.createHorizontalGlue());
		setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		add(newAction);
	}

}
