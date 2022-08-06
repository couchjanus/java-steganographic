
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

public class TabToolsPanel extends JPanel{
	private JTabbedPane TPane;
	
	public TabToolsPanel(JTabbedPane TPane) {
		super();
		this.TPane = TPane;
		setLayout(new FlowLayout());
		addControls();
	}
	
	private void addControls()
	{
		JButton btnAdd = new JButton("New Tab");
		add(btnAdd);
		
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				PicturePanel tabPanel = new PicturePanel();
				TPane.addTab("New Picture", tabPanel);
			}
		});
	}

}
