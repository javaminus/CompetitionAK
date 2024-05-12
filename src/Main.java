import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class Main{
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int n = sc.nextInt();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
        }
        Arrays.sort(nums);
        if (nums.length == 1) {
            System.out.println(0);
        } else if ((n & 1) == 0) {
            long mx = Long.MIN_VALUE, mn = Long.MAX_VALUE;
            for (int i = 0; i <= n / 2; i++) {
                int j = n - 1 - i;
                mx = Math.max(mx, (long) nums[i] * nums[j]);
                mn = Math.min(mn, (long) nums[i] * nums[j]);
            }
            System.out.println(mx - mn);
        }else{
            // 为奇数
            long mx = Long.MIN_VALUE, mn = Long.MAX_VALUE;
            for (int i = 0; i <= (n - 1) / 2; i++) {
                int j = n - 2 - i;
                mx = Math.max(mx, (long) nums[i] * nums[j]);
                mn = Math.min(mn, (long) nums[i] * nums[j]);
            }
            System.out.println(mx - mn);
        }
    }

}