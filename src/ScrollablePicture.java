import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.*;

/* ScrollablePicture.java is used by ScrollDemo.java. */

public class ScrollablePicture extends JLabel
                               implements Scrollable,
                                          MouseMotionListener {
	static final long serialVersionUID = -1L;
	
    private int maxUnitIncrement = 1;
    private boolean missingPicture = false;
    
    Point startDrag, endDrag;
    private Shape shape = null;
    
    private SelectedRegion selectedRegion;
    private BufferedImage image;
    private String path;
    
    private final Coords coords;
    
    public ScrollablePicture(ImageIcon i, int m, SelectedRegion selectedRegion, String path, Coords coords) {
        super(i);
        this.selectedRegion = selectedRegion;
        this.path = path;
        this.image = ImageUtils.loadImage(this.path);
        this.coords = coords;
        
        if (i == null) {
            missingPicture = true;
            setText("No picture found.");
            setHorizontalAlignment(CENTER);
            setOpaque(true);
            setBackground(Color.white);
        }
        maxUnitIncrement = m;

        //Let the user scroll by dragging to outside the window.
        setAutoscrolls(true); //enable synthetic drag events
        addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent e) {
        		startDrag = new Point(e.getX(), e.getY());
        		endDrag = startDrag;
        		System.out.println(endDrag);
        		repaint();
        	}
            public void mouseReleased(MouseEvent e) {
        		if (endDrag!=null && startDrag!=null) {
        			try {
        				shape = makeRectangle(startDrag.x, startDrag.y, e.getX(), e.getY());
        				
//        				System.out.println("Rect: ("+startDrag.x +"," + startDrag.y+") ("+(e.getX()-startDrag.x) +","+ (e.getY()-startDrag.y)+")");
        				
        				System.out.println("Rect: ("+startDrag.x +"," + startDrag.y+") ("+(endDrag.x) +","+ (endDrag.y)+")");
        				
        				coords.setX1(startDrag.x);
        				coords.setX2(e.getX()-startDrag.x);
        				coords.setY1(startDrag.y);
        				coords.setY2(e.getY()-startDrag.y);
        				
        				System.out.println("Coords: ("+coords.getX1() +"," + coords.getY1()+") ("+coords.getX2() +","+ coords.getY2()+")");
        				
        				selectedRegion.updateSelectedRegion(image.getSubimage(startDrag.x, startDrag.y, e.getX()-startDrag.x, e.getY()-startDrag.y));
        				
        				startDrag = null;
        				endDrag = null;
        				repaint();
        			}catch(Exception ex) {
        				ex.printStackTrace();
        			}
        		}
        	}

        });
        addMouseMotionListener(this); //handle mouse drags
    }

    //Methods required by the MouseMotionListener interface:
    public void mouseMoved(MouseEvent e) { }
    public void mouseDragged(MouseEvent e) {
        //The user is dragging us, so scroll!
        Rectangle r = new Rectangle(e.getX(), e.getY(), 1, 1);
        scrollRectToVisible(r);
        endDrag = new Point(e.getX(), e.getY());
        
		repaint();
    }
    
    
    public Dimension getPreferredSize() {
        if (missingPicture) {
            return new Dimension(320, 480);
        } else {
            return super.getPreferredSize();
        }
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }
    
    private Rectangle2D.Float makeRectangle(int x1, int y1, int x2, int y2){
		return new Rectangle2D.Float(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x1-x2), Math.abs(y1-y2));
	}

    public int getScrollableUnitIncrement(Rectangle visibleRect,
                                          int orientation,
                                          int direction) {
        //Get the current position.
        int currentPosition = 0;
        if (orientation == SwingConstants.HORIZONTAL) {
            currentPosition = visibleRect.x;
        } else {
            currentPosition = visibleRect.y;
        }

        //Return the number of pixels between currentPosition
        //and the nearest tick mark in the indicated direction.
        if (direction < 0) {
            int newPosition = currentPosition -
                             (currentPosition / maxUnitIncrement)
                              * maxUnitIncrement;
            return (newPosition == 0) ? maxUnitIncrement : newPosition;
        } else {
            return ((currentPosition / maxUnitIncrement) + 1)
                   * maxUnitIncrement
                   - currentPosition;
        }
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation,
                                           int direction) {
        if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - maxUnitIncrement;
        } else {
            return visibleRect.height - maxUnitIncrement;
        }
    }

    public boolean getScrollableTracksViewportWidth() {
        return false;
    }

    public boolean getScrollableTracksViewportHeight() {
        return false;
    }

    public void setMaxUnitIncrement(int pixels) {
        maxUnitIncrement = pixels;
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