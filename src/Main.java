import java.io.*;
import java.math.BigInteger;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private final static int INF = Integer.MAX_VALUE / 2;
    private final static int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    static class Read {
        BufferedReader bf;
        StringTokenizer st;
        BufferedWriter bw;

        public Read() {
            bf = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        public String nextLine() throws IOException {
            return bf.readLine();
        }

        public String next() throws IOException {
            while (!st.hasMoreTokens()) {
                st = new StringTokenizer(bf.readLine());
            }
            return st.nextToken();
        }

        public char nextChar() throws IOException {
            return next().charAt(0);
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public float nextFloat() throws IOException {
            return Float.parseFloat(next());
        }

        public byte nextByte() throws IOException {
            return Byte.parseByte(next());
        }

        public short nextShort() throws IOException {
            return Short.parseShort(next());
        }

        public BigInteger nextBigInteger() throws IOException {
            return new BigInteger(next());
        }

        public void println(int a) throws IOException {
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }

        public void print(int a) throws IOException {
            bw.write(String.valueOf(a));
            return;
        }

        public void println(String a) throws IOException {
            bw.write(a);
            bw.newLine();
            return;
        }

        public void print(String a) throws IOException {
            bw.write(a);
            return;
        }

        public void println(long a) throws IOException {
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }

        public void print(long a) throws IOException {
            bw.write(String.valueOf(a));
            return;
        }

        public void println(double a) throws IOException {
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }

        public void print(double a) throws IOException {
            bw.write(String.valueOf(a));
            return;
        }

        public void print(BigInteger a) throws IOException {
            bw.write(a.toString());
            return;
        }

        public void print(char a) throws IOException {
            bw.write(String.valueOf(a));
            return;
        }

        public void println(char a) throws IOException {
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
    }

    static class Pair<T, U> {
        T fir;
        U sec;
        public Pair(T fir, U sec) {
            this.fir = fir;
            this.sec = sec;
        }
    }

    private static long qpow(long a, long b, long p) {
        long res = 1L;
        while (b > 0) {
            if ((b & 1) == 1) {
                res = (res * a) % p;
            }
            a = a * a % p;
            b >>= 1;
        }
        return res;
    }

    private static long sqrt(long N) { // 二分查找快速开方
        long lo = 1;
        long hi = N;
        long ans = 0;
        while(lo <= hi) {
            long mid = (lo + hi) / 2;
            if (mid <= N / mid) {
                ans = mid;
                lo = mid + 1;
            }  else {
                hi = mid - 1;
            }
        }
        return ans;
    }

    private static void reverse(char[] s) {
        int l = 0, r = s.length - 1;
        while (l <= r) {
            char tmp = s[l];
            s[l] = s[r];
            s[r] = tmp;
            l++;
            r--;
        }
    }

    private static void reverse(int[] s) {
        int l = 0, r = s.length - 1;
        while (l <= r) {
            int tmp = s[l];
            s[l] = s[r];
            s[r] = tmp;
            l++;
            r--;
        }
    }

    private static void reverse(long[] s) {
        int l = 0, r = s.length - 1;
        while (l <= r) {
            long tmp = s[l];
            s[l] = s[r];
            s[r] = tmp;
            l++;
            r--;
        }
    }

    static Read sc = new Read();
    private static final int Mod = (int) 1e9 + 7;
    private static int T = 1;

    public static void main(String[] args) throws IOException {
        // int T = sc.nextInt();
        while (T-- > 0) {
            solve();
            // sc.bw.flush();
        }
        sc.bw.flush();
        sc.bw.close();
    }

    private static String[] ss;
    private static String s;
    private static char[] cs;
    private static List<Integer>[] g;
    private static int m, n;


    private static void solve() throws IOException {
        ss = sc.nextLine().split(" ");
        int n = Integer.parseInt(ss[0]), a = Integer.parseInt(ss[1]), b = Integer.parseInt(ss[2]), c = Integer.parseInt(ss[3]);
        int[] nums = new int[n];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = a;
            for (int j = i + 1; j < n; j++) {
                int r = Math.max(dp[i + 1][j], dp[i][j - 1]) + a;
                for (int k = i + 1; k < j; k++) {
                    r = Math.max(r, dp[i][k] + dp[k + 1][j]);
                }
                if (nums[i] == nums[j]) {
                    r = Math.max(r, dp[i + 1][j - 1] + b);
                    for (int k = i + 1; k < j; k++) {
                        if (nums[k] == nums[i]) {
                            r = Math.max(r, dp[i + 1][k - 1] + dp[k + 1][j - 1] + c);
                        }
                    }
                }
                dp[i][j] = r;
            }
        }
        sc.println(dp[0][n - 1]);
    }




}