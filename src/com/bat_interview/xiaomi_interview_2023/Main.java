package com.bat_interview.xiaomi_interview_2023;

/**
 * @author Minus
 * @date 2024/8/1 22:32
 *
 * 第一题学到贪心的思想：按照差值来贪心取
 * 第二题学到了如何读取字符串，sc.nextLine(),不是sc.next()。然后如何四舍五入保留一位小数：
 * DecimalFormat df = new DecimalFormat("#.0");
 * String formattedValue = df.format(ans);
 * System.out.println(formattedValue);
 */

import java.io.*;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

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
    public static void main(String[] args) throws IOException {
        sc.bw.flush();
        sc.bw.close();
    }


    // https://kamacoder.com/problempage.php?pid=1229 贪心好难想啊
    private static void solve1() throws IOException {
        String[] split = sc.nextLine().split(",");
        int n = split.length;
        int[][] nums = new int[n][2];
        for (int i = 0; i < n; i++) {
            String[] s = split[i].split(":");
            nums[i][0] = Integer.parseInt(s[0]);
            nums[i][1] = Integer.parseInt(s[1]);
        }
        Arrays.sort(nums, (a, b) -> (a[1] - a[0] - (b[1] - b[0]))); // 核心
        int left = 0, right = 4800; // 1:10,2:12,3:10
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(nums,mid)) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        System.out.println(right + 1 > 4800 ? -1 : right + 1);
    }
    private static boolean check(int[][] nums, int sum) {
        for (int i = nums.length - 1; i >= 0; i--) {
            int a = nums[i][0], b = nums[i][1];
            if (sum >= b && sum - a >= 0) {
                sum -= a;
            } else {
                return false;
            }
        }
        return true;
    }

    // https://kamacoder.com/problempage.php?pid=1230 秒了
    private static void solve() throws IOException {
        int freq = sc.nextInt();
        String[] words = sc.nextLine().split(" ");
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] nums = new int[words.length];
        int i = 0;
        for (String w : words) {
            String[] s = w.split(":");
            map.put(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
            nums[i++] = Integer.parseInt(s[0]);
        }
        int i1 = Math.min(nums.length - 1, binarySearch1(nums, freq));
        int i2 = Math.max(0, binarySearch2(nums, freq));
        double ans;
        if (Math.abs(nums[i1] - freq) == Math.abs(nums[i2] - freq)) {
            ans = ((double) map.get(nums[i1]) + map.get(nums[i2])) / 2;
            DecimalFormat df = new DecimalFormat("#.0");
            String formattedValue = df.format(ans);
            System.out.println(formattedValue);
            return;
        }
        if (Math.abs(nums[i1] - freq) < Math.abs(nums[i2] - freq)) {
            ans = ((double) map.get(nums[i1]));
            DecimalFormat df = new DecimalFormat("#.0");
            String formattedValue = df.format(ans);
            System.out.println(formattedValue);
        }else{
            ans = ((double) map.get(nums[i2]));
            DecimalFormat df = new DecimalFormat("#.0");
            String formattedValue = df.format(ans);
            System.out.println(formattedValue);
        }
    }

}
