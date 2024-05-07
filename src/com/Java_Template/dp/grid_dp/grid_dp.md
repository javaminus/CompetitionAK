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

