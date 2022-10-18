import java.awt.Color;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import javax.swing.Box;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.LineBorder;

public class RightToolbar extends JToolBar {
	
	JTabbedPane TPane;
	public BufferedImage image;
	
	public RightToolbar(JTabbedPane TPane) {
		super();
		this.TPane = TPane;
		
		add(Box.createHorizontalGlue());
		setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		add(histAction);
		
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public void loadImage(String fileName) {
		try {
			File file = new File(fileName);
			image = ImageIO.read(file);	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	Action histAction = new AbstractAction("Histogram", null) {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			loadImage(ImgList.images.get(TabbedPanelLeft.getIndex()));
			BufferedImage img = getImage();
			
			Histogram hist = new Histogram(img);	
			
			JInternalFrame frame = new JInternalFrame();
			Container container = frame.getContentPane();
			container.add(hist.histpanel, BorderLayout.CENTER);
			
			frame.pack();
			frame.setResizable(false);
			frame.setMaximizable(false);
			frame.setVisible(true);
			
			int index = TPane.indexOfTab("Histogram");
			TPane.removeTabAt(index);
			TPane.addTab("Histogram", frame);
			
			try {
				frame.setSelected(true);
			} catch(java.beans.PropertyVetoException s) {}
			
		}
	};

}
