package t06neighborhoods;

import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessible;
import net.imglib2.algorithm.neighborhood.Neighborhood;
import net.imglib2.algorithm.neighborhood.RectangleShape;
import net.imglib2.algorithm.neighborhood.Shape;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.IntType;

/**
 * Example explaining how Shapes and Neighborhoods work.
 *
 * @author Tobias Pietzsch
 */
public class T06E01Neighborhoods
{
	public static void main( String[] args )
	{
		final int[] values = {
				11, 12, 13, 14,
				21, 22, 23, 24,
				31, 32, 33, 34,
				41, 42, 43, 44,
		};

		final Img< IntType > img = ArrayImgs.ints( values, 4, 4 );

		/*
		 * A Shape defines a particular structuring element.
		 */
		Shape shape = new RectangleShape( 1, false );

		System.out.println( shape.getStructuringElementBoundingBox( 2 ) );

		/*
		 * Shape.neighborhoodsXXX( img ) creates an Accessible where every pixel contains
		 * a neighborhood of values of img (centered at that pixel).
		 *
		 * Neighborhood<T> extends IterableInterval<T>, Localizable
		 */
		final RandomAccessible< Neighborhood< IntType > > neighborhoods = shape.neighborhoodsRandomAccessible( img );

		final RandomAccess< Neighborhood< IntType > > na = neighborhoods.randomAccess();

		na.setPosition( new int[] { 2, 1 } );
		final Neighborhood< IntType > neighborhood = na.get();

		Cursor< IntType > c = neighborhood.cursor();
		while ( c.hasNext() )
			System.out.println( c.next().get() );
	}
}
