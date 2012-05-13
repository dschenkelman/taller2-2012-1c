package infrastructure;

import java.util.ArrayList;
import java.util.List;

public class IterableExtensions {
    public static <T> int count(Iterable<T> iterable) {
        int count = 0;
        for (@SuppressWarnings("unused") T item : iterable) {
            count++;
        }
        return count;
    }

    public static <T> List<T> getListOf(Iterable<T> iterable){
        List<T> list = new ArrayList<T>();
        for(T item : iterable){
            list.add(item);
        }
        return list;
    }

    public static <T, U> T firstOrDefault(Iterable<T> iterable, Func<T, U, Boolean> func, U param) {
        for (T item : iterable) {
            if (func.execute(item, param)) {
                return item;
            }
        }
        return null;
    }

    public static <T, U> Iterable<T> where(Iterable<T> iterable, Func<T, U, Boolean> func, U param) {

        ArrayList<T> allItems = new ArrayList<T>();
        for (T item : iterable) {
            if (func.execute(item, param)) {
                allItems.add(item);
            }
        }
        return allItems;
    }
}
