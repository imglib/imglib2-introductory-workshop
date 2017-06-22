

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import bdv.util.Bdv;
import bdv.util.BdvFunctions;
import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.converter.Converter;
import net.imglib2.converter.Converters;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.display.imagej.ImageJFunctions;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.ARGBType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.util.Intervals;
import net.imglib2.view.Views;

public class LabelingOverlay
{
	public static class MyConverter implements Converter< LabelingType< Integer >, ARGBType >
	{
		Integer selectedLabel = 0;

		public void setSelectedLabel( final Integer selectedLabel )
		{
			this.selectedLabel = selectedLabel;
		}

		@Override
		public void convert( final LabelingType< Integer > input, final ARGBType output )
		{
			if ( input.contains( selectedLabel ) )
				output.set( 0xff0000 );
//			else if ( input.contains( 102 ) )
//				output.set( 0x00ff00 );
			else
				output.set( 0x000000 );
		}
	};


	public static void main( final String[] args )
	{
		new ImageJ();
		final ImagePlus imp = IJ.openImage( LabelingOverlay.class.getResource( "/DrosophilaWing.tif" ).getFile() );
		final RandomAccessibleInterval< UnsignedByteType > img = ImageJFunctions.wrap( imp );
		ImageJFunctions.show( img );

		// load image with integer "labels"
		final ImagePlus imp2 = IJ.openImage( LabelingOverlay.class.getResource( "/DrosophilaWingLabels.tif" ).getFile() );
		final RandomAccessibleInterval< UnsignedByteType > imglabels = ImageJFunctions.wrap( imp2 );
		ImageJFunctions.show( imglabels );

		// create a ImgLabeling and transfer the integer "labels" to LabelingType< Integer >
		final ImgLabeling< Integer, IntType > labeling = new ImgLabeling<>( ArrayImgs.ints( Intervals.dimensionsAsLongArray( imglabels ) ) );
		Views.interval( Views.pair( imglabels, labeling ), imglabels ).forEach( p -> {
			final UnsignedByteType in = p.getA();
			final LabelingType< Integer > out = p.getB();
			if ( in.get() != 0 )
				out.add( in.get() );
		} );

		System.out.println( labeling.getMapping().getLabels() );

		final MyConverter labelsToColors = new MyConverter();
		labelsToColors.setSelectedLabel( new Integer( 102 ) );
		final RandomAccessibleInterval< ARGBType > colored = Converters.convert(
				( RandomAccessibleInterval< LabelingType< Integer > > ) labeling,
				labelsToColors,
				new ARGBType() );

		final Bdv bdv = BdvFunctions.show( img, "image data", Bdv.options().is2D() );
		BdvFunctions.show( colored, "labels", Bdv.options().addTo( bdv ) );

		final JFrame frame = new JFrame();
		final BoxLayout boxLayout = new BoxLayout( frame.getContentPane(), BoxLayout.Y_AXIS );
		frame.setLayout( boxLayout );
		frame.add( new JButton( new AbstractAction( "102" )
		{
			@Override
			public void actionPerformed( final ActionEvent e )
			{
				labelsToColors.setSelectedLabel( 102 );
				bdv.getBdvHandle().getViewerPanel().requestRepaint();
			}
		} ) );
		frame.add( new JButton( new AbstractAction( "88" )
		{
			@Override
			public void actionPerformed( final ActionEvent e )
			{
				labelsToColors.setSelectedLabel( 88 );
				bdv.getBdvHandle().getViewerPanel().requestRepaint();
			}
		} ) );
		frame.add( new JButton( new AbstractAction( "235" )
		{
			@Override
			public void actionPerformed( final ActionEvent e )
			{
				labelsToColors.setSelectedLabel( 235 );
				bdv.getBdvHandle().getViewerPanel().requestRepaint();
			}
		} ) );
		frame.pack();
		frame.setVisible( true );
	}
}
