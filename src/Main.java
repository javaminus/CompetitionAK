import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    public static void main(String[] args) {
        int T = sc.nextInt();
        while (T-- > 0) {
            solve();
        }
    }

    private static void solve() {
        int n = sc.nextInt();
        int m = sc.nextInt();
        String s = sc.next();
        int[] cnt = new int[7];
        for (char c : s.toCharArray()) {
            cnt[c - 'A']++;
        }
        int ans = 0;
        for (int i = 0; i < 7; i++) {
            if (cnt[i] < m) {
                ans += m - cnt[i];
            }
        }
        System.out.println(ans);
    }

}