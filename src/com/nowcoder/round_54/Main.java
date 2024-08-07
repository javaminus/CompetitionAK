package com.nowcoder.round_54;

import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 *
 * @author Minus
 * @date 2024/8/4 21:56
 * 牛客round54补题，3/6 rank 930
 */
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
        while (T-- > 0) {
            // solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }
    static String[] ss;

    // https://ac.nowcoder.com/acm/contest/87303/D 过题目的所有人都是队列，唉，我怎么想不到啊
    private static void solveD() throws IOException { // 队列优化dp
        int n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        long[] nums = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            nums[i] = Long.parseLong(ss[i - 1]);
        }
        long[] dp = new long[n + 1];
        Arrays.fill(dp, INF);
        dp[1] = 0;
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        visited[1] = true;
        while (!queue.isEmpty()) {
            int i = queue.poll();
            for (int j = 1; j <= Math.min(nums[i], n - 1); j++) {
                if (nums[i] % j == 0) {
                    if (i - j >= 1 && !visited[i - j]) { // 往左走
                        queue.offer(i - j);
                        visited[i - j] = true;
                        dp[i - j] = Math.min(dp[i - j], dp[i] + 1);
                    }
                    if (i + j <= n && !visited[i + j]) {
                        queue.add(i + j);
                        visited[i + j] = true;
                        dp[i + j] = Math.min(dp[i + j], dp[i] + 1);
                    }
                }
            }
        }
        sc.print(dp[n]);
    }

    // https://ac.nowcoder.com/acm/contest/87303/E 背包dp，下标都是从1开始
    private static void solveE() throws IOException { // 背包问题
        int n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] nums = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            nums[i] = Integer.parseInt(ss[i - 1]);
        }
        int[] dp = new int[n + 1];
        Arrays.fill(dp, INF);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            // 当前长度
            int len = nums[i];
            // 第i张布必须覆盖第i个坐标
            // j表示起始位置
            for (int j = Math.max(1 , i - len + 1); j <= Math.min(i, n - len + 1); j++) {
                dp[j + len - 1] = Math.min(dp[j + len - 1], dp[j - 1] + 1);
            }
        }
        System.out.println(dp[n] == INF ? -1 : dp[n]);
    }

}

