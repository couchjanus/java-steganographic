
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
	
	JTabbedPane tabbedPane = new JTabbedPane();
	private static int index;
		
	private Plot2DPanel chiSquarePanel;
	
	private ConfigPanel configPanel;
	
	private JPanel welcome = new JPanel();
	
	TabbedPanelLeft leftPanel;
	
	public static int getIndex() {
		return index;
	}
	public TabbedPanelRight(TabbedPanelLeft leftPanel) {
	  super();
	  this.leftPanel = leftPanel;
	  
	  setLayout(new BorderLayout());
	  
//	  System.out.println(leftPanel.getPicturePanel().getScrollPanel().getSelectedRegion());
//	  leftPanel.getPicturePanel().getScrollPanel().getSelectedRegion().addContainerListener(new LeftPaneContainerListener());
	  
	  leftPanel.getPicturePanel().getScrollPanel().addContainerListener(new CustomContainerListener());
	  
	  RightToolbar toolBar = new RightToolbar(tabbedPane);
	  
	  add(toolBar, BorderLayout.PAGE_START);
	  tabbedPane.addTab("Welcome", null, welcome, "");
	  
//	  configPanel = new ConfigPanel();
	  
//	  configPanel = new ConfigPanel(leftPanel);
	  
//      tabbedPane.addTab("Configs", null, configPanel, "");
	  
      chiSquarePanel = new Plot2DPanel();
      PresetsPanel presetsPanel = new PresetsPanel(configPanel, chiSquarePanel);
      tabbedPane.addTab("Presets", null, presetsPanel, "");
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
       
      JComponent panel2 = makeTextPanel("Histogram");
      tabbedPane.addTab("Histogram", null, panel2,
              "Does Histogram as much nothing");
      tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
      
      tabbedPane.addTab("Chi-squere", null, chiSquarePanel, null);
       
      tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
      
      
      ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				index = sourceTabbedPane.getSelectedIndex();
				System.out.println("Tabbed index: " + index);
				System.out.println("Tabbed left: " + TabbedPanelLeft.getIndex());
//			
				if(ImgList.images.size() > 0){
//					if((ImgList.images.get(TabbedPanelLeft.getIndex()) != null) && index == 1) {
						configPanel = new ConfigPanel(leftPanel);
						tabbedPane.setComponentAt(tabbedPane.getTabCount()-1, configPanel);
//					}
				}
//				else {
//					configPanel = new ConfigPanel();
//					tabbedPane.setComponentAt(index, configPanel);
//				}
				
			}
		};
	  tabbedPane.addChangeListener(changeListener);
		
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
	
	class CustomContainerListener implements ContainerListener {
		public void componentAdded(ContainerEvent e) {
//			tabbedPane.setSelectedIndex(0);
			configPanel = new ConfigPanel(leftPanel);
			tabbedPane.addTab("Config", null, configPanel, "");
			tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
		}
		public void componentRemoved(ContainerEvent e) {
			
		}
	}
	
	class LeftPaneContainerListener implements ContainerListener {
		public void componentAdded(ContainerEvent e) {
//			configPanel = new ConfigPanel(leftPanel);
//			tabbedPane.addTab("Config", null, configPanel, "");
			System.out.println("LeftPaneContainerListener Tabbed index: " + index);
			tabbedPane.setSelectedIndex(0);
		}
		public void componentRemoved(ContainerEvent e) {
			
		}
	}
	
}
