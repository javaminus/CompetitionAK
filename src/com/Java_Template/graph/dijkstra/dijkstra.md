743\. 网络延迟时间
------------

有 `n` 个网络节点，标记为 `1` 到 `n`。

给你一个列表 `times`，表示信号经过 **有向** 边的传递时间。 `times[i] = (ui, vi, wi)`，其中 `ui` 是源节点，`vi` 是目标节点， `wi` 是一个信号从源节点传递到目标节点的时间。

现在，从某个节点 `K` 发出一个信号。需要多久才能使所有节点都收到信号？如果不能使所有节点收到信号，返回 `-1` 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2019/05/23/931_example_1.png)

**输入：**times = \[\[2,1,1\],\[2,3,1\],\[3,4,1\]\], n = 4, k = 2
**输出：**2

**示例 2：**

**输入：**times = \[\[1,2,1\]\], n = 2, k = 1
**输出：**1

**示例 3：**

**输入：**times = \[\[1,2,1\]\], n = 2, k = 2
**输出：**\-1

**提示：**

*   `1 <= k <= n <= 100`
*   `1 <= times.length <= 6000`
*   `times[i].length == 3`
*   `1 <= ui, vi <= n`
*   `ui != vi`
*   `0 <= wi <= 100`
*   所有 `(ui, vi)` 对都 **互不相同**（即，不含重复边）

