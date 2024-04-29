121\. 买卖股票的最佳时机
---------------

给定一个数组 `prices` ，它的第 `i` 个元素 `prices[i]` 表示一支给定股票第 `i` 天的价格。

你只能选择 **某一天** 买入这只股票，并选择在 **未来的某一个不同的日子** 卖出该股票。设计一个算法来计算你所能获取的最大利润。

返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 `0` 。

**示例 1：**

**输入：**\[7,1,5,3,6,4\]
**输出：**5
**解释：**在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。

**示例 2：**

**输入：**prices = \[7,6,4,3,1\]
**输出：**0
**解释：**在这种情况下, 没有交易完成, 所以最大利润为 0。

**提示：**

*   `1 <= prices.length <= 105`
*   `0 <= prices[i] <= 104`

[https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/description/)

```java
class Solution {
    // 五、状态机 DP
    //一般定义 f[i][j] 表示前缀 a[:i] 在状态 j 下的最优值。一般 j 都很小。代表题目是「买卖股票」系列。
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int ans = 0;
        for (int price : prices) {
            minPrice = Math.min(minPrice, price); // 维护状态，买入股票最小值
            ans = Math.max(ans, price - minPrice);
        }
        return ans;
    }
}   
```

122\. 买卖股票的最佳时机 II
------------------

给你一个整数数组 `prices` ，其中 `prices[i]` 表示某支股票第 `i` 天的价格。

在每一天，你可以决定是否购买和/或出售股票。你在任何时候 **最多** 只能持有 **一股** 股票。你也可以先购买，然后在 **同一天** 出售。

返回 _你能获得的 **最大** 利润_ 。

**示例 1：**

**输入：**prices = \[7,1,5,3,6,4\]
**输出：**7
**解释：**在第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
     随后，在第 4 天（股票价格 = 3）的时候买入，在第 5 天（股票价格 = 6）的时候卖出, 这笔交易所能获得利润 = 6 - 3 = 3 。
     总利润为 4 + 3 = 7 。

**示例 2：**

**输入：**prices = \[1,2,3,4,5\]
**输出：**4
**解释：**在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5 - 1 = 4 。
     总利润为 4 。

**示例 3：**

**输入：**prices = \[7,6,4,3,1\]
**输出：**0
**解释：**在这种情况下, 交易无法获得正利润，所以不参与交易可以获得最大利润，最大利润为 0 。

**提示：**

*   `1 <= prices.length <= 3 * 104`
*   `0 <= prices[i] <= 104`

[https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/description/](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-ii/description/)

```java
import java.util.Arrays;

class Solution {
    // 五、状态机 DP
    //一般定义 f[i][j] 表示前缀 a[:i] 在状态 j 下的最优值。一般 j 都很小。代表题目是「买卖股票」系列。
    private int[][] memo;
    private int[] prices;
    private int n;
    public int maxProfit(int[] prices) {
        this.prices = prices;
        n = prices.length;
        memo = new int[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1); // 表示没有计算
        }
        return dfs(0, 0); // 表示从第0天开始，没有持有股票
    }

    private int dfs(int i, int hold) { // 第i天，hold==1?持有：不持有
        if (i == n) {
            return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        }
        if (memo[i][hold] != -1) {
            return memo[i][hold];
        }
        if (hold == 1) { // 需要卖股票
            return memo[i][hold] = Math.max(dfs(i + 1, 0) + prices[i], dfs(i + 1, 1));
        }
        return memo[i][hold] = Math.max(dfs(i + 1, 0), dfs(i + 1, 1) - prices[i]);
    }
}
```

```java
class Solution {
    // 五、状态机 DP
    //一般定义 f[i][j] 表示前缀 a[:i] 在状态 j 下的最优值。一般 j 都很小。代表题目是「买卖股票」系列。
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];
        dp[0][1] = Integer.MIN_VALUE / 2; // 关键
        for (int i = 0; i < n; i++) {
            dp[i + 1][0] = Math.max(dp[i][0], dp[i][1] + prices[i]);
            dp[i + 1][1] = Math.max(dp[i][0] - prices[i], dp[i][1]);
        }
        return dp[n][0]; // 最后不能持有
    }
}
```

```java
class Solution { // 想要一步写出这个还是不太可能，dfs的还是很不错的
    public int maxProfit(int[] prices) {
        int f0 = 0, f1 = Integer.MIN_VALUE;
        for (int p : prices) {
            int newF0 = Math.max(f0, f1 + p);
            f1 = Math.max(f1, f0 - p);
            f0 = newF0;
        }
        return f0;
    }
}
```

