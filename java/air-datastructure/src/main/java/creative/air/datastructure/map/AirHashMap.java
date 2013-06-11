package creative.air.datastructure.map;

/**
 * 
 * @author
 * Eric Han feuyeux@gmail.com
 * 16/09/2012
 * @since  0.0.1
 * @version 0.0.1
 */
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class AirHashMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Cloneable, Serializable {
	private static final long serialVersionUID = 3476735979928755996L;
	static final int DEFAULT_INITIAL_CAPACITY = 16;// 初始容量
	static final int MAXIMUM_CAPACITY = 1 << 30;
	static final float DEFAULT_LOAD_FACTOR = 0.75f;// 负载因子
	transient AirEntry<K, V>[] table;// hash数组
	transient int size;
	int threshold;// 阈值
	final float loadFactor;
	transient int modCount;// 修改次数

	public AirHashMap(int initialCapacity, float loadFactor) {
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
		if (initialCapacity > MAXIMUM_CAPACITY)
			initialCapacity = MAXIMUM_CAPACITY;
		if (loadFactor <= 0 || Float.isNaN(loadFactor))
			throw new IllegalArgumentException("Illegal load factor: " + loadFactor);

		// Find a power of 2 >= initialCapacity
		int capacity = 1;
		while (capacity < initialCapacity)
			capacity <<= 1;

		this.loadFactor = loadFactor;
		threshold = (int) (capacity * loadFactor);
		table = new AirEntry[capacity];
		init();
	}

	public AirHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_LOAD_FACTOR);
	}

	public AirHashMap() {
		this.loadFactor = DEFAULT_LOAD_FACTOR;
		threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
		table = new AirEntry[DEFAULT_INITIAL_CAPACITY];
		init();
	}

	public AirHashMap(Map<? extends K, ? extends V> m) {
		this(Math.max((int) (m.size() / DEFAULT_LOAD_FACTOR) + 1, DEFAULT_INITIAL_CAPACITY), DEFAULT_LOAD_FACTOR);
		putAllForCreate(m);
	}

	void init() {
	}

	static int hash(int h) {
		// This function ensures that hashCodes that differ only by
		// constant multiples at each bit position have a bounded
		// number of collisions (approximately 8 at default load factor).
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	static int indexFor(int h, int length) {
		return h & (length - 1);
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public V get(Object key) {
		if (key == null)
			return getForNullKey();
		int hash = hash(key.hashCode());
		for (AirEntry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || key.equals(k)))
				return e.value;
		}
		return null;
	}

	private V getForNullKey() {
		for (AirEntry<K, V> e = table[0]; e != null; e = e.next) {
			if (e.key == null)
				return e.value;
		}
		return null;
	}

	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	final AirEntry<K, V> getEntry(Object key) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		for (AirEntry<K, V> e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k))))
				return e;
		}
		return null;
	}

	public V put(K key, V value) {
		if (key == null)
			return putForNullKey(value);
		int hash = hash(key.hashCode());
		int i = indexFor(hash, table.length);
		for (AirEntry<K, V> e = table[i]; e != null; e = e.next) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
				V oldValue = e.value;
				e.value = value;
				e.recordAccess(this);
				return oldValue;
			}
		}

		modCount++;
		addEntry(hash, key, value, i);
		return null;
	}

	private V putForNullKey(V value) {
		for (AirEntry<K, V> e = table[0]; e != null; e = e.next) {
			if (e.key == null) {
				V oldValue = e.value;
				e.value = value;
				e.recordAccess(this);
				return oldValue;
			}
		}
		modCount++;
		addEntry(0, null, value, 0);
		return null;
	}

	private void putForCreate(K key, V value) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		int i = indexFor(hash, table.length);

		/**
		 * Look for preexisting entry for key. This will never happen for clone or deserialize. It will only happen for construction if the input Map is a
		 * sorted map whose ordering is inconsistent w/ equals.
		 */
		for (AirEntry<K, V> e = table[i]; e != null; e = e.next) {
			Object k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
				e.value = value;
				return;
			}
		}

		createEntry(hash, key, value, i);
	}

	private void putAllForCreate(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			putForCreate(e.getKey(), e.getValue());
	}

	void resize(int newCapacity) {
		AirEntry[] oldTable = table;
		int oldCapacity = oldTable.length;
		if (oldCapacity == MAXIMUM_CAPACITY) {
			threshold = Integer.MAX_VALUE;
			return;
		}

		AirEntry[] newTable = new AirEntry[newCapacity];
		transfer(newTable);
		table = newTable;
		threshold = (int) (newCapacity * loadFactor);
	}

	void transfer(AirEntry[] newTable) {
		AirEntry[] src = table;
		int newCapacity = newTable.length;
		for (int j = 0; j < src.length; j++) {
			AirEntry<K, V> e = src[j];
			if (e != null) {
				src[j] = null;
				do {
					AirEntry<K, V> next = e.next;
					int i = indexFor(e.hash, newCapacity);
					e.next = newTable[i];
					newTable[i] = e;
					e = next;
				} while (e != null);
			}
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		int numKeysToBeAdded = m.size();
		if (numKeysToBeAdded == 0)
			return;

		if (numKeysToBeAdded > threshold) {
			int targetCapacity = (int) (numKeysToBeAdded / loadFactor + 1);
			if (targetCapacity > MAXIMUM_CAPACITY)
				targetCapacity = MAXIMUM_CAPACITY;
			int newCapacity = table.length;
			while (newCapacity < targetCapacity)
				newCapacity <<= 1;
			if (newCapacity > table.length)
				resize(newCapacity);
		}

		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			put(e.getKey(), e.getValue());
	}

	public V remove(Object key) {
		AirEntry<K, V> e = removeEntryForKey(key);
		return (e == null ? null : e.value);
	}

	final AirEntry<K, V> removeEntryForKey(Object key) {
		int hash = (key == null) ? 0 : hash(key.hashCode());
		int i = indexFor(hash, table.length);
		AirEntry<K, V> prev = table[i];
		AirEntry<K, V> e = prev;

		while (e != null) {
			AirEntry<K, V> next = e.next;
			Object k;
			if (e.hash == hash && ((k = e.key) == key || (key != null && key.equals(k)))) {
				modCount++;
				size--;
				if (prev == e)
					table[i] = next;
				else
					prev.next = next;
				e.recordRemoval(this);
				return e;
			}
			prev = e;
			e = next;
		}

		return e;
	}

	final AirEntry<K, V> removeMapping(Object o) {
		if (!(o instanceof Map.Entry))
			return null;

		Map.Entry<K, V> entry = (Map.Entry<K, V>) o;
		Object key = entry.getKey();
		int hash = (key == null) ? 0 : hash(key.hashCode());
		int i = indexFor(hash, table.length);
		AirEntry<K, V> prev = table[i];
		AirEntry<K, V> e = prev;

		while (e != null) {
			AirEntry<K, V> next = e.next;
			if (e.hash == hash && e.equals(entry)) {
				modCount++;
				size--;
				if (prev == e)
					table[i] = next;
				else
					prev.next = next;
				e.recordRemoval(this);
				return e;
			}
			prev = e;
			e = next;
		}

		return e;
	}

	public void clear() {
		modCount++;
		AirEntry[] tab = table;
		for (int i = 0; i < tab.length; i++)
			tab[i] = null;
		size = 0;
	}

	public boolean containsValue(Object value) {
		if (value == null)
			return containsNullValue();

		AirEntry[] tab = table;
		for (int i = 0; i < tab.length; i++)
			for (AirEntry e = tab[i]; e != null; e = e.next)
				if (value.equals(e.value))
					return true;
		return false;
	}

	private boolean containsNullValue() {
		AirEntry[] tab = table;
		for (int i = 0; i < tab.length; i++)
			for (AirEntry e = tab[i]; e != null; e = e.next)
				if (e.value == null)
					return true;
		return false;
	}

	public Object clone() {
		AirHashMap<K, V> result = null;
		try {
			result = (AirHashMap<K, V>) super.clone();
		} catch (CloneNotSupportedException e) {
			// assert false;
		}
		result.table = new AirEntry[table.length];
		result.entrySet = null;
		result.modCount = 0;
		result.size = 0;
		result.init();
		result.putAllForCreate(this);

		return result;
	}

	void addEntry(int hash, K key, V value, int bucketIndex) {
		AirEntry<K, V> e = table[bucketIndex];
		table[bucketIndex] = new AirEntry<K, V>(hash, key, value, e);
		if (size++ >= threshold)
			resize(2 * table.length);
	}

	void createEntry(int hash, K key, V value, int bucketIndex) {
		AirEntry<K, V> e = table[bucketIndex];
		table[bucketIndex] = new AirEntry<K, V>(hash, key, value, e);
		size++;
	}

	private final class ValueIterator extends HashIterator<V> {
		public V next() {
			return nextEntry().value;
		}
	}

	private final class KeyIterator extends HashIterator<K> {
		public K next() {
			return nextEntry().getKey();
		}
	}

	private final class EntryIterator extends HashIterator<Map.Entry<K, V>> {
		public Map.Entry<K, V> next() {
			return nextEntry();
		}
	}

	// Subclass overrides these to alter behavior of views' iterator() method
	Iterator<K> newKeyIterator() {
		return new KeyIterator();
	}

	Iterator<V> newValueIterator() {
		return new ValueIterator();
	}

	Iterator<Map.Entry<K, V>> newEntryIterator() {
		return new EntryIterator();
	}

	private transient Set<Map.Entry<K, V>> entrySet = null;

	// public Set<K> keySet() {
	// Set<K> ks = keySet;
	// return (ks != null ? ks : (keySet = new KeySet()));
	// }

	private final class KeySet extends AbstractSet<K> {
		public Iterator<K> iterator() {
			return newKeyIterator();
		}

		public int size() {
			return size;
		}

		public boolean contains(Object o) {
			return containsKey(o);
		}

		public boolean remove(Object o) {
			return AirHashMap.this.removeEntryForKey(o) != null;
		}

		public void clear() {
			AirHashMap.this.clear();
		}
	}

	// public Collection<V> values() {
	// Collection<V> vs = values;
	// return (vs != null ? vs : (values = new Values()));
	// }

	private final class Values extends AbstractCollection<V> {
		public Iterator<V> iterator() {
			return newValueIterator();
		}

		public int size() {
			return size;
		}

		public boolean contains(Object o) {
			return containsValue(o);
		}

		public void clear() {
			AirHashMap.this.clear();
		}
	}

	public Set<Map.Entry<K, V>> entrySet() {
		return entrySet0();
	}

	private Set<Map.Entry<K, V>> entrySet0() {
		Set<Map.Entry<K, V>> es = entrySet;
		return es != null ? es : (entrySet = new EntrySet());
	}

	private final class EntrySet extends AbstractSet<Map.Entry<K, V>> {
		public Iterator<Map.Entry<K, V>> iterator() {
			return newEntryIterator();
		}

		public boolean contains(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry<K, V> e = (Map.Entry<K, V>) o;
			AirEntry<K, V> candidate = getEntry(e.getKey());
			return candidate != null && candidate.equals(e);
		}

		public boolean remove(Object o) {
			return removeMapping(o) != null;
		}

		public int size() {
			return size;
		}

		public void clear() {
			AirHashMap.this.clear();
		}
	}

	private void writeObject(java.io.ObjectOutputStream s) throws IOException {
		Iterator<Map.Entry<K, V>> i = (size > 0) ? entrySet0().iterator() : null;

		// Write out the threshold, loadfactor, and any hidden stuff
		s.defaultWriteObject();

		// Write out number of buckets
		s.writeInt(table.length);

		// Write out size (number of Mappings)
		s.writeInt(size);

		// Write out keys and values (alternating)
		if (i != null) {
			while (i.hasNext()) {
				Map.Entry<K, V> e = i.next();
				s.writeObject(e.getKey());
				s.writeObject(e.getValue());
			}
		}
	}

	private void readObject(java.io.ObjectInputStream s) throws IOException, ClassNotFoundException {
		// Read in the threshold, loadfactor, and any hidden stuff
		s.defaultReadObject();

		// Read in number of buckets and allocate the bucket array;
		int numBuckets = s.readInt();
		table = new AirEntry[numBuckets];

		init(); // Give subclass a chance to do its thing.

		// Read in size (number of Mappings)
		int size = s.readInt();

		// Read the keys and values, and put the mappings in the AirHashMap
		for (int i = 0; i < size; i++) {
			K key = (K) s.readObject();
			V value = (V) s.readObject();
			putForCreate(key, value);
		}
	}

	// These methods are used when serializing HashSets
	int capacity() {
		return table.length;
	}

	float loadFactor() {
		return loadFactor;
	}

	// ====Entry====
	static class AirEntry<K, V> implements Map.Entry<K, V> {
		final K key;
		V value;
		AirEntry<K, V> next;
		final int hash;

		/**
		 * Creates new entry.
		 */
		AirEntry(int h, K k, V v, AirEntry<K, V> n) {
			value = v;
			next = n;
			key = k;
			hash = h;
		}

		public final K getKey() {
			return key;
		}

		public final V getValue() {
			return value;
		}

		public final V setValue(V newValue) {
			V oldValue = value;
			value = newValue;
			return oldValue;
		}

		public final boolean equals(Object o) {
			if (!(o instanceof Map.Entry))
				return false;
			Map.Entry e = (Map.Entry) o;
			Object k1 = getKey();
			Object k2 = e.getKey();
			if (k1 == k2 || (k1 != null && k1.equals(k2))) {
				Object v1 = getValue();
				Object v2 = e.getValue();
				if (v1 == v2 || (v1 != null && v1.equals(v2)))
					return true;
			}
			return false;
		}

		public final int hashCode() {
			return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
		}

		public final String toString() {
			return getKey() + "=" + getValue();
		}

		/**
		 * This method is invoked whenever the value in an entry is overwritten by an invocation of put(k,v) for a key k that's already in the AirHashMap.
		 */
		void recordAccess(AirHashMap<K, V> m) {
		}

		/**
		 * This method is invoked whenever the entry is removed from the table.
		 */
		void recordRemoval(AirHashMap<K, V> m) {
		}
	}

	// ====HashIterator====
	private abstract class HashIterator<E> implements Iterator<E> {
		AirEntry<K, V> next; // next entry to return
		int expectedModCount; // For fast-fail
		int index; // current slot
		AirEntry<K, V> current; // current entry

		HashIterator() {
			expectedModCount = modCount;
			if (size > 0) { // advance to first entry
				AirEntry[] t = table;
				while (index < t.length && (next = t[index++]) == null)
					;
			}
		}

		public final boolean hasNext() {
			return next != null;
		}

		final AirEntry<K, V> nextEntry() {
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			AirEntry<K, V> e = next;
			if (e == null)
				throw new NoSuchElementException();

			if ((next = e.next) == null) {
				AirEntry[] t = table;
				while (index < t.length && (next = t[index++]) == null)
					;
			}
			current = e;
			return e;
		}

		public void remove() {
			if (current == null)
				throw new IllegalStateException();
			if (modCount != expectedModCount)
				throw new ConcurrentModificationException();
			Object k = current.key;
			current = null;
			AirHashMap.this.removeEntryForKey(k);
			expectedModCount = modCount;
		}
	}

	public AirEntry<K, V>[] getTable() {
		return table;
	}
}
