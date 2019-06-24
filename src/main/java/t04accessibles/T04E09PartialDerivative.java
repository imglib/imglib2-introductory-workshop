package t04accessibles;

import ij.IJ;
import ij.ImagePlus;
import java.io.IOException;
import java.util.ArrayList;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imglib2.algorithm.gauss3.Gauss3;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgFactory;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.loops.LoopBuilder;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.util.Intervals;
import net.imglib2.util.Util;
import net.imglib2.view.Views;
import sc.fiji.simplifiedio.SimplifiedIO;

/**
 * Another example of using LoopBuilder.
 *
 * @author Tobias Pietzsch
 */
public class T04E09PartialDerivative
{
	public static void main( final String[] args ) throws IOException, IncompatibleTypeException
	{
		final ImageJ ij = new ImageJ();
		ij.ui().showUI();

		final Img< UnsignedByteType > img = SimplifiedIO.openImage( T04E09PartialDerivative.class.getResource( "/t1-head.tif" ).getFile(), new UnsignedByteType() );
		final Img< DoubleType > output = new ArrayImgFactory<>( new DoubleType() ).create( img );

		LoopBuilder.setImages(
					Views.interval( Views.extendBorder( img ), Intervals.translate( output, 1, 0 ) ),
					Views.interval( Views.extendBorder( img ), Intervals.translate( output, -1, 0 ) ),
					output )
				.multiThreaded()
				.forEachPixel( ( i1, i2, o ) -> o.set( i1.get() - i2.get() ) );

		ij.ui().show( "img", img );
		ij.ui().show( "output", output );
	}
}
