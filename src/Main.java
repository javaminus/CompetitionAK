import java.io.*;
import java.util.Scanner;

public class Main{
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    static int n, k;
    static String s;
    public static void main(String[] args) throws IOException {
        n = sc.nextInt();
        k = sc.nextInt();
        s = sc.next();
        solve();
    }

    private static void solve() {
        char[] chars = s.toCharArray();
        // 只有遇到10交换
        for (int i = 0; i < s.length(); i++) {
            if (chars[i] == '0') {
                continue;
            }
            if (i + 1 < n && chars[i + 1] == '0') {
                chars[i] = '0';
                chars[i + 1] = '1';
                k--;
                if (i > 0 && chars[i - 1] == '1') {
                    i -= 2;
                }
            }
            if (k == 0) {
                break;
            }
        }
        System.out.println(new String(chars));

    }
}