package t01sandbox;

import java.io.IOException;

import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.util.Util;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;

/**
 * Opening input images and displaying results using ImageJ1 wrappers.
 *
 * @author Tobias Pietzsch
 */
public class SandboxIJ1
{
	public static void main( final String[] args ) throws IOException
	{
		// show the ImageJ window
		new ImageJ();

		// open an ImageJ1 ImagePlus
		final ImagePlus imp = IJ.openImage( SandboxIJ1.class.getResource( "/clown.png" ).getFile() );
//		final ImagePlus imp = IJ.openImage( "https://imagej.net/images/clown.png" );

		// wrap it as an ImgLib2 img
		final Img< ? > img = ImageJFunctions.wrap( imp );

		/*
		 * We do not know the ImgLib2 pixel type yet.
		 *
		 * ImageJFunctions.wrap returns
		 *     Img< T >
		 * where
		 *     < T extends NumericType< T > & NativeType< T > >
		 *
		 * Lets just print the ImgLib2 class that we actually got:
		 */
		final Object type = Util.getTypeFromInterval( img );
		System.out.println( "Pixel Type: " + type.getClass() );
		System.out.println( "Img Type: " + img.getClass() );

		// show it as an ImageJ1 (virtual) stack
		ImageJFunctions.show( ( Img< ARGBType > ) img );







		// Create an ImgLib2 Img (ArrayImg)
		final Img< IntType > img2 = ArrayImgs.ints( 640, 480, 10 );

		// show it as an ImageJ1 (virtual) stack
		ImageJFunctions.show( img2 );
	}
}
