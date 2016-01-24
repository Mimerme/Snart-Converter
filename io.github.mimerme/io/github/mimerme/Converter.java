package io.github.mimerme;

import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.IplImage;

public abstract class Converter {
	IplImage result;
	ArrayList<String> snartBuffer = new ArrayList<String>();
	String version;
	public String getVersion(){return version;}
	public abstract String[] convert(IplImage image);
	public IplImage getResultingImage(){return result;}
}
