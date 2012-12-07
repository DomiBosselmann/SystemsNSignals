package backEnd;

import java.io.*;
import java.util.ArrayList;

public class JavaFileGeneration 
{
    //String function = "new double[]{gleichung1.hetText(), gleichung2.getText()};";



	private File file1;
	private ArrayList<String> strings=new ArrayList<String>();


	public void writeTheFile(File file1, String content)
	{
		this.file1 = file1;
		this.strings = splitString(content);
		
		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.file1));
			//Name des Files eingeben
			for(String line:strings)
			{
				writer.write(line);
				writer.newLine();
			}
			writer.flush();
			writer.close();
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	}
	private ArrayList<String> splitString(String content)
	{
		content=content.substring(1, content.length()-1);
		for(String test: content.split(","))
		{
			strings.add(test);
		}
		return strings;
	}
}
