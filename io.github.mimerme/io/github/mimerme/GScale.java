package io.github.mimerme;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import io.github.mimerme.interpreter.ADBLinker;
import io.github.mimerme.interpreter.SNARTinterpreter;

import java.io.IOException;
import java.util.ArrayList;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacv.*;

import static org.bytedeco.javacpp.opencv_imgproc.*;

public class GScale extends Converter {
	int lastBit = 2;
	int bitCount = 0;
	boolean startBitCount = false;
	SNARTinterpreter interpreter = new SNARTinterpreter();
	@Override
	public String[] convert(IplImage image) {
		try {
			interpreter.connect();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		version = "GSCALE";
		snartBuffer.add("VERSION=" + version);
		snartBuffer.add("{");

		
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
		        		try {
							interpreter.sendSwipe(x - bitCount, y, x, y);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			        lastBit = 0;
		        }
			}
			if(startBitCount && lastBit > 0){
				try {
					interpreter.sendSwipe(result.width() - 1 - bitCount, y, result.width() - 1, y);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(y == result.height() - 1){
				snartBuffer.add(y + ": " + arrayToString(bin) + "\n");
			}
			else{
				snartBuffer.add(y + ": " + arrayToString(bin) + "," + "\n");
			}
		}
		snartBuffer.add("}");

		

		
		return null;
	}
	
	private String arrayToString(int[] arr){
		String buf = "[";
		for (int val : arr) {
			buf = buf + val + ",";
		}
		buf = buf.substring(0, buf.length() - 1);
		return buf + "]";
	}

}
