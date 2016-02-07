package io.github.mimerme;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import io.github.mimerme.interpreter.SNARTinterpreter;

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
	public static SNARTinterpreter interpreter = new SNARTinterpreter();

	public static String imageLocation;
	public static String outputLocation;
    static Converter converter;
	public static SNARTWritter writter = new SNARTWritter();
    public static boolean createFile = true;
	public static int offset = 350;
    
    public static void main( String[] args ) throws IOException
    {

        System.out.println( "This is the Snart-Converter tool" );
        System.out.println( "This converts an image into a series of touches readable by the Snart Interpreter" );
        System.out.println( "Developed by Andros (Mimerme) Yang. Powered by JavaCV" );
        Scanner input = new Scanner(System.in);
        System.out.println( "Enter image location" );
        imageLocation = input.nextLine();
        System.out.println( "Enter the file output location. Default (No File)" );
        outputLocation = input.nextLine();
        if(outputLocation.equals("") || outputLocation == null){
        	createFile = false;
        }
        System.out.println("Enter .SNART conversion version (GScale=1) (GScale Android=2)(Pixelate=3) (GScale Edge=4) (Thinning Test=5)");
        SNARToutputversion = input.nextInt();
        
        switch(SNARToutputversion){
        case 1:
        	converter = new GScale();
        	break;
        case 2:
        	converter = new GScaleAndroid();
        	break;
        case 3:
        	converter = new Pixelate();
        	break;
        case 4:
        	converter = new GScaleEdge();
        	break;
        case 5:
        	converter = new Thinning();
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
        else if(createFile == true){
            System.out.println("Saving file...");
            writter.writeTo(outputLocation, converter.snartBuffer);
        }

        System.out.println("Wow! The converter ran succesfully without error!");


    }

}
