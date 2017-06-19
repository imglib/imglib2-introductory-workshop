package t05labelings;

import io.scif.img.ImgIOException;
import net.imglib2.Cursor;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelingType;

public class T05E01Labeling
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
		final ImgLabeling< String, ? > labeling = new ImgLabeling<>( ArrayImgs.ints( 2, 2 ) );

		/*
		 * Get the LabelingType<String> for the pixel at 0,0.
		 *
		 * LabelingType< T > implements
		 * Type< LabelingType< T > > and
		 * Set< T >
		 */
		final Cursor< LabelingType< String > > cursor = labeling.cursor();
		final LabelingType< String > labels = cursor.next();

		/*
		 * Do some operations on the Set<String> of labels for pixel 0,0
		 */
		labels.add( "A" );
		System.out.println( labels );

		labels.add( "B" );
		System.out.println( labels );

		labels.remove( "A" );
		labels.add( "C" );
		System.out.println( labels );

		System.out.println( labels.contains( "A" ) );

		System.out.println( labels.contains( "B" ) );

		System.out.println( labels.size() );
	}
}
