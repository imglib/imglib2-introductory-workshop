package t03types;

import java.util.ArrayList;

import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.complex.ComplexDoubleType;
import net.imglib2.type.numeric.integer.IntType;

/**
 * Demonstrate that NativeTypes are proxy objects.
 *
 * @author Tobias Pietzsch
 */
public class T03Proxy
{
	public static void main( final String[] args )
	{
		final int[] data = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		/*
		 * NativeTypes refer to values in primitive arrays. (This is a
		 * simplification).
		 *
		 * Wrap int[] data into an Img<IntType>:
		 */
		final Img< IntType > img = ArrayImgs.ints( data, 10 );

		/*
		 * The type obtained from an accessor reflects the corresponding value
		 * in the primitive array
		 */
		final RandomAccess< IntType > access = img.randomAccess();
		access.setPosition( 5, 0 );
		final IntType type = access.get();
		System.out.println( type );

		data[ 5 ] = 42;
		System.out.println( type );

		type.set( 1000 );
		System.out.println( data[ 5 ] );
		System.out.println();




		/*
		 * Pixel to primitive value correspondence is not necessarily one-to-one
		 */
		final double[] data2 = new double[] {1,2,3,4};
		final Img< ComplexDoubleType > img2 = ArrayImgs.complexDoubles( data2, 2 );
		final RandomAccess< ComplexDoubleType > access2 = img2.randomAccess();
		access2.setPosition( 1, 0 );
		final ComplexDoubleType type2 = access2.get();
		System.out.println( type2 );
		System.out.println();




		/*
		 * Accessors (may) reuse the same type instance.
		 * (But distinct accessor instances use distinct type instances.)
		 *
		 * After you move an accessor, you should assume that previously
		 * returned values become invalid.
		 */
		final Cursor< IntType > c = img.cursor();
		final ArrayList< IntType > values = new ArrayList<>();
		while ( c.hasNext() )
			values.add( c.next() );
		System.out.println( values );

		values.clear();
		c.reset();
		while ( c.hasNext() )
			values.add( c.next().copy() );
		System.out.println( values );
	}
}
