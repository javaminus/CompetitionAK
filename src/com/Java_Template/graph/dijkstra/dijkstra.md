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

1631\. 最小体力消耗路径
---------------

你准备参加一场远足活动。给你一个二维 `rows x columns` 的地图 `heights` ，其中 `heights[row][col]` 表示格子 `(row, col)` 的高度。一开始你在最左上角的格子 `(0, 0)` ，且你希望去最右下角的格子 `(rows-1, columns-1)` （注意下标从 **0** 开始编号）。你每次可以往 **上**，**下**，**左**，**右** 四个方向之一移动，你想要找到耗费 **体力** 最小的一条路径。

一条路径耗费的 **体力值** 是路径上相邻格子之间 **高度差绝对值** 的 **最大值** 决定的。

请你返回从左上角走到右下角的最小 **体力消耗值** 。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/10/25/ex1.png)

**输入：**heights = \[\[1,2,2\],\[3,8,2\],\[5,3,5\]\]
**输出：**2
**解释：**路径 \[1,3,5,3,5\] 连续格子的差值绝对值最大为 2 。
这条路径比路径 \[1,2,2,2,5\] 更优，因为另一条路径差值最大值为 3 。

**示例 2：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/10/25/ex2.png)

**输入：**heights = \[\[1,2,3\],\[3,8,4\],\[5,3,5\]\]
**输出：**1
**解释：**路径 \[1,2,3,4,5\] 的相邻格子差值绝对值最大为 1 ，比路径 \[1,3,5,3,5\] 更优。

**示例 3：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/10/25/ex3.png)

**输入：**heights = \[\[1,2,1,1,1\],\[1,2,1,2,1\],\[1,2,1,2,1\],\[1,2,1,2,1\],\[1,1,1,2,1\]\]
**输出：**0
**解释：**上图所示路径不需要消耗任何体力。

**提示：**

*   `rows == heights.length`
*   `columns == heights[i].length`
*   `1 <= rows, columns <= 100`
*   `1 <= heights[i][j] <= 106`

