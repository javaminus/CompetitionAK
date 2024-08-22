package com.codeforces.round967_div2;

import java.io.*;
import java.math.BigInteger;
import java.util.*;


/**
 * @author Minus
 * @date 2024/8/21 23:20
 *
 * C题是构造题
 */


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

    private static int binarySearch1(int[] nums, int target) {
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
    }

    private static int binarySearch2(int[] nums, int target) {
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
    }

    static Read sc = new Read();
    private static final int Mod = (int) 1e9 + 7;
    private static int T = 1;

    public static void main(String[] args) throws IOException {
        int T = sc.nextInt();
        while (T-- > 0) {
            // solve();
            sc.bw.flush();
        }
        sc.bw.close();
    }

    private static String[] ss;
    private static String s;

    // https://codeforces.com/contest/2001/problem/C 树的构造题，记得sc.bw.flush()
    private static void solveC() throws IOException {
        int n = sc.nextInt();
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 2; i <= n; i++) { // 节点编号从1开始
            list.add(i);
        }
        int[] inTree = new int[n + 1];
        inTree[1] = 1;
        List<int[]> edges = new ArrayList<>();
        while (!list.isEmpty()) {
            int y = list.get(list.size() - 1);
            list.remove(list.size() - 1);
            if (inTree[y] == 1) {
                continue;
            }
            int l = 1, r = y; // 左右端点
            while (true) {
                int q = query(l, r);
                if (q == l) {
                    inTree[r] = 1;
                    edges.add(new int[]{l, r});
                    break;
                }
                if (inTree[q] == 1) {
                    l = q;
                }else{
                    r = q;
                }
            }
            list.add(y); // 这里还要将y加回来！！！
        }
        sc.print("! ");
        for (int[] edge : edges) {
            sc.print(edge[0] + " " + edge[1] + " ");
        }
    }
    // 不知道错哪里，唉
    private static void solveC_WA() throws IOException {
        int n = sc.nextInt();
        ArrayList<int[]> edges = new ArrayList<>();
        int[] c = new int[n + 1];
        Arrays.setAll(c, i -> i);
        for (int i = 0; i < n - 1; i++) { // 总共n - 1条边
            int u = 1, v = 1;
            while (c[u] == c[v]) {
                v++;
            }
            int x;
            while ((x = query(u, v)) != u) { // 二分的过程
                if (c[u] == c[x]) {
                    u = x;
                }else {
                    v = x;
                }
            }
            // addEdge
            edges.add(new int[]{u, v});
            // 将c[u] 与c[v] 合并
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (c[j] == c[v]) {
                    tmp.add(j);
                }
            }
            for (int j : tmp) {
                c[j] = c[u];
            }
        }
        sc.print("! ");
        for (int[] edge : edges) {
            sc.print(edge[0] + " " + edge[1] + " ");
        }
        // sc.bw.flush();
    }
    private static int query(int a, int b) throws IOException {
        sc.println("? " + a + " " + b);
        sc.bw.flush();
        return sc.nextInt();
    }

    // https://codeforces.com/contest/2001/problem/D
    private static void solveD() throws IOException {
        int n = sc.nextInt();
        ss = sc.nextLine().split(" ");
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
        }
        ArrayList<Integer> list = new ArrayList<>();
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            cnt.merge(x, 1, Integer::sum);
        }
        HashSet<Integer> set = new HashSet<>();
        boolean isOdd;
        for (int x : nums) {
            isOdd = list.size() % 2 != 1; // 表示当前需要填的元素是否为奇数
            cnt.merge(x, -1, Integer::sum);
            if (set.contains(x)) {
                continue;
            }
            while (list.size() > 0) {
                if (cnt.get(list.get(list.size() - 1)) > 0 && ((isOdd && list.get(list.size() - 1) > x) || (!isOdd && list.get(list.size() - 1) < x))) {
                    set.remove(list.get(list.size() - 1));
                    list.remove(list.size() - 1);
                    isOdd = !isOdd;
                }
                else if (list.size() > 1 && cnt.get(list.get(list.size() - 1)) > 0 && cnt.get(list.get(list.size() - 2)) > 0
                        && ((isOdd && list.get(list.size() - 2) < x) || (!isOdd && list.get(list.size() - 2) > x))) {
                    set.remove(list.get(list.size() - 1));
                    set.remove(list.get(list.size() - 2));
                    list.remove(list.size() - 1);
                    list.remove(list.size() - 1); // 细节  删除两个元素
                }else{
                    break;
                }
            }
            list.add(x);
            set.add(x);
        }
        sc.println(list.size());
        for (int x : list) {
            sc.print(x + " ");
        }
        sc.print("\n");
    }

}