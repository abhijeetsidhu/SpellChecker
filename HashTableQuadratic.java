import java.lang.reflect.Array;

/**
 * Created by Abhijeet Sidhu
 */
public class HashTableQuadratic<T> extends Object implements HashTable<T>, HashMetrics {

    private Object EMPTY_NODE = 123456789;
    private int maxcollisions;
    private int size = 0;
    private double loadFactor;
    private int collisions;
    private T[] arr;   // arr = (T[])new Object[tableSize];
    private long tableSize = 17;



    public HashTableQuadratic() {
        this.loadFactor = .5;
        arr = (T[])new Object[(int)tableSize];
    }

    public HashTableQuadratic(int tableSize) {
        if(tableSize <= 0){
            throw new IllegalArgumentException();
        }

        this.loadFactor = .5;
        int t = PrimeTools.nextPrime(tableSize);
        arr = (T[])new Object[t];
    }

    public HashTableQuadratic(int tableSize, double loadFactor){
        if(tableSize <= 0 || loadFactor <= 0){
            throw new IllegalArgumentException();
        }
        int t = PrimeTools.nextPrime(tableSize);
        this.loadFactor = loadFactor;
        arr = (T[])new Object[t];

    }

    public boolean contains(T element){
        int code = Math.abs(element.hashCode()) % tableSize();
        if(arr[code] == null){

            return false;
        }
        if(arr[code].equals(element)){
            return true;
        }
        else{
            for(long i = 1; i <= tableSize()/2; i++){
                long nextSpot = ((Math.abs(element.hashCode())) + i * i) % tableSize();
                if(arr[(int)nextSpot] == null){

                    return false;
                }
                if(arr[(int)nextSpot].equals(element)){

                    return true;
                }

            }
        }

        return false;

    }

    public boolean isEmpty(){
        if(size() == 0)
            return true;
        else
            return false;

    }

    private void rehash()
    {

        int newtable = (PrimeTools.nextPrime(tableSize() * 2));
        T[] doublearr = (T[])new Object[newtable];
        T[] temp = arr;
        arr = doublearr;
        size = 0;
        for(int i = 0; i < temp.length; i++){

            if(temp[i] == null || temp[i] == EMPTY_NODE ){}

            else
            {
                 add(temp[i]);
            }

        }

    }

    public T add(T element)
    {
        int counter = 0;
        if(element == null){
            throw new IllegalArgumentException();
        }
        int hc = Math.abs(element.hashCode()) % tableSize();

        if (arr[hc] == null)
        {
            arr[hc] = element;
            size++;
            if (loadFactor() > this.loadFactor)
            {
                rehash();
            }
            return null;
        }
        else if (arr[hc] == EMPTY_NODE)
        {
            arr[hc] = element;
            size++;
            if (loadFactor() > this.loadFactor)
            {
                rehash();
            }
            return null;
        }
        else if (arr[hc].equals(element))
        {
           // collisions++;
            T temp = arr[hc];
            arr[hc] = element;
            return temp;
        }

        else
        {

            for (int i = 1; i < tableSize() ; i++){
                counter++;

                long nextSpot = (Math.abs(element.hashCode()) + i*i) % tableSize();

            if (arr[(int) nextSpot] == null)
            {
                arr[(int) nextSpot] = element;
                size++;
                if (loadFactor() > this.loadFactor)
                {
                    rehash();
                }
                if(counter >= maxcollisions){
                    maxcollisions = counter;
                }

                collisions = collisions + counter;

                return null;
            }
            if(arr[(int) nextSpot] == EMPTY_NODE)
            {
                arr[(int) nextSpot] = element;
                size++;
                if (loadFactor() > this.loadFactor)
                {
                    rehash();
                }
                if(counter >= maxcollisions){
                    maxcollisions = counter;
                }

                collisions = collisions + counter;

                return null;
            }
            if(arr[(int) nextSpot].equals(element))
            {
                T temp = arr[(int) nextSpot];
                arr[(int) nextSpot] = element;
                if(counter >= maxcollisions){
                    maxcollisions = counter;
                }

                collisions = collisions + counter;

                return temp;
            }






        }


        }

        throw new HashTableInsertionException();


    }



    public boolean remove(T element){

        int code = Math.abs(element.hashCode()) % tableSize();

        if(arr[code] == null){

            return false;
        }
        if(arr[code].equals(element)){
            arr[code] = (T)EMPTY_NODE;
            size--;
            return true;

        }
        else{
            for(int i = 1; i < tableSize() / 2; i++){

                long nextSpot = ((Math.abs(element.hashCode())) + i * i) % tableSize();

                if(arr[(int)nextSpot] == null){

                    return false;
                }
                if(arr[(int)nextSpot].equals(element)){
                    arr[(int)nextSpot] = (T)EMPTY_NODE;
                    size--;
                    return true;

                }

            }
        }

        return false;



    }

    public int size(){
        return size;

    }

    public double loadFactor(){
       int a = size;
       int b = tableSize();
       double ab = (double)a/b;
       return ab;

    }

    public int tableSize(){
        return arr.length;

    }

    public long collisions(){
        return collisions;

    }

    public int maxCollisions(){
        return maxcollisions;


    }

//    public void printHashTable(){
//        for (int i = 0; i < arr.length; i++) {
//            System.out.println("table: " + arr[i]);
//        }
//    }

    public static class HashTableInsertionException extends RuntimeException{
        public HashTableInsertionException(){
            super();
        }
        public HashTableInsertionException(String msg){
            super(msg);
        }

    }








}
