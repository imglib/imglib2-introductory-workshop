package t02accessors;

import net.imagej.ImageJ;
import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.cell.CellImgFactory;
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
		final ImageJ ij = new ImageJ();
		ij.ui().showUI();

		final int[] dimensions = new int[] { 400, 320 };

		final Img< FloatType > arrayImg = new ArrayImgFactory<>( new FloatType() ).create( dimensions );
		System.out.println( arrayImg.iterationOrder() );

		final Img< FloatType > cellImg = new CellImgFactory<>( new FloatType(), 100, 100 ).create( dimensions );
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

		ij.ui().show( "ArrayImg", arrayImg );
		ij.ui().show( "CellImg", cellImg );
	}
}
