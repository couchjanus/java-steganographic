import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public class BitwiseXORPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel imageLabel = new JLabel();
	private JButton nextButton = new JButton();
	private JButton previousButton = new JButton();
	private JPanel navPanel = new JPanel();
	private JScrollPane jScrollPane = new JScrollPane(); 
	private BufferedImage src, currentImage;
	
	private int pixelSize;
	private BitwiseXOR bitwiseXOR;
	private int bitwiseXORIndex;
	
	public BitwiseXORPanel(BufferedImage src) {
		super();
		this.src = src;
		
		bitwiseXOR = new BitwiseXOR();
		bitwiseXORIndex = 0;
		
		ColorModel colorModel = src.getColorModel();
		pixelSize = colorModel.getPixelSize();
		
		
		setLayout(new BorderLayout());
		setSize(300, 400);
		
		imageLabel.setIcon(new ImageIcon(src));
		jScrollPane.setViewportView(imageLabel);
		add(jScrollPane, BorderLayout.NORTH);
		
		previousButton.setText("Previous");
		previousButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousButtonPerformed(e);
			}
		});
		
		nextButton.setText("Next");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextButtonPerformed(e);
			}
		});
		navPanel.setLayout(new GridLayout(1,2));
		navPanel.add(previousButton);
		navPanel.add(nextButton);
		
		add(navPanel, BorderLayout.SOUTH);
	}
	
	private void previousButtonPerformed(ActionEvent e) {
		if (bitwiseXORIndex <= 1) {
			bitwiseXORIndex = 29;
		}
		bitwiseXORIndex--;
		applyBitwiseXOR();
	}
	private void nextButtonPerformed(ActionEvent e) {
		if(bitwiseXORIndex>=28) {
			bitwiseXORIndex=0;
		}
		bitwiseXORIndex++;
		applyBitwiseXOR();
	}
	
	private void applyBitwiseXOR() {
		if (src != null) {
			currentImage = copyImage(src);
			bitwiseXOR.xor(currentImage, bitwiseXORIndex, pixelSize);
			imageLabel.setIcon(new ImageIcon(currentImage));
		}
	}

	public BufferedImage copyImage(BufferedImage coverImage) {
        ColorModel colorModel = coverImage.getColorModel();
        boolean isAlphaPremultiplied = coverImage.isAlphaPremultiplied();
        WritableRaster raster = coverImage.copyData(null);
        BufferedImage newImage = new BufferedImage(colorModel, raster,
                isAlphaPremultiplied, null);
        return newImage;
    }
}
