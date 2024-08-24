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
    private static int Mod = (int) 1e9 + 7;
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
    static boolean flag = true;

    private static void solve() throws IOException {
        int n = sc.nextInt();
        int q = sc.nextInt();
        Mod = sc.nextInt();
        ss = sc.nextLine().split(" ");
        for (int i = 1; i <= n; i++) {
            nums[i] = Integer.parseInt(ss[i - 1]);
        }
        build(1, 1, n);
        for (int i = 0; i < q; i++) {
            ss = sc.nextLine().split(" ");
            int ops = Integer.parseInt(ss[0]);
            int left = Integer.parseInt(ss[1]);
            int right = Integer.parseInt(ss[2]);
            if (ops == 1 || ops == 2) {
                int val = Integer.parseInt(ss[3]);
                update(1, left, right, val, ops);
            } else {
                sc.println(query(1, left, right));
            }
        }
    }

    static final int N = 1000010;
    static Node[] nodes = new Node[4 * N + 2];
    static long[] nums = new long[N];

    static class Node {
        int l, r;
        long val, tag1, tag2; // tag1表示累乘，tag2表示累加

        Node(int l, int r) {
            this.tag2 = 1;
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
        nodes[p].val = (nodes[p * 2].val + nodes[p * 2 + 1].val) % Mod;
    }

    static void pushdown(int p) {
        // 根据我们规定的优先度，儿子的值=此刻儿子的值*爸爸的乘法lazy_tag+儿子的区间长度*爸爸的加法lazy_tag
        int mid = (nodes[p].l + nodes[p].r) / 2;
        nodes[p * 2].val = (nodes[p * 2].val * nodes[p].tag2 + nodes[p].tag1 * (mid - nodes[p].l + 1)) % Mod; // 要中间节点
        nodes[p * 2 + 1].val = (nodes[p * 2 + 1].val * nodes[p].tag2 + nodes[p].tag1 * (nodes[p].r - mid)) % Mod; // 不要中间节点
        // 维护lazy_tag
        nodes[p * 2].tag2 = (nodes[p * 2].tag2 * nodes[p].tag2) % Mod;
        nodes[p * 2 + 1].tag2 = (nodes[p * 2 + 1].tag2 * nodes[p].tag2) % Mod;
        nodes[p * 2].tag1 = (nodes[p * 2].tag1 * nodes[p].tag2 + nodes[p].tag1) % Mod;
        nodes[p * 2 + 1].tag1 = (nodes[p * 2 + 1].tag1 * nodes[p ].tag2 + nodes[p].tag1) % Mod;
        // 重置父节点
        nodes[p].tag2 = 1;
        nodes[p].tag1 = 0;
    }

    static void update(int p, int pl, int pr, int x, int ops) { // 这里的节点p不是真正意义上的数组编号，而是二叉树节点编号
        if (pl <= nodes[p].l && pr >= nodes[p].r) {
            if (ops == 1) { // 乘法
                nodes[p].val = nodes[p].val * x % Mod;
                nodes[p].tag2 = (nodes[p].tag2 * x) % Mod;
                nodes[p].tag1 = (nodes[p].tag1 * x) % Mod;
            } else if (ops == 2) { // 加法
                nodes[p].val = (nodes[p].val + (long) x * (nodes[p].r - nodes[p].l + 1)) % Mod;
                nodes[p].tag1 = (nodes[p].tag1 + x) % Mod;
            }
            return;
        }
        pushdown(p);
        int mid = (nodes[p].l + nodes[p].r) >> 1;
        if (pl <= mid) update(p * 2, pl, pr, x, ops);
        if (pr > mid) update(p * 2 + 1, pl, pr, x, ops);
        // 给定区间[l,r]，求区间内的最大值。  归并过程
        nodes[p].val = (nodes[p * 2].val + nodes[p * 2 + 1].val) % Mod;
    }

    static long query(int p, int pl, int pr) {
        if (pl <= nodes[p].l && pr >= nodes[p].r) {
            return nodes[p].val;
        }
        pushdown(p);
        int mid = (nodes[p].l + nodes[p].r) >> 1;
        long ans = 0;
        if (pl <= mid) ans += query(p * 2, pl, pr);
        if (pr > mid) ans += query(p * 2 + 1, pl, pr);
        return ans % Mod;
    }
}