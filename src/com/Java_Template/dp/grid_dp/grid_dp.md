741\. 摘樱桃
---------

给你一个 `n x n` 的网格 `grid` ，代表一块樱桃地，每个格子由以下三种数字的一种来表示：

*   `0` 表示这个格子是空的，所以你可以穿过它。
*   `1` 表示这个格子里装着一个樱桃，你可以摘到樱桃然后穿过它。
*   `-1` 表示这个格子里有荆棘，挡着你的路。

请你统计并返回：在遵守下列规则的情况下，能摘到的最多樱桃数：

*   从位置 `(0, 0)` 出发，最后到达 `(n - 1, n - 1)` ，只能向下或向右走，并且只能穿越有效的格子（即只可以穿过值为 `0` 或者 `1` 的格子）；
*   当到达 `(n - 1, n - 1)` 后，你要继续走，直到返回到 `(0, 0)` ，只能向上或向左走，并且只能穿越有效的格子；
*   当你经过一个格子且这个格子包含一个樱桃时，你将摘到樱桃并且这个格子会变成空的（值变为 `0` ）；
*   如果在 `(0, 0)` 和 `(n - 1, n - 1)` 之间不存在一条可经过的路径，则无法摘到任何一个樱桃。

**示例 1：**

![](https://assets.leetcode.com/uploads/2020/12/14/grid.jpg)

**输入：**grid = \[\[0,1,-1\],\[1,0,-1\],\[1,1,1\]\]
**输出：**5
**解释：**玩家从 (0, 0) 出发：向下、向下、向右、向右移动至 (2, 2) 。
在这一次行程中捡到 4 个樱桃，矩阵变成 \[\[0,1,-1\],\[0,0,-1\],\[0,0,0\]\] 。
然后，玩家向左、向上、向上、向左返回起点，再捡到 1 个樱桃。
总共捡到 5 个樱桃，这是最大可能值。

**示例 2：**

**输入：**grid = \[\[1,1,-1\],\[1,-1,1\],\[-1,1,1\]\]
**输出：**0

**提示：**

*   `n == grid.length`
*   `n == grid[i].length`
*   `1 <= n <= 50`
*   `grid[i][j]` 为 `-1`、`0` 或 `1`
*   `grid[0][0] != -1`
*   `grid[n - 1][n - 1] != -1`

[https://leetcode.cn/problems/cherry-pickup/description/](https://leetcode.cn/problems/cherry-pickup/description/)

```java
import java.util.Arrays;

class Solution {
    private final static int[][] directions = {{0, 0}, {-1, 0}, {0, -1}, {-1, -1}};
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int maxStep = (n - 1) * 2;
        int[][][] dp = new int[maxStep + 1][n][n];
        for (int i = 0; i <= maxStep; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE);
            }
        }
        dp[0][0][0] = grid[0][0];
        for (int step = 1; step <= maxStep; step++) {
            int maxRow = Math.min(n - 1, step), minRow = Math.max(0, step - n + 1);
            for (int x1 = minRow; x1 <= maxRow; x1++) {
                if (grid[x1][step - x1] == -1) {
                    continue;
                }
                // 枚举x2
                for (int x2 = x1; x2 <= maxRow; x2++) { // x2 = x1 减枝
                    if (grid[x2][step - x2] == -1) {
                        continue;
                    }
                    int curr = x1 == x2 ? grid[x1][step - x1] : grid[x1][step - x1] + grid[x2][step - x2];
                    int prev = Integer.MIN_VALUE;
                    for (int[] d : directions) {
                        int preRow1 = x1 + d[0], preRow2 = x2 + d[1];
                        if (preRow1 >= 0 && preRow2 >= 0) {
                            prev = Math.max(prev, dp[step - 1][preRow1][preRow2]);
                        }
                    }
                    dp[step][x1][x2] = prev + curr;
                }
            }
        }
        return Math.max(dp[maxStep][n - 1][n - 1], 0);
    }
}
```

1463\. 摘樱桃 II
-------------

给你一个 `rows x cols` 的矩阵 `grid` 来表示一块樱桃地。 `grid` 中每个格子的数字表示你能获得的樱桃数目。

你有两个机器人帮你收集樱桃，机器人 1 从左上角格子 `(0,0)` 出发，机器人 2 从右上角格子 `(0, cols-1)` 出发。

请你按照如下规则，返回两个机器人能收集的最多樱桃数目：

*   从格子 `(i,j)` 出发，机器人可以移动到格子 `(i+1, j-1)`，`(i+1, j)` 或者 `(i+1, j+1)` 。
*   当一个机器人经过某个格子时，它会把该格子内所有的樱桃都摘走，然后这个位置会变成空格子，即没有樱桃的格子。
*   当两个机器人同时到达同一个格子时，它们中只有一个可以摘到樱桃。
*   两个机器人在任意时刻都不能移动到 `grid` 外面。
*   两个机器人最后都要到达 `grid` 最底下一行。

**示例 1：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/05/30/sample_1_1802.png)**

**输入：**grid = \[\[3,1,1\],\[2,5,1\],\[1,5,5\],\[2,1,1\]\]
**输出：**24
**解释：**机器人 1 和机器人 2 的路径在上图中分别用绿色和蓝色表示。
机器人 1 摘的樱桃数目为 (3 + 2 + 5 + 2) = 12 。
机器人 2 摘的樱桃数目为 (1 + 5 + 5 + 1) = 12 。
樱桃总数为： 12 + 12 = 24 。

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/05/30/sample_2_1802.png)**

