package io.github.mimerme;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

import java.io.IOException;
import java.util.Scanner;

import org.bytedeco.javacpp.opencv_core.IplImage;
/**
 * Algorithm for Snart is found here
 * http://r3dux.org/2010/12/how-to-pixelise-a-webcam-stream-using-opencv/
 * Not written by me. I actually have no idea how Open CV works
 */
public class App 
{
	static int SNARToutputversion;
	
	public static String imageLocation;
	public static String outputLocation;
    static Converter converter;
	public static SNARTWritter writter = new SNARTWritter();
    
    public static void main( String[] args ) throws IOException
    {

        System.out.println( "This is the Snart-Converter tool" );
        System.out.println( "This converts an image into a series of touches readable by the Snart Interpreter" );
        System.out.println( "Developed by Andros (Mimerme) Yang. Powered by JavaCV" );
        Scanner input = new Scanner(System.in);
        System.out.println( "Enter image location" );
        imageLocation = input.nextLine();
        System.out.println( "Enter .SNART instruction output location" );
        outputLocation = input.nextLine();
        System.out.println("Enter .SNART conversion version (GScale=1) (RGB=2) (Pixelate=3)");
        SNARToutputversion = input.nextInt();
        
        switch(SNARToutputversion){
        case 1:
        	converter = new GScale();
        	break;
        case 2:
        	break;
        case 3:
        	converter = new Pixelate();
        	break;
        default:
        	System.out.println("Invalid option...");
        	main(null);
        	return;
        }
        
        System.out.println("Buffering image...");
        IplImage image = cvLoadImage(imageLocation);

        if(image == null){
        	System.out.println("Invalid image. Image was returned null. Perhaps it doesn't exist?");
        	System.exit(-1);
        }
        
        converter.convert(image);
                
        if(SNARToutputversion == 3){
            System.out.println("Saving resulting image...");
        	writter.writeImage(outputLocation, converter.getResultingImage());
        }
        else{
            System.out.println("Saving .SNART...");
            writter.writeTo(outputLocation, converter.snartBuffer);
        }
        System.out.println("Wow! The converter ran succesfully without error!");


    }

}
