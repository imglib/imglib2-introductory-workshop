package t02accessors;
import ij.ImageJ;
import net.imglib2.Cursor;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.integer.UnsignedByteType;

/**
 * Use a Cursor to access pixels
 *
 * @author Tobias Pietzsch
 */
public class T02E02Cursor
{
    public static void main( final String[] args )
    {
        final Img< UnsignedByteType > img = ArrayImgs.unsignedBytes(  400, 320 );

		final Cursor< UnsignedByteType > cursor = img.cursor();
		while( cursor.hasNext() )
			cursor.next().inc();

//		final Cursor< UnsignedByteType > cursor = img.cursor();
//		while( cursor.hasNext() )
//		{
//			cursor.fwd();
//			final UnsignedByteType type = cursor.get();
//			type.inc();
////			System.out.println( Util.printCoordinates( cursor ) );
//		}

//		for ( final UnsignedByteType type : img )
//			type.inc();

//		final Iterator< UnsignedByteType > iter = img.iterator();
//		while ( iter.hasNext() )
//			iter.next().inc();

//		img.forEach( t -> t.inc() );

		new ImageJ();
		ImageJFunctions.show( img, "Img" );
    }
}
