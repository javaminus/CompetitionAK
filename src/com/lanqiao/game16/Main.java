package com.lanqiao.game16;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Minus
 * @date 2024/8/11 0:20
 *
 * 蓝桥杯强者赛16
 */
public class Main {
    private final static int INF = Integer.MAX_VALUE;
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

    private static int binarySearch1(int[] nums, int target) {
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

    private static int binarySearch2(int[] nums, int target) {
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
    private static int Mod = (int) 1e9 + 7;
    public static void main(String[] args) throws IOException {
        int T = 1;
        while (T-- > 0) {
            // solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }


    // https://www.lanqiao.cn/problems/19767/learning/?contest_id=199
    private static void solveA() throws IOException {
        int n = sc.nextInt();
        String[] ss = sc.nextLine().split(" ");
        long[][] nums = new long[n][2];
        for (int i = 0; i < n; i++) {
            nums[i][0] = Long.parseLong(ss[i]);
        }
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            nums[i][1] = Long.parseLong(ss[i]);
        }
        Arrays.sort(nums, (a, b) -> Math.toIntExact(a[0] - b[0]));
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (i == 0 && (i + 1 < n && ((nums[i][0] + nums[i][1]) < nums[i + 1][0]))) {
                ans++;
            } else if (i == n - 1 && (i - 1 >= 0 && ((nums[i][0] - nums[i][1]) > nums[i - 1][0]))) {
                ans++;
            } else {
                if (i > 0 && i < n - 1 && (nums[i][0] + nums[i][1] < nums[i + 1][0]) && (nums[i][0] - nums[i][1]) > nums[i - 1][0]) {
                    ans++;
                }
            }
        }
        sc.print(ans);

    }

    // https://www.lanqiao.cn/problems/19766/learning/?contest_id=199
    static int[] ans, degree;
    private static void solveB() throws IOException {
        int n = sc.nextInt();
        int[][] nums = new int[n - 1][2];
        for (int i = 0; i < n - 1; i++) {
            nums[i][0] = sc.nextInt();
            nums[i][1] = sc.nextInt();
        }
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        degree = new int[n];
        for (int[] e : nums) {
            int x = e[0] - 1, y = e[1] - 1;
            g[x].add(y);
            g[y].add(x);
            degree[x]++;
            degree[y]++;
        }
        ans = new int[n];
        for (int i = 0; i < n; i++) {
            for (int x : g[i]) {
                ans[i] += degree[x] - 1;
            }
        }
        for (int i = 0; i < n; i++) {
            sc.print(ans[i] + " ");
        }

    }

    // https://www.lanqiao.cn/problems/19768/learning/?contest_id=199
    private static void solveD() throws IOException {
        int n = sc.nextInt();
        int k = sc.nextInt();
        long[] dp = new long[n + 1];
        long[] prefixSum = new long[n + 1];
        dp[0] = 1;
        prefixSum[0] = 1;
        for (int i = 1; i <= n; i++) {
            if (i >= k) {
                dp[i] = prefixSum[i - k];
            }
            prefixSum[i] = (prefixSum[i - 1] + dp[i]) % Mod;
        }
        long ans = 0;
        for (int i = 0; i <= n; i++) {
            ans = (ans + dp[i]) % Mod;
        }
        sc.print(ans);
    }

}
