package io.github.mimerme.utils;

import org.bytedeco.javacpp.opencv_core.IplImage;

public class ASCIIImage {
	int[][] data;
	public ASCIIImage(IplImage src){
		
	}
	public int getPoint(int x, int y){
		return data[y][x];
	}
}
