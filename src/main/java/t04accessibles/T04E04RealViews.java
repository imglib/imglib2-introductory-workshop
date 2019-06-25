package t04accessibles;

import helpers.GetResource;
import ij.IJ;
import ij.ImagePlus;
import net.imglib2.RandomAccessible;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.RealRandomAccessible;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.interpolation.randomaccess.NearestNeighborInterpolatorFactory;
import net.imglib2.realtransform.AffineTransform2D;
import net.imglib2.realtransform.RealViews;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.util.Intervals;
import net.imglib2.view.Views;

public class T04E04RealViews
{
	public static void main( final String[] args )
	{
		final ImagePlus imp = IJ.openImage( GetResource.getFile("/clown.png" ) );
		final RandomAccessibleInterval< ARGBType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img, "img" );

		final RandomAccessible< ARGBType > extendedView = Views.extendMirrorSingle( img );

		/*
		 * Interpolate the image to turn it into a continuous RealRandomAccessible
		 */
		final RealRandomAccessible< ARGBType > interpolatedView = Views.interpolate( extendedView, new NearestNeighborInterpolatorFactory<>() );
//		final RealRandomAccessible< ARGBType > interpolatedView = Views.interpolate( extendedView, new NLinearInterpolatorFactory<>() );
//		final RealRandomAccessible< ARGBType > interpolatedView = Views.interpolate( extendedView, new LanczosInterpolatorFactory<>() );

		/*
		 * Scale by 1.7 and rotate by 30 degrees.
		 */
		final AffineTransform2D transform = new AffineTransform2D();
		transform.scale( 1.7 );
		transform.rotate( 30.0 / 180.0 * Math.PI );
		final RealRandomAccessible< ARGBType > transformedView = RealViews.affineReal( interpolatedView, transform );

		/*
		 * Rasterize it to go back to integral coordinates
		 */
		final RandomAccessible< ARGBType > rasterizedView = Views.raster( transformedView );

		/*
		 * Define boundaries to show it
		 */
		final RandomAccessibleInterval< ARGBType > croppedView = Views.interval( rasterizedView, Intervals.createMinSize( -300, -300, 1000, 1000 ) );
		ImageJFunctions.show( croppedView, "croppedView" );

		/*
		 * You can do transformation and rasterization in one step using
		 * RealViews.affine instead of RealViews.affineReal.
		 *
		 * You can use RealViews.transform and RealView.transformReal for
		 * more general (for example elastic) transformations.
		 */
	}
}
