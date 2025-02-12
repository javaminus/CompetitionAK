import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private final static int INF = Integer.MAX_VALUE / 2;
    private final static int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    // private final static int[][] dirs = new int[][]{{1, 0}, {0, -1}, {-1, 0}, {0, 1}};

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
    private static int m, n, sum;

    private static List<Integer> list = new ArrayList<>();
    static char[][] grid;

    private static void solve() throws IOException {
        n = sc.nextInt();
        grid = new char[n][n];
        for (int i = 0; i < n; i++) {
            grid[i] = sc.next().toCharArray();
        }
        HashMap<String, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '*') {
                    sum = 1;
                    list.clear();
                    int b = dfs(i, j);
                    for (int x = 0; x < list.size(); x += 2) {
                        grid[list.get(x)][list.get(x + 1)] = '.';
                    }
                    if (b == 1) {
                        String key = "" + list.get(0) + list.get(1);
                        map.put(key, map.getOrDefault(key, 0) + sum);
                        ans = Math.max(ans, map.get(key));
                    }
                }
            }
        }
        sc.println(ans);
    }

    private static int dfs(int i, int j) {
        int ans = 0;
        grid[i][j] = '1';
        for (int[] d : dirs) {
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
                if (grid[ni][nj] == '*') {
                    sum++;
                    ans += dfs(ni, nj);
                } else if (grid[ni][nj] == '.') {
                    grid[ni][nj] = '2';
                    list.add(ni);
                    list.add(nj);
                    ans++;
                }
            }
        }
        return ans;
    }
    
}