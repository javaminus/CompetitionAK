package com.Java_Template.graph.basic;

import java.util.*;


class Node{
    int val;
    List<Node> neighbors;
    public Node(){
        val = 0;
        neighbors = new ArrayList<Node>();
    }

    public Node(int val) {
        this.val = val;
        neighbors = new ArrayList<Node>();
    }

    public Node(int val, ArrayList<Node> neighbors) {
        this.val = val;
        this.neighbors = neighbors;
    }
}

public class problemImpl implements problem {

    // 997. 找到小镇的法官
    @Override
    public int findJudge(int n, int[][] trust) {
        int[] inDegrees = new int[n + 1];
        int[] outDegrees = new int[n + 1];
        for (int[] edge : trust) {
            inDegrees[edge[1]]++;
            outDegrees[edge[0]]++;
        }
        for (int i = 1; i <= n; i++) {
            // 法官这个节点的入度是 n−1, 出度是 0
            if (inDegrees[i] == n - 1 && outDegrees[i] == 0) {
                return i;
            }
        }
        return -1;
    }

    // 1791. 找出星型图的中心节点
    @Override
    public int findCenter(int[][] edges) {
//        int n = edges.length + 1;
//        int[] degrees = new int[n + 1];
//        for (int[] edge : edges) {
//            degrees[edge[1]]++;
//            degrees[edge[0]]++;
//        }
//        for (int i = 1;; i++) {
//            if (degrees[i] == n - 1) {
//                return i;
//            }
//
//        }
        return edges[0][0] == edges[1][0] || edges[0][0] == edges[1][1] ? edges[0][0] : edges[0][1];
    }


