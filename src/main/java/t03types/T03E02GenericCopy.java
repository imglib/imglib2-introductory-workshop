package t03types;

import java.io.IOException;

import ij.IJ;
import ij.ImagePlus;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.Type;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

/**
 * Using Type interface to specify that copy() will work for all pixel types
 * with a set(T) method
 *
 * @author Tobias Pietzsch
 */
public class T03E02GenericCopy
{
	public static < T extends Type< T > >void copy( final Img< T > source, final Img< T > target )
	{
		final RandomAccess< T > in = source.randomAccess();
		final Cursor< T > out = target.localizingCursor();
		while ( out.hasNext() )
		{
			out.fwd();
			in.setPosition( out );
			final T type = out.get();
			type.set( in.get() );
		}
	}

	public static void main( final String[] args ) throws IOException
	{
		final ImagePlus imp = IJ.openImage( T03E02GenericCopy.class.getResource( "/clown.png" ).getFile() );
//		final ImagePlus imp = IJ.openImage( "https://imagej.net/images/clown.png" );
		final Img< ARGBType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img, "img" );

		final Img< ARGBType > copy = new CellImgFactory<ARGBType>().create( img, new ARGBType() );
		copy( img, copy );
		ImageJFunctions.show( copy, "copy" );

		final ImagePlus imp2 = IJ.openImage( T03E02GenericCopy.class.getResource( "/blobs.tif" ).getFile() );
//		final ImagePlus imp2 = IJ.openImage( "https://imagej.net/images/blobs.gif" );
		final Img< UnsignedByteType > img2 = ImageJFunctions.wrap( imp2 );
		ImageJFunctions.show( img2, "img2" );

		final Img< UnsignedByteType > copy2 = ArrayImgs.unsignedBytes( 256, 254 );
		copy( img2, copy2 );
		ImageJFunctions.show( copy2, "copy2" );

		/*
		 * Note that the generic copy() method is type-safe.
		 * This will not compile:
		 */
//		copy( img, copy2 );
	}
}
