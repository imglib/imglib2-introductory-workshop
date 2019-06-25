package t04accessibles;

import java.io.IOException;
import java.util.ArrayList;

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
 * Run Gauss smoothing with different sigmas and stack the results into a 3D output image.
 *
 * @author Tobias Pietzsch
 */
public class T04E08SmoothingAndStacking
{
	public static void main( final String[] args ) throws IOException, IncompatibleTypeException
	{
		final ImagePlus imp = IJ.openImage( GetResource.getFile("/clown.png" ) );
//		final ImagePlus imp = IJ.openImage( "https://imagej.net/images/clown.png" );

		final Img< ARGBType > input = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( input );

		// create smoothed versions with sigma = 1..20
		final ArrayList< Img< ARGBType > > smoothedVersions = new ArrayList<>();
		for ( int sigma = 1; sigma <= 20; ++sigma )
		{
			final Img< ARGBType > output = input.factory().create( input );
			Gauss3.gauss( sigma, Views.extendBorder( input ), output );
//			ImageJFunctions.show( output );
			smoothedVersions.add( output );
		}

		// show stacked output slices
		ImageJFunctions.show( Views.stack( smoothedVersions ) );
	}
}
