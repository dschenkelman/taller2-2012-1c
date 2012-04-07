package models;

import infrastructure.Func;
import infrastructure.IterableExtensions;

import java.util.ArrayList;
import java.util.List;

public abstract class ModelCollection<T extends INameable> {
	protected List<T> items;

	public ModelCollection() {
		this.items = new ArrayList<T>();
	}

	public int count() {
		return this.items.size();
	}

	public boolean add(String itemName) {
		if (this.get(itemName) == null) 
		{
			T item = this.createItemInstance(itemName);
			this.items.add(item);
			return true;
		}
		return false;
	}

	protected abstract T createItemInstance (String itemName);
	
	
	public T get(String itemName) {
		return IterableExtensions.firstOrDefault(this.items,
				new Func<T, String, Boolean>() {
					@Override
					public Boolean execute(T item, String itemName) {
						return item.getName() == itemName;
					}
				}, itemName);
	}	

	public boolean remove(String itemName) {
		T item = this.get(itemName);
		return this.items.remove(item);
	}


}