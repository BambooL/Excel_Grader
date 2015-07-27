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

public class Assignment {
	public void checkAssign(File assign, HashMap<String, Cell> hsAnswer, XSSFWorkbook wbAnswer, HashMap<String, Double> hsRound, HashMap<String, Double> hsPoint ) throws IOException {
		
		FileInputStream fIP_assignment = new FileInputStream(assign);
		XSSFWorkbook wbAssignment = new XSSFWorkbook(fIP_assignment);
		FormulaEvaluator evaluator_assignment = wbAssignment.getCreationHelper().createFormulaEvaluator();
		FormulaEvaluator evaluator_answer = wbAnswer.getCreationHelper().createFormulaEvaluator();
		XSSFSheet spreadsheet_assignment = wbAssignment.getSheetAt(0);
		Double total = 100.0;
	    Double assignvalue = 0.0;
	    Double answervalue = 0.0;
	    Double round = 0.0001;
	    String report = "report/report_" + assign.getName()+".txt";
	    FileWriter fw = new FileWriter(report,true); 
		fw.write("\n Report for "+ assign.getName()+"\n");//appends the string to the file
		fw.close();
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
		      		 if (hsPoint.get(key) != null) {
		      			Double point = hsPoint.get(key);
		      			total = WriteStringToFile(index, point, total, assign);
		      		 }
		      		 XSSFCellStyle style = wbAssignment.createCellStyle();
		      		 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		      		 style.setFillForegroundColor(HSSFColor.RED.index);
		      		 style.setFillBackgroundColor(HSSFColor.RED.index);
		      		 cell.setCellStyle(style);
		      	 }

	         }
	      
	      }
	      
	      fw = new FileWriter(report,true); 
		  fw.write("\n Your total point for this assignment is "+ total.toString());//appends the string to the file
		  fw.close();
		  String assignment_after = "after/assignment_after_"+assign.getName()+ ".xlsx";
	      FileOutputStream fileOut=new FileOutputStream(assignment_after);
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
	   
	   
	   public Double WriteStringToFile(String cell, Double point, Double total, File assign) {  
	       try {    
	    	   String report = "report/report_" + assign.getName()+".txt";
	    	   FileWriter fw = new FileWriter(report,true); 
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
//		          	System.out.println(cell.getNumericCellValue());
//		          	System.out.println(hsAnswer.get(key).getNumericCellValue());
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
