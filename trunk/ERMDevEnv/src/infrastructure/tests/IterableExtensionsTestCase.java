package infrastructure.tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;

import junit.framework.Assert;

public class IterableExtensionsTestCase 
{
	private ArrayList<Integer> iterable1;
	private ArrayList<Integer> iterable2;
	
	@Test
	public void testCountReturnsCorrectCount()
	{
		Assert.assertEquals(50, IterableExtensions.count(iterable1));
		Assert.assertEquals(100, IterableExtensions.count(iterable2));
	}
	
	@Test
	public void testFirstOrDefaultReturnsFirstValueThatMatches()
	{
		class CmpFunc extends Func<Integer, Integer, Boolean> 
		{
			@Override
			public Boolean execute(Integer val, Integer param) 
			{
				return val % param == 0;
			}
		}
		
		int value = IterableExtensions.firstOrDefault(this.iterable1, new CmpFunc(), new Integer(5));
		Assert.assertEquals(5, value);
	}
	
	@Test
	public void testFirstOrDefaultReturnsNullIfValueIsNotFound()
	{
		class CmpFunc extends Func<Integer, Integer, Boolean> 
		{
			@Override
			public Boolean execute(Integer val, Integer param) 
			{
				return val < param;
			}
		}
		
		Assert.assertNull(IterableExtensions.firstOrDefault(this.iterable1, new CmpFunc(), new Integer(0)));
	}
	
	@Before
	public void setUp() throws Exception 
	{
		this.iterable1 = new ArrayList<Integer>();
		for (int i = 1; i < 51; i++) 
		{
			this.iterable1.add(i);
		}
		
		this.iterable2 = new ArrayList<Integer>();
		for (int i = 1; i < 101; i++) 
		{
			this.iterable2.add(i);
		}
	}

	@After
	public void tearDown() throws Exception {
	}

}
