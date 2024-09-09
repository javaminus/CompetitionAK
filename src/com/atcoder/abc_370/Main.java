package com.atcoder.abc_370;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main {
    // https://atcoder.jp/contests/abc370/tasks/abc370_c 简单问题，简单处理，别想太复杂了
    private static void solveC() throws IOException { // O(n^3)的时间复杂度
        char[] s = sc.next().toCharArray(), t = sc.next().toCharArray();
        ArrayList<String> ans = new ArrayList<>();
        int n = s.length;
        char[] nxt = new char[n];
        while (!Arrays.equals(s, t)) {
            Arrays.fill(nxt, 'z');
            for (int i = 0; i < n; i++) {
                if (s[i] != t[i]) {
                    char[] tmp = s.clone();
                    tmp[i] = t[i];
                    if (compare(tmp, nxt)) {
                        nxt = tmp;
                    }
                }
            }
            ans.add(new String(nxt));
            s = nxt.clone();
        }
        sc.println(ans.size());
        for (int i = 0; i < ans.size(); i++) {
            sc.println(ans.get(i));
        }
    }

    private static boolean compare(char[] s, char[] t) {
        for (int i = 0; i < s.length; i++) {
            if (s[i] < t[i]) {
                return true;
            } else if (s[i] > t[i]) {
                return false;
            }
        }
        return false;
    }

    private static void solveC2() throws IOException {
        char[] s = sc.next().toCharArray(), t = sc.next().toCharArray();
        ArrayList<String> ans = new ArrayList<>();
        int n = s.length;
        ArrayList<Integer> diff = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (s[i] > t[i]) {
                diff.add(i);
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (s[i] < t[i]) {
                diff.add(i);
            }
        }
        for (int id : diff) {
            s[id] = t[id];
            ans.add(new String(s));
        }
        sc.println(ans.size());
        for (int i = 0; i < ans.size(); i++) {
            sc.println(ans.get(i));
        }
    }

    // https://atcoder.jp/contests/abc370/tasks/abc370_d
    private static void solve() throws IOException {
        int m = sc.nextInt(), n = sc.nextInt(), q = sc.nextInt();
        TreeSet<Integer>[] g1 = new TreeSet[m];
        TreeSet<Integer>[] g2 = new TreeSet[n];
        Arrays.setAll(g1, e -> new TreeSet<>());
        Arrays.setAll(g2, e -> new TreeSet<>());
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                g1[i].add(j);
                g2[j].add(i);
            }
        }
        while (q-- > 0) {
            int i = sc.nextInt() - 1, j = sc.nextInt() - 1;
            int left = -1, right = -1, top = -1, bottom = -1;
            if (g1[i].floor(j) != null) {
                left = g1[i].floor(j);
            }
            if (g1[i].ceiling(j) != null) {
                right = g1[i].ceiling(j);
            }
            if (g2[j].ceiling(i) != null) {
                bottom = g2[j].ceiling(i);
            }
            if (g2[j].floor(i) != null) {
                top = g2[j].floor(i);
            }
            if (left != -1) {
                g1[i].remove(left);
                g2[left].remove(i);
            }
            if (right != -1) {
                g1[i].remove(right);
                g2[right].remove(i);
            }
            if (bottom != -1) {
                g2[j].remove(bottom);
                g1[bottom].remove(j);
            }
            if (top != -1) {
                g2[j].remove(top);
                g1[top].remove(j);
            }
        }
        int ans = 0;
        for (int i = 0; i < m; i++) {
            ans += g1[i].size();
        }
        sc.println(ans);
    }


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

    private static int binarySearch1(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right + 1;
    }

    private static int binarySearch2(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left - 1;
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
            // solve();
            // sc.bw.flush();
        }
        sc.bw.flush();
        sc.bw.close();
    }

    private static String[] ss;
    private static String s;
    private static char[] cs;
    private static List<Integer>[] g;
    private static int n;


}