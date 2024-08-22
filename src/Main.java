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

    private static void solve() throws IOException {
        int n = sc.nextInt();
        int m = sc.nextInt();
        ss = sc.nextLine().split(" ");
        for (int i = 1; i <= n; i++) {
            nums[i] = Integer.parseInt(ss[i - 1]); // 下标从1开始
        }
        build(1, 1, n);
        for (int i = 0; i < m; i++) {
            ss = sc.nextLine().split(" ");
            int q = Integer.parseInt(ss[0]);
            if (q == 1) {
                int x = Integer.parseInt(ss[1]);
                int y = Integer.parseInt(ss[2]);
                int z = Integer.parseInt(ss[3]);
                update(1, x, y, z);
            }else {
                int x = Integer.parseInt(ss[1]);
                int y = Integer.parseInt(ss[2]);
                sc.println(query(1, x, y));
            }
        }
    }

    static final int N = 100010;
    static int[] nums = new int[N + 2];
    static Node[] nodes = new Node[4 * N + 2];

    static class Node {
        int l, r;
        long val, add;

        Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    static void build(int p, int l, int r) { // 对于一个区间（编号为p），他的左儿子为2p，右儿子为2p+1
        nodes[p] = new Node(l, r);
        if (l == r) {
            nodes[p].val = nums[l];
            return;
        }
        int mid = (l + r) >> 1;
        build(p * 2, l, mid);
        build(p * 2 + 1, mid + 1, r);
        nodes[p].val = nodes[p * 2].val + nodes[p * 2 + 1].val;
    }

    static void pushdown(int p) {
        if (nodes[p].add != 0) {
            nodes[p * 2].val += nodes[p].add * (nodes[p * 2].r - nodes[p * 2].l + 1);
            nodes[p * 2 + 1].val += nodes[p].add * (nodes[p * 2 + 1].r - nodes[p * 2 + 1].l + 1);
            nodes[p * 2].add += nodes[p].add;
            nodes[p * 2 + 1].add += nodes[p].add;
            nodes[p].add = 0;
        }
    }

    static void update(int p, int x, int y, int z) {
        if (x <= nodes[p].l && y >= nodes[p].r) {
            nodes[p].val += (long) z * (nodes[p].r - nodes[p].l + 1);
            nodes[p].add += z;
            return;
        }
        pushdown(p);
        int mid = (nodes[p].l + nodes[p].r) >> 1;
        if (x <= mid) update(p * 2, x, y, z);
        if (y > mid) update(p * 2 + 1, x, y, z);
        nodes[p].val = nodes[p * 2].val + nodes[p * 2 + 1].val;
    }

    static long query(int p, int x, int y) {
        if (x <= nodes[p].l && y >= nodes[p].r) return nodes[p].val;
        pushdown(p);
        int mid = (nodes[p].l + nodes[p].r) >> 1;
        long ans = 0;
        if (x <= mid) ans += query(p * 2, x, y);
        if (y > mid) ans += query(p * 2 + 1, x, y);
        return ans;
    }
}