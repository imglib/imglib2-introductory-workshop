package t02accessors;

import ij.ImageJ;
import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.real.FloatType;

/**
 * Illustrate the different iteration orders of ArrayImg and CellImg.
 *
 * @author Tobias Pietzsch
 */
public class T02E04IterationOrder
{
	public static void main( final String[] args )
	{
		new ImageJ();

		final int[] dimensions = new int[] { 400, 320 };

		final Img< FloatType > arrayImg = new ArrayImgFactory< FloatType >().create( dimensions, new FloatType() );
		System.out.println( arrayImg.iterationOrder() );

		final Img< FloatType > cellImg = new CellImgFactory< FloatType >( 100, 100 ).create( dimensions, new FloatType() );
		System.out.println( cellImg.iterationOrder() );

		final Cursor< FloatType > arrayCursor = arrayImg.cursor();
		final Cursor< FloatType > cellCursor = cellImg.cursor();

		int i = 0;
		while ( arrayCursor.hasNext() )
		{
			arrayCursor.next().set( i );
			cellCursor.next().set( i );
			++i;
		}

		ImageJFunctions.show( arrayImg, "ArrayImg" );
		ImageJFunctions.show( cellImg, "CellImg" );
	}
}
