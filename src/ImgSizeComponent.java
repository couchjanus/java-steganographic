import javax.swing.JComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class ImgSizeComponent extends JComponent{
	private JLabel lblX1, lblX2, lblY1, lblY2;
	private JTextField txtX1, txtX2, txtY1, txtY2;
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
		
		txtX1 = new JTextField();
		txtX1.setText(Integer.toString(Coords.getX1()));
		txtX2 = new JTextField();
		txtX2.setText(Integer.toString(Coords.getX2()));
		txtY1 = new JTextField();
		txtY1.setText(Integer.toString(Coords.getY1()));
		txtY2 = new JTextField();
		txtY2.setText(Integer.toString(Coords.getY2()));
		
		txtX1.setHorizontalAlignment(SwingConstants.CENTER);
		txtX2.setHorizontalAlignment(SwingConstants.CENTER);
		txtY1.setHorizontalAlignment(SwingConstants.CENTER);
		txtY2.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtX1.setPreferredSize(new Dimension(50, 30));
		txtX2.setPreferredSize(new Dimension(50, 30));
		txtY1.setPreferredSize(new Dimension(50, 30));
		txtY2.setPreferredSize(new Dimension(50, 30));
		
		setLayout(new GridLayout(2,4));
		
		txtX1.addFocusListener((FocusListener) new FocusListener() {
			public void focusGained(FocusEvent e) {
				txtX1.setText(Integer.toString(Coords.getX1()));
				
				txtX2.setText(Integer.toString(Coords.getX2()));
				
				txtY1.setText(Integer.toString(Coords.getY1()));
				
				txtY2.setText(Integer.toString(Coords.getY2()));
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
