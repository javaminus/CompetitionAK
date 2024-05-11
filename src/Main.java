import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        long n = sc.nextLong();
        int m = sc.nextInt();
        while (m-- > 0) {
            long q = sc.nextLong();
            long flip = 0;
            for (int i = 0; i < n; i++) {
                if ((q & (1L << i)) != 0) {
                    flip |= (1L << (n - 1 - i));
                }
            }
            System.out.println(flip);
        }
    }
}
