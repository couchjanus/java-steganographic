import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ScrollPanel extends JPanel
implements ItemListener{
	static final long serialVersionUID = -1L;
	private Rule columnView;
    private Rule rowView;
    private JToggleButton isMetric;
    private ScrollablePicture picture;
    private ImageIcon imageIcon;
    
    
    private SelectedRegion selectedRegion;
    
    private JFileChooser fc;
    static final private String ATTACH = "attach";
    private JPanel buttonCorner;
   
    public SelectedRegion getSelectedRegion() {
    	return selectedRegion;
    }
    
	public ScrollPanel(SelectedRegion selectedRegion) {
		
		this.selectedRegion = selectedRegion;
		
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		//Create the row and column headers.
        columnView = new Rule(Rule.HORIZONTAL, true);
        rowView = new Rule(Rule.VERTICAL, true);
        
        columnView.setPreferredWidth(320);
        rowView.setPreferredHeight(480);
        
        //Create the corners.
        buttonCorner = new JPanel(); //use FlowLayout
        isMetric = new JToggleButton("cm", true);
        isMetric.setFont(new Font("SansSerif", Font.PLAIN, 11));
        isMetric.setMargin(new Insets(2,2,2,2));
        isMetric.addItemListener(this);
        buttonCorner.add(isMetric); 
        
        JPanel boundPanel = new JPanel();
        
        boundPanel.setBorder(BorderFactory.createTitledBorder("Choose the File"));
        BoxLayout layout2 = new BoxLayout(boundPanel, BoxLayout.Y_AXIS);
        boundPanel.setLayout(layout2);
        
        JLabel label = new JLabel("<html><h1>Drag Abd Drop File HERE</h1><br><br><h2>Or Click Brouse Button</h2><br><br></html>");
        Border border = BorderFactory.createLineBorder(Color.RED);
		label.setBorder(border);
		label.setBounds(getVisibleRect());
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		new FileDrop(System.out, label, new FileDrop.Listener() {
			@Override
			public void filesDropped(File[] files) {
				try {
					drawPicture(files[0].getCanonicalPath());
				}catch(java.io.IOException e) {
					
				}
			}
		});
		
		boundPanel.add(label);
		
		JButton brouseButton = new JButton("Bouse Image File");
		brouseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		brouseButton.setActionCommand(ATTACH);
		brouseButton.setToolTipText("Open file");
		
		brouseButton.addActionListener(new ActionListener() {
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
       	        int returnVal = fc.showDialog(ScrollPanel.this,
       	                "Attach");
       			if (returnVal == JFileChooser.APPROVE_OPTION) 
       			{
       				File file = fc.getSelectedFile();
       				drawPicture(file.getPath().toString());
       			}
       		}
       	 }
       });
		
		
		boundPanel.add(brouseButton);
		add(boundPanel);
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	}
	
	private void drawPicture(String path) {
		
		imageIcon = createImageIcon(path);
		
		if (imageIcon != null) {
            columnView.setPreferredWidth(imageIcon.getIconWidth());
            rowView.setPreferredHeight(imageIcon.getIconHeight());
            
//            System.out.println(imageIcon.getIconWidth());
        } else {
            columnView.setPreferredWidth(320);
            rowView.setPreferredHeight(480);
        }
		
		picture = new ScrollablePicture(imageIcon, columnView.getIncrement(), selectedRegion, path);
        JScrollPane pictureScrollPane = new JScrollPane(picture);
        pictureScrollPane.setPreferredSize(new Dimension(300, 250));
        pictureScrollPane.setViewportBorder(
                BorderFactory.createLineBorder(Color.black));

        pictureScrollPane.setColumnHeaderView(columnView);
        pictureScrollPane.setRowHeaderView(rowView);

	//Set the corners.
        
        pictureScrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
                                    buttonCorner);
        pictureScrollPane.setCorner(JScrollPane.LOWER_LEFT_CORNER,
                                    new Corner());
        pictureScrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, 
        		new Corner());
        
        removeAll();

        
        Coords.setX1(0);	
		Coords.setY1(0);
		
		Coords.setX2(imageIcon.getIconWidth());
		Coords.setY2(imageIcon.getIconHeight());
		
        add(pictureScrollPane);
        ImgList.images.add(path);
        
        revalidate();
        repaint();				
        	
	}
	
	public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            //Turn it to metric.
            rowView.setIsMetric(true);
            columnView.setIsMetric(true);
        } else {
            //Turn it to inches.
            rowView.setIsMetric(false);
            columnView.setIsMetric(false);
        }
        picture.setMaxUnitIncrement(rowView.getIncrement());
    }
	
	protected static ImageIcon createImageIcon(String path) {
        BufferedImage imgURL = ImageUtils.loadImage(path);;
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }


}
