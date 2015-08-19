package test;

import java.io.IOException;
import java.util.ArrayList;

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
		for (int i=0; i<list.size(); i++) {
			
		}
		
	}
	
	private ArrayList<String> makeList (String nameField) throws IOException {
		ArrayList<String> list = new ArrayList<String>();
		TsvReader reader = new TsvReader(nameField);
		list = reader.getID(nameField);
		return list;
	}
}
