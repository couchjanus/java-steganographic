import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.FlowLayout;

public class ConfigPanel extends JComponent{
	
	ChiComponent chiComponent;
	
	public ChiComponent getChiConfig() {
		
		return chiComponent;
	}
	
	public ConfigPanel(TabbedPanelLeft leftPanel) {
		super();
		
		chiComponent = new ChiComponent();
		chiComponent.setBorder(BorderFactory.createTitledBorder("Config Chi Square"));
		PicturePanel picturePanel = leftPanel.getPicturePanel();
		SelectedRegion selectedRegion = picturePanel.getSelectedRegion();
		ImgSizeComponent imgSizeComponent = new ImgSizeComponent(selectedRegion);
		imgSizeComponent.setBorder(BorderFactory.createTitledBorder("Selected Image Region"));

		setLayout(new FlowLayout());
		add(chiComponent);
		add(imgSizeComponent);

        
	}

}
