1334\. 阈值距离内邻居最少的城市（Floyd模板）
-------------------

有 `n` 个城市，按从 `0` 到 `n-1` 编号。给你一个边数组 `edges`，其中 `edges[i] = [fromi, toi, weighti]` 代表 `fromi` 和 `toi` 两个城市之间的双向加权边，距离阈值是一个整数 `distanceThreshold`。

返回在路径距离限制为 `distanceThreshold` 以内可到达城市最少的城市。如果有多个这样的城市，则返回编号最大的城市。

注意，连接城市 _**i**_ 和 _**j**_ 的路径的距离等于沿该路径的所有边的权重之和。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/01/26/find_the_city_01.png)

**输入：**n = 4, edges = \[\[0,1,3\],\[1,2,1\],\[1,3,4\],\[2,3,1\]\], distanceThreshold = 4
**输出：**3
**解释：**城市分布图如上。
每个城市阈值距离 distanceThreshold = 4 内的邻居城市分别是：
城市 0 -> \[城市 1, 城市 2\] 
城市 1 -> \[城市 0, 城市 2, 城市 3\] 
城市 2 -> \[城市 0, 城市 1, 城市 3\] 
城市 3 -> \[城市 1, 城市 2\] 
城市 0 和 3 在阈值距离 4 以内都有 2 个邻居城市，但是我们必须返回城市 3，因为它的编号最大。

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/01/26/find_the_city_02.png)**

**输入：**n = 5, edges = \[\[0,1,2\],\[0,4,8\],\[1,2,3\],\[1,4,2\],\[2,3,1\],\[3,4,1\]\], distanceThreshold = 2
**输出：**0
**解释：**城市分布图如上。 
每个城市阈值距离 distanceThreshold = 2 内的邻居城市分别是：
城市 0 -> \[城市 1\] 
城市 1 -> \[城市 0, 城市 4\] 
城市 2 -> \[城市 3, 城市 4\] 
城市 3 -> \[城市 2, 城市 4\]
城市 4 -> \[城市 1, 城市 2, 城市 3\] 
城市 0 在阈值距离 2 以内只有 1 个邻居城市。

**提示：**

*   `2 <= n <= 100`
*   `1 <= edges.length <= n * (n - 1) / 2`
*   `edges[i].length == 3`
*   `0 <= fromi < toi < n`
*   `1 <= weighti, distanceThreshold <= 10^4`
*   所有 `(fromi, toi)` 都是不同的。

