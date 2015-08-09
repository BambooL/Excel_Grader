package test;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import test.Test;
import test.Execution;

public class UserInterface extends JFrame {
	
	private JPanel panel;
	private JTextArea area;


    public UserInterface() {
        
        initUI();
    }
    
    public void initUI() {
    	panel = (JPanel) getContentPane();
    	JScrollPane spane = new JScrollPane();
        spane.getViewport().add(area);
        setTitle("Automatic Grader");
        setSize(600, 750);
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenuBar();
        panel.setLayout(new FlowLayout());

        JButton assignbtn = new JButton("Browse Assignment Path");
        JButton answerbtn = new JButton("Browse Answer File");
        JButton scorebtn = new JButton("Browse Score Template File");
        JButton runbtn = new JButton("Run");
        JButton resetbtn = new JButton("Reset");
        final JTextField assignField = new JTextField();
        assignField.setPreferredSize(new Dimension(320, 20));
        final JTextField answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(320, 20));
        final JTextField scoreField = new JTextField();
        scoreField.setPreferredSize(new Dimension(320, 20));
        final JTextField scoreset = new JTextField("Score Set, Example: A1:5, D10:10");
        final JTextField roundset = new JTextField("Rounding, Example: A1:0.01, D10:0.05");
        final JTextField CheckCorrect = new JTextField("CheckBox: C4=200000*E1/(E1+100000)                                        ");
        final JTextArea result = new JTextArea();
        result.setEditable(false);
        panel.add(assignbtn);
        panel.add(assignField);
        panel.add(answerbtn);
        panel.add(answerField);
        panel.add(scorebtn);
        panel.add(scoreField);
        panel.add(scoreset);
        panel.add(roundset);
        panel.add(CheckCorrect);
        panel.add(runbtn); 
        panel.add(resetbtn);
        panel.add(result);
        JScrollPane scroller = new JScrollPane(result);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setPreferredSize( new Dimension( 500, 500 ) );
        panel.add(scroller);
        panel.setVisible (true);

        
        
        
        
        assignbtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        		String path = null;
        		
    	        JFileChooser fileChooser = new JFileChooser(".");
    	        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {//Click on Choose 
                    path = fileChooser.getSelectedFile().getAbsolutePath();//get the path  
        		assignField.setText(path);
        	}
        }
        });
        
        answerbtn.addActionListener(new ActionListener() {

        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        		String path = null;
    	        JFileChooser fileChooser = new JFileChooser(".");
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {//用户点击了确定  
                    path = fileChooser.getSelectedFile().getAbsolutePath();//取得路径选择  
        		answerField.setText(path);
        	}
        }
        });
        
        scorebtn.addActionListener(new ActionListener() {

        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        		String path = null;
    	        JFileChooser fileChooser = new JFileChooser(".");
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {//用户点击了确定  
                    path = fileChooser.getSelectedFile().getAbsolutePath();//取得路径选择  
        		scoreField.setText(path);
        	}
        }
        });
        
        final Execution newexec = new Execution(); 
        final Thread newThread = new Thread(newexec);

        
        runbtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		newexec._run = true;
        		newexec.assignPath = assignField.getText();
        		newexec.answerPath = answerField.getText();
        		newexec.scorePath = scoreField.getText();
        		newexec.scoreSet = scoreset.getText();
        		newexec.roundSet = roundset.getText();
        		
        		newThread.start();
        		    /* Do something. */
        		
        			
        		
        		
        		System.out.println(newexec.assignPath);
    			result.setPreferredSize( new Dimension( 500, 10000 ) );
    	        result.setText(null);
    	        result.setText("Here is the report:\n");
    			try {
					printReport(result);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        }
        });
        
        resetbtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {

        		JOptionPane.showMessageDialog(null, "Stop Success!");
                newThread.interrupt();
                newexec._run = false;
                    // Sleep in order to let the interruption handling end before
                    // exiting the program (else the interruption could be handled
                    // after the main thread ends!).
                
        	}	
        		
        
        });

    
}
    

    public void printReport(JTextArea result) throws IOException {
    	File reportFolder = new File("./report/");
    	File[] files = reportFolder.listFiles();
    	FileReader fileReader;
    	for (int n=0; n<files.length; n++) {
    		fileReader = new FileReader(files[n]);
    	    BufferedReader bufferedReader = new BufferedReader(fileReader);
    	    String inputFile = "";
    	    String textFieldReadable = bufferedReader.readLine();

    	    while (textFieldReadable != null){
    	        inputFile += textFieldReadable + "\n";
    	        textFieldReadable = bufferedReader.readLine();                    
    	    }
    	    
    	    result.setText(result.getText()+ inputFile);
    	}
    	
    }

	private void createMenuBar() {
        
        JMenuBar menubar = new JMenuBar();
        
        ImageIcon iconNew = new ImageIcon("new.png");
        ImageIcon iconOpen = new ImageIcon("open.png");
        ImageIcon iconSave = new ImageIcon("save.png");
        ImageIcon iconExit = new ImageIcon("exit.png");

        JMenu fileMenu = new JMenu("File");

        JMenu impMenu = new JMenu("Import");

        JMenuItem assignMi = new JMenuItem("Import Assignment...");
        JMenuItem answerMi = new JMenuItem("Import Answer...");
        JMenuItem scoreMi = new JMenuItem("Import Score...");
        
        
//        String assignPath;
//        assignMi.addActionListener(new OpenFileAction());
//        String answerPath = answerMi.addActionListener(new OpenFileAction());
//        String scorePath = scoreMi.addActionListener(new OpenFileAction());

        
        impMenu.add(assignMi);
        impMenu.add(answerMi);
        impMenu.add(scoreMi);
        
        

        JMenuItem newMi = new JMenuItem("New", iconNew);
        JMenuItem openMi = new JMenuItem("Open", iconOpen);
        JMenuItem saveMi = new JMenuItem("Save", iconSave);

        JMenuItem exitMi = new JMenuItem("Exit", iconExit);
        exitMi.setToolTipText("Exit application");

        exitMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        fileMenu.add(newMi);
        fileMenu.add(openMi);
        fileMenu.add(saveMi);
        fileMenu.addSeparator();
        fileMenu.add(impMenu);
        fileMenu.addSeparator();
        fileMenu.add(exitMi);

        menubar.add(fileMenu);

        setJMenuBar(menubar);        
    }
    
    
    public String readFile(File file) {

        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(
                    file.getAbsolutePath())));
        } catch (IOException ex) {
            Logger.getLogger(UserInterface.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        return content;
    }

    public void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            	UserInterface ex = new UserInterface();
                ex.setVisible(true);
            }
        });
    }
    
}








