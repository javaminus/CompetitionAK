import java.io.*;
import java.util.*;

class Standard {
    // 定义边的类，记录边的终点和权值（1表示正常，2表示损坏）
    static class Edge {
        int to, weight;
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static int N;
    // 利用邻接表存储图，graph[i] 表示与城市 i 相连的边集合
    static ArrayList<Edge>[] graph;
    // 保存被选择的城市
    static ArrayList<Integer> ans = new ArrayList<>();

    /**
     * DFS 遍历树
     * @param u 当前节点
     * @param parent 父节点
     * @param weightFromParent 从父节点到 u 的边是否损坏（1：正常，2：损坏）
     * @return 当前节点所在子树中是否已经有城市被选中覆盖 damage 的边
     */
    static boolean dfs(int u, int parent, int weightFromParent) {
        boolean hasChosen = false;
        for (Edge edge : graph[u]) {
            int v = edge.to;
            if (v == parent) continue;
            // 递归遍历子树，如果子树中已选节点，则说明对应的损坏边得到了覆盖
            if (dfs(v, u, edge.weight)) {
                hasChosen = true;
            }
        }
        // 如果当前节点与父节点之间的边是损坏的，且子树中没有已选择节点，则在当前节点选择
        if(weightFromParent == 2 && !hasChosen) {
            ans.add(u);
            return true;
        }
        return hasChosen;
    }

    public static void main(String[] args) throws Exception {
        // 使用 BufferedReader 读取输入
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine().trim());
        // 初始化邻接表，城市编号 1..N
        graph = new ArrayList[N+1];
        for (int i = 0; i <= N; i++){
            graph[i] = new ArrayList<>();
        }
        // 读取 N-1 条道路信息
        for (int i = 1; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            graph[u].add(new Edge(v, x));
            graph[v].add(new Edge(u, x));
        }
        // 从首都 1 开始 DFS，根节点没有父边，传入正常状态 1
        dfs(1, -1, 1);

        // 为了满足部分样例要求按照降序输出选中的城市
        Collections.sort(ans, Collections.reverseOrder());

        // 输出结果
        System.out.println(ans.size());
        if (!ans.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (int num : ans) {
                sb.append(num).append(" ");
            }
            System.out.println(sb.toString().trim());
        }
    }
}