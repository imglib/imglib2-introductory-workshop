package t05labelings;

import java.awt.Color;
import java.util.Iterator;

import helpers.GetResource;
import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.labeling.ConnectedComponents;
import net.imglib2.algorithm.labeling.ConnectedComponents.StructuringElement;
import net.imglib2.converter.Converters;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.roi.Regions;
import net.imglib2.roi.boundary.Boundary;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.roi.labeling.LabelRegions;
import net.imglib2.type.logic.BoolType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.util.Intervals;
import net.imglib2.view.Views;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.gui.Overlay;
import ij.gui.Roi;
import ij.gui.TextRoi;

public class T05E07Unfinished
{
	public static void main( final String[] args )
	{
		new ImageJ();

		// Load the image to segment.
		final ImagePlus imp = IJ.openImage( GetResource.getFile("/blobs.tif" ) );
		final RandomAccessibleInterval< UnsignedByteType > img = ImageJFunctions.wrap( imp );

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

		final LabelRegions< String > regions = new LabelRegions<>( labeling );

		// Mix IJ1 and ImgLib2 stuff.
		final Overlay overlay = new Overlay();
		imp.show();
		imp.setOverlay( overlay );
		for ( final LabelRegion< String > region : regions )
		{
			final String label = region.getLabel();
			final long[] min = new long[ img.numDimensions() ];
			final long[] max = new long[ img.numDimensions() ];
			region.min( min );
			region.max( max );
			final Roi roi = new Roi( min[ 0 ], min[ 1 ], max[ 0 ] - min[ 0 ], max[ 1 ] - min[ 1 ] );
			overlay.add( roi, label );

			final TextRoi tr = new TextRoi( ( max[ 0 ] + min[ 0 ] ) / 2, ( max[ 1 ] + min[ 1 ] ) / 2, label );
			tr.setCurrentFont( tr.getCurrentFont().deriveFont( 6f ) );
			tr.setStrokeColor( Color.RED );
			overlay.add( tr, label );
		}
		imp.updateAndDraw();

		final LabelRegion< String > labelRegion = regions.getLabelRegion( "l37" );
		// is (among other things) RandomAccessibleInterval< T extends BooleanType< T > >

		Regions.sample( labelRegion, img ).forEach( t -> t.set( 255 ) );

		labelRegion.move( 80, 0 );
		final Boundary< BoolType > boundary = new Boundary<>( labelRegion );
		Regions.sample( boundary, img ).forEach( t -> t.set( 255 ) );
		ImageJFunctions.show( img, "l37 and boundary" );

		for ( final LabelRegion< String > region : regions )
		{
			double sum = 0;
			final Cursor< UnsignedByteType > cursor = Regions.sample( region, img ).cursor();
			while ( cursor.hasNext() )
				sum += cursor.next().getRealDouble();
			final double mean = sum / region.size();
			System.out.println( region.getLabel() + " mean = " + mean );
		}


		final int n = regions.numDimensions();
		final long[] maxDim = new long[ n ];
		for ( final LabelRegion< String > region : regions )
			for ( int d = 0; d < n; ++d )
				maxDim[ d ] = Math.max( region.dimension( d ), maxDim[ d ] );

		final Img< UnsignedByteType > catalogue = ArrayImgs.unsignedBytes( maxDim[ 0 ], maxDim[ 1 ], regions.getExistingLabels().size() );
		int z = 0;
		for ( final LabelRegion< String > region : regions )
		{
			final long[] translation = new long[ n ];
			region.min( translation );
			Regions.sample( region, Views.pair(
					Views.translate( Views.hyperSlice( catalogue, 2, z ), translation ),
					img ) ).forEach( p -> p.getA().set( p.getB() ) );
			++z;
		}
		ImageJFunctions.show( catalogue );
	}

}