**输入：**grid = \[\[1,0,0,0,0,0,1\],\[2,0,0,0,0,3,0\],\[2,0,9,0,0,0,0\],\[0,3,0,5,4,0,0\],\[1,0,2,3,0,0,6\]\]
**输出：**28
**解释：**机器人 1 和机器人 2 的路径在上图中分别用绿色和蓝色表示。
机器人 1 摘的樱桃数目为 (1 + 9 + 5 + 2) = 17 。
机器人 2 摘的樱桃数目为 (1 + 3 + 4 + 3) = 11 。
樱桃总数为： 17 + 11 = 28 。

**示例 3：**

**输入：**grid = \[\[1,0,0,3\],\[0,0,0,3\],\[0,0,3,3\],\[9,0,3,3\]\]
**输出：**22

**示例 4：**

**输入：**grid = \[\[1,1\],\[1,1\]\]
**输出：**4

**提示：**

*   `rows == grid.length`
*   `cols == grid[i].length`
*   `2 <= rows, cols <= 70`
*   `0 <= grid[i][j] <= 100` 

[https://leetcode.cn/problems/cherry-pickup-ii/description/?envType=daily-question&envId=2024-05-07](https://leetcode.cn/problems/cherry-pickup-ii/description/?envType=daily-question&envId=2024-05-07)

```java
import java.util.Arrays;

class Solution {
    int[][][] memo;
    public int cherryPickup(int[][] grid) { 
        int m = grid.length, n = grid[0].length;
        memo = new int[m][n][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(0, 0, n - 1, grid);
    }
    private int dfs(int i, int j, int k, int[][] grid) {
        if (i == grid.length) {
            return 0;
        }
        if (memo[i][j][k] != -1) {
            return memo[i][j][k];
        }
        int ans = 0;
        for (int line1 = -1; line1 <= 1; line1++) {
            if (j + line1 < 0 || j + line1 >= grid[0].length) {
                continue;
            }
            for (int line2 = -1; line2 <= 1; line2++) {
                if (k + line2 < 0 || k + line2 >= grid[0].length) {
                    continue;
                }
                // 我的错误写法 ans = Math.max(ans, dfs(i + 1, j + line1, k + line2, grid) + j == k ? grid[i][j] : grid[i][j] + grid[i][k]); 后面这个三目表达式一定要写括号
                ans = Math.max(ans, dfs(i + 1, j + line1, k + line2, grid) + (j == k ? grid[i][j] : grid[i][j] + grid[i][k]));
            }
        }
        return memo[i][j][k] = ans;
    }
}
```

```java
import java.util.Arrays;

class Solution {
    public int cherryPickup(int[][] grid) {
        /*其中 dp[i][j][k] 表示机器人 1 到达格子 (i,j) 且机器人 2 到达格子 (i,k) 时能收集的最多樱桃数目，该状态表示为 (i,j,k)。*/
        int m = grid.length, n = grid[0].length;
        int[][][] dp = new int[m][n][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE);
            }
        }
        dp[0][0][n - 1] = grid[0][0] + grid[0][n - 1];
        int[] ds = {0, -1, 1};
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    int curr = j == k ? grid[i][j] : grid[i][j] + grid[i][k];
                    int pre = Integer.MIN_VALUE;
                    for (int d : ds) {
                        if (j + d < 0 || j + d >= n) {
                            continue;
                        }
                        for (int s : ds) {
                            int x1 = j + d, x2 = k + s;
                            if (x2 >= 0 && x2 < n) {
                                pre = Math.max(pre, dp[i - 1][x1][x2]);
                            }
                        }
                    }
                    dp[i][j][k] = curr + pre;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, Arrays.stream(dp[m - 1][i]).max().getAsInt());
        }
        return ans;
    }
}
```

1289\. 下降路径最小和 II
-----------------

给你一个 `n x n` 整数矩阵 `grid` ，请你返回 **非零偏移下降路径** 数字和的最小值。

**非零偏移下降路径** 定义为：从 `grid` 数组中的每一行选择一个数字，且按顺序选出来的数字中，相邻数字不在原数组的同一列。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/08/10/falling-grid.jpg)