123\. 买卖股票的最佳时机 III
-------------------

给定一个数组，它的第 `i` 个元素是一支给定的股票在第 `i` 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 **两笔** 交易。

**注意：**你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

**示例 1:**

**输入：**prices = \[3,3,5,0,0,3,1,4\]
**输出：**6
**解释：**在第 4 天（股票价格 = 0）的时候买入，在第 6 天（股票价格 = 3）的时候卖出，这笔交易所能获得利润 = 3-0 = 3 。
     随后，在第 7 天（股票价格 = 1）的时候买入，在第 8 天 （股票价格 = 4）的时候卖出，这笔交易所能获得利润 = 4-1 = 3 。

**示例 2：**

**输入：**prices = \[1,2,3,4,5\]
**输出：**4
**解释：**在第 1 天（股票价格 = 1）的时候买入，在第 5 天 （股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。   
     注意你不能在第 1 天和第 2 天接连购买股票，之后再将它们卖出。   
     因为这样属于同时参与了多笔交易，你必须在再次购买前出售掉之前的股票。

**示例 3：**

**输入：**prices = \[7,6,4,3,1\] 
**输出：**0 
**解释：**在这个情况下, 没有交易完成, 所以最大利润为 0。

**示例 4：**

**输入：**prices = \[1\]
**输出：**0

**提示：**

*   `1 <= prices.length <= 105`
*   `0 <= prices[i] <= 105`

[https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/description/](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iii/description/)

```java
import java.util.Arrays;

class Solution {
    // 五、状态机 DP
    //一般定义 f[i][j] 表示前缀 a[:i] 在状态 j 下的最优值。一般 j 都很小。代表题目是「买卖股票」系列。
    private int[][][] memo;
    private int[] prices;
    private int n;
    public int maxProfit(int[] prices) {
        this.prices = prices;
        n = prices.length;
        memo = new int[n][2][3];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(0, 0, 0); // 表示从第0天开始，没有持有股票
    }

    private int dfs(int i, int hold,int sellCnt) { // 第i天，hold==1?持有：不持有 ，sellCnt卖出次数
        if (i == n || sellCnt == 2) {
            return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        }
        if (memo[i][hold][sellCnt] != -1) {
            return memo[i][hold][sellCnt];
        }
        if (hold == 1) { // 需要卖股票
            return memo[i][hold][sellCnt] = Math.max(dfs(i + 1, 0, sellCnt + 1) + prices[i], dfs(i + 1, 1, sellCnt));
        }
        return memo[i][hold][sellCnt] = Math.max(dfs(i + 1, 0, sellCnt), dfs(i + 1, 1, sellCnt) - prices[i]);
    }
}
```

```java
class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][3];
        for (int i = 0; i < 3; i++) {
            dp[0][1][i] = Integer.MIN_VALUE / 2;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < 3; j++) { // j表示还可以买入的次数 1/2
                dp[i + 1][0][j] = Math.max(dp[i][0][j], dp[i][1][j] + prices[i]);
                dp[i + 1][1][j] = Math.max(dp[i][1][j], dp[i][0][j - 1] - prices[i]);
            }
        }
        int ans = 0;
        for (int i = 0; i < 3; i++) {
            ans = Math.max(dp[n][0][i], ans);
        }
        return ans;
    }
}
```

```java
class Solution {
    // 空间复杂度优化版本
    int maxProfit(int[] prices) { // 这种版本不做要求，务必掌握方法一二
        // base case
        int dp_i10 = 0, dp_i11 = Integer.MIN_VALUE;
        int dp_i20 = 0, dp_i21 = Integer.MIN_VALUE;
        for (int price : prices) {
            dp_i20 = Math.max(dp_i20, dp_i21 + price);
            dp_i21 = Math.max(dp_i21, dp_i10 - price);
            dp_i10 = Math.max(dp_i10, dp_i11 + price);
            dp_i11 = Math.max(dp_i11, -price);
        }
        return dp_i20;
    }
}
```

188\. 买卖股票的最佳时机 IV
------------------

给你一个整数数组 `prices` 和一个整数 `k` ，其中 `prices[i]` 是某支给定的股票在第 `i` 天的价格。

设计一个算法来计算你所能获取的最大利润。你最多可以完成 `k` 笔交易。也就是说，你最多可以买 `k` 次，卖 `k` 次。

**注意：**你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

**示例 1：**

