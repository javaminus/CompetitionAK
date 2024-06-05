> 如果一个图是连通且无环，那么拓扑排序后的序列长度一定等于这个图的大小，当一个图有环，那么就一定有节点无法被遍历，因为总是存在点的入度永远大于0.

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

```java
class Solution {
    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        Set<Integer>[] anc = new Set[n];   // 存储每个节点祖先的辅助数组
        for (int i = 0; i < n; ++i) {
            anc[i] = new HashSet<Integer>();
        }
        List<Integer>[] e = new List[n];   // 邻接表
        for (int i = 0; i < n; ++i) {
            e[i] = new ArrayList<Integer>();
        }
        int[] indeg = new int[n];   // 入度表
        // 预处理
        for (int[] edge : edges) {
            e[edge[0]].add(edge[1]);
            ++indeg[edge[1]];
        }
        // 广度优先搜索求解拓扑排序
        Queue<Integer> q = new ArrayDeque<Integer>();
        for (int i = 0; i < n; ++i) {
            if (indeg[i] == 0) {
                q.offer(i);
            }
        }
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : e[u]) {
                // 更新子节点的祖先哈希表
                anc[v].add(u);
                for (int i : anc[u]) {
                    anc[v].add(i);
                }
                --indeg[v];
                if (indeg[v] == 0) {
                    q.offer(v);
                }
            }
        }
        // 转化为答案数组
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        for (int i = 0; i < n; ++i) {
            res.add(new ArrayList<Integer>());
            for (int j : anc[i]) {
                res.get(i).add(j);
            }
            Collections.sort(res.get(i));
        }
        return res;
    }
}
```

1203\. 项目管理（分组拓扑排序）
-----------

有 `n` 个项目，每个项目或者不属于任何小组，或者属于 `m` 个小组之一。`group[i]` 表示第 `i` 个项目所属的小组，如果第 `i` 个项目不属于任何小组，则 `group[i]` 等于 `-1`。项目和小组都是从零开始编号的。可能存在小组不负责任何项目，即没有任何项目属于这个小组。

请你帮忙按要求安排这些项目的进度，并返回排序后的项目列表：

*   同一小组的项目，排序后在列表中彼此相邻。
*   项目之间存在一定的依赖关系，我们用一个列表 `beforeItems` 来表示，其中 `beforeItems[i]` 表示在进行第 `i` 个项目前（位于第 `i` 个项目左侧）应该完成的所有项目。

如果存在多个解决方案，只需要返回其中任意一个即可。如果没有合适的解决方案，就请返回一个 **空列表** 。

**示例 1：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2019/09/22/1359_ex1.png)**

**输入：**n = 8, m = 2, group = \[-1,-1,1,0,0,1,0,-1\], beforeItems = \[\[\],\[6\],\[5\],\[6\],\[3,6\],\[\],\[\],\[\]\]
**输出：**\[6,3,4,1,5,2,0,7\]

**示例 2：**

**输入：**n = 8, m = 2, group = \[-1,-1,1,0,0,1,0,-1\], beforeItems = \[\[\],\[6\],\[5\],\[6\],\[3\],\[\],\[4\],\[\]\]
**输出：**\[\]
**解释：**与示例 1 大致相同，但是在排序后的列表中，4 必须放在 6 的前面。

**提示：**

*   `1 <= m <= n <= 3 * 104`
*   `group.length == beforeItems.length == n`
*   `-1 <= group[i] <= m - 1`
*   `0 <= beforeItems[i].length <= n - 1`
*   `0 <= beforeItems[i][j] <= n - 1`
*   `i != beforeItems[i][j]`
*   `beforeItems[i]` 不含重复元素

