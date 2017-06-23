package t01sandbox;

import net.imglib2.Point;
import net.imglib2.RandomAccess;
import net.imglib2.type.numeric.NumericType;

public class CentralDifferenceRandomAccess< T extends NumericType< T > > extends Point implements RandomAccess< T >
{
	private final RandomAccess< T > s;

	private final T myValue;

	public CentralDifferenceRandomAccess(
			final RandomAccess< T > s,
			final T myValue )
	{
		super( s.numDimensions() );
		this.s = s;
		this.myValue = myValue;
	}

	public CentralDifferenceRandomAccess(final CentralDifferenceRandomAccess<T> other )
	{
		super( other );
		this.s = other.s.copyRandomAccess();
		this.myValue = other.myValue.copy();
	}

	@Override
	public T get()
	{
		s.setPosition( this );
		s.fwd( 0 );
		myValue.set( s.get() );
		s.bck( 0 );
		s.bck( 0 );
		myValue.sub( s.get() );
		myValue.mul( 0.5 );
		return myValue;
	}

	@Override
	public CentralDifferenceRandomAccess< T > copy()
	{
		return new CentralDifferenceRandomAccess<>( this );
	}

	@Override
	public CentralDifferenceRandomAccess< T > copyRandomAccess()
	{
		return new CentralDifferenceRandomAccess<>( this );
	}
}