**输入：**k = 2, prices = \[2,4,1\]
**输出：**2
**解释：**在第 1 天 (股票价格 = 2) 的时候买入，在第 2 天 (股票价格 = 4) 的时候卖出，这笔交易所能获得利润 = 4-2 = 2 。

**示例 2：**

**输入：**k = 2, prices = \[3,2,6,5,0,3\]
**输出：**7
**解释：**在第 2 天 (股票价格 = 2) 的时候买入，在第 3 天 (股票价格 = 6) 的时候卖出, 这笔交易所能获得利润 = 6-2 = 4 。
     随后，在第 5 天 (股票价格 = 0) 的时候买入，在第 6 天 (股票价格 = 3) 的时候卖出, 这笔交易所能获得利润 = 3-0 = 3 。

**提示：**

*   `1 <= k <= 100`
*   `1 <= prices.length <= 1000`
*   `0 <= prices[i] <= 1000`

[https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/description/](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-iv/description/)

```java
import java.util.Arrays;

class Solution {
    // 五、状态机 DP
    //一般定义 f[i][j] 表示前缀 a[:i] 在状态 j 下的最优值。一般 j 都很小。代表题目是「买卖股票」系列。
    private int[][][] memo;
    private int[] prices;
    private int n, k;
    public int maxProfit(int k, int[] prices) {
        this.prices = prices;
        this.k = k;
        n = prices.length;
        memo = new int[n][2][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(0, 0, 0); // 表示从第0天开始，没有持有股票
    }

    private int dfs(int i, int hold,int sellCnt) { // 第i天，hold==1?持有：不持有 ，sellCnt卖出次数
        if (i == n || sellCnt == k) {
            return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        }
        if (memo[i][hold][sellCnt] != -1) {
            return memo[i][hold][sellCnt];
        }
        if (hold == 1) { // 需要卖股票
            return memo[i][hold][sellCnt] = Math.max(dfs(i + 1, 0, sellCnt + 1) + prices[i], dfs(i + 1, 1, sellCnt));
        }
        return memo[i][hold][sellCnt] = Math.max(dfs(i + 1, 0, sellCnt), dfs(i + 1, 1, sellCnt) - prices[i]);
    }
}
```

```java
class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;
        int[][][] dp = new int[n + 1][2][k + 1];
        for (int i = 0; i < k + 1; i++) {
            dp[0][1][i] = Integer.MIN_VALUE / 2;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < k + 1; j++) { // j表示还可以买入的次数
                dp[i + 1][0][j] = Math.max(dp[i][0][j], dp[i][1][j] + prices[i]);
                dp[i + 1][1][j] = Math.max(dp[i][1][j], dp[i][0][j - 1] - prices[i]);
            }
        }
        int ans = 0;
        for (int i = 0; i < k + 1; i++) {
            ans = Math.max(dp[n][0][i], ans);
        }
        return ans;
    }
}
```

309\. 买卖股票的最佳时机含冷冻期
-------------------

给定一个整数数组`prices`，其中第  `prices[i]` 表示第 `_i_` 天的股票价格 。​

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）:

*   卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)。

**注意：**你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

**示例 1:**

**输入:** prices = \[1,2,3,0,2\]
**输出:** 3 
**解释:** 对应的交易状态为: \[买入, 卖出, 冷冻期, 买入, 卖出\]

**示例 2:**

**输入:** prices = \[1\]
**输出:** 0

**提示：**

*   `1 <= prices.length <= 5000`
*   `0 <= prices[i] <= 1000`

[https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-cooldown/description/)

```java
import java.util.Arrays;

class Solution {
    // 五、状态机 DP
    //一般定义 f[i][j] 表示前缀 a[:i] 在状态 j 下的最优值。一般 j 都很小。代表题目是「买卖股票」系列。
    private int[][][] memo;
    private int[] prices;
    private int n;
    public int maxProfit(int[] prices) {
        this.prices = prices;
        n = prices.length;
        memo = new int[n][2][2];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(memo[i][j], -1); // 表示没有计算
            }
        }
        return dfs(0, 0, 0); // 表示从第0天开始，没有持有股票
    }

    private int dfs(int i, int hold,int isFreeze) { // 第i天，hold==1?持有：不持有
        if (i == n) {
            return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        }
        if (memo[i][hold][isFreeze] != -1) {
            return memo[i][hold][isFreeze];
        }
        if (hold == 1) { // 需要卖股票
            return memo[i][hold][isFreeze] = Math.max(dfs(i + 1, 0, 1) + prices[i], dfs(i + 1, 1, 0));
        }
        // hold==0
        int res = dfs(i + 1, 0, 0); // 不买
        if (isFreeze == 0) {
            // 买
            res = Math.max(res, dfs(i + 1, 1, 0) - prices[i]);
        }
        return memo[i][hold][isFreeze] = res;
    }
}
```

