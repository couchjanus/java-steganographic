import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import static javax.swing.GroupLayout.Alignment.*;

import com.tutego.jrtf.*;
import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfHeader.font;
import static com.tutego.jrtf.RtfPara.*;
import static com.tutego.jrtf.RtfText.*;
import static com.tutego.jrtf.RtfUnit.CM;
import java.awt.Desktop;
import java.util.Date;
import java.awt.image.*;

public class TabbedPanelRight extends JPanel {
	
	JTabbedPane tabbedPane = new JTabbedPane();
	ArrayList<String> imageList;
	
	private BufferedImage image = null;
	private BufferedImage lsbImage = null;
	private BufferedImage outImage = null;
	
	public TabbedPanelRight(ArrayList<String> imageList) {
	  super();
	  setLayout(new BorderLayout());
	  this.imageList = imageList;
	  
	  RightToolbar toolBar = new RightToolbar(tabbedPane, imageList);
	  
	  add(toolBar, BorderLayout.PAGE_START);
	  
      JComponent panel1 = makePresetsPanel("Check Some Presets: ");
      tabbedPane.addTab("Presets", null, panel1,
              "Does nothing");
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
       
      JComponent panel2 = makeTextPanel("Histogram");
      tabbedPane.addTab("Histogram", null, panel2,
              "Does Histogram as much nothing");
      tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
       
      tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      
      add(tabbedPane);
	}
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
	
	public static void makertf() throws IOException{
		File out = new File("report.rtf");
		RtfPara nextPar = RtfPara.p("second paragraph");
		rtf()
		.header(font(RtfHeaderFont.WINDINGS).at(1))
		.section(p("Line 1: ", 2, bold(" Now: "), new Date(), text("dd"), text(true, "111", 2)), 
				nextPar, row(bold("Hello World"), bold("Test"))
				.bottomCellBorder().topCellBorder().cellSpace(1, CM)
				)
		.out(new FileWriter(out));
		try {
			Desktop.getDesktop().open(out);
		}catch(IOException e) {
			e.printStackTrace();
		
		}
	}
	
	protected JComponent makePresetsPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel label = new JLabel(text);
        
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        JCheckBox chisChackBox = new JCheckBox("Chi Squared");
        JCheckBox spChackBox = new JCheckBox("Simple Pair");
        JCheckBox rsChackBox = new JCheckBox("Rs Preset");
        JCheckBox lmbChackBox = new JCheckBox("LMB Preset");
        JCheckBox caseChackBox = new JCheckBox("Case Preset");
        
