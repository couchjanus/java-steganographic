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
    private final Coords coords;
    
    private ScrollPanel scrollPanel;
    
	public PicturePanel(ArrayList<String> imageList, Coords coords) {
		super();
		this.imageList = imageList;
		this.coords = coords;
		
		setLayout(new GridLayout(2,1));
		SelectedRegion selectedAreaPanel = new SelectedRegion();
		
		selectedAreaPanel.setLayout(new BorderLayout());
		selectedAreaPanel.setBackground(Color.red);
		scrollPanel = new ScrollPanel(imageList, selectedAreaPanel, coords);
		
		add(scrollPanel);
		add(selectedAreaPanel);
	}

}
