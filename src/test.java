import java.io.*;

import org.apache.poi.xssf.usermodel.*;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
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

public class test
{
   public static void main(String args[])throws Exception
   { 
      File Answer = new File("answer.xlsx");
      File Assignment = new File("assignment_before.xlsx");
      FileInputStream fIP_answer = new FileInputStream(Answer);
      FileInputStream fIP_assignment = new FileInputStream(Assignment);
      //Get the workbook instance for XLSX file 
      XSSFWorkbook wbAnswer = new XSSFWorkbook(fIP_answer);
      XSSFWorkbook wbAssignment = new XSSFWorkbook(fIP_assignment);
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
      
      XSSFSheet spreadsheet_assignment = wbAssignment.getSheetAt(0);
      XSSFSheet spreadsheet_answer = wbAnswer.getSheetAt(0);
      Iterator < Row > rowIterator1 = spreadsheet_answer.iterator();
      HashMap<String, Cell> hsAnswer = new HashMap<String, Cell>();
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
      Iterator < Row > rowIterator2 = spreadsheet_assignment.iterator();
      while (rowIterator2.hasNext()) 
      {
         XSSFRow r = (XSSFRow) rowIterator2.next();
         Iterator < Cell > cellIterator2 = r.cellIterator();
         while ( cellIterator2.hasNext()) 
         {
        	 Cell cell = cellIterator2.next();
        	 String key = Integer.toString(cell.getColumnIndex()) + Integer.toString(cell.getRowIndex());
        	 boolean id = false;
        	 switch (cell.getCellType()) 
             {
        	    case Cell.CELL_TYPE_BLANK:
        	    	if(cell.getCellType() == 3) {
        	    		id = true;
        	    	}
                case Cell.CELL_TYPE_NUMERIC:
//                	System.out.println(cell.getNumericCellValue());
//                	System.out.println(hsAnswer.get(key).getNumericCellValue());
                	if (cell.getNumericCellValue() == hsAnswer.get(key).getNumericCellValue()) {
//                		System.out.println("True");
                		id = true;
                	}
                		
                break;
                case Cell.CELL_TYPE_STRING:
//                	System.out.println(cell.getStringCellValue());
//                	System.out.println(hsAnswer.get(key).getStringCellValue());
                	if (cell.getStringCellValue().equals(hsAnswer.get(key).getStringCellValue())) {
//                		System.out.println("True");
                		id = true;
                	}
                break;
                case Cell.CELL_TYPE_FORMULA:
                	FormulaEvaluator evaluator1 = wbAnswer.getCreationHelper().createFormulaEvaluator();
                	FormulaEvaluator evaluator2 = wbAssignment.getCreationHelper().createFormulaEvaluator();
                	CellValue cellValueAnswer = evaluator1.evaluate(hsAnswer.get(key));
                	CellValue cellValueAssignment = evaluator2.evaluate(cell);
                	if (cellValueAnswer.getNumberValue() == cellValueAssignment.getNumberValue()) id = true;
                break;
             	  
             }
        	 
        	 if (id == false){
        		 System.out.println(key);
        		 XSSFCellStyle style = wbAssignment.createCellStyle();
        		 style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        		 style.setFillForegroundColor(HSSFColor.RED.index);
        		 style.setFillBackgroundColor(HSSFColor.RED.index);
        		 cell.setCellStyle(style);

        		 
        	 }
//        	 if (!cell.equals(hsAnswer.get(key))) {
//        		 System.out.println(key);
//        		 XSSFCellStyle style = wbAssignment.createCellStyle();
//        		 XSSFFont font = wbAssignment.createFont();
//        		 font.setColor(HSSFColor.RED.index);
//        		 style.setFont(font);
//        		 cell.setCellStyle(style);
//        		 FileOutputStream out = new FileOutputStream("assignment.xlsx");
//        		 wbAssignment.write(out);
//        	 }
         }
      
      }
      FileOutputStream fileOut=new FileOutputStream("assignment_after.xlsx");
      wbAssignment.write(fileOut);
      fileOut.close();
   }
   
	   
}
    
   
   
//   public Double 