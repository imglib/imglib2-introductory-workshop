package t05labelings;

import java.util.Arrays;
import java.util.Set;

import io.scif.img.ImgIOException;
import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccess;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelingMapping;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.util.Util;

public class T05E02LabelRepresentation
{
	public static void main( final String[] args ) throws ImgIOException
	{
		/*
		 * Create an empty 2x2 labeling with String labels.
		 *
		 * ImgLabeling< T, ? > implements
		 * RandomAccessibleInterval< LabelingType< T > > and
		 * IterableInterval< LabelingType< T > >
		 */
		final Img< IntType > img = ArrayImgs.ints( 2, 2 );
		final ImgLabeling< String, IntType > labeling = new ImgLabeling<>( img );

		/*
		 * Set up the labels like this:
		 *
		 *   +-----+-------+
		 *   | {A} | {A,B} |
		 *   +-----+-------|
		 *   | {}  | {B}   |
		 *   +-----+-------+
		 */
		final RandomAccess< LabelingType< String > > access = labeling.randomAccess();
		access.setPosition( new int[] { 0, 0 } );
		access.get().add( "A" );
		access.get().add( "C" );
		access.get().remove( "C" );
		access.setPosition( new int[] { 1, 0 } );
		access.get().addAll( Arrays.asList( "A", "B" ) );
		access.setPosition( new int[] { 1, 1 } );
		access.get().add( "B" );

		// show the labels
		System.out.println( "Labels:" );
		printPixels( labeling );
		System.out.println();

		// show the IntType backing img
		System.out.println( "Backing img:" );
		printPixels( img );
		System.out.println();

		// show the int-to-labelset mapping
		System.out.println( "Mapping:" );
		final LabelingMapping< String > mapping = labeling.getMapping();
		for ( int i = 0; i < mapping.numSets(); ++i )
		{
			final Set<String> labels = mapping.labelsAtIndex( i );
			System.out.println( i + " => " + labels );
		}
		System.out.println( "occurring labels: " + mapping.getLabels() );
		/*
		 * Note that the mapping contains all label sets that ever occurred, not
		 * only the label sets present at the moment.
		 */
	}

	static < T > void printPixels( final IterableInterval< T > labeling )
	{
		final Cursor< T > cursor = labeling.cursor();
		while ( cursor.hasNext() )
		{
			cursor.fwd();
			System.out.println( Util.printCoordinates( cursor ) + " : " + cursor.get() );
		}
	}
}
