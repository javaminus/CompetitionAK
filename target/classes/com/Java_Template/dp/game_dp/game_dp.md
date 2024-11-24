

1025\. 除数博弈
-----------

爱丽丝和鲍勃一起玩游戏，他们轮流行动。爱丽丝先手开局。

最初，黑板上有一个数字 `n` 。在每个玩家的回合，玩家需要执行以下操作：

*   选出任一 `x`，满足 `0 < x < n` 且 `n % x == 0` 。
*   用 `n - x` 替换黑板上的数字 `n` 。

如果玩家无法执行这些操作，就会输掉游戏。

_只有在爱丽丝在游戏中取得胜利时才返回 `true` 。假设两个玩家都以最佳状态参与游戏。_

**示例 1：**

**输入：**n = 2
**输出：**true
**解释：**爱丽丝选择 1，鲍勃无法进行操作。

**示例 2：**

**输入：**n = 3
**输出：**false
**解释：**爱丽丝选择 1，鲍勃也选择 1，然后爱丽丝无法进行操作。

**提示：**

*   `1 <= n <= 1000`

[https://leetcode.cn/problems/divisor-game/description/](https://leetcode.cn/problems/divisor-game/description/)
```java
class Solution {
    // 其中 dp[i] 为黑板上的数字等于 i 时当前玩家的游戏结果
    public boolean divisorGame(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (i % j == 0 && !dp[i - j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
```

877\. 石子游戏
----------

Alice 和 Bob 用几堆石子在做游戏。一共有偶数堆石子，**排成一行**；每堆都有 **正** 整数颗石子，数目为 `piles[i]` 。

游戏以谁手中的石子最多来决出胜负。石子的 **总数** 是 **奇数** ，所以没有平局。

Alice 和 Bob 轮流进行，**Alice 先开始** 。 每回合，玩家从行的 **开始** 或 **结束** 处取走整堆石头。 这种情况一直持续到没有更多的石子堆为止，此时手中 **石子最多** 的玩家 **获胜** 。

假设 Alice 和 Bob 都发挥出最佳水平，当 Alice 赢得比赛时返回 `true` ，当 Bob 赢得比赛时返回 `false` 。

**示例 1：**

**输入：**piles = \[5,3,4,5\]
**输出：**true
**解释：**
Alice 先开始，只能拿前 5 颗或后 5 颗石子 。
假设他取了前 5 颗，这一行就变成了 \[3,4,5\] 。
如果 Bob 拿走前 3 颗，那么剩下的是 \[4,5\]，Alice 拿走后 5 颗赢得 10 分。
如果 Bob 拿走后 5 颗，那么剩下的是 \[3,4\]，Alice 拿走后 4 颗赢得 9 分。
这表明，取前 5 颗石子对 Alice 来说是一个胜利的举动，所以返回 true 。

**示例 2：**

**输入：**piles = \[3,7,2,3\]
**输出：**true

**提示：**

*   `2 <= piles.length <= 500`
*   `piles.length` 是 **偶数**
*   `1 <= piles[i] <= 500`
*   `sum(piles[i])` 是 **奇数**

[https://leetcode.cn/problems/stone-game/description/](https://leetcode.cn/problems/stone-game/description/)

```java
import java.util.Arrays;

class Solution {
    int[][] memo;
    public boolean stoneGame(int[] piles) { // 记忆化搜索
        int n = piles.length;
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, n - 1, piles) >= 0;
    }
    private int dfs(int i, int j, int[] piles) { // 表示最大分差
        if (i == j) {
            return piles[i];
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        return memo[i][j] = Math.max(piles[i] - dfs(i + 1, j, piles), piles[j] - dfs(i, j - 1, piles));
    }
}
```

```java
class Solution {
    public boolean stoneGame(int[] piles) { // 递推
        int n = piles.length;
        int[][] dp = new int[n][n]; // dp[i][j]表示区间[i,j]选择的最大差值
        for (int i = 0; i < n; i++) {
            dp[i][i] = piles[i];
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = n - 1; j > 0; j--) {
                dp[i][j] = Math.max(piles[i] - dp[i + 1][j], piles[j] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] > 0;
    }
}
```

486\. 预测赢家
----------

给你一个整数数组 `nums` 。玩家 1 和玩家 2 基于这个数组设计了一个游戏。

玩家 1 和玩家 2 轮流进行自己的回合，玩家 1 先手。开始时，两个玩家的初始分值都是 `0` 。每一回合，玩家从数组的任意一端取一个数字（即，`nums[0]` 或 `nums[nums.length - 1]`），取到的数字将会从数组中移除（数组长度减 `1` ）。玩家选中的数字将会加到他的得分上。当数组中没有剩余数字可取时，游戏结束。

如果玩家 1 能成为赢家，返回 `true` 。如果两个玩家得分相等，同样认为玩家 1 是游戏的赢家，也返回 `true` 。你可以假设每个玩家的玩法都会使他的分数最大化。

**示例 1：**

**输入：**nums = \[1,5,2\]
**输出：**false
**解释：**一开始，玩家 1 可以从 1 和 2 中进行选择。
如果他选择 2（或者 1 ），那么玩家 2 可以从 1（或者 2 ）和 5 中进行选择。如果玩家 2 选择了 5 ，那么玩家 1 则只剩下 1（或者 2 ）可选。 
所以，玩家 1 的最终分数为 1 + 2 = 3，而玩家 2 为 5 。
因此，玩家 1 永远不会成为赢家，返回 false 。

**示例 2：**

**输入：**nums = \[1,5,233,7\]
**输出：**true
**解释：**玩家 1 一开始选择 1 。然后玩家 2 必须从 5 和 7 中进行选择。无论玩家 2 选择了哪个，玩家 1 都可以选择 233 。
最终，玩家 1（234 分）比玩家 2（12 分）获得更多的分数，所以返回 true，表示玩家 1 可以成为赢家。

**提示：**

*   `1 <= nums.length <= 20`
*   `0 <= nums[i] <= 107`

[https://leetcode.cn/problems/predict-the-winner/description/](https://leetcode.cn/problems/predict-the-winner/description/)

```java
import java.util.Arrays;

class Solution {
    int[][] memo;
    public boolean predictTheWinner(int[] nums) { // 记忆化搜索
        int n = nums.length;
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, n - 1, nums) >= 0;
    }
    private int dfs(int i, int j, int[] piles) { // 表示最大分差
        if (i == j) {
            return piles[i];
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        return memo[i][j] = Math.max(piles[i] - dfs(i + 1, j, piles), piles[j] - dfs(i, j - 1, piles));
    }
}
```

1510\. 石子游戏 IV
--------------

Alice 和 Bob 两个人轮流玩一个游戏，Alice 先手。

一开始，有 `n` 个石子堆在一起。每个人轮流操作，正在操作的玩家可以从石子堆里拿走 **任意** 非零 **平方数** 个石子。

如果石子堆里没有石子了，则无法操作的玩家输掉游戏。

给你正整数 `n` ，且已知两个人都采取最优策略。如果 Alice 会赢得比赛，那么返回 `True` ，否则返回 `False` 。

**示例 1：**

**输入：**n = 1
**输出：**true
**解释：**Alice 拿走 1 个石子并赢得胜利，因为 Bob 无法进行任何操作。

**示例 2：**

**输入：**n = 2
**输出：**false
**解释：**Alice 只能拿走 1 个石子，然后 Bob 拿走最后一个石子并赢得胜利（2 -> 1 -> 0）。

**示例 3：**

**输入：**n = 4
**输出：**true
**解释：**n 已经是一个平方数，Alice 可以一次全拿掉 4 个石子并赢得胜利（4 -> 0）。

**示例 4：**

**输入：**n = 7
**输出：**false
**解释：**当 Bob 采取最优策略时，Alice 无法赢得比赛。
如果 Alice 一开始拿走 4 个石子， Bob 会拿走 1 个石子，然后 Alice 只能拿走 1 个石子，Bob 拿走最后一个石子并赢得胜利（7 -> 3 -> 2 -> 1 -> 0）。
如果 Alice 一开始拿走 1 个石子， Bob 会拿走 4 个石子，然后 Alice 只能拿走 1 个石子，Bob 拿走最后一个石子并赢得胜利（7 -> 6 -> 2 -> 1 -> 0）。

**示例 5：**

**输入：**n = 17
**输出：**false
**解释：**如果 Bob 采取最优策略，Alice 无法赢得胜利。

**提示：**

*   `1 <= n <= 10^5`

[https://leetcode.cn/problems/stone-game-iv/submissions/529169872/](https://leetcode.cn/problems/stone-game-iv/submissions/529169872/)

```java
class Solution {
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];
        dp[1] = true;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                if (!dp[i - j * j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
```

1690\. 石子游戏 VII
---------------

石子游戏中，爱丽丝和鲍勃轮流进行自己的回合，**爱丽丝先开始** 。

有 `n` 块石子排成一排。每个玩家的回合中，可以从行中 **移除** 最左边的石头或最右边的石头，并获得与该行中剩余石头值之 **和** 相等的得分。当没有石头可移除时，得分较高者获胜。

鲍勃发现他总是输掉游戏（可怜的鲍勃，他总是输），所以他决定尽力 **减小得分的差值** 。爱丽丝的目标是最大限度地 **扩大得分的差值** 。

给你一个整数数组 `stones` ，其中 `stones[i]` 表示 **从左边开始** 的第 `i` 个石头的值，如果爱丽丝和鲍勃都 **发挥出最佳水平** ，请返回他们 **得分的差值** 。

**示例 1：**

**输入：**stones = \[5,3,1,4,2\]
**输出：**6
**解释：**
- 爱丽丝移除 2 ，得分 5 + 3 + 1 + 4 = 13 。游戏情况：爱丽丝 = 13 ，鲍勃 = 0 ，石子 = \[5,3,1,4\] 。
- 鲍勃移除 5 ，得分 3 + 1 + 4 = 8 。游戏情况：爱丽丝 = 13 ，鲍勃 = 8 ，石子 = \[3,1,4\] 。
- 爱丽丝移除 3 ，得分 1 + 4 = 5 。游戏情况：爱丽丝 = 18 ，鲍勃 = 8 ，石子 = \[1,4\] 。
- 鲍勃移除 1 ，得分 4 。游戏情况：爱丽丝 = 18 ，鲍勃 = 12 ，石子 = \[4\] 。
- 爱丽丝移除 4 ，得分 0 。游戏情况：爱丽丝 = 18 ，鲍勃 = 12 ，石子 = \[\] 。
  得分的差值 18 - 12 = 6 。

**示例 2：**

**输入：**stones = \[7,90,5,1,100,10,10,2\]
**输出：**122

**提示：**

*   `n == stones.length`
*   `2 <= n <= 1000`
*   `1 <= stones[i] <= 1000`

[https://leetcode.cn/problems/stone-game-vii/description/](https://leetcode.cn/problems/stone-game-vii/description/)

```java
import java.util.Arrays;

class Solution {
    int[][] memo;
    public int stoneGameVII(int[] stones) {
        int n = stones.length;
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stones[i];
        }
        return dfs(0, n - 1, prefix);
    }

    private int dfs(int i, int j, int[] prefix) {
        if (i == j) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        return memo[i][j] = Math.max(prefix[j + 1] - prefix[i + 1] - dfs(i + 1, j, prefix), prefix[j] - prefix[i] - dfs(i, j - 1, prefix));
    }
}
```

1406\. 石子游戏 III
---------------

Alice 和 Bob 继续他们的石子游戏。几堆石子 **排成一行** ，每堆石子都对应一个得分，由数组 `stoneValue` 给出。

Alice 和 Bob 轮流取石子，**Alice** 总是先开始。在每个玩家的回合中，该玩家可以拿走剩下石子中的的前 **1、2 或 3 堆石子** 。比赛一直持续到所有石头都被拿走。

每个玩家的最终得分为他所拿到的每堆石子的对应得分之和。每个玩家的初始分数都是 **0** 。

比赛的目标是决出最高分，得分最高的选手将会赢得比赛，比赛也可能会出现平局。

假设 Alice 和 Bob 都采取 **最优策略** 。

如果 Alice 赢了就返回 `"Alice"` _，_Bob 赢了就返回 `"Bob"`_，_分数相同返回 `"Tie"` 。

**示例 1：**

**输入：**values = \[1,2,3,7\]
**输出：**"Bob"
**解释：**Alice 总是会输，她的最佳选择是拿走前三堆，得分变成 6 。但是 Bob 的得分为 7，Bob 获胜。

**示例 2：**

**输入：**values = \[1,2,3,-9\]
**输出：**"Alice"
**解释：**Alice 要想获胜就必须在第一个回合拿走前三堆石子，给 Bob 留下负分。
如果 Alice 只拿走第一堆，那么她的得分为 1，接下来 Bob 拿走第二、三堆，得分为 5 。之后 Alice 只能拿到分数 -9 的石子堆，输掉比赛。
如果 Alice 拿走前两堆，那么她的得分为 3，接下来 Bob 拿走第三堆，得分为 3 。之后 Alice 只能拿到分数 -9 的石子堆，同样会输掉比赛。
注意，他们都应该采取 **最优策略** ，所以在这里 Alice 将选择能够使她获胜的方案。

**示例 3：**

**输入：**values = \[1,2,3,6\]
**输出：**"Tie"
**解释：**Alice 无法赢得比赛。如果她决定选择前三堆，她可以以平局结束比赛，否则她就会输。

**提示：**

*   `1 <= stoneValue.length <= 5 * 104`
*   `-1000 <= stoneValue[i] <= 1000`

[https://leetcode.cn/problems/stone-game-iii/description/](https://leetcode.cn/problems/stone-game-iii/description/)

```java
import java.util.Arrays;

class Solution {
    int[] memo;
    public String stoneGameIII(int[] stoneValue) {
        int n = stoneValue.length;
        memo = new int[n];
        Arrays.fill(memo, -1);
        int ans = dfs(0, stoneValue);
        return ans == 0 ? "Tie" : ans > 0 ? "Alice" : "Bob";
    }

    private int dfs(int i,int[] stoneValue) {
        if (i >= stoneValue.length) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int res = stoneValue[i] - dfs(i + 1, stoneValue);
        if (i + 1 < stoneValue.length) {
            res = Math.max(res, stoneValue[i] + stoneValue[i + 1] - dfs(i + 2, stoneValue));
        }
        if (i + 2 < stoneValue.length) {
            res = Math.max(res, stoneValue[i] + stoneValue[i + 1] + stoneValue[i + 2] - dfs(i + 3, stoneValue));
        }
        return memo[i] = res;
    }
}
```

1140\. 石子游戏 II
--------------

爱丽丝和鲍勃继续他们的石子游戏。许多堆石子 **排成一行**，每堆都有正整数颗石子 `piles[i]`。游戏以谁手中的石子最多来决出胜负。

爱丽丝和鲍勃轮流进行，爱丽丝先开始。最初，`M = 1`。

在每个玩家的回合中，该玩家可以拿走剩下的 **前** `X` 堆的所有石子，其中 `1 <= X <= 2M`。然后，令 `M = max(M, X)`。

游戏一直持续到所有石子都被拿走。

假设爱丽丝和鲍勃都发挥出最佳水平，返回爱丽丝可以得到的最大数量的石头。

**示例 1：**

**输入：**piles = \[2,7,9,4,4\]
**输出：**10
**解释：**如果一开始Alice取了一堆，Bob取了两堆，然后Alice再取两堆。爱丽丝可以得到2 + 4 + 4 = 10堆。如果Alice一开始拿走了两堆，那么Bob可以拿走剩下的三堆。在这种情况下，Alice得到2 + 7 = 9堆。返回10，因为它更大。

**示例 2:**

**输入：**piles = \[1,2,3,4,5,100\]
**输出：**104

**提示：**

*   `1 <= piles.length <= 100`
*   `1 <= piles[i] <= 104`

[https://leetcode.cn/problems/stone-game-ii/description/](https://leetcode.cn/problems/stone-game-ii/description/)

```java
import java.util.Arrays;

class Solution {
    int[][] memo;
    public int stoneGameII(int[] piles) {
        int n = piles.length;
        memo = new int[n][n * 2];
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + piles[i];
            Arrays.fill(memo[i], -1);
        }
        return (prefix[n] + dfs(0, 1, prefix, piles)) / 2;
    }

    private int dfs(int i, int k, int[] prefix, int[] piles) {
        if (i >= piles.length) {
            return 0;
        }
        if (memo[i][k] != -1) {
            return memo[i][k];
        }
        int res = Integer.MIN_VALUE / 2;
        for (int j = 1; j <= 2 * k; j++) {
            if (i + j > piles.length) {
                break;
            }
            res = Math.max(res, prefix[i + j] - prefix[i] - dfs(i + j, Math.max(k, j), prefix, piles));
        }
        return memo[i][k] = res;
    }
}
```

1563\. 石子游戏 V
-------------

几块石子 **排成一行** ，每块石子都有一个关联值，关联值为整数，由数组 `stoneValue` 给出。

游戏中的每一轮：Alice 会将这行石子分成两个 **非空行**（即，左侧行和右侧行）；Bob 负责计算每一行的值，即此行中所有石子的值的总和。Bob 会丢弃值最大的行，Alice 的得分为剩下那行的值（每轮累加）。如果两行的值相等，Bob 让 Alice 决定丢弃哪一行。下一轮从剩下的那一行开始。

只 **剩下一块石子** 时，游戏结束。Alice 的分数最初为 **`0`** 。

返回 **Alice 能够获得的最大分数** _。_

**示例 1：**

**输入：**stoneValue = \[6,2,3,4,5,5\]
**输出：**18
**解释：**在第一轮中，Alice 将行划分为 \[6，2，3\]，\[4，5，5\] 。左行的值是 11 ，右行的值是 14 。Bob 丢弃了右行，Alice 的分数现在是 11 。
在第二轮中，Alice 将行分成 \[6\]，\[2，3\] 。这一次 Bob 扔掉了左行，Alice 的分数变成了 16（11 + 5）。
最后一轮 Alice 只能将行分成 \[2\]，\[3\] 。Bob 扔掉右行，Alice 的分数现在是 18（16 + 2）。游戏结束，因为这行只剩下一块石头了。

**示例 2：**

**输入：**stoneValue = \[7,7,7,7,7,7,7\]
**输出：**28

**示例 3：**

**输入：**stoneValue = \[4\]
**输出：**0

**提示：**

*   `1 <= stoneValue.length <= 500`
*   `1 <= stoneValue[i] <= 10^6`

[https://leetcode.cn/problems/stone-game-v/description/](https://leetcode.cn/problems/stone-game-v/description/)

```java
import java.util.Arrays;

class Solution {
    int[] prefixSum;
    int[][] memo;
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        prefixSum = new int[n + 1]; // 这里我们统计区间前i个数的和
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + stoneValue[i];
        }
        return dfs(0, n - 1, stoneValue);
    }

    private int dfs(int left, int right, int[] stoneValues) { // 左闭右闭
        if (left == right) { // 剩一个数，直接会被bob丢弃
            return 0;
        }
        if (memo[left][right] != -1) {
            return memo[left][right];
        }
        for (int i = left; i < right; i++) {
            int leftSum = prefixSum[i + 1] - prefixSum[left]; // 这里最好手画坐标轴演示一下，包含点left
            int rightSum = prefixSum[right + 1] - prefixSum[i + 1]; // 不包含点i
            if (leftSum < rightSum) { // bob丢弃大的
                memo[left][right] = Math.max(memo[left][right], leftSum + dfs(left, i, stoneValues));
            } else if (leftSum > rightSum) {
                memo[left][right] = Math.max(memo[left][right], rightSum + dfs(i + 1, right, stoneValues));
            }else{ // 相等
                memo[left][right] = Math.max(memo[left][right], leftSum + Math.max(dfs(left, i, stoneValues), dfs(i + 1, right, stoneValues)));
            }
        }
        return memo[left][right];
    }
}
```

464\. 我能赢吗（模板）
----------

> 这道题目官标难度不合理，从知识点角度看，状态压缩DP在LC平台属于较高级的技巧，从思维角度看，一旦受到292题的影响，就有可能长时间陷入错误的思路。但这是一道非常经典的博弈题目，吃透这道题就能体会“必胜态”“必败态”的含义，广大天坑转码刷题者还是要尽量掌握的~ 

在 "100 game" 这个游戏中，两名玩家轮流选择从 `1` 到 `10` 的任意整数，累计整数和，先使得累计整数和 **达到或超过**  100 的玩家，即为胜者。

如果我们将游戏规则改为 “玩家 **不能** 重复使用整数” 呢？

例如，两个玩家可以轮流从公共整数池中抽取从 1 到 15 的整数（不放回），直到累计整数和 >= 100。

给定两个整数 `maxChoosableInteger` （整数池中可选择的最大数）和 `desiredTotal`（累计和），若先出手的玩家能稳赢则返回 `true` ，否则返回 `false` 。假设两位玩家游戏时都表现 **最佳** 。

**示例 1：**

**输入：**maxChoosableInteger = 10, desiredTotal = 11
**输出：**false
**解释：**
无论第一个玩家选择哪个整数，他都会失败。
第一个玩家可以选择从 1 到 10 的整数。
如果第一个玩家选择 1，那么第二个玩家只能选择从 2 到 10 的整数。
第二个玩家可以通过选择整数 10（那么累积和为 11 >= desiredTotal），从而取得胜利.
同样地，第一个玩家选择任意其他整数，第二个玩家都会赢。

**示例 2:**

**输入：**maxChoosableInteger = 10, desiredTotal = 0
**输出：**true

**示例 3:**

**输入：**maxChoosableInteger = 10, desiredTotal = 1
**输出：**true

**提示:**

*   `1 <= maxChoosableInteger <= 20`
*   `0 <= desiredTotal <= 300`

[https://leetcode.cn/problems/can-i-win/solutions/1506106/wo-neng-ying-ma-by-leetcode-solution-ef5v/](https://leetcode.cn/problems/can-i-win/solutions/1506106/wo-neng-ying-ma-by-leetcode-solution-ef5v/)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    Map<Integer, Boolean> memo = new HashMap<Integer, Boolean>();
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) { // 错误的解法，这里的memo[key]是currentTotal,这肯定不对，我们要用usedNumbers作为唯一key
        // 等差数列求和公式
        if ((maxChoosableInteger + 1) * maxChoosableInteger / 2 < desiredTotal) {
            return false; // 表示两个人都无法得到desiredTotal
        }
        return dfs(0, 0, maxChoosableInteger, desiredTotal);
    }

    private boolean dfs(int usedNumbers, int currentTotal, int maxChoosableInteger, int desiredTotal) {
        if (memo.containsKey(currentTotal)) {
            return memo.get(currentTotal);
        }
        boolean res = false;
        for (int i = 0; i < maxChoosableInteger; i++) {
            if (((usedNumbers >> i) & 1) == 0) { // 表示没有被用
                if (i + 1 + currentTotal >= desiredTotal) {
                    res = true;
                    break;
                }
                if (!dfs(usedNumbers | (1 << i), currentTotal + i + 1, maxChoosableInteger, desiredTotal)) {
                    res = true;
                    break;
                }
            }
        }
        memo.put(currentTotal, res);
        return memo.get(currentTotal);
    }
}
```

```java
import java.util.HashMap;
import java.util.Map;

class Solution { // 状态压缩，如果maxChoosableInteger比较大，只能用字符串存储了？或则用map?
    Map<Integer, Boolean> memo = new HashMap<Integer, Boolean>();
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        // 等差数列求和公式
        if ((maxChoosableInteger + 1) * maxChoosableInteger / 2 < desiredTotal) {
            return false; // 表示两个人都无法得到desiredTotal
        }
        return dfs(0, 0, maxChoosableInteger, desiredTotal);
    }

    private boolean dfs(int usedNumbers, int currentTotal, int maxChoosableInteger, int desiredTotal) {
        if (memo.containsKey(usedNumbers)) {
            return memo.get(usedNumbers);
        }
        boolean res = false;
        for (int i = 1; i <= maxChoosableInteger; i++) {
            if ((usedNumbers >> i & 1) == 0) { // 表示没有被用
                if (i + currentTotal >= desiredTotal) {
                    res = true;
                    break;
                }
                if (!dfs(usedNumbers | (1 << i), currentTotal + i, maxChoosableInteger, desiredTotal)) {
                    res = true;
                    break;
                }
            }
        }
        memo.put(usedNumbers, res);
        return memo.get(usedNumbers);
    }
}
```

1872\. 石子游戏 VIII
----------------

Alice 和 Bob 玩一个游戏，两人轮流操作， **Alice 先手** 。

总共有 `n` 个石子排成一行。轮到某个玩家的回合时，如果石子的数目 **大于 1** ，他将执行以下操作：

1.  选择一个整数 `x > 1` ，并且 **移除** 最左边的 `x` 个石子。
2.  将 **移除** 的石子价值之 **和** 累加到该玩家的分数中。
3.  将一个 **新的石子** 放在最左边，且新石子的值为被移除石子值之和。

当只剩下 **一个** 石子时，游戏结束。

Alice 和 Bob 的 **分数之差** 为 `(Alice 的分数 - Bob 的分数)` 。 Alice 的目标是 **最大化** 分数差，Bob 的目标是 **最小化** 分数差。

给你一个长度为 `n` 的整数数组 `stones` ，其中 `stones[i]` 是 **从左边起** 第 `i` 个石子的价值。请你返回在双方都采用 **最优** 策略的情况下，Alice 和 Bob 的 **分数之差** 。

**示例 1：**

**输入：**stones = \[-1,2,-3,4,-5\]
**输出：**5
**解释：**
- Alice 移除最左边的 4 个石子，得分增加 (-1) + 2 + (-3) + 4 = 2 ，并且将一个价值为 2 的石子放在最左边。stones = \[2,-5\] 。
- Bob 移除最左边的 2 个石子，得分增加 2 + (-5) = -3 ，并且将一个价值为 -3 的石子放在最左边。stones = \[-3\] 。
  两者分数之差为 2 - (-3) = 5 。

**示例 2：**

**输入：**stones = \[7,-6,5,10,5,-2,-6\]
**输出：**13
**解释：**
- Alice 移除所有石子，得分增加 7 + (-6) + 5 + 10 + 5 + (-2) + (-6) = 13 ，并且将一个价值为 13 的石子放在最左边。stones = \[13\] 。
  两者分数之差为 13 - 0 = 13 。

**示例 3：**

**输入：**stones = \[-10,-12\]
**输出：**\-22
**解释：**
- Alice 只有一种操作，就是移除所有石子。得分增加 (-10) + (-12) = -22 ，并且将一个价值为 -22 的石子放在最左边。stones = \[-22\] 。
  两者分数之差为 (-22) - 0 = -22 。

**提示：**

*   `n == stones.length`
*   `2 <= n <= 105`
*   `-104 <= stones[i] <= 104`

[https://leetcode.cn/problems/stone-game-viii/description/](https://leetcode.cn/problems/stone-game-viii/description/)

```java
import java.util.Arrays;

class Solution {
    int[] prefixSum;
    int[] memo;
    public int stoneGameVIII(int[] stones) { // 抓住核心：前缀和不变，暴力O(n^2)超时
        int n = stones.length;
        prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + stones[i];
        }
        memo = new int[n];
        Arrays.fill(memo, Integer.MIN_VALUE / 2);
        return dfs(0, stones);
    }

    private int dfs(int left, int[] stones) { // 表示(left,n)的最大值
        if (left == stones.length) { // 把所有石头拿完
            return 0;
        }
        if (memo[left] != Integer.MIN_VALUE / 2) {
            return memo[left];
        }
        for (int i = left + 1; i < stones.length; i++) {
            memo[left] = Math.max(memo[left], prefixSum[i + 1] - dfs(i, stones));
        }
        return memo[left] != Integer.MIN_VALUE / 2 ? memo[left] : 0;
    }
}
```

```java
class Solution {

    int n;
    int[] stones;
    int[] sum;
    Integer[] memo;

    public int stoneGameVIII(int[] stones) { // 看不懂，以后慢慢看吧
        n = stones.length;
        this.stones = stones;
        
        memo = new Integer[n + 1];
        sum = new int[n + 1];

        for(int i = 0; i < n; i++) sum[i + 1] = sum[i] + stones[i];
        memo[n] = sum[n];
        return solve(2);
    }

    public int solve(int idx){
        if(memo[idx] != null) return memo[idx];

        int res = Math.max(solve(idx + 1), sum[idx] - solve(idx + 1));
        return memo[idx] = res;
    }
}

```

