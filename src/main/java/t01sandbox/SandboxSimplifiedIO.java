package t01sandbox;

import java.io.IOException;

import helpers.GetResource;
import net.imagej.ImageJ;
import net.imglib2.img.Img;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import sc.fiji.simplifiedio.SimplifiedIO;

public class SandboxSimplifiedIO
{
	public static void main( final String[] args ) throws IOException
	{
		final ImageJ ij = new ImageJ();

		// show the ImageJ window
		ij.ui().showUI();

		final Img< UnsignedByteType > img = SimplifiedIO.openImage( GetResource.getFile("/clown.png" ), new UnsignedByteType() );
//		final Img< UnsignedByteType > img = SimplifiedIO.openImage( "https://imagej.net/images/clown.png", new UnsignedByteType() );

		// show the img
		ij.ui().show( img );
	}
}
