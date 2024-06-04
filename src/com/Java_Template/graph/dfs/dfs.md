547\. 省份数量
----------

有 `n` 个城市，其中一些彼此相连，另一些没有相连。如果城市 `a` 与城市 `b` 直接相连，且城市 `b` 与城市 `c` 直接相连，那么城市 `a` 与城市 `c` 间接相连。

**省份** 是一组直接或间接相连的城市，组内不含其他没有相连的城市。

给你一个 `n x n` 的矩阵 `isConnected` ，其中 `isConnected[i][j] = 1` 表示第 `i` 个城市和第 `j` 个城市直接相连，而 `isConnected[i][j] = 0` 表示二者不直接相连。

返回矩阵中 **省份** 的数量。

**示例 1：**

![](https://assets.leetcode.com/uploads/2020/12/24/graph1.jpg)

**输入：**isConnected = \[\[1,1,0\],\[1,1,0\],\[0,0,1\]\]
**输出：**2

**示例 2：**

![](https://assets.leetcode.com/uploads/2020/12/24/graph2.jpg)

**输入：**isConnected = \[\[1,0,0\],\[0,1,0\],\[0,0,1\]\]
**输出：**3

**提示：**

*   `1 <= n <= 200`
*   `n == isConnected.length`
*   `n == isConnected[i].length`
*   `isConnected[i][j]` 为 `1` 或 `0`
*   `isConnected[i][i] == 1`
*   `isConnected[i][j] == isConnected[j][i]`

[https://leetcode.cn/problems/number-of-provinces/description/](https://leetcode.cn/problems/number-of-provinces/description/)

```java
class Solution {
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, visited, isConnected, n);
                ans++;
            }
        }
        return ans;
    }

    private void dfs(int i, boolean[] visited, int[][] isConnected,int n) {
        visited[i] = true;
        for (int j = 0; j < n; j++) {
            if (!visited[j] && isConnected[i][j] == 1) {
                dfs(j, visited, isConnected, n);
            }
        }

    }
}
```

1971\. 寻找图中是否存在路径
-----------------

有一个具有 `n` 个顶点的 **双向** 图，其中每个顶点标记从 `0` 到 `n - 1`（包含 `0` 和 `n - 1`）。图中的边用一个二维整数数组 `edges` 表示，其中 `edges[i] = [ui, vi]` 表示顶点 `ui` 和顶点 `vi` 之间的双向边。 每个顶点对由 **最多一条** 边连接，并且没有顶点存在与自身相连的边。

请你确定是否存在从顶点 `source` 开始，到顶点 `destination` 结束的 **有效路径** 。

给你数组 `edges` 和整数 `n`、`source` 和 `destination`，如果从 `source` 到 `destination` 存在 **有效路径** ，则返回 `true`，否则返回 `false` 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/08/14/validpath-ex1.png)

**输入：**n = 3, edges = \[\[0,1\],\[1,2\],\[2,0\]\], source = 0, destination = 2
**输出：**true
**解释：**存在由顶点 0 到顶点 2 的路径:
- 0 → 1 → 2 
- 0 → 2

**示例 2：**

![](https://assets.leetcode.com/uploads/2021/08/14/validpath-ex2.png)

**输入：**n = 6, edges = \[\[0,1\],\[0,2\],\[3,5\],\[5,4\],\[4,3\]\], source = 0, destination = 5
**输出：**false
**解释：**不存在由顶点 0 到顶点 5 的路径.

**提示：**

*   `1 <= n <= 2 * 105`
*   `0 <= edges.length <= 2 * 105`
*   `edges[i].length == 2`
*   `0 <= ui, vi <= n - 1`
*   `ui != vi`
*   `0 <= source, destination <= n - 1`
*   不存在重复边
*   不存在指向顶点自身的边

[https://leetcode.cn/problems/find-if-path-exists-in-graph/description/](https://leetcode.cn/problems/find-if-path-exists-in-graph/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        List<Integer>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        boolean[] visited = new boolean[n];
        return dfs(g, visited, source, destination);
    }

    private boolean dfs(List<Integer>[] g,boolean[] visited, int source, int destination) {
        if (source == destination) {
            return true;
        }
        visited[source] = true;
        for (int x : g[source]) {
//            if (!visited[x]) {
//                return dfs(g, visited, x, destination);
//            }
            if (!visited[x]&&dfs(g, visited, x, destination)) {
                return true;  // 只有在任何递归调用返回true时才返回true
            }
        }
        return false;
    }
}
```

3067\. 在带权树网络中统计可连接服务器对数目(树上模板题)
-------------------------

给你一棵无根带权树，树中总共有 `n` 个节点，分别表示 `n` 个服务器，服务器从 `0` 到 `n - 1` 编号。同时给你一个数组 `edges` ，其中 `edges[i] = [ai, bi, weighti]` 表示节点 `ai` 和 `bi` 之间有一条双向边，边的权值为 `weighti` 。再给你一个整数 `signalSpeed` 。

如果两个服务器 `a` ，`b` 和 `c` 满足以下条件，那么我们称服务器 `a` 和 `b` 是通过服务器 `c` **可连接的** ：

*   `a < b` ，`a != c` 且 `b != c` 。
*   从 `c` 到 `a` 的距离是可以被 `signalSpeed` 整除的。
*   从 `c` 到 `b` 的距离是可以被 `signalSpeed` 整除的。
*   从 `c` 到 `b` 的路径与从 `c` 到 `a` 的路径没有任何公共边。

请你返回一个长度为 `n` 的整数数组 `count` ，其中 `count[i]` 表示通过服务器 `i` **可连接** 的服务器对的 **数目** 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2024/01/21/example22.png)

**输入：**edges = \[\[0,1,1\],\[1,2,5\],\[2,3,13\],\[3,4,9\],\[4,5,2\]\], signalSpeed = 1
**输出：**\[0,4,6,6,4,0\]
**解释：**由于 signalSpeed 等于 1 ，count\[c\] 等于所有从 c 开始且没有公共边的路径对数目。
在输入图中，count\[c\] 等于服务器 c 左边服务器数目乘以右边服务器数目。

**示例 2：**

![](https://assets.leetcode.com/uploads/2024/01/21/example11.png)

**输入：**edges = \[\[0,6,3\],\[6,5,3\],\[0,3,1\],\[3,2,7\],\[3,1,6\],\[3,4,2\]\], signalSpeed = 3
**输出：**\[2,0,0,0,0,0,2\]
**解释：**通过服务器 0 ，有 2 个可连接服务器对(4, 5) 和 (4, 6) 。
通过服务器 6 ，有 2 个可连接服务器对 (4, 5) 和 (0, 5) 。
所有服务器对都必须通过服务器 0 或 6 才可连接，所以其他服务器对应的可连接服务器对数目都为 0 。

**提示：**

*   `2 <= n <= 1000`
*   `edges.length == n - 1`
*   `edges[i].length == 3`
*   `0 <= ai, bi < n`
*   `edges[i] = [ai, bi, weighti]`
*   `1 <= weighti <= 106`
*   `1 <= signalSpeed <= 106`
*   输入保证 `edges` 构成一棵合法的树。

[https://leetcode.cn/problems/count-pairs-of-connectable-servers-in-a-weighted-tree-network/description/?envType=daily-question&envId=2024-06-04](https://leetcode.cn/problems/count-pairs-of-connectable-servers-in-a-weighted-tree-network/description/?envType=daily-question&envId=2024-06-04)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int[] countPairsOfConnectableServers(int[][] edges, int signalSpeed) {
        int n = edges.length + 1;
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], z = edge[2];
            g[x].add(new int[]{y, z});
            g[y].add(new int[]{x, z});
        }
        int[] ans = new int[n]; // 这个图没有环，所以不用visited数组，这其实就是一棵树
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int[] e : g[i]) {
                int cnt = dfs(e[0], i, g, e[1], signalSpeed);
                ans[i] += cnt * sum;
                sum += cnt;
            }
        }
        return ans;
    }

    private int dfs(int x, int fa, List<int[]>[] g, int sum, int signalSpeed) {
        int res = sum % signalSpeed == 0 ? 1 : 0;
        for (int[] y : g[x]) {
            if (y[0] != fa) {
                res += dfs(y[0], x, g, sum + y[1], signalSpeed);
            }
        }
        return res;
    }

}
```

