package io.github.mimerme.interpreter;

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
	
	public void sendSwipe(int xStart, int yStart, int xStop, int yStop) throws IOException, InterruptedException{
		System.out.println("adb shell input swipe " + xStart +  " " + yStart + " " + xStop + " " + yStop);
		adbLink.cmd("adb shell input swipe " + xStart +  " " + yStart + " " + xStop + " " + yStop);
	}
	
	public void byFile(){
		
	}
	private void parseGScale(){
		
	}
	private void parseRGB(){
		
	}
}
