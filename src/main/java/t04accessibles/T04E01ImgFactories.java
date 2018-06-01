package t04accessibles;

import net.imglib2.Cursor;
import net.imglib2.cache.img.DiskCachedCellImgFactory;
//import net.imglib2.cache.img.DiskCachedCellImgFactory;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.img.array.ArrayImgFactory;
//import net.imglib2.img.array.ArrayImgFactory;
//import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
//import net.imglib2.img.planar.PlanarImgFactory;
import net.imglib2.type.numeric.integer.UnsignedByteType;

public class T04E01ImgFactories
{
	public static void main( final String[] args )
	{
		final ImgFactory< UnsignedByteType > factory;
		factory = new ArrayImgFactory<>( new UnsignedByteType() );
//		factory = new PlanarImgFactory<>( new UnsignedByteType() );
//		factory = new CellImgFactory<>( new UnsignedByteType() );
//		factory = new DiskCachedCellImgFactory<>( new UnsignedByteType() );

		/*
		 * Create a 3D stack with these dimensions:
		 */
		final long[] dim = new long[] { 640, 480, 100 };

		/*
		 * You have to provide the (same) pixel type both in the factory
		 * constructor and in the create() call, unfortunately.
		 */
		Img< UnsignedByteType > img = factory.create( dim );

		/*
		 * For simple ArrayImg with known type you can just use the ArrayImgs
		 * convenience class.
		 */
//		img = ArrayImgs.unsignedBytes( 640, 480, 100 );

		/*
		 * Fill image with (nD) checkerboard pattern
		 */
		final Cursor< UnsignedByteType > cursor = img.localizingCursor();
		final int width = 25;
		final int n = img.numDimensions();
		while( cursor.hasNext() )
		{
			cursor.fwd();
			long sum = 0;
			for ( int d = 0; d < n; ++d )
				sum += cursor.getLongPosition( d ) / width;
			final int value = ( sum % 2 == 0 ) ? 0 : 255;
			cursor.get().set( value );
		}

		ImageJFunctions.show( img );


		/*
		 * ImgFactories create images of arbitrary number of dimensions.
		 */
		img = factory.create( 640, 480, 2, 2 );
		img = factory.create( 640, 480, 2, 2, 2 );
		img = factory.create( 640, 480, 2, 2, 2, 2 );

		/*
		 * Depending on the number of pixels, choose which ImgFactory to use.
		 *
		 * ArrayImg is fastest, but the number of pixels must be < 2^31.
		 *
		 * For PlanarImg the number of pixels in each XY slice must be < 2^31.
		 *
		 * For CellImg the pixels must fit in memory.
		 *
		 * For DiskCachedCellImg the pixels must fit on hard-drive.
		 */
		img = new DiskCachedCellImgFactory<>( new UnsignedByteType() ).create( 50000, 50000, 50000, 3 );
	}
}
