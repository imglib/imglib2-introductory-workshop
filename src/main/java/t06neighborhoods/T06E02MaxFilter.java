package t06neighborhoods;

import helpers.GetResource;
import ij.IJ;
import ij.ImagePlus;
import net.imagej.ImageJ;
import net.imglib2.RandomAccessible;
import net.imglib2.algorithm.neighborhood.Neighborhood;
import net.imglib2.algorithm.neighborhood.CenteredRectangleShape;
import net.imglib2.algorithm.neighborhood.HyperSphereShape;
import net.imglib2.algorithm.neighborhood.RectangleShape;
import net.imglib2.img.Img;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.view.Views;

/**
 * Use Neighborhoods to create a MaxFilter
 */
public class T06E02MaxFilter
{
	public static void main( String[] args )
	{
		final ImageJ ij = new ImageJ();
		ij.ui().showUI();

		final ImagePlus imp = IJ.openImage( GetResource.getFile("/blobs.tif" ) );
		final Img< UnsignedByteType > img = ImageJFunctions.wrapByte( imp );

		ij.ui().show( "img", img );

		final Img< UnsignedByteType > output = img.factory().create( img );

		final RectangleShape shape = new RectangleShape( 8, false );
		final RandomAccessible< Neighborhood< UnsignedByteType > > neighborhoods =
				shape.neighborhoodsRandomAccessible( Views.extendBorder( img ) );

		LoopBuilder.setImages( Views.interval( neighborhoods, output ), output )
				.multiThreaded()
				.forEachPixel( ( neighborhood, o ) -> {
					o.set( neighborhood.firstElement() );
					neighborhood.forEach( t -> {
						final int v = t.get();
						if ( v > o.get() )
							o.set( v );
					} );
				} );

		ij.ui().show( "output", output );
	}
}
