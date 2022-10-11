
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.*;
import javax.swing.*;
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
	ArrayList<String> imageList;
		
	private Plot2DPanel chiSquarePanel;
	
	private ConfigPanel configPanel;
	private final Coords coords;
	
	public TabbedPanelRight(ArrayList<String> imageList, Coords coords) {
	  super();
	  setLayout(new BorderLayout());
	  this.imageList = imageList;
	  this.coords = coords;
	  
	  RightToolbar toolBar = new RightToolbar(tabbedPane, imageList);
	  
	  add(toolBar, BorderLayout.PAGE_START);
	  
	  configPanel = new ConfigPanel(coords);
	  
      tabbedPane.addTab("Congigs", null, configPanel, "");
	  
      chiSquarePanel = new Plot2DPanel();
      PresetsPanel presetsPanel = new PresetsPanel(configPanel, chiSquarePanel, imageList, coords);
      tabbedPane.addTab("Presets", null, presetsPanel, "");
      tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
       
      JComponent panel2 = makeTextPanel("Histogram");
      tabbedPane.addTab("Histogram", null, panel2,
              "Does Histogram as much nothing");
      tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
      
      tabbedPane.addTab("Chi-squere", null, chiSquarePanel, null);
       
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
	
}
