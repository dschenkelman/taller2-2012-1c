package infrastructure;

public class IterableExtensions 
{
	public static <T> int count(Iterable<T> iterable) 
	{		
		int count = 0;
		for (@SuppressWarnings("unused") T item : iterable) 
		{
			count++;
		}
		
		return count;
	}

	public static <T, U> T firstOrDefault(Iterable<T> iterable, Func<T, U, Boolean> func, U param) {
		for (T item : iterable) 
		{
			if (func.execute(item, param))
			{
				return item;
			}
		};
		
		return null;
	}
}
