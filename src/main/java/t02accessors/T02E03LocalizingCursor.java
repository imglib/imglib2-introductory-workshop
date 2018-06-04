package t02accessors;
import net.imagej.ImageJ;
import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.IntType;

/**
 * Use localizingCursor() to get a Cursor that keeps track of its position
 * instead of recomputing it whenever you ask for it.
 *
 * @author Tobias Pietzsch
 */
public class T02E03LocalizingCursor
{
    public static void main( final String[] args )
    {
		final ImageJ ij = new ImageJ();
		ij.ui().showUI();

		final Img< IntType > img = ArrayImgs.ints( 400, 320 );

//		final Cursor< IntType > cursor = img.cursor();
		final Cursor< IntType > cursor = img.localizingCursor();
		while( cursor.hasNext() )
		{
			final IntType t = cursor.next();
			final int x = cursor.getIntPosition( 0 );
			final int y = cursor.getIntPosition( 1 );
			t.set( x + y );
		}

		ij.ui().show( "img", img );
    }
}
