// package niuke.round.round29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {

    static long mod = (long)1e9 + 7;

    static int MZ = 1000;
    static long[] gFac = new long[MZ + 1];
    static long[] gInv = new long[MZ + 1];
    static {
        gFac[0] = 1;
        for (int i = 1; i <= MZ; i++) {
            gFac[i] = gFac[i-1] * i % mod;
        }
        gInv[MZ] = BigInteger.valueOf(gFac[MZ]).modInverse(BigInteger.valueOf(mod)).longValue();
        for (int i = MZ - 1; i >= 0; i--) {
            gInv[i] = gInv[i + 1] * (i + 1) % mod;
        }
    }

    static Map<Long, Long> memo = new HashMap<>();
    static long dfs(int x, int y) {
        // 直接使用技能2
        if (x > 0 && y == 0) {
            return 1;
        }
        // 必输态
        if (x == 0 && y == 0) {
            return 0;
        }

        long k = ((long)x << 32) | y;
        if (memo.containsKey(k)) {
            return memo.get(k);
        }
//        long inv = BigInteger.valueOf(x + y).modInverse(BigInteger.valueOf(mod)).longValue();
//        long inv = gInv[x + y];
        long inv = gInv[x + y] * gFac[x + y - 1] % mod;

        long r = 0;
        if (x > 0) {
            long p = dfs(x - 1, y);
            r += (1 - p) * x % mod * inv % mod;
        }

        if (y > 0) {
            long p = dfs(x + 1, y - 1);
            r += (1 - p) * y % mod * inv % mod;
        }

        r = (r % mod + mod) % mod;
        memo.put(k, r);
        return r;
    }


    public static void main(String[] args) {
        AReader sc = new AReader();
        int x = 0, y = 0;
        int n = sc.nextInt();
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
            if (arr[i] == 1) x++;
            else if (arr[i] == 2) y++;
        }
        long p = dfs(x, y);
        System.out.println(p);
    }

    static
    class AReader {
        private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer = new StringTokenizer("");
        private String innerNextLine() {
            try {
                return reader.readLine();
            } catch (IOException ex) {
                return null;
            }
        }
        public boolean hasNext() {
            while (!tokenizer.hasMoreTokens()) {
                String nextLine = innerNextLine();
                if (nextLine == null) {
                    return false;
                }
                tokenizer = new StringTokenizer(nextLine);
            }
            return true;
        }
        public String nextLine() {
            tokenizer = new StringTokenizer("");
            return innerNextLine();
        }
        public String next() {
            hasNext();
            return tokenizer.nextToken();
        }
        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }
    }

}
