package io.github.mimerme;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Scanner;

import javax.imageio.ImageIO;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.CvType;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
/**
 * Algorithm for Snart is found here
 * http://r3dux.org/2010/12/how-to-pixelise-a-webcam-stream-using-opencv/
 * Not written by me. I actually have no idea how Open CV works
 */
public class App 
{
	public static String imageLocation;
	public static String outputLocation;
	static int actuallyit;
    static int blockXSize,blockYSize, width, divisions, height, pixelCount, redSum, blueSum, greenSum, red, green, blue;
    
    public static void main( String[] args )
    {

    	
        System.out.println( "This is the Snart-Converter tool" );
        System.out.println( "This converts an image into a series of touches readable by the Snart Interpreter" );
        System.out.println( "Developed by Andros (Mimerme) Yang. Powered by JavaCV" );
        Scanner input = new Scanner(System.in);
        System.out.println( "Enter image location" );
        imageLocation = input.nextLine();
        System.out.println( "Enter .SNART instruction output" );
        outputLocation = input.nextLine();
        
        System.out.println("Buffering image...");
        IplImage image = cvLoadImage(imageLocation);
        IplImage pixelated = cvCreateImage(cvSize(image.width(), image.height()), IPL_DEPTH_8U, 3);

        if(image == null){
        	System.out.println("Invalid image. Image was returned null. Perhaps it doesn't exist?");
        	System.exit(-1);
        }
    	//Set that arsenal of variables above
    	divisions = 32;
        width = image.width();
        height = image.height();
        
        System.out.println("Pixilating image...");
        //Ported code from OpenCV
        
        // Calculate our blocksize per frame to cater for slider
        blockXSize = width  / divisions;
        blockYSize = height / divisions;
 
        pixelCount = blockXSize * blockYSize; // How many pixels we'll read per block - used to find the average colour
 
        System.out.println("At " + divisions + " divisions (Block size " + blockXSize + "x" + blockYSize + ", so " + pixelCount + " pixels per block)");
 
        // Loop through each block horizontally
        for (int xLoop = 0; xLoop < width - 1; xLoop += blockXSize)
        {
 
            // Loop through each block vertically
            for (int yLoop = 0; yLoop < height - 1; yLoop += blockYSize)
            {
 
                // Reset our colour counters for each block
                redSum     = 0;
                greenSum   = 0;
                blueSum    = 0;
 
                // Read every pixel in the block and calculate the average colour
                for (int pixXLoop = 0; pixXLoop < blockXSize; pixXLoop++)
                {
 
                    for (int pixYLoop = 0; pixYLoop < blockYSize; pixYLoop++)
                    {	
                    	//Satisfies a weird image array bug where iterations go out of bounds
                    	if((yLoop + pixYLoop) >= height)
                    		break;
                    	if((xLoop + pixXLoop) >= width)
                    		break;

                    	
                    	System.out.println(yLoop + pixYLoop);
                    	System.out.println(xLoop + pixXLoop);

                        CvScalar ptr = cvGet2D(image, yLoop + pixYLoop, xLoop + pixXLoop);
                        // Add each component to its sum
                        redSum   += ptr.val(2);
                        greenSum += ptr.val(1);
                        blueSum  += ptr.val(0);
                        
                        System.out.println(redSum);
                        System.out.println(greenSum);
                        System.out.println(blueSum);
                        System.out.println("---------------");

 
                    } // End of inner y pixel counting loop
                    
                	//Satisfies a weird image array bug where iterations go out of bounds
                	if((xLoop + pixXLoop) >= width)
                		break;
 
                } // End of outer x pixel countier loop
 
                // Calculate the average colour of the block
                red   = redSum   / pixelCount;
                green = greenSum / pixelCount;
                blue  = blueSum  / pixelCount;
 
                // Draw a rectangle of the average colour
                
                actuallyit++;
                
                cvRectangle(
                    pixelated,
                    cvPoint(xLoop, yLoop),
                    cvPoint(xLoop + blockXSize, yLoop + blockYSize),
                    CV_RGB(red, green, blue),
                    CV_FILLED,
                    8,
                    0
                );
                
                System.out.println("A total of " + actuallyit + " pixels were created. When " + pixelCount + " was supposed to be");

 
            } // End of inner y loop
 
        } // End of outer x loop
                
        System.out.println("Saving the pixelated image...");
        cvSaveImage(outputLocation, pixelated);
        System.out.println("Wow! The converter ran succesfully without error!");


    }

}
