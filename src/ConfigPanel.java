import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ConfigPanel extends JComponent{
	
	private int chunkSize = 1024;
	private int chiDirection = 1;
	
	public int getChiSize() {
		return chunkSize;
	}
	public int getChiDirection() {
		return chiDirection;
	}
	public ConfigPanel() {
		super();
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        setLayout(layout);
        
        ButtonGroup group = new ButtonGroup();
        
        JRadioButton rb1 = new JRadioButton("Top To Bottom", true);
        JRadioButton rb2 = new JRadioButton("Left To Right");
        JRadioButton rb3 = new JRadioButton("Right To Left");
        JRadioButton rb4 = new JRadioButton("Bottom To Top");
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        group.add(rb4);
        
        
        rb1.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent ev) {
        		chiDirection = 1;
//        		System.out.println("chiDirection = "+chiDirection);
        		
        	}
        });
        
        rb2.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent ev) {
        		chiDirection = 2;
//        		System.out.println("chiDirection = "+chiDirection);
        		
        	}
        });
        
        rb3.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent ev) {
        		chiDirection = 3;
//        		System.out.println("chiDirection = "+chiDirection);
        		
        	}
        });
        rb4.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent ev) {
        		chiDirection = 4;
//        		System.out.println("chiDirection = "+chiDirection);
        		
        	}
        });
        
        String [] sizes = new String[] {"128", "512", "1024", "2048"};
        
        JComboBox<String> sizeField = new JComboBox(sizes);
        
        JLabel sizeLabel = new JLabel("Size Chunk:");
        
        JLabel directionLabel = new JLabel("Direcrion:");
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        		.addComponent(sizeLabel)
        		.addGroup(layout.createParallelGroup(LEADING)
        				.addComponent(sizeField)
        		.addGroup(layout.createParallelGroup(LEADING)
        				.addComponent(directionLabel))
        		.addGroup(layout.createParallelGroup(LEADING)
        				.addGroup(layout.createSequentialGroup()
        						.addGroup(layout.createParallelGroup(LEADING)
        								.addComponent(rb1)	
        								.addComponent(rb4))
        						.addGroup(layout.createParallelGroup(LEADING)
                						.addComponent(rb2)	
                						.addComponent(rb3))
        				)))
        		);
        
        layout.setVerticalGroup(layout.createSequentialGroup()
        		.addGroup(layout.createParallelGroup(BASELINE)
        				.addComponent(sizeLabel)
        				.addComponent(sizeField))
        		.addGroup(layout.createParallelGroup(LEADING)
        				.addComponent(directionLabel))
        		.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(BASELINE)
								.addComponent(rb1)	
								.addComponent(rb2))
						.addGroup(layout.createParallelGroup(BASELINE)
        						.addComponent(rb4)	
        						.addComponent(rb3)))
        		);
        
        
        sizeField.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent event) {
        		int selectedIndex = (int) sizeField.getSelectedIndex();
        		chunkSize = Integer.parseInt(sizeField.getSelectedItem().toString());
//        		System.out.println("chunkSize = " + chunkSize);
        		
        	}
        });
        
	}

}
