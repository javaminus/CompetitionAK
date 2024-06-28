928\. 尽量减少恶意软件的传播 II
--------------------

给定一个由 `n` 个节点组成的网络，用 `n x n` 个邻接矩阵 `graph` 表示。在节点网络中，只有当 `graph[i][j] = 1` 时，节点 `i` 能够直接连接到另一个节点 `j`。

一些节点 `initial` 最初被恶意软件感染。只要两个节点直接连接，且其中至少一个节点受到恶意软件的感染，那么两个节点都将被恶意软件感染。这种恶意软件的传播将继续，直到没有更多的节点可以被这种方式感染。

假设 `M(initial)` 是在恶意软件停止传播之后，整个网络中感染恶意软件的最终节点数。

我们可以从 `initial` 中 **删除一个节点**，**并完全移除该节点以及从该节点到任何其他节点的任何连接。**

请返回移除后能够使 `M(initial)` 最小化的节点。如果有多个节点满足条件，返回索引 **最小的节点** 。

**示例 1：**

**输入：**graph = \[\[1,1,0\],\[1,1,0\],\[0,0,1\]\], initial = \[0,1\]
**输出：**0

**示例 2：**

**输入：**graph = \[\[1,1,0\],\[1,1,1\],\[0,1,1\]\], initial = \[0,1\]
**输出：**1

**示例 3：**

**输入：**graph = \[\[1,1,0,0\],\[1,1,1,0\],\[0,1,1,1\],\[0,0,1,1\]\], initial = \[0,1\]
**输出：**1

**提示：**

*   `n == graph.length`
*   `n == graph[i].length`
*   `2 <= n <= 300`
*   `graph[i][j]` 是 `0` 或 `1`.
*   `graph[i][j] == graph[j][i]`
*   `graph[i][i] == 1`
*   `1 <= initial.length < n`
*   `0 <= initial[i] <= n - 1`
*   `initial` 中每个整数都**不同**

[https://leetcode.cn/problems/minimize-malware-spread-ii/description/](https://leetcode.cn/problems/minimize-malware-spread-ii/description/)

```java
class Solution {
    private int nodeId, size;
    public int minMalwareSpread(int[][] graph, int[] initial) {
        // 就是找到只有一个被感染点的连通分量
        int n = graph.length;
        boolean[] visited = new boolean[n];
        boolean[] isInitial = new boolean[n];
        int mn = Integer.MAX_VALUE;
        for (int x : initial) {
            isInitial[x] = true;
            mn = Math.min(mn, x);
        }
        int[] cnt = new int[n];
        for (int i = 0; i < n; i++) {
            if (visited[i] || isInitial[i]) {
                continue;
            }
            nodeId = -1;
            size = 0;
            dfs(i, graph, visited, isInitial);
            if (nodeId >= 0) { // 这里的nodeId就是被感染的节点
                cnt[nodeId] += size;
            }
        }
        int maxCnt = 0, minNodeId = -1;
        for (int i = 0; i < n; i++) {
            if (cnt[i] > maxCnt) {
                maxCnt = cnt[i];
                minNodeId = i;
            }
        }
        return minNodeId == -1 ? mn : minNodeId;
    }

    private void dfs(int x, int[][] graph, boolean[] visited, boolean[] isInitial) {
        visited[x] = true;
        size++;
        for (int y = 0; y < graph.length; y++) {
            if (graph[x][y] == 0) {
                continue;
            }
            if (isInitial[y]) {
                if (nodeId != -2 && nodeId != y) {
                    nodeId = nodeId == -1 ? y : -2;
                }
            } else if (!visited[y]) {
                dfs(y, graph, visited, isInitial);
            }
        }
    }
}
```

1192\. 查找集群内的关键连接(tarjan模板题)
-----------------

力扣数据中心有 `n` 台服务器，分别按从 `0` 到 `n-1` 的方式进行了编号。它们之间以 **服务器到服务器** 的形式相互连接组成了一个内部集群，连接是无向的。用  `connections` 表示集群网络，`connections[i] = [a, b]` 表示服务器 `a` 和 `b` 之间形成连接。任何服务器都可以直接或者间接地通过网络到达任何其他服务器。

**关键连接** 是在该集群中的重要连接，假如我们将它移除，便会导致某些服务器无法访问其他服务器。

请你以任意顺序返回该集群内的所有 **关键连接** 。

**示例 1：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/original_images/critical-connections-in-a-network.png)**

**输入：**n = 4, connections = \[\[0,1\],\[1,2\],\[2,0\],\[1,3\]\]
**输出：**\[\[1,3\]\]
**解释：**\[\[3,1\]\] 也是正确的。

**示例 2:**

**输入：**n = 2, connections = \[\[0,1\]\]
**输出：**\[\[0,1\]\]

