package com.tea._2024.August._6th;

/**
 * @author Minus
 * @date 2024/8/6 12:28
 * https://atcoder.jp/contests/abc359/tasks/abc359_d  1381  (dp + 位运算 + 状压)
 */
import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
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
        // T = sc.nextInt();
        while (T-- > 0) {
            solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }
    static String[] ss;
    static char[] s;
    static int n;
    static int k;
    private static final int Mod = 998244353;
    private static void solve() throws IOException {
        n = sc.nextInt();
        k = sc.nextInt();
        s = sc.nextLine().toCharArray();
        memo = new int[n][1 << k];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        boolean[] isPal = new boolean[1 << k]; // 预处理长度为K的二进制回文字符串
        for (int i = 0; i < (1 << k); i++) {
            boolean flag = true;
            for (int j = 0; j < k / 2; j++) {
                if (((i >> j) & 1) != ((i >> (k - 1 - j)) & 1)) {
                    flag = false;
                    break;
                }
            }
            isPal[i] = flag;
        }
        int mask = (1 << (k - 1)) - 1;
        int res = dfs(s, n - 1, 0, isPal, mask);
        System.out.println(res);
    }

    private static int[][] memo;

    // 表示i右边的k - 1个字母的二进制为j
    private static int dfs(char[] s, int i, int j, boolean[] isPal, int mask) {
        if (i < 0) {
            return 1; // 判断[0, k]这一段是否合法
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        int res = 0;
        for (int b = 0; b <= 1; b++) {
            if (s[i] != '?' && ((s[i] - 'A') & 1) != b) {
                continue;
            }
            int next = (j << 1) | b;
            if (i > s.length - k || !isPal[next]) {
                res += dfs(s, i - 1, next & mask, isPal, mask);
                res %= Mod;
            }
        }
        return memo[i][j] = res;
    }

}


