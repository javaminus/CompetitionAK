2127\. 参加会议的最多员工数
-----------------

一个公司准备组织一场会议，邀请名单上有 `n` 位员工。公司准备了一张 **圆形** 的桌子，可以坐下 **任意数目** 的员工。

员工编号为 `0` 到 `n - 1` 。每位员工都有一位 **喜欢** 的员工，每位员工 **当且仅当** 他被安排在喜欢员工的旁边，他才会参加会议。每位员工喜欢的员工 **不会** 是他自己。

给你一个下标从 **0** 开始的整数数组 `favorite` ，其中 `favorite[i]` 表示第 `i` 位员工喜欢的员工。请你返回参加会议的 **最多员工数目** 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/12/14/ex1.png)

**输入：**favorite = \[2,2,1,2\]
**输出：**3
**解释：**
上图展示了公司邀请员工 0，1 和 2 参加会议以及他们在圆桌上的座位。
没办法邀请所有员工参与会议，因为员工 2 没办法同时坐在 0，1 和 3 员工的旁边。
注意，公司也可以邀请员工 1，2 和 3 参加会议。
所以最多参加会议的员工数目为 3 。

**示例 2：**

**输入：**favorite = \[1,2,0\]
**输出：**3
**解释：**
每个员工都至少是另一个员工喜欢的员工。所以公司邀请他们所有人参加会议的前提是所有人都参加了会议。
座位安排同图 1 所示：
- 员工 0 坐在员工 2 和 1 之间。
- 员工 1 坐在员工 0 和 2 之间。
- 员工 2 坐在员工 1 和 0 之间。
  参与会议的最多员工数目为 3 。

**示例 3：**

![](https://assets.leetcode.com/uploads/2021/12/14/ex2.png)

**输入：**favorite = \[3,0,1,4,1\]
**输出：**4
**解释：**
上图展示了公司可以邀请员工 0，1，3 和 4 参加会议以及他们在圆桌上的座位。
员工 2 无法参加，因为他喜欢的员工 1 旁边的座位已经被占领了。
所以公司只能不邀请员工 2 。
参加会议的最多员工数目为 4 。

**提示：**

*   `n == favorite.length`
*   `2 <= n <= 105`
*   `0 <= favorite[i] <= n - 1`
*   `favorite[i] != i`

[https://leetcode.cn/problems/maximum-employees-to-be-invited-to-a-meeting/description/](https://leetcode.cn/problems/maximum-employees-to-be-invited-to-a-meeting/description/)

```java
import java.util.*;

class Solution { // 就是找一个最大的环，还有就是如果这个环是两个节点组成，那么就找链
    public int maximumInvitations(int[] favorite) {
        int n = favorite.length;
        int[] indegree = new int[n];
        for (int x : favorite) {
            indegree[x]++;
        }
        Queue<Integer> queue = new LinkedList<>();
        List<Integer>[] rg = new List[n];
        Arrays.setAll(rg, e -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int x = queue.poll();
            int y = favorite[x];
            rg[y].add(x);
            if (--indegree[y] == 0) {
                queue.offer(y);
            }
        }
        int maxRingSize = 0, sumChainSize = 0;
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                continue;
            }
            indegree[i] = 0;
            int ringSize = 1;
            for (int x = favorite[i]; x != i; x = favorite[x]) {
                indegree[x] = 0;
                ringSize++;
            }
            if (ringSize == 2) {
                sumChainSize += dfs(i, rg) + dfs(favorite[i], rg);
            }else{
                maxRingSize = Math.max(maxRingSize, ringSize);
            }
        }
        return Math.max(maxRingSize, sumChainSize);
    }

    private int dfs(int x, List<Integer>[] g) { // 求从x点出发最长的链
        int res = 1;
        for (int y : g[x]) {
            res = Math.max(res, dfs(y, g) + 1);
        }
        return res;
    }
}
```

2359\. 找到离给定两个节点最近的节点
---------------------

给你一个 `n` 个节点的 **有向图** ，节点编号为 `0` 到 `n - 1` ，每个节点 **至多** 有一条出边。

有向图用大小为 `n` 下标从 **0** 开始的数组 `edges` 表示，表示节点 `i` 有一条有向边指向 `edges[i]` 。如果节点 `i` 没有出边，那么 `edges[i] == -1` 。

同时给你两个节点 `node1` 和 `node2` 。

请你返回一个从 `node1` 和 `node2` 都能到达节点的编号，使节点 `node1` 和节点 `node2` 到这个节点的距离 **较大值最小化**。如果有多个答案，请返回 **最小** 的节点编号。如果答案不存在，返回 `-1` 。

注意 `edges` 可能包含环。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/06/07/graph4drawio-2.png)

**输入：**edges = \[2,2,3,-1\], node1 = 0, node2 = 1
**输出：**2
**解释：**从节点 0 到节点 2 的距离为 1 ，从节点 1 到节点 2 的距离为 1 。
两个距离的较大值为 1 。我们无法得到一个比 1 更小的较大值，所以我们返回节点 2 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/06/07/graph4drawio-4.png)

