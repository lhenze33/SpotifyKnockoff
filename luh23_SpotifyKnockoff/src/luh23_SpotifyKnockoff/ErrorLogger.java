package luh23_SpotifyKnockoff;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ErrorLogger {
	public static void log(String errorMessage) {
		//save the following information to errorlog.txt
		//Date, Time, Error Message \n
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy @ HH:mm:ss");
		Date date = new Date();
		String filePath = "/Users/LukeHenze/eclipse-workspace/luh23_SpotifyKnockoff/src";
		filePath	 += "/luh23_SpotifyKnockoff/errorlog.txt";
		
		try {
			PrintWriter out = new PrintWriter(new FileWriter(filePath, true));
		    out.write(dateFormat.format(date) +" , "+ errorMessage+"\n\n");
		    out.close();
		}
		catch (IOException e)
		{
		    System.out.println("Exception");

		}
	}
}
