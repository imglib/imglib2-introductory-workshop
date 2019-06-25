package t04accessibles;

import helpers.GetResource;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.Converters;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.Views;

import ij.IJ;
import ij.ImagePlus;

public class T04E06EvenMoreViews
{
	public static void main( final String[] args )
	{
		final ImagePlus imp = IJ.openImage( GetResource.getFile("clown.png" ) );
		final RandomAccessibleInterval< ARGBType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img, "img" );

		/*
		 * Use converters to extract channels
		 */
		final RandomAccessibleInterval< UnsignedByteType > red = Converters.convert( img, ( i, o ) -> o.set( ARGBType.red( i.get() ) ), new UnsignedByteType() );
		final RandomAccessibleInterval< UnsignedByteType > green = Converters.convert( img, ( i, o ) -> o.set( ARGBType.green( i.get() ) ), new UnsignedByteType() );
		final RandomAccessibleInterval< UnsignedByteType > blue = Converters.convert( img, ( i, o ) -> o.set( ARGBType.blue( i.get() ) ), new UnsignedByteType() );

		/*
		 * Stack 2D red, green, blue channels to get 3D image
		 */
		final RandomAccessibleInterval< UnsignedByteType > channels = Views.stack( red, green, blue );
		ImageJFunctions.show( channels, "channels" );

		/*
		 * Look at methods in Views class for overview of what's there...
		 * There's more: Views.pair(), Views.collapse(), ...
		 */
	}
}
