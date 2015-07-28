package test;
import java.io.*;

import org.apache.poi.xssf.usermodel.*;

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;

import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import test.Assignment;

public class Test
{
   private static final int CELL_TYPE_NUMERIC = 0;

public void exec(String assignsPath, String answerPath, String scorePath, String score, String rounding)throws Exception
   { 
	  File assignFolder = new File(assignsPath);
      File Answer = new File(answerPath);
      FileInputStream fIP_answer = new FileInputStream(Answer);      
      //Get the workbook instance for XLSX file 
      XSSFWorkbook wbAnswer = new XSSFWorkbook(fIP_answer);    
      FormulaEvaluator evaluator_answer = wbAnswer.getCreationHelper().createFormulaEvaluator(); 
      File[] files = assignFolder.listFiles();
      XSSFSheet spreadsheet_answer = wbAnswer.getSheetAt(0);
      Iterator < Row > rowIterator1 = spreadsheet_answer.iterator();
      HashMap<String, Cell> hsAnswer = new HashMap<String, Cell>();
      HashMap<String, Double> hsPoint = new HashMap<String, Double>();
      HashMap<String, Double> hsRound = new HashMap<String, Double>();
      Double round;
      // create hashmap for round
      
      if(!rounding.trim().isEmpty()){
    	  String[] rounds = rounding.trim().split(",");
          
          int i = 0;
          try {
    	      while (i < rounds.length && rounds[i] != ""  ) {
    	    	  String[] item = rounds[i].split(":");
    	    	  String key = LetterToInt(item[0].trim());
    	    	  Double value = Double.parseDouble(item[1]);
    	    	  hsRound.put(key, value);
    	    	  i++;
    	      } 
    	  }  
          catch(ArrayIndexOutOfBoundsException ex) {
    	    	  JOptionPane.showMessageDialog(null, "Please Set the Round Following Examples!");
    	    	  return;
    	  }
      }
      
      
   // create hashmap for answer
      while (rowIterator1.hasNext()) 
      {
         XSSFRow r = (XSSFRow) rowIterator1.next();
         Iterator < Cell > cellIterator1 = r.cellIterator();
         while ( cellIterator1.hasNext() ) 
         {
            Cell cell = cellIterator1.next();
            String key = Integer.toString(cell.getColumnIndex()) + Integer.toString(cell.getRowIndex());
            hsAnswer.put(key, cell);
           
         }
      }
      
      // create hashmap for point
      if (!scorePath.trim().isEmpty()) {
    	  System.out.println(scorePath);
    	  File Point = new File(scorePath);
    	  FileInputStream fIP_point = new FileInputStream(Point);
    	  XSSFWorkbook wbPoint = new XSSFWorkbook(fIP_point);
    	  XSSFSheet spreadsheet_point = wbPoint.getSheetAt(0);
    	  Iterator < Row > rowIterator3 = spreadsheet_point.iterator();
    	  while (rowIterator3.hasNext()) 
          {
             XSSFRow r = (XSSFRow) rowIterator3.next();
             Iterator < Cell > cellIterator3 = r.cellIterator();
             while ( cellIterator3.hasNext() ) 
             {
                Cell cell = cellIterator3.next();
                String key = Integer.toString(cell.getColumnIndex()) + Integer.toString(cell.getRowIndex());
                if (cell.getCellType() == CELL_TYPE_NUMERIC){
                	hsPoint.put(key, cell.getNumericCellValue());
                	System.out.print(key + " ");
                	System.out.println(cell.getNumericCellValue());
                }
                
             }
          }     
      } 
      else if(!score.trim().isEmpty()){
    	  String[] scores = score.trim().split(",");
          
          int j = 0;
          try {
    	      while (j < scores.length && scores[j] != ""  ) {
    	    	  String[] item = scores[j].split(":");
    	    	  String key = LetterToInt(item[0].trim());
    	    	  Double value = Double.parseDouble(item[1]);
    	    	  hsPoint.put(key, value);
              	  System.out.print(key + " ");
              	  System.out.println(value);
    	    	  j++;
    	      } 
    	  }  catch(ArrayIndexOutOfBoundsException ex) {
    	    	  JOptionPane.showMessageDialog(null, "Please Set the Score Following Examples!");
    	    	  return;
    	  } 
    	  
       }
      else {
	    	 JOptionPane.showMessageDialog(null, "Please Input the Score Path or Set Score!"); 
	    	 return;
	  }
      
      
      
      
      for (int k=0; k<files.length; k++) {
    	  Assignment a = new Assignment();
    	  a.checkAssign(files[k], hsAnswer, wbAnswer, hsRound, hsPoint);
      }
   }
   
   public String IntToLetters(int value)
   {
       String result = new String();
       while (--value >= 0)
       {
           result = (char)('A' + value % 26 +1) + result;
           value /= 26;
       }
       return result ;
   }
   
   public String LetterToInt(String value) {
	   value = value.toUpperCase();
	   String result = "";
	   StringBuilder column = new StringBuilder();
	   int i = 0;
	   int columnInt = 0;
	   int base = 1;
	   
	   while (Character.isLetter(value.charAt(i))){
		   column.append(value.charAt(i));
		   i++;
	   }
	   String row = Integer.toString((Integer.parseInt(value.substring(i)) - 1)) ;
	   String r = column.reverse().toString();
	   i = 0;
	   while (i < r.length()) {
		   columnInt += (r.charAt(i)-'A'+1) * base -1;
		   i ++;
		   base *= 26;
	   }
	   result = Integer.toString(columnInt) + row;
	   return result;
   }
   
   
   public Double WriteStringToFile(String cell, Double point, Double total) {  
       try {    
    	   FileWriter fw = new FileWriter("report.txt",true); 
    	   fw.write("\n  Cell "+ cell + " is wrong, " + point.toString() + "pt is deducted!");//appends the string to the file
    	   fw.close();
      
       } catch (IOException ioe) {  
    	   System.err.println("IOException: " + ioe.getMessage()); 
       }  
       
       return total-point;
   }  
   
   public boolean correct(Double a, Double b, Double round) {
	   if (Math.abs(a-b) <= round) {
		   return true;
	   } else {
		   return false;
	   }
   }
   
   public Double getVal(Cell cell, XSSFWorkbook wb) {
	   Double result = 0.0;
	   if (cell.getCellType() == Cell.CELL_TYPE_BLANK || cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
		   switch (cell.getCellType()) 
	       {
	  	      case Cell.CELL_TYPE_BLANK:
	  	    	result = (double) 21211411;
	  	      break;
	          case Cell.CELL_TYPE_NUMERIC:
//	          	System.out.println(cell.getNumericCellValue());
//	          	System.out.println(hsAnswer.get(key).getNumericCellValue());
	        	  result = cell.getNumericCellValue();
	          break;
	          case Cell.CELL_TYPE_FORMULA:
	          	FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
	          	CellValue cellValue = evaluator.evaluate(cell);
	          	result = cellValue.getNumberValue();
	          break;
	       	  
	       }
	   }
	   
	   return result;
   }
		 

  		 
   

	   
}
    
   
   
//   public Double 