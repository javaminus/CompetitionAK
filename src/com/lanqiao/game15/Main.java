package com.lanqiao.game15;

import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

/**
 * 蓝桥第 15 场小白入门赛 / 强者挑战赛非官方题解 —— 代码很少，但想得多！一些数学的小思考
 */
public class Main {
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

    static Read sc = new Read();
    public static void main(String[] args) throws IOException {
        sc.bw.flush();
        sc.bw.close();
    }

    // https://www.lanqiao.cn/problems/19744/learning/?contest_id=197
    private static void solve1() throws IOException {
        // 首先，不论如何操作，数组元素之和sum是不变的，那么，数组和为正数时，如果除了最大数之外的元素之和也是正数，
        // 最后就会剩下两个正数（想象成用所有负数去消耗剩下的正数，还消耗不完），无法操作；数组和为负数时同理。
        int n = sc.nextInt();
        int[] nums = new int[n];
        int mn = Integer.MAX_VALUE, mx = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            nums[i] = sc.nextInt();
            mn = Math.min(mn, nums[i]);
            mx = Math.max(mx, nums[i]);
            sum += nums[i];
        }
        if (sum > 0) {
            sc.println((sum - mx > 0 ? "N" : "Y"));
        } else if (sum < 0) {
            sc.println(sum - mn < 0 ? "N" : "Y");
        }else{
            sc.println("Y");
        }
    }
}
