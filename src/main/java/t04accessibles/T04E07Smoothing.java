package t04accessibles;

import java.io.IOException;

import helpers.GetResource;
import net.imglib2.algorithm.gauss3.Gauss3;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.view.Views;

import ij.IJ;
import ij.ImagePlus;

/**
 * Run Gauss smoothing from imglib2-algorithm.
 *
 * @author Tobias Pietzsch
 */
public class T04E07Smoothing
{
	public static void main( final String[] args ) throws IOException, IncompatibleTypeException
	{
		final ImagePlus imp = IJ.openImage( GetResource.getFile("/clown.png" ) );
//		final ImagePlus imp = IJ.openImage( "https://imagej.net/images/clown.png" );

		final Img< ARGBType > input = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( input );

		// Create ARGBType output image of the same kind
		final Img< ARGBType > output = input.factory().create( input );

		// smooth img (Gaussian smoothing with sigma=5), write result to output
		Gauss3.gauss( 5.0, Views.extendBorder( input ), output );

		// show output
		ImageJFunctions.show( output );
	}
}
