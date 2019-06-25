package t05labelings;

import java.util.Iterator;

import helpers.GetResource;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import net.imglib2.algorithm.binary.Thresholder;
import net.imglib2.algorithm.labeling.ConnectedComponents;
import net.imglib2.algorithm.labeling.ConnectedComponents.StructuringElement;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.logic.BitType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

public class T05E03ObjectSegmentation1
{
	public static void main( final String[] args )
	{
		new ImageJ();

		// Load the image to segment.
		final ImagePlus imp = IJ.openImage( GetResource.getFile("/blobs.tif" ) );
		final Img< UnsignedByteType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img );

		// Segment it based on simple threshold.
		final UnsignedByteType threshold = new UnsignedByteType( 127 );
		final Img< BitType > mask = Thresholder.threshold( img, threshold, true, 1 );
		ImageJFunctions.show( mask, "mask" );

		/*
		 * We explicitly compute the thresholded mask as a new BitType img.
		 * Instead, we could also just use a converted view:
		 */
//		final RandomAccessibleInterval< BoolType > mask = Converters.convert(
//				( RandomAccessibleInterval< UnsignedByteType > ) img,
//				( i, o ) -> o.set( i.compareTo( threshold ) > 0 ),
//				new BoolType() );

		/*
		 * Or we could use an Op:
		 */
//		final net.imagej.ImageJ ij = new net.imagej.ImageJ();
//		final IterableInterval< BitType > mask = ij.op().threshold().apply( img, threshold );
//		final Img< BitType > mask = ArrayImgs.bits( Intervals.dimensionsAsLongArray( img ) );
//		ij.op().threshold().apply( mask, img, threshold );



		/*
		 * Create a labeling of the same size as the image with String labels
		 */
		final long[] dims = new long[ img.numDimensions() ];
		img.dimensions( dims );
		final Img< IntType > labelImg = ArrayImgs.ints( dims );
//		final Img< IntType > labelImg = ArrayImgs.ints( Intervals.dimensionsAsLongArray( img ) );
//		final Img< IntType > labelImg = new ArrayImgFactory< IntType >().create( img, new IntType() );
		final ImgLabeling< String, IntType > labeling = new ImgLabeling<>( labelImg );

		/*
		 * Now we want to move from the binary mask image to a list of objects.
		 * We do this by looking for connected components.
		 */
		final StructuringElement se = StructuringElement.FOUR_CONNECTED;
		final Iterator< String > labelCreator = new Iterator< String >()
		{
			int id = 0;

			@Override
			public boolean hasNext()
			{
				return true;
			}

			@Override
			public synchronized String next()
			{
				return "l" + ( id++ );
			}
		};
		ConnectedComponents.labelAllConnectedComponents( mask, labeling, labelCreator, se );

		/*
		 * Show the IntType backing image of the labeling.
		 */
		ImageJFunctions.show( labelImg, "labelImg" );

		/*
		 * We can show the backing image, but we cannot show
		 * LabelingType<String> images!?!!
		 */
	}
}