[https://leetcode.cn/problems/path-with-minimum-effort/description/](https://leetcode.cn/problems/path-with-minimum-effort/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.minimumEffortPath(new int[][]{{4, 3, 4, 10, 5, 5, 9, 2}, {10, 8, 2, 10, 9, 7, 5, 6}, {5, 8, 10, 10, 10, 7, 4, 2}, {5, 1, 3, 1, 1, 3, 1, 9}, {6, 4, 10, 6, 10, 9, 4, 6}});
    }
    
    public int minimumEffortPath(int[][] heights) {
        int m = heights.length, n = heights[0].length;
        List<int[]>[] g = new List[m * n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 0; i < m; i++) { // 建图
            for (int j = 0; j < n; j++) {
                if (j + 1 < n) {
                    g[n * i + j].add(new int[]{n * i + j + 1, Math.abs(heights[i][j] - heights[i][j + 1])});
                }
                if (j > 0) {
                    g[n * i + j].add(new int[]{n * i + j - 1, Math.abs(heights[i][j] - heights[i][j - 1])});
                }
                if (i + 1 < m) {
                    g[n * i + j].add(new int[]{n * (i + 1) + j, Math.abs(heights[i][j] - heights[i + 1][j])});
                }
                if (i > 0) {
                    g[n * i + j].add(new int[]{n * (i - 1) + j, Math.abs(heights[i][j] - heights[i - 1][j])});
                }
            }
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        int[] dist = new int[m * n];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        dist[0] = 0;
        pq.offer(new int[]{0, 0});
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int x = poll[0], d = poll[1];
            if (dist[x] < d) {
                continue;
            }
            for (int[] e : g[x]) {
                int y = e[0], d1 = Math.max(dist[x], e[1]);
                if (dist[y] >  d1) {
                    dist[y] = d1;
                    pq.offer(new int[]{y, d1});
                }
            }
        }
        // for (int i = 0; i < m * n; i++) {
        //     System.out.println(i + ":" + dist[i]);
        // }
        return dist[m * n - 1];
    }
    // [[4,3,4,10,5,5,9,2],
    // [10,8,2,10,9,7,5,6],
    // [5,8,10,10,10,7,4,2],
    // [5,1,3,1,1,3,1,9],
    // [6,4,10,6,10,9,4,6]]
}
```

1368\. 使网格图至少有一条有效路径的最小代价
-------------------------

给你一个 m x n 的网格图 `grid` 。 `grid` 中每个格子都有一个数字，对应着从该格子出发下一步走的方向。 `grid[i][j]` 中的数字可能为以下几种情况：

*   **1** ，下一步往右走，也就是你会从 `grid[i][j]` 走到 `grid[i][j + 1]`
*   **2** ，下一步往左走，也就是你会从 `grid[i][j]` 走到 `grid[i][j - 1]`
*   **3** ，下一步往下走，也就是你会从 `grid[i][j]` 走到 `grid[i + 1][j]`
*   **4** ，下一步往上走，也就是你会从 `grid[i][j]` 走到 `grid[i - 1][j]`

注意网格图中可能会有 **无效数字** ，因为它们可能指向 `grid` 以外的区域。

一开始，你会从最左上角的格子 `(0,0)` 出发。我们定义一条 **有效路径** 为从格子 `(0,0)` 出发，每一步都顺着数字对应方向走，最终在最右下角的格子 `(m - 1, n - 1)` 结束的路径。有效路径 **不需要是最短路径** 。

你可以花费 `cost = 1` 的代价修改一个格子中的数字，但每个格子中的数字 **只能修改一次** 。

请你返回让网格图至少有一条有效路径的最小代价。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/02/29/grid1.png)

**输入：**grid = \[\[1,1,1,1\],\[2,2,2,2\],\[1,1,1,1\],\[2,2,2,2\]\]
**输出：**3
**解释：**你将从点 (0, 0) 出发。
到达 (3, 3) 的路径为： (0, 0) --> (0, 1) --> (0, 2) --> (0, 3) 花费代价 cost = 1 使方向向下 --> (1, 3) --> (1, 2) --> (1, 1) --> (1, 0) 花费代价 cost = 1 使方向向下 --> (2, 0) --> (2, 1) --> (2, 2) --> (2, 3) 花费代价 cost = 1 使方向向下 --> (3, 3)
总花费为 cost = 3.

**示例 2：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/02/29/grid2.png)

**输入：**grid = \[\[1,1,3\],\[3,2,2\],\[1,1,4\]\]
**输出：**0
**解释：**不修改任何数字你就可以从 (0, 0) 到达 (2, 2) 。

**示例 3：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/02/29/grid3.png)

**输入：**grid = \[\[1,2\],\[4,3\]\]
**输出：**1

**示例 4：**

**输入：**grid = \[\[2,2,2\],\[2,2,2\]\]
**输出：**3

**示例 5：**

**输入：**grid = \[\[4\]\]
**输出：**0

**提示：**

*   `m == grid.length`
*   `n == grid[i].length`
*   `1 <= m, n <= 100`

[https://leetcode.cn/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/description/](https://leetcode.cn/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/description/)

```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    private static int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] cost = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(cost[i], Integer.MAX_VALUE);
        }
        cost[0][0] = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        pq.offer(new int[]{0, 0, 0});
        while (!pq.isEmpty()) {
            int[] poll = pq.poll();
            int row = poll[0], col = poll[1], c = poll[2];
            if (cost[row][col] < c) {
                continue;
            }
            for (int i = 0; i < 4; i++) {
                int[] dir = dirs[i];
                int newRow = row + dir[0], newCol = col + dir[1];
                int newC = c + (grid[row][col] == i + 1 ? 0 : 1);
                if (newRow >= 0 && newCol >= 0 && newRow < m && newCol < n && newC < cost[newRow][newCol]) {
                    cost[newRow][newCol] = newC;
                    pq.offer(new int[]{newRow, newCol, newC});
                }
            }
        }
        return cost[m - 1][n - 1];
    }
}
```

2662\. 前往目标的最小代价
----------------

给你一个数组 `start` ，其中 `start = [startX, startY]` 表示你的初始位置位于二维空间上的 `(startX, startY)` 。另给你一个数组 `target` ，其中 `target = [targetX, targetY]` 表示你的目标位置 `(targetX, targetY)` 。

从位置 `(x1, y1)` 到空间中任一其他位置 `(x2, y2)` 的 **代价** 是 `|x2 - x1| + |y2 - y1|` 。

给你一个二维数组 `specialRoads` ，表示空间中存在的一些 **特殊路径**。其中 `specialRoads[i] = [x1i, y1i, x2i, y2i, costi]` 表示第 `i` 条特殊路径可以从 `(x1i, y1i)` 到 `(x2i, y2i)` ，但成本等于 `costi` 。你可以使用每条特殊路径任意次数。

返回从 `(startX, startY)` 到 `(targetX, targetY)` 所需的 **最小** 代价。

**示例 1：**

**输入：**start = \[1,1\], target = \[4,5\], specialRoads = \[\[1,2,3,3,2\],\[3,4,4,5,1\]\]

**输出：**5

**解释：**

1.  (1,1) 到 (1,2) 花费为 |1 - 1| + |2 - 1| = 1。
2.  (1,2) 到 (3,3)。使用 `specialRoads[0]` 花费为 2。
3.  (3,3) 到 (3,4) 花费为 |3 - 3| + |4 - 3| = 1。
4.  (3,4) 到 (4,5)。使用 `specialRoads[1]` 花费为 1。

所以总花费是 1 + 2 + 1 + 1 = 5。

**示例 2：**

**输入：**start = \[3,2\], target = \[5,7\], specialRoads = \[\[5,7,3,2,1\],\[3,2,3,4,4\],\[3,3,5,5,5\],\[3,4,5,6,6\]\]

**输出：**7

**解释：**

不使用任何特殊路径，直接从开始到结束位置是最优的，花费为 |5 - 3| + |7 - 2| = 7。

注意 `specialRoads[0]` 直接从 (5,7) 到 (3,2)。

**示例 3：**

**输入：**start = \[1,1\], target = \[10,4\], specialRoads = \[\[4,2,1,1,3\],\[1,2,7,4,4\],\[10,3,6,1,2\],\[6,1,1,2,3\]\]

**输出：**8

**解释：**

1.  (1,1) 到 (1,2) 花费为 |1 - 1| + |2 - 1| = 1。
2.  (1,2) 到 (7,4)。使用 `specialRoads[1]` 花费为 4。
3.  (7,4) 到 (10,4) 花费为 |10 - 7| + |4 - 4| = 3。

**提示：**

*   `start.length == target.length == 2`
*   `1 <= startX <= targetX <= 105`
*   `1 <= startY <= targetY <= 105`
*   `1 <= specialRoads.length <= 200`
*   `specialRoads[i].length == 5`
*   `startX <= x1i, x2i <= targetX`
*   `startY <= y1i, y2i <= targetY`
*   `1 <= costi <= 105`

[https://leetcode.cn/problems/minimum-cost-of-a-path-with-special-roads/](https://leetcode.cn/problems/minimum-cost-of-a-path-with-special-roads/)

```java
import java.util.HashMap;
import java.util.PriorityQueue;

class Solution {
    public int minimumCost(int[] start, int[] target, int[][] specialRoads) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        int res = calDist(start, target);
        pq.offer(new int[]{start[0], start[1], 0});
        HashMap<String, Integer> map = new HashMap<>();
        map.put(getKey(start), 0);
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            for (int[] sp : specialRoads) {
                int[] p1 = new int[]{sp[0], sp[1]};
                int[] p2 = new int[]{sp[2], sp[3]};
                int d = p[2] + calDist(p, p1) + sp[4]; 
                String key = getKey(p2);
                int x = map.getOrDefault(key, calDist(start, p2));
                if (d < x) {
                    map.put(key, d);
                    pq.offer(new int[]{p2[0], p2[1], d});
                    res = Math.min(res, d + calDist(p2, target));
                }
            }
        }
        return res;
    }

    private int calDist(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }

    private String getKey(int[] p) {
        return p[0] + "_" + p[1];
    }
}
```

