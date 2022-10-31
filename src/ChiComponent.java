import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.awt.GridLayout;

public class ChiComponent extends JComponent{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static int chunkSize = 1024;
	public static int chiDirection = 1;

	public ChiComponent() {
		super();
        
		JRadioButton rb1 = new JRadioButton("Top To Bottom", true);
        JRadioButton rb2 = new JRadioButton("Left To Right");
        JRadioButton rb3 = new JRadioButton("Right To Left");
        JRadioButton rb4 = new JRadioButton("Bottom To Top");
        
        ButtonGroup group = new ButtonGroup();
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        group.add(rb4);
        
        
        rb1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent ev) {
        		chiDirection = 1;
        		
        	}
        });
        
        rb2.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent ev) {
        		chiDirection = 2;

        	}
        });
        
        rb3.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent ev) {
        		chiDirection = 3;

        	}
        });
        rb4.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent ev) {
        		chiDirection = 4;

        	}
        });
        
        String [] sizes = new String[] {"128", "512", "1024", "2048"};
        
        JComboBox<String> sizeField = new JComboBox(sizes);
        
        sizeField.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent event) {
//        		int selectedIndex = (int) sizeField.getSelectedIndex();
        		chunkSize = Integer.parseInt(sizeField.getSelectedItem().toString());

        	}
        });
        
        JLabel sizeLabel = new JLabel("Size Chunk:");
        
        JLabel directionLabel = new JLabel("Direcrion:");
        
        setLayout(new GridLayout(3,2));
        
        add(sizeLabel);
        add(sizeField);
        add(rb1);
        add(rb4);
        add(rb2);
        add(rb3);
	}

}
