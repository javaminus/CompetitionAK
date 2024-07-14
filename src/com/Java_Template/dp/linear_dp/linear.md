# 七、其他线性 DP

100358\. 找出有效子序列的最大长度 II
------------------------

给你一个整数数组 `nums` 和一个 **正** 整数 `k` 。

`nums` 的一个 

子序列

`sub` 的长度为 `x` ，如果其满足以下条件，则称其为 **有效子序列** ：

*   `(sub[0] + sub[1]) % k == (sub[1] + sub[2]) % k == ... == (sub[x - 2] + sub[x - 1]) % k`

返回 `nums` 的 **最长****有效子序列** 的长度。

**示例 1：**

**输入：**nums = \[1,2,3,4,5\], k = 2

**输出：**5

**解释：**

最长有效子序列是 `[1, 2, 3, 4, 5]` 。

**示例 2：**

**输入：**nums = \[1,4,2,3,1,4\], k = 3

**输出：**4

**解释：**

最长有效子序列是 `[1, 4, 1, 4]` 。

**提示：**

*   `2 <= nums.length <= 103`
*   `1 <= nums[i] <= 107`
*   `1 <= k <= 103`

[https://leetcode.cn/problems/find-the-maximum-length-of-valid-subsequence-ii/description/](https://leetcode.cn/problems/find-the-maximum-length-of-valid-subsequence-ii/description/)

```java
import java.util.Arrays;
import java.util.HashMap;

class Solution { // 记忆化直接超时，用数组Memo直接爆内存，需要滚动数组压缩维度
    int n,k;
    HashMap<Long,Integer> memo;
    public int maximumLength(int[] nums, int k) {
        n = nums.length;
        this.k = k;
        memo = new HashMap<>();
        for (int i = 0; i < n; i++) {
            nums[i] %= k;
        }
        // System.out.println(Arrays.toString(nums));
        return dfs(0, k, k, nums);
    }

    private int dfs(int i,int pre,int ppre, int[] nums) {
        if (i == n) {
            return 0;
        }
        long key = getKey(i, pre, ppre);
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int res = dfs(i + 1, pre, ppre, nums);
        if (nums[i] == ppre || ppre == k) {
            res = Math.max(res, dfs(i + 1, nums[i], pre, nums) + 1);
        }
        memo.put(key, res);
        return res;
    }

    private Long getKey(int x,int pre, int ppre) {
        return (((long) x << 32) + ((long) pre << 15) + ppre);
    }
}
```

```java
class Solution {
    public int maximumLength(int[] nums, int k) {
        int ans = 0;
        int[][] dp = new int[k][k]; // dp[i][j]表示前一个数是i，前前个数是j的最大长度
        for (int x : nums) {
            x %= k;
            for (int y = 0; y < k; y++) { // 枚举前前个数
                dp[x][y] = dp[y][x] + 1;
                ans = Math.max(ans, dp[x][y]);
            }
        }
        return ans;
    }
}
```

# §7.1 一维(从后往前的dp)

> ## **发生在前缀/后缀之间的转移，例如从 f[i-1]转移到 f[i]，或者从 f[j] 转移到 f[i]。**

2140\. 解决智力问题
-------------

给你一个下标从 **0** 开始的二维整数数组 `questions` ，其中 `questions[i] = [pointsi, brainpoweri]` 。

这个数组表示一场考试里的一系列题目，你需要 **按顺序** （也就是从问题 `0` 开始依次解决），针对每个问题选择 **解决** 或者 **跳过** 操作。解决问题 `i` 将让你 **获得**  `pointsi` 的分数，但是你将 **无法** 解决接下来的 `brainpoweri` 个问题（即只能跳过接下来的 `brainpoweri` 个问题）。如果你跳过问题 `i` ，你可以对下一个问题决定使用哪种操作。

*   比方说，给你 `questions = [[3, 2], [4, 3], [4, 4], [2, 5]]` ：
    *   如果问题 `0` 被解决了， 那么你可以获得 `3` 分，但你不能解决问题 `1` 和 `2` 。
    *   如果你跳过问题 `0` ，且解决问题 `1` ，你将获得 `4` 分但是不能解决问题 `2` 和 `3` 。

请你返回这场考试里你能获得的 **最高** 分数。

**示例 1：**

**输入：**questions = \[\[3,2\],\[4,3\],\[4,4\],\[2,5\]\]
**输出：**5
**解释：**解决问题 0 和 3 得到最高分。
- 解决问题 0 ：获得 3 分，但接下来 2 个问题都不能解决。
- 不能解决问题 1 和 2
- 解决问题 3 ：获得 2 分
  总得分为：3 + 2 = 5 。没有别的办法获得 5 分或者多于 5 分。

**示例 2：**

**输入：**questions = \[\[1,1\],\[2,2\],\[3,3\],\[4,4\],\[5,5\]\]
**输出：**7
**解释：**解决问题 1 和 4 得到最高分。
- 跳过问题 0
- 解决问题 1 ：获得 2 分，但接下来 2 个问题都不能解决。
- 不能解决问题 2 和 3
- 解决问题 4 ：获得 5 分
  总得分为：2 + 5 = 7 。没有别的办法获得 7 分或者多于 7 分。

**提示：**

*   `1 <= questions.length <= 105`
*   `questions[i].length == 2`
*   `1 <= pointsi, brainpoweri <= 105`

[https://leetcode.cn/problems/solving-questions-with-brainpower/description/](https://leetcode.cn/problems/solving-questions-with-brainpower/description/)

```java
class Solution { // 正序遍历
    public long mostPoints(int[][] questions) { // 刷表法
        int n = questions.length;
        long[] dp = new long[n + 1];
        for (int i = 1; i <= n; i++) {
           dp[i] = Math.max(dp[i], dp[i - 1]); // 不选当前的nums[i - 1]
           int[] q = questions[i - 1];
           int j = Math.min(i + q[1], n);
           dp[j] = Math.max(dp[j], dp[i - 1] + q[0]); // 选择当前的nums[i - 1],影响后面的dp[j];
        }
        return dp[n];
    }
}
// f[j] = f[i] + point[i]
```

```java
class Solution { // 推荐，实现简单，不过两种算法务必学会
    public long mostPoints(int[][] questions) { // 刷表法
        int n = questions.length;
        long[] dp = new long[n + 1];
        for(int i = n - 1; i >= 0; i--){
            int[] q = questions[i];
            int j = i + q[1] + 1;
            dp[i] = Math.max(dp[i + 1], q[0] + (j < n ? dp[j] : 0));
        }
        return dp[0];
    }
}
```

983\. 最低票价
----------

在一个火车旅行很受欢迎的国度，你提前一年计划了一些火车旅行。在接下来的一年里，你要旅行的日子将以一个名为 `days` 的数组给出。每一项是一个从 `1` 到 `365` 的整数。

火车票有 **三种不同的销售方式** ：

*   一张 **为期一天** 的通行证售价为 `costs[0]` 美元；
*   一张 **为期七天** 的通行证售价为 `costs[1]` 美元；
*   一张 **为期三十天** 的通行证售价为 `costs[2]` 美元。

通行证允许数天无限制的旅行。 例如，如果我们在第 `2` 天获得一张 **为期 7 天** 的通行证，那么我们可以连着旅行 7 天：第 `2` 天、第 `3` 天、第 `4` 天、第 `5` 天、第 `6` 天、第 `7` 天和第 `8` 天。

返回 _你想要完成在给定的列表 `days` 中列出的每一天的旅行所需要的最低消费_ 。

**示例 1：**

**输入：**days = \[1,4,6,7,8,20\], costs = \[2,7,15\]
**输出：**11
**解释：** 
例如，这里有一种购买通行证的方法，可以让你完成你的旅行计划：
在第 1 天，你花了 costs\[0\] = $2 买了一张为期 1 天的通行证，它将在第 1 天生效。
在第 3 天，你花了 costs\[1\] = $7 买了一张为期 7 天的通行证，它将在第 3, 4, ..., 9 天生效。
在第 20 天，你花了 costs\[0\] = $2 买了一张为期 1 天的通行证，它将在第 20 天生效。
你总共花了 $11，并完成了你计划的每一天旅行。

**示例 2：**

**输入：**days = \[1,2,3,4,5,6,7,8,9,10,30,31\], costs = \[2,7,15\]
**输出：**17
**解释：**
例如，这里有一种购买通行证的方法，可以让你完成你的旅行计划： 
在第 1 天，你花了 costs\[2\] = $15 买了一张为期 30 天的通行证，它将在第 1, 2, ..., 30 天生效。
在第 31 天，你花了 costs\[0\] = $2 买了一张为期 1 天的通行证，它将在第 31 天生效。 
你总共花了 $17，并完成了你计划的每一天旅行。

**提示：**

*   `1 <= days.length <= 365`
*   `1 <= days[i] <= 365`
*   `days` 按顺序严格递增
*   `costs.length == 3`
*   `1 <= costs[i] <= 1000`

[https://leetcode.cn/problems/minimum-cost-for-tickets/description/](https://leetcode.cn/problems/minimum-cost-for-tickets/description/)

```java
class Solution { // 后面影响前面
    public int mincostTickets(int[] days, int[] costs) {
        int n = days.length;
        int mxDay = days[n - 1], mnDay = days[0];
        int[] dp = new int[mxDay + 31];
        for(int d = mxDay, i = n - 1; d >= mnDay && i >= 0; d--){
            if(d == days[i]){
                // 说明今天必须出行
                // 也可提前将所有 days 放入 Set，再通过 set.contains() 判断
                dp[d] = Math.min(dp[d + 1] + costs[0], Math.min(dp[d + 7] + costs[1], dp[d + 30] + costs[2]));
                i--;
            }else{ // 今天不用出行
                dp[d] = dp[d + 1]; 
            }
        }
        return dp[mnDay];
    }
}

```

2901\. 最长相邻不相等子序列 II (好难好难)
--------------------

给你一个整数 `n` 和一个下标从 **0** 开始的字符串数组 `words` ，和一个下标从 **0** 开始的数组 `groups` ，两个数组长度都是 `n` 。

两个长度相等字符串的 **汉明距离** 定义为对应位置字符 **不同** 的数目。

你需要从下标 `[0, 1, ..., n - 1]` 中选出一个 **最长**

**子序列**

 ，将这个子序列记作长度为 `k` 的 `[i0, i1, ..., ik - 1]` ，它需要满足以下条件：

*   **相邻** 下标对应的 `groups` 值 **不同**。即，对于所有满足 `0 < j + 1 < k` 的 `j` 都有 `groups[ij] != groups[ij + 1]` 。
*   对于所有 `0 < j + 1 < k` 的下标 `j` ，都满足 `words[ij]` 和 `words[ij + 1]` 的长度 **相等** ，且两个字符串之间的 **汉明距离** 为 `1` 。

请你返回一个字符串数组，它是下标子序列 **依次** 对应 `words` 数组中的字符串连接形成的字符串数组。如果有多个答案，返回任意一个。

**子序列** 指的是从原数组中删掉一些（也可能一个也不删掉）元素，剩余元素不改变相对位置得到的新的数组。

**注意：**`words` 中的字符串长度可能 **不相等** 。

**示例 1：**

**输入：**n = 3, words = \["bab","dab","cab"\], groups = \[1,2,2\]
**输出：**\["bab","cab"\]
**解释：**一个可行的子序列是 \[0,2\] 。
- groups\[0\] != groups\[2\]
- words\[0\].length == words\[2\].length 且它们之间的汉明距离为 1 。
  所以一个可行的答案是 \[words\[0\],words\[2\]\] = \["bab","cab"\] 。
  另一个可行的子序列是 \[0,1\] 。
- groups\[0\] != groups\[1\]
- words\[0\].length = words\[1\].length 且它们之间的汉明距离为 1 。
  所以另一个可行的答案是 \[words\[0\],words\[1\]\] = \["bab","dab"\] 。
  符合题意的最长子序列的长度为 2 。

**示例 2：**

**输入：**n = 4, words = \["a","b","c","d"\], groups = \[1,2,3,4\]
**输出：**\["a","b","c","d"\]
**解释：**我们选择子序列 \[0,1,2,3\] 。
它同时满足两个条件。
所以答案为 \[words\[0\],words\[1\],words\[2\],words\[3\]\] = \["a","b","c","d"\] 。
它是所有下标子序列里最长且满足所有条件的。
所以它是唯一的答案。

**提示：**

*   `1 <= n == words.length == groups.length <= 1000`
*   `1 <= words[i].length <= 10`
*   `1 <= groups[i] <= n`
*   `words` 中的字符串 **互不相同** 。
*   `words[i]` 只包含小写英文字母。

[https://leetcode.cn/problems/longest-unequal-adjacent-groups-subsequence-ii/description/](https://leetcode.cn/problems/longest-unequal-adjacent-groups-subsequence-ii/description/)

```java
import java.util.ArrayList;
import java.util.List;
 
class Solution { // 太牛了，又学到新东西了
    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;
        int[] dp = new int[n];
        int[] from = new int[n];
        int mx = n - 1;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (dp[j] > dp[i] && groups[j] != groups[i] && judge(words[i], words[j])) {
                    dp[i] = dp[j];
                    from[i] = j;
                }
            }
            dp[i]++;
            if (dp[i] > dp[mx]) {
                mx = i;
            }
        }
        int m = dp[mx];
        ArrayList<String> ans = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            ans.add(words[mx]);
            mx = from[mx];
        }
        return ans;
    }

    private boolean judge(String s1,String s2){
        if(s1.length() != s2.length()){
            return false;
        }
        int cnt = 0;
        for(int i = 0; i < s1.length(); i++){
            if (s1.charAt(i) == s2.charAt(i)) {
                continue;
            }
            cnt++;
            if (cnt > 1) {
                return false;
            }
        }
        return true;
    }
}
```

3144\. 分割字符频率相等的最少子字符串
----------------------

给你一个字符串 `s` ，你需要将它分割成一个或者更多的 **平衡** 子字符串。比方说，`s == "ababcc"` 那么 `("abab", "c", "c")` ，`("ab", "abc", "c")` 和 `("ababcc")` 都是合法分割，但是 `("a", **"bab"**, "cc")` ，`(**"aba"**, "bc", "c")` 和 `("ab", **"abcc"**)` 不是，不平衡的子字符串用粗体表示。

请你返回 `s` **最少** 能分割成多少个平衡子字符串。

**注意：**一个 **平衡** 字符串指的是字符串中所有字符出现的次数都相同。

**示例 1：**

**输入：**s = "fabccddg"

**输出：**3

**解释：**

我们可以将 `s` 分割成 3 个子字符串：`("fab, "ccdd", "g")` 或者 `("fabc", "cd", "dg")` 。

**示例 2：**

**输入：**s = "abababaccddb"

**输出：**2

**解释：**

我们可以将 `s` 分割成 2 个子字符串：`("abab", "abaccddb")` 。

**提示：**

*   `1 <= s.length <= 1000`
*   `s` 只包含小写英文字母。

[https://leetcode.cn/problems/minimum-substring-partition-of-equal-character-frequency/description/](https://leetcode.cn/problems/minimum-substring-partition-of-equal-character-frequency/description/)

```java
import java.util.Arrays;

class Solution {
    public int minimumSubstringsInPartition(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.setAll(dp, i -> i);
        for (int i = 1; i <= n; i++) {
            int freq = 0;
            int mx = 0;
            int[] cnt = new int[26];
            for (int j = i - 1; j >= 0; j--) {
                int k = s.charAt(j) - 'a';
                cnt[k]++;
                if (cnt[k] == 1) {
                    freq++;
                }
                mx = Math.max(mx, cnt[k]);
                if (mx * freq == i - j) {
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }
        return dp[n];
    }
}
```

871\. 最低加油次数
------------

汽车从起点出发驶向目的地，该目的地位于出发位置东面 `target` 英里处。

沿途有加油站，用数组 `stations` 表示。其中 `stations[i] = [positioni, fueli]` 表示第 `i` 个加油站位于出发位置东面 `positioni` 英里处，并且有 `fueli` 升汽油。

假设汽车油箱的容量是无限的，其中最初有 `startFuel` 升燃料。它每行驶 1 英里就会用掉 1 升汽油。当汽车到达加油站时，它可能停下来加油，将所有汽油从加油站转移到汽车中。

为了到达目的地，汽车所必要的最低加油次数是多少？如果无法到达目的地，则返回 `-1` 。

注意：如果汽车到达加油站时剩余燃料为 `0`，它仍然可以在那里加油。如果汽车到达目的地时剩余燃料为 `0`，仍然认为它已经到达目的地。

**示例 1：**

**输入：**target = 1, startFuel = 1, stations = \[\]
**输出：**0
**解释：**可以在不加油的情况下到达目的地。

**示例 2：**

**输入：**target = 100, startFuel = 1, stations = \[\[10,100\]\]
**输出：**\-1
**解释：**无法抵达目的地，甚至无法到达第一个加油站。

**示例 3：**

**输入：**target = 100, startFuel = 10, stations = \[\[10,60\],\[20,30\],\[30,30\],\[60,40\]\]
**输出：**2
**解释：**
出发时有 10 升燃料。
开车来到距起点 10 英里处的加油站，消耗 10 升燃料。将汽油从 0 升加到 60 升。
然后，从 10 英里处的加油站开到 60 英里处的加油站（消耗 50 升燃料），
并将汽油从 10 升加到 50 升。然后开车抵达目的地。
沿途在两个加油站停靠，所以返回 2 。

**提示：**

*   `1 <= target, startFuel <= 109`
*   `0 <= stations.length <= 500`
*   `1 <= positioni < positioni+1 < target`
*   `1 <= fueli < 109`

[https://leetcode.cn/problems/minimum-number-of-refueling-stops/solutions/1636921/zui-di-jia-you-ci-shu-by-leetcode-soluti-nmga/](https://leetcode.cn/problems/minimum-number-of-refueling-stops/solutions/1636921/zui-di-jia-you-ci-shu-by-leetcode-soluti-nmga/)

```java
class Solution {
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        int[] dp = new int[n + 1]; // dp[i] 表示加油 i 次的最大行驶英里数
        dp[0] = startFuel;
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                if (dp[j] >= stations[i][0]) {
                    dp[j + 1] = Math.max(dp[j + 1], dp[j] + stations[i][1]);
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            if (dp[i] >= target) {
                return i;
            }
        }
        return -1;
    }
}
```

```java
class Solution { // 贪心 + 优先队列
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> b - a);
        int ans = 0, prev = 0, fuel = startFuel;
        int n = stations.length;
        for (int i = 0; i <= n; i++) {
            int curr = i < n ? stations[i][0] : target;
            fuel -= curr - prev;
            while (fuel < 0 && !pq.isEmpty()) {
                fuel += pq.poll();
                ans++;
            }
            if (fuel < 0) {
                return -1;
            }
            if (i < n) {
                pq.offer(stations[i][1]);
                prev = curr;
            }
        }
        return ans;
    }
}
```