**输入：**grid = \[\[1,2,3\],\[4,5,6\],\[7,8,9\]\]
**输出：**13
**解释：**
所有非零偏移下降路径包括：
\[1,5,9\], \[1,5,7\], \[1,6,7\], \[1,6,8\],
\[2,4,8\], \[2,4,9\], \[2,6,7\], \[2,6,8\],
\[3,4,8\], \[3,4,9\], \[3,5,7\], \[3,5,9\]
下降路径中数字和最小的是 \[1,5,7\] ，所以答案是 13 。

**示例 2：**

**输入：**grid = \[\[7\]\]
**输出：**7

**提示：**

*   `n == grid.length == grid[i].length`
*   `1 <= n <= 200`
*   `-99 <= grid[i][j] <= 99`

[https://leetcode.cn/problems/minimum-falling-path-sum-ii/description/](https://leetcode.cn/problems/minimum-falling-path-sum-ii/description/)

```java
import java.util.Arrays;

class Solution {
    public int minFallingPathSum(int[][] grid) {
        int n = grid.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = grid[0][i];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    if (j == k) {
                        continue;
                    }
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k] + grid[i][j]);
                }
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[n - 1][i]);
        }
        return ans;
    }
}
```

1594\. 矩阵的最大非负积
---------------

给你一个大小为 `m x n` 的矩阵 `grid` 。最初，你位于左上角 `(0, 0)` ，每一步，你可以在矩阵中 **向右** 或 **向下** 移动。

在从左上角 `(0, 0)` 开始到右下角 `(m - 1, n - 1)` 结束的所有路径中，找出具有 **最大非负积** 的路径。路径的积是沿路径访问的单元格中所有整数的乘积。

返回 **最大非负积** 对 **`109 + 7`** **取余** 的结果。如果最大积为 **负数** ，则返回 `-1` 。

**注意，**取余是在得到最大积之后执行的。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/12/23/product1.jpg)

**输入：**grid = \[\[-1,-2,-3\],\[-2,-3,-3\],\[-3,-3,-2\]\]
**输出：**\-1
**解释：**从 (0, 0) 到 (2, 2) 的路径中无法得到非负积，所以返回 -1 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2021/12/23/product2.jpg)

**输入：**grid = \[\[1,-2,1\],\[1,-2,1\],\[3,-4,1\]\]
**输出：**8
**解释：**最大非负积对应的路径如图所示 (1 \* 1 \* -2 \* -4 \* 1 = 8)

**示例 3：**

