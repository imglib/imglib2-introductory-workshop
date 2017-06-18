package t04accessibles;

import ij.IJ;
import ij.ImagePlus;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.Converters;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.Views;

public class T04E06EvenMoreViews
{
	public static void main( final String[] args )
	{
		final ImagePlus imp = IJ.openImage( T04E06EvenMoreViews.class.getResource( "/clown.png" ).getFile() );
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
		 * There's more: Views.pair(), Views.collapse(), ...
		 */
	}
}
