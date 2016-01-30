package io.github.mimerme;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import io.github.mimerme.interpreter.ADBLinker;
import io.github.mimerme.interpreter.SNARTinterpreter;
import static org.bytedeco.javacpp.opencv_core.BORDER_DEFAULT;
import static org.bytedeco.javacpp.opencv_imgcodecs.imread;
import static org.bytedeco.javacpp.opencv_imgproc.Laplacian;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.WindowConstants;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacv.*;

import static org.bytedeco.javacpp.opencv_imgproc.*;

public class GScaleEdge extends Converter {
	int lastBit = 2;
	int bitCount = 0;
	int offset = 200;
	boolean startBitCount = false;
	@Override
	public String[] convert(IplImage image) {


        // Read an image.
        final Mat src = imread("C:\\Users\\Allen\\Documents\\dickbutt.jpg");
        display(src, "Input");

        // Apply Laplacian filter
        final Mat dest = new Mat();
        Laplacian(src, dest, src.depth(), 1, 3, 0, BORDER_DEFAULT);
        display(dest, "Laplacian");
		/*try {
			App.interpreter.connect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		version = "GSCALEAndroid";

		
		// TODO Auto-generated method stub
		System.out.println("Converting image into greyscale...");
		IplImage imageGrey = cvCreateImage(cvGetSize(image),IPL_DEPTH_8U,1);
		cvCvtColor(image,imageGrey,CV_RGB2GRAY);
		
		System.out.println("Converting image into binary...");
		result = cvCreateImage(cvGetSize(imageGrey),IPL_DEPTH_8U,1);
		cvThreshold(imageGrey, result, 128, 255, CV_THRESH_BINARY | CV_THRESH_OTSU);
		
        // Note that you need to indicate to CanvasFrame not to apply gamma correction, 
        // by setting gamma to 1, otherwise the image will not look correct.
        final CanvasFrame canvas = new CanvasFrame("My Image", 1);
        
        // Request closing of the application when the image window is closed.
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        final OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

        // Show image on window.
        canvas.showImage(converter.convert(result));
        
		//Count white and black
		for(int y = 0; y < result.height(); y++){
			int[] bin = new int[result.width()];
			for(int x = 0; x < result.width(); x++){

		        CvScalar ptr = cvGet2D(result, y, x);

		        //If pixel is black
		        if(ptr.val(2) == 0 && ptr.val(1) == 0 && ptr.val(0) == 0){
		        	bin[x] = 1;
		        	//If last pixel was white
		        	if(lastBit == 0 || lastBit == 2){
		        		//Start coutning bits and reset the counter
		        		bitCount = 0;
		        		startBitCount = true;
		        	}
		        	if(startBitCount)
		        		bitCount++;
		        	lastBit = 1;

		        }
		        //If pixel is opaque catch the possible conditional
		        else{
			        bin[x] = 0;
			        //If last pixel was black
			        if(lastBit == 1){
			        	//Stop the bitcount and reset the bitcounter
		        		startBitCount = false;
		        		//You want to draw so send a swipe
		        			int xStart = x-bitCount;
		        			int yStart = y;
		        			int xStop = x;
		        			int yStop = y;
		        			snartBuffer.add("input swipe " + (xStart + offset) +  " " + (yStart + offset)+ " " + (xStop + offset) + " " + (yStop + offset) + "\n");
			        }
			        lastBit = 0;
		        }
			}
			if(startBitCount && lastBit > 0){
        			int xStart = result.width() - 1 - bitCount;
        			int yStart = y;
        			int xStop = result.width() - 1;
        			int yStop = y;
        			snartBuffer.add("input swipe " + (xStart + offset) +  " " + (yStart + offset)+ " " + (xStop + offset) + " " + (yStop + offset) + "\n");
			}
		}*/
		
		return null;
	}
	
    private void display(Mat image, String caption) {
        // Create image window named "My Image".
        final CanvasFrame canvas = new CanvasFrame(caption, 1.0);

        // Request closing of the application when the image window is closed.
        canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Convert from OpenCV Mat to Java Buffered image for display
        final OpenCVFrameConverter converter = new OpenCVFrameConverter.ToMat();
        // Show image on window.
        canvas.showImage(converter.convert(image));
    }

}
