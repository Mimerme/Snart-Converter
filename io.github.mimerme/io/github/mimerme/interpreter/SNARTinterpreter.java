package io.github.mimerme.interpreter;

import io.github.mimerme.App;

import java.io.IOException;

public class SNARTinterpreter {
	private ADBLinker adbLink = new ADBLinker();

	public void connect() throws IOException, InterruptedException{
		System.out.println("Connecting to Android device...");
		adbLink.cmd("adb devices");
	}
	
	public void sendTouch(int x, int y) throws IOException, InterruptedException{
		adbLink.cmd("adb shell input tap " + x +  " " + y);
	}
	
	int offset = 300;
	//Snapchat doesn't like swipes being sent instantly (0ms)?????
	public void sendSwipe(int xStart, int yStart, int xStop, int yStop) throws IOException, InterruptedException{
		adbLink.cmd("adb shell input swipe " + (xStart + offset) +  " " + (yStart + offset)+ " " + (xStop + offset) + " " + (yStop + offset));
	}
	
	public void runFile(){
		try {
			adbLink.cmd("adb shell < " + App.outputLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void byFile(){
		
	}
	private void parseGScale(){
		
	}
	private void parseRGB(){
		
	}
}
