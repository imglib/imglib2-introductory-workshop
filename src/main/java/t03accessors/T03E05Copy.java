package t03accessors;

import java.io.IOException;

import ij.IJ;
import ij.ImagePlus;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.cell.CellImgFactory;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.type.numeric.ARGBType;

/**
 * Opening input images and displaying results using ImageJ1 wrappers.
 *
 * @author Tobias Pietzsch
 */
public class T03E05Copy
{
	public static void main( final String[] args ) throws IOException
	{
		final ImagePlus imp = IJ.openImage( "http://imagej.net/images/clown.png" );
		final Img< ARGBType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img, "img" );

		final Img< ARGBType > copy = new CellImgFactory<ARGBType>().create( img, new ARGBType() );

		final Cursor< ARGBType > out = copy.localizingCursor();
		final RandomAccess< ARGBType > in = img.randomAccess();

		while( out.hasNext() )
		{
			out.fwd();
			in.setPosition( out );
			out.get().set( in.get() );
		}

		ImageJFunctions.show( copy, "copy" );
	}
}