![](https://assets.leetcode.com/uploads/2021/12/23/product3.jpg)

**输入：**grid = \[\[1,3\],\[0,-4\]\]
**输出：**0
**解释：**最大非负积对应的路径如图所示 (1 \* 0 \* -4 = 0)

**提示：**

*   `m == grid.length`
*   `n == grid[i].length`
*   `1 <= m, n <= 15`
*   `-4 <= grid[i][j] <= 4`

[https://leetcode.cn/problems/maximum-non-negative-product-in-a-matrix/description/](https://leetcode.cn/problems/maximum-non-negative-product-in-a-matrix/description/)

```java
class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        long[][] dpMax = new long[m][n];
        long[][] dpMin = new long[m][n];
        dpMax[0][0] = grid[0][0];
        dpMin[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            dpMax[i][0] = dpMax[i - 1][0] * grid[i][0];
            dpMin[i][0] = dpMin[i - 1][0] * grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dpMax[0][i] = dpMax[0][i - 1] * grid[0][i];
            dpMin[0][i] = dpMin[0][i - 1] * grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] > 0) {
                    dpMax[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                    dpMin[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                } else if (grid[i][j] < 0) {
                    dpMin[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                    dpMax[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                }else{
                    dpMin[i][j] = 0;
                    dpMax[i][j] = 0;
                }
            }
        }
        return dpMax[m - 1][n - 1] >= 0 ? (int) (dpMax[m - 1][n - 1] % Mod) : -1;
    }
}
```

1301\. 最大得分的路径数目
----------------

给你一个正方形字符数组 `board` ，你从数组最右下方的字符 `'S'` 出发。

你的目标是到达数组最左上角的字符 `'E'` ，数组剩余的部分为数字字符 `1, 2, ..., 9` 或者障碍 `'X'`。在每一步移动中，你可以向上、向左或者左上方移动，可以移动的前提是到达的格子没有障碍。

一条路径的 「得分」 定义为：路径上所有数字的和。

请你返回一个列表，包含两个整数：第一个整数是 「得分」 的最大值，第二个整数是得到最大得分的方案数，请把结果对 **`10^9 + 7`** **取余**。

如果没有任何路径可以到达终点，请返回 `[0, 0]` 。

**示例 1：**

**输入：**board = \["E23","2X2","12S"\]
**输出：**\[7,1\]

**示例 2：**

**输入：**board = \["E12","1X1","21S"\]
**输出：**\[4,2\]

**示例 3：**

**输入：**board = \["E11","XXX","11S"\]
**输出：**\[0,0\]

**提示：**

*   `2 <= board.length == board[i].length <= 100`

[https://leetcode.cn/problems/number-of-paths-with-max-score/description/](https://leetcode.cn/problems/number-of-paths-with-max-score/description/)

```java
import java.util.List;

class Solution {
    public int[] pathsWithMaxScore(List<String> board) {
        int Mod = (int) 1e9 + 7;
        int n = board.size();
        int[][][] dp = new int[n + 1][n + 1][2];
        dp[n][n][1] = 1; // 最开始就一种方法
        for (int i = n; i > 0; i--) {
            String s = board.get(i - 1);
            for (int j = n; j > 0; j--) {
                char c = s.charAt(j - 1);
                if (c != 'X' && (dp[i][j][1] > 0 || dp[i - 1][j][1] > 0 || dp[i][j - 1][1] > 0)) {
                    int maxScore = Math.max(dp[i][j][0], Math.max(dp[i - 1][j][0], dp[i][j - 1][0]));
                    dp[i - 1][j - 1][0] = maxScore + c - '0';
                    if (dp[i][j][0] == maxScore) {
                        dp[i - 1][j - 1][1] += dp[i][j][1] % Mod;
                    }
                    if (dp[i - 1][j][0] == maxScore) {
                        dp[i - 1][j - 1][1] += dp[i - 1][j][1] % Mod;
                    }
                    if (dp[i][j- 1][0] == maxScore) {
                        dp[i - 1][j - 1][1] += dp[i][j - 1][1] % Mod;
                    }
                }
            }
        }
        return new int[]{Math.max(0, dp[0][0][0] - ('E' + 'S' - '0' - '0')), dp[0][0][1] % Mod};
    }
}
```

2435\. 矩阵中和能被 K 整除的路径
---------------------

给你一个下标从 **0** 开始的 `m x n` 整数矩阵 `grid` 和一个整数 `k` 。你从起点 `(0, 0)` 出发，每一步只能往 **下** 或者往 **右** ，你想要到达终点 `(m - 1, n - 1)` 。

请你返回路径和能被 `k` 整除的路径数目，由于答案可能很大，返回答案对 `109 + 7` **取余** 的结果。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/08/13/image-20220813183124-1.png)

**输入：**grid = \[\[5,2,4\],\[3,0,5\],\[0,7,2\]\], k = 3
**输出：**2
**解释：**有两条路径满足路径上元素的和能被 k 整除。
第一条路径为上图中用红色标注的路径，和为 5 + 2 + 4 + 5 + 2 = 18 ，能被 3 整除。
第二条路径为上图中用蓝色标注的路径，和为 5 + 3 + 0 + 5 + 2 = 15 ，能被 3 整除。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/08/17/image-20220817112930-3.png)

**输入：**grid = \[\[0,0\]\], k = 5
**输出：**1
**解释：**红色标注的路径和为 0 + 0 = 0 ，能被 5 整除。

**示例 3：**

![](https://assets.leetcode.com/uploads/2022/08/12/image-20220812224605-3.png)

**输入：**grid = \[\[7,3,4,9\],\[2,3,6,2\],\[2,3,7,0\]\], k = 1
**输出：**10
**解释：**每个数字都能被 1 整除，所以每一条路径的和都能被 k 整除。

**提示：**

*   `m == grid.length`
*   `n == grid[i].length`
*   `1 <= m, n <= 5 * 104`
*   `1 <= m * n <= 5 * 104`
*   `0 <= grid[i][j] <= 100`
*   `1 <= k <= 50`

[https://leetcode.cn/problems/paths-in-matrix-whose-sum-is-divisible-by-k/description/](https://leetcode.cn/problems/paths-in-matrix-whose-sum-is-divisible-by-k/description/)

```java
class Solution {
    /*  超时
    private int ans = 0;
    public int numberOfPaths(int[][] grid, int k) {
        int Mod = (int) 1e9 + 7;
        int m = grid.length, n = grid[0].length;
        dfs(grid, 0, 0, 0, m, n, k);
        return ans;
    }

    private void dfs(int[][] grid, int sum, int i, int j, int m, int n, int k) {
        if (i == m || j == n) {
            return;
        }
        sum += grid[i][j];
        if (i == m - 1 && j == n - 1) {
            if (sum % k == 0) {
                ans++;
            }
            return;
        }
        dfs(grid, sum, i + 1, j, m, n, k);
        dfs(grid, sum, i, j + 1, m, n, k);
    }*/
    public int numberOfPaths(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length, Mod = (int) 1e9 + 7;
        /*套路：把路径和模 k 的结果当成一个扩展维度。
        具体地，定义 f[i][j][v] 表示从左上走到 (i,j)，且路径和模 k 的结果为 v 时的路径数。 */
        int[][][] dp = new int[m + 1][n + 1][k];
        dp[1][0][0] = 1; // dp[0][1][0] = 1也行
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int l = 0; l < k; l++) {
                    dp[i + 1][j + 1][(l + grid[i][j]) % k] = (dp[i + 1][j][l] + dp[i][j + 1][l]) % Mod;
                }
            }
        }
        return dp[m][n][0];
    }
}
```

174\. 地下城游戏
-----------

table.dungeon, .dungeon th, .dungeon td { border:3px solid black; } .dungeon th, .dungeon td { text-align: center; height: 70px; width: 70px; }

恶魔们抓住了公主并将她关在了地下城 `dungeon` 的 **右下角** 。地下城是由 `m x n` 个房间组成的二维网格。我们英勇的骑士最初被安置在 **左上角** 的房间里，他必须穿过地下城并通过对抗恶魔来拯救公主。

骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。

有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为_负整数_，则表示骑士将损失健康点数）；其他房间要么是空的（房间里的值为 _0_），要么包含增加骑士健康点数的魔法球（若房间里的值为_正整数_，则表示骑士将增加健康点数）。

为了尽快解救公主，骑士决定每次只 **向右** 或 **向下** 移动一步。

返回确保骑士能够拯救到公主所需的最低初始健康点数。

**注意：**任何房间都可能对骑士的健康点数造成威胁，也可能增加骑士的健康点数，包括骑士进入的左上角房间以及公主被监禁的右下角房间。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/03/13/dungeon-grid-1.jpg)

**输入：**dungeon = \[\[-2,-3,3\],\[-5,-10,1\],\[10,30,-5\]\]
**输出：**7
**解释：**如果骑士遵循最佳路径：右 -> 右 -> 下 -> 下 ，则骑士的初始健康点数至少为 7 。

**示例 2：**

**输入：**dungeon = \[\[0\]\]
**输出：**1

**提示：**

*   `m == dungeon.length`
*   `n == dungeon[i].length`
*   `1 <= m, n <= 200`
*   `-1000 <= dungeon[i][j] <= 1000`

[https://leetcode.cn/problems/dungeon-game/description/](https://leetcode.cn/problems/dungeon-game/description/)

```java
class Solution {
    public int calculateMinimumHP(int[][] dungeon) {
        int n = dungeon.length, m = dungeon[0].length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 0; i <= n; ++i) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[n][m - 1] = dp[n - 1][m] = 1;
        for (int i = n - 1; i >= 0; --i) {
            for (int j = m - 1; j >= 0; --j) {
                int minn = Math.min(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = Math.max(minn - dungeon[i][j], 1);
            }
        }
        return dp[0][0];
    }
}
```

329\. 矩阵中的最长递增路径
----------------

给定一个 `m x n` 整数矩阵 `matrix` ，找出其中 **最长递增路径** 的长度。

对于每个单元格，你可以往上，下，左，右四个方向移动。 你 **不能** 在 **对角线** 方向上移动或移动到 **边界外**（即不允许环绕）。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/01/05/grid1.jpg)

**输入：**matrix = \[\[9,9,4\],\[6,6,8\],\[2,1,1\]\]
**输出：**4 
**解释：**最长递增路径为 `[1, 2, 6, 9]`。

**示例 2：**

![](https://assets.leetcode.com/uploads/2021/01/27/tmp-grid.jpg)

**输入：**matrix = \[\[3,4,5\],\[3,2,6\],\[2,2,1\]\]
**输出：**4 
**解释：**最长递增路径是 `[3, 4, 5, 6]`。注意不允许在对角线方向上移动。

**示例 3：**

**输入：**matrix = \[\[1\]\]
**输出：**1

**提示：**

*   `m == matrix.length`
*   `n == matrix[i].length`
*   `1 <= m, n <= 200`
*   `0 <= matrix[i][j] <= 231 - 1`

[https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/description/](https://leetcode.cn/problems/longest-increasing-path-in-a-matrix/description/)

```java
class Solution {
    static int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int[][] matrix;
    int[][] memo;
    int m, n;
    public int longestIncreasingPath(int[][] matrix) {
        m = matrix.length;
        n = matrix[0].length;
        this.matrix = matrix;
        memo = new int[m][n];
        int maxLength = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                maxLength = Math.max(maxLength, dfs(i, j));
            }
        }
        return maxLength;
    }

    private int dfs(int i, int j) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        memo[i][j] = 1;
        for (int[] d : dirs) {
            int newI = i + d[0], newJ = j + d[1];
            if (newI >= 0 && newI < m && newJ >= 0 && newJ < n && matrix[newI][newJ] < matrix[i][j]) {
                memo[i][j] = Math.max(memo[i][j], dfs(newI, newJ) + 1);
            }
        }
        return memo[i][j];
    }
}
```

2328\. 网格图中递增路径的数目
------------------

给你一个 `m x n` 的整数网格图 `grid` ，你可以从一个格子移动到 `4` 个方向相邻的任意一个格子。

请你返回在网格图中从 **任意** 格子出发，达到 **任意** 格子，且路径中的数字是 **严格递增** 的路径数目。由于答案可能会很大，请将结果对 `109 + 7` **取余** 后返回。

如果两条路径中访问过的格子不是完全相同的，那么它们视为两条不同的路径。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/05/10/griddrawio-4.png)

**输入：**grid = \[\[1,1\],\[3,4\]\]
**输出：**8
**解释：**严格递增路径包括：
- 长度为 1 的路径：\[1\]，\[1\]，\[3\]，\[4\] 。
- 长度为 2 的路径：\[1 -> 3\]，\[1 -> 4\]，\[3 -> 4\] 。
- 长度为 3 的路径：\[1 -> 3 -> 4\] 。
  路径数目为 4 + 3 + 1 = 8 。

**示例 2：**

**输入：**grid = \[\[1\],\[2\]\]
**输出：**3
**解释：**严格递增路径包括：
- 长度为 1 的路径：\[1\]，\[2\] 。
- 长度为 2 的路径：\[1 -> 2\] 。
  路径数目为 2 + 1 = 3 。

**提示：**

*   `m == grid.length`
*   `n == grid[i].length`
*   `1 <= m, n <= 1000`
*   `1 <= m * n <= 105`
*   `1 <= grid[i][j] <= 105`

[https://leetcode.cn/problems/number-of-increasing-paths-in-a-grid/description/](https://leetcode.cn/problems/number-of-increasing-paths-in-a-grid/description/)

```java
class Solution {
    static int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private int Mod = (int) 1e9 + 7;
    int[][] memo;
    int[][] grid;
    int m, n;
    public int countPaths(int[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;
        memo = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans = (ans + dfs(i, j)) % Mod;
            }
        }
        return ans % Mod;
    }

    private int dfs(int i, int j) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        memo[i][j] = 1;
        for (int[] d : dirs) {
            int newI = i + d[0], newJ = j + d[1];
            if (newI >= 0 && newI < m && newJ >= 0 && newJ < n && grid[i][j] > grid[newI][newJ]) {
                memo[i][j] = (memo[i][j] + dfs(newI, newJ)) % Mod;
            }
        }
        return memo[i][j];
    }
}
```

2267\. 检查是否有合法括号字符串路径
---------------------

一个括号字符串是一个 **非空** 且只包含 `'('` 和 `')'` 的字符串。如果下面 **任意** 条件为 **真** ，那么这个括号字符串就是 **合法的** 。

*   字符串是 `()` 。
*   字符串可以表示为 `AB`（`A` 连接 `B`），`A` 和 `B` 都是合法括号序列。
*   字符串可以表示为 `(A)` ，其中 `A` 是合法括号序列。

给你一个 `m x n` 的括号网格图矩阵 `grid` 。网格图中一个 **合法括号路径** 是满足以下所有条件的一条路径：

*   路径开始于左上角格子 `(0, 0)` 。
*   路径结束于右下角格子 `(m - 1, n - 1)` 。
*   路径每次只会向 **下** 或者向 **右** 移动。
*   路径经过的格子组成的括号字符串是 **合法** 的。

如果网格图中存在一条 **合法括号路径** ，请返回 `true` ，否则返回 `false` 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/03/15/example1drawio.png)

**输入：**grid = \[\["(","(","("\],\[")","(",")"\],\["(","(",")"\],\["(","(",")"\]\]
**输出：**true
**解释：**上图展示了两条路径，它们都是合法括号字符串路径。
第一条路径得到的合法字符串是 "()(())" 。
第二条路径得到的合法字符串是 "((()))" 。
注意可能有其他的合法括号字符串路径。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/03/15/example2drawio.png)

**输入：**grid = \[\[")",")"\],\["(","("\]\]
**输出：**false
**解释：**两条可行路径分别得到 "))(" 和 ")((" 。由于它们都不是合法括号字符串，我们返回 false 。

**提示：**

*   `m == grid.length`
*   `n == grid[i].length`
*   `1 <= m, n <= 100`
*   `grid[i][j]` 要么是 `'('` ，要么是 `')'` 。

[https://leetcode.cn/problems/check-if-there-is-a-valid-parentheses-string-path/description/](https://leetcode.cn/problems/check-if-there-is-a-valid-parentheses-string-path/description/)

```java
class Solution {
    char[][] grid;
    int m, n;
    boolean[][][] dp;
    public boolean hasValidPath(char[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;
        if ((m + n) % 2 == 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        dp = new boolean[m][n][m + n];
        return dfs(0, 0, 0);
    }

    private boolean dfs(int i, int j, int c) {
        if (c > m - i + n - j - 1) {
            return false;
        }
        if (i == m - 1 && j == n - 1) {
            return c == 1;
        }
        if (dp[i][j][c]) {
            return false;
        }
        dp[i][j][c] = true;
        c += grid[i][j] == '(' ? 1 : -1;
        return c >= 0 && (i < m - 1 && dfs(i + 1, j, c) || j < n - 1 && dfs(i, j + 1, c));
    }
}
```

1293\. 网格中的最短路径
---------------

给你一个 `m * n` 的网格，其中每个单元格不是 `0`（空）就是 `1`（障碍物）。每一步，您都可以在空白单元格中上、下、左、右移动。

如果您 **最多** 可以消除 `k` 个障碍物，请找出从左上角 `(0, 0)` 到右下角 `(m-1, n-1)` 的最短路径，并返回通过该路径所需的步数。如果找不到这样的路径，则返回 `-1` 。

**示例 1：**

![](https://pic.leetcode.cn/1700710956-kcxqcC-img_v3_025f_d55a658c-8f40-464b-800f-22ccd27cc9fg.jpg)

**输入：** grid = \[\[0,0,0\],\[1,1,0\],\[0,0,0\],\[0,1,1\],\[0,0,0\]\], k = 1
**输出：**6
**解释：**
不消除任何障碍的最短路径是 10。
消除位置 (3,2) 处的障碍后，最短路径是 6 。该路径是 `(0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2) -> **(3,2)** -> (4,2)`.

**示例 2：**

![](https://pic.leetcode.cn/1700710701-uPqkZe-img_v3_025f_0edd50fb-8a70-4a42-add0-f602caaad35g.jpg)

**输入：**grid = \[\[0,1,1\],\[1,1,1\],\[1,0,0\]\], k = 1
**输出：**\-1
**解释：**我们至少需要消除两个障碍才能找到这样的路径。

**提示：**

*   `grid.length == m`
*   `grid[0].length == n`
*   `1 <= m, n <= 40`
*   `1 <= k <= m*n`
*   `grid[i][j]` 是 `0` 或 `1`
*   `grid[0][0] == grid[m-1][n-1] == 0`

[https://leetcode.cn/problems/shortest-path-in-a-grid-with-obstacles-elimination/description/](https://leetcode.cn/problems/shortest-path-in-a-grid-with-obstacles-elimination/description/)

```java
import java.util.ArrayDeque;
import java.util.Arrays;

class Solution { // 只能广度优先
    public int shortestPath(int[][] grid, int k) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int m = grid.length, n = grid[0].length;
        if (m + n - 3 <= k) {
            return m + n - 2;
        }
        int[][][] dp = new int[m][n][k + 1]; // 其中 m 和 n 分别是矩阵的行数和列数，k 是最多可以消除的障碍物个数。
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE); // 步数
            }
        }
        dp[0][0][0] = 0;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offerLast(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] poll = queue.pollFirst();
            int x = poll[0], y = poll[1], z = poll[2];
            if (x == m - 1 && y == n - 1) { // 广度搜索的特性，步数 小->大
                return dp[x][y][z];
            }
            for (int[] dir : dirs) {
                int newX = x + dir[0], newY = y + dir[1];
                if (newX >= 0 && newX < m && newY >= 0 && newY < n) {
                    if (grid[newX][newY] == 0) {
                        if (dp[newX][newY][z] == Integer.MAX_VALUE) {
                            dp[newX][newY][z] = dp[x][y][z] + 1;
                            queue.offerLast(new int[]{newX, newY, z});
                        }
                    }else{
                        if (z < k && dp[newX][newY][z + 1] == Integer.MAX_VALUE) {
                            dp[newX][newY][z + 1] = dp[x][y][z] + 1;
                            queue.offerLast(new int[]{newX, newY, z + 1});
                        }
                    }
                }
            }
        }
        return -1;
    }
}
```

1937\. 扣分后的最大得分
---------------

给你一个 `m x n` 的整数矩阵 `points` （下标从 **0** 开始）。一开始你的得分为 `0` ，你想最大化从矩阵中得到的分数。

你的得分方式为：**每一行** 中选取一个格子，选中坐标为 `(r, c)` 的格子会给你的总得分 **增加** `points[r][c]` 。

然而，相邻行之间被选中的格子如果隔得太远，你会失去一些得分。对于相邻行 `r` 和 `r + 1` （其中 `0 <= r < m - 1`），选中坐标为 `(r, c1)` 和 `(r + 1, c2)` 的格子，你的总得分 **减少** `abs(c1 - c2)` 。

请你返回你能得到的 **最大** 得分。

`abs(x)` 定义为：

*   如果 `x >= 0` ，那么值为 `x` 。
*   如果 `x < 0` ，那么值为 `-x` 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/07/12/screenshot-2021-07-12-at-13-40-26-diagram-drawio-diagrams-net.png)

**输入：**points = \[\[1,2,3\],\[1,5,1\],\[3,1,1\]\]
**输出：**9
**解释：**
蓝色格子是最优方案选中的格子，坐标分别为 (0, 2)，(1, 1) 和 (2, 0) 。
你的总得分增加 3 + 5 + 3 = 11 。
但是你的总得分需要扣除 abs(2 - 1) + abs(1 - 0) = 2 。
你的最终得分为 11 - 2 = 9 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2021/07/12/screenshot-2021-07-12-at-13-42-14-diagram-drawio-diagrams-net.png)

**输入：**points = \[\[1,5\],\[2,3\],\[4,2\]\]
**输出：**11
**解释：**
蓝色格子是最优方案选中的格子，坐标分别为 (0, 1)，(1, 1) 和 (2, 0) 。
你的总得分增加 5 + 3 + 4 = 12 。
但是你的总得分需要扣除 abs(1 - 1) + abs(1 - 0) = 1 。
你的最终得分为 12 - 1 = 11 。

**提示：**

*   `m == points.length`
*   `n == points[r].length`
*   `1 <= m, n <= 105`
*   `1 <= m * n <= 105`
*   `0 <= points[r][c] <= 105`

[https://leetcode.cn/problems/maximum-number-of-points-with-cost/description/](https://leetcode.cn/problems/maximum-number-of-points-with-cost/description/)

```java
import java.util.Arrays;

class Solution {
    public long maxPoints(int[][] points) {
        int m = points.length, n = points[0].length;
        long[][] dp = new long[m][n];
        for (int j = 0; j < n; j++) {
            dp[0][j] = points[0][j];
        }
        for (int i = 1; i < m; i++) {
            long ret = dp[i - 1][0] + 0;
            for (int j = 0; j < n; j++) {
                ret = Math.max(ret, dp[i - 1][j] + j);
                dp[i][j] = points[i][j] - j + ret;
            }
            ret = dp[i - 1][n - 1] - (n - 1);
            for (int j = n - 2; j >= 0; j--) {
                dp[i][j] = Math.max(dp[i][j], points[i][j] +j+ ret);
                ret = Math.max(ret, dp[i - 1][j] - j);
            }
        }
        long ans = Arrays.stream(dp[m - 1]).max().getAsLong();
        return ans;
    }
}
```

3122\. 使矩阵满足条件的最少操作次数
---------------------

给你一个大小为 `m x n` 的二维矩形 `grid` 。每次 **操作** 中，你可以将 **任一** 格子的值修改为 **任意** 非负整数。完成所有操作后，你需要确保每个格子 `grid[i][j]` 的值满足：

*   如果下面相邻格子存在的话，它们的值相等，也就是 `grid[i][j] == grid[i + 1][j]`（如果存在）。
*   如果右边相邻格子存在的话，它们的值不相等，也就是 `grid[i][j] != grid[i][j + 1]`（如果存在）。

请你返回需要的 **最少** 操作数目。

**示例 1：**

**输入：**grid = \[\[1,0,2\],\[1,0,2\]\]

**输出：**0

**解释：**

**![](https://assets.leetcode.com/uploads/2024/04/15/examplechanged.png)**

矩阵中所有格子已经满足要求。

**示例 2：**

**输入：**grid = \[\[1,1,1\],\[0,0,0\]\]

**输出：**3

**解释：**

**![](https://assets.leetcode.com/uploads/2024/03/27/example21.png)**

将矩阵变成 `[[1,0,1],[1,0,1]]` ，它满足所有要求，需要 3 次操作：

*   将 `grid[1][0]` 变为 1 。
*   将 `grid[0][1]` 变为 0 。
*   将 `grid[1][2]` 变为 1 。

**示例 3：**

**输入：**grid = \[\[1\],\[2\],\[3\]\]

**输出：**2

**解释：**

![](https://assets.leetcode.com/uploads/2024/03/31/changed.png)

这个矩阵只有一列，我们可以通过 2 次操作将所有格子里的值变为 1 。

**提示：**

*   `1 <= n, m <= 1000`
*   `0 <= grid[i][j] <= 9`

[https://leetcode.cn/problems/minimum-number-of-operations-to-satisfy-conditions/description/](https://leetcode.cn/problems/minimum-number-of-operations-to-satisfy-conditions/description/)

```java
public int minimumOperations(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] memo = new int[n][11];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        int[][] cnt = new int[n][10];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                cnt[j][grid[i][j]]++;
            }
        }
        return m * n - dfs(n - 1, 10, memo, cnt);
    }
    private int dfs(int i, int j, int[][] memo, int[][] cnt) { // dfs(i,j) 表示前i列全部变成j最大保留的数目
        if (i < 0) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        int res = 0;
        for (int k = 0; k < 10; k++) {
            if (k != j) {
                res = Math.max(res, dfs(i - 1, k, memo, cnt) + cnt[i][k]);
            }
        }
        return memo[i][j] = res;
    }
```

```java
public int minimumOperations_DP(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] cnt = new int[n][10];
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m; i++) {
                cnt[j][grid[i][j]]++;
            }
        }
        int[][] dp = new int[n][10]; // dp[i][j]表示第i列变成j能保留的最多数
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], -1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0) {
                    dp[i][j] = cnt[i][j];
                    continue;
                }
                for (int k = 0; k < 10; k++) {
                    if (j != k) {
                        dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + cnt[i][j]);
                    }
                }
            }
        }
        int mx = 0;
        for (int i = 0; i < 10; i++) {
            mx = Math.max(mx, dp[n - 1][i]);
        }
        return m * n - mx;
    }
```

