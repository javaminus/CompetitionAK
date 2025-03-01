import java.io.*;
import java.util.*;

public class Main {
    static int n, s;
    static int[] a;
    static ArrayList<Integer>[] g;
    static int[] in, out;
    static int timer = 0;
    // posOfValue[x] 表示生命力为 x 的结点在 Euler Tour 中的位置
    static int[] posOfValue;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] parts = br.readLine().split("\\s+");
        n = Integer.parseInt(parts[0]);
        s = Integer.parseInt(parts[1]);
        a = new int[n+1];
        parts = br.readLine().split("\\s+");
        for (int i = 1; i <= n; i++) {
            a[i] = Integer.parseInt(parts[i-1]);
        }

        // 构造树
        g = new ArrayList[n+1];
        for (int i = 1; i <= n; i++) {
            g[i] = new ArrayList<>();
        }
        for (int i = 1; i <= n-1; i++) {
            parts = br.readLine().split("\\s+");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            g[u].add(v);
            g[v].add(u);
        }

        in = new int[n+1];
        out = new int[n+1];
        posOfValue = new int[n+1]; // 注意：a的取值范围在 1~n 且各不相同

        // DFS 生成 Euler Tour (以 s 为根)
        dfs(s, -1);

        // 构造数组 nodes，存放所有结点 id 按照 a[id] 的值升序排列
        Integer[] nodes = new Integer[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = i+1;
        }
        Arrays.sort(nodes, Comparator.comparingInt(o -> a[o]));

        // 初始化 BIT，大小为 n，indices 范围为 [1,n]
        BIT bit = new BIT(n);

        long ans = 0;

        // 对每个结点按照生命力升序处理，保证在处理一个结点时，其子树中比其生命力小的结点已经加入 BIT
        for (int id : nodes) {
            int L = in[id], R = out[id];
            // 查询 BIT，在 Euler Tour 区间 [L+1, R] 中已经出现的结点个数
            // 注意：区间 [L+1, R] 表示 id 自身之外的子树结点
            int count = bit.query(R) - bit.query(L);
            // penalty 为满足：d 是 a[id] 的因子（且 d < a[id]）且该因子结点在 id 的子树中的个数
            int penalty = 0;
            List<Integer> divisors = getDivisors(a[id]);
            for (int d : divisors) {
                if(d < a[id]) {
                    int pos = posOfValue[d];
                    // 判断节点（生命力为 d）的 Euler Tour 位置是否在 id 的子树中
                    if(pos > L && pos <= R) {
                        penalty++;
                    }
                }
            }
            ans += (count - penalty);
            // 将当前结点加入 BIT，便于后续查询
            bit.update(in[id], 1);
        }
        System.out.println(ans);
    }

    // 计算结点 id 的 Euler Tour 入时间和出时间
    static void dfs(int u, int parent) {
        in[u] = ++timer;
        // 将该结点的生命力在 posOfValue 中记录下 Euler Tour 位置
        posOfValue[a[u]] = in[u];
        for (int v : g[u]) {
            if(v == parent) continue;
            dfs(v, u);
        }
        out[u] = timer;
    }

    // 获取一个数的所有除数，不包括该数本身
    static List<Integer> getDivisors(int x) {
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i*i <= x; i++) {
            if(x % i == 0) {
                res.add(i);
                if(i * i != x) {
                    res.add(x / i);
                }
            }
        }
        return res;
    }

    // 树状数组（Fenwick Tree）实现，索引从 1 开始
    static class BIT {
        int n;
        int[] tree;

        BIT(int n) {
            this.n = n;
            tree = new int[n+1];
        }

        // 将 idx 位置加上 val
        void update(int idx, int val) {
            for(; idx <= n; idx += idx & -idx) {
                tree[idx] += val;
            }
        }

        // 求前缀和 query(1...idx)
        int query(int idx) {
            int sum = 0;
            for(; idx > 0; idx -= idx & -idx) {
                sum += tree[idx];
            }
            return sum;
        }
    }
}