3241\. 标记所有节点需要的时间
------------------

给你一棵 **无向** 树，树中节点从 `0` 到 `n - 1` 编号。同时给你一个长度为 `n - 1` 的二维整数数组 `edges` ，其中 `edges[i] = [ui, vi]` 表示节点 `ui` 和 `vi` 在树中有一条边。

一开始，**所有** 节点都 **未标记** 。对于节点 `i` ：

*   当 `i` 是奇数时，如果时刻 `x - 1` 该节点有 **至少** 一个相邻节点已经被标记了，那么节点 `i` 会在时刻 `x` 被标记。
*   当 `i` 是偶数时，如果时刻 `x - 2` 该节点有 **至少** 一个相邻节点已经被标记了，那么节点 `i` 会在时刻 `x` 被标记。

请你返回一个数组 `times` ，表示如果你在时刻 `t = 0` 标记节点 `i` ，那么时刻 `times[i]` 时，树中所有节点都会被标记。

请注意，每个 `times[i]` 的答案都是独立的，即当你标记节点 `i` 时，所有其他节点都未标记。

**示例 1：**

**输入：**edges = \[\[0,1\],\[0,2\]\]

**输出：**\[2,4,3\]

**解释：**

![](https://assets.leetcode.com/uploads/2024/06/01/screenshot-2024-06-02-122236.png)

*   对于 `i = 0` ：
    *   节点 1 在时刻 `t = 1` 被标记，节点 2 在时刻 `t = 2` 被标记。
*   对于 `i = 1` ：
    *   节点 0 在时刻 `t = 2` 被标记，节点 2 在时刻 `t = 4` 被标记。
*   对于 `i = 2` ：
    *   节点 0 在时刻 `t = 2` 被标记，节点 1 在时刻 `t = 3` 被标记。

**示例 2：**

**输入：**edges = \[\[0,1\]\]

**输出：**\[1,2\]

**解释：**

![](https://assets.leetcode.com/uploads/2024/06/01/screenshot-2024-06-02-122249.png)

*   对于 `i = 0` ：
    *   节点 1 在时刻 `t = 1` 被标记。
*   对于 `i = 1` ：
    *   节点 0 在时刻 `t = 2` 被标记。

**示例 3：**

**输入：**edges = \[\[2,4\],\[0,1\],\[2,3\],\[0,2\]\]

**输出：**\[4,6,3,5,5\]

**解释：**

![](https://assets.leetcode.com/uploads/2024/06/03/screenshot-2024-06-03-210550.png)

**提示：**

*   `2 <= n <= 105`
*   `edges.length == n - 1`
*   `edges[i].length == 2`
*   `0 <= edges[i][0], edges[i][1] <= n - 1`
*   输入保证 `edges` 表示一棵合法的树。

[https://leetcode.cn/problems/time-taken-to-mark-all-nodes/description/](https://leetcode.cn/problems/time-taken-to-mark-all-nodes/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    List<Integer>[] g;
    public int[] timeTaken(int[][] edges) {
        // 先计算以0为根节点，计算树的最大深度
        int n = edges.length + 1;
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        // nodes[x] 保存子树 x 的最大深度 maxD，次大深度 maxD2，以及最大深度要往儿子 my 走
        int[][] nodes = new int[n][3];
        dfs(0, -1, nodes);
        int[] ans = new int[n];
        reRoot(0, -1, 0, nodes, ans);
        return ans;
    }

    private int dfs(int x, int fa, int[][] nodes) {
        int maxD = 0;
        int maxD2 = 0;
        int my = 0;
        for (int y : g[x]) {
            if (y == fa) {
                continue;
            }
            int depth = dfs(y, x, nodes) + 2 - y % 2;
            if (depth > maxD) {
                maxD2 = maxD;
                maxD = depth;
                my = y;
            } else if (depth > maxD2) {
                maxD2 = depth;
            }
        }
        nodes[x][0] = maxD;
        nodes[x][1] = maxD2;
        nodes[x][2] = my;
        return maxD;
    }

    private void reRoot(int x, int fa, int fromUp, int[][] nodes, int[] ans) {
        int maxD = nodes[x][0];
        int maxD2 = nodes[x][1];
        int my = nodes[x][2];
        ans[x] = Math.max(fromUp, maxD);
        for (int y : g[x]) {
            if (y != fa) {
                reRoot(y, x, Math.max(fromUp, (y == my ? maxD2 : maxD)) + 2 - x % 2, nodes, ans);
            }
        }
    }

}
```

