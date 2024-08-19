package com.codeforces.round169_div2;

/**
 * @author Minus
 * @date 2024/8/18 16:48
 */
import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    private final static int INF = Integer.MAX_VALUE / 4;
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
    private static final int Mod = (int) 1e9 + 7;
    private static int T = 1;
    public static void main(String[] args) throws IOException {
        int T = sc.nextInt();
        while (T-- > 0) {
            // solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }

    private static String[] ss;
    private static String[] words = {"BG", "BR", "BY", "GR", "GY", "RY"};
    private static HashMap<String, String> map = new HashMap<>();
    static {
        for (int i = 0, j = 5; i < 6; i++, j--) {
            map.put(words[i], words[j]);
        }
    }
    private static void solveD() throws IOException {
        int n = sc.nextInt(), q = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, -INF);
        Arrays.fill(right, INF);
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j-- > 0) {
                if (ss[j].equals(ss[i])) {
                    left[i] = left[j];
                    break;
                } else if (!ss[i].equals(map.get(ss[j]))) {
                    left[i] = j;
                    break;
                }
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            int j = i;
            while (j++ < n - 1) {
                if (ss[j].equals(ss[i])) {
                    right[i] = right[j];
                    break;
                } else if (!ss[i].equals(map.get(ss[j]))) {
                    right[i] = j;
                    break;
                }
            }
        }
        while (q-- > 0) {
            int a = sc.nextInt() - 1, b = sc.nextInt() - 1;
            if (a == b) {
                sc.println(0);
                continue;
            }
            if (!ss[a].equals(map.get(ss[b]))) {
                sc.println(Math.abs(b - a));
                continue;
            }
            // 从a到b
            int ans = Math.min(Math.abs(left[a] - b) + a - left[a], Math.abs(right[a] - a + Math.abs(right[a] - b)));
            sc.println(ans >= INF ? -1 : ans);
        }
    }

}
