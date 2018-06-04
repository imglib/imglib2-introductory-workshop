package t04accessibles;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.Converter;
import net.imglib2.converter.Converters;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import ij.IJ;
import ij.ImagePlus;

public class T04E05Converters
{
	public static void main( final String[] args )
	{
		final ImagePlus imp = IJ.openImage( T04E05Converters.class.getResource( "/clown.png" ).getFile() );
		final RandomAccessibleInterval< ARGBType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img, "img" );

		/*
		 * Convert ARGBType to UnsignedByteType by extracting the red component
		 */
		final Converter< ARGBType, UnsignedByteType > redConverter = new Converter< ARGBType, UnsignedByteType >()
		{
			@Override
			public void convert( final ARGBType input, final UnsignedByteType output )
			{
				output.set( ARGBType.red( input.get() ) );
			}
		};
		final RandomAccessibleInterval< UnsignedByteType > red = Converters.convert( img, redConverter, new UnsignedByteType() );
		ImageJFunctions.show( red, "red" );

		/*
		 * Converter inline as lambda
		 */
		final RandomAccessibleInterval< UnsignedByteType > green = Converters.convert( img, ( i, o ) -> o.set( ARGBType.green( i.get() ) ), new UnsignedByteType() );
		ImageJFunctions.show( green, "green" );

		/*
		 * Converters are read-only.
		 *
		 * There are SamplerConverters which are read/write, but this is beyond
		 * the scope of this workshop.
		 */

		/*
		 * Look at methods in Converters class, and classes in package
		 * net.imglib2.converter, for overview of what's there...
		 */
	}
}
