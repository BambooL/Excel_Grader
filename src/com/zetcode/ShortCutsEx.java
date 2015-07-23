package com.zetcode;

import java.awt.EventQueue;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ShortCutsEx extends JFrame {

	private JPanel panel;
	private JTextArea area;
    public ShortCutsEx() {
        
        initUI();
    }

    private void initUI() {
    	panel = (JPanel) getContentPane();
    	area = new JTextArea();
    	JScrollPane spane = new JScrollPane();
        spane.getViewport().add(area);
        
        createMenuBar();

        setTitle("Submenu");
        setSize(360, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        assignMi.addActionListener(new OpenFileAction());

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

    private class OpenFileAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            JFileChooser fdia = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter("Java files", 
                    "java");
            fdia.addChoosableFileFilter(filter);

            int ret = fdia.showDialog(panel, "Open file");

            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fdia.getSelectedFile();
                String text = readFile(file);
                area.setText(text);
            }
        }
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