     // 1971. 寻找图中是否存在路径()
    @Override  // 广度优先搜索
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<Integer>();
        }
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        boolean[] visited = new boolean[n];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.offer(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            if (vertex == destination) {
                break;
            }
            for (int next : g[vertex]) {
                if (!visited[next]) {
                    queue.offer(next);
                    visited[next] = true;
                }
            }
        }
        return visited[destination];
    }

    // 深度优先
    /* public boolean validPath(int n, int[][] edges, int source, int destination) {
        List<Integer>[] g = new List[n];
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<Integer>();
        }
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        boolean[] visited = new boolean[n];
        return dfs(source, destination, g, visited);
    }

    private boolean dfs(int source, int destination, List<Integer>[] g, boolean[] visited) {
        if (source == destination) {
            return true;
        }
        visited[source] = true;
        for (int next : g[source]) {
            if (!visited[next] && dfs(next, destination, g, visited)) {
                return true;
            }
        }
        return false;
    } */

    // LCP 07. 传递信息 BFS
    public int numWays(int n, int[][] edges, int k) {
        List<Integer>[] g = new List[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<Integer>();
        }
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            // g[y].add(x);
        }
        boolean[] visited = new boolean[n];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        visited[0] = true;
        int step = 0;
        while (!queue.isEmpty() && step < k) {
            step++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int vertex = queue.poll();
                for (int next : g[vertex]) {
                    queue.offer(next);
                }
            }
        }
        if (step == k) {
            while (!queue.isEmpty()) {
                if (queue.pop() == n - 1) {
                    ans++;
                }
            }
        }
        return ans;
    }

    // bfs
    /*
    int ans;
    public int numWays(int n, int[][] edges, int k) {
        List<Integer>[] g = new List[n];
        ans = 0;
        for (int i = 0; i < n; i++) {
            g[i] = new ArrayList<Integer>();
        }
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            // g[y].add(x);
        }
        dfs(0, 0, n, k, g);
        return ans;
    }

    public void dfs(int index, int steps, int n, int k, List<Integer>[] g) {
        if (steps == k) {
            if (index == n - 1) {
                ans++;
            }
            return;
        }
        for (int next : g[index]) {
            dfs(next, steps + 1, n, k, g);
        }
    }*/

    // 拷贝一个图，所有节点都要自己重新new出来
    HashMap<Node, Node> visited = new HashMap<>();
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        // 如果该节点已经被访问过了，则直接从哈希表中取出对应的克隆节点返回
        if (visited.containsKey(node)) {
            return visited.get(node);
        }
        // 克隆节点，注意到为了深拷贝我们不会克隆它的邻居的列表
        Node cloneNode = new Node(node.val, new ArrayList<>());
        // 哈希表存储
        visited.put(node, cloneNode);

        // 遍历该节点的邻居并更新克隆节点的邻居列表
        for (Node neighnbor : node.neighbors) {
            cloneNode.neighbors.add(cloneGraph(neighnbor));
        }
        return cloneNode;
    }



    boolean valid = true;
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            g.add(new ArrayList<Integer>());
        }
        for (int[] edge : prerequisites) {
            int x = edge[0], y = edge[1];
            g.get(y).add(x);
        }
        int[] visited = new int[numCourses];
        for (int i = 0; i < numCourses && valid; i++) {
            if (visited[i] == 0) {
                dfs1(g, i, visited);
            }
        }
        return valid;
    }
    private void dfs1(List<List<Integer>> g, int i, int[] visited) {
        visited[i] = 1;
        for (int j : g.get(i)) {
            if (visited[j] == 0) {
                dfs1(g, j, visited);
                if (!valid) {
                    return;
                }
            } else if (visited[j] == 1) {
                valid = false;
                return;
            }
        }
        visited[i] = 2;
    }


    // 210. 课程表 II
    List<Integer> ans;  // 存储结果的列表
    // boolean valid;  // 判断是否存在有效的课程顺序
    @Override
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        ans = new ArrayList<>();  // 初始化结果列表
        ArrayList<List<Integer>> g = new ArrayList<>();  // 创建邻接表，表示课程图
        for (int i = 0; i < numCourses; i++) {
            g.add(new ArrayList<Integer>());
        }
        // 构建课程图的邻接表
        for (int[] edge : prerequisites) {
            int x = edge[0], y = edge[1];
            g.get(y).add(x);
        }
        int[] visited = new int[numCourses];  // 记录课程访问状态的数组
        valid = true;  // 初始化为 true，表示初始状态为有效
        // 对每个课程进行深度优先搜索
        for (int i = 0; i < numCourses; i++) {
            if (visited[i] == 0) {
                dfs(g, i, visited);
            }
        }
        // 如果存在环，则返回空数组
        if (!valid) {
            return new int[0];
        }
        // 将结果列表转换为数组形式返回
        int[] res = new int[numCourses];
        int i = numCourses - 1;
        for (int num : ans) {
            res[i--] = num;
        }
        return res;
    }
    // 深度优先搜索函数
    private void dfs(List<List<Integer>> g, int i, int[] visited) {
        visited[i] = 1;  // 将当前课程标记为正在访问
        // 遍历当前课程的后续课程
        for (int x : g.get(i)) {
            if (visited[x] == 0) {
                dfs(g, x, visited);
                if (!valid) {
                    return;
                }
            } else if (visited[x] == 1) {
                valid = false;  // 如果存在环，则将 valid 设为 false
                return;
            }
        }
        ans.add(i);  // 将当前课程加入结果列表
        visited[i] = 2;  // 将当前课程标记为已访问
    }

    @Override
    public int findCircleNum(int[][] isConnected) { // int[][] isConnected 邻接矩阵
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(isConnected, visited, n, i);
                ans++;
            }
        }
        return ans;
    }

    private void dfs(int[][] g, boolean[] visited, int n, int i) {
        for (int j = 0; j < n; j++) {
            if (!visited[j] && g[i][j] == 1) {
                visited[j] = true;
                dfs(g, visited, n, j);
            }
        }
    }

    @Override
    public boolean isBipartite(int[][] graph) { // 判断二分图，输入：邻接矩阵
        int n = graph.length;
        int[] visited = new int[n];
        for (int i = 0; i < n; i++) {
            if (visited[i]==0 && !dfs(graph, visited, i, 1)) {
                return false;
            }
        }
        return true;
    }

    private boolean dfs(int[][] g, int[] visited, int i, int color) {
        // 如果要对某顶点染色时，发现它已经被染色了，则判断它的颜色是否与本次要染的颜色相同，如果矛盾，说明此无向图无法被正确染色，返回 false。
        if (visited[i] != 0) {
            return visited[i] == color;
        }
        // 对当前顶点进行染色，并将当前顶点的所有邻接点染成相反的颜色。
        visited[i] = color;
        for (int j : g[i]) {  // j是i的邻接点
            // return dfs(g, visited, j, -color);
            if (!dfs(g, visited, j, -color)) {
                return false;
            }
        }
        return true;
    }

    // 797. 所有可能的路径
    ArrayList<List<Integer>> ans1;
    Deque<Integer> stack;
    int n;
    @Override
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        n = graph.length;
        ans1 = new ArrayList<>();
        stack = new ArrayDeque<>();
        stack.offerLast(0);
        dfs(graph, 0);
        return ans1;
    }

    private void dfs(int[][] graph, int x) {
        if (x == n) {
            ans1.add(new ArrayList<>(stack));
            return;
        }
        for (int y : graph[x]) {
            stack.offerLast(y);
            dfs(graph, y);
            stack.pollLast();
        }
    }

    // 2242. 节点序列的最大得分
    //  暴力超时写法
