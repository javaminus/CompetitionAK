import java.util.*;

public class Main {
    private static int LOG;
    private static int[][] up;
    private static int[] depth;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 读取岛屿数量和查询次数
        int n = scanner.nextInt();
        int q = scanner.nextInt();

        // 计算 log(n)
        LOG = (int) (Math.log(n) / Math.log(2)) + 1;

        // 构建图
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 读取桥梁信息
        for (int i = 0; i < n - 1; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        // 初始化深度和 up 数组
        depth = new int[n + 1];
        up = new int[n + 1][LOG];

        // 预处理深度和父节点信息
        dfs(graph, 1, 1);

        // 处理每个查询
        for (int i = 0; i < q; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            System.out.println(distance(a, b));
        }

        scanner.close();
    }

    // 深度优先搜索预处理深度和父节点信息
    private static void dfs(List<List<Integer>> graph, int node, int parent) {
        depth[node] = depth[parent] + 1;
        up[node][0] = parent;

        for (int i = 1; i < LOG; i++) {
            up[node][i] = up[up[node][i - 1]][i - 1];
        }

        for (int neighbor : graph.get(node)) {
            if (neighbor != parent) {
                dfs(graph, neighbor, node);
            }
        }
    }

    // 计算两个节点之间的距离
    private static int distance(int a, int b) {
        int lca = lca(a, b);
        return depth[a] + depth[b] - 2 * depth[lca];
    }

    // 计算两个节点的最近公共祖先（LCA）
    private static int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        // 将 a 和 b 提升到同一深度
        for (int i = LOG - 1; i >= 0; i--) {
            if (depth[a] - (1 << i) >= depth[b]) {
                a = up[a][i];
            }
        }

        if (a == b) {
            return a;
        }

        // 同时提升 a 和 b
        for (int i = LOG - 1; i >= 0; i--) {
            if (up[a][i] != up[b][i]) {
                a = up[a][i];
                b = up[b][i];
            }
        }

        return up[a][0];
    }
}