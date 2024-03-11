import java.util.Scanner;
// 1:无需package
// 2: 类名必须Main, 不可修改

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] memo = new int[]{0b1111110, 0b0000110, 0b1011011, 0b1001111, 0b0100111, 0b1101101, 0b1111101, 0b1000110, 0b1111111, 0b1101111};
        String s = scan.next();
        String t = scan.next();
        int n = s.length();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Integer.bitCount(memo[s.charAt(i) - '0'] ^ memo[t.charAt(i) - '0']);
        }
        System.out.println(ans);
        scan.close();
    }
}