**提示：**

*   `2 <= n <= 105`
*   `n - 1 <= connections.length <= 105`
*   `0 <= ai, bi <= n - 1`
*   `ai != bi`
*   不存在重复的连接

[https://leetcode.cn/problems/critical-connections-in-a-network/description/](https://leetcode.cn/problems/critical-connections-in-a-network/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Solution {
    List<List<Integer>> ans = new ArrayList<>();
    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        // 移除环内的点是没有用的，只有移除非环上的点
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (List<Integer> edge : connections) {
            int x = edge.get(0), y = edge.get(1);
            g[x].add(y);
            g[y].add(x);
        }
        // 时间戳数组
        int[] times = new int[n];
        Arrays.fill(times, -1);
        // 选取一个点作为根节点，dfs向下递归，过程中识别出哪个边是critical connection
        dfs(0, -1, 0, times, g);
        return ans;
    }

    private int dfs(int x, int fa, int curTime, int[] times, List<Integer>[] g) { // 返回点x的时间戳
        times[x] = curTime;
        for (int y : g[x]) {
            if (y != fa) {
                if (times[y] == -1) { // 表示当前节点没有被访问
                    times[x] = Math.min(dfs(y, x, curTime + 1, times, g), times[x]);
                }else{
                    times[x] = Math.min(times[x], times[y]); // 遇到环了
                }
            }
        }
        // 核心
        if (times[x] == curTime && x != 0) {
            ans.add(Arrays.asList(x, fa));
        }
        return times[x]; // 这里不能返回curTime
    }
}
```

1568\. 使陆地分离的最少天数
-----------------

给你一个大小为 `m x n` ，由若干 `0` 和 `1` 组成的二维网格 `grid` ，其中 `1` 表示陆地， `0` 表示水。**岛屿** 由水平方向或竖直方向上相邻的 `1` （陆地）连接形成。

如果 **恰好只有一座岛屿** ，则认为陆地是 **连通的** ；否则，陆地就是 **分离的** 。

一天内，可以将 **任何单个** 陆地单元（`1`）更改为水单元（`0`）。

返回使陆地分离的最少天数。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/12/24/land1.jpg)

**输入：**grid = \[\[0,1,1,0\],\[0,1,1,0\],\[0,0,0,0\]\]
**输出：**2
**解释：**至少需要 2 天才能得到分离的陆地。
将陆地 grid\[1\]\[1\] 和 grid\[0\]\[2\] 更改为水，得到两个分离的岛屿。

**示例 2：**

![](https://assets.leetcode.com/uploads/2021/12/24/land2.jpg)

**输入：**grid = \[\[1,1\]\]
**输出：**2
**解释：**如果网格中都是水，也认为是分离的 (\[\[1,1\]\] -> \[\[0,0\]\])，0 岛屿。

**提示：**

*   `m == grid.length`
*   `n == grid[i].length`
*   `1 <= m, n <= 30`
*   `grid[i][j]` 为 `0` 或 `1`

[https://leetcode.cn/problems/minimum-number-of-days-to-disconnect-island/description/](https://leetcode.cn/problems/minimum-number-of-days-to-disconnect-island/description/)

```java
import java.util.Arrays;

class Solution {
    int size;
    private static int[][] directions = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    public int minDays(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        int cnt = 0;
        size = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j] && grid[i][j] == 1) {
                    dfs(i, j, grid, visited);
                    cnt++;
                }
            }
        }
        if (cnt != 1) {
            return 0;
        }
        // 等待天数最多为2天
        if (size == 1 || size == 3) {
            return 1;
        }
        if (size == 2) {
            return 2;
        }

        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                if (grid[x][y] == 1) {
                    for (int i = 0; i < m; i++) {
                        Arrays.fill(visited[i], false);
                    }
                    grid[x][y] = 0;
                    cnt = 0;
                    for (int i = 0; i < m; i++) {
                        for (int j = 0; j < n; j++) {
                            if (!visited[i][j] && grid[i][j] == 1) {
                                dfs(i, j, grid, visited);
                                cnt++;
                            }
                            if (cnt > 1) {
                                return 1;
                            }
                        }
                    }
                    grid[x][y] = 1;
                }
            }
        }
        return 2;
    }

    private void dfs(int i, int j, int[][] grid,boolean[][] visited){
        visited[i][j] = true;
        size++;
        for (int[] d : directions) {
            int newI = d[0] + i, newJ = d[1] + j;
            if (newI >= 0 && newI < grid.length && newJ >= 0 && newJ < grid[0].length) {
                if (grid[newI][newJ] == 1 && !visited[newI][newJ]) {
                    dfs(newI, newJ, grid, visited);
                }
            }
        }
    }
}
```

