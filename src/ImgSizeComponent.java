import javax.swing.JComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.*;
import javax.swing.event.*;

public class ImgSizeComponent extends JComponent{
	private JLabel lblX1, lblX2, lblY1, lblY2;
//	private JTextField txtX1, txtX2, txtY1, txtY2;
	public NumericTextField txtX1, txtX2, txtY1, txtY2;
//	private final Coords coords;
	
	public static int getIndex() {
		return 1;
	}
	
	public ImgSizeComponent(Coords coords) {
		super();
//		this.coords = coords;
		lblX1 = new JLabel("Start x:");
		lblX2 = new JLabel("End x:");
		lblY1 = new JLabel("Start y:");
		lblY2 = new JLabel("End y:");
		DecimalFormat format = new DecimalFormat();
		format.setParseIntegerOnly(true);
		
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
		
		txtX1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
				txtX1.normalize();
				int x1 = Math.toIntExact(txtX1.getLongValue());
				Coords.setX1(x1);
				txtX1.setValue(Coords.getX1());
				System.out.println(x1);
				}catch(ParseException e1) {
					System.out.println(e1);
				}
			}
		});
		
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
		
		
//		txtX1.addFocusListener((FocusListener) new FocusListener() {
//			public void focusGained(FocusEvent e) {
//
//				txtX1.setValue(Coords.getX1());
//				txtX2.setValue(Coords.getX2());
//				txtY1.setValue(Coords.getY1());
//				txtY2.setValue(Coords.getY2());
//			}
//			public void focusLost(FocusEvent e) {
//				txtX1.setValue(Coords.getX1());
//			}
//		});
		
		txtX2.addFocusListener((FocusListener) new FocusListener() {
			public void focusGained(FocusEvent e) {
				txtX1.setValue(Coords.getX1());
				txtX2.setValue(Coords.getX2());
				txtY1.setValue(Coords.getY1());
				txtY2.setValue(Coords.getY2());
			}
			public void focusLost(FocusEvent e) {
				
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
