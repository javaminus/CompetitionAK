package com.tea._2024.August._7th;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private final static int INF = Integer.MAX_VALUE / 2;
    private final static int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    static class Read{
        BufferedReader bf;
        StringTokenizer st;
        BufferedWriter bw;
        public Read(){
            bf=new BufferedReader(new InputStreamReader(System.in));
            st=new StringTokenizer("");
            bw=new BufferedWriter(new OutputStreamWriter(System.out));
        }
        public String nextLine() throws IOException {
            return bf.readLine();
        }
        public String next() throws IOException{
            while(!st.hasMoreTokens()){
                st=new StringTokenizer(bf.readLine());
            }
            return st.nextToken();
        }
        public char nextChar() throws IOException{
            //确定下一个token只有一个字符的时候再用
            return next().charAt(0);
        }
        public int nextInt() throws IOException{
            return Integer.parseInt(next());
        }
        public long nextLong() throws IOException{
            return Long.parseLong(next());
        }
        public double nextDouble() throws IOException{
            return Double.parseDouble(next());
        }
        public float nextFloat() throws IOException{
            return Float.parseFloat(next());
        }
        public byte nextByte() throws IOException{
            return Byte.parseByte(next());
        }
        public short nextShort() throws IOException{
            return Short.parseShort(next());
        }
        public BigInteger nextBigInteger() throws IOException{
            return new BigInteger(next());
        }
        public void println(int a) throws IOException{
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
        public void print(int a) throws IOException{
            bw.write(String.valueOf(a));
            return;
        }
        public void println(String a) throws IOException{
            bw.write(a);
            bw.newLine();
            return;
        }
        public void print(String a) throws IOException{
            bw.write(a);
            return;
        }
        public void println(long a) throws IOException{
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
        public void print(long a) throws IOException{
            bw.write(String.valueOf(a));
            return;
        }
        public void println(double a) throws IOException{
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
        public void print(double a) throws IOException{
            bw.write(String.valueOf(a));
            return;
        }
        public void print(BigInteger a) throws IOException{
            bw.write(a.toString());
            return;
        }
        public void print(char a) throws IOException{
            bw.write(String.valueOf(a));
            return;
        }
        public void println(char a) throws IOException{
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
    }
    private static int binarySearch1(int[] nums, int target) { // >=target
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] >= target) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return right + 1;
    }
    private static int binarySearch2(int[] nums, int target) { // <=target
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] > target) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return left - 1;
    }

    static Read sc = new Read();
    static int T = 1;
    public static void main(String[] args) throws IOException {
        T = sc.nextInt();
        while (T-- > 0) {
            solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }
    static String[] ss;
    static int n, k, c; // 树中顶点的数量、每条边的长度以及操作的成本。
    static long ans;
    static List<Integer>[] g;
    static long[][] nodes;
    static long[] tmp;
    private static final long Mod = 998244353;
    private static void solve() throws IOException {
        n = sc.nextInt();
        k = sc.nextInt();
        c = sc.nextInt();
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int i = 0; i < n - 1; i++) {
            int x = sc.nextInt() - 1, y = sc.nextInt() - 1;
            g[x].add(y);
            g[y].add(x);
        }
        nodes = new long[n][3];
        tmp = new long[n];
        dfs(0, -1);
        reRoot(0, -1, 0, 0);
        sc.println(ans);
    }

    private static long dfs(int x, int fa) {
        long maxD = 0, maxD2 = 0, my = 0;
        for (int y : g[x]) {
            if (y != fa) {
                long d = dfs(y, x) + k;
                if (d > maxD) {
                    maxD2 = maxD;
                    maxD = d;
                    my = y;
                } else if (d > maxD2) {
                    maxD2 = d;
                }
            }
        }
        nodes[x][0] = maxD;
        nodes[x][1] = maxD2;
        nodes[x][2] = my;
        return maxD;
    }

    private static void reRoot(int x, int fa, long up, long cost) {
        ans = Math.max(ans, Math.max(nodes[x][0], up) - cost);
        for (int y : g[x]) {
            if (y != fa) {
                reRoot(y, x, Math.max(up, y == nodes[x][2] ? nodes[x][1] : nodes[x][0]) + k, cost + c);
            }
        }
    }
}

