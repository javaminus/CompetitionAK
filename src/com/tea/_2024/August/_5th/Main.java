package com.tea._2024.August._5th;

/**
 * @author Minus
 * @date 2024/8/5 12:26
 *
 * https://atcoder.jp/contests/abc249/tasks/abc249_d   983 (枚举 + 调和级数)
 */

import java.io.*;
import java.math.BigInteger;
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
    private static void solve() throws IOException {
        int N = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] cnt = new int[(int) 2e5 + 1];
        for (int i = 0; i < N; i++) {
            cnt[Integer.parseInt(ss[i])]++;
        }
        long ans = 0;
        for (int i = 1; i < (int) 2e5 + 1; i++) {
            for (int j = 1; i * j < (int) 2e5 + 1; j++) {
                ans += (long) cnt[i] * cnt[j] * cnt[i * j];
            }
        }
        sc.print(ans);
    }
}


