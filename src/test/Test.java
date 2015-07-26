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


public class Test
{
   public void exec(String assignPath, String answerPath, String scorePath, String score, String rounding)throws Exception
   { 
	  
      File Answer = new File(answerPath);
      File Assignment = new File(assignPath);
      File Point = new File(scorePath);
  
      FileInputStream fIP_answer = new FileInputStream(Answer);
      FileInputStream fIP_point = new FileInputStream(Point);
      FileInputStream fIP_assignment = new FileInputStream(Assignment);
      
      //Get the workbook instance for XLSX file 
      XSSFWorkbook wbAnswer = new XSSFWorkbook(fIP_answer);
      XSSFWorkbook wbAssignment = new XSSFWorkbook(fIP_assignment);
      XSSFWorkbook wbPoint = new XSSFWorkbook(fIP_point);
      FormulaEvaluator evaluator_answer = wbAnswer.getCreationHelper().createFormulaEvaluator();
      FormulaEvaluator evaluator_assignment = wbAssignment.getCreationHelper().createFormulaEvaluator();
      
//      if(file.isFile() && file.exists())
//      {
//         System.out.println("openworkbook.xlsx file open successfully.");
//      }
//      else
//      {
//         System.out.println("Error to open openworkbook.xlsx file.");
//      }
      
      
      String checkcell;
      Double round;
      
      XSSFSheet spreadsheet_assignment = wbAssignment.getSheetAt(0);
      XSSFSheet spreadsheet_answer = wbAnswer.getSheetAt(0);
      XSSFSheet spreadsheet_point = wbPoint.getSheetAt(0);
      Iterator < Row > rowIterator1 = spreadsheet_answer.iterator();
      Iterator < Row > rowIterator3 = spreadsheet_point.iterator();
      HashMap<String, Cell> hsAnswer = new HashMap<String, Cell>();
      HashMap<String, Cell> hsPoint = new HashMap<String, Cell>();
      HashMap<String, Double> hsRound = new HashMap<String, Double>();
      
      Double total = 100.0;
      
      // create hashmap for round
      
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
	  }  catch(ArrayIndexOutOfBoundsException ex) {
	    	  JOptionPane.showMessageDialog(null, "Please Set the Round Following Examples!");
	    	  return;
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
      
      // create hashmap for score
      while (rowIterator3.hasNext()) 
      {
         XSSFRow r = (XSSFRow) rowIterator3.next();
         Iterator < Cell > cellIterator3 = r.cellIterator();
         while ( cellIterator3.hasNext() ) 
         {
            Cell cell = cellIterator3.next();
            String key = Integer.toString(cell.getColumnIndex()) + Integer.toString(cell.getRowIndex());
            hsPoint.put(key, cell);
         }
      }     
      Double assignvalue = 0.0;
      Double answervalue = 0.0;
      // begin comparison
      Iterator < Row > rowIterator2 = spreadsheet_assignment.iterator();
      while (rowIterator2.hasNext()) 
      {
         XSSFRow r = (XSSFRow) rowIterator2.next();
         Iterator < Cell > cellIterator2 = r.cellIterator();
         while ( cellIterator2.hasNext()) 
         {
        	 Cell cell = cellIterator2.next();
        	 if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
        		 continue;
        	 }
        	 String key = Integer.toString(cell.getColumnIndex()) + Integer.toString(cell.getRowIndex());
        	 boolean id = false;
        	 round = 0.001;
        	 if (hsRound.get(key) != null) {
        		 round = hsRound.get(key);
        	 }
        	 if (cell != null)  assignvalue = getVal(cell, wbAssignment );
        	 
        	 if (hsAnswer.get(key) != null)answervalue = getVal(hsAnswer.get(key), wbAnswer );
        	 
        	 id = correct(assignvalue, answervalue, round);
        	 System.out.print(key+" ");
        	 System.out.print(assignvalue+ " ");
        	 System.out.print(round+ " ");
    		 System.out.println(id);
	      	 if (id == false){
	
	      		 String column = IntToLetters(cell.getColumnIndex());
	      		 String index = column + Integer.toString(cell.getRowIndex()+1);
	      		 Double point = hsPoint.get(key).getNumericCellValue();
	      		 total = WriteStringToFile(index, point, total);
	      		 XSSFCellStyle style = wbAssignment.createCellStyle();
	      		 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	      		 style.setFillForegroundColor(HSSFColor.RED.index);
	      		 style.setFillBackgroundColor(HSSFColor.RED.index);
	      		 cell.setCellStyle(style);
	      	 }

         }
      
      }
      FileWriter fw = new FileWriter("report.txt",true); 
	  fw.write("Your total point for this assignment is "+ total.toString());//appends the string to the file
	  fw.close();
      FileOutputStream fileOut=new FileOutputStream("assignment_after.xlsx");
      wbAssignment.write(fileOut);
      fileOut.close();
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
    	   fw.write("Cell "+ cell + " is wrong, " + point.toString() + "pt is deducted!\n");//appends the string to the file
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