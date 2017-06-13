package t02processing;

import java.io.IOException;
import java.util.ArrayList;

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
public class T02E01SmoothingAndStacking
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

		// create smoothed versions with sigma = 1..20
		final ArrayList< Img< ARGBType > > smoothedVersions = new ArrayList<>();
		for ( int sigma = 1; sigma <= 20; ++sigma )
		{
			final Img< ARGBType > output = input.factory().create( input, new ARGBType() );
			Gauss3.gauss( sigma, Views.extendBorder( input ), output );
//			ImageJFunctions.show( output );
			smoothedVersions.add( output );
		}

		// show stacked output slices
		ImageJFunctions.show( Views.stack( smoothedVersions ) );
	}
}
