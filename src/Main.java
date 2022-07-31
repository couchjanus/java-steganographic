
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Properties;
import javax.swing.filechooser.*;

public class Main extends JFrame
implements ActionListener {
	static private String newline = "\n";
    private JTextArea log;
    private JTextArea textArea;
    private JFileChooser fc;
    static final private String ATTACH = "attach";
    static final private String ANALIZE = "analize";
    static final private String SANIZE = "sanaze";
    
    private JPanel mainPanel, leftPanel, rightPanel;
    
    DTPicture picture1, picture2, picture3;
    PictureTransferHandler picHandler;
	public Main() {
		super("Staganographsc Analizer");
		
		mainPanel = new JPanel(new BorderLayout());
		
		mainPanel.setBackground(Color.RED);
		
		mainPanel.setLayout(new GridLayout(1,2));
		
		
		picHandler = new PictureTransferHandler();
		
		DTPicture.setInstallInputMapBindings(false);
		
		log = new JTextArea(5,20);
//        log.setMargin(new Insets(5,5,5,5));
        
		log.setEditable(false);
        
//        JScrollPane logScrollPane = new JScrollPane(log);

//        JPanel picturespanel = new JPanel(new GridLayout(4, 3));
        
//        picture1 = new DTPicture(null);
//        picture1.setTransferHandler(picHandler);
//        picturespanel.add(picture1);
//        
//        picture2 = new DTPicture(null);
//        picture2.setTransferHandler(picHandler);
//        picturespanel.add(picture2);
//        
//        picture3 = new DTPicture(null);
//        picture3.setTransferHandler(picHandler);
//        picturespanel.add(picture3);
//        
//        JToolBar toolBar = new JToolBar("Buttons list");
//        
//        addButtons(toolBar);
//        toolBar.setFloatable(false);
//        toolBar.setRollover(true);
        
//        JButton sendButton = new JButton("Attach...");
//        sendButton.addActionListener(this);
        
//        textArea = new JTextArea(5,20);
//        textArea.setEditable(true);
//        JScrollPane textScrollPane = new JScrollPane(textArea);
//        setPreferredSize(new Dimension(800, 400));
        
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(1,1));
//        leftPanel.setBackground(Color.GREEN);
        

        JLabel label = new JLabel("Find What:");;
        JTextField textField = new JTextField();
        JCheckBox caseCheckBox = new JCheckBox("Match Case");
        JCheckBox wrapCheckBox = new JCheckBox("Wrap Around");
        JCheckBox wholeCheckBox = new JCheckBox("Whole Words");
        JCheckBox backCheckBox = new JCheckBox("Search Backwards");
        JButton findButton = new JButton("Find");
        
        findButton.setActionCommand(ATTACH);
        findButton.setToolTipText("Open file");
        findButton.addActionListener(this);
      
        JButton cancelButton = new JButton("Cancel");
        caseCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        wrapCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        wholeCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        backCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        
        GroupLayout layout = new GroupLayout(leftPanel);
        
        leftPanel.setLayout(layout);
        
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createSequentialGroup()
            .addComponent(label)
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(textField)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(caseCheckBox)
                        .addComponent(wholeCheckBox))
                    .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(wrapCheckBox)
                        .addComponent(backCheckBox))))
            .addGroup(layout.createParallelGroup(LEADING)
                .addComponent(findButton)
                .addComponent(cancelButton))
        );
       
        layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

        layout.setVerticalGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(BASELINE)
                .addComponent(label)
                .addComponent(textField)
                .addComponent(findButton))
            .addGroup(layout.createParallelGroup(LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(caseCheckBox)
                        .addComponent(wrapCheckBox))
                    .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(wholeCheckBox)
                        .addComponent(backCheckBox)))
                .addComponent(cancelButton))
        );

       
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(1,1));
        JTabbedPane tabbedPane = new JTabbedPane();
//        ImageIcon icon = createImageIcon("images/middle.gif");
         
        JComponent panel1 = makeTextPanel("Panel #1");
        tabbedPane.addTab("Tab 1", null, panel1,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        JComponent panel2 = makeTextPanel("Panel #2");
        tabbedPane.addTab("Tab 2", null, panel2,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
         
        JComponent panel3 = makeTextPanel("Panel #3");
        tabbedPane.addTab("Tab 3", null, panel3,
                "Still does nothing");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
         
        JComponent panel4 = makeTextPanel(
                "Panel #4 (has a preferred size of 410 x 50).");
        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Tab 4", null, panel4,
                "Does nothing at all");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        //Add the tabbed pane to this panel.
        rightPanel.add(tabbedPane);
         
        //The following line enables to use scrolling tabs.
        
//        rightPanel.setBackground(Color.BLUE);
        
        mainPanel.add(leftPanel, BorderLayout.LINE_START);
        mainPanel.add(rightPanel, BorderLayout.LINE_END);
        
        Container pane = getContentPane();
        
        setContentPane(pane);
        pane.add(mainPanel);
        
//        add(toolBar, BorderLayout.PAGE_START);
//        add(picturespanel, BorderLayout.LINE_START);
//        add(textScrollPane, BorderLayout.LINE_END);
//        add(logScrollPane, BorderLayout.PAGE_END);
//        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
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
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }
     
    /** Returns an ImageIcon, or null if the path was invalid. */
//    protected static ImageIcon createImageIcon(String path) {
//        java.net.URL imgURL = TabbedPaneDemo.class.getResource(path);
//        if (imgURL != null) {
//            return new ImageIcon(imgURL);
//        } else {
//            System.err.println("Couldn't find file: " + path);
//            return null;
//        }
//    }
	
	protected void addButtons(JToolBar toolBar) {
		JButton button = null;
		
		button = new JButton("Attach file");
		
		button.setActionCommand(ATTACH);
		button.setToolTipText("Open file");
		button.addActionListener(this);
		toolBar.add(button);
		
		toolBar.addSeparator();
        button = new JButton("Analize File");
		
		button.setActionCommand(ANALIZE);
		button.setToolTipText("Analize File");
		button.addActionListener(this);
		toolBar.add(button);
		
		toolBar.addSeparator();
		
		button = new JButton("Sanize file");
		
		button.setActionCommand(SANIZE);
		button.setToolTipText("Sanize file");
		button.addActionListener(this);
		toolBar.add(button);
		
		// toolBar.addSeparator();
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		
		String message = null;
		if (ATTACH.equals(cmd)) {
        //Set up the file chooser.
        if (fc == null) {
            fc = new JFileChooser();

	    //Add a custom file filter and disable the default
	    //(Accept All) file filter.
            fc.addChoosableFileFilter(new ImageFilter());
            fc.setAcceptAllFileFilterUsed(false);

	    //Add custom icons for file types.
            fc.setFileView(new ImageFileView());

	    //Add the preview pane.
            fc.setAccessory(new ImagePreview(fc));
        }
        int returnVal = fc.showDialog(Main.this,
                "Attach");

		//Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) 
		{
			
		File file = fc.getSelectedFile();
		
		Path path = Paths.get(file.getPath());
		
		
		
		log.append("Current Directory: " + fc.getCurrentDirectory()
		+ "." + newline);
		
		log.append("Attaching file: " + file.getName()
		+ "." + newline);
		
		log.append("Attaching file path: " + file.getPath()
		+ "." + newline);
		
		log.append("Attaching file size: " + file.length()
		+ "." + newline);
		
		
		log.append("Type Description: " + fc.getTypeDescription(file)
		+ "." + newline);
		
//		
		log.append("file read permission: " + file.canRead()
		+ "." + newline);
		log.append("file write permission: " + file.canWrite()
		+ "." + newline);
		log.append("file execute permission: " + file.canExecute()
		+ "." + newline);
		
		log.append("Attaching file size: " + Utils.getExtension(file)
		+ "." + newline);
		
		try {
			BasicFileAttributeView bfv = Files.getFileAttributeView(path, BasicFileAttributeView.class);
			BasicFileAttributes bfa = bfv.readAttributes();
			
			log.append("file creation: " + bfa.creationTime()
			+ "." + newline);
			log.append("file last access: " + bfa.lastAccessTime()
			+ "." + newline);
			log.append("file size: " + bfa.size()
			+ "." + newline);
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		
		
		} else {
		log.append("Attachment cancelled by user." + newline);
		}
		log.setCaretPosition(log.getDocument().getLength());
		
		//Reset the file chooser for the next time it's shown.
		fc.setSelectedFile(null);
		} else if(ANALIZE.equals(cmd)) {
			message = "Bla blas bla analize";
			log.append(message);
		}else if (SANIZE.equals(cmd)) {
			message = "Bla blas bla sanize";
			log.append(message);
		}
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
