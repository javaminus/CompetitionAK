package com.nowcoder.mouth100;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class Main { private final static int INF = Integer.MAX_VALUE / 2;private final static int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};static class Read {
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
    }private static int binarySearch1(int[] nums, int target) {
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
    }private static int binarySearch2(int[] nums, int target) {
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
    }static class Pair<T, U> {
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

    // https://ac.nowcoder.com/acm/contest/88878/D  太粗心了，-10写成10
    private static void solveD() throws IOException {
        int n = sc.nextInt(), m = sc.nextInt();
        int x = sc.nextInt(), y = sc.nextInt();
        char[][] cs = new char[n][m];
        for (int i = 0; i < n; i++) {
            cs[i] = sc.next().toCharArray();
        }
        boolean[][] visited = new boolean[n][m];
        boolean[][] visitedC = new boolean[n][m];
        visited[x - 1][y - 1] = true;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{x - 1, y - 1, 0, x - 1, y - 1});
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int i = poll[0], j = poll[1], ci = poll[3], cj = poll[4];
            for (int[] d : dirs) {
                int newI = i + d[0], newJ = j + d[1], newCi = ci, newCj = cj;
                if (ci != -10 && cj != -10) {
                    newCi = ci - d[0];
                    newCj = cj - d[1];
                }
                if (newI >= 0 && newJ >= 0 && newI < n && newJ < m && ((newCi >= 0 && newCj >= 0 && newCi < n && newCj < m) || (newCi == -10 && newCj == -10))) {
                    if (newCi != -10 && newCj != -10) {
                        if (cs[newI][newJ] == '#' || cs[newCi][newCj] == '#' || visited[newI][newJ]) {
                            continue;
                        }
                        if (cs[newI][newJ] == '@' && cs[newCi][newCj] == '@') {
                            sc.println(poll[2] + 1);
                            return;
                        } else if (cs[newI][newJ] == '@') {
                            pq.offer(new int[]{newCi, newCj, poll[2] + 1, -10, -10});
                            visited[newCi][newCj] = true;
                        } else if (cs[newCi][newCj] == '@') {
                            pq.offer(new int[]{newI, newJ, poll[2] + 1, -10, -10});
                            visited[newI][newJ] = true;
                        } else {
                            pq.offer(new int[]{newI, newJ, poll[2] + 1, newCi, newCj});
                            visited[newI][newJ] = true;
                        }
                    } else {
                        if (cs[newI][newJ] == '#' || visitedC[newI][newJ]) {
                            continue;
                        }
                        if (cs[newI][newJ] == '@') {
                            sc.println(poll[2] + 1);
                            return;
                        } else {
                            pq.offer(new int[]{newI, newJ, poll[2] + 1, -10, -10});
                            visitedC[newI][newJ] = true;
                        }
                    }
                }
            }
        }
        sc.println(-1);
    }

    // https://ac.nowcoder.com/acm/contest/88878/E 堆的应用，又给我上一课
    private static void solveE() throws IOException {
        int n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            set.add(Integer.parseInt(ss[i]));
        }
        int ans = set.size();
        int mx = set.last();
        for (int i = 1; i <= mx; i++) {
            int cnt = i;
            int right = set.first();
            while (right <= mx) {
                cnt++;
                right += i + 1;
                if (right > mx) {
                    break;
                }
                right = set.ceiling(right);
            }
            ans = Math.min(ans, cnt);
        }
        sc.println(ans);
    }

}