**输入：**edges = \[1,2,-1\], node1 = 0, node2 = 2
**输出：**2
**解释：**节点 0 到节点 2 的距离为 2 ，节点 2 到它自己的距离为 0 。
两个距离的较大值为 2 。我们无法得到一个比 2 更小的较大值，所以我们返回节点 2 。

**提示：**

*   `n == edges.length`
*   `2 <= n <= 105`
*   `-1 <= edges[i] < n`
*   `edges[i] != i`
*   `0 <= node1, node2 < n`

[https://leetcode.cn/problems/find-closest-node-to-given-two-nodes/description/](https://leetcode.cn/problems/find-closest-node-to-given-two-nodes/description/)

```java
import java.util.Arrays;

class Solution {
    public int closestMeetingNode(int[] edges, int node1, int node2) {
        int[] d1 = bfs(edges, node1), d2 = bfs(edges, node2);
        int ans = -1, n = edges.length;
        for (int i = 0, minDis = n; i < n; i++) {
            int d = Math.max(d1[i], d2[i]);
            if (d < minDis) {
                minDis = d;
                ans = i;
            }
        }
        return ans;
    }

    private int[] bfs(int[] edges, int x) { // 可能有环
        int n = edges.length;
        int[] dist = new int[n];
        Arrays.fill(dist, n);
        for (int d = 0; x >= 0 && dist[x] == n; x = edges[x]) {
            dist[x] = d++;
        }
        return dist;
    }
}
```

2360\. 图中的最长环
-------------

给你一个 `n` 个节点的 **有向图** ，节点编号为 `0` 到 `n - 1` ，其中每个节点 **至多** 有一条出边。

图用一个大小为 `n` 下标从 **0** 开始的数组 `edges` 表示，节点 `i` 到节点 `edges[i]` 之间有一条有向边。如果节点 `i` 没有出边，那么 `edges[i] == -1` 。

请你返回图中的 **最长** 环，如果没有任何环，请返回 `-1` 。

一个环指的是起点和终点是 **同一个** 节点的路径。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/06/08/graph4drawio-5.png)

**输入：**edges = \[3,3,4,2,3\]
**输出去：**3
**解释：**图中的最长环是：2 -> 4 -> 3 -> 2 。
这个环的长度为 3 ，所以返回 3 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/06/07/graph4drawio-1.png)

**输入：**edges = \[2,-1,3,1\]
**输出：**\-1
**解释：**图中没有任何环。

**提示：**

*   `n == edges.length`
*   `2 <= n <= 105`
*   `-1 <= edges[i] < n`
*   `edges[i] != i`

