package t01sandbox;

import java.io.IOException;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.Converters;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.view.Views;

/**
 * Opening input images and displaying results using ImageJ1 wrappers.
 *
 * @author Tobias Pietzsch
 */
public class SandboxCentralDifference
{
	public static void main( final String[] args ) throws IOException
	{
		// show the ImageJ window
		new ImageJ();

		// open an ImageJ1 ImagePlus
		final ImagePlus imp = IJ.openImage( SandboxCentralDifference.class.getResource( "/blobs.tif" ).getFile() );
//		final ImagePlus imp = IJ.openImage( "https://imagej.net/images/clown.png" );

		// wrap it as an ImgLib2 img
		final RandomAccessibleInterval< UnsignedByteType > img = ImageJFunctions.wrap( imp );

		ImageJFunctions.show( img );

		ImageJFunctions.show(
				Views.interval(
						new CentralDifferenceRandomAccessible<>( Views.extendBorder(
								Converters.convert( img, ( i, o ) -> o.set( i.getRealDouble() ), new DoubleType() ) ) ),
						img ) );
	}
}
