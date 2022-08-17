import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PicturePanel extends JPanel {
	private JFileChooser fc;
    static final private String ATTACH = "attach";
    private JPanel imagePanel, topPanel, bottomPanel;
    ArrayList<String> imageList;
    private int w, h;
    
	public PicturePanel(ArrayList<String> imageList) {
		super();
		this.imageList = imageList;
		setLayout(new BorderLayout());
		addControls();
	}
	
	private void putPicture(File file) {
		removeAll();
		w = getSize().width;
		h = getSize().height;
	
		ImageScale image = new ImageScale(file);
		JLabel picLabel = image.drawImage(w, h);
		
		setBackground(Color.yellow);
		add(picLabel);
		revalidate();
		repaint();
	}
	
	private void addControls()
	{

		Border redline = BorderFactory.createLineBorder(Color.RED);
		
		JLabel label = new JLabel("Drag Abd Drop File HERE");
		label.setBorder(redline);
		label.setBounds(getVisibleRect());
		
		JButton findButton = new JButton("Brouse Image File");
		
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
        				putPicture(file);
        				imageList.add(file.getPath());
        				System.out.println(imageList);
        			}
        		}
        	 }
        });
        
        
        new FileDrop(System.out, label, new FileDrop.Listener() {
			
			@Override
			public void filesDropped(File[] files) {
				try {
					putPicture(new File(files[0].getCanonicalPath()));
					imageList.add(files[0].getCanonicalPath());
    				System.out.println(imageList);
				}catch(java.io.IOException e) {
					
				}
				
			}
		});

        add(label, BorderLayout.CENTER);
        add(findButton, BorderLayout.PAGE_END);
    }

}