[https://leetcode.cn/problems/longest-cycle-in-a-graph/description/](https://leetcode.cn/problems/longest-cycle-in-a-graph/description/)

```java
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int longestCycle(int[] edges) {
        int n = edges.length;
        int[] indegree = new int[n];
        for (int x : edges) {
            if (x != -1) {
                indegree[x]++;
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int x = queue.poll();
            if (edges[x] != -1 && --indegree[edges[x]] == 0) {
                queue.offer(edges[x]);
            }
        }
        int ans = -1;
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                continue;
            }
            indegree[i] = 0;
            int ringSize = 1;
            for (int x = edges[i]; x != i; x = edges[x]) {
                indegree[x] = 0;
                ringSize++;
            }
            ans = Math.max(ans, ringSize);
        }
        return ans < 2 ? -1 : ans;
    }
}
```

2876\. 有向图访问计数
--------------

现有一个有向图，其中包含 `n` 个节点，节点编号从 `0` 到 `n - 1` 。此外，该图还包含了 `n` 条有向边。

给你一个下标从 **0** 开始的数组 `edges` ，其中 `edges[i]` 表示存在一条从节点 `i` 到节点 `edges[i]` 的边。

想象在图上发生以下过程：

*   你从节点 `x` 开始，通过边访问其他节点，直到你在 **此过程** 中再次访问到之前已经访问过的节点。

返回数组 `answer` 作为答案，其中 `answer[i]` 表示如果从节点 `i` 开始执行该过程，你可以访问到的不同节点数。

**示例 1：**

![](https://assets.leetcode.com/uploads/2023/08/31/graaphdrawio-1.png)

**输入：**edges = \[1,2,0,0\]
**输出：**\[3,3,3,4\]
**解释：**从每个节点开始执行该过程，记录如下：
- 从节点 0 开始，访问节点 0 -> 1 -> 2 -> 0 。访问的不同节点数是 3 。
- 从节点 1 开始，访问节点 1 -> 2 -> 0 -> 1 。访问的不同节点数是 3 。
- 从节点 2 开始，访问节点 2 -> 0 -> 1 -> 2 。访问的不同节点数是 3 。
- 从节点 3 开始，访问节点 3 -> 0 -> 1 -> 2 -> 0 。访问的不同节点数是 4 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2023/08/31/graaph2drawio.png)

**输入：**edges = \[1,2,3,4,0\]
**输出：**\[5,5,5,5,5\]
**解释：**无论从哪个节点开始，在这个过程中，都可以访问到图中的每一个节点。

**提示：**

*   `n == edges.length`
*   `2 <= n <= 105`
*   `0 <= edges[i] <= n - 1`
*   `edges[i] != i`

[https://leetcode.cn/problems/count-visited-nodes-in-a-directed-graph/](https://leetcode.cn/problems/count-visited-nodes-in-a-directed-graph/)

```java
import java.util.*;

class Solution { // 注意题目给出的图可能不是连通的，可能有多棵内向基环树。
    public int[] countVisitedNodes(List<Integer> edges) {
        int[] g = edges.stream().mapToInt(i -> i).toArray(); // 将列表转化成数组
        int n = g.length;
        List<Integer>[] rg = new List[n]; // 反图
        Arrays.setAll(rg, e -> new ArrayList<>());
        int[] indegree = new int[n];
        for (int x = 0; x < n; x++) {
            int y = g[x];
            rg[y].add(x);
            indegree[y]++;
        }
        // 拓扑排序，剪掉 g 上的所有树枝
        // 拓扑排序后，deg 值为 1 的点必定在基环上，为 0 的点必定在树枝上
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            int x = queue.poll();
            if (--indegree[g[x]] == 0) {
                queue.offer(g[x]);
            }
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) { // 万一图没有连通？
            if (indegree[i] <= 0) { // 表面当前节点已经被访问
                continue;
            }
            ArrayList<Integer> ring = new ArrayList<>(); // 记录环上的点
            for (int x = i; ; x = g[x]) {
                indegree[x] = -1;
                ring.add(x);
                if (g[x] == i) {
                    break;
                }
            }
            for (int x : ring) {
                rdfs(x, ring.size(), rg, indegree, ans);
            }
        }
        return ans;
    }

    private void rdfs(int x, int depth, List<Integer>[] rg, int[] indegree, int[] ans) {
        ans[x] = depth;
        for (int y : rg[x]) {
            if (indegree[y] == 0) { // 树枝上的点在拓扑排序后，入度均为 0
                rdfs(y, depth + 1, rg, indegree, ans);
            }
        }
    }
}
```