[https://leetcode.cn/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/description/](https://leetcode.cn/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/description/)

```java
import java.util.Arrays;

class Solution { // 不建议，因为不好debug
    int[][][] memo;
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] w = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(w[i], Integer.MAX_VALUE / 2); // 防止加法溢出
        }
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], z = edge[2];
            w[x][y] = w[y][x] = z;
        }
        memo = new int[n][n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        int ans = 0;
        int minCnt = n;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (j != i && dfs(n - 1, i, j, w) <= distanceThreshold) {
                    cnt++;
                }
            }
            if (cnt <= minCnt) {
                minCnt = cnt;
                ans = i;
            }
        }
        return ans;
    }

    private int dfs(int k, int i, int j, int[][] w) { // 定义 dfs(k,i,j) 表示从 i 到 j 的最短路长度，并且这条最短路的中间节点编号都 ≤k。注意中间节点不包含 i 和 j。
        if (k < 0) {
            return w[i][j];
        }
        if (memo[k][i][j] != -1) {
            return memo[k][i][j];
        }
        return memo[k][i][j] = Math.min(dfs(k - 1, i, j, w), dfs(k - 1, i, k, w) + dfs(k - 1, k, j, w));
    }
}
```

```java
public class Solution {
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] w = new int[n][n];
        for (int[] row : w) {
            Arrays.fill(row, Integer.MAX_VALUE / 2); // 防止加法溢出
        }
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            w[x][y] = w[y][x] = wt;
        }

        int[][][] f = new int[n + 1][n][n];
        f[0] = w;  // 初始化
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    f[k + 1][i][j] = Math.min(f[k][i][j], f[k][i][k] + f[k][k][j]);
                }
            }
        }

        int ans = 0;
        int minCnt = n;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (j != i && f[n][i][j] <= distanceThreshold) {
                    cnt++;
                }
            }
            if (cnt <= minCnt) { // 相等时取最大的 i
                minCnt = cnt;
                ans = i;
            }
        }
        return ans;
    }
}
```

```java
public class Solution { // 推荐模板
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] w = new int[n][n];
        for (int[] row : w) {
            Arrays.fill(row, Integer.MAX_VALUE / 2); // 防止加法溢出
        }
        for (int[] e : edges) {
            int x = e[0], y = e[1], wt = e[2];
            w[x][y] = w[y][x] = wt;
        }

        int[][] f = w; // 这里不能写 int[][] f = new int[n][n];
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    f[i][j] = Math.min(f[i][j], f[i][k] + f[k][j]);
                }
            }
        }

        int ans = 0;
        int minCnt = n;
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int j = 0; j < n; j++) {
                if (j != i && f[i][j] <= distanceThreshold) {
                    cnt++;
                }
            }
            if (cnt <= minCnt) { // 相等时取最大的 i
                minCnt = cnt;
                ans = i;
            }
        }
        return ans;
    }
}
```

2976\. 转换字符串的最小成本 I
-------------------

给你两个下标从 **0** 开始的字符串 `source` 和 `target` ，它们的长度均为 `n` 并且由 **小写** 英文字母组成。

另给你两个下标从 **0** 开始的字符数组 `original` 和 `changed` ，以及一个整数数组 `cost` ，其中 `cost[i]` 代表将字符 `original[i]` 更改为字符 `changed[i]` 的成本。

你从字符串 `source` 开始。在一次操作中，**如果** 存在 **任意** 下标 `j` 满足 `cost[j] == z`  、`original[j] == x` 以及 `changed[j] == y` 。你就可以选择字符串中的一个字符 `x` 并以 `z` 的成本将其更改为字符 `y` 。

返回将字符串 `source` 转换为字符串 `target` 所需的 **最小** 成本。如果不可能完成转换，则返回 `-1` 。

**注意**，可能存在下标 `i` 、`j` 使得 `original[j] == original[i]` 且 `changed[j] == changed[i]` 。

**示例 1：**

**输入：**source = "abcd", target = "acbe", original = \["a","b","c","c","e","d"\], changed = \["b","c","b","e","b","e"\], cost = \[2,5,5,1,2,20\]
**输出：**28
**解释：**将字符串 "abcd" 转换为字符串 "acbe" ：
- 更改下标 1 处的值 'b' 为 'c' ，成本为 5 。
- 更改下标 2 处的值 'c' 为 'e' ，成本为 1 。
- 更改下标 2 处的值 'e' 为 'b' ，成本为 2 。
- 更改下标 3 处的值 'd' 为 'e' ，成本为 20 。
  产生的总成本是 5 + 1 + 2 + 20 = 28 。
  可以证明这是可能的最小成本。

**示例 2：**

**输入：**source = "aaaa", target = "bbbb", original = \["a","c"\], changed = \["c","b"\], cost = \[1,2\]
**输出：**12
**解释：**要将字符 'a' 更改为 'b'：
- 将字符 'a' 更改为 'c'，成本为 1 
- 将字符 'c' 更改为 'b'，成本为 2 
  产生的总成本是 1 + 2 = 3。
  将所有 'a' 更改为 'b'，产生的总成本是 3 \* 4 = 12 。

**示例 3：**

**输入：**source = "abcd", target = "abce", original = \["a"\], changed = \["e"\], cost = \[10000\]
**输出：**\-1
**解释：**无法将 source 字符串转换为 target 字符串，因为下标 3 处的值无法从 'd' 更改为 'e' 。

**提示：**

*   `1 <= source.length == target.length <= 105`
*   `source`、`target` 均由小写英文字母组成
*   `1 <= cost.length== original.length == changed.length <= 2000`
*   `original[i]`、`changed[i]` 是小写英文字母
*   `1 <= cost[i] <= 106`
*   `original[i] != changed[i]`

[https://leetcode.cn/problems/minimum-cost-to-convert-string-i/description/](https://leetcode.cn/problems/minimum-cost-to-convert-string-i/description/)

```java
import java.util.Arrays;

class Solution {
    public long minimumCost(String source, String target, char[] original, char[] changed, int[] cost) {
        int n = changed.length;
        int[][] dp = new int[26][26];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
            dp[i][i] = 0;
        }
        for (int i = 0; i < n; i++) {
            int x = original[i] - 'a';
            int y = changed[i] - 'a';
            dp[x][y] = Math.min(dp[x][y], cost[i]);
        }
        for (int k = 0; k < 26; k++) {
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                }
            }
        }

        long ans = 0;
        for (int i = 0; i < source.length(); i++) {
            int x = source.charAt(i) - 'a';
            int y = target.charAt(i) - 'a';
            if (dp[x][y] == Integer.MAX_VALUE / 2) {
                return -1;
            }
            ans += dp[x][y];
        }
        return ans;
    }
}
```

2959\. 关闭分部的可行集合数目
------------------

一个公司在全国有 `n` 个分部，它们之间有的有道路连接。一开始，所有分部通过这些道路两两之间互相可以到达。

公司意识到在分部之间旅行花费了太多时间，所以它们决定关闭一些分部（**也可能不关闭任何分部**），同时保证剩下的分部之间两两互相可以到达且最远距离不超过 `maxDistance` 。

两个分部之间的 **距离** 是通过道路长度之和的 **最小值** 。

给你整数 `n` ，`maxDistance` 和下标从 **0** 开始的二维整数数组 `roads` ，其中 `roads[i] = [ui, vi, wi]` 表示一条从 `ui` 到 `vi` 长度为 `wi`的 **无向** 道路。

请你返回关闭分部的可行方案数目，满足每个方案里剩余分部之间的最远距离不超过 `maxDistance`。

**注意**，关闭一个分部后，与之相连的所有道路不可通行。

**注意**，两个分部之间可能会有多条道路。

**示例 1：**

![](https://assets.leetcode.com/uploads/2023/11/08/example11.png)

**输入：**n = 3, maxDistance = 5, roads = \[\[0,1,2\],\[1,2,10\],\[0,2,10\]\]
**输出：**5
**解释：**可行的关闭分部方案有：
- 关闭分部集合 \[2\] ，剩余分部为 \[0,1\] ，它们之间的距离为 2 。
- 关闭分部集合 \[0,1\] ，剩余分部为 \[2\] 。
- 关闭分部集合 \[1,2\] ，剩余分部为 \[0\] 。
- 关闭分部集合 \[0,2\] ，剩余分部为 \[1\] 。
- 关闭分部集合 \[0,1,2\] ，关闭后没有剩余分部。
  总共有 5 种可行的关闭方案。

**示例 2：**

![](https://assets.leetcode.com/uploads/2023/11/08/example22.png)

**输入：**n = 3, maxDistance = 5, roads = \[\[0,1,20\],\[0,1,10\],\[1,2,2\],\[0,2,2\]\]
**输出：**7
**解释：**可行的关闭分部方案有：
- 关闭分部集合 \[\] ，剩余分部为 \[0,1,2\] ，它们之间的最远距离为 4 。
- 关闭分部集合 \[0\] ，剩余分部为 \[1,2\] ，它们之间的距离为 2 。
- 关闭分部集合 \[1\] ，剩余分部为 \[0,2\] ，它们之间的距离为 2 。
- 关闭分部集合 \[0,1\] ，剩余分部为 \[2\] 。
- 关闭分部集合 \[1,2\] ，剩余分部为 \[0\] 。
- 关闭分部集合 \[0,2\] ，剩余分部为 \[1\] 。
- 关闭分部集合 \[0,1,2\] ，关闭后没有剩余分部。
  总共有 7 种可行的关闭方案。

**示例 3：**

**输入：**n = 1, maxDistance = 10, roads = \[\]
**输出：**2
**解释：**可行的关闭分部方案有：
- 关闭分部集合 \[\] ，剩余分部为 \[0\] 。
- 关闭分部集合 \[0\] ，关闭后没有剩余分部。
  总共有 2 种可行的关闭方案。

**提示：**

*   `1 <= n <= 10`
*   `1 <= maxDistance <= 105`
*   `0 <= roads.length <= 1000`
*   `roads[i].length == 3`
*   `0 <= ui, vi <= n - 1`
*   `ui != vi`
*   `1 <= wi <= 1000`
*   一开始所有分部之间通过道路互相可以到达。

[https://leetcode.cn/problems/number-of-possible-sets-of-closing-branches/description/](https://leetcode.cn/problems/number-of-possible-sets-of-closing-branches/description/)

```java
import java.util.Arrays;

class Solution { // 位运算写法
    public int numberOfSets(int n, int maxDistance, int[][] roads) {
        // 因为 n<=10,枚举这10个点组成的不同集合，然后判断是否满足条件
        // floyd算法，我们都是建邻接矩阵
        int[][] g = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(g[i], Integer.MAX_VALUE / 2);
            g[i][i] = 0;
        }
        for (int[] road : roads) {
            int x = road[0], y = road[1], z = road[2];
            g[x][y] = Math.min(g[x][y], z);
            g[y][x] = Math.min(g[y][x], z);
        }
        int ans = 0;
        int[][] dp = new int[n][n];
        // 枚举集合
        next:
        for (int s = 0; s < (1 << n); s++) {
            for (int i = 0; i < n; i++) {
                if ((s >> i & 1) == 1) { // 表示选了i这个节点
                    System.arraycopy(g[i], 0, dp[i], 0, n); // 复制从i出发到各个节点的距离
                }
            }

            // floyd
            for (int k = 0; k < n; k++) {
                if ((s >> k & 1) == 0) {
                    continue; // 没有选当前节点
                }
                for (int i = 0; i < n; i++) {
                    if ((s >> i & 1) == 0) {
                        continue;
                    }
                    for (int j = 0; j < n; j++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k][j]);
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                if ((s >> i & 1) == 0) {
                    continue;
                }
                for (int j = 0; j < n; j++) {
                    if ((s >> j & 1) == 1 && dp[i][j] > maxDistance) {
                        continue next;
                    }
                }
            }
            ans++;
        }
        return ans;
    }
}
```

2977\. 转换字符串的最小成本 II（字典树+Floyd）
--------------------

给你两个下标从 **0** 开始的字符串 `source` 和 `target` ，它们的长度均为 `n` 并且由 **小写** 英文字母组成。

另给你两个下标从 **0** 开始的字符串数组 `original` 和 `changed` ，以及一个整数数组 `cost` ，其中 `cost[i]` 代表将字符串 `original[i]` 更改为字符串 `changed[i]` 的成本。

你从字符串 `source` 开始。在一次操作中，**如果** 存在 **任意** 下标 `j` 满足 `cost[j] == z`  、`original[j] == x` 以及 `changed[j] == y` ，你就可以选择字符串中的 **子串** `x` 并以 `z` 的成本将其更改为 `y` 。 你可以执行 **任意数量** 的操作，但是任两次操作必须满足 **以下两个** 条件 **之一** ：

*   在两次操作中选择的子串分别是 `source[a..b]` 和 `source[c..d]` ，满足 `b < c`  **或** `d < a` 。换句话说，两次操作中选择的下标 **不相交** 。
*   在两次操作中选择的子串分别是 `source[a..b]` 和 `source[c..d]` ，满足 `a == c` **且** `b == d` 。换句话说，两次操作中选择的下标 **相同** 。

返回将字符串 `source` 转换为字符串 `target` 所需的 **最小** 成本。如果不可能完成转换，则返回 `-1` 。

**注意**，可能存在下标 `i` 、`j` 使得 `original[j] == original[i]` 且 `changed[j] == changed[i]` 。

**示例 1：**

**输入：**source = "abcd", target = "acbe", original = \["a","b","c","c","e","d"\], changed = \["b","c","b","e","b","e"\], cost = \[2,5,5,1,2,20\]
**输出：**28
**解释：**将 "abcd" 转换为 "acbe"，执行以下操作：
- 将子串 source\[1..1\] 从 "b" 改为 "c" ，成本为 5 。
- 将子串 source\[2..2\] 从 "c" 改为 "e" ，成本为 1 。
- 将子串 source\[2..2\] 从 "e" 改为 "b" ，成本为 2 。
- 将子串 source\[3..3\] 从 "d" 改为 "e" ，成本为 20 。
  产生的总成本是 5 + 1 + 2 + 20 = 28 。 
  可以证明这是可能的最小成本。

**示例 2：**

**输入：**source = "abcdefgh", target = "acdeeghh", original = \["bcd","fgh","thh"\], changed = \["cde","thh","ghh"\], cost = \[1,3,5\]
**输出：**9
**解释：**将 "abcdefgh" 转换为 "acdeeghh"，执行以下操作：
- 将子串 source\[1..3\] 从 "bcd" 改为 "cde" ，成本为 1 。
- 将子串 source\[5..7\] 从 "fgh" 改为 "thh" ，成本为 3 。可以执行此操作，因为下标 \[5,7\] 与第一次操作选中的下标不相交。
- 将子串 source\[5..7\] 从 "thh" 改为 "ghh" ，成本为 5 。可以执行此操作，因为下标 \[5,7\] 与第一次操作选中的下标不相交，且与第二次操作选中的下标相同。
  产生的总成本是 1 + 3 + 5 = 9 。
  可以证明这是可能的最小成本。

**示例 3：**

**输入：**source = "abcdefgh", target = "addddddd", original = \["bcd","defgh"\], changed = \["ddd","ddddd"\], cost = \[100,1578\]
**输出：**\-1
**解释：**无法将 "abcdefgh" 转换为 "addddddd" 。
如果选择子串 source\[1..3\] 执行第一次操作，以将 "abcdefgh" 改为 "adddefgh" ，你无法选择子串 source\[3..7\] 执行第二次操作，因为两次操作有一个共用下标 3 。
如果选择子串 source\[3..7\] 执行第一次操作，以将 "abcdefgh" 改为 "abcddddd" ，你无法选择子串 source\[1..3\] 执行第二次操作，因为两次操作有一个共用下标 3 。

**提示：**

*   `1 <= source.length == target.length <= 1000`
*   `source`、`target` 均由小写英文字母组成
*   `1 <= cost.length == original.length == changed.length <= 100`
*   `1 <= original[i].length == changed[i].length <= source.length`
*   `original[i]`、`changed[i]` 均由小写英文字母组成
*   `original[i] != changed[i]`
*   `1 <= cost[i] <= 106`

[https://leetcode.cn/problems/minimum-cost-to-convert-string-ii/description/](https://leetcode.cn/problems/minimum-cost-to-convert-string-ii/description/)

```java
import java.util.Arrays;

class Solution {
    class Node{
        Node[] son = new Node[26];
        int sid = -1; // 字符串的编号
    }

    private Node root = new Node();
    private int sid = 0;
    private char[] s, t;
    private int[][] dist;
    private long[] memo;
    public long minimumCost(String source, String target, String[] original, String[] changed, int[] cost) {
        // 初始化距离矩阵
        int m = cost.length;
        dist = new int[m * 2][m * 2];
        for (int i = 0; i < m * 2; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE / 2);
            dist[i][i] = 0;
        }
        for (int i = 0; i < m; i++) {
            int x = insert(original[i]), y = insert(changed[i]);
            dist[x][y] = Math.min(dist[x][y], cost[i]);
        }

        // Floyd 求任意两点最短路
        for (int k = 0; k < sid; k++) {
            for (int i = 0; i < sid; i++) {
                if (dist[i][k] == Integer.MAX_VALUE / 2) {
                    continue;
                }
                for (int j = 0; j < sid; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        s = source.toCharArray();
        t = target.toCharArray();
        memo = new long[s.length];
        Arrays.fill(memo, -1);
        long ans = dfs(0);
        return ans < Long.MAX_VALUE / 2 ? ans : -1;
    }

    private long dfs(int i) { // 定义 dfs(i) 表示从 source[i] 开始向后修改的最小成本。
        if (i >= s.length) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        long res = Long.MAX_VALUE / 2;
        if (s[i] == t[i]) {
            res = dfs(i + 1); // 不修改当前节点
        }
        Node p = root, q = root;
        for (int j = i; j < s.length; j++) {
            p = p.son[s[j] - 'a'];
            q = q.son[t[j] - 'a'];
            if (p == null || q == null) {
                break;
            }
            if (p.sid < 0 || q.sid < 0) {
                continue;
            }
            // 修改从 i 到 j 的这一段
            int d = dist[p.sid][q.sid];
            if (d < Integer.MAX_VALUE / 2) {
                res = Math.min(res, d + dfs(j + 1));
            }
        }
        return memo[i] = res;
    }

    private int insert(String s) {
        Node cur = root;
        for (char c : s.toCharArray()) {
            if (cur.son[c - 'a'] == null) {
                cur.son[c - 'a'] = new Node();
            }
            cur = cur.son[c - 'a'];
        }
        if (cur.sid < 0) {
            cur.sid = sid++;
        }
        return cur.sid; // 拿这个编号
    }


}
```