714\. 买卖股票的最佳时机含手续费
-------------------

给定一个整数数组 `prices`，其中 `prices[i]`表示第 `i` 天的股票价格 ；整数 `fee` 代表了交易股票的手续费用。

你可以无限次地完成交易，但是你每笔交易都需要付手续费。如果你已经购买了一个股票，在卖出它之前你就不能再继续购买股票了。

返回获得利润的最大值。

**注意：**这里的一笔交易指买入持有并卖出股票的整个过程，每笔交易你只需要为支付一次手续费。

**示例 1：**

**输入：**prices = \[1, 3, 2, 8, 4, 9\], fee = 2
**输出：**8
**解释：**能够达到的最大利润:  
在此处买入 prices\[0\] = 1
在此处卖出 prices\[3\] = 8
在此处买入 prices\[4\] = 4
在此处卖出 prices\[5\] = 9
总利润: ((8 - 1) - 2) + ((9 - 4) - 2) = 8

**示例 2：**

**输入：**prices = \[1,3,7,5,10,3\], fee = 3
**输出：**6

**提示：**

*   `1 <= prices.length <= 5 * 104`
*   `1 <= prices[i] < 5 * 104`
*   `0 <= fee < 5 * 104`

[https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/description/](https://leetcode.cn/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/description/)

```java
import java.util.Arrays;

class Solution {
    // 五、状态机 DP
    //一般定义 f[i][j] 表示前缀 a[:i] 在状态 j 下的最优值。一般 j 都很小。代表题目是「买卖股票」系列。
    private int[][] memo;
    private int[] prices;
    private int n,fee;
    public int maxProfit(int[] prices,int fee) {
        this.prices = prices;
        this.fee = fee;
        n = prices.length;
        memo = new int[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1); // 表示没有计算
        }
        return dfs(0, 0); // 表示从第0天开始，没有持有股票
    }

    private int dfs(int i, int hold) { // 第i天，hold==1?持有：不持有
        if (i == n) {
            return hold == 1 ? Integer.MIN_VALUE / 2 : 0;
        }
        if (memo[i][hold] != -1) {
            return memo[i][hold];
        }
        if (hold == 1) { // 需要卖股票
            return memo[i][hold] = Math.max(dfs(i + 1, 0) + prices[i] - fee, dfs(i + 1, 1));
        }
        return memo[i][hold] = Math.max(dfs(i + 1, 0), dfs(i + 1, 1) - prices[i]);
    }
}
```

```java
class Solution {
    // 五、状态机 DP
    //一般定义 f[i][j] 表示前缀 a[:i] 在状态 j 下的最优值。一般 j 都很小。代表题目是「买卖股票」系列。
    public int maxProfit(int[] prices, int fee) {
        int n = prices.length;
        int[][] dp = new int[n + 1][2];
        dp[0][1] = Integer.MIN_VALUE / 2; // 关键
        for (int i = 0; i < n; i++) {
            dp[i + 1][0] = Math.max(dp[i][0], dp[i][1] + prices[i] - fee);
            dp[i + 1][1] = Math.max(dp[i][0] - prices[i], dp[i][1]);
        }
        return dp[n][0]; // 最后不能持有
    }
}
```

1493\. 删掉一个元素以后全为 1 的最长子数组
--------------------------

给你一个二进制数组 `nums` ，你需要从中删掉一个元素。

请你在删掉元素的结果数组中，返回最长的且只包含 1 的非空子数组的长度。

如果不存在这样的子数组，请返回 0 。

**提示 1：**

**输入：**nums = \[1,1,0,1\]
**输出：**3
**解释：**删掉位置 2 的数后，\[1,1,1\] 包含 3 个 1 。

**示例 2：**

**输入：**nums = \[0,1,1,1,0,1,1,0,1\]
**输出：**5
**解释：**删掉位置 4 的数字后，\[0,1,1,1,1,1,0,1\] 的最长全 1 子数组为 \[1,1,1,1,1\] 。

**示例 3：**

**输入：**nums = \[1,1,1\]
**输出：**2
**解释：**你必须要删除一个元素。

**提示：**

*   `1 <= nums.length <= 105`
*   `nums[i]` 要么是 `0` 要么是 `1` 。

[https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/description/](https://leetcode.cn/problems/longest-subarray-of-1s-after-deleting-one-element/description/)

```java
class Solution {
    public int longestSubarray(int[] nums) {
        int ans = 0;
        int a = 0, b = 0;
        for (int num : nums) {
            if (num == 1) {
                a++;
                b++;
            }else{
                b = a;
                a = 0;
            }
            ans = Math.max(b, ans);
        }
        // 特判全为1
        if (a == nums.length) {
            return a - 1;
        }
        return ans;
    }
}
```

1395\. 统计作战单位数(树状数组优化)
--------------

 `n` 名士兵站成一排。每个士兵都有一个 **独一无二** 的评分 `rating` 。

每 **3** 个士兵可以组成一个作战单位，分组规则如下：

*   从队伍中选出下标分别为 `i`、`j`、`k` 的 3 名士兵，他们的评分分别为 `rating[i]`、`rating[j]`、`rating[k]`
*   作战单位需满足： `rating[i] < rating[j] < rating[k]` 或者 `rating[i] > rating[j] > rating[k]` ，其中  `0 <= i < j < k < n`

请你返回按上述条件可以组建的作战单位数量。每个士兵都可以是多个作战单位的一部分。

**示例 1：**

**输入：**rating = \[2,5,3,4,1\]
**输出：**3
**解释：**我们可以组建三个作战单位 (2,3,4)、(5,4,1)、(5,3,1) 。

**示例 2：**

**输入：**rating = \[2,1,3\]
**输出：**0
**解释：**根据题目条件，我们无法组建作战单位。

**示例 3：**

**输入：**rating = \[1,2,3,4\]
**输出：**4

**提示：**

*   `n == rating.length`
*   `3 <= n <= 1000`
*   `1 <= rating[i] <= 10^5`
*   `rating` 中的元素都是唯一的

[https://leetcode.cn/problems/count-number-of-teams/description/](https://leetcode.cn/problems/count-number-of-teams/description/)

```java
class Solution {
    public int numTeams(int[] rating) { // 暴力解法O(n^3)
        int n = rating.length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (rating[i] > rating[j] && rating[j] > rating[k] || rating[i] < rating[j] && rating[j] < rating[k]) {
                        ans++;
                    }
                }
            }
        }
        return ans;
    }
}
```

```java
class Solution {
    public int numTeams(int[] rating) { // 这种三个节点的，一般都可以枚举中间节点来解决问题
        int n = rating.length;
        int ans = 0;
        for (int i = 1; i < n - 1; i++) { // 枚举中间节点
            int iLess = 0, iMore = 0;
            int kLess = 0, kMore = 0;
            for (int j = 0; j < i; j++) {
                if (rating[j] < rating[i]) {
                    iLess++;
                }else{
                    iMore++;
                }
            }
            for (int k = i + 1; k < n; k++) {
                if (rating[k] < rating[i]) {
                    kLess++;
                }else{
                    kMore++;
                }
            }
            ans += iLess * kMore + kLess * iMore;
        }
        return ans;
    }
}
```

```java
import java.util.Arrays;
import java.util.Comparator;

class Solution {
    public int numTeams(int[] rating) { // 树状数组优化
        int n = rating.length;
        int[][] nums = new int[n][2];
        for (int i = 0; i < n; i++) {
            nums[i][0] = rating[i];
            nums[i][1] = i + 1; // 记得加一！！！
        }
        // Arrays.sort(nums, (i, j) -> Integer.compare(i[0], j[0])); // 相减操作可能会超出 int 范围
        Arrays.sort(nums, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                //相减操作可能会超出 int 范围
                if (o1[0] != o2[0]) {
                    return Integer.compare(o1[0], o2[0]);
                } else {
                    return Integer.compare(o1[1], o2[1]);
                }
            }
        });
        BIT bit = new BIT(n);
        int[] iLess = new int[n];
        int[] iMore = new int[n];
        int[] kLess = new int[n];
        int[] kMore = new int[n];
        for (int i = 0; i < n; i++) { // 更新前缀,比当前数小的元素个数
            iLess[i] = bit.query(nums[i][1]);
            iMore[i] = bit.query(n) - iLess[i]; // 这里厉害
            bit.update(nums[i][1], 1);
        }
        bit = new BIT(n);
        for (int i = n - 1; i >= 0; i--) {
            kLess[i] = bit.query(nums[i][1]);
            kMore[i] = bit.query(n) - kLess[i];
            bit.update(nums[i][1], 1);
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += iLess[i] * kMore[i] + iMore[i] * kLess[i];
        }
        return ans;
    }

    class BIT {
        // 最大数组长度
        private int maxN;
        // 树状数组存储结构
        private int[] treeArray;

        // 构造函数，初始化树状数组
        public BIT(int maxN) {
            this.maxN = maxN;
            treeArray = new int[maxN + 1];
        }

        // 获取x的二进制表示中最低位的1所对应的值
        public int lowBit(int x) {
            return x & (-x);
        }

        // 更新操作，将数组中位置x的元素加dt
        public void update(int x,int dt) {
            while (x <= maxN) {
                treeArray[x]+=dt;
                x += lowBit(x);
            }
        }

        // 查询操作，获取数组前缀和，即位置1到位置x的所有元素的和
        public int query(int x) {
            int res = 0;
            while (x > 0) {
                res += treeArray[x];
                x -= lowBit(x);
            }
            return res;
        }
    }
}
```

2745\. 构造最长的新字符串
----------------

给你三个整数 `x` ，`y` 和 `z` 。

这三个整数表示你有 `x` 个 `"AA"` 字符串，`y` 个 `"BB"` 字符串，和 `z` 个 `"AB"` 字符串。你需要选择这些字符串中的部分字符串（可以全部选择也可以一个都不选择），将它们按顺序连接得到一个新的字符串。新字符串不能包含子字符串 `"AAA"` 或者 `"BBB"` 。

请你返回 _新字符串的最大可能长度。_

**子字符串** 是一个字符串中一段连续 **非空** 的字符序列。

**示例 1：**

**输入：**x = 2, y = 5, z = 1
**输出：**12
**解释：** 我们可以按顺序连接 "BB" ，"AA" ，"BB" ，"AA" ，"BB" 和 "AB" ，得到新字符串 "BBAABBAABBAB" 。
字符串长度为 12 ，无法得到一个更长的符合题目要求的字符串。

**示例 2：**

**输入：**x = 3, y = 2, z = 2
**输出：**14
**解释：**我们可以按顺序连接 "AB" ，"AB" ，"AA" ，"BB" ，"AA" ，"BB" 和 "AA" ，得到新字符串 "ABABAABBAABBAA" 。
字符串长度为 14 ，无法得到一个更长的符合题目要求的字符串。

**提示：**

*   `1 <= x, y, z <= 50`

[https://leetcode.cn/problems/construct-the-longest-new-string/description/](https://leetcode.cn/problems/construct-the-longest-new-string/description/)

```java
class Solution {
    public int longestString(int x, int y, int z) { // 数学方法
        return (Math.min(x, y)*2 + (x == y ? 0 : 1) + z) * 2;
    }
}
```

```java
class Solution {
    public int longestString(int x, int y, int z) {
        /* 创建 (x+1)×(y+1)×(z+1)×3(x + 1) 的四维数组 dp，其中 dp[i][j][k][t] 表示最多使用 i 个 “AA"、j 个 “BB" 和 kkk 个 “AB"，且最后一个子串是类型 t 的子串时的新字符串的最大长度，t=0 对应 “AA"，t=1 对应 “BB"，t=2 对应 “AB"。*/
        int[][][][] dp = new int[x + 1][y + 1][z + 1][3];
        for (int i = 0; i <= x; i++) {
            for (int j = 0; j <= y; j++) {
                for (int k = 0; k <= z; k++) {
                    if (i > 0) { // 选择第一个
                        dp[i][j][k][0] = Math.max(dp[i - 1][j][k][1], dp[i - 1][j][k][2]) + 2;
                    }
                    if (j > 0) {
                        dp[i][j][k][1] = dp[i][j - 1][k][0] + 2;
                    }
                    if (k > 0) {
                        dp[i][j][k][2] = Math.max(dp[i][j][k - 1][1], dp[i][j][k - 1][2]) + 2;
                    }
                }
            }
        }
        return Math.max(Math.max(dp[x][y][z][0], dp[x][y][z][1]), dp[x][y][z][2]);
    }
}
```

2222\. 选择建筑的方案数
---------------

给你一个下标从 **0** 开始的二进制字符串 `s` ，它表示一条街沿途的建筑类型，其中：

*   `s[i] = '0'` 表示第 `i` 栋建筑是一栋办公楼，
*   `s[i] = '1'` 表示第 `i` 栋建筑是一间餐厅。

作为市政厅的官员，你需要随机 **选择** 3 栋建筑。然而，为了确保多样性，选出来的 3 栋建筑 **相邻** 的两栋不能是同一类型。

*   比方说，给你 `s = "0_**0**_1_**1**_0_**1**_"` ，我们不能选择第 `1` ，`3` 和 `5` 栋建筑，因为得到的子序列是 `"0_**11**_"` ，有相邻两栋建筑是同一类型，所以 **不合** 题意。

请你返回可以选择 3 栋建筑的 **有效方案数** 。

**示例 1：**

**输入：**s = "001101"
**输出：**6
**解释：**
以下下标集合是合法的：
- \[0,2,4\] ，从 "_**0**_0_**1**_1_**0**_1" 得到 "010"
- \[0,3,4\] ，从 "_**0**_01_**10**_1" 得到 "010"
- \[1,2,4\] ，从 "0_**01**_1_**0**_1" 得到 "010"
- \[1,3,4\] ，从 "0_**0**_1_**10**_1" 得到 "010"
- \[2,4,5\] ，从 "00_**1**_1_**01**_" 得到 "101"
- \[3,4,5\] ，从 "001_**101**_" 得到 "101"
  没有别的合法选择，所以总共有 6 种方法。

**示例 2：**

**输入：**s = "11100"
**输出：**0
**解释：**没有任何符合题意的选择。

**提示：**

*   `3 <= s.length <= 105`
*   `s[i]` 要么是 `'0'` ，要么是 `'1'` 。

[https://leetcode.cn/problems/number-of-ways-to-select-buildings/description/](https://leetcode.cn/problems/number-of-ways-to-select-buildings/description/)

```java
class Solution {
    public long numberOfWays(String s) { // 数学方法
        // 根据题意，只有两种情况：
        //010：对每个 1，统计左边 0 的个数和右边 0 的个数；
        //101：对每个 0，统计左边 1 的个数和右边 1 的个数。
        long ans = 0;
        int n = s.length(), cnt0 = 0, cnt1 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                cnt0++;
            }else{
                cnt1++;
            }
        }
        int curCnt0 = 0, curCnt1 = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                curCnt0++;
                ans += (long) curCnt1 * (cnt1 - curCnt1);
            }else{
                curCnt1++;
                ans += (long) curCnt0 * (cnt0 - curCnt0);
            }
        }
        return ans;
    }
}
```

376\. 摆动序列
----------

如果连续数字之间的差严格地在正数和负数之间交替，则数字序列称为 **摆动序列 。**第一个差（如果存在的话）可能是正数或负数。仅有一个元素或者含两个不等元素的序列也视作摆动序列。

*   例如， `[1, 7, 4, 9, 2, 5]` 是一个 **摆动序列** ，因为差值 `(6, -3, 5, -7, 3)` 是正负交替出现的。

*   相反，`[1, 4, 7, 2, 5]` 和 `[1, 7, 4, 5, 5]` 不是摆动序列，第一个序列是因为它的前两个差值都是正数，第二个序列是因为它的最后一个差值为零。

**子序列** 可以通过从原始序列中删除一些（也可以不删除）元素来获得，剩下的元素保持其原始顺序。

给你一个整数数组 `nums` ，返回 `nums` 中作为 **摆动序列** 的 **最长子序列的长度** 。

**示例 1：**

**输入：**nums = \[1,7,4,9,2,5\]
**输出：**6
**解释：**整个序列均为摆动序列，各元素之间的差值为 (6, -3, 5, -7, 3) 。

**示例 2：**

**输入：**nums = \[1,17,5,10,13,15,10,5,16,8\]
**输出：**7
**解释：**这个序列包含几个长度为 7 摆动序列。
其中一个是 \[1, 17, 10, 13, 10, 16, 8\] ，各元素之间的差值为 (16, -7, 3, -3, 6, -8) 。

**示例 3：**

**输入：**nums = \[1,2,3,4,5,6,7,8,9\]
**输出：**2

**提示：**

*   `1 <= nums.length <= 1000`
*   `0 <= nums[i] <= 1000`

**进阶：**你能否用 `O(n)` 时间复杂度完成此题?

[https://leetcode.cn/problems/wiggle-subsequence/description/](https://leetcode.cn/problems/wiggle-subsequence/description/)

```java
class Solution {
    public int wiggleMaxLength(int[] nums) {
        int n = nums.length;
        if (n < 2) {
            return n;
        }
        int[] up = new int[n];
        int[] down = new int[n];
        up[0] = down[0] = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i] > nums[i - 1]) {
                up[i] = Math.max(up[i - 1], down[i - 1] + 1);
                down[i] = down[i - 1];
            } else if (nums[i] < nums[i - 1]) {
                down[i] = Math.max(up[i - 1] + 1, down[i]);
                up[i] = up[i - 1];
            }else{  // nums[i] == nums[i - 1]
                up[i] = up[i - 1];
                down[i] = down[i - 1];
            }
        }
        return Math.max(up[n - 1], down[n - 1]);
    }
}
```

1567\. 乘积为正数的最长子数组长度
--------------------

给你一个整数数组 `nums` ，请你求出乘积为正数的最长子数组的长度。

一个数组的子数组是由原数组中零个或者更多个连续数字组成的数组。

请你返回乘积为正数的最长子数组长度。

**示例  1：**

**输入：**nums = \[1,-2,-3,4\]
**输出：**4
**解释：**数组本身乘积就是正数，值为 24 。

**示例 2：**

**输入：**nums = \[0,1,-2,-3,-4\]
**输出：**3
**解释：**最长乘积为正数的子数组为 \[1,-2,-3\] ，乘积为 6 。
注意，我们不能把 0 也包括到子数组中，因为这样乘积为 0 ，不是正数。

**示例 3：**

**输入：**nums = \[-1,-2,-3,0,1\]
**输出：**2
**解释：**乘积为正数的最长子数组是 \[-1,-2\] 或者 \[-2,-3\] 。

**提示：**

*   `1 <= nums.length <= 10^5`
*   `-10^9 <= nums[i] <= 10^9`

[https://leetcode.cn/problems/maximum-length-of-subarray-with-positive-product/description/](https://leetcode.cn/problems/maximum-length-of-subarray-with-positive-product/description/)

```java
import java.util.Arrays;

class Solution {
    public int getMaxLen(int[] nums) {
        int n = nums.length;
        int[] positive = new int[n];
        int[] negative = new int[n];
        if (nums[0] > 0) {
            positive[0] = 1;
        } else if (nums[0] < 0) {
            negative[0] = 1;
        }
        int ans = positive[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] > 0) {
                positive[i] = positive[i - 1] + 1;
                if (negative[i - 1] > 0) {
                    negative[i] = negative[i - 1] + 1;
                }
            } else if (nums[i] < 0) {
                if (negative[i - 1] > 0) {
                    positive[i] = negative[i - 1] + 1;
                }
                negative[i] = positive[i - 1] + 1;
            }else{
                positive[i] = 0;
                negative[i] = 0;
            }
            ans = Math.max(positive[i], ans);
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
        return dpMax[m - 1][n - 1] >= 0 ? (int) (dpMax[m - 1][n - 1] % Mod) : -1; // 记得加括号
    }
}
```

2826\. 将三个组排序
-------------

给你一个整数数组 `nums` 。`nums` 的每个元素是 1，2 或 3。在每次操作中，你可以删除 `nums` 中的一个元素。返回使 nums 成为 **非递减** 顺序所需操作数的 **最小值**。

**示例 1：**

**输入：**nums = \[2,1,3,2,1\]
**输出：**3
**解释：**
其中一个最优方案是删除 nums\[0\]，nums\[2\] 和 nums\[3\]。

**示例 2：**

**输入：**nums = \[1,3,2,1,3,3\]
**输出：**2
**解释：**
其中一个最优方案是删除 nums\[1\] 和 nums\[2\]。

**示例 3：**

**输入：**nums = \[2,2,2,2,3,3\]
**输出：**0
**解释：**
nums 已是非递减顺序的。

**提示：**

*   `1 <= nums.length <= 100`
*   `1 <= nums[i] <= 3`

**进阶：**你可以使用 `O(n)` 时间复杂度以内的算法解决吗？

[https://leetcode.cn/problems/sorting-three-groups/description/](https://leetcode.cn/problems/sorting-three-groups/description/)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    public int minimumOperations(List<Integer> nums) { // 可以取等，只需要非递减
        ArrayList<Integer> g = new ArrayList<>();
        for (int x : nums) {
            int j = upperBound(g, x);
            if (j == g.size()) {
                g.add(x);
            }else{
                g.set(j, x);
            }
        }
        return nums.size() - g.size();
    }

    private int upperBound(List<Integer> g, int target) {
        int left = 0, right = g.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (g.get(mid) <= target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left;
    }

}
```

