import java.io.*;
import java.util.*;

public class Main {
    static int N, Q;
    static ArrayList<Integer>[] tree; // 存储树的邻接表
    static int[] tin, tout; // Euler Tour中的进入时间与离开时间记录
    static int timer = 0;
    static long[] weight; // 每个顶点的权重
    static int[] parent; // 每个顶点在以1为根的树中的父节点（根的父节点为0）
    // 对于每条边，用 child[y] 表示在边 y 中哪个端点是子节点
    static int[] child;
    // 用于做树状数组，支持点更新和区间求和
    static FenwTree fenw;
    
    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 和 BufferedWriter 提高IO效率
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        
        // 读取 N
        N = Integer.parseInt(br.readLine());
        tree = new ArrayList[N+1];
        for (int i = 1; i <= N; i++) {
            tree[i] = new ArrayList<>();
        }
        // 初始化 parent 数组，并申请 Euler Tour数组
        parent = new int[N+1];
        tin = new int[N+1];
        tout = new int[N+1];
        
        // 预先存储边信息，为后续判断父子关系
        // edges[i] = {u, v} 保证边标号 1~(N-1)
        int[][] edges = new int[N][2]; // 使用下标1~N-1
        for (int i = 1; i <= N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            edges[i] = new int[]{u, v};
            // 将边加入无向图中
            tree[u].add(v);
            tree[v].add(u);
        }
        
        // 以顶点1作为根，做一次DFS构建 Euler Tour 和父节点信息
        weight = new long[N+1];
        Arrays.fill(weight, 1); // 初始化每个点的权重为 1
        parent[1] = 0; // 根没有父节点
        dfs(1, 0);
        
        // 初始化 child 数组，对于每条边，记录边中子节点（即不为父节点的那个顶点）
        child = new int[N]; // 1-indexed, child[edgeId]
        for (int i = 1; i <= N-1; i++) {
            int u = edges[i][0], v = edges[i][1];
            // 若 u 是 v 的父节点，则 v 为子节点；否则若 v 是 u 的父节点，则 u为子节点
            if (parent[u] == v) {
                child[i] = u;
            } else if (parent[v] == u) {
                child[i] = v;
            }
            // 注：题目保证此边一定能分为两棵子树，所以必有一个是子节点
        }
        
        // 用 Euler Tour 的顺序建树状数组，arr[tin[v]]=weight[v]
        int size = N; // Euler Tour 数组大小等于节点数
        long[] arr = new long[size+1];
        for (int i = 1; i <= N; i++) {
            arr[tin[i]] = weight[i];
        }
        fenw = new FenwTree(size);
        fenw.build(arr);
        
        // 维护整个树的总权重
        long totalSum = N; // 初始时每个节点为1
        
        // 读取 Q 查询数
        Q = Integer.parseInt(br.readLine());
        StringBuilder ans = new StringBuilder();
        for(int qi = 0; qi < Q; qi++){
            st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());
            if(type == 1){
                // Query 类型1: 1 x w，给顶点 x 增加权重 w
                int x = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                // 更新树状数组中对应位置
                fenw.update(tin[x], w);
                // 更新总权重
                totalSum += w;
            } else if(type == 2){
                // Query 类型2: 2 y，查询如果删除边 y分成两部分的权重差值
                int y = Integer.parseInt(st.nextToken());
                int c = child[y]; // 边 y 的子节点
                // 子树的权重求和：所有在 Euler Tour 中处于 [tin[c], tout[c]]
                long subSum = fenw.query(tout[c]) - fenw.query(tin[c]-1);
                // 另一部分的权重为 totalSum - subSum
                long diff = Math.abs(totalSum - 2 * subSum);
                ans.append(diff).append("\n");
            }
        }
        bw.write(ans.toString());
        bw.flush();
        bw.close();
    }
    
    // DFS 进行 Euler Tour，并记录父节点信息
    static void dfs(int cur, int par) {
        timer++;
        tin[cur] = timer;
        parent[cur] = par;
        for (int nxt : tree[cur]) {
            if (nxt == par) continue;
            dfs(nxt, cur);
        }
        tout[cur] = timer;
    }
    
    // 树状数组实现：1-indexed
    static class FenwTree {
        int n;
        long[] tree;
        public FenwTree(int n) {
            this.n = n;
            tree = new long[n+1];
        }
        
        // 更新：位置 idx 增加 val
        public void update(int idx, long val) {
            while(idx <= n) {
                tree[idx] += val;
                idx += idx & -idx;
            }
        }
        
        // 前缀和求解：区间 [1, idx] 的和
        public long query(int idx) {
            long sum = 0;
            while(idx > 0) {
                sum += tree[idx];
                idx -= idx & -idx;
            }
            return sum;
        }
        
        // 构造树状数组 (将 arr 数组中的值一次性处理好)
        public void build(long[] arr) {
            for (int i = 1; i <= n; i++) {
                tree[i] += arr[i];
                int j = i + (i & -i);
                if(j <= n) {
                    tree[j] += tree[i];
                }
            }
        }
    }
}