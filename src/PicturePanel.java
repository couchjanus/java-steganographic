import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.*;

public class PicturePanel extends JPanel {
	static final long serialVersionUID = -1L;

    ArrayList<String> imageList;
    private BufferedImage image;
 
    private ScrollPanel scrollPanel;
    
	public PicturePanel(ArrayList<String> imageList) {
		super();
		this.imageList = imageList;

		setLayout(new GridLayout(2,1));
		SelectedRegion selectedAreaPanel = new SelectedRegion();
		
		selectedAreaPanel.setLayout(new BorderLayout());
		selectedAreaPanel.setBackground(Color.red);
		scrollPanel = new ScrollPanel(imageList, selectedAreaPanel);
		
		add(scrollPanel);
		add(selectedAreaPanel);
	}

}
