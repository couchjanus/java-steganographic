
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ContainerListener;
import java.awt.event.ContainerEvent;
import java.awt.*;
//import java.awt.event.*;
import java.util.ArrayList;
//import static javax.swing.GroupLayout.Alignment.*;

import com.tutego.jrtf.*;
import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfHeader.font;
import static com.tutego.jrtf.RtfPara.*;
import static com.tutego.jrtf.RtfText.*;
import static com.tutego.jrtf.RtfUnit.CM;
import java.awt.Desktop;
import java.util.Date;
//import java.awt.image.*;
import org.math.plot.Plot2DPanel;


public class TabbedPanelRight extends JPanel {
	static final long serialVersionUID = -1L; 
	
	JTabbedPane tabbedPaneRight; // = new JTabbedPane();
	private static int index;
		
	private Plot2DPanel chiSquarePanel;
	
	private ConfigPanel configPanel;
	
	private JPanel welcome = new JPanel();
	
	TabbedPanelLeft leftPanel;
	
	public static int getIndex() {
		return index;
	}
	public TabbedPanelRight(TabbedPanelLeft leftPanel, JTabbedPane tabbedPaneRight) {
	  super();
	  this.leftPanel = leftPanel;
	  this.tabbedPaneRight = tabbedPaneRight;
	  
	  setLayout(new BorderLayout());
	  
//	  System.out.println(leftPanel.getPicturePanel().getScrollPanel().getSelectedRegion());
//	  leftPanel.getPicturePanel().getScrollPanel().getSelectedRegion().addContainerListener(new LeftPaneContainerListener());
	  
	  leftPanel.getPicturePanel().getScrollPanel().addContainerListener(new CustomContainerListener());
	  
	  RightToolbar toolBar = new RightToolbar(tabbedPaneRight);
	  
	  add(toolBar, BorderLayout.PAGE_START);
	  tabbedPaneRight.addTab("Welcome", null, welcome, "");
	  
      chiSquarePanel = new Plot2DPanel();
      PresetsPanel presetsPanel = new PresetsPanel(configPanel, chiSquarePanel);
      tabbedPaneRight.addTab("Presets", null, presetsPanel, "");
      tabbedPaneRight.setMnemonicAt(0, KeyEvent.VK_1);
       
      JComponent panel2 = makeTextPanel("Histogram");
      tabbedPaneRight.addTab("Histogram", null, panel2,
              "Does Histogram as much nothing");
      tabbedPaneRight.setMnemonicAt(1, KeyEvent.VK_2);
      
      tabbedPaneRight.addTab("Chi-squere", null, chiSquarePanel, null);
       
      tabbedPaneRight.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      
      
      ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				index = sourceTabbedPane.getSelectedIndex();
				System.out.println("Tabbed index: " + index);
//				System.out.println("Tabbed left: " + sourceTabbedPane.getComponentAt(index));
//			
				if(ImgList.images.size() > 0){
//					if((ImgList.images.get(TabbedPanelLeft.getIndex()) != null) && index == 1) {
						configPanel = new ConfigPanel(leftPanel);
						tabbedPaneRight.setComponentAt(tabbedPaneRight.getTabCount()-1, configPanel);
//					}
				}
//				else {
//					configPanel = new ConfigPanel();
//					tabbedPane.setComponentAt(index, configPanel);
//				}
				
			}
		};
		tabbedPaneRight.addChangeListener(changeListener);
		
      add(tabbedPaneRight);
	}
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
	
	class CustomContainerListener implements ContainerListener {
		public void componentAdded(ContainerEvent e) {
//			tabbedPane.setSelectedIndex(0);
			configPanel = new ConfigPanel(leftPanel);
			tabbedPaneRight.addTab("Config", null, configPanel, "");
			tabbedPaneRight.setSelectedIndex(tabbedPaneRight.getTabCount()-1);
		}
		public void componentRemoved(ContainerEvent e) {
			
		}
	}
	
	class LeftPaneContainerListener implements ContainerListener {
		public void componentAdded(ContainerEvent e) {
//			configPanel = new ConfigPanel(leftPanel);
//			tabbedPane.addTab("Config", null, configPanel, "");
			System.out.println("LeftPaneContainerListener Tabbed index: " + index);
			tabbedPaneRight.setSelectedIndex(0);
		}
		public void componentRemoved(ContainerEvent e) {
			
		}
	}
	
}
