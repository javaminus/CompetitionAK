import java.util.*;

public class Main {
    static int len = 0;
    static long sum = 0;
    static final int MOD = 1000000007;

    public static boolean dfs(int u, int pre, int target, int cnt, long curSum, int[] w, List<Integer>[] g) {
        curSum += w[u];
        if (target == u) {
            len = cnt;
            sum = curSum;
            return true;
        }

        for (int v : g[u]) {
            if (pre == v) continue;
            if (dfs(v, u, target, cnt + 1, curSum, w, g)) {
                return true;
            }
        }
        return false;
    }

    public static void extendgcd(int a, int b, int[] xy) {
        if (b == 0) {
            xy[0] = 1;
            xy[1] = 0;
            return;
        }

        int[] xy1 = new int[2];
        extendgcd(b, a % b, xy1);
        xy[0] = xy1[1];
        xy[1] = xy1[0] - a / b * xy1[1];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = scanner.nextInt();
        }

        List<Integer>[] g = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            g[u - 1].add(v - 1);
            g[v - 1].add(u - 1);
        }

        int q = scanner.nextInt();
        while (q-- > 0) {
            int u = scanner.nextInt();
            int v = scanner.nextInt();
            len = 0;
            sum = 0;
            dfs(u - 1, -1, v - 1, 1, 0, w, g);
            int[] xy = new int[2];
            extendgcd(len, MOD, xy);
            int inv_b = xy[0];
            while (inv_b < 0) {
                inv_b += MOD;
            }

            long res = ((long) inv_b % MOD) * (sum % MOD);
            System.out.println(res % MOD);
        }
    }
}