//    boolean[] visited;
//    int ans = -1;
//    public int maximumScore(int[] scores, int[][] edges) {
//        int n = scores.length;
//        List<Integer>[] g = new List[n];
//        Arrays.setAll(g, e -> new ArrayList<Integer>());
//        visited = new boolean[n];
//        for (int[] edge : edges) {
//            int x = edge[0], y = edge[1];
//            g[x].add(y);
//            g[y].add(x);
//        }
//        for (int i = 0; i < n; i++) {
//            dfs(g, scores, 0, i, scores[i]);
//        }
//        return ans;
//    }
//
//    private void dfs(List<Integer>[] g, int[] scores, int len, int x, int sum) {
//        if (len == 3) {
//            System.out.println(sum);
//            ans = Math.max(ans, sum);
//            return;
//        }
//        visited[x] = true;
//        for (int y : g[x]) {
//            if (!visited[y]) {
//                dfs(g, scores, len + 1, y, sum + scores[y]);
//            }
//        }
//        visited[x] = false;  // 恢复visited数组为先前的状态
//    }
    @Override
    public int maximumScore(int[] scores, int[][] edges) { // 换根写法
        int n = scores.length;
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<int[]>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(new int[]{scores[y], y});
            g[y].add(new int[]{scores[x], x});
        }
        for (int i = 0; i < n; i++) {
            if (g[i].size() > 3) {
                g[i].sort((a, b) -> b[0] - a[0]);
                g[i] = new ArrayList<>(g[i].subList(0, 3)); // 左闭右开
            }
        }
        for (List<int[]> gi : g) {
            if (gi.size() > 3) {
                gi.sort((a, b) -> b[0] - a[0]);
                gi = new ArrayList<>(gi.subList(0, 3));
            }
        }
        int ans = -1;
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            for (int[] p : g[x]) {
                int a = p[1];
                for (int[] q : g[y]) {
                    int b = q[1];
                    if (a != b && a != y && b != x) {
                        ans = Math.max(ans, scores[a] + scores[b] + scores[x] + scores[y]);
                    }
                }
            }
        }
        return ans;
    }

    // 3067. 在带权树网络中统计可连接服务器对数目
    @Override
    public int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed) {
        int n = edges.length + 1;
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], w = edge[2];
            g[x].add(new int[]{y, w});
            g[y].add(new int[]{x, w});
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int[] j : g[i]) {
                int cnt = dfs(g, signalSpeed, j[0], i, j[1]);
                ans[i] += cnt * sum;
                sum += cnt;
            }
        }
        return ans;
    }

    private int dfs(List<int[]>[] g, int signal, int x, int fa, int total) {
        int ans = total % signal == 0 ? 1 : 0;
        for (int[] y : g[x]) {
            if (y[0] != fa) {
                ans += dfs(g, signal, y[0], x, total + y[1]);
            }
        }
        return ans;
    }


    // 3108. 带权图里旅途的最小代价
    public int[] minimumCost(int n, int[][] edges, int[][] query) {
        List[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<int[]>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], z = edge[2];
            g[x].add(new int[]{y, z});
            g[y].add(new int[]{x, z});
        }
        int[] ids = new int[n]; // 记录每个点所在连通块的编号
        Arrays.fill(ids, -1);
        ArrayList<Integer> ccAnd = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (ids[i] < 0) {
                ccAnd.add(dfs(ids, ccAnd.size(), g, i));
            }
        }
        int[] ans = new int[query.length];
        for (int i = 0; i < query.length; i++) {
            int from = query[i][0], to = query[i][1];
            ans[i] = from == to ? 0 : ids[from] == ids[to] ? ccAnd.get(ids[from]) : -1;
        }
        return ans;

    }
    private int dfs(int[] ids, int curId, List<int[]>[] g, int x) {
        ids[x] = curId;
        int and = -1;
        for (int[] y : g[x]) {
            and &= y[1];
            if (ids[y[0]] < 0) { // 没有被访问
                and &= dfs(ids, curId, g, y[0]);
            }
        }
        return and;
    }



}

