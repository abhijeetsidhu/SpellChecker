/**
 * Created by Abhijeet Sidhu
 */
public class PrimeTools extends Object {

    public static boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;
        return true;
    }

    public static int nextPrime(int value) {
        if (value < 0) {
            throw new IllegalArgumentException();
        }
        if (isPrime(value)) {
            return value;
        }
        else {
            while(isPrime(value) == false){
                value = value + 1;
            }
            return value;
        }

    }

}