[https://leetcode.cn/problems/network-delay-time/description/](https://leetcode.cn/problems/network-delay-time/description/)

```java
class Solution {
    // 堆优化版本
    public int networkDelayTime(int[][] times, int n, int k) { // 使用一个小根堆来寻找「未确定节点」中与起点距离最近的点
        // 定义一个常量 INF 表示无穷大，为防止溢出，取 Integer.MAX_VALUE 的一半
        final int INF = Integer.MAX_VALUE / 2;
        // 创建一个邻接表，用于表示图的结构
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<int[]>());
        // 遍历输入的 times 数组，构建邻接表
        for (int[] time : times) {
            int x = time[0] - 1, y = time[1] - 1, z = time[2];
            g[x].add(new int[] { y, z });
        }
        // 初始化距离数组，用于记录起点到各点的最短距离，默认为无穷大
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[k - 1] = 0;
        // 使用优先队列（最小堆）存储节点和到达该节点的时间
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[] { k - 1, 0 }); // 将起点加入优先队列

        // 进行 Dijkstra 算法的主循环
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int x = poll[0], time = poll[1];
            if (dist[x] < time) {
                continue; // 如果当前节点的最短距离已经更新，则跳过
            }
            for (int[] e : g[x]) {
                int y = e[0], d = e[1];
                if (dist[y] > dist[x] + d) {
                    dist[y] = dist[x] + d; // 更新到达节点 y 的最短距离
                    pq.offer(new int[] { y, dist[y] }); // 将节点 y 加入优先队列
                }
            }
        }
        // 找到最终的最大距离，即为答案
        int ans = Arrays.stream(dist).max().getAsInt();
        return ans == INF ? -1 : ans; // 如果存在无法到达的节点，则返回 -1
    }
}
```

2642\. 设计可以求最短路径的图类
-------------------

给你一个有 `n` 个节点的 **有向带权** 图，节点编号为 `0` 到 `n - 1` 。图中的初始边用数组 `edges` 表示，其中 `edges[i] = [fromi, toi, edgeCosti]` 表示从 `fromi` 到 `toi` 有一条代价为 `edgeCosti` 的边。

请你实现一个 `Graph` 类：

*   `Graph(int n, int[][] edges)` 初始化图有 `n` 个节点，并输入初始边。
*   `addEdge(int[] edge)` 向边集中添加一条边，其中 `edge = [from, to, edgeCost]` 。数据保证添加这条边之前对应的两个节点之间没有有向边。
*   `int shortestPath(int node1, int node2)` 返回从节点 `node1` 到 `node2` 的路径 **最小** 代价。如果路径不存在，返回 `-1` 。一条路径的代价是路径中所有边代价之和。

**示例 1：**

![](https://assets.leetcode.com/uploads/2023/01/11/graph3drawio-2.png)

**输入：**
\["Graph", "shortestPath", "shortestPath", "addEdge", "shortestPath"\]
\[\[4, \[\[0, 2, 5\], \[0, 1, 2\], \[1, 2, 1\], \[3, 0, 3\]\]\], \[3, 2\], \[0, 3\], \[\[1, 3, 4\]\], \[0, 3\]\]
**输出：**
\[null, 6, -1, null, 6\]

**解释：**
Graph g = new Graph(4, \[\[0, 2, 5\], \[0, 1, 2\], \[1, 2, 1\], \[3, 0, 3\]\]);
g.shortestPath(3, 2); // 返回 6 。从 3 到 2 的最短路径如第一幅图所示：3 -> 0 -> 1 -> 2 ，总代价为 3 + 2 + 1 = 6 。
g.shortestPath(0, 3); // 返回 -1 。没有从 0 到 3 的路径。
g.addEdge(\[1, 3, 4\]); // 添加一条节点 1 到节点 3 的边，得到第二幅图。
g.shortestPath(0, 3); // 返回 6 。从 0 到 3 的最短路径为 0 -> 1 -> 3 ，总代价为 2 + 4 = 6 。

**提示：**

*   `1 <= n <= 100`
*   `0 <= edges.length <= n * (n - 1)`
*   `edges[i].length == edge.length == 3`
*   `0 <= fromi, toi, from, to, node1, node2 <= n - 1`
*   `1 <= edgeCosti, edgeCost <= 106`
*   图中任何时候都不会有重边和自环。
*   调用 `addEdge` 至多 `100` 次。
*   调用 `shortestPath` 至多 `100` 次。

[https://leetcode.cn/problems/design-graph-with-shortest-path-calculator/description/](https://leetcode.cn/problems/design-graph-with-shortest-path-calculator/description/)

```java
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Graph {
    List<int[]>[] g;
    int n;
    public Graph(int n, int[][] edges) {
        this.n = n;
        g = new List[n];
        Arrays.setAll(g,e->new ArrayList<int[]>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], z = edge[2];
            g[x].add(new int[]{y, z});
        }
    }

    public void addEdge(int[] edge) {
        int x = edge[0], y = edge[1], z = edge[2];
        g[x].add(new int[]{y, z});
    }

    public int shortestPath(int node1, int node2) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[node1] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{node1, 0});
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int x = poll[0], v = poll[1];
            if (v > dist[x]) {
                continue;
            }
            for (int[] e : g[x]) {
                int y = e[0], w = e[1];
                if(dist[y]>dist[x]+w){
					dist[y] = dist[x]+w;
                    pq.offer(new int[]{y,dist[y]});
                }
            }
        }
        return dist[node2]==Integer.MAX_VALUE/2?-1:dist[node2];
    }
}

/**
 * Your Graph object will be instantiated and called as such:
 * Graph obj = new Graph(n, edges);
 * obj.addEdge(edge);
 * int param_2 = obj.shortestPath(node1,node2);
 */
```

1976\. 到达目的地的方案数
----------------

你在一个城市里，城市由 `n` 个路口组成，路口编号为 `0` 到 `n - 1` ，某些路口之间有 **双向** 道路。输入保证你可以从任意路口出发到达其他任意路口，且任意两个路口之间最多有一条路。

给你一个整数 `n` 和二维整数数组 `roads` ，其中 `roads[i] = [ui, vi, timei]` 表示在路口 `ui` 和 `vi` 之间有一条需要花费 `timei` 时间才能通过的道路。你想知道花费 **最少时间** 从路口 `0` 出发到达路口 `n - 1` 的方案数。

请返回花费 **最少时间** 到达目的地的 **路径数目** 。由于答案可能很大，将结果对 `109 + 7` **取余** 后返回。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/07/17/graph2.png)

**输入：**n = 7, roads = \[\[0,6,7\],\[0,1,2\],\[1,2,3\],\[1,3,3\],\[6,3,3\],\[3,5,1\],\[6,5,1\],\[2,5,1\],\[0,4,5\],\[4,6,2\]\]
**输出：**4
**解释：**从路口 0 出发到路口 6 花费的最少时间是 7 分钟。
四条花费 7 分钟的路径分别为：
- 0 ➝ 6
- 0 ➝ 4 ➝ 6
- 0 ➝ 1 ➝ 2 ➝ 5 ➝ 6
- 0 ➝ 1 ➝ 3 ➝ 5 ➝ 6

**示例 2：**

**输入：**n = 2, roads = \[\[1,0,10\]\]
**输出：**1
**解释：**只有一条从路口 0 到路口 1 的路，花费 10 分钟。

**提示：**

*   `1 <= n <= 200`
*   `n - 1 <= roads.length <= n * (n - 1) / 2`
*   `roads[i].length == 3`
*   `0 <= ui, vi <= n - 1`
*   `1 <= timei <= 109`
*   `ui != vi`
*   任意两个路口之间至多有一条路。
*   从任意路口出发，你能够到达其他任意路口。

[https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/description/?envType=daily-question&envId=Invalid%20Date](https://leetcode.cn/problems/number-of-ways-to-arrive-at-destination/description/?envType=daily-question&envId=Invalid%20Date)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int countPaths(int n, int[][] roads) {
        int Mod = (int) 1e9 + 7;
        long INF = Long.MAX_VALUE / 2;
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<int[]>());
        for (int[] road : roads) {
            int x = road[0], y = road[1], z = road[2];
            g[x].add(new int[]{y, z});
            g[y].add(new int[]{x, z});
        }
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        int[] dp = new int[n];
        dp[0] = 1;
        dist[0] = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[1], b[1]));
        pq.offer(new long[]{0, 0});
        while (!pq.isEmpty()) {
            long[] poll = pq.poll();
            int x = (int) poll[0];
            long road = poll[1];
            if (dist[x] < road) {
                continue;
            }
            for (int[] e : g[x]) {
                int y = e[0];
                long z = e[1];
                if (dist[y] > dist[x] + z) {
                    dist[y] = dist[x] + z;
                    dp[y] = dp[x];
                    pq.offer(new long[]{y, dist[y]});
                } else if (dist[y] == dist[x] + z) {
                    dp[y] = (dp[y] + dp[x]) % Mod;
                }
            }
        }
        return dp[n - 1];
    }
}
```

1514\. 概率最大的路径
--------------

给你一个由 `n` 个节点（下标从 0 开始）组成的无向加权图，该图由一个描述边的列表组成，其中 `edges[i] = [a, b]` 表示连接节点 a 和 b 的一条无向边，且该边遍历成功的概率为 `succProb[i]` 。

指定两个节点分别作为起点 `start` 和终点 `end` ，请你找出从起点到终点成功概率最大的路径，并返回其成功概率。

如果不存在从 `start` 到 `end` 的路径，请 **返回 0** 。只要答案与标准答案的误差不超过 **1e-5** ，就会被视作正确答案。

**示例 1：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/07/12/1558_ex1.png)**

**输入：**n = 3, edges = \[\[0,1\],\[1,2\],\[0,2\]\], succProb = \[0.5,0.5,0.2\], start = 0, end = 2
**输出：**0.25000
**解释：**从起点到终点有两条路径，其中一条的成功概率为 0.2 ，而另一条为 0.5 \* 0.5 = 0.25

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/07/12/1558_ex2.png)**

**输入：**n = 3, edges = \[\[0,1\],\[1,2\],\[0,2\]\], succProb = \[0.5,0.5,0.3\], start = 0, end = 2
**输出：**0.30000

**示例 3：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/07/12/1558_ex3.png)**

**输入：**n = 3, edges = \[\[0,1\]\], succProb = \[0.5\], start = 0, end = 2
**输出：**0.00000
**解释：**节点 0 和 节点 2 之间不存在路径

**提示：**

*   `2 <= n <= 10^4`
*   `0 <= start, end < n`
*   `start != end`
*   `0 <= a, b < n`
*   `a != b`
*   `0 <= succProb.length == edges.length <= 2*10^4`
*   `0 <= succProb[i] <= 1`
*   每两个节点之间最多有一条边

[https://leetcode.cn/problems/path-with-maximum-probability/description/](https://leetcode.cn/problems/path-with-maximum-probability/description/)

```java
class Solution {
    public double maxProbability(int n, int[][] edges, double[] succProb, int start, int end) {

        // 构造图
        List<double[]>[] graph = new ArrayList[n];
        for(int i = 0; i < n; i++){
            graph[i] = new ArrayList<double[]>();
        }
        for(int i = 0; i < edges.length; i++){
            int idstart = edges[i][0];
            int idend = edges[i][1];
            double weight = succProb[i];

            graph[idstart].add(new double[]{idend, weight});
            graph[idend].add(new double[]{idstart, weight});
        }

        // 自定义排序，优先更新概率大的节点
        Queue<State> pq = new PriorityQueue<State>((a, b) -> {
            if(a.prop > b.prop){
                return -1;
            }else if(a.prop < b.prop){
                return 1;
            }else{
                return 0;
            }
        });
        pq.offer(new State(start, 1));

        double[] res = new double[n];
        res[start] = 1;
        while(!pq.isEmpty()){
            State curNode = pq.poll();
            // 找到了，因为这个节点已经是队列里概率最大的节点了，再通过其他节点去更新这个节点，只会越更新概率越小，所以不用找了，这个就是我们要的答案。
            if(curNode.id == end){
                return curNode.prop;
            }

            // 更新邻居的概率
            for(double[] neighbor : graph[curNode.id]){
                int nextID = (int)neighbor[0];
                double nextProp = curNode.prop * neighbor[1];
                if(nextProp > res[nextID]){
                    res[nextID] = nextProp;
                    pq.offer(new State(nextID, nextProp));
                }

            }
        }

        return res[end];
    }
}

class State{
    int id;
    double prop;

    State(int id, double prop){
        this.id = id;
        this.prop = prop;
    }
}
```
