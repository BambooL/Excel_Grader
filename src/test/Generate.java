package test;

import java.util.ArrayList;

public class Generate {
	private String assignGenField;
	private String assignTemField;
	private String nameField;
	public void Generate (String a, String b, String c) {
		assignGenField = a;
		assignTemField = b;
		nameField = c;
	}
	
	public void exec (String assignGenField, String assignTemField, String nameField) {
		ArrayList<String> list = new ArrayList<String>();
		list = makeList(nameField);
		
	}
	
	private ArrayList<String> makeList (String nameField) {
		ArrayList<String> list = new ArrayList<String>();
		while () {
			list.append();
		}
		return list;
	}
}
