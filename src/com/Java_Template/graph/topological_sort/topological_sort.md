310\. 最小高度树
-----------

树是一个无向图，其中任何两个顶点只通过一条路径连接。 换句话说，任何一个没有简单环路的连通图都是一棵树。

给你一棵包含 `n` 个节点的树，标记为 `0` 到 `n - 1` 。给定数字 `n` 和一个有 `n - 1` 条无向边的 `edges` 列表（每一个边都是一对标签），其中 `edges[i] = [ai, bi]` 表示树中节点 `ai` 和 `bi` 之间存在一条无向边。

可选择树中任何一个节点作为根。当选择节点 `x` 作为根节点时，设结果树的高度为 `h` 。在所有可能的树中，具有最小高度的树（即，`min(h)`）被称为 **最小高度树** 。

请你找到所有的 **最小高度树** 并按 **任意顺序** 返回它们的根节点标签列表。

树的 **高度** 是指根节点和叶子节点之间最长向下路径上边的数量。

**示例 1：**

![](https://assets.leetcode.com/uploads/2020/09/01/e1.jpg)

**输入：**n = 4, edges = \[\[1,0\],\[1,2\],\[1,3\]\]
**输出：**\[1\]
**解释：**如图所示，当根是标签为 1 的节点时，树的高度是 1 ，这是唯一的最小高度树。

**示例 2：**

![](https://assets.leetcode.com/uploads/2020/09/01/e2.jpg)

**输入：**n = 6, edges = \[\[3,0\],\[3,1\],\[3,2\],\[3,4\],\[5,4\]\]
**输出：**\[3,4\]

**提示：**

*   `1 <= n <= 2 * 104`
*   `edges.length == n - 1`
*   `0 <= ai, bi < n`
*   `ai != bi`
*   所有 `(ai, bi)` 互不相同
*   给定的输入 **保证** 是一棵树，并且 **不会有重复的边**

[https://leetcode.cn/problems/minimum-height-trees/description/](https://leetcode.cn/problems/minimum-height-trees/description/)

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        ArrayList<Integer> ans = new ArrayList<>();
        if (n == 1) {
            ans.add(0);
            return ans;
        }
        // 类似拓扑排序
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        int[] degree = new int[n];
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
            degree[x]++;
            degree[y]++;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                queue.offer(i);
            }
        }
        int cntNodeNumber = n;
        while (cntNodeNumber > 2) {
            int size = queue.size();
            cntNodeNumber -= size;
            for (int i = 0; i < size; i++) {
                int x = queue.poll();
                for (int y : g[x]) {
                    degree[y]--;
                    if (degree[y] == 1) {
                        queue.offer(y);
                    }
                }
            }
        }
        while (!queue.isEmpty()) {
            ans.add(queue.poll());
        }
        return ans;
    }
}
```

2192\. 有向无环图中一个节点的所有祖先
----------------------

给你一个正整数 `n` ，它表示一个 **有向无环图** 中节点的数目，节点编号为 `0` 到 `n - 1` （包括两者）。

给你一个二维整数数组 `edges` ，其中 `edges[i] = [fromi, toi]` 表示图中一条从 `fromi` 到 `toi` 的单向边。

请你返回一个数组 `answer`，其中 `answer[i]`是第 `i` 个节点的所有 **祖先** ，这些祖先节点 **升序** 排序。

如果 `u` 通过一系列边，能够到达 `v` ，那么我们称节点 `u` 是节点 `v` 的 **祖先** 节点。

**示例 1：**

![](https://assets.leetcode.com/uploads/2019/12/12/e1.png)

**输入：**n = 8, edgeList = \[\[0,3\],\[0,4\],\[1,3\],\[2,4\],\[2,7\],\[3,5\],\[3,6\],\[3,7\],\[4,6\]\]
**输出：**\[\[\],\[\],\[\],\[0,1\],\[0,2\],\[0,1,3\],\[0,1,2,3,4\],\[0,1,2,3\]\]
**解释：**
上图为输入所对应的图。
- 节点 0 ，1 和 2 没有任何祖先。
- 节点 3 有 2 个祖先 0 和 1 。
- 节点 4 有 2 个祖先 0 和 2 。
- 节点 5 有 3 个祖先 0 ，1 和 3 。
- 节点 6 有 5 个祖先 0 ，1 ，2 ，3 和 4 。
- 节点 7 有 4 个祖先 0 ，1 ，2 和 3 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2019/12/12/e2.png)

**输入：**n = 5, edgeList = \[\[0,1\],\[0,2\],\[0,3\],\[0,4\],\[1,2\],\[1,3\],\[1,4\],\[2,3\],\[2,4\],\[3,4\]\]
**输出：**\[\[\],\[0\],\[0,1\],\[0,1,2\],\[0,1,2,3\]\]
**解释：**
上图为输入所对应的图。
- 节点 0 没有任何祖先。
- 节点 1 有 1 个祖先 0 。
- 节点 2 有 2 个祖先 0 和 1 。
- 节点 3 有 3 个祖先 0 ，1 和 2 。
- 节点 4 有 4 个祖先 0 ，1 ，2 和 3 。

**提示：**

*   `1 <= n <= 1000`
*   `0 <= edges.length <= min(2000, n * (n - 1) / 2)`
*   `edges[i].length == 2`
*   `0 <= fromi, toi <= n - 1`
*   `fromi != toi`
*   图中不会有重边。
*   图是 **有向** 且 **无环** 的。

[https://leetcode.cn/problems/all-ancestors-of-a-node-in-a-directed-acyclic-graph/description/](https://leetcode.cn/problems/all-ancestors-of-a-node-in-a-directed-acyclic-graph/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> getAncestors(int n, int[][] edges){
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[y].add(x);
        }
        List[] ans = new List[n];
        Arrays.setAll(ans, e -> new ArrayList<Integer>());
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(visited, false);
            dfs(g, visited, i);
            visited[i] = false;
            for (int j = 0; j < n; j++) {
                if (visited[j]) {
                    ans[i].add(j);
                }
            }
        }
        return Arrays.asList(ans);
    }

    private void dfs(List<Integer>[] g, boolean[] visited, int x) {
        visited[x] = true;
        for (int y : g[x]) {
            if (!visited[y]) {
                dfs(g, visited, y);
            }
        }
    }
}
```

