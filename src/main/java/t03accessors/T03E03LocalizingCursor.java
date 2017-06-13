package t03accessors;
import ij.ImageJ;
import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.integer.IntType;

/**
 * Use localizingCursor() to get a Cursor that keeps track of its position
 * instead of recomputing it whenever you ask for it.
 *
 * @author Tobias Pietzsch
 */
public class T03E03LocalizingCursor
{
    public static void main( final String[] args )
    {
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
		new ImageJ();
		ImageJFunctions.show( img, "Img" );
    }
}
