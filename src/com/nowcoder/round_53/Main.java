package com.nowcoder.round_53;

/**
 * @author Minus
 * @date 2024/7/28 21:37
 *
 * 牛客round53补题，4/7 rank 428
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

    static Read sc = new Read();
    public static void main(String[] args) throws IOException {
        // solve();
        sc.bw.flush();
        sc.bw.close();
    }

    // https://ac.nowcoder.com/acm/contest/86387/D
    private static void solveD() throws IOException {
        int n = sc.nextInt();
        int m = sc.nextInt();
        HashMap<Integer, Integer> map = new HashMap<>(); // 合成的数字，累加的层数
        HashMap<Integer, Integer> map1 = new HashMap<>(); // 合成的数字，累加的层数
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            if (!map.containsKey(x)) {
                map.put(x, 1);
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int x = sc.nextInt();
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    int k = entry.getKey(), v = entry.getValue();
                    if (v == i) {
                        map1.put(k + x, i + 1);
                    }
                }
            }
            map.clear();
            for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
                int k = entry.getKey(), v = entry.getValue();
                map.put(k, v);
            }
            map1.clear();

        }
        int target = sc.nextInt();
        int ans = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int k = entry.getKey(), v = entry.getValue();
            if (v == n) {
                ans = Math.min(ans, Math.abs(k - target));
                if (ans == 0) {
                    sc.print(0);
                    return;
                }
            }
        }
        sc.print(ans);
    }

    // https://ac.nowcoder.com/acm/contest/86387/E
    private static void solveE() throws IOException {
        int T = sc.nextInt();
        while (T-- > 0) {
            int n = sc.nextInt();
            TreeSet<Integer> set = new TreeSet<>();
            while (n-- > 0) {
                int x = sc.nextInt();
                if (set.contains(x)) {
                    x /= 2;
                    while (x != 0 && set.contains(x)) {
                        x /= 2;
                    }
                    set.add(x);
                }else{
                    set.add(x);
                }
            }

            while (set.last() != set.size() - 1) {
                Integer x = set.last();
                set.remove(x);
                x /= 2;
                if (set.contains(x)) {
                    x /= 2;
                    while (x != 0 && set.contains(x)) {
                        x /= 2;
                    }
                    set.add(x);
                }else{
                    set.add(x);
                }
            }
            sc.println(set.last() + 1);
        }
    }

    // https://ac.nowcoder.com/acm/contest/86387/F
    private static void solveF() throws IOException {
        int n = sc.nextInt();
        int m = sc.nextInt();
        char[][] grid = new char[n][m];
        for (int i = 0; i < n; i++) {
            grid[i] = sc.next().toCharArray();
        }

        int ans = dijkstra(grid, -1, 0);
        for (int i = 0; i < 4; i++) {
            ans = Math.min(ans, dijkstra(grid, i, 1));
        }
        sc.println(ans < INF ? ans : -1);
    }
    private static int dijkstra(char[][] grid, int tag, int k) { // k的取值 0/1 表示是否已经使用过超能力
        int n = grid.length, m = grid[0].length;
        boolean[][][] visited = new boolean[n][m][2]; // 0: 不能使用, 1: 能使用
        // 因为这里的边权为1，所以直接bfs，可以不用优先队列
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, k});
        visited[0][0][k] = true;
        int dist = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int c = 0; c < size; c++) {
                int[] e = queue.poll();
                int x = e[0], y = e[1], z = e[2];
                if (x == n - 1 && y == m - 1) {
                    return dist;
                }
                for (int i = 0; i < 4; i++) {
                    if (i == tag) {
                        continue;
                    }
                    int newX = x + dirs[i][0], newY = y + dirs[i][1];
                    if (newX >= 0 && newX < n && newY >= 0 && newY < m && !visited[newX][newY][z]) {
                        if (grid[newX][newY] == '.') {
                            visited[newX][newY][z] = true;
                            queue.offer(new int[]{newX, newY, z});
                        } else if (z > 0 && !visited[newX][newY][0]) {
                            visited[newX][newY][0] = true;
                            queue.offer(new int[]{newX, newY, 0});
                        }
                    }
                }
            }
            dist++;
        }
        return INF;
    }
}



