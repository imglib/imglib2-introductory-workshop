package t01sandbox;

import java.io.IOException;

import helpers.GetResource;
import net.imagej.Dataset;
import net.imagej.ImageJ;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.util.Util;

public class SandboxIJ2
{
	public static void main( final String[] args ) throws IOException
	{
		final ImageJ ij = new ImageJ();

		// show the ImageJ window
		ij.ui().showUI();

		final Img< ? > img = ij.scifio().datasetIO().open( GetResource.getFile("clown.png" ) );
//		final Img< ? > img = ij.scifio().datasetIO().open( "https://imagej.net/images/clown.png" );

		/*
		 * We do not know the ImgLib2 pixel type yet.
		 *
		 * ImageJFunctions.wrap returns
		 *     Img< T >
		 * where
		 *     < T extends NumericType< T > & NativeType< T > >
		 *
		 * Let's just print the ImgLib2 class that we actually got:
		 */
		final Object type = Util.getTypeFromInterval( img );
		System.out.println( "Pixel Type: " + type.getClass() );
		System.out.println( "Img Type: " + img.getClass() );


		/*
		 * Dataset wraps ImgPlus wraps Img.
		 *
		 * Let's see what the wrapped ImgLib2 Img actually is:
		 */
		final Img<?> wrappedImg = ( ( Dataset ) img ).getImgPlus().getImg();
		System.out.println( "Wrapped Img Type: " + wrappedImg.getClass() );

		// show the img
		ij.ui().show( img );







		// Create an ImgLib2 Img (ArrayImg)
		final Img< IntType > img2 = ArrayImgs.ints( 640, 480, 10 );

		// show it
		ij.ui().show( img2 );
	}
}
