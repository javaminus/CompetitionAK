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
        int m = sc.nextInt();
        for (int i = 1; i <= n; i++) {
            nums[i] = sc.nextInt();
        }
        build(1, 1, n);
        for (int i = 0; i < m; i++) {
            ss = sc.nextLine().split(" ");
            int ops = Integer.parseInt(ss[0]);
            if (ops == 1) {
                int left = Integer.parseInt(ss[1]);
                int right = Integer.parseInt(ss[2]);
                if (left > right) {
                    int tmp = left;
                    left = right;
                    right = tmp;
                }
                sc.println(query(1, left, right).ans);
            } else if (ops == 2) {
                int p = Integer.parseInt(ss[1]);
                int s = Integer.parseInt(ss[2]);
                update(1, p, p,s);
            }
        }
    }

    static final int N = 500010;
    static Node[] nodes = new Node[4 * N + 2];
    static long[] nums = new long[N];

    static class Node {
        int l, r;
        long sum, leftMax, rightMax, ans; // sum就是区间和，就是（val）,leftMax:左端点开始最大值；rightMax:右端点开始最大值；ans:区间最大值
        Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }

    static void build(int p, int l, int r) { // 对于一个区间（编号为p），他的左儿子为2p，右儿子为2p+1
        nodes[p] = new Node(l, r);
        if (l == r) {
            nodes[p].sum = nums[l];
            return;
        }
        int mid = (l + r) >> 1;
        build(p * 2, l, mid);
        build(p * 2 + 1, mid + 1, r);
        pushup(p);
    }

    static void pushup(int p) {
        nodes[p].sum = nodes[p * 2].sum + nodes[p * 2 + 1].sum;
        nodes[p].leftMax = Math.max(nodes[p * 2].leftMax, nodes[p * 2].sum + nodes[p * 2 + 1].leftMax);
        nodes[p].rightMax = Math.max(nodes[p * 2 + 1].rightMax, nodes[p * 2 + 1].sum + nodes[p * 2].rightMax);
        nodes[p].ans = Math.max(nodes[p * 2].rightMax + nodes[p].leftMax, Math.max(nodes[p * 2].ans, nodes[p * 2 + 1].ans));
    }

    static void update(int p, int pl, int pr, int s) { // 这里的节点p不是真正意义上的数组编号，而是二叉树节点编号
        if (pl <= nodes[p].l && pr >= nodes[p].r) {
            nodes[p].sum = nodes[p].leftMax = nodes[p].rightMax = nodes[p].ans = s; // 单点更新
            return;
        }
        int mid = (nodes[p].l + nodes[p].r) >> 1;
        if (pl <= mid) update(p * 2, pl, pr, s);
        if (pr > mid) update(p * 2 + 1, pl, pr, s);
        // 给定区间[l,r]，求区间内的最大值。  归并过程
        pushup(p);
    }

    static Node query(int p, int pl, int pr) {
        if (pl <= nodes[p].l && pr >= nodes[p].r) {
            return nodes[p];
        }
        int mid = (nodes[p].l + nodes[p].r) >> 1;
        if (pr <= mid) { // 全在左边
            return query(p * 2, pl, pr);
        } else if (nodes[p].l > mid) { // 全在右边
            return query(p * 2 + 1, pl, pr);
        }else{
            //否则就左右儿子都访问，然后合并区间
            Node node = new Node(0, 0), a = query(p * 2, pl, pr), b = query(p * 2 + 1, pl, pr);
            node.leftMax = Math.max(a.leftMax, a.sum + b.leftMax);
            node.rightMax = Math.max(b.rightMax, b.sum + a.rightMax);
            node.ans = Math.max(a.rightMax + b.leftMax, Math.max(a.ans, b.ans));
            return node;
        }
    }
}