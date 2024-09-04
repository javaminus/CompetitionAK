package com.codeforces.round971_div4;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * 主要是F题，人麻了
 * 别人四分钟写完，我调四个小时
 *
 *
 */
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

    static Read sc = new Read();
    private static final int Mod = (int) 1e9 + 7;
    private static int T = 1;

    public static void main(String[] args) throws IOException {
        // int T = sc.nextInt();
        while (T-- > 0) {
            // solveF();
            // sc.bw.flush();
        }
        sc.bw.flush();
        sc.bw.close();
    }

    private static String[] ss;
    private static String s;



    private static void solveF1() throws IOException { // 这是我调试四个多小时的程序，真的累。以后这种循环数组，直接建n * 2长度的数组
        int n = sc.nextInt(), q = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] nums = new int[n];
        long[] prefixSum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        while (q-- > 0) {
            long ans = 0L;
            long l = sc.nextLong() - 1, r = sc.nextLong() - 1;
            int divl = (int) ((l / n) % n); // 定义divl为最左端点
            if (l % n != 0 &&l / n != r / n) { // l与r不在一块儿
                if (l % n + 1 > n - divl) { // l在划分点右边
                    ans += prefixSum[divl] - prefixSum[(int) ((l + divl) % n)];
                } else {
                    ans += prefixSum[divl] + prefixSum[n] - prefixSum[(int) ((l + divl) % n)];
                }
                l = (l + n - 1) / n * n;
            }
            long len = r - l + 1;
            long p = len / n;
            ans += prefixSum[n] * p;
            int divr = (int) ((r / n) % n);
            if ((r + 1) % n != 0 || (l % n) != 0) {
                if (r % n + 1 > n - divr) {
                    // 都在右边
                    if (l % n + 1 > n - divr) {
                        ans += prefixSum[(int) ((r + divr) % n + 1)] - prefixSum[(int) ((l + divr) % n)];
                    } else {
                        ans += prefixSum[(int) ((r +divr) % n + 1)];
                        ans += prefixSum[n] - prefixSum[(int) ((l + divr) % n)];
                    }
                } else { // 都在左边
                    ans += prefixSum[(int) ((r + divr) % n + 1)] - prefixSum[(int) ((l + divr) % n)];
                }
            }
            sc.println(ans);
        }
    }

    static long[] prefixSum;

    static int n, q;

    private static void solveF2() throws IOException {
        n = sc.nextInt();
        q = sc.nextInt();
        int[] nums = new int[n];
        ss = sc.nextLine().split(" ");
        prefixSum = new long[2 * n + 1];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        for (int i = 0; i < n * 2; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i % n];
        }
        while (q-- > 0) {
            long l = sc.nextLong(), r = sc.nextLong();
            // 前r个元素和 - 前l个元素和
            sc.println(cal(r) - cal(l - 1));
        }
    }
    private static long cal(long p) {
        long ans = p / n * prefixSum[n];
        long start = p / n % n;
        ans += prefixSum[(int) (start + p % n)] - prefixSum[(int) start];
        return ans;
    }


}
