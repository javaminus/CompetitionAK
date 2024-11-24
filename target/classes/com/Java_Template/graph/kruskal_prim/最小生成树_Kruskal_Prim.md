1584\. 连接所有点的最小费用
-----------------

给你一个`points` 数组，表示 2D 平面上的一些点，其中 `points[i] = [xi, yi]` 。

连接点 `[xi, yi]` 和点 `[xj, yj]` 的费用为它们之间的 **曼哈顿距离** ：`|xi - xj| + |yi - yj|` ，其中 `|val|` 表示 `val` 的绝对值。

请你返回将所有点连接的最小总费用。只有任意两点之间 **有且仅有** 一条简单路径时，才认为所有点都已连接。

**示例 1：**

![](https://assets.leetcode.com/uploads/2020/08/26/d.png)

**输入：**points = \[\[0,0\],\[2,2\],\[3,10\],\[5,2\],\[7,0\]\]
**输出：**20
**解释：**
![](https://assets.leetcode.com/uploads/2020/08/26/c.png)
我们可以按照上图所示连接所有点得到最小总费用，总费用为 20 。
注意到任意两个点之间只有唯一一条路径互相到达。

**示例 2：**

**输入：**points = \[\[3,12\],\[-2,5\],\[-4,1\]\]
**输出：**18

**示例 3：**

**输入：**points = \[\[0,0\],\[1,1\],\[1,0\],\[-1,1\]\]
**输出：**4

**示例 4：**

**输入：**points = \[\[-1000000,-1000000\],\[1000000,1000000\]\]
**输出：**4000000

**示例 5：**

**输入：**points = \[\[0,0\]\]
**输出：**0

**提示：**

*   `1 <= points.length <= 1000`
*   `-106 <= xi, yi <= 106`
*   所有点 `(xi, yi)` 两两不同。

[https://leetcode.cn/problems/min-cost-to-connect-all-points/description/](https://leetcode.cn/problems/min-cost-to-connect-all-points/description/)

```java
import java.util.PriorityQueue;

class Solution { // kruskal
    public int minCostConnectPoints(int[][] points) {
        int ans = 0;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[2] - b[2]);
        int n = points.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int cost = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                pq.offer(new int[]{i, j, cost});
            }
        }
        UnionFind uf = new UnionFind(n);
        while (uf.getCount() > 1) {
            int[] edge = pq.poll();
            int p0 = edge[0], p1 = edge[1], cost = edge[2];
            if (uf.find(p0) != uf.find(p1)) {
                ans += cost;
                uf.union(p0, p1);
            }
        }
        return ans;
    }
}

class UnionFind{
    private int[] parent;
    private int[] rank;
    private int count;

    public UnionFind(int n) { // 初始化并查集
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        rank = new int[n];
        this.count = n;
    }

    public void union(int x, int y) {
        int rootx = find(x);
        int rooty = find(y);
        if (rootx != rooty) {
            if (rank[rootx] > rank[rooty]) {
                parent[rooty] = rootx;
            } else if (rank[rootx] < rank[rooty]) {
                parent[rootx] = rooty;
            }else{
                parent[rooty] = rootx;
                rank[rootx]++;
            }
            count--;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public int getCount() {
        return count;
    }


}
```

```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution { // Prim
    public int minCostConnectPoints(int[][] points) {
        int ans = 0;
        int n = points.length;
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int cost = Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1]);
                g[i][j] = g[j][i] = cost;
            }
        }
        boolean[] visited = new boolean[n];
        visited[0] = true;
        int[] costs = new int[n];
        Arrays.fill(costs, Integer.MAX_VALUE);
        costs[0] = 0;
        for (int i = 1; i < n; i++) {
            costs[i] = g[0][i];
        }
        for (int i = 1; i < n; i++) {
            int minIndex = -1;
            int minCost = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (visited[j]) {
                    continue;
                }
                if (minCost > costs[j]) {
                    minIndex = j;
                    minCost = costs[j];
                }
            }
            ans += minCost;
            visited[minIndex] = true;
            costs[minIndex] = 0;
            for (int j = 0; j < n; j++) {
                if (!visited[j]) {
                    costs[j] = Math.min(costs[j], g[minIndex][j]);
                }
            }
        }
        return ans;
    }
}
```

1489\. 找到最小生成树里的关键边和伪关键边
------------------------

给你一个 `n` 个点的带权无向连通图，节点编号为 `0` 到 `n-1` ，同时还有一个数组 `edges` ，其中 `edges[i] = [from``i, toi, weighti]` 表示在 `fromi` 和 `toi` 节点之间有一条带权无向边。最小生成树 (MST) 是给定图中边的一个子集，它连接了所有节点且没有环，而且这些边的权值和最小。

请你找到给定图中最小生成树的所有关键边和伪关键边。如果从图中删去某条边，会导致最小生成树的权值和增加，那么我们就说它是一条关键边。伪关键边则是可能会出现在某些最小生成树中但不会出现在所有最小生成树中的边。

请注意，你可以分别以任意顺序返回关键边的下标和伪关键边的下标。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/21/ex1.png)

**输入：**n = 5, edges = \[\[0,1,1\],\[1,2,1\],\[2,3,2\],\[0,3,2\],\[0,4,3\],\[3,4,3\],\[1,4,6\]\]
**输出：**\[\[0,1\],\[2,3,4,5\]\]
**解释：**上图描述了给定图。
下图是所有的最小生成树。
![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/21/msts.png)
注意到第 0 条边和第 1 条边出现在了所有最小生成树中，所以它们是关键边，我们将这两个下标作为输出的第一个列表。
边 2，3，4 和 5 是所有 MST 的剩余边，所以它们是伪关键边。我们将它们作为输出的第二个列表。

**示例 2 ：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/21/ex2.png)

**输入：**n = 4, edges = \[\[0,1,1\],\[1,2,1\],\[2,3,1\],\[0,3,1\]\]
**输出：**\[\[\],\[0,1,2,3\]\]
**解释：**可以观察到 4 条边都有相同的权值，任选它们中的 3 条可以形成一棵 MST 。所以 4 条边都是伪关键边。

**提示：**

*   `2 <= n <= 100`
*   `1 <= edges.length <= min(200, n * (n - 1) / 2)`
*   `edges[i].length == 3`
*   `0 <= fromi < toi < n`
*   `1 <= weighti <= 1000`
*   所有 `(fromi, toi)` 数对都是互不相同的。

[https://leetcode.cn/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/description/](https://leetcode.cn/problems/find-critical-and-pseudo-critical-edges-in-minimum-spanning-tree/description/)

```java
class Solution {
    public List<List<Integer>> findCriticalAndPseudoCriticalEdges(int n, int[][] edges) {
        List<Integer> criticalEdges = new ArrayList<Integer>();
        List<Integer> pseudoCriticalEdges = new ArrayList<Integer>();
        int m = edges.length;
        int[][] edgesIndices = new int[m][4];
        for (int i = 0; i < m; i++) {
            System.arraycopy(edges[i], 0, edgesIndices[i], 0, 3);
            edgesIndices[i][3] = i;
        }
        Arrays.sort(edgesIndices, (a, b) -> a[2] - b[2]);
        boolean[] included = new boolean[m];
        Arrays.fill(included, true);
        int mstWeight = kruskal(n, edgesIndices, included, -1);
        for (int i = 0; i < m; i++) {
            int index = edgesIndices[i][3];
            included[i] = false;
            if (kruskal(n, edgesIndices, included, -1) > mstWeight) {
                criticalEdges.add(index);
            } else if (kruskal(n, edgesIndices, included, i) == mstWeight) {
                pseudoCriticalEdges.add(index);
            }
            included[i] = true;
        }
        List<List<Integer>> criticalAndPseudoCriticalEdges = new ArrayList<List<Integer>>();
        criticalAndPseudoCriticalEdges.add(criticalEdges);
        criticalAndPseudoCriticalEdges.add(pseudoCriticalEdges);
        return criticalAndPseudoCriticalEdges;
    }

    public int kruskal(int n, int[][] edgesIndices, boolean[] included, int startEdgeIndex) {
        int mstWeight = 0;
        UnionFind uf = new UnionFind(n);
        if (startEdgeIndex >= 0) {
            int[] startEdge = edgesIndices[startEdgeIndex];
            mstWeight += startEdge[2];
            uf.union(startEdge[0], startEdge[1]);
        }
        int m = edgesIndices.length;
        for (int i = 0; i < m && uf.getCount() > 1; i++) {
            if (!included[i]) {
                continue;
            }
            int[] edge = edgesIndices[i];
            int vertex0 = edge[0], vertex1 = edge[1], weight = edge[2];
            if (uf.find(vertex0) != uf.find(vertex1)) {
                mstWeight += weight;
                uf.union(vertex0, vertex1);
            }
        }
        return uf.getCount() == 1 ? mstWeight : Integer.MAX_VALUE;
    }
}

class UnionFind {
    private int[] parent;
    private int[] rank;
    private int count;

    public UnionFind(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        rank = new int[n];
        this.count = n;
    }

    public void union(int x, int y) {
        int rootx = find(x);
        int rooty = find(y);
        if (rootx != rooty) {
            if (rank[rootx] > rank[rooty]) {
                parent[rooty] = rootx;
            } else if (rank[rootx] < rank[rooty]) {
                parent[rootx] = rooty;
            } else {
                parent[rooty] = rootx;
                rank[rootx]++;
            }
            count--;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public int getCount() {
        return count;
    }
}
```

