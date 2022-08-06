import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame
implements ActionListener {
	
    private JPanel mainPanel, leftPanel, rightPanel;
    
    DTPicture picture1, picture2, picture3;
    PictureTransferHandler picHandler;
	public Main() {
		super("Staganographsc Analizer");
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.RED);
		mainPanel.setLayout(new GridLayout(1,2));
		leftPanel = new TabbedPanelLeft();
        rightPanel = new TabbedPanelRight();

        mainPanel.add(leftPanel, BorderLayout.LINE_START);
        mainPanel.add(rightPanel, BorderLayout.LINE_END);
        
        Container pane = getContentPane();   
        setContentPane(pane);
        pane.add(mainPanel);
	}
	
	 public JMenuBar createMenuBar() {
		    JMenuItem menuItem = null;
		    JMenuBar menuBar = new JMenuBar();
		    JMenu mainMenu = new JMenu("Edit");
		    mainMenu.setMnemonic(KeyEvent.VK_E);
		    TransferActionListener actionListener = new TransferActionListener();

		    menuItem = new JMenuItem("Cut");
		    menuItem.setActionCommand((String) TransferHandler.getCutAction()
		        .getValue(Action.NAME));
		    menuItem.addActionListener(actionListener);
		    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
		        ActionEvent.CTRL_MASK));
		    menuItem.setMnemonic(KeyEvent.VK_T);
		    mainMenu.add(menuItem);
		    menuItem = new JMenuItem("Copy");
		    menuItem.setActionCommand((String) TransferHandler.getCopyAction()
		        .getValue(Action.NAME));
		    menuItem.addActionListener(actionListener);
		    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
		        ActionEvent.CTRL_MASK));
		    menuItem.setMnemonic(KeyEvent.VK_C);
		    mainMenu.add(menuItem);
		    menuItem = new JMenuItem("Paste");
		    menuItem.setActionCommand((String) TransferHandler.getPasteAction()
		        .getValue(Action.NAME));
		    menuItem.addActionListener(actionListener);
		    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
		        ActionEvent.CTRL_MASK));
		    menuItem.setMnemonic(KeyEvent.VK_P);
		    mainMenu.add(menuItem);

		    menuBar.add(mainMenu);
		    return menuBar;
		  }

	public void actionPerformed(ActionEvent e) {

	}
	
	 private static void createAndShowGUI() {
	        //Create and set up the window.
	        Main frame = new Main();
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setPreferredSize(new Dimension(800, 400));
	        

	        //Add content to the window.
	        frame.setJMenuBar(frame.createMenuBar());

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }
	 
	 public static void main(String[] args) {
	        //Schedule a job for the event dispatch thread:
	        //creating and showing this application's GUI.
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                //Turn off metal's use of bold fonts
	                UIManager.put("swing.boldMetal", Boolean.FALSE);
	                createAndShowGUI();
	            }
	        });
	 }

}
