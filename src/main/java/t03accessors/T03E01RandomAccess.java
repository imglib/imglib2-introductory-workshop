package t03accessors;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.integer.UnsignedByteType;

/**
 * Using a RandomAccess to access pixels
 *
 * @author Tobias Pietzsch
 */
public class T03E01RandomAccess
{
    public static void main( final String[] args )
    {
        final Img< UnsignedByteType > img = ArrayImgs.unsignedBytes( 400, 320 );

        final RandomAccess< UnsignedByteType > r = img.randomAccess();
		for ( int x = 10; x < 400; x += 10 )
		{
			for ( int y = 10; y < 320; y += 10 )
			{
				r.setPosition( x, 0 );
				r.setPosition( y, 1 );
				final UnsignedByteType t = r.get();
				t.set( 255 );
			}
		}

        ImageJFunctions.show( img );
    }
}
