package io.github.mimerme.interpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ADBLinker {
    public static void main( String[] args ) throws IOException, InterruptedException{
    	new ADBLinker();
    }

	public void cmd(String command) throws IOException, InterruptedException{
		System.out.println(command);
		Runtime rt = Runtime.getRuntime();
		Process proc = rt.exec(command);

		BufferedReader stdInput = new BufferedReader(new 
		     InputStreamReader(proc.getInputStream()));

		BufferedReader stdError = new BufferedReader(new 
		     InputStreamReader(proc.getErrorStream()));

		// read the output from the command
		String s = null;
		while ((s = stdInput.readLine()) != null) {
		    System.out.println(s);
		}

		// read any errors from the attempted command
		while ((s = stdError.readLine()) != null) {
		    System.out.println(s);
		}
	}
}
