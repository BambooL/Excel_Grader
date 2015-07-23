package test;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import test.Test;


public class ShortCutsEx extends JFrame {

	private JPanel panel;
	private JTextArea area;
    public ShortCutsEx() {
        
        initUI();
    }

    public void initUI() {
    	panel = (JPanel) getContentPane();
    	JScrollPane spane = new JScrollPane();
        spane.getViewport().add(area);
        setTitle("Automatic Grader");
        setSize(360, 250);
//        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        createMenuBar();
        panel.setLayout(new FlowLayout());

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
        JTextField scoreset = new JTextField("Score Set, Example: A1:5, D10:10");
        JTextField roundset = new JTextField("Rounding, Example: A1:0.01, D10:0.05");
        JTextField result = new JTextField();
        panel.add(assignbtn, BorderLayout.WEST);
        panel.add(assignField);
        panel.add(answerbtn, BorderLayout.WEST);
        panel.add(answerField);
        panel.add(scorebtn, BorderLayout.WEST);
        panel.add(scoreField);
        panel.add(scoreset, BorderLayout.WEST);
        panel.add(roundset, BorderLayout.WEST);
        panel.add(runbtn, BorderLayout.WEST);
        result.setPreferredSize( new Dimension( 500, 500 ) );
        panel.add(result);
        
        
        
        
        assignbtn.addActionListener(new ActionListener() {

        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// TODO Auto-generated method stub
        		String path = null;
    	        JFileChooser fileChooser = new JFileChooser();
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
    	        JFileChooser fileChooser = new JFileChooser();
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
    	        JFileChooser fileChooser = new JFileChooser();
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {//用户点击了确定  
                    path = fileChooser.getSelectedFile().getAbsolutePath();//取得路径选择  
        		scoreField.setText(path);
        	}
        }
        });
        
        runbtn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {

        		String assignPath = assignField.getText();
        		String answerPath = answerField.getText();
        		String scorePath = answerField.getText();
        		
        		Test fly = new Test();
        		try {
					fly.exec(assignPath, answerPath, scorePath);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
        		
        }
        });
        
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
            Logger.getLogger(ShortCutsEx.class.getName()).log(
                    Level.SEVERE, null, ex);
        }

        return content;
    }

    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                
                ShortCutsEx ex = new ShortCutsEx();
                ex.setVisible(true);
            }
        });
    }
}