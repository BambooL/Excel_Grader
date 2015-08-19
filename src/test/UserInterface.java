package test;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import test.Test;


public class UserInterface extends JFrame {

	private JPanel panel = new JPanel();
	private JTextArea area;
    public UserInterface() {
        
        initUI();
    }

    public void initUI() {
    	panel = (JPanel) getContentPane();
    	
    	JTabbedPane tabbedPane = new JTabbedPane();
    	JComponent panel1 = makeTextPanel("");
    	tabbedPane.addTab("Tab 1", null, panel1,
                "Does nothing");
    	tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
         
        JComponent panel2 = makeTextPanel("");
        tabbedPane.addTab("Tab 2", null, panel2,
                "Does twice as much nothing");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        
//     panel1 layout
        panel1.setLayout(new FlowLayout());
        
        JButton assignGenbtn = new JButton("Browse Assignment Path");
        JButton assignTembtn = new JButton("Browse Assignment Template");
        JButton namebtn = new JButton("Browse Name List");
        JButton generatebtn = new JButton("Generate");
        final JTextField assignGenField = new JTextField();
        final JTextField assignTemField = new JTextField();
        final JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(320, 20));
        assignGenField.setPreferredSize(new Dimension(320, 20));
        assignTemField.setPreferredSize(new Dimension(320, 20));
        panel1.add(assignGenbtn);
        panel1.add(assignGenField);
        panel1.add(assignTembtn);
        panel1.add(assignTemField);  
        panel1.add(namebtn);
        panel1.add(nameField);
        panel1.add(generatebtn);
//      panel 1     
    	
	  	assignGenbtn.addActionListener(new ActionListener() {
	      	
	      	@Override
	      	public void actionPerformed(ActionEvent e) {
	      		// TODO Auto-generated method stub
	      		String path = null;
	      		
	  	        JFileChooser fileChooser = new JFileChooser(".");
	  	        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	              if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {//用户点击了确定  
	                  path = fileChooser.getSelectedFile().getAbsolutePath();//取得路径选择  
	      		assignGenField.setText(path);
	      		
	      	}
	      }
	      });
  	
	  	assignTembtn.addActionListener(new ActionListener() {
	
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		// TODO Auto-generated method stub
	    		String path = null;
		        JFileChooser fileChooser = new JFileChooser(".");
	            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {//用户点击了确定  
	                path = fileChooser.getSelectedFile().getAbsolutePath();//取得路径选择  
	    		assignTemField.setText(path);
	    	}
	    }
	    });
	  	
	  	namebtn.addActionListener(new ActionListener() {
	  		
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		// TODO Auto-generated method stub
	    		String path = null;
		        JFileChooser fileChooser = new JFileChooser(".");
	            if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {//用户点击了确定  
	                path = fileChooser.getSelectedFile().getAbsolutePath();//取得路径选择  
	    		nameField.setText(path);
	    	}
	    }
	    });
	  	
	  	generatebtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		Generate g = new Generate();
        		try {
					g.exec(assignGenField.getText(), assignTemField.getText(), nameField.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        		
        		
        		
        }
        });
  	
        
        
//     panel2 layout
    	JScrollPane spane = new JScrollPane();
        spane.getViewport().add(area);
        setTitle("Automatic Grader");
        setSize(620, 750);
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

//        createMenuBar();
        panel2.setLayout(new FlowLayout());

        JButton assignbtn = new JButton("Browse Assignment Path");
        JButton answerbtn = new JButton("Browse Answer File");
        JButton scorebtn = new JButton("Browse Score Template File");
        JButton runbtn = new JButton("Run");
        final JTextField assignField = new JTextField();
        assignField.setPreferredSize(new Dimension(320, 20));
        final JTextField answerField = new JTextField();
        answerField.setPreferredSize(new Dimension(320, 20));
        final JTextField scoreField = new JTextField();
        scoreField.setPreferredSize(new Dimension(320, 20));
        final JTextField scoreset = new JTextField("Score Set, Example: A1:5, D10:10");
        scoreset.setPreferredSize(new Dimension(200, 20));
        final JTextField roundset = new JTextField("Rounding, Example: A1:0.01, D10:0.05");
        roundset.setPreferredSize(new Dimension(200, 20));
        final JTextArea result = new JTextArea();
        result.setEditable(false);
        panel2.add(assignbtn);
        panel2.add(assignField);
        panel2.add(answerbtn);
        panel2.add(answerField);
        panel2.add(scorebtn);
        panel2.add(scoreField);
        panel2.add(scoreset);
        panel2.add(roundset);
        panel2.add(runbtn); 
        panel2.add(result);
        JScrollPane scroller = new JScrollPane(result);
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setPreferredSize( new Dimension( 500, 500 ) );
        panel2.add(scroller);
        panel2.setVisible (true);
        result.setText("Here is the report:\n");
        
    	panel.add(tabbedPane);
    	tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    	
    	
        
//        panel 2
    	
        assignbtn.addActionListener(new ActionListener() {
        	
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        		String path = null;
        		
    	        JFileChooser fileChooser = new JFileChooser(".");
    	        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {//用户点击了确定  
                    path = fileChooser.getSelectedFile().getAbsolutePath();//取得路径选择  
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
        
        runbtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		File reportFolder = new File("./report/");
        		for(File file: reportFolder.listFiles()) file.delete();
        		String assignPath = assignField.getText();
        		String answerPath = answerField.getText();
        		String scorePath = scoreField.getText();
        		String scoreSet = scoreset.getText();
        		String roundSet = roundset.getText();
        		
        		Test fly = new Test();
        		try {
        			
					fly.exec(assignPath, answerPath, scorePath, scoreSet, roundSet);
					result.setPreferredSize( new Dimension( 500, 10000 ) );
					printReport(result);
					
				    
					
				}  catch (FileNotFoundException ex) {
				    System.out.println("no such file exists");
				}
				catch (IOException ex) {
				    System.out.println("unkownerror");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        		
        		
        		
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

//	private void createMenuBar() {
//        
//        JMenuBar menubar = new JMenuBar();
//        
//        ImageIcon iconNew = new ImageIcon("new.png");
//        ImageIcon iconOpen = new ImageIcon("open.png");
//        ImageIcon iconSave = new ImageIcon("save.png");
//        ImageIcon iconExit = new ImageIcon("exit.png");
//
//        JMenu fileMenu = new JMenu("File");
//
//        JMenu impMenu = new JMenu("Import");
//
//        JMenuItem assignMi = new JMenuItem("Import Assignment...");
//        JMenuItem answerMi = new JMenuItem("Import Answer...");
//        JMenuItem scoreMi = new JMenuItem("Import Score...");
//        
//        
////        String assignPath;
////        assignMi.addActionListener(new OpenFileAction());
////        String answerPath = answerMi.addActionListener(new OpenFileAction());
////        String scorePath = scoreMi.addActionListener(new OpenFileAction());
//
//        
//        impMenu.add(assignMi);
//        impMenu.add(answerMi);
//        impMenu.add(scoreMi);
//        
//        
//
//        JMenuItem newMi = new JMenuItem("New", iconNew);
//        JMenuItem openMi = new JMenuItem("Open", iconOpen);
//        JMenuItem saveMi = new JMenuItem("Save", iconSave);
//
//        JMenuItem exitMi = new JMenuItem("Exit", iconExit);
//        exitMi.setToolTipText("Exit application");
//
//        exitMi.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent event) {
//                System.exit(0);
//            }
//        });
//
//        fileMenu.add(newMi);
//        fileMenu.add(openMi);
//        fileMenu.add(saveMi);
//        fileMenu.addSeparator();
//        fileMenu.add(impMenu);
//        fileMenu.addSeparator();
//        fileMenu.add(exitMi);
//
//        menubar.add(fileMenu);
//
//        setJMenuBar(menubar);        
//    }
    
    
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

    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
            	UserInterface ex = new UserInterface();
                ex.setVisible(true);
            }
        });
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