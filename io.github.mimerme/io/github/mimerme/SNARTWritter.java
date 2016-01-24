package io.github.mimerme;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.IplImage;

public class SNARTWritter {
	
	public void writeTo(String path, ArrayList<String> data) throws IOException{
		File file = new File(path);

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		for (String string : data) {
			bw.write(string);
		}
		bw.close();

		System.out.println("File writting finished");
	}
	
	public void writeImage(String path, IplImage image){
		cvSaveImage(path, image);
	}
}
