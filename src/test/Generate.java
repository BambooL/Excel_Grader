package test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import test.TsvReader;

public class Generate {
	private String assignGenField;
	private String assignTemField;
	private String nameField;
	public void Generate (String a, String b, String c) {
		assignGenField = a;
		assignTemField = b;
		nameField = c;
	}
	
	public void exec (String assignGenField, String assignTemField, String nameField) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		list = makeList(nameField);
		FileInputStream fIP_assignment = new FileInputStream(assignTemField);
		XSSFWorkbook wbAssignment = new XSSFWorkbook(fIP_assignment);
		
		int N_sheet = wbAssignment.getNumberOfSheets();
		
		for (int i=0; i<list.size(); i++) {
			
			String name = assignGenField + "/" + list.get(i) + ".xlsx";
			wbAssignment.createSheet(list.get(i));
	        wbAssignment.setSheetHidden(N_sheet, true);
	        XSSFSheet sheet = wbAssignment.getSheetAt(N_sheet);
	        sheet.protectSheet("password");
	        wbAssignment.lockStructure();
			FileOutputStream fileOut=new FileOutputStream(name);
			wbAssignment.write(fileOut);
			fileOut.close();
			wbAssignment.removeSheetAt(N_sheet);
			
		}
		
	}
	
	private ArrayList<String> makeList (String nameField) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		TsvReader reader = new TsvReader(nameField);
		list = reader.getID(nameField);
		return list;
	}
}
