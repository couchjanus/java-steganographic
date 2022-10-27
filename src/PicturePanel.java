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
    private BufferedImage image;
    
    private ScrollPanel scrollPanel;
    SelectedRegion selectedAreaPanel;
    
    public SelectedRegion getSelectedRegion() {
    	return selectedAreaPanel;
    }
    
    public ScrollPanel getScrollPanel() {
    	return scrollPanel;
    }
    
	public PicturePanel(JTabbedPane tabbedPaneRight) {
		super();
		
		setLayout(new GridLayout(2,1));
		selectedAreaPanel = new SelectedRegion();
		
		selectedAreaPanel.setLayout(new BorderLayout());
		selectedAreaPanel.setBackground(Color.red);
		scrollPanel = new ScrollPanel(selectedAreaPanel, tabbedPaneRight);
		
		add(scrollPanel);
		add(selectedAreaPanel);
	}

}
