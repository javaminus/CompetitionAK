package com.Java_Template.dp.grid_dp;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

/**
 * @author Minus
 * @date 2024/4/13 14:41
 *
 * 网格dp
 */
public class problemImpl {
    /* 1289. 下降路径最小和 II

       给你一个 n x n 整数矩阵 grid ，请你返回 非零偏移下降路径 数字和的最小值。
       非零偏移下降路径 定义为：从 grid 数组中的每一行选择一个数字，且按顺序选出来的数字中，相邻数字不在原数组的同一列 */
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

    /* 1594. 矩阵的最大非负积
        给你一个大小为 m x n 的矩阵 grid 。最初，你位于左上角 (0, 0) ，每一步，你可以在矩阵中 向右 或 向下 移动。
        在从左上角 (0, 0) 开始到右下角 (m - 1, n - 1) 结束的所有路径中，找出具有 最大非负积 的路径。路径的积是沿路径访问的单元格中所有整数的乘积。
        返回 最大非负积 对 109 + 7 取余 的结果。如果最大积为 负数 ，则返回 -1 。
        注意，取余是在得到最大积之后执行的。 */
    public int maxProductPath(int[][] grid) {
        int m = grid.length, n = grid[0].length, Mod = (int) 1e9 + 7;
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
                    dpMax[i][j] = Math.min(dpMin[i - 1][j], dpMin[i][j - 1]) * grid[i][j];
                    dpMin[i][j] = Math.max(dpMax[i - 1][j], dpMax[i][j - 1]) * grid[i][j];
                } else if (grid[i][j] == 0) {
                    dpMax[i][j] = 0;
                    dpMin[i][j] = 0;
                }
            }
        }
        return (int) (Math.max(dpMax[m - 1][n - 1], -1) % Mod);
    }


    /*  1301. 最大得分的路径数目

        给你一个正方形字符数组 board ，你从数组最右下方的字符 'S' 出发。

        你的目标是到达数组最左上角的字符 'E' ，数组剩余的部分为数字字符 1, 2, ..., 9 或者障碍 'X'。在每一步移动中，你可以向上、向左或者左上方移动，可以移动的前提是到达的格子没有障碍。

        一条路径的 「得分」 定义为：路径上所有数字的和。

        请你返回一个列表，包含两个整数：第一个整数是 「得分」 的最大值，第二个整数是得到最大得分的方案数，请把结果对 10^9 + 7 取余。

        如果没有任何路径可以到达终点，请返回 [0, 0] 。*/
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


    /*  题目：2435. 矩阵中和能被 K 整除的路径
        给你一个下标从 0 开始的 m x n 整数矩阵 grid 和一个整数 k 。你从起点 (0, 0) 出发，每一步只能往 下 或者往 右 ，你想要到达终点 (m - 1, n - 1) 。
        请你返回路径和能被 k 整除的路径数目，由于答案可能很大，返回答案对 109 + 7 取余 的结果。*/

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


    /*174. 地下城游戏
        恶魔们抓住了公主并将她关在了地下城 dungeon 的 右下角 。地下城是由 m x n 个房间组成的二维网格。我们英勇的骑士最初被安置在 左上角 的房间里，他必须穿过地下城并通过对抗恶魔来拯救公主。

        骑士的初始健康点数为一个正整数。如果他的健康点数在某一时刻降至 0 或以下，他会立即死亡。

        有些房间由恶魔守卫，因此骑士在进入这些房间时会失去健康点数（若房间里的值为负整数，则表示骑士将损失健康点数）；其他房间要么是空的（房间里的值为 0），要么包含增加骑士健康点数的魔法球（若房间里的值为正整数，则表示骑士将增加健康点数）。

        为了尽快解救公主，骑士决定每次只 向右 或 向下 移动一步。

        返回确保骑士能够拯救到公主所需的最低初始健康点数。

        注意：任何房间都可能对骑士的健康点数造成威胁，也可能增加骑士的健康点数，包括骑士进入的左上角房间以及公主被监禁的右下角房间*/
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

    /*  741. 摘樱桃
        给你一个 n x n 的网格 grid ，代表一块樱桃地，每个格子由以下三种数字的一种来表示：
        0 表示这个格子是空的，所以你可以穿过它。
        1 表示这个格子里装着一个樱桃，你可以摘到樱桃然后穿过它。
        -1 表示这个格子里有荆棘，挡着你的路。
        请你统计并返回：在遵守下列规则的情况下，能摘到的最多樱桃数：
        从位置 (0, 0) 出发，最后到达 (n - 1, n - 1) ，只能向下或向右走，并且只能穿越有效的格子（即只可以穿过值为 0 或者 1 的格子）；
        当到达 (n - 1, n - 1) 后，你要继续走，直到返回到 (0, 0) ，只能向上或向左走，并且只能穿越有效的格子；
        当你经过一个格子且这个格子包含一个樱桃时，你将摘到樱桃并且这个格子会变成空的（值变为 0 ）；
        如果在 (0, 0) 和 (n - 1, n - 1) 之间不存在一条可经过的路径，则无法摘到任何一个樱桃。*/
    private static int[][] directions = new int[][]{{0, 0}, {-1, 0}, {0, -1}, {-1, -1}}; // (x1,x2):（下，下），（下，右），（右，下），（右，右）
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int maxSteps = (n - 1) * 2;
        int[][][] dp = new int[maxSteps + 1][n][n];
        for (int i = 0; i <= maxSteps; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], Integer.MIN_VALUE);
            }
        }
        dp[0][0][0] = grid[0][0];
        for (int i = 1; i <= maxSteps; i++) { // 枚举步数
            int maxRow = Math.min(i, n - 1), minRow = Math.max(0, i - n + 1);
            for (int j = minRow; j <= maxRow; j++) { // 枚举行x1
                if (grid[j][i - j] == -1) {
                    continue;
                }
                for (int k = j; k <= maxRow; k++) { // 枚举行x2，减枝
                    if (grid[k][i - k] == -1) {
                        continue;
                    }
                    int curr = k == j ? grid[j][i - j] : grid[j][i - j] + grid[k][i - k];
                    int prev = Integer.MIN_VALUE;
                    for (int[] d : directions) {
                        int prevRow1 = j + d[0], prevRow2 = k + d[1];
                        if (prevRow1 >= 0 && prevRow2 >= 0) {
                            prev = Math.max(prev, dp[i - 1][prevRow1][prevRow2]);
                        }
                    }
                    dp[i][j][k] = prev + curr;
                }
            }
        }
        return Math.max(dp[maxSteps][n - 1][n - 1], 0);
    }

    /*  1463. 摘樱桃 II
        给你一个 rows x cols 的矩阵 grid 来表示一块樱桃地。 grid 中每个格子的数字表示你能获得的樱桃数目。
        你有两个机器人帮你收集樱桃，机器人 1 从左上角格子 (0,0) 出发，机器人 2 从右上角格子 (0, cols-1) 出发。
        请你按照如下规则，返回两个机器人能收集的最多樱桃数目：

        从格子 (i,j) 出发，机器人可以移动到格子 (i+1, j-1)，(i+1, j) 或者 (i+1, j+1) 。
        当一个机器人经过某个格子时，它会把该格子内所有的樱桃都摘走，然后这个位置会变成空格子，即没有樱桃的格子。
        当两个机器人同时到达同一个格子时，它们中只有一个可以摘到樱桃。
        两个机器人在任意时刻都不能移动到 grid 外面。
        两个机器人最后都要到达 grid 最底下一行。*/
    public int cherryPickup2(int[][] grid) {
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

    /*  329. 矩阵中的最长递增路径
        给定一个 m x n 整数矩阵 matrix ，找出其中 最长递增路径 的长度。
        对于每个单元格，你可以往上，下，左，右四个方向移动。 你 不能 在 对角线 方向上移动或移动到 边界外（即不允许环绕）。*/
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


    /*  2328. 网格图中递增路径的数目
        给你一个 m x n 的整数网格图 grid ，你可以从一个格子移动到 4 个方向相邻的任意一个格子。
        请你返回在网格图中从 任意 格子出发，达到 任意 格子，且路径中的数字是 严格递增 的路径数目。由于答案可能会很大，请将结果对 109 + 7 取余 后返回。
        如果两条路径中访问过的格子不是完全相同的，那么它们视为两条不同的路径。*/
//    static int[][] dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
//    int[][] memo;
//    int m, n;
    private int Mod = (int) 1e9 + 7;
    int[][] grid;

    public int countPaths(int[][] grid) {
        this.grid = grid;
        m = grid.length;
        n = grid[0].length;
        memo = new int[m][n];
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ans = (ans + dfs1(i, j)) % Mod;
            }
        }
        return ans % Mod;
    }
    private int dfs1(int i, int j) {
        if (memo[i][j] != 0) {
            return memo[i][j];
        }
        memo[i][j] = 1;
        for (int[] d : dirs) {
            int newI = i + d[0], newJ = j + d[1];
            if (newI >= 0 && newI < m && newJ >= 0 && newJ < n && grid[i][j] > grid[newI][newJ]) {
                memo[i][j] = (memo[i][j] + dfs1(newI, newJ)) % Mod;
            }
        }
        return memo[i][j];
    }


    /*  2267. 检查是否有合法括号字符串路径
        一个括号字符串是一个 非空 且只包含 '(' 和 ')' 的字符串。如果下面 任意 条件为 真 ，那么这个括号字符串就是 合法的 。

        字符串是 () 。
        字符串可以表示为 AB（A 连接 B），A 和 B 都是合法括号序列。
        字符串可以表示为 (A) ，其中 A 是合法括号序列。
        给你一个 m x n 的括号网格图矩阵 grid 。网格图中一个 合法括号路径 是满足以下所有条件的一条路径：

        路径开始于左上角格子 (0, 0) 。
        路径结束于右下角格子 (m - 1, n - 1) 。
        路径每次只会向 下 或者向 右 移动。
        路径经过的格子组成的括号字符串是 合法 的。
        如果网格图中存在一条 合法括号路径 ，请返回 true ，否则返回 false 。*/
    //   char[][] grid1;
    //   int m,n;
    boolean[][][] dp;
    public boolean hasValidPath(char[][] grid) {
        // this.grid = grid;
        m = grid.length;
        n = grid[0].length;
        if ((m + n) % 2 == 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        dp = new boolean[m][n][(m + n + 1) / 2];
        return dfs(0, 0, 0);
    }
    private boolean dfs(int i, int j, int c) {
        if (c > m - i + n - j - 1) {
            return false; // 剪枝：即使后面都是 ')' 也不能将 c 减为 0
        }
        if (i == m - 1 && j == n - 1) {
            return c == 1; // 终点一定是 ')
        }
        if (dp[i][j][c]) { // 已经被访问了，避免重复访问
            return false;
        }
        dp[i][j][c] = true;
        c += grid[i][j] == '(' ? 1 : -1;
        return c >= 0 && (i < m - 1 && dfs(i + 1, j, c) || j < n - 1 && dfs(i, j + 1, c)); // 往下或者往右
    }


    /*  1293. 网格中的最短路径
        给你一个 m * n 的网格，其中每个单元格不是 0（空）就是 1（障碍物）。每一步，您都可以在空白单元格中上、下、左、右移动。
        如果您 最多 可以消除 k 个障碍物，请找出从左上角 (0, 0) 到右下角 (m-1, n-1) 的最短路径，并返回通过该路径所需的步数。如果找不到这样的路径，则返回 -1 。*/
    // static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public int shortestPath(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        if (k >= m + n - 3) {
            return m + n - 2;
        }
        int[][][] dp = new int[m][n][k + 1]; // 步数
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(dp[i][j], Integer.MAX_VALUE);
            }
        }
        dp[0][0][0] = 0;
        ArrayDeque<int[]> queue = new ArrayDeque<>();
        queue.offerLast(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] state = queue.pollFirst();
            int row = state[0], col = state[1], eliminations = state[2]; // eliminations:淘汰
            int d = dp[row][col][eliminations];
            if (row == m - 1 && col == n - 1) { // 广度搜索的特性，步数 小->大
                return d;
            }
            for (int[] dir : dirs) {
                int newRow = row + dir[0], newCol = col + dir[1];
                if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n) {
                    if (grid[newRow][newCol] == 0) {
                        if (dp[newRow][newCol][eliminations] == Integer.MAX_VALUE) {
                            dp[newRow][newCol][eliminations] = d + 1;
                            queue.offerLast(new int[]{newRow, newCol, eliminations});
                        }
                    }else{
                        if (eliminations < k && dp[newRow][newCol][eliminations + 1] == Integer.MAX_VALUE) {
                            dp[newRow][newCol][eliminations + 1] = d + 1;
                            queue.offerLast(new int[]{newRow, newCol, eliminations + 1});
                        }
                    }
                }
            }
        }
        return -1;
    }


    /*  1937. 扣分后的最大得分 (难)：拆项+前后缀最大值
        给你一个 m x n 的整数矩阵 points （下标从 0 开始）。一开始你的得分为 0 ，你想最大化从矩阵中得到的分数。
        你的得分方式为：每一行 中选取一个格子，选中坐标为 (r, c) 的格子会给你的总得分 增加 points[r][c] 。
        然而，相邻行之间被选中的格子如果隔得太远，你会失去一些得分。对于相邻行 r 和 r + 1 （其中 0 <= r < m - 1），选中坐标为 (r, c1) 和 (r + 1, c2) 的格子，你的总得分 减少 abs(c1 - c2) 。
        请你返回你能得到的 最大 得分。
        abs(x) 定义为：
        如果 x >= 0 ，那么值为 x 。
        如果 x < 0 ，那么值为 -x 。*/
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
                dp[i][j] = Math.max(dp[i][j], points[i][j] + j + ret);
                ret = Math.max(ret, dp[i - 1][j] - j);
            }
        }
        long ans = Arrays.stream(dp[m - 1]).max().getAsLong();
        return ans;
    }
}
