import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.FlowLayout;

public class ConfigPanel extends JComponent{
	private final Coords coords;

	public ConfigPanel(Coords coords) {
		super();
		this.coords = coords;
		ChiComponent chiComponent = new ChiComponent();
		chiComponent.setBorder(BorderFactory.createTitledBorder("Config Chi Square"));
		ImgSizeComponent imgSizeComponent = new ImgSizeComponent(coords);
		imgSizeComponent.setBorder(BorderFactory.createTitledBorder("Selected Image Region"));

		setLayout(new FlowLayout());
		add(chiComponent);
		add(imgSizeComponent);

        
	}

}
