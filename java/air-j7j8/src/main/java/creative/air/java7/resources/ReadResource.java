package creative.air.java7.resources;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadResource {
public static void main(String[] args) {
	try (BufferedReader reader=new BufferedReader(new FileReader("me.xml"))){
		String line;
		while((line=reader.readLine())!=null){
			System.out.println(line);
		}
	} catch (Exception e) {
		System.err.println(e);
	}
}
}
