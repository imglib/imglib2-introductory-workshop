package t05labelings;

import java.util.Iterator;
import java.util.Set;

import helpers.GetResource;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import net.imglib2.Cursor;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.labeling.ConnectedComponents;
import net.imglib2.algorithm.labeling.ConnectedComponents.StructuringElement;
import net.imglib2.converter.Converters;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.roi.labeling.LabelRegions;
import net.imglib2.type.logic.BoolType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.util.Intervals;

public class T05E05LabelRegions1
{
	public static void main( final String[] args )
	{
		new ImageJ();

		// Load the image to segment.
		final ImagePlus imp = IJ.openImage( GetResource.getFile("/blobs.tif" ) );
		final RandomAccessibleInterval< UnsignedByteType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img );

		// Segment it based on simple threshold.
		final RandomAccessibleInterval< BoolType > mask = Converters.convert(
				img, ( i, o ) -> o.set( i.get() > 127 ), new BoolType() );

		// Create a String labeling
		final Img< IntType > labelImg = ArrayImgs.ints( Intervals.dimensionsAsLongArray( img ) );
		final ImgLabeling< String, IntType > labeling = new ImgLabeling<>( labelImg );

		// Label connected components
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

		ImageJFunctions.show( labelImg, "labelImg" );

		/*
		 * Convert the labeling from type LabelingType<String> to something that
		 * can be displayed. For example, UnsignedByteType with label "l30"
		 * showing as white, everything else showing as black.
		 */
//		final RandomAccessibleInterval< UnsignedByteType > l30 = Converters.convert(
//				( RandomAccessibleInterval< LabelingType< String > > ) labeling,
//				( i, o ) -> {
//					o.set( i.contains( "l30" ) ? 255 : 0 );
//				}, new UnsignedByteType() );

		final Img< UnsignedByteType > l30 = ArrayImgs.unsignedBytes( Intervals.dimensionsAsLongArray( img ) );

		final LabelRegions< String > labelRegions = new LabelRegions<>( labeling );
		final Set< String > existingLabels = labelRegions.getExistingLabels();
		System.out.println( "existingLabels = " + existingLabels );

		final LabelRegion< String > region = labelRegions.getLabelRegion( "l30" );
		/*
		 * LabelRegion implements PositionableIterableRegion< BoolType >
		 *
		 * PositionableIterableRegion< BoolType > extends IterableRegion< BoolType >
		 *
		 * IterableRegion extends IterableInterval< Void >
		 *
		 * Allows to iterate all pixels in a region.
		 * Cursor< Void > means: Only the position counts.
		 */
		final Cursor< Void > cursor = region.inside().cursor();
		final RandomAccess< UnsignedByteType > access = l30.randomAccess();
		while ( cursor.hasNext() )
		{
			cursor.fwd();
			access.setPosition( cursor );
			access.get().set( 255 );
		}

		/*
		 * Regions.sample() binds a region to an image.
		 */
//		Regions.sample( region, l30 ).forEach( t -> t.set( 255 ) );

		ImageJFunctions.show( l30, "l30" );
	}
}