[https://leetcode.cn/problems/sort-items-by-groups-respecting-dependencies/description/](https://leetcode.cn/problems/sort-items-by-groups-respecting-dependencies/description/)

```java
import java.util.*;

class Solution {
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        for (int i = 0; i < n; i++) {
            if (group[i] < 0) {
                group[i] = m++;
            }
        }
        List<Integer>[] groupItem = new List[m]; // m 个组，存储组中的节点，存储组和节点的关系
        Arrays.setAll(groupItem, e -> new ArrayList<Integer>());
        int[] groupIndegree = new int[m]; // 存储组的入度
        int[] itemIndegree = new int[n]; // 存储节点的入度
        List<Integer>[] groupNextArr = new List[m]; // 存储下一个组，存储组与组的关系
        Arrays.setAll(groupNextArr, e -> new ArrayList<Integer>());
        List<Integer>[] itemNextArr = new List[n]; // 存储下一个节点，存储节点与节点的关系
        Arrays.setAll(itemNextArr, e -> new ArrayList<Integer>());
        for (int i = 0; i < n; i++) {
            int curGroup = group[i]; // 当前组号
            groupItem[curGroup].add(i);
            for (int j : beforeItems.get(i)) { // 遍历组内的前辈
                int preGroup = group[j];
                if (preGroup == curGroup) { // 如果组号相同
                    itemIndegree[i]++;
                    itemNextArr[j].add(i);
                }else{
                    groupIndegree[curGroup]++;
                    groupNextArr[preGroup].add(curGroup);
                }
            }
        }
        List<Integer> groupList = new ArrayList<Integer>(); // 组号集合
        for (int i = 0; i < m; i++) {
            groupList.add(i);
        }
        int[] groupOrder = topSort(groupIndegree, groupNextArr, groupList);
        if (groupOrder.length != groupList.size()) {
            return new int[0];
        }
        int[] itemOrder = new int[n];
        int itemIndex = 0;
        for (int i = 0; i < m; i++) {
            List<Integer> items = groupItem[groupOrder[i]]; // 当前组内的所有节点
            int[] groupItemOrder = topSort(itemIndegree, itemNextArr, items);
            int currCount = groupItemOrder.length;
            if (currCount != items.size()) {
                return new int[0];
            }
            System.arraycopy(groupItemOrder, 0, itemOrder, itemIndex, currCount);
            itemIndex += currCount;
        }
        return itemOrder;
    }

    /**
     * @param indegrees 节点的入度
     * @param g 邻接表
     * @param nums 需要排序的节点集合
     * @return int[] 返回拓扑排序的节点顺序
     */
    private int[] topSort(int[] indegrees, List<Integer>[] g,List<Integer> nums) {
        int n = nums.size();
        int[] ans = new int[n];
        Queue<Integer> queue = new LinkedList<>();
        for (int num : nums) {
            if (indegrees[num] == 0) {
                queue.offer(num);
            }
        }
        int index = 0;
        while (!queue.isEmpty()) {
            int poll = queue.poll();
            ans[index++] = poll;
            for (int y : g[poll]) {
                indegrees[y]--;
                if (indegrees[y] == 0) {
                    queue.offer(y);
                }
            }
        }
        return index == n ? ans : new int[0];
    }
}
```

2603\. 收集树中金币
-------------

给你一个 `n` 个节点的无向无根树，节点编号从 `0` 到 `n - 1` 。给你整数 `n` 和一个长度为 `n - 1` 的二维整数数组 `edges` ，其中 `edges[i] = [ai, bi]` 表示树中节点 `ai` 和 `bi` 之间有一条边。再给你一个长度为 `n` 的数组 `coins` ，其中 `coins[i]` 可能为 `0` 也可能为 `1` ，`1` 表示节点 `i` 处有一个金币。

一开始，你需要选择树中任意一个节点出发。你可以执行下述操作任意次：

*   收集距离当前节点距离为 `2` 以内的所有金币，或者
*   移动到树中一个相邻节点。

你需要收集树中所有的金币，并且回到出发节点，请你返回最少经过的边数。

如果你多次经过一条边，每一次经过都会给答案加一。

**示例 1：**

![](https://assets.leetcode.com/uploads/2023/03/01/graph-2.png)

**输入：**coins = \[1,0,0,0,0,1\], edges = \[\[0,1\],\[1,2\],\[2,3\],\[3,4\],\[4,5\]\]
**输出：**2
**解释：**从节点 2 出发，收集节点 0 处的金币，移动到节点 3 ，收集节点 5 处的金币，然后移动回节点 2 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2023/03/02/graph-4.png)

**输入：**coins = \[0,0,0,1,1,0,0,1\], edges = \[\[0,1\],\[0,2\],\[1,3\],\[1,4\],\[2,5\],\[5,6\],\[5,7\]\]
**输出：**2
**解释：**从节点 0 出发，收集节点 4 和 3 处的金币，移动到节点 2 处，收集节点 7 处的金币，移动回节点 0 。

**提示：**

*   `n == coins.length`
*   `1 <= n <= 3 * 104`
*   `0 <= coins[i] <= 1`
*   `edges.length == n - 1`
*   `edges[i].length == 2`
*   `0 <= ai, bi < n`
*   `ai != bi`
*   `edges` 表示一棵合法的树。

[https://leetcode.cn/problems/collect-coins-in-a-tree/solutions/2191371/tuo-bu-pai-xu-ji-lu-ru-dui-shi-jian-pyth-6uli/](https://leetcode.cn/problems/collect-coins-in-a-tree/solutions/2191371/tuo-bu-pai-xu-ji-lu-ru-dui-shi-jian-pyth-6uli/)

```java
import java.util.*;

class Solution {
    public int collectTheCoins(int[] coins, int[][] edges) {
        int n = coins.length;
        List<Integer> g[] = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        int[] deg = new int[n];
        for (int[] e : edges) {
            int x = e[0], y = e[1];
            g[x].add(y);
            g[y].add(x); // 建图
            deg[x]++;
            deg[y]++; // 统计每个节点的度数（邻居个数）
        }

        int leftEdges = n - 1; // 剩余边数
        // 拓扑排序，去掉没有金币的子树
        Queue<Integer> q = new ArrayDeque<Integer>();
        for (int i = 0; i < n; i++) {
            if (deg[i] == 1 && coins[i] == 0) { // 没有金币的叶子
                q.add(i);
            }
        }
        while (!q.isEmpty()) {
            leftEdges--; // 删除节点到其父节点的边
            for (int y : g[q.poll()]) {
                if (--deg[y] == 1 && coins[y] == 0) { // 没有金币的叶子
                    q.add(y);
                }
            }
        }

        // 再次拓扑排序
        for (int i = 0; i < n; i++) {
            if (deg[i] == 1 && coins[i] == 1) { // 有金币的叶子（判断 coins[i] 是避免把没有金币的叶子也算进来）
                q.add(i);
            }
        }
        leftEdges -= q.size(); // 删除所有叶子（到其父节点的边）
        for (int x : q) { // 遍历所有叶子
            for (int y : g[x]) {
                if (--deg[y] == 1) { // y 现在是叶子了
                    leftEdges--; // 删除 y（到其父节点的边）
                }
            }
        }
        return Math.max(leftEdges * 2, 0);
    }
}
```

