import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Standard {
    static int n, m, k, ans, t;
    static int[] a = new int[1000002];
    static int[][] dp = new int[100002][6];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        t = read(br);
        while (t-- > 0) {
            n = read(br);
            m = read(br);
            k = read(br);
            ans = 0;
            for (int i = 1; i <= n; i++) {
                a[i] = read(br);
            }
            for (int i = 0; i <= k; i++) {
                for (int j = 1; j <= n; j++) {
                    dp[j][i] = dp[j - 1][i] + a[j];
                    if (i != 0 && j != n) {
                        dp[j][i] = Math.max(dp[j][i], dp[j + 1][i - 1] + a[j]);
                    }
                    if (j - 1 + i * 2 == m) {
                        ans = Math.max(ans, dp[j][i]);
                    }
                }
            }
            System.out.println(Arrays.toString(dp[1]));
            System.out.println(ans);
        }
    }

    private static int read(BufferedReader br) throws IOException {
        int t = 0;
        char v = (char) br.read();
        while (v < '0') v = (char) br.read();
        while (v >= '0') {
            t = (t << 3) + (t << 1) + v - '0';
            v = (char) br.read();
        }
        return t;
    }
}