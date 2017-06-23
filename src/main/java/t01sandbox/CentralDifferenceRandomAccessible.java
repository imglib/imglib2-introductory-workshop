package t01sandbox;

import net.imglib2.Interval;
import net.imglib2.RandomAccessible;
import net.imglib2.type.numeric.NumericType;

public class CentralDifferenceRandomAccessible< T extends NumericType< T > > implements RandomAccessible< T >
{

	private final RandomAccessible< T > s;

	private final T type;

	public CentralDifferenceRandomAccessible(
			final RandomAccessible< T > s )
	{
		this.s = s;
		this.type = s.randomAccess().get().copy();
	}

	@Override
	public int numDimensions()
	{
		return s.numDimensions();
	}

	@Override
	public CentralDifferenceRandomAccess< T > randomAccess()
	{
		return new CentralDifferenceRandomAccess<>( s.randomAccess(), type.copy() );
	}

	@Override
	public CentralDifferenceRandomAccess< T > randomAccess( final Interval interval )
	{
		return randomAccess();
	}
}
