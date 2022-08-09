import java.awt.*;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicturePanel extends JPanel {
	private JFileChooser fc;
    static final private String ATTACH = "attach";
    private JPanel imagePanel, topPanel, bottomPanel;
    
	public PicturePanel() {
		super();
		setLayout(new BorderLayout());
		addControls();
	}
	
	private void addControls()
	{
		topPanel = new JPanel();
		imagePanel = new JPanel();
		
		JLabel label = new JLabel("Find What:");
		JButton findButton = new JButton("Find");
		
		findButton.setActionCommand(ATTACH);
        findButton.setToolTipText("Open file");
       
        findButton.addActionListener(new ActionListener() {
        	 @Override
        	 public void actionPerformed(ActionEvent e) {
        		String cmd = e.getActionCommand();
        			
        		if (ATTACH.equals(cmd)) {
        	        if (fc == null) {
        	            fc = new JFileChooser();
        	            fc.addChoosableFileFilter(new ImageFilter());
        	            fc.setAcceptAllFileFilterUsed(false);
        	            fc.setFileView(new ImageFileView());
        	            fc.setAccessory(new ImagePreview(fc));
        	        }
        	        int returnVal = fc.showDialog(PicturePanel.this,
        	                "Attach");
        			if (returnVal == JFileChooser.APPROVE_OPTION) 
        			{
        				File file = fc.getSelectedFile();
        				
        				try {
        					imagePanel.removeAll();
        					BufferedImage img =	ImageScale.loadImage(file);
        					ImageScale.drawRectangle(img);
        					JLabel picLabel = ImageScale.drawImage(img);
        					imagePanel.setBackground(Color.yellow);
        					imagePanel.add(picLabel);
        					imagePanel.revalidate();
        					imagePanel.repaint();
        				} catch(IOException ex) {
        					ex.printStackTrace();
        				}
        			}
        		}
        	 }
        });
        
        topPanel.add(label);
        topPanel.add(findButton);
        add(topPanel, BorderLayout.PAGE_START);
        add(imagePanel, BorderLayout.PAGE_END);
    }

}
