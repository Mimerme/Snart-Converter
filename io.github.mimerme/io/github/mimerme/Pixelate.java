package io.github.mimerme;

import static org.bytedeco.javacpp.helper.opencv_core.CV_RGB;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvCreateImage;
import static org.bytedeco.javacpp.opencv_core.cvGet2D;
import static org.bytedeco.javacpp.opencv_core.cvPoint;
import static org.bytedeco.javacpp.opencv_core.cvSize;
import static org.bytedeco.javacpp.opencv_imgproc.CV_FILLED;
import static org.bytedeco.javacpp.opencv_imgproc.cvRectangle;

import org.bytedeco.javacpp.opencv_core.CvScalar;
import org.bytedeco.javacpp.opencv_core.IplImage;

public class Pixelate extends Converter {

    static int blockXSize,blockYSize, width, divisions, height, pixelCount, redSum, blueSum, greenSum, red, green, blue;
    
	@Override
	public String[] convert(IplImage image) {
		// TODO Auto-generated method stub
		
		result = cvCreateImage(cvSize(image.width(), image.height()), IPL_DEPTH_8U, 3);
		
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
                                
                cvRectangle(
                    result,
                    cvPoint(xLoop, yLoop),
                    cvPoint(xLoop + blockXSize, yLoop + blockYSize),
                    CV_RGB(red, green, blue),
                    CV_FILLED,
                    8,
                    0
                );
                
 
            } // End of inner y loop
 
        } // End of outer x loop
		return null;
	}

}
