import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main{
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    private static int Mod = 998244353;
    public static void main(String[] args) {
        int n = sc.nextInt();
        int[] nums = new int[n];
        long sum = 1;
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
            sum *= nums[i];
            long p = (long) Math.sqrt(sum);
            for (int j = 2; j <= p; j++) {
                if (sum % ((long) j * j) == 0) {
                    sum = sum / ((long) j * j);
                }
            }
        }




    }

}