        String[] generate = new String[] {"Choose Reports Format", "Create RTF Report", "Create HTML Report"}; 
        String[] prisets = new String[] {"Select a Group", "All Prisets", "Custom Prisets", "Prisets 3"}; 
        JComboBox<String> reportBox = new JComboBox<>(generate);
        JComboBox<String> customBox = new JComboBox<>(prisets);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        	.addGroup(layout.createParallelGroup(LEADING)
        		.addComponent(label)
        		.addComponent(customBox)
        		
        				.addComponent(chisChackBox)
        				.addComponent(spChackBox)
        				.addComponent(rsChackBox)
        				.addComponent(lmbChackBox)
        				.addComponent(caseChackBox)
        				.addComponent(reportBox)
        				)
        		
        		);
        
        layout.setVerticalGroup(layout.createSequentialGroup()
        	.addGroup(layout.createParallelGroup(BASELINE)
        	.addComponent(label))
        	.addGroup(layout.createParallelGroup(BASELINE)
        	.addComponent(customBox, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        	
            .addGroup(layout.createParallelGroup(LEADING)
        				.addComponent(chisChackBox))
            .addGroup(layout.createParallelGroup(LEADING)
    				.addComponent(spChackBox))
        	.addGroup(layout.createParallelGroup(LEADING)
        				.addComponent(rsChackBox))
        	.addGroup(layout.createParallelGroup(LEADING)
        				.addComponent(lmbChackBox))
        	.addGroup(layout.createParallelGroup(LEADING)
        				.addComponent(caseChackBox)
        				)
        		.addGroup(layout.createParallelGroup(BASELINE)
        				.addComponent(reportBox)
        				)
        		);
        
//        
        chisChackBox.addItemListener(new ItemListener() {
        	@Override
        	public void itemStateChanged(ItemEvent e) {
        		if(e.getStateChange() == ItemEvent.SELECTED) {
//        			System.out.println("It's selected");
        			try {
        				BufferedImage image = ImageUtils.loadImage(imageList.get(TabbedPanelLeft.getIndex()));
        				double[] chiSquareAttack = new ChiSquare(image).attackTopToBottom(1024);
        				double q = 0;
        				for(double v : chiSquareAttack)
        					q += v;
        				System.out.println("Chi square attack top to bottom = " + q);
        			}catch(Exception ex) {}
        		}else {
        			System.out.println("It's deselected");
        		}
        	}
        });
        
        spChackBox.addItemListener(new ItemListener() {
        	@Override
        	public void itemStateChanged(ItemEvent e) {
        		if(e.getStateChange() == ItemEvent.SELECTED) {
//        			System.out.println("It's selected");
        			try {
        				BufferedImage image = ImageUtils.loadImage(imageList.get(TabbedPanelLeft.getIndex()));
        				SimplePair sp = new SimplePair(image);
        				double avg = 0;
        				double result = sp.analysis(0);
        				System.out.println("Stegano into Red = " + result);
        				avg += result;
        				result = sp.analysis(1);
        				System.out.println("Stegano into Green = " + result);
        				avg += result;
        				result = sp.analysis(2);
        				System.out.println("Stegano into Blue = " + result);
        				avg += result;
        				System.out.println("Average = " + avg/3);
        				
        			}catch(Exception ex) {}
        		}else {
        			System.out.println("It's deselected");
        		}
        	}
        });
        
        
        rsChackBox.addItemListener(new ItemListener() {
        	@Override
        	public void itemStateChanged(ItemEvent event) {
        		if(event.getStateChange() == ItemEvent.SELECTED) {
        			
        			String mime = null;
        			
        			
        			try {
        				mime = ImageUtils.getMimeType(imageList.get(TabbedPanelLeft.getIndex()));
        				System.out.println("It's mime type " + mime);
        			} catch(IOException ex) {}
        			
        			switch(mime) {
        			case "image/bmp":
        				
        				try {
        					image = BitmapLoader.loadBitmap(imageList.get(TabbedPanelLeft.getIndex()));
        				}catch(IOException ex) {}
        				lsbImage = new BufferedImage(image.getWidth(), image.getHeight(), 4);
        				ChiSquareAttack attack = new ChiSquareAttack(image, lsbImage);
        				
        				outImage = attack.execute();
        				
        				break;
        			case "image/jpeg":
        				
        				break;
        			case "image/png":
            				
            			break;
        			case "image/tif":
            				
            			break;
        			}
        			
        			
        			int[] mask= {1, 0, 0, 1};
        			int[] invert_mask = {-1, 0, 0, -1};
        			
        			BufferedImage image = ImageUtils.loadImage(imageList.get(TabbedPanelLeft.getIndex()));
        			int [] RGB = ImageUtils.getSplitRGB(image, 100, 100);
        			
        			System.out.println(RGB[0]);
        			
        			ImageUtils.imgChannel(image, "red");
        			ImageUtils.imgChannel(image, "green");
        			ImageUtils.imgChannel(image, "blue");
        		}else {
        			System.out.println("It's deselected");
        		}
        	}
        });
        
        customBox.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int selectedIndex = customBox.getSelectedIndex();
        		switch(selectedIndex) {
        		case 0:
        			break;
        		case 1:
        			chisChackBox.setSelected(true);
        	        rsChackBox.setSelected(true);
        	        lmbChackBox.setSelected(true);
        	        caseChackBox.setSelected(true);
        			break;
        		case 2:
        			chisChackBox.setSelected(false);
        	        rsChackBox.setSelected(false);
        	        lmbChackBox.setSelected(true);
        	        caseChackBox.setSelected(true);
        			break;
        		case 3:
        			chisChackBox.setSelected(true);
        	        rsChackBox.setSelected(true);
        	        lmbChackBox.setSelected(false);
        	        caseChackBox.setSelected(false);
        			break;
        		default:
        			System.out.println("Index: " + selectedIndex);
        		}
        		
        	}
        });
        
        reportBox.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		int selectedIndex = customBox.getSelectedIndex();
        		switch(selectedIndex) {
        		case 0:
        			break;
        		case 1:
        			System.out.println("Index: " + selectedIndex);
        			try {
        				makertf();
        			}catch(IOException ex) {
        				ex.printStackTrace();
        			}
        			break;
        		case 2:
        			System.out.println("Index: " + selectedIndex);
        			break;
        		
        		default:
        			System.out.println("Index: " + selectedIndex);
        		}
        		
        	}
        });
        return panel;
    }
}
