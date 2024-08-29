package com.bat_interview.alibaba_cloud;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

// 阿里云笔试
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
    static Read sc = new Read();
    private static final int Mod = (int) 1e9 + 7;
    private static int T = 1;

    public static void main(String[] args) throws IOException {
        // int T = sc.nextInt();
        while (T-- > 0) {
            solve();
            // sc.bw.flush();
        }
        sc.bw.flush();
        sc.bw.close();
    }

    private static String[] ss;
    private static String s;
    // https://kamacoder.com/problempage.php?pid=1243
    private static void solve() throws IOException {
        int a = sc.nextInt(), b = sc.nextInt(), k = sc.nextInt();
        LinkedList<Character> list = new LinkedList<>();
        int cnt = 0;
        while (b-- > 0) {
            list.add('b');
            cnt++;
            if (b > 0 && cnt == k) {
                list.add('a');
                a--;
                cnt = 0;
            }
        }
        int aa1 = a;
        for (int i = 0; i < Math.min(aa1, k); i++) {
            list.add('a');
            a--;
        }
        if (a < 0) {
            sc.print(-1);
            return;
        }
        int id = list.size() - 1;
        while (id >= 0 && a > 0) {
            if (list.get(id) == 'b') {
                if (id - 1 >= 0 && list.get(id - 1) == 'b') {
                    int id1 = id;
                    int a1 = a;
                    for (int i = 0; i < Math.min(k, a1); i++) {
                        list.add(id1++, 'a');
                        a--;
                    }
                } else if (id - 1 >= 0 &&list.get(id - 1) != 'b') {
                    int id1 = id;
                    int a1 = a;
                    for (int i = 0; i < Math.min(k - 1, a1); i++) {
                        list.add(id1++, 'a');
                        a--;
                    }
                }
            }
            id--;
        }
        if (a <= k) {
            for (int i = 0; i < a; i++) {
                list.add(i, 'a');
            }
        } else{
            sc.print(-1);
            return;
        }
        for (Character c : list) {
            sc.print(c);
        }
    }


}