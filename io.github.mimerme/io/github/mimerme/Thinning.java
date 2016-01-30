package io.github.mimerme;

import static org.bytedeco.javacpp.opencv_core.CV_32SC1;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGet2D;
import static org.bytedeco.javacpp.opencv_core.cvGetSize;

import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.IplImage;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.CV_RGB2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;
import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_OTSU;
import static org.bytedeco.javacpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.cvThreshold;
import io.github.mimerme.interpreter.ADBLinker;
import io.github.mimerme.interpreter.SNARTinterpreter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Scanner;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacv.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

//This class is a JavaCV implementation of Zhang-Suen thinning algorithm
//Since code of a JavaCv implementation of the algorithm is nowhere to be
//found on the internet, this code is of free use

public class Thinning extends Converter{
	int lastBit = 2;
	int bitCount = 0;
	int offset = 200;
	boolean startBitCount = false;
	
	static IplImage src;
	ByteBuffer buffer;

	ArrayList<Point> delList = new ArrayList<Point>();
	Indexer indxer;

	public String[] convert(IplImage image) {
		this.src = image;
		
		System.out.println("Running thinning algorithm...");

		System.out.println("Converting image into greyscale...");
		IplImage imageGrey = cvCreateImage(cvGetSize(image),IPL_DEPTH_8U,1);
		cvCvtColor(image,imageGrey,CV_RGB2GRAY);
		
		System.out.println("Converting image into binary...");
		src = cvCreateImage(cvGetSize(imageGrey),IPL_DEPTH_8U,1);
		cvThreshold(imageGrey, src, 128, 255, CV_THRESH_BINARY | CV_THRESH_OTSU);
		
		final CanvasFrame canvas = new CanvasFrame("Tmp", 1);

		// Request closing of the application when the image window is closed.
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		final OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

		canvas.showImage(converter.convert(src));
		
		buffer = src.getByteBuffer();
		

		
/*		cvSaveImage("binary1.jpg", src);
*/		
		for(int ll = 0; ll < 5; ll ++){
			getStep1();
			getStep2();
		}
		
/*		for(int ex = 342; ex < 344; ex++){
			for(int why = 12; why < 13; why++){
				indxer.putDouble(new int[]{why,ex,1}, 255);
				indxer.putDouble(new int[]{why,ex,2}, 255);
				indxer.putDouble(new int[]{why,ex,3}, 255);
			}
		}*/
		
/*		indxer.putDouble(new int[]{12,342,1}, 255);
		indxer.putDouble(new int[]{12,342,2}, 255);
		indxer.putDouble(new int[]{12,342,3}, 255);
		indxer.putDouble(new int[]{13,342,1}, 255);
		indxer.putDouble(new int[]{13,342,2}, 255);
		indxer.putDouble(new int[]{13,342,3}, 255);
		indxer.putDouble(new int[]{14,342,1}, 255);
		indxer.putDouble(new int[]{14,342,2}, 255);
		indxer.putDouble(new int[]{14,342,3}, 255);*/
		
		final CanvasFrame canvas1 = new CanvasFrame("Thinned", 1);

		cvSaveImage("binary_thin3.jpg", src);
		
		// Request closing of the application when the image window is closed.
		canvas1.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		canvas1.showImage(converter.convert(src));
		
		System.out.println("Drawing thinned image...");
		
		for(int y = 0; y < src.height(); y++){
			int[] bin = new int[src.width()];
			for(int x = 0; x < src.width(); x++){

		        CvScalar ptr = cvGet2D(src, y, x);

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
		}

		return null;
	}	


	public static void main (String[] args){
		Mat m = imread("C:\\Users\\Allen\\Documents\\dota_opacback.jpg");
		Indexer i = m.createIndexer();

		for(int q = 0; q < 50; q++){
			for(int k = 0; k < 25; k ++){
				//Need 3 for RGB (RGB 3 channels)
				i.putDouble(new int[]{q,k,2}, 255);
				i.putDouble(new int[]{q,k,1}, 255);
				i.putDouble(new int[]{q,k,3}, 255);

				//Need 1 for bi
			}
		}

		final CanvasFrame canvas = new CanvasFrame("My Image", 1);

		// Request closing of the application when the image window is closed.
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		final OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

		canvas.showImage(converter.convert(m));

	}

	//Functions A(P1) and B(P1) are compressed here to prevent recreation of same objects
	public void getStep1(){
		for(int y = 0; y < src.height(); y++){
			for(int x = 0; x < src.width(); x++){
				System.out.println(src.height());
				System.out.println("New Step...");

				int[] counts = new int[2];

				//Function A
				//Counts the number of transitions from white -> black

				//Todo: Now compare in each order
				String P1 = getColor(cvGet2D(src, y, x));
				
				//Make sure pixel is black
				if(P1.equals("white")){
					continue;
				}
				
				//P2: P2 is iterated 2 times (REMINDER: Multi result by 2)
				String P2;
				if(y-1 > 0)
					P2 = getColor(cvGet2D(src, y - 1, x));
				else
					continue;

				//P3

				String P3;
				if(y-1 > 0 && x + 1 < src.width())
					P3 = getColor(cvGet2D(src, y - 1, x + 1));
				else
					continue;

				String P4;
				if(x + 1 < src.width())
					P4 = getColor(cvGet2D(src, y, x + 1));
				else
					continue;

				String P5;
				if(y+1 < src.height() && x + 1 < src.width())
					P5 = getColor(cvGet2D(src, y + 1, x + 1));
				else
					continue;

				String P6;
				if(y+1 < src.height())
					P6 = getColor(cvGet2D(src, y + 1, x));
				else
					continue;

				String P7;
				if(y+1 < src.height() && x - 1 >0)
					P7 = getColor(cvGet2D(src, y + 1, x - 1));
				else
					continue;

				String P8;
				if(x - 1 >0)
					P8 = getColor(cvGet2D(src, y, x - 1));
				else
					continue;

				String P9;
				if(y-1 > 0 && x - 1 >0)
					P9 = getColor(cvGet2D(src, y - 1, x - 1));
				else
					continue;

				if(P2.equals("white")&&P3.equals("black"))
					counts[0] ++;
				if(P3.equals("white")&&P4.equals("black"))
					counts[0] ++;
				if(P4.equals("white")&&P5.equals("black"))
					counts[0] ++;
				if(P5.equals("white")&&P6.equals("black"))
					counts[0] ++;
				if(P6.equals("white")&&P7.equals("black"))
					counts[0] ++;
				if(P7.equals("white")&&P8.equals("black"))
					counts[0] ++;
				if(P8.equals("white")&&P9.equals("black"))
					counts[0] ++;
				if(P9.equals("white")&&P2.equals("black"))
					counts[0] ++;

				//Function B
				//Number of black pixel neighborrs of P1

				if(P2.equals("black"))
					counts[1] ++;
				if(P3.equals("black"))
					counts[1] ++;
				if(P4.equals("black"))
					counts[1] ++;
				if(P5.equals("black"))
					counts[1] ++;
				if(P6.equals("black"))
					counts[1] ++;
				if(P7.equals("black"))
					counts[1] ++;
				if(P8.equals("black"))
					counts[1] ++;
				if(P9.equals("black"))
					counts[1] ++;
				
				
				//Make sure b(p1) has only 2 - 6 surrounding black pixels
				if(((2 <= counts[1]) && (counts[1] <= 6)))
					continue;

				//Make sure there is only 1 0->1 transition
				if(counts[0] != 1)
					continue;

				//Make sure one of P2, P4, or P6 is white
				if(P2.equals("white") || P4.equals("white") || P6.equals("white")){
					if(P4.equals("white") || P8.equals("white") || P6.equals("white")){
						delList.add(new Point(x,y));
					}
				}
			
				System.out.println(x);
				System.out.println(y);

				if(x == 14 && y == 25){
					System.out.println(counts[1]);
					return;
				}
			}
		}
		
		for (Point point : delList) {
	        int index = point.y * src.widthStep() + point.x * src.nChannels();
	        buffer.put(index, (byte) 255);
	        buffer.put(index + 1, (byte) 255);
	        buffer.put(index + 2, (byte) 255);
		}
		delList.clear();
	}
	
	public void getStep2(){
		for(int y = 0; y < src.height(); y++){
			for(int x = 0; x < src.width(); x++){
				System.out.println(src.height());
				System.out.println("New Step...");

				int[] counts = new int[2];

				//Function A
				//Counts the number of transitions from white -> black

				//Todo: Now compare in each order
				String P1 = getColor(cvGet2D(src, y, x));
				
				//Make sure pixel is black
				if(P1.equals("white")){
					continue;
				}
				
				//P2: P2 is iterated 2 times (REMINDER: Multi result by 2)
				String P2;
				if(y-1 > 0)
					P2 = getColor(cvGet2D(src, y - 1, x));
				else
					continue;

				//P3

				String P3;
				if(y-1 > 0 && x + 1 < src.width())
					P3 = getColor(cvGet2D(src, y - 1, x + 1));
				else
					continue;
				
				String P4;
				if(x + 1 < src.width())
					P4 = getColor(cvGet2D(src, y, x + 1));
				else
					continue;

				String P5;
				if(y+1 < src.height() && x + 1 < src.width())
					P5 = getColor(cvGet2D(src, y + 1, x + 1));
				else
					continue;

				String P6;
				if(y+1 < src.height())
					P6 = getColor(cvGet2D(src, y + 1, x));
				else
					continue;

				String P7;
				if(y+1 < src.height() && x - 1 >0)
					P7 = getColor(cvGet2D(src, y + 1, x - 1));
				else
					continue;

				String P8;
				if(x - 1 >0)
					P8 = getColor(cvGet2D(src, y, x - 1));
				else
					continue;

				String P9;
				if(y-1 > 0 && x - 1 >0)
					P9 = getColor(cvGet2D(src, y - 1, x - 1));
				else
					continue;

				if(P2.equals("white")&&P3.equals("black"))
					counts[0] ++;
				if(P3.equals("white")&&P4.equals("black"))
					counts[0] ++;
				if(P4.equals("white")&&P5.equals("black"))
					counts[0] ++;
				if(P5.equals("white")&&P6.equals("black"))
					counts[0] ++;
				if(P6.equals("white")&&P7.equals("black"))
					counts[0] ++;
				if(P7.equals("white")&&P8.equals("black"))
					counts[0] ++;
				if(P8.equals("white")&&P9.equals("black"))
					counts[0] ++;
				if(P9.equals("white")&&P2.equals("black"))
					counts[0] ++;

				//Function B
				//Number of black pixel neighborrs of P1

				if(P2.equals("black"))
					counts[1] ++;
				if(P3.equals("black"))
					counts[1] ++;
				if(P4.equals("black"))
					counts[1] ++;
				if(P5.equals("black"))
					counts[1] ++;
				if(P6.equals("black"))
					counts[1] ++;
				if(P7.equals("black"))
					counts[1] ++;
				if(P8.equals("black"))
					counts[1] ++;
				if(P9.equals("black"))
					counts[1] ++;
				

				//Make sure b(p1) has only 2 - 6 surrounding black pixels
				if(!(2 <= counts[1] && counts[1] <= 6))
					continue;

				//Make sure there is only 1 0->1 transition
				if(counts[0] != 1)
					continue;

				//Make sure one of P2, P4, or P6 is white
				if(P2.equals("white") || P4.equals("white") || P8.equals("white")){
					if(P2.equals("white") || P8.equals("white") || P6.equals("white")){
						delList.add(new Point(x,y));

					}
				}
			}
		}
		for (Point point : delList) {
	        int index = point.y * src.widthStep() + point.x * src.nChannels();
	        buffer.put(index, (byte) 255);
	        buffer.put(index + 1, (byte) 255);
	        buffer.put(index + 2, (byte) 255);
		}
		delList.clear();
	}

	public String getColor(CvScalar ptr){
        if(ptr.val(2) == 0 && ptr.val(1) == 0 && ptr.val(0) == 0){
			return "black";
		}
		else{
			return "white";
		}
	}
	class Point{
		public int x,y;
		public Point(int x1, int y1){
			this.x = x1;
			this.y = y1;
		}
	}
}