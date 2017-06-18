package t03types;

import java.io.IOException;

import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.DoubleType;

/**
 * Playing with types (without underlying image).
 *
 * @author Tobias Pietzsch
 */
public class T03E01Types
{
	public static void main( final String[] args ) throws IOException
	{
		/*
		 * Create some types.
		 */
		final UnsignedByteType A = new UnsignedByteType( 10 );
		final UnsignedByteType B = new UnsignedByteType();
		B.set( 20 );

		final DoubleType C = new DoubleType( 1.2 );
		final DoubleType D = new DoubleType();
		D.set( 2.5 );

		final ARGBType E = new ARGBType( ARGBType.rgba( 255, 0, 0, 0 ) );
		final ARGBType F = new ARGBType();
		F.set( ARGBType.rgba( 0, 255, 0, 0 ) );

		System.out.println( "A = " + A );
		System.out.println( "B = " + B );
		System.out.println();
		System.out.println( "C = " + C );
		System.out.println( "D = " + D );
		System.out.println();
		System.out.println( "E = " + E );
		System.out.println( "F = " + F );
		System.out.println();

		/*
		 * These all extend NumericType<T>, so we can call NumericType
		 * operations.
		 */
		final UnsignedByteType G = new UnsignedByteType();
		G.set( A );
		G.add( B );

		final DoubleType H = new DoubleType();
		H.set( C );
		H.add( D );

		final ARGBType I = new ARGBType();
		I.set( E );
		I.add( F );

		System.out.println( "C + D = " + H );
		System.out.println( "A + B = " + G );
		System.out.println( "E + F = " + I );
		System.out.println();

		/*
		 * Note that NumericType operations only work on the same type! This
		 * does not compile:
		 */
//		G.set( C );

		/*
		 * If you know the concrete type, you can get() or set() value directly.
		 */
		A.set( 12 );
//		A.set( 12.0 ); // does not compile
		final double a = C.get();

		/*
		 * If you know some type bound (e.g. T extends NumericType<T>) you can
		 * use the getX/setX methods of that interface
		 */
		final float b = A.getRealFloat();
		C.setReal( b );

		/*
		 * Caveat: The type inheritance hierarchy only makes sense in the "read"
		 * direction. Expect "write" write to be lossy.
		 */
		final double c = A.getRealDouble(); // 10.0
		final double d = A.getImaginaryDouble(); // 0.0
		A.setImaginary( 12.5 ); // does nothing
		A.setReal( 2.9 ); // rounded to 3
		A.setInteger( 257 ); // overflows to 1

	}
}
