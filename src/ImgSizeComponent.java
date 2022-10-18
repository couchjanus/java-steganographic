import javax.swing.JComponent;
import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.*;
import javax.swing.event.*;
import java.awt.image.BufferedImage;
public class ImgSizeComponent extends JComponent{
	private JLabel lblX1, lblX2, lblY1, lblY2;

	public NumericTextField txtX1, txtX2, txtY1, txtY2;

	private BufferedImage image;
	
	private int w, h;
	
	public static int getIndex() {
		return 1;
	}
	
	public ImgSizeComponent(SelectedRegion selectedRegion) {
		super();
		
		
//		this.coords = coords;
		lblX1 = new JLabel("Start x:");
		lblX2 = new JLabel("End x:");
		lblY1 = new JLabel("Start y:");
		lblY2 = new JLabel("End y:");
		DecimalFormat format = new DecimalFormat();
		format.setParseIntegerOnly(true);
		
		NumberFormatter nf = new NumberFormatter();
		nf.setValueClass(Integer.class);
		nf.setMinimum(0);
		nf.setMaximum(w);
		
		txtX1 = new NumericTextField();
		
		txtX1.setValue(Coords.getX1());
		txtX2 = new NumericTextField();
		txtX2.setValue(Coords.getX2());
		txtY1 = new NumericTextField();
		txtY1.setValue(Coords.getY1());
		txtY2 = new NumericTextField();
		txtY2.setValue(Coords.getY2());
		
		txtX1.setHorizontalAlignment(SwingConstants.CENTER);
		txtX2.setHorizontalAlignment(SwingConstants.CENTER);
		txtY1.setHorizontalAlignment(SwingConstants.CENTER);
		txtY2.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtX1.setPreferredSize(new Dimension(50, 30));
		txtX2.setPreferredSize(new Dimension(50, 30));
		txtY1.setPreferredSize(new Dimension(50, 30));
		txtY2.setPreferredSize(new Dimension(50, 30));
		
		setLayout(new GridLayout(2,4));
		
		
		txtX1.getDocument().addDocumentListener(new SimleDocumentListener() {
			@Override
			public void update(DocumentEvent e) {
				try {
//					System.out.println(txtX1.getLongValue());
					int v = Math.toIntExact(txtX1.getLongValue());
					Coords.setX1(v);
				}catch(ParseException e1) {
					System.out.println(e1);
				}
			}
		});
		
		txtX2.getDocument().addDocumentListener(new SimleDocumentListener() {
			@Override
			public void update(DocumentEvent e) {
				try {
//					System.out.println(txtX1.getLongValue());
					int v = Math.toIntExact(txtX2.getLongValue());
					Coords.setX2(v);
				}catch(ParseException e1) {
					System.out.println(e1);
				}
			}
		});
		
		txtY2.getDocument().addDocumentListener(new SimleDocumentListener() {
			@Override
			public void update(DocumentEvent e) {
				try {
//					System.out.println(txtX1.getLongValue());
					int v = Math.toIntExact(txtY2.getLongValue());
					Coords.setY2(v);
				}catch(ParseException e1) {
					System.out.println(e1);
				}
			}
		});
		
		txtY1.getDocument().addDocumentListener(new SimleDocumentListener() {
			@Override
			public void update(DocumentEvent e) {
				try {
//					System.out.println(txtX1.getLongValue());
					int v = Math.toIntExact(txtY1.getLongValue());
					Coords.setY1(v);
				}catch(ParseException e1) {
					System.out.println(e1);
				}
			}
		});
		
		txtX1.addFocusListener((FocusListener) new FocusListener() {
			public void focusGained(FocusEvent e) {
				txtX1.setValue(Coords.getX1());
				txtX2.setValue(Coords.getX2());
				txtY1.setValue(Coords.getY1());
				txtY2.setValue(Coords.getY2());
			}
			public void focusLost(FocusEvent e) {
				image = ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex()));
				
				w = image.getWidth();
				h = image.getHeight();
				
				selectedRegion.updateSelectedRegion(ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex())).getSubimage(Coords.getX1(), Coords.getY1(), Coords.getX2(), Coords.getY2()));
			}
		});
		
		txtX2.addFocusListener((FocusListener) new FocusListener() {
			public void focusGained(FocusEvent e) {
				txtX1.setValue(Coords.getX1());
				txtX2.setValue(Coords.getX2());
				txtY1.setValue(Coords.getY1());
				txtY2.setValue(Coords.getY2());
			}
			public void focusLost(FocusEvent e) {
				selectedRegion.updateSelectedRegion(ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex())).getSubimage(Coords.getX1(), Coords.getY1(), Coords.getX2(), Coords.getY2()));
			}
		});
		
		txtY1.addFocusListener((FocusListener) new FocusListener() {
			public void focusGained(FocusEvent e) {
				txtX1.setValue(Coords.getX1());
				txtX2.setValue(Coords.getX2());
				txtY1.setValue(Coords.getY1());
				txtY2.setValue(Coords.getY2());
			}
			public void focusLost(FocusEvent e) {
				selectedRegion.updateSelectedRegion(ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex())).getSubimage(Coords.getX1(), Coords.getY1(), Coords.getX2(), Coords.getY2()));
			}
		});
		
		txtY2.addFocusListener((FocusListener) new FocusListener() {
			public void focusGained(FocusEvent e) {
				txtX1.setValue(Coords.getX1());
				txtX2.setValue(Coords.getX2());
				txtY1.setValue(Coords.getY1());
				txtY2.setValue(Coords.getY2());
			}
			public void focusLost(FocusEvent e) {
				selectedRegion.updateSelectedRegion(ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex())).getSubimage(Coords.getX1(), Coords.getY1(), Coords.getX2(), Coords.getY2()));
			}
		});
		
		
		add(lblX1);
		add(txtX1);
		
		add(lblY1);
		add(txtY1);
		add(lblX2);
		add(txtX2);
		add(lblY2);
		add(txtY2);
		
	}

}
