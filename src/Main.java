import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static final int MOD = 1000000007;
    static final int MAX = 1000000;
    static long[] dp = new long[MAX + 1];

    static {
        dp[0] = 1;
        if (MAX >= 1) {
            dp[1] = 1;
        }
        for (int i = 2; i <= MAX; i++) {
            dp[i] = ((dp[i - 1] + ((i - 1) * dp[i - 2]) % MOD) % MOD);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            int n = Integer.parseInt(br.readLine());
            sb.append(dp[n]).append('\n');
        }
        System.out.print(sb);
    }
}