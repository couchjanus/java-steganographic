
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.SwingUtilities;
import java.util.Properties;
import javax.swing.filechooser.*;
import javax.swing.JPanel;

import javax.swing.JToolBar;

public class Main extends JPanel
implements ActionListener {
	static private String newline = "\n";
    private JTextArea log;
    private JTextArea textArea;
    private JFileChooser fc;
    static final private String ATTACH = "attach";
    static final private String ANALIZE = "analize";
    static final private String SANIZE = "sanaze";
    
    DTPicture picture1, picture2, picture3;
    PictureTransferHandler picHandler;
	public Main() {
		super(new BorderLayout());
		
		picHandler = new PictureTransferHandler();
		
		DTPicture.setInstallInputMapBindings(false);
		
		log = new JTextArea(5,20);
//        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        
        JScrollPane logScrollPane = new JScrollPane(log);

        JPanel picturespanel = new JPanel(new GridLayout(4, 3));
        
        picture1 = new DTPicture(null);
        picture1.setTransferHandler(picHandler);
        picturespanel.add(picture1);
        
        picture2 = new DTPicture(null);
        picture2.setTransferHandler(picHandler);
        picturespanel.add(picture2);
        
        picture3 = new DTPicture(null);
        picture3.setTransferHandler(picHandler);
        picturespanel.add(picture3);
        
        JToolBar toolBar = new JToolBar("Buttons list");
        
        addButtons(toolBar);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        
//        JButton sendButton = new JButton("Attach...");
//        sendButton.addActionListener(this);
        
        textArea = new JTextArea(5,20);
        textArea.setEditable(true);
        JScrollPane textScrollPane = new JScrollPane(textArea);
        setPreferredSize(new Dimension(800, 400));

        add(toolBar, BorderLayout.PAGE_START);
        add(picturespanel, BorderLayout.LINE_START);
        add(textScrollPane, BorderLayout.LINE_END);
        add(logScrollPane, BorderLayout.PAGE_END);
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
	}
	
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
	        JFrame frame = new JFrame("Main");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Add content to the window.
	        frame.add(new Main());

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
