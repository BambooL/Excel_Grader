package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TsvReader {
	
	private String path;
	
	public TsvReader (String a) {
		path = a;
	}
	
	public ArrayList<String> getID (String path) throws IOException {
		ArrayList<String> res = new ArrayList<String>();
		BufferedReader TSVFile = new BufferedReader(new FileReader(path));

		String dataRow = TSVFile.readLine(); // Read first line.

		while (dataRow != null){
		String name = dataRow.split("\t")[2];
		res.add(name);
		dataRow = TSVFile.readLine(); // Read next line of data.
		}
		// Close the file once all data has been read.
		TSVFile.close();
		return res;
	}
		
}
