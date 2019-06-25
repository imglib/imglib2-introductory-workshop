package t04accessibles;

import helpers.GetResource;
import net.imglib2.RandomAccessible;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.util.Intervals;
import net.imglib2.view.Views;

import ij.IJ;
import ij.ImagePlus;

public class T04E03MoreViews
{
	public static void main( final String[] args )
	{
		final ImagePlus imp = IJ.openImage( GetResource.getFile("/clown.png" ) );
		final RandomAccessibleInterval< ARGBType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img, "img" );

		/*
		 * Extend (make infinitely large, specifying what happens outside the boundary)
		 */
//		final RandomAccessible< ARGBType > extendedView = Views.extendBorder( img );
		final RandomAccessible< ARGBType > extendedView = Views.extendMirrorSingle( img );
//		final RandomAccessible< ARGBType > extendedView = Views.extendValue( img, new ARGBType( 0xff0000 ) );
//		final RandomAccessible< ARGBType > extendedView = Views.extendZero( img );

		/*
		 * We cannot show the result (because it has no boundaries)
		 *
		 * Use Views.interval to define new boundaries)
		 */
		final RandomAccessibleInterval< ARGBType > extendedAndCroppedView = Views.interval( extendedView, Intervals.createMinSize( -300, -300, 1000, 1000 ) );
		ImageJFunctions.show( extendedAndCroppedView, "extendedAndCroppedView" );

		/*
		 * Many views are read/write.
		 * "Many" meaning in general: "as you would expect if you think about it..."
		 */
//		Views.interval( extendedView, Intervals.createMinSize( -500, -500, 20, 20 ) ).forEach( t -> t.set( 0x0ff00 ) );
//		ImageJFunctions.show( extendedAndCroppedView, "modified" );
	}
}
