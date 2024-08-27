package com.codeforces.round968_div2;

/**
 * @author Minus
 * @date 2024/8/26 14:03
 *
 *
 */
import javax.print.DocFlavor;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

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
        int T = sc.nextInt();
        while (T-- > 0) {
            // solve();
            // sc.bw.flush();
        }
        sc.bw.flush();
        sc.bw.close();
    }

    private static String[] ss;
    private static String s;


    // https://codeforces.com/contest/2003/problem/C
    // 题目意思太难懂了 贪心+构造 就是尽可能使相邻字符不一样
    private static void solveC() throws IOException {
        int n = sc.nextInt();
        s = sc.nextLine();
        StringBuilder ans = new StringBuilder();
        HashMap<Character, Integer> cnt = new HashMap<>();
        for (char c : s.toCharArray()) {
            cnt.merge(c, 1, Integer::sum);
        }
        PriorityQueue<Pair<Character, Integer>> pq = new PriorityQueue<>((a, b) -> b.sec - a.sec);
        for (Character c : cnt.keySet()) {
            pq.offer(new Pair(c, cnt.get(c)));
        }
        if (pq.size() == 1) {
            sc.println(s);
            return;
        }
        // 保证 pq.size()>=2
        Pair<Character,Integer> p1 = pq.poll();
        Pair<Character,Integer> p2 = pq.poll();
        for (int i = 0; i < p1.sec - p2.sec; i++) {
            ans.append(p1.fir);
        }
        p1.sec = p2.sec;
        pq.offer(p1);
        pq.offer(p2);
        while (!pq.isEmpty()) {
            Pair<Character,Integer> poll = pq.poll();
            ans.append(poll.fir);
            poll.sec--;
            if (poll.sec == 0) {
                continue;
            }
            pq.offer(poll);
        }
        sc.println(ans.toString());
    }

    // https://codeforces.com/contest/2003/problem/D1  当然求MEX可以用O(n)的方法，就是开一个visited数组
    private static void solveD1() throws IOException {
        int n = sc.nextInt(), m = sc.nextInt();
        long mx = 0;
        for (int i = 0; i < n; i++) {
            ss = sc.nextLine().split(" ");
            int len = Integer.parseInt(ss[0]);
            int[] nums = new int[len];
            for (int j = 0; j < len; j++) {
                nums[j] = Integer.parseInt(ss[j + 1]);
            }
            Arrays.sort(nums);
            int fi = -1;
            int j = 0, pre = -1;
            for (; j < len; j++) {
                if (nums[j] == pre + 1 || nums[j]==pre) {
                    pre = nums[j];
                    continue;
                }
                if (fi == -1) {
                    fi = 1;
                    pre++;
                    j--;
                    continue;
                }
                mx = Math.max(mx, pre + 1);
                break;
            }
            if (j == len) {
                if (fi == -1) {
                    mx = Math.max(mx, nums[len - 1] + 2);
                }
                mx = Math.max(mx, nums[len - 1] + 1);
            }
        }
        if (mx >= m) {
            sc.println(mx * (m + 1));
        }else{
            sc.println(mx * mx + (m + mx) * (m - mx + 1) / 2);
        }
    }

    private static final int MAXN = 200100;
    private static long[] a = new long[MAXN];
    private static boolean[] vis = new boolean[MAXN];
    private static void solveD1_pro(Scanner sc) {
        long n = sc.nextLong();
        long m = sc.nextLong();
        long k = 0;
        while (n-- > 0) {
            int t = sc.nextInt();
            for (int i = 0; i <= t + 5; ++i) {
                vis[i] = false;
            }
            for (int i = 1; i <= t; ++i) {
                a[i] = sc.nextLong();
                if (a[i] <= t + 4) {
                    vis[(int) a[i]] = true;
                }
            }
            long mex = 0;
            while (vis[(int) mex]) {
                mex++;
            }
            vis[(int) mex] = true;
            while (vis[(int) mex]) {
                mex++;
            }
            k = Math.max(k, mex);
        }
        System.out.println(k >= m ? (m + 1) * k : k * k + (m + k) * (m - k + 1) / 2);
    }

    private static int maxN = 200100;
    static long[] visited = new long[maxN];
    static List<Integer>[] g = new List[maxN];

    // https://codeforces.com/contest/2003/problem/D2
    private static void solveD2() throws IOException {
        int n = sc.nextInt();
        int m = sc.nextInt();
        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
        int k = 0, t = -1;
        for (int i = 0; i < n; i++) {
            ss = sc.nextLine().split(" ");
            int len = Integer.parseInt(ss[0]);
            for (int j = 0; j < len; j++) {
                int x = Integer.parseInt(ss[j + 1]);
                a[j] = x;
                if (x < maxN) {
                    visited[x] = 1;
                }
            }
            int u = 0;
            while (visited[u] > 0) {
                u++;
            }
            t = Math.max(t, u);
            int v = u + 1;
            while (visited[v] > 0) {
                v++;
            }
            pairs.add(new Pair<>(u, v));
            k = Math.max(k, v);
            for (int j = 0; j < len; j++) {
                if (a[j] < maxN) {
                    visited[(int) a[j]] = 0;
                }
            }
        }
        for (int i = 0; i <= k; i++) {
            g[i] = new ArrayList<>();
        }
        for (Pair<Integer, Integer> p : pairs) {
            g[p.fir].add(p.sec);
        }
        int[] dp = new int[k + 1];
        for (int u = k; u >= 0; u--) {
            dp[u] = u;
            for (int v : g[u]) {
                dp[u] = Math.max(dp[u], dp[v]);
            }
            if (g[u].size() >= 2) {
                t = Math.max(t, dp[u]);
            }
        }
        long ans = 0;
        for (int i = 0; i <= Math.min(k, m); i++) {
            ans += Math.max(t, dp[i]);
        }
        if (k < m) {
            ans += cal(k + 1, m);
        }
        sc.println(ans);
    }
    private static long cal(long l,long r){
        return (l + r) * (r - l + 1) / 2;
    }
    static class Pair<T, U> {
        T fir;
        U sec;
        public Pair(T fir, U sec) {
            this.fir = fir;
            this.sec = sec;
        }
    }

}
