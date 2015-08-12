package test;

import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import test.UserInterface;
import test.Test;


public class Execution implements Runnable{
	
	public Thread t;
	public Boolean _run = true;
	
	String assignPath;
	String answerPath;
	String scorePath;
	String scoreSet;
	String roundSet;
	
//	void Execution( String name){
//	       threadName = "oneExecution";
//	       System.out.println("Creating " +  threadName );
//	}

   public void run() {
	    if(!_run){
	    	return;
	    }
	    while (_run) {
	    	System.out.print("Run Executed!");
		    File reportFolder = new File("./report");
			File afterFolder = new File("./after");
			File[] files = reportFolder.listFiles();
			if (files!=null && files.length!=0) {for (File f: files) f.delete();}
			files = afterFolder.listFiles();
			if (files!=null && files.length!=0) {for (File f: files) f.delete();}
			
			Test fly = new Test();

			try {
				fly.exec(assignPath, answerPath, scorePath, scoreSet, roundSet);
				
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
	    
   }
	    

   public void start ()
   {
     
      if (t == null)
      {
         t = new Thread ();
         t.start ();
      }
   }
   void suspend() {
  
   }
   synchronized void resume() {
  
       notify();
   }

	
	public void stop(){	
	}

}
