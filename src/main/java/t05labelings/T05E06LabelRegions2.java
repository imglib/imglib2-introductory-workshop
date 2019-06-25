package t05labelings;

import java.util.ArrayList;
import java.util.Iterator;

import helpers.GetResource;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
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
import net.imglib2.view.Views;

public class T05E06LabelRegions2
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

		final LabelRegions< String > regions = new LabelRegions<>( labeling );

		/*
		 * Make 3D stack where each slice corresponds to the mask of one label.
		 */
		final ArrayList< RandomAccessibleInterval< UnsignedByteType > > slices = new ArrayList<>();
		for ( final LabelRegion< String > region : regions )
		{
			/*
			 * LabelRegion implements PositionableIterableRegion< BoolType >
			 *
			 * PositionableIterableRegion< BoolType > extends IterableRegion< BoolType >
			 *
			 * IterableRegion extends RandomAccessibleInterval< BoolType >
			 *
			 * The interval bounds of the LabelRegion correspond to the bounding box of the label.
			 * Set new bounds such that every slice is as big as the input image.
			 */
			final RandomAccessibleInterval< BoolType > justTheRightSize = Views.interval( region, img );
			slices.add(
					Converters.convert(
							justTheRightSize,
							( i, o ) -> o.set( i.get() ? 255 : 0 ),
							new UnsignedByteType() ) );
		}
		ImageJFunctions.show( Views.stack( slices ) );

		/*
		 * Actually, with the Converter we are just showing off... We can also
		 * just ImageJFunctions.show() a BoolType stack.
		 */
//		final ArrayList< RandomAccessibleInterval< BoolType > > slices = new ArrayList<>();
//		for ( final LabelRegion< String > region : regions )
//		{
//			final RandomAccessibleInterval< BoolType > justTheRightSize = Views.interval( region, img );
//			slices.add( justTheRightSize );
//		}
//		ImageJFunctions.show( Views.stack( slices ) );
	}
}
