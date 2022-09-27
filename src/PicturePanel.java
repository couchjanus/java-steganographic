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
	private JFileChooser fc;
    static final private String ATTACH = "attach";
    private JPanel imageAreaPanel;
    ArrayList<String> imageList;
    private int w, h;
    private BufferedImage image;
    Point startDrag, endDrag;
    private Shape shape = null;
    
	public PicturePanel(ArrayList<String> imageList) {
		super();
		this.imageList = imageList;
//		
		setLayout(new GridLayout(2,1));
		
//		setLayout(new BorderLayout());
		addPictureControls();
//		addControls();
		add(imageAreaPanel);
		
		SelectedRegion selectedAreaPanel = new SelectedRegion();
		
		selectedAreaPanel.setLayout(new BorderLayout());
//		selectedAreaPanel.setBackground(Color.red);
		add(selectedAreaPanel);
	
	
	imageAreaPanel.addMouseListener(new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			startDrag = new Point(e.getX(), e.getY());
			endDrag = startDrag;
			repaint();
		}
		public void mouseReleased(MouseEvent e) {
			if (endDrag!=null && startDrag!=null) {
				try {
					shape = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
					System.out.println("Rect: ("+startDrag.x +"," + startDrag.y+") ("+(e.getX()-startDrag.x) +","+ (e.getY()-startDrag.y)+")");
					selectedAreaPanel.updateSelectedRegion(image.getSubimage(startDrag.x, startDrag.y, e.getX()-startDrag.x, e.getY()-startDrag.y));
					startDrag = null;
					endDrag = null;
					imageAreaPanel.repaint();
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
	
	});
	
	imageAreaPanel.addMouseMotionListener(new MouseMotionAdapter() {
		public void mouseDragged(MouseEvent e) {
			endDrag = new Point(e.getX(), e.getY());
			repaint();
		}
	});
	
	}
	private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2){
		return new Rectangle2D.Float(Math.min(x2, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
	}
//		h = getSize().height;
//	
//		ImageScale image = new ImageScale(file);
//		JLabel picLabel = image.drawImage(w, h);
//		
//		setBackground(Color.yellow);
//		add(picLabel);
//		revalidate();
//		repaint();
//	}
	private void putPicture(String file) {
		imageAreaPanel.removeAll();
		w = getSize().width;
		h = getSize().height;
	
//		ImageScale image = new ImageScale(file);
		image = ImageUtils.loadImage(file);
		JLabel picLabel = new JLabel(new ImageIcon(image));		
//		setBackground(Color.yellow);
		imageAreaPanel.add(picLabel);
		imageAreaPanel.revalidate();
		imageAreaPanel.repaint();
	}
	
	private void addPictureControls()
	{
		imageAreaPanel = new JPanel();
		imageAreaPanel.setLayout(new BorderLayout());
		
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
//        				putPicture(file);
        				putPicture(file.getPath());
        				imageList.add(file.getPath());
//        				System.out.println(imageList);
        			}
        		}
        	 }
        });
        new FileDrop(System.out, label, new FileDrop.Listener() {
			
			@Override
			public void filesDropped(File[] files) {
				try {
//					putPicture(new File(files[0].getCanonicalPath()));
					putPicture(files[0].getCanonicalPath());
					imageList.add(files[0].getCanonicalPath());
//    				System.out.println(imageList);
				}catch(java.io.IOException e) {
					
				}
				
			}
		});

        imageAreaPanel.add(label, BorderLayout.CENTER);
        imageAreaPanel.add(findButton, BorderLayout.PAGE_END);
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
//        				putPicture(file);
        				putPicture(file.getPath());
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
//					putPicture(new File(files[0].getCanonicalPath()));
					putPicture(files[0].getCanonicalPath());
					imageList.add(files[0].getCanonicalPath());
    				System.out.println(imageList);
				}catch(java.io.IOException e) {
					
				}
				
			}
		});

        add(label, BorderLayout.CENTER);
        add(findButton, BorderLayout.PAGE_END);
    }
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		if (image != null) {
			g2.drawImage(image, 0, 0, null);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			g2.setStroke(new BasicStroke(2));
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
			
			if (shape != null) {
				g2.setPaint(Color.BLACK);
				g2.draw(shape);
				
				g2.setPaint(Color.YELLOW);
				g2.fill(shape);
			}
			
			if(startDrag != null && endDrag != null) {
				g2.setPaint(Color.LIGHT_GRAY);
				Shape r = makeRectangle(startDrag.x, startDrag.y, endDrag.x, endDrag.y);
				g2.draw(r);
			}
		}
	}

}
