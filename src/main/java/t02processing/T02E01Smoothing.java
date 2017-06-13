package t02processing;

import java.io.IOException;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import net.imglib2.algorithm.gauss3.Gauss3;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.view.Views;

/**
 * Opening input images and displaying results using ImageJ1 wrappers.
 *
 * @author Tobias Pietzsch
 */
public class T02E01Smoothing
{
	public static void main( final String[] args ) throws IOException, IncompatibleTypeException
	{
		// show the ImageJ window
		new ImageJ();

		// open an ImageJ1 ImagePlus
		final ImagePlus imp = IJ.openImage( "http://imagej.net/images/clown.png" );

		// wrap it as an ImgLib2 img
		final Img< ? > img = ImageJFunctions.wrap( imp );

		// cast it to the correct type
		final Img< ARGBType > input = ( Img< ARGBType > ) img;

		// show input
		ImageJFunctions.show( input );

		// Create ARGBType output image of the same kind
		final Img< ARGBType > output = input.factory().create( input, new ARGBType() );

		// smooth img (Gaussian smoothing with sigma=5), write result to output
		Gauss3.gauss( 5.0, Views.extendBorder( input ), output );

		// show output
		ImageJFunctions.show( output );
	}
}
