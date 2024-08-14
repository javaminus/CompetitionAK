package com.nowcoder.round_55;

/**
 * @author Minus
 * @date 2024/8/13 14:59
 */
import java.io.*;
import java.math.BigInteger;
import java.util.*;

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
    private static final int Mod = (int) 1e9 + 7;
    public static void main(String[] args) throws IOException {  // 网格图用dfs你也是疯了，直接建队列，bfs呀！！！
        int T = 1;
        while (T-- > 0) {
            // solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }
    static String[] ss;

    // https://ac.nowcoder.com/acm/contest/87656/D
    private static void solveD() throws IOException {
        int n = sc.nextInt();
        int[][] grid = new int[n][n];
        for (int i = 0; i < n; i++) {
            ss = sc.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                grid[i][j] = Integer.parseInt(ss[j]);
            }
        }
        ArrayList<int[]>[][] edges = new ArrayList[n][n]; // 表示两点之间的连通 这里是虫洞的传送点位
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                edges[i][j] = new ArrayList<>();
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    continue;
                }
                if (j == 0 || grid[i][j - 1] == 1) {
                    int t = j + 1;
                    while (t < n) {
                        if (grid[i][t] == 1) {
                            break;
                        }
                        t++;
                    }
                    if (t - 1 != j) {
                        edges[i][j].add(new int[]{i, t - 1});
                        edges[i][t - 1].add(new int[]{i, j});
                    }
                    j = t; // j移动到墙的位置
                }
            }
        }
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < n; i++) {
                if (grid[i][j] == 1) {
                    continue;
                }
                if (i == 0 || grid[i - 1][j] == 1) {
                    int t = i + 1;
                    while (t < n) {
                        if (grid[t][j] == 1) {
                            break;
                        }
                        t++;
                    }
                    if (t - 1 != i) {
                        edges[i][j].add(new int[]{t - 1, j});
                        edges[t - 1][j].add(new int[]{i, j});
                    }
                    i = t;
                }
            }
        }
        // PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[2] - b[2]);
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[2], b[2]);
            }
        });
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
        }
        dist[0][0] = 0;
        pq.add(new int[]{0, 0, 0});
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int x = poll[0], y = poll[1], z = poll[2];
            if (x == n - 1 && y == n - 1) {
                sc.print(z);
                return;
            }
            if (dist[x][y] < z) {
                continue;
            }
            for (int[] d : dirs) {
                int newX = x + d[0], newY = y + d[1];
                if (newX >= 0 && newY >= 0 && newX < n && newY < n && grid[newX][newY] == 0) {
                    if (dist[newX][newY] > z + 1) {
                        dist[newX][newY] = z + 1;
                        pq.offer(new int[]{newX, newY, z + 1});
                    }
                }
            }
            for (int[] d : edges[x][y]) {
                int newX = d[0], newY = d[1];
                if (newX >= 0 && newY >= 0 && newX < n && newY < n) {
                    if (dist[newX][newY] > z + 1) {
                        dist[newX][newY] = z + 1;
                        pq.offer(new int[]{newX, newY, z + 1});
                    }
                }
            }
        }
        sc.print(-1);
    }

    // https://ac.nowcoder.com/acm/contest/87656/E
    private static void solveE() throws IOException {
        int n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        int[][] dp = new int[n + 1][10]; // 表示以第i项结尾的末尾数字是j的数量
        long ans = 0;
        dp[0][1] = 1;
        for (int i = 1; i <= n; i++) {
            int x = nums[i - 1];
            for (int j = 0; j < 10; j++) {
                dp[i][j] = (dp[i][j] + dp[i - 1][j]) % Mod;
                dp[i][j * x % 10] = (dp[i][j * x % 10] + dp[i - 1][j]) % Mod; //以a[i]结尾的序列
                if (j * x % 10 == 6) {
                    //以a[i]结尾的序列乘积贡献答案
                    ans = (ans + (long) dp[i - 1][j] * power(2, n - i, Mod) % Mod) % Mod;
                }
            }
        }
        sc.print(ans);
    }
    private static long power(long a,long b,int p){ // 求 (a ^ b) % p
        long ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans * a) % p;
            }
            a = (a * a) % p;
            b >>= 1;
        }
        return ans;
    }



}
