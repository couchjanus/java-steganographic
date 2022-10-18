import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfHeader.font;
import static com.tutego.jrtf.RtfPara.p;
import static com.tutego.jrtf.RtfPara.row;
import static com.tutego.jrtf.RtfText.bold;
import static com.tutego.jrtf.RtfText.text;
import static com.tutego.jrtf.RtfUnit.CM;
import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.math.plot.Plot2DPanel;
import org.math.plot.plotObjects.BaseLabel;

import com.tutego.jrtf.RtfHeaderFont;
import com.tutego.jrtf.RtfPara;


public class PresetsPanel extends JComponent{
	private int chunkSize = 1024;
	private int chiDirection = 1;
	
	ChiComponent chiComponent;
	
	BufferedImage image, src;

	
	public PresetsPanel(ConfigPanel configPanel, Plot2DPanel chiSquarePanel) {
		super();
		
		
//		image = ImageUtils.loadImage(imageList.get(TabbedPanelLeft.getIndex()));
		
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        setLayout(layout);
        
        JCheckBox chisChackBox = new JCheckBox("Chi Squared");
        JCheckBox spChackBox = new JCheckBox("Detect LSB SPA");
        JCheckBox rsChackBox = new JCheckBox("Rs Preset");
        JCheckBox aspChackBox = new JCheckBox("Simple PAir");
        JCheckBox caseChackBox = new JCheckBox("Case Preset");
        
        String[] generate = new String[] {"Choose Reports Format", "Create RTF Report", "Create HTML Report"}; 
        String[] prisets = new String[] {"Select a Group", "All Prisets", "Custom Prisets", "Prisets 3"}; 
        JComboBox<String> reportBox = new JComboBox<>(generate);
        JComboBox<String> customBox = new JComboBox<>(prisets);
        JLabel label = new JLabel("Check Some Presets: ");
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
        	.addGroup(layout.createParallelGroup(LEADING)
        		.addComponent(label)
        		.addComponent(customBox)
        		
        				.addComponent(chisChackBox)
        				.addComponent(spChackBox)
        				.addComponent(rsChackBox)
        				.addComponent(aspChackBox)
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
        				.addComponent(aspChackBox))
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

        			try {
        				if (Coords.isSelected) {
        					image = copyImage(ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex())).getSubimage(Coords.getX1(), Coords.getY1(), Coords.getX2(), Coords.getY2()));
//        					
        				}else {
        					image = ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex()));
        				}
        				int numChunks = (int) (Math.floor((image.getWidth() * image.getHeight() * 3 / chunkSize)) + 1.0D);
        				        				
        				double[] chiSquareAttack = new double[numChunks];
        				double[] avarege = new double[numChunks];
 
        				chiComponent = configPanel.getChiConfig();
        				chunkSize = chiComponent.getChiSize();
        				
        				chiDirection = chiComponent.getChiDirection();

        				switch(chiDirection) {
        				case 1:
        					chiSquareAttack = new ChiSquare(image).attackTopToBottom(chunkSize);
        					System.out.println("chiSquareAttack = "+chiSquareAttack );
            				avarege = new AvarageLsb(image).attackTopToBottom(chunkSize);
        					break;
        				case 2:
        					chiSquareAttack = new ChiSquare(image).attackLeftToRigth(chunkSize);
            				avarege = new AvarageLsb(image).attackLeftToRight(chunkSize);
        					break;
        				case 3:
        					chiSquareAttack = new ChiSquare(image).attackRightToLeft(chunkSize);
            				avarege = new AvarageLsb(image).attackRightToLeft(chunkSize);
        					break;
        				case 4:
        					break;
            				
        				}
        				
        				double [] x = new double[numChunks];
        				
        				for(int i = 0; i < numChunks; i++) {
        					x[i] = i;
        				}
        				
        				chiSquarePanel.removeAllPlots(); 
        				chiSquarePanel.addScatterPlot("Avarage LSB", x, avarege);
        				BaseLabel title = new BaseLabel("Chi Squere And Avarege LSB", null, 0.5, 1.1);
        				title.setFont(new Font("Courier", Font.BOLD, 20));
        				
        				chiSquarePanel.addPlotable(title);
        				chiSquarePanel.addLinePlot("Chi-Squere", x, chiSquareAttack);
        				chiSquarePanel.addLegend("EAST");
        				chiSquarePanel.setAxisLabel(0, chunkSize+"-bytes data block");
        				chiSquarePanel.setAxisLabel(1, "Avarage LSB value CHi-Squere test");
        				
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
        
        
        aspChackBox.addItemListener(new ItemListener() {
        	@Override
        	public void itemStateChanged(ItemEvent e) {
        		if(e.getStateChange() == ItemEvent.SELECTED) {
        			System.out.println("Siple Pair Detected selected");
        			double [] results;
        			double avg = 0;
        			try {
        				BufferedImage image = ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex()));
        				SPA sp = new SPA(image);
        				results = sp.analysis();
        				String [] channels = {"Red", "Green", "Blue"};
        				
        				for (int i = 0; i < 3; i++) {
        					avg += results[i];
        					if(results[i] > 0)
        						System.out.println("The " + channels[i] + " channel has the steganorgam: " + results[i]);
        					else
        						System.out.println("The " + channels[i] + " channel doesn't have the steganorgam");
        					
        				}
        				
        				System.out.println("Average = " + avg/3);
        				
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

        			try {
        				BufferedImage image = ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex()));
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
        			
        			try {
        				BufferedImage image = ImageUtils.loadImage(ImgList.images.get(TabbedPanelLeft.getIndex()));
        				RSA rsa = new RSA(image);
        				double avg = 0;
        				double[] result = rsa.analysis(0, true);
        				
        				System.out.println("RSA into Red = " + result[0]);
        				System.out.println("RSA into Red = " + result[1]);

        			}catch(Exception ex) {}
        			

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
        	        aspChackBox.setSelected(true);
        	        caseChackBox.setSelected(true);
        			break;
        		case 2:
        			chisChackBox.setSelected(false);
        	        rsChackBox.setSelected(false);
        	        aspChackBox.setSelected(true);
        	        caseChackBox.setSelected(true);
        			break;
        		case 3:
        			chisChackBox.setSelected(true);
        	        rsChackBox.setSelected(true);
        	        aspChackBox.setSelected(false);
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
	
	public static BufferedImage copyImage(BufferedImage src) {
		BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		Graphics g = dest.getGraphics();
		g.drawImage(src, 0, 0, null);
		g.dispose();
		return dest;
	}
}
