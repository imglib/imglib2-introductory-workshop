package t02accessors;

import java.io.IOException;

import net.imglib2.RealRandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.interpolation.randomaccess.NLinearInterpolatorFactory;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.view.Views;

/**
 * Use RealRandomAccess to access a continuous (interpolated) image.
 *
 * @author Tobias Pietzsch
 */
public class T02E06RealRandomAccess
{
	public static void main( final String[] args ) throws IOException
	{
		// create 1D image with two pixels: {1,0}
		final Img< DoubleType > img = ArrayImgs.doubles( new double[] { 1.0,  0.0 }, 2 );

		// create a RealRandomAccess on the linearly interpolated image
		final RealRandomAccess< DoubleType > r = Views
				.interpolate( img, new NLinearInterpolatorFactory<>() )
				.realRandomAccess();

		// set the RealRandomAccess in between pixels
		r.setPosition( new double[] { 0.173 } );

		final double value = r.get().get();
		System.out.println( "value = " + value );
	}
}
