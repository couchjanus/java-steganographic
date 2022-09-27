import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class SelectedRegion extends JPanel{

	public SelectedRegion() {
		super();
	}
	
	public void updateSelectedRegion(BufferedImage img) {
		Graphics g = this.getGraphics(); 
		g.drawImage(img, 50, 50, null);
	}

}
