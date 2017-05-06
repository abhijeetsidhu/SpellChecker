/**
 * Created by Abhijeet Sidhu
 */
public interface HashTable<T> {
    T add(T element);
    boolean contains(T element);
    boolean isEmpty();
    double loadFactor();
    boolean remove(T element);
    int size();
    int tableSize();
}
