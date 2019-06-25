package t04accessibles;

import helpers.GetResource;
import ij.IJ;
import ij.ImagePlus;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.util.Intervals;
import net.imglib2.util.Util;
import net.imglib2.view.Views;

public class T04E02Views
{
	public static void main( final String[] args )
	{
		final ImagePlus imp = IJ.openImage(GetResource.getFile( "/clown.png" ) );
		final RandomAccessibleInterval< ARGBType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img, "img" );

		/*
		 * "Crop" (set interval boundaries)
		 */
		final RandomAccessibleInterval< ARGBType > intervalView = Views.interval( img, Intervals.createMinSize( 50, 50, 100, 100 ) );
		ImageJFunctions.show( intervalView, "intervalView" );

		/*
		 * No "real" cropping, data is still there
		 */
//		ImageJFunctions.show( Views.interval( intervalView, Intervals.createMinSize( 0, 0, 320, 200 ) ), "intervalView interval" );
//		ImageJFunctions.show( Views.interval( intervalView, img ), "intervalView interval" );

		/*
		 * Translate (adds translation vector to all coordinates)
		 */
		System.out.println( "intervalView.min = " + Util.printCoordinates( Intervals.minAsIntArray( intervalView ) ) );
		final RandomAccessibleInterval< ARGBType > translatedView = Views.translate( intervalView, 10, -10 );
		System.out.println( "translatedView.min = " + Util.printCoordinates( Intervals.minAsIntArray( translatedView ) ) );

		/*
		 * Translate to origin (translation = -min)
		 */
		final RandomAccessibleInterval< ARGBType > zeroMinView = Views.zeroMin( intervalView );
		System.out.println( "translatedView.min = " + Util.printCoordinates( Intervals.minAsIntArray( zeroMinView ) ) );
//		ImageJFunctions.show( zeroMinView, "zeroMinView" );

		/*
		 * Hyperslice (fix one dimension)
		 */
		final RandomAccessibleInterval< ARGBType > sliceView = Views.hyperSlice( img, 1, 100 );
		System.out.println( "sliceView.numDimensions() = " + sliceView.numDimensions() );

		/*
		 * We cannot ImageJFunctions.show() this, because show does not work for 1D images.
		 *
		 * Add a dimension (repeat data across that dimension).
		 */
		final RandomAccessibleInterval< ARGBType > addDimView = Views.addDimension( sliceView, 0, 400 );
		ImageJFunctions.show( addDimView );

		/*
		 * Rotate (one axis onto another)
		 */
		ImageJFunctions.show( Views.rotate( img, 0, 1 ), "rotated" );

		/*
		 * Permute (two axes)
		 */
		ImageJFunctions.show( Views.permute( img, 0, 1 ), "permuted" );

		/*
		 * Because of Java the results of Views operations are
		 * RandomAccessible(Interval)s. If you want to iterate, use
		 * Views.iterable() or Views.flatIterable() to get IterableInterval.
		 *
		 * Views tries to be clever about this:
		 */
		System.out.println( Views.iterable( img ) == img );
	}
}
