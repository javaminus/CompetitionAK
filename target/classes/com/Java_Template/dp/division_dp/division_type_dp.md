# §6.2 计算划分最优值

> 计算最少（最多）可以划分出多少段、最优划分得分等。
>
> 一般定义 f[i] 表示长为 i 的前缀 a[:i] 在题目约束下，分割出的最少（最多）子数组个数（或者定义成分割方案数）。
>
> 枚举最后一个子数组的左端点 L，从 f[L] 转移到 f[i]，并考虑 a[L:j]对最优解的影响。
>

3130\. 找出所有稳定的二进制数组 II
----------------------

给你 3 个正整数 `zero` ，`one` 和 `limit` 。一个二进制数组 `arr` 如果满足以下条件，那么我们称它是 **稳定的** ：

*   0 在 `arr` 中出现次数 **恰好** 为 `zero` 。
*   1 在 `arr` 中出现次数 **恰好** 为 `one` 。
*   `arr` 中每个长度超过 `limit` 的子数组都 **同时** 包含 0 和 1 。


请你返回 **稳定** 二进制数组的 _总_ 数目。

由于答案可能很大，将它对 `109 + 7` **取余** 后返回。

**示例 1：**

**输入：**zero = 1, one = 1, limit = 2

**输出：**2

**解释：**

两个稳定的二进制数组为 `[1,0]` 和 `[0,1]` ，两个数组都有一个 0 和一个 1 ，且没有子数组长度大于 2 。

**示例 2：**

**输入：**zero = 1, one = 2, limit = 1

**输出：**1

**解释：**

唯一稳定的二进制数组是 `[1,0,1]` 。

二进制数组 `[1,1,0]` 和 `[0,1,1]` 都有长度为 2 且元素全都相同的子数组，所以它们不稳定。

**示例 3：**

**输入：**zero = 3, one = 3, limit = 2

**输出：**14

**解释：**

所有稳定的二进制数组包括 `[0,0,1,0,1,1]` ，`[0,0,1,1,0,1]` ，`[0,1,0,0,1,1]` ，`[0,1,0,1,0,1]` ，`[0,1,0,1,1,0]` ，`[0,1,1,0,0,1]` ，`[0,1,1,0,1,0]` ，`[1,0,0,1,0,1]` ，`[1,0,0,1,1,0]` ，`[1,0,1,0,0,1]` ，`[1,0,1,0,1,0]` ，`[1,0,1,1,0,0]` ，`[1,1,0,0,1,0]` 和 `[1,1,0,1,0,0]` 。

**提示：**

*   `1 <= zero, one, limit <= 1000`

[https://leetcode.cn/problems/find-all-possible-stable-binary-arrays-ii/description/](https://leetcode.cn/problems/find-all-possible-stable-binary-arrays-ii/description/)
```java
import java.util.Arrays;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    int limit;
    int[][][] memo;
    public int numberOfStableArrays(int zero, int one, int limit) {
        this.limit = limit;
        memo = new int[zero + 1][one + 1][2];
        for (int i = 0; i < zero + 1; i++) {
            for (int j = 0; j < one + 1; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return (dfs(zero, one, 0) + dfs(zero, one, 1)) % Mod;
    }
    private int dfs(int i, int j, int k) { // 选择i个0，j个1，第（i+j）个数为k 的合法方案数
        if (i == 0) { // 选择0个0
            return k == 1 && j <= limit ? 1 : 0;
        }
        if (j == 0) { // 选择0个1
            return k == 0 && i <= limit ? 1 : 0;
        }
        if (memo[i][j][k] != -1) {
            return memo[i][j][k];
        }
        if (k == 0) { 
            memo[i][j][k] = (int) (((long) dfs(i - 1, j, 0) + dfs(i - 1, j, 1) + (i > limit ? Mod - dfs(i - limit - 1, j, 1) : 0)) % Mod); // （int） ((long) f %Mod) 这样写才有用，这样没有用的（int）(long)） f %Mod
        }else{ // k==1
            memo[i][j][k] = (int) (((long) dfs(i, j - 1, 0) + dfs(i, j - 1, 1) + (j > limit ? Mod - dfs(i, j - limit - 1, 0) : 0)) % Mod);
        }
        return memo[i][j][k];
    }
} // 为什么是i - limit - 1,因为大于limit才不合法，等于limit合法！！！
```
2369\. 检查数组是否存在有效划分
-------------------

给你一个下标从 **0** 开始的整数数组 `nums` ，你必须将数组划分为一个或多个 **连续** 子数组。

如果获得的这些子数组中每个都能满足下述条件 **之一** ，则可以称其为数组的一种 **有效** 划分：

1.  子数组 **恰** 由 `2` 个相等元素组成，例如，子数组 `[2,2]` 。
2.  子数组 **恰** 由 `3` 个相等元素组成，例如，子数组 `[4,4,4]` 。
3.  子数组 **恰** 由 `3` 个连续递增元素组成，并且相邻元素之间的差值为 `1` 。例如，子数组 `[3,4,5]` ，但是子数组 `[1,3,5]` 不符合要求。

如果数组 **至少** 存在一种有效划分，返回 `true` ，否则，返回 `false` 。

**示例 1：**

**输入：**nums = \[4,4,4,5,6\]
**输出：**true
**解释：**数组可以划分成子数组 \[4,4\] 和 \[4,5,6\] 。
这是一种有效划分，所以返回 true 。

**示例 2：**

**输入：**nums = \[1,1,1,2\]
**输出：**false
**解释：**该数组不存在有效划分。

**提示：**

*   `2 <= nums.length <= 105`
*   `1 <= nums[i] <= 106`

[https://leetcode.cn/problems/check-if-there-is-a-valid-partition-for-the-array/description/](https://leetcode.cn/problems/check-if-there-is-a-valid-partition-for-the-array/description/)

```java
class Solution {
    public boolean validPartition(int[] nums) {
        int n = nums.length;
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1]) {
                dp[i + 1] = dp[i - 1] | dp[i + 1];
            }
            if (i > 1) {
                if (nums[i] == nums[i - 1] && nums[i - 1] == nums[i - 2] || nums[i] == nums[i - 1] + 1 && nums[i - 1] == nums[i - 2] + 1) {
                    dp[i + 1] = dp[i - 2] | dp[i + 1];
                }
            }
        }
        return dp[n];
    }
}
```

139\. 单词拆分
----------

给你一个字符串 `s` 和一个字符串列表 `wordDict` 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 `s` 则返回 `true`。

**注意：**不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。

**示例 1：**

**输入:** s = "leetcode", wordDict = \["leet", "code"\]
**输出:** true
**解释:** 返回 true 因为 "leetcode" 可以由 "leet" 和 "code" 拼接成。

**示例 2：**

**输入:** s = "applepenapple", wordDict = \["apple", "pen"\]
**输出:** true
**解释:** 返回 true 因为 "applepenapple" 可以由 "apple" "pen" "apple" 拼接成。
     注意，你可以重复使用字典中的单词。

**示例 3：**

**输入:** s = "catsandog", wordDict = \["cats", "dog", "sand", "and", "cat"\]
**输出:** false

**提示：**

*   `1 <= s.length <= 300`
*   `1 <= wordDict.length <= 1000`
*   `1 <= wordDict[i].length <= 20`
*   `s` 和 `wordDict[i]` 仅由小写英文字母组成
*   `wordDict` 中的所有字符串 **互不相同**

[https://leetcode.cn/problems/word-break/description/](https://leetcode.cn/problems/word-break/description/)

```java
import java.util.List;

class Solution {
    public boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 0; i < n; i++) {
            for (String str : wordDict) {
                if (i + 1 >= str.length()) {
                    if (str.equals(s.substring(i - str.length() + 1, i + 1))) {
                        dp[i + 1] = dp[i + 1] | dp[i + 1 - str.length()];
                    }
                }
            }
        }
        return dp[n];
    }
}
```

```java
class Solution {
    // 7ms
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        Set<String> set = new HashSet<>(wordDict);
        for (int r = 1; r < s.length() + 1; r++) {
            for (int l = 0; l < r; l++) {
                if (dp[l] == true && set.contains(s.substring(l, r))) {
                    dp[r] = true;
                }
            }
        }
        return dp[s.length()];
    }
}
```



3117\. 划分数组得到最小的值之和
-------------------

给你两个数组 `nums` 和 `andValues`，长度分别为 `n` 和 `m`。

数组的 **值** 等于该数组的 **最后一个** 元素。

你需要将 `nums` 划分为 `m` 个 **不相交的连续** 子数组，对于第 `ith` 个子数组 `[li, ri]`，子数组元素的按位`AND`运算结果等于 `andValues[i]`，换句话说，对所有的 `1 <= i <= m`，`nums[li] & nums[li + 1] & ... & nums[ri] == andValues[i]` ，其中 `&` 表示按位`AND`运算符。

返回将 `nums` 划分为 `m` 个子数组所能得到的可能的 **最小** 子数组 **值** 之和。如果无法完成这样的划分，则返回 `-1` 。

**示例 1：**

**输入：** nums = \[1,4,3,3,2\], andValues = \[0,3,3,2\]

**输出：** 12

**解释：**

唯一可能的划分方法为：

1.  `[1,4]` 因为 `1 & 4 == 0`
2.  `[3]` 因为单元素子数组的按位 `AND` 结果就是该元素本身
3.  `[3]` 因为单元素子数组的按位 `AND` 结果就是该元素本身
4.  `[2]` 因为单元素子数组的按位 `AND` 结果就是该元素本身

这些子数组的值之和为 `4 + 3 + 3 + 2 = 12`

**示例 2：**

**输入：** nums = \[2,3,5,7,7,7,5\], andValues = \[0,7,5\]

**输出：** 17

**解释：**

划分 `nums` 的三种方式为：

1.  `[[2,3,5],[7,7,7],[5]]` 其中子数组的值之和为 `5 + 7 + 5 = 17`
2.  `[[2,3,5,7],[7,7],[5]]` 其中子数组的值之和为 `7 + 7 + 5 = 19`
3.  `[[2,3,5,7,7],[7],[5]]` 其中子数组的值之和为 `7 + 7 + 5 = 19`

子数组值之和的最小可能值为 `17`

**示例 3：**

**输入：** nums = \[1,2,3,4\], andValues = \[2\]

**输出：** \-1

**解释：**

整个数组 `nums` 的按位 `AND` 结果为 `0`。由于无法将 `nums` 划分为单个子数组使得元素的按位 `AND` 结果为 `2`，因此返回 `-1`。

**提示：**

*   `1 <= n == nums.length <= 104`
*   `1 <= m == andValues.length <= min(n, 10)`
*   `1 <= nums[i] < 105`
*   `0 <= andValues[j] < 105`

[https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/description/](https://leetcode.cn/problems/minimum-sum-of-values-by-dividing-array/description/)

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int minimumValueSum(int[] nums, int[] andValues) {
        HashMap<Long, Integer> memo = new HashMap<>();
        int ans = dfs(0, 0, -1, nums, andValues, memo);
        return ans < Integer.MAX_VALUE / 2 ? ans : -1;
    }

    private int dfs(int i, int j, int and, int[] nums, int[] andValues, Map<Long,Integer> memo) {
        int n = nums.length;
        int m = andValues.length;
        if (m - j > n - i) { // 剩余元素不足
            return Integer.MAX_VALUE / 2;
        }
        if (j == m) { // 分了m段
            return i == n ? 0 : Integer.MAX_VALUE / 2;
        }
        and &= nums[i];
        if (and < andValues[j]) { // 剪枝：无法等于 andValues[j]
            return Integer.MAX_VALUE / 2;
        }
        long mask = (long) i << 36 | (long) j << 32 | and; // 三个状态压缩成一个 long
        if (memo.containsKey(mask)) {
            return memo.get(mask);
        }
        int res = dfs(i + 1, j, and, nums, andValues, memo); // 不划分
        if (and == andValues[j]) { // 划分，nums[i]是这一段的最后一个数
            res = Math.min(res, dfs(i + 1, j + 1, -1, nums, andValues, memo) + nums[i]);
        }
        memo.put(mask, res);
        return res;
    }
}
```

132\. 分割回文串 II
--------------

给你一个字符串 `s`，请你将 `s` 分割成一些子串，使每个子串都是

回文串

。

返回符合要求的 **最少分割次数** 。

**示例 1：**

**输入：**s = "aab"
**输出：**1
**解释：**只需一次分割就可将 _s_ 分割成 \["aa","b"\] 这样两个回文子串。

**示例 2：**

**输入：**s = "a"
**输出：**0

**示例 3：**

**输入：**s = "ab"
**输出：**1

**提示：**

*   `1 <= s.length <= 2000`
*   `s` 仅由小写英文字母组成

[https://leetcode.cn/problems/palindrome-partitioning-ii/description/](https://leetcode.cn/problems/palindrome-partitioning-ii/description/)

> 总结：求回文串都可以用f数组表示i到j是否是回文串

```java
import java.util.Arrays;

class Solution {
    public int minCut(String s) {
        // 定义f[i][j]表示字符串[i,j]是否是回文串
        int n = s.length();
        boolean[][] f = new boolean[n][n]; // 最终是[0,n - 1]，所以先降序，后升序
        for (int i = 0; i < n; i++) {
            Arrays.fill(f[i], true);
        }
        for (int i = n - 1; i >= 0; i--) { // 模板
            for (int j = i + 1; j < n; j++) {
                f[i][j] = s.charAt(i) == s.charAt(j) && f[i + 1][j - 1];
            }
        }
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        for (int i = 0; i < n; i++) {
            if (f[0][i]) {
                dp[i] = 0;
            }else{
                for (int j = 0; j < i; j++) {
                    if (f[j + 1][i]) {
                        dp[i] = Math.min(dp[i], dp[j] + 1);
                    }
                }
            }
        }
        return dp[n - 1];
    }
    
}
```

2707\. 字符串中的额外字符
----------------

给你一个下标从 **0** 开始的字符串 `s` 和一个单词字典 `dictionary` 。你需要将 `s` 分割成若干个 **互不重叠** 的子字符串，每个子字符串都在 `dictionary` 中出现过。`s` 中可能会有一些 **额外的字符** 不在任何子字符串中。

请你采取最优策略分割 `s` ，使剩下的字符 **最少** 。

**示例 1：**

**输入：**s = "leetscode", dictionary = \["leet","code","leetcode"\]
**输出：**1
**解释：**将 s 分成两个子字符串：下标从 0 到 3 的 "leet" 和下标从 5 到 8 的 "code" 。只有 1 个字符没有使用（下标为 4），所以我们返回 1 。

**示例 2：**

**输入：**s = "sayhelloworld", dictionary = \["hello","world"\]
**输出：**3
**解释：**将 s 分成两个子字符串：下标从 3 到 7 的 "hello" 和下标从 8 到 12 的 "world" 。下标为 0 ，1 和 2 的字符没有使用，所以我们返回 3 。

**提示：**

*   `1 <= s.length <= 50`
*   `1 <= dictionary.length <= 50`
*   `1 <= dictionary[i].length <= 50`
*   `dictionary[i]` 和 `s` 只包含小写英文字母。
*   `dictionary` 中的单词互不相同。

[https://leetcode.cn/problems/extra-characters-in-a-string/description/](https://leetcode.cn/problems/extra-characters-in-a-string/description/)

```java
import java.util.HashSet;

class Solution {
    public int minExtraChar(String s, String[] dictionary) {
        HashSet<String> set = new HashSet<>();
        for (String x : dictionary) {
            set.add(x);
        }
        int n = s.length();
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i] + 1; // 不选
            for (int j = 0; j <= i; j++) {
                if (set.contains(s.substring(j, i + 1))) {
                    dp[i + 1] = Math.min(dp[i + 1], dp[j]);
                }
            }
        }
        return dp[n];
    }
}
```

```java
class Solution {
    public int minExtraChar(String s, String[] dictionary) {
        int n = s.length();
        int[] dp = new int[n + 1]; // 表示字符串前i个位置最多剩下的次数
        Trie root = new Trie();
        for (String x : dictionary) {
            root.insert(new StringBuilder(x).reverse().toString());
        }
        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i] + 1;
            Trie curr = root;
            for (int j = i; j >= 0; j--) {
                if (curr.son[s.charAt(j) - 'a'] == null) {
                    break;
                }
                curr = curr.son[s.charAt(j) - 'a'];
                if (curr.len != -1) { // 表示从j到i这一段为0
                    dp[i + 1] = Math.min(dp[i + 1], dp[j]);
                }
            }
        }
        return dp[n];
    }

    class Trie{
        Trie[] son;
        int len;

        public Trie() {
            son = new Trie[26];
            len = -1;
        }

        void insert(String s) {
            Trie root = this;
            int n = s.length();
            for (int i = 0; i < n; i++) {
                int index = s.charAt(i) - 'a';
                if (root.son[index] == null) {
                    root.son[index] = new Trie();
                }
                root = root.son[index];
            }
            root.len = n;
        }
    }
}
```

3196\. 最大化子数组的总成本
-----------------

给你一个长度为 `n` 的整数数组 `nums`。

子数组 `nums[l..r]`（其中 `0 <= l <= r < n`）的 **成本** 定义为：

`cost(l, r) = nums[l] - nums[l + 1] + ... + nums[r] * (−1)r − l`

你的任务是将 `nums` 分割成若干子数组，使得所有子数组的成本之和 **最大化**，并确保每个元素 **正好** 属于一个子数组。

具体来说，如果 `nums` 被分割成 `k` 个子数组，且分割点为索引 `i1, i2, ..., ik − 1`（其中 `0 <= i1 < i2 < ... < ik - 1 < n - 1`），则总成本为：

`cost(0, i1) + cost(i1 + 1, i2) + ... + cost(ik − 1 + 1, n − 1)`

返回在最优分割方式下的子数组成本之和的最大值。

**注意：**如果 `nums` 没有被分割，即 `k = 1`，则总成本即为 `cost(0, n - 1)`。

**示例 1：**

**输入：** nums = \[1,-2,3,4\]

**输出：** 10

**解释：**

一种总成本最大化的方法是将 `[1, -2, 3, 4]` 分割成子数组 `[1, -2, 3]` 和 `[4]`。总成本为 `(1 + 2 + 3) + 4 = 10`。

**示例 2：**

**输入：** nums = \[1,-1,1,-1\]

**输出：** 4

**解释：**

一种总成本最大化的方法是将 `[1, -1, 1, -1]` 分割成子数组 `[1, -1]` 和 `[1, -1]`。总成本为 `(1 + 1) + (1 + 1) = 4`。

**示例 3：**

**输入：** nums = \[0\]

**输出：** 0

**解释：**

无法进一步分割数组，因此答案为 0。

**示例 4：**

**输入：** nums = \[1,-1\]

**输出：** 2

**解释：**

选择整个数组，总成本为 `1 + 1 = 2`，这是可能的最大成本。

**提示：**

*   `1 <= nums.length <= 105`
*   `-109 <= nums[i] <= 109`

[https://leetcode.cn/problems/maximize-total-cost-of-alternating-subarrays/description/](https://leetcode.cn/problems/maximize-total-cost-of-alternating-subarrays/description/)

```java
import java.util.Arrays;

class Solution { // 45ms
    long[][] memo;
    public long maximumTotalCost(int[] nums) {
        int n = nums.length;
        memo = new long[n][2];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(1, 0, nums) + nums[0];
    }

    private long dfs(int i,int j, int[] nums) { // 定义到达点i的最大值
        if (i == nums.length) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        long res = 0L;
        if (j == 0) { // 表示上一位选的正
            res = Math.max(nums[i] + dfs(i + 1, 0, nums), -nums[i] + dfs(i + 1, 1, nums));
        }
        if (j == 1) { // 表示上一位选的负
            res = nums[i] + dfs(i + 1, 0, nums);
        }
        return memo[i][j] = res;
    }
}
```

```java
import java.util.Arrays;

class Solution { // 2ms
    public long maximumTotalCost(int[] nums) {
        // 就是任意节点都可以是加正号，但是负号前面的那个数必须是正号
        int n = nums.length;
        long[] dp = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + nums[i - 1];
            if (i > 1 && nums[i - 1] < 0) {
                dp[i] = Math.max(dp[i], dp[i - 2] + nums[i - 2] - nums[i - 1]);
            }
        }
        return dp[n];
    }
}
```

2767\. 将字符串分割为最少的美丽子字符串
-----------------------

给你一个二进制字符串 `s` ，你需要将字符串分割成一个或者多个 **子字符串**  ，使每个子字符串都是 **美丽** 的。

如果一个字符串满足以下条件，我们称它是 **美丽** 的：

*   它不包含前导 0 。
*   它是 `5` 的幂的 **二进制** 表示。

请你返回分割后的子字符串的 **最少** 数目。如果无法将字符串 `s` 分割成美丽子字符串，请你返回 `-1` 。

子字符串是一个字符串中一段连续的字符序列。

**示例 1：**

**输入：**s = "1011"
**输出：**2
**解释：**我们可以将输入字符串分成 \["101", "1"\] 。
- 字符串 "101" 不包含前导 0 ，且它是整数 51 = 5 的二进制表示。
- 字符串 "1" 不包含前导 0 ，且它是整数 50 = 1 的二进制表示。
  最少可以将 s 分成 2 个美丽子字符串。

**示例 2：**

**输入：**s = "111"
**输出：**3
**解释：**我们可以将输入字符串分成 \["1", "1", "1"\] 。
- 字符串 "1" 不包含前导 0 ，且它是整数 50 = 1 的二进制表示。
  最少可以将 s 分成 3 个美丽子字符串。

**示例 3：**

**输入：**s = "0"
**输出：**\-1
**解释：**无法将给定字符串分成任何美丽子字符串。

**提示：**

*   `1 <= s.length <= 15`
*   `s[i]` 要么是 `'0'` 要么是 `'1'` 。

[https://leetcode.cn/problems/partition-string-into-minimum-beautiful-substrings/](https://leetcode.cn/problems/partition-string-into-minimum-beautiful-substrings/)

```java
import java.util.Arrays;
import java.util.HashSet;

class Solution {
    private static HashSet<String> set = new HashSet<>();
    static { // 先预处理5的二进制
        for (int i = 1; i < 40000; i *= 5) {
            set.add(Integer.toString(i, 2));
        }
    }
    public int minimumBeautifulSubstrings(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (set.contains(s.substring(j, i + 1))) {
                    dp[i + 1] = Math.min(dp[i + 1], dp[j] + 1);
                }
            }
        }
        return dp[n] == Integer.MAX_VALUE / 2 ? -1 : dp[n];
    }
}
```

91\. 解码方法
---------

一条包含字母 `A-Z` 的消息通过以下映射进行了 **编码** ：

'A' -> "1"
'B' -> "2"
...
'Z' -> "26"

要 **解码** 已编码的消息，所有数字必须基于上述映射的方法，反向映射回字母（可能有多种方法）。例如，`"11106"` 可以映射为：

*   `"AAJF"` ，将消息分组为 `(1 1 10 6)`
*   `"KJF"` ，将消息分组为 `(11 10 6)`

注意，消息不能分组为  `(1 11 06)` ，因为 `"06"` 不能映射为 `"F"` ，这是由于 `"6"` 和 `"06"` 在映射中并不等价。

给你一个只含数字的 **非空** 字符串 `s` ，请计算并返回 **解码** 方法的 **总数** 。

题目数据保证答案肯定是一个 **32 位** 的整数。

**示例 1：**

**输入：**s = "12"
**输出：**2
**解释：**它可以解码为 "AB"（1 2）或者 "L"（12）。

**示例 2：**

**输入：**s = "226"
**输出：**3
**解释：**它可以解码为 "BZ" (2 26), "VF" (22 6), 或者 "BBF" (2 2 6) 。

**示例 3：**

**输入：**s = "06"
**输出：**0
**解释：**"06" 无法映射到 "F" ，因为存在前导零（"6" 和 "06" 并不等价）。

**提示：**

*   `1 <= s.length <= 100`
*   `s` 只包含数字，并且可能包含前导零。

[https://leetcode.cn/problems/decode-ways/description/](https://leetcode.cn/problems/decode-ways/description/)

```java
class Solution {
    public int numDecodings(String s) {
        int n = s.length();
        int[] dp = new int[n + 1];
        if (s.charAt(0) == '0') {
            return 0;
        }
        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i];
            if (i > 0) {
                if (s.charAt(i) == '0') {
                    if (s.charAt(i - 1) - '0' > 2 || s.charAt(i - 1) == '0') {
                        return 0;
                    }
                    dp[i + 1] = dp[i - 1];
                    continue;
                }
                if (s.charAt(i - 1) != '0'&& Integer.parseInt(s.substring(i - 1, i + 1)) <= 26) {
                    dp[i + 1] += dp[i - 1] + 1;
                }
            }
        }
        return dp[n] + 1;
    }
}
```

639\. 解码方法 II
-------------

一条包含字母 `A-Z` 的消息通过以下的方式进行了 **编码** ：

'A' -> "1"
'B' -> "2"
...
'Z' -> "26"

要 **解码** 一条已编码的消息，所有的数字都必须分组，然后按原来的编码方案反向映射回字母（可能存在多种方式）。例如，`"11106"` 可以映射为：

*   `"AAJF"` 对应分组 `(1 1 10 6)`
*   `"KJF"` 对应分组 `(11 10 6)`

注意，像 `(1 11 06)` 这样的分组是无效的，因为 `"06"` 不可以映射为 `'F'` ，因为 `"6"` 与 `"06"` 不同。

**除了** 上面描述的数字字母映射方案，编码消息中可能包含 `'*'` 字符，可以表示从 `'1'` 到 `'9'` 的任一数字（不包括 `'0'`）。例如，编码字符串 `"1*"` 可以表示 `"11"`、`"12"`、`"13"`、`"14"`、`"15"`、`"16"`、`"17"`、`"18"` 或 `"19"` 中的任意一条消息。对 `"1*"` 进行解码，相当于解码该字符串可以表示的任何编码消息。

给你一个字符串 `s` ，由数字和 `'*'` 字符组成，返回 **解码** 该字符串的方法 **数目** 。

由于答案数目可能非常大，返回 `109 + 7` 的 **模** 。

**示例 1：**

**输入：**s = "\*"
**输出：**9
**解释：**这一条编码消息可以表示 "1"、"2"、"3"、"4"、"5"、"6"、"7"、"8" 或 "9" 中的任意一条。
可以分别解码成字符串 "A"、"B"、"C"、"D"、"E"、"F"、"G"、"H" 和 "I" 。
因此，"\*" 总共有 9 种解码方法。

**示例 2：**

**输入：**s = "1\*"
**输出：**18
**解释：**这一条编码消息可以表示 "11"、"12"、"13"、"14"、"15"、"16"、"17"、"18" 或 "19" 中的任意一条。
每种消息都可以由 2 种方法解码（例如，"11" 可以解码成 "AA" 或 "K"）。
因此，"1\*" 共有 9 \* 2 = 18 种解码方法。

**示例 3：**

**输入：**s = "2\*"
**输出：**15
**解释：**这一条编码消息可以表示 "21"、"22"、"23"、"24"、"25"、"26"、"27"、"28" 或 "29" 中的任意一条。
"21"、"22"、"23"、"24"、"25" 和 "26" 由 2 种解码方法，但 "27"、"28" 和 "29" 仅有 1 种解码方法。
因此，"2\*" 共有 (6 \* 2) + (3 \* 1) = 12 + 3 = 15 种解码方法。

**提示：**

*   `1 <= s.length <= 105`
*   `s[i]` 是 `0 - 9` 中的一位数字或字符 `'*'`

[https://leetcode.cn/problems/decode-ways-ii/description/](https://leetcode.cn/problems/decode-ways-ii/description/)

```java
class Solution {
    private static final int Mod = (int) 1e9 + 7;
    public int numDecodings(String s) {
        int n = s.length();
        long[] dp = new long[n + 1];
        dp[0] = 1;
        dp[1] = (s.charAt(0) == '*' ? 9 : 1);
        if (s.charAt(0) == '0') {
            return 0;
        }
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) != '*') {
                if (s.charAt(i) != '0') {
                    dp[i + 1] = dp[i];
                }
                if (s.charAt(i - 1) == '1') {
                    dp[i + 1] += dp[i - 1];
                } else if (s.charAt(i - 1) == '2' && s.charAt(i) < '7') {
                    dp[i + 1] += dp[i - 1];
                } else if (s.charAt(i - 1) == '*') {
                    if (s.charAt(i) < '7') {
                        dp[i + 1] += (dp[i - 1] * 2);
                    }else{
                        dp[i + 1] += dp[i - 1];
                    }
                }
            }else{
                dp[i + 1] = dp[i] * 9;
                if (s.charAt(i - 1) == '1') {
                    dp[i + 1] += dp[i - 1] * 9;
                } else if (s.charAt(i - 1) == '2') {
                    dp[i + 1] += dp[i - 1] * 6;
                } else if (s.charAt(i - 1) == '*') {
                    dp[i + 1] += dp[i - 1] * 15;
                }
            }
            dp[i + 1] %= Mod;
        }
        return (int) dp[n];
    }
}
```

LCR 165. 解密数字
-------------

现有一串神秘的密文 `ciphertext`，经调查，密文的特点和规则如下：

*   密文由非负整数组成
*   数字 0-25 分别对应字母 a-z

请根据上述规则将密文 `ciphertext` 解密为字母，并返回共有多少种解密结果。

**示例 1:**

**输入:** ciphertext = 216612
**输出:** `6`
**解释:** 216612 解密后有 6 种不同的形式，分别是 "cbggbc"，"vggbc"，"vggm"，"cbggm"，"cqggbc" 和 "cqggm" 

**提示：**

*   `0 <= ciphertext < 231`

[https://leetcode.cn/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof/description/](https://leetcode.cn/problems/ba-shu-zi-fan-yi-cheng-zi-fu-chuan-lcof/description/)

```java
class Solution {
    public int crackNumber(int num) {
        String s = Integer.toString(num);
        int n = s.length();
        int[] dp = new int[n + 1];
        dp[0] = dp[1] = 1;
        for (int i = 1; i < n; i++) {
            dp[i + 1] = dp[i];
            String pre = s.substring(i - 1, i + 1);
            if (pre.compareTo("25") <= 0 && pre.compareTo("10") >= 0) {
                dp[i + 1] += dp[i - 1];
            }
        }
        return dp[n];
    }
}
```

1416\. 恢复数组
-----------

某个程序本来应该输出一个整数数组。但是这个程序忘记输出空格了以致输出了一个数字字符串，我们所知道的信息只有：数组中所有整数都在 `[1, k]` 之间，且数组中的数字都没有前导 0 。

给你字符串 `s` 和整数 `k` 。可能会有多种不同的数组恢复结果。

按照上述程序，请你返回所有可能输出字符串 `s` 的数组方案数。

由于数组方案数可能会很大，请你返回它对 `10^9 + 7` **取余** 后的结果。

**示例 1：**

**输入：**s = "1000", k = 10000
**输出：**1
**解释：**唯一一种可能的数组方案是 \[1000\]

**示例 2：**

**输入：**s = "1000", k = 10
**输出：**0
**解释：**不存在任何数组方案满足所有整数都 >= 1 且 <= 10 同时输出结果为 s 。

**示例 3：**

**输入：**s = "1317", k = 2000
**输出：**8
**解释：**可行的数组方案为 \[1317\]，\[131,7\]，\[13,17\]，\[1,317\]，\[13,1,7\]，\[1,31,7\]，\[1,3,17\]，\[1,3,1,7\]

**示例 4：**

**输入：**s = "2020", k = 30
**输出：**1
**解释：**唯一可能的数组方案是 \[20,20\] 。 \[2020\] 不是可行的数组方案，原因是 2020 > 30 。 \[2,020\] 也不是可行的数组方案，因为 020 含有前导 0 。

**示例 5：**

**输入：**s = "1234567890", k = 90
**输出：**34

**提示：**

*   `1 <= s.length <= 10^5`.
*   `s` 只包含数字且不包含前导 0 。
*   `1 <= k <= 10^9`.

[https://leetcode.cn/problems/restore-the-array/description/](https://leetcode.cn/problems/restore-the-array/description/)

```java
import java.util.Arrays;

class Solution { // 285ms 切片耗时
    private static final int Mod = (int) 1e9 + 7;
    public int numberOfArrays(String s, int k) {
        int n = s.length();
        if (s.charAt(0) == '0') {
            return 0;
        }
        long[] dp = new long[n + 1];
        dp[0] = 1;
        for (int i = 0; i < n; i++) {
            for (int j = Math.max(0, i - 10); j <= i; j++) { // 表示[j,i]可以
                if (dp[j] != 0 && s.charAt(j) != '0') {
                    long p = Long.parseLong(s.substring(j, i + 1));
                    if (p <= k) {
                        dp[i + 1] += dp[j];
                    }
                }
            }
            dp[i + 1] %= Mod;
        }
        return (int) dp[n];
    }
}
```

2472\. 不重叠回文子字符串的最大数目
---------------------

给你一个字符串 `s` 和一个 **正** 整数 `k` 。

从字符串 `s` 中选出一组满足下述条件且 **不重叠** 的子字符串：

*   每个子字符串的长度 **至少** 为 `k` 。
*   每个子字符串是一个 **回文串** 。

返回最优方案中能选择的子字符串的 **最大** 数目。

**子字符串** 是字符串中一个连续的字符序列。

**示例 1 ：**

**输入：**s = "abaccdbbd", k = 3
**输出：**2
**解释：**可以选择 s = "_**aba**_cc_**dbbd**_" 中斜体加粗的子字符串。"aba" 和 "dbbd" 都是回文，且长度至少为 k = 3 。
可以证明，无法选出两个以上的有效子字符串。

**示例 2 ：**

**输入：**s = "adbcda", k = 2
**输出：**0
**解释：**字符串中不存在长度至少为 2 的回文子字符串。

**提示：**

*   `1 <= k <= s.length <= 2000`
*   `s` 仅由小写英文字母组成

[https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/description/](https://leetcode.cn/problems/maximum-number-of-non-overlapping-palindrome-substrings/description/)

```java
class Solution { // o(n^2) 132ms
    public int maxPalindromes(String s, int k) { 
        int n = s.length();
        boolean[][] f = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || f[i + 1][j - 1])) {
                    f[i][j] = true;
                }
            }
        }
        int[] dp = new int[n + 1];
        for (int i = k - 1; i < n; i++) {
            dp[i + 1] = dp[i]; // 不选择
            for (int j = i - k + 1; j >= 0; j--) { // 枚举[j,i]之间的回文串
                if (f[j][i]) {
                    dp[i + 1] = Math.max(dp[i + 1], dp[j] + 1);
                }
            }
        }
        return dp[n];
    }
}
```

```java
class Solution { // o(nk) 3ms
    public int maxPalindromes(String S, int k) {
        char[] s = S.toCharArray();
        int n = s.length;
        int[] f = new int[n + 1];
        for (int i = 0; i < 2 * n - 1; ++i) {
            int l = i / 2, r = l + i % 2; // 中心扩展法
            f[l + 1] = Math.max(f[l + 1], f[l]);
            for (; l >= 0 && r < n && s[l] == s[r]; --l, ++r) {
                if (r - l + 1 >= k) {
                    f[r + 1] = Math.max(f[r + 1], f[l] + 1);
                    break;
                }
            }
        }
        return f[n];
    }
}

```

1105\. 填充书架
-----------

给定一个数组 `books` ，其中 `books[i] = [thicknessi, heighti]` 表示第 `i` 本书的厚度和高度。你也会得到一个整数 `shelfWidth` 。

**按顺序** 将这些书摆放到总宽度为 `shelfWidth` 的书架上。

先选几本书放在书架上（它们的厚度之和小于等于书架的宽度 `shelfWidth` ），然后再建一层书架。重复这个过程，直到把所有的书都放在书架上。

需要注意的是，在上述过程的每个步骤中，**摆放书的顺序与给定图书数组** `books` **顺序相同**。

*   例如，如果这里有 5 本书，那么可能的一种摆放情况是：第一和第二本书放在第一层书架上，第三本书放在第二层书架上，第四和第五本书放在最后一层书架上。

每一层所摆放的书的最大高度就是这一层书架的层高，书架整体的高度为各层高之和。

以这种方式布置书架，返回书架整体可能的最小高度。

**示例 1：**

![](https://assets.leetcode.com/uploads/2019/06/24/shelves.png)

**输入：**books = \[\[1,1\],\[2,3\],\[2,3\],\[1,1\],\[1,1\],\[1,1\],\[1,2\]\], shelfWidth = 4
**输出：**6
**解释：**
3 层书架的高度和为 1 + 3 + 2 = 6 。
第 2 本书不必放在第一层书架上。

**示例 2:**

**输入:** books = \[\[1,3\],\[2,4\],\[3,2\]\], shelfWidth = 6
**输出:** 4

**提示：**

*   `1 <= books.length <= 1000`
*   `1 <= thicknessi <= shelfWidth <= 1000`
*   `1 <= heighti <= 1000`

[https://leetcode.cn/problems/filling-bookcase-shelves/description/](https://leetcode.cn/problems/filling-bookcase-shelves/description/)

```java
import java.util.Arrays;

class Solution {
    private int[][] books;
    private int shelfWidth;
    private int[] memo;
    public int minHeightShelves(int[][] books, int shelfWidth) {
        // 其实就是枚举当前的书放在当前层或则进入下一层
        this.books = books;
        this.shelfWidth = shelfWidth;
        int n = books.length;
        memo = new int[n];
        Arrays.fill(memo, -1);
        return dfs(n - 1);
    }

    private int dfs(int i) {
        if (i < 0) {
            return 0; // 没有书了，高度是0
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int res = Integer.MAX_VALUE, maxH = 0, leftW = shelfWidth;
        for (int j = i; j >= 0; j--) {
            leftW -= books[j][0];
            if (leftW < 0) {
                break;
            }
            maxH = Math.max(maxH, books[j][1]);
            res = Math.min(res, dfs(j - 1) + maxH);
        }
        return memo[i] = res;
    }
}
```

```java
class Solution {
    public int minHeightShelves(int[][] books, int shelfWidth) {
        int n = books.length;
        int[] dp = new int[n + 1]; // 表示放到第i本书需要的最小高度
        for (int i = 0; i < n; i++) {
            dp[i + 1] = Integer.MAX_VALUE;
            int maxH = 0, leftW = shelfWidth;
            for (int j = i; j >= 0; j--) {
                leftW -= books[j][0]; // 第j本书放在当前层
                if (leftW < 0) { 
                    break;
                }
                // 表示[j,i]的书可以放在同一层，枚举从哪一本书单开一层使最后结果最优
                maxH = Math.max(maxH, books[j][1]); // 更新当前层的最大高度
                dp[i + 1] = Math.min(dp[i + 1], dp[j] + maxH); // 表示将第j本书放在
            }
        }
        return dp[n];
    }
}
```

2547\. 拆分数组的最小代价
----------------

给你一个整数数组 `nums` 和一个整数 `k` 。

将数组拆分成一些非空子数组。拆分的 **代价** 是每个子数组中的 **重要性** 之和。

令 `trimmed(subarray)` 作为子数组的一个特征，其中所有仅出现一次的数字将会被移除。

*   例如，`trimmed([3,1,2,4,3,4]) = [3,4,3,4]` 。

子数组的 **重要性** 定义为 `k + trimmed(subarray).length` 。

*   例如，如果一个子数组是 `[1,2,3,3,3,4,4]` ，`trimmed([1,2,3,3,3,4,4]) = [3,3,3,4,4]` 。这个子数组的重要性就是 `k + 5` 。

找出并返回拆分 `nums` 的所有可行方案中的最小代价。

**子数组** 是数组的一个连续 **非空** 元素序列。

**示例 1：**

**输入：**nums = \[1,2,1,2,1,3,3\], k = 2
**输出：**8
**解释：**将 nums 拆分成两个子数组：\[1,2\], \[1,2,1,3,3\]
\[1,2\] 的重要性是 2 + (0) = 2 。
\[1,2,1,3,3\] 的重要性是 2 + (2 + 2) = 6 。
拆分的代价是 2 + 6 = 8 ，可以证明这是所有可行的拆分方案中的最小代价。

**示例 2：**

**输入：**nums = \[1,2,1,2,1\], k = 2
**输出：**6
**解释：**将 nums 拆分成两个子数组：\[1,2\], \[1,2,1\] 。
\[1,2\] 的重要性是 2 + (0) = 2 。
\[1,2,1\] 的重要性是 2 + (2) = 4 。
拆分的代价是 2 + 4 = 6 ，可以证明这是所有可行的拆分方案中的最小代价。

**示例 3：**

**输入：**nums = \[1,2,1,2,1\], k = 5
**输出：**10
**解释：**将 nums 拆分成一个子数组：\[1,2,1,2,1\].
\[1,2,1,2,1\] 的重要性是 5 + (3 + 2) = 10 。
拆分的代价是 10 ，可以证明这是所有可行的拆分方案中的最小代价。

**提示：**

*   `1 <= nums.length <= 1000`
*   `0 <= nums[i] < nums.length`
*   `1 <= k <= 109`

[https://leetcode.cn/problems/minimum-cost-to-split-an-array/description/](https://leetcode.cn/problems/minimum-cost-to-split-an-array/description/)

```java
import java.util.Arrays;
import java.util.HashMap;

class Solution { // o(n^2) 61ms
    public int minCost(int[] nums, int k) {
        int n = nums.length;
        int[][] costs = new int[n][n]; // 表示[i,j]的cost
        for (int i = 0; i < n; i++) {
            int[] freq = new int[n];
            int cnt = 0;
            for (int j = i; j < n; j++) {
                freq[nums[j]]++;
                if (freq[nums[j]] == 2) {
                    cnt += 2;
                } else if (freq[nums[j]] > 2) {
                    cnt++;
                }
                costs[i][j] = cnt;
            }
        }
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i + 1] = Integer.MAX_VALUE;
            for (int j = i; j >= 0; j--) { // 子状态推现在状态
                dp[i + 1] = Math.min(dp[i + 1], dp[j] + costs[j][i] + k);
            }
        }
        return dp[n];
    }
}
```

```java
// 线段树优化 因为有区间查询
```





2430\. 对字母串可执行的最大删除数
--------------------

给你一个仅由小写英文字母组成的字符串 `s` 。在一步操作中，你可以：

*   删除 **整个字符串** `s` ，或者
*   对于满足 `1 <= i <= s.length / 2` 的任意 `i` ，如果 `s` 中的 **前** `i` 个字母和接下来的 `i` 个字母 **相等** ，删除 **前** `i` 个字母。

例如，如果 `s = "ababc"` ，那么在一步操作中，你可以删除 `s` 的前两个字母得到 `"abc"` ，因为 `s` 的前两个字母和接下来的两个字母都等于 `"ab"` 。

返回删除 `s` 所需的最大操作数。

**示例 1：**

**输入：**s = "abcabcdabc"
**输出：**2
**解释：**
- 删除前 3 个字母（"abc"），因为它们和接下来 3 个字母相等。现在，s = "abcdabc"。
- 删除全部字母。
  一共用了 2 步操作，所以返回 2 。可以证明 2 是所需的最大操作数。
  注意，在第二步操作中无法再次删除 "abc" ，因为 "abc" 的下一次出现并不是位于接下来的 3 个字母。

**示例 2：**

**输入：**s = "aaabaab"
**输出：**4
**解释：**
- 删除第一个字母（"a"），因为它和接下来的字母相等。现在，s = "aabaab"。
- 删除前 3 个字母（"aab"），因为它们和接下来 3 个字母相等。现在，s = "aab"。 
- 删除第一个字母（"a"），因为它和接下来的字母相等。现在，s = "ab"。
- 删除全部字母。
  一共用了 4 步操作，所以返回 4 。可以证明 4 是所需的最大操作数。

**示例 3：**

**输入：**s = "aaaaa"
**输出：**5
**解释：**在每一步操作中，都可以仅删除 s 的第一个字母。

**提示：**

*   `1 <= s.length <= 4000`
*   `s` 仅由小写英文字母组成

[https://leetcode.cn/problems/maximum-deletions-on-a-string/](https://leetcode.cn/problems/maximum-deletions-on-a-string/)

```java
class Solution {
    public int deleteString(String S) {
        // 难点1:每次要反复遍历字串，使用kmp中的Next数组应该可以优化成o(n)
        // 难点2:字符串是一直在改变,所以n/2一直在改变
        char[] s = S.toCharArray();
        int n = s.length;
        if (allEqual(s)) { // 特判全部相同的情况
            return n;
        }
        int[][] f = new int[n + 1][n + 1]; // 预处理解决难点1：表示 s[i:] 和 s[j:] 的最长公共前缀
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (s[i] == s[j]) {
                    f[i][j] = f[i + 1][j + 1] + 1;
                }
            }
        }
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 1; i + j * 2 <= n; j++) {
                if (f[i][i + j] >= j) {
                    dp[i] = Math.max(dp[i], dp[i + j]);
                }
            }
            dp[i]++;
        }
        return dp[0];
    }
    
    private boolean allEqual(char[] s) {
        for (int i = 1; i < s.length; i++) {
            if (s[i] != s[0]) {
                return false;
            }
        }
        return true;
    }
}
```

2463\. 最小移动总距离
--------------

X 轴上有一些机器人和工厂。给你一个整数数组 `robot` ，其中 `robot[i]` 是第 `i` 个机器人的位置。再给你一个二维整数数组 `factory` ，其中 `factory[j] = [positionj, limitj]` ，表示第 `j` 个工厂的位置在 `positionj` ，且第 `j` 个工厂最多可以修理 `limitj` 个机器人。

每个机器人所在的位置 **互不相同** 。每个工厂所在的位置也 **互不相同** 。注意一个机器人可能一开始跟一个工厂在 **相同的位置** 。

所有机器人一开始都是坏的，他们会沿着设定的方向一直移动。设定的方向要么是 X 轴的正方向，要么是 X 轴的负方向。当一个机器人经过一个没达到上限的工厂时，这个工厂会维修这个机器人，且机器人停止移动。

**任何时刻**，你都可以设置 **部分** 机器人的移动方向。你的目标是最小化所有机器人总的移动距离。

请你返回所有机器人移动的最小总距离。测试数据保证所有机器人都可以被维修。

**注意：**

*   所有机器人移动速度相同。
*   如果两个机器人移动方向相同，它们永远不会碰撞。
*   如果两个机器人迎面相遇，它们也不会碰撞，它们彼此之间会擦肩而过。
*   如果一个机器人经过了一个已经达到上限的工厂，机器人会当作工厂不存在，继续移动。
*   机器人从位置 `x` 到位置 `y` 的移动距离为 `|y - x|` 。

**示例 1：**

![](https://pic.leetcode-cn.com/1667542978-utuiPv-image.png)

**输入：**robot = \[0,4,6\], factory = \[\[2,2\],\[6,2\]\]
**输出：**4
**解释：**如上图所示：
- 第一个机器人从位置 0 沿着正方向移动，在第一个工厂处维修。
- 第二个机器人从位置 4 沿着负方向移动，在第一个工厂处维修。
- 第三个机器人在位置 6 被第二个工厂维修，它不需要移动。
  第一个工厂的维修上限是 2 ，它维修了 2 个机器人。
  第二个工厂的维修上限是 2 ，它维修了 1 个机器人。
  总移动距离是 |2 - 0| + |2 - 4| + |6 - 6| = 4 。没有办法得到比 4 更少的总移动距离。

**示例 2：**

![](https://pic.leetcode-cn.com/1667542984-OAIRFN-image.png)

**输入：**robot = \[1,-1\], factory = \[\[-2,1\],\[2,1\]\]
**输出：**2
**解释：**如上图所示：
- 第一个机器人从位置 1 沿着正方向移动，在第二个工厂处维修。
- 第二个机器人在位置 -1 沿着负方向移动，在第一个工厂处维修。
  第一个工厂的维修上限是 1 ，它维修了 1 个机器人。
  第二个工厂的维修上限是 1 ，它维修了 1 个机器人。
  总移动距离是 |2 - 1| + |(-2) - (-1)| = 2 。没有办法得到比 2 更少的总移动距离。

**提示：**

*   `1 <= robot.length, factory.length <= 100`
*   `factory[j].length == 2`
*   `-109 <= robot[i], positionj <= 109`
*   `0 <= limitj <= robot.length`
*   测试数据保证所有机器人都可以被维修。

[https://leetcode.cn/problems/minimum-total-distance-traveled/description/](https://leetcode.cn/problems/minimum-total-distance-traveled/description/)

```java
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Solution {
    // f[i][j]表示第i~n-1个工厂处理第j~m-1个机器人的代价总和
    long[][] f;
    long inf = (long)1e13;

    public long minimumTotalDistance(List<Integer> robot, int[][] factory) {
        int m = robot.size(), n = factory.length;
        Arrays.sort(factory, (o, p) -> o[0] - p[0]);
        Collections.sort(robot);
        f = new long[n][m];
        for(int i = 0;i < n;i++)
            Arrays.fill(f[i], inf);
        return dfs(0, 0, robot, factory);
    }

    public long dfs(int i, int j, List<Integer> robot, int[][] factory) {
        int m = robot.size(), n = factory.length;
        // 如果到第i个工厂时，所有的机器人都被处理完毕
        if(j == m) {
            return 0;
        }
        // 处理最后一个工厂
        if(i == n - 1) {
            // 当前剩余未处理的机器人个数大于最后一个工厂的limit
            // 表示无法以题目的要求处理完成，返回一个无穷大
            if(m - j > factory[i][1]) {
                return inf;
            }
            // 否则，计算剩下的机器人到最后一个工厂的距离之和
            long ans = 0;
            for(int k = j;k < m;k++)
                ans += Math.abs(robot.get(k) - factory[i][0]);
            f[i][j] = ans;
            return ans;
        }
        // 如果当前状态已经计算过就直接返回
        if(f[i][j] != inf)
            return f[i][j];
        // 当前工厂不处理任何机器人的情况
        long ans = dfs(i + 1, j, robot, factory);
        // 记录当前工厂处理的机器人与工厂的距离总和
        long dis = 0;		
        for(int k = j;k < Math.min(m, j + factory[i][1]);k++) {
            dis += Math.abs(robot.get(k) - factory[i][0]);
            long c = dfs(i + 1, k + 1, robot, factory);
            ans = Math.min(ans, c + dis);
        }
        f[i][j] = ans;
        return ans;
    }
}
```

# §6.3 约束划分个数(全是模板，三层for循环)

![1720677431918](assets/1720677431918.png)

410\. 分割数组的最大值
--------------

给定一个非负整数数组 `nums` 和一个整数 `k` ，你需要将这个数组分成 `k` 个非空的连续子数组。

设计一个算法使得这 `k` 个子数组各自和的最大值最小。

**示例 1：**

**输入：**nums = \[7,2,5,10,8\], k = 2
**输出：**18
**解释：**
一共有四种方法将 nums 分割为 2 个子数组。 
其中最好的方式是将其分为 \[7,2,5\] 和 \[10,8\] 。
因为此时这两个子数组各自的和的最大值为18，在所有情况中最小。

**示例 2：**

**输入：**nums = \[1,2,3,4,5\], k = 2
**输出：**9

**示例 3：**

**输入：**nums = \[1,4,4\], k = 3
**输出：**4

**提示：**

*   `1 <= nums.length <= 1000`
*   `0 <= nums[i] <= 106`
*   `1 <= k <= min(50, nums.length)`

[https://leetcode.cn/problems/split-array-largest-sum/description/](https://leetcode.cn/problems/split-array-largest-sum/description/)

```java
import java.util.Arrays;

class Solution {
    public int splitArray(int[] nums, int k) {
        // 定义dp[][] 表示前i个数被划分为j个数组的最大值
        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                for (int x = 0; x < i; x++) {
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[x][j - 1], prefixSum[i] - prefixSum[x]));
                }
            }
        }
        return dp[n][k];
    }
}
```

```java
// 最快的做法 二分+贪心
class Solution {
    // 贪心+二分
    public int splitArray(int[] nums, int k) {
        int left = 0, right = 0; // left是nums中最大元素，right = Math.sum(nums), 代表二分的上下界
        for (int num : nums) {
            if (left < num) {
                left = num;
            }
            right += num;
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(nums, mid, k)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right + 1;
    }

    private boolean check(int[] nums, int limit, int k) {
        int sum = 0;
        int cnt = 1; // 划分的子数组数量
        for (int num : nums) {
            if (sum + num > limit) {
                sum = num;
                cnt++;
            }else{
                sum += num;
            }
        }
        return cnt <= k; // 贪心思想
    }
}
```

1043\. 分隔数组以得到最大和（求数组中i到j的最大值util）
-----------------

给你一个整数数组 `arr`，请你将该数组分隔为长度 **最多** 为 k 的一些（连续）子数组。分隔完成后，每个子数组的中的所有值都会变为该子数组中的最大值。

返回将数组分隔变换后能够得到的元素最大和。本题所用到的测试用例会确保答案是一个 32 位整数。

**示例 1：**

**输入：**arr = \[1,15,7,9,2,5,10\], k = 3
**输出：**84
**解释：**数组变为 \[15,15,15,9,10,10,10\]

**示例 2：**

**输入：**arr = \[1,4,1,5,7,3,6,1,9,9,3\], k = 4
**输出：**83

**示例 3：**

**输入：**arr = \[1\], k = 1
**输出：**1

**提示：**

*   `1 <= arr.length <= 500`
*   `0 <= arr[i] <= 109`
*   `1 <= k <= arr.length`

[https://leetcode.cn/problems/partition-array-for-maximum-sum/description/](https://leetcode.cn/problems/partition-array-for-maximum-sum/description/)

```java
import java.util.Arrays;

class Solution {
    public int maxSumAfterPartitioning(int[] arr, int k) {
        int n = arr.length;
        // f[][]表示i到j的最大值 dp[i][j] = Math.max(dp[i][j - 1],nums[j])
        int[][] f = new int[n][n];
        for (int i = 0; i < n; i++) {
            f[i][i] = arr[i];
            for (int j = i + 1; j < n; j++) {
                f[i][j] = Math.max(f[i][j - 1], arr[j]);
            }
        }
        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = i - 1; j >= 0 && i - j <= k; j--) {
                dp[i] = Math.max(dp[i], dp[j] + (i - j) * f[j][i - 1]);
            }
        }
        return dp[n];
    }
}
```

```java
class Solution {
    public int maxSumAfterPartitioning(int[] nums, int k) { // 优化时空复杂度
        int n = nums.length;
        int[] dp = new int[n + 1];
        int mx = 0;
        for (int i = 1; i <= n; i++) {
            mx = nums[i - 1];
            for (int j = i - 1; j >= 0 && i - j <= k; j--) {
                dp[i] = Math.max(dp[i], dp[j] + (i - j) * mx);
                if (j > 0) {
                    mx = Math.max(mx, nums[j - 1]);
                }
            }
        }
        return dp[n];
    }
}
```

1745\. 分割回文串 IV
---------------

给你一个字符串 `s` ，如果可以将它分割成三个 **非空** 回文子字符串，那么返回 `true` ，否则返回 `false` 。

当一个字符串正着读和反着读是一模一样的，就称其为 **回文字符串** 。

**示例 1：**

**输入：**s = "abcbdd"
**输出：**true
**解释：**"abcbdd" = "a" + "bcb" + "dd"，三个子字符串都是回文的。

**示例 2：**

**输入：**s = "bcbddxy"
**输出：**false
**解释：**s 没办法被分割成 3 个回文子字符串。

**提示：**

*   `3 <= s.length <= 2000`
*   `s`​​​​​​ 只包含小写英文字母。

[https://leetcode.cn/problems/palindrome-partitioning-iv/description/](https://leetcode.cn/problems/palindrome-partitioning-iv/description/)

```java
class Solution {
    public boolean checkPartitioning(String s) {
        int n = s.length();
        boolean[][] f = new boolean[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j) && (j - i <= 1 || f[i + 1][j - 1])) {
                    f[i][j] = true;
                }
            }
        }
        // 枚举分割点
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (f[0][i] && f[i + 1][j] && f[j + 1][n - 1]) {
                    return true;
                }
            }
        }
        return false;
    }
}
```

813\. 最大平均值和的分组
---------------

给定数组 `nums` 和一个整数 `k` 。我们将给定的数组 `nums` 分成 **最多** `k` 个非空子数组，且数组内部是连续的 。 **分数** 由每个子数组内的平均值的总和构成。

注意我们必须使用 `nums` 数组中的每一个数进行分组，并且分数不一定需要是整数。

返回我们所能得到的最大 **分数** 是多少。答案误差在 `10-6` 内被视为是正确的。

**示例 1:**

**输入:** nums = \[9,1,2,3,9\], k = 3
**输出:** 20.00000
**解释:** 
nums 的最优分组是\[9\], \[1, 2, 3\], \[9\]. 得到的分数是 9 + (1 + 2 + 3) / 3 + 9 = 20. 
我们也可以把 nums 分成\[9, 1\], \[2\], \[3, 9\]. 
这样的分组得到的分数为 5 + 2 + 6 = 13, 但不是最大值.

**示例 2:**

**输入:** nums = \[1,2,3,4,5,6,7\], k = 4
**输出:** 20.50000

**提示:**

*   `1 <= nums.length <= 100`
*   `1 <= nums[i] <= 104`
*   `1 <= k <= nums.length`

[https://leetcode.cn/problems/largest-sum-of-averages/description/](https://leetcode.cn/problems/largest-sum-of-averages/description/)

```java
class Solution {
    public double largestSumOfAverages(int[] nums, int k) {
        // 这个题有点意思
        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        // 表示前i个数分为k个子数组的最大值
        double[][] dp = new double[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                if(j == 1){
                    dp[i][j] = (double) prefixSum[i] / i;
                }else{
                    for (int x = 1; x < i; x++) { // 这里的x表示将[x,i)单独成一个组的值
                        dp[i][j] = Math.max(dp[i][j], dp[x][j - 1] + (double) (prefixSum[i] - prefixSum[x]) / (i - x));
                    }
                }
            }
        }
        return dp[n][k];
    }
}
```

1278\. 分割回文串 III
----------------

给你一个由小写字母组成的字符串 `s`，和一个整数 `k`。

请你按下面的要求分割字符串：

*   首先，你可以将 `s` 中的部分字符修改为其他的小写英文字母。
*   接着，你需要把 `s` 分割成 `k` 个非空且不相交的子串，并且每个子串都是回文串。

请返回以这种方式分割字符串所需修改的最少字符数。

**示例 1：**

**输入：**s = "abc", k = 2
**输出：**1
**解释：**你可以把字符串分割成 "ab" 和 "c"，并修改 "ab" 中的 1 个字符，将它变成回文串。

**示例 2：**

**输入：**s = "aabbc", k = 3
**输出：**0
**解释：**你可以把字符串分割成 "aa"、"bb" 和 "c"，它们都是回文串。

**示例 3：**

**输入：**s = "leetcode", k = 8
**输出：**0

**提示：**

*   `1 <= k <= s.length <= 100`
*   `s` 中只含有小写英文字母。

[https://leetcode.cn/problems/palindrome-partitioning-iii/description/](https://leetcode.cn/problems/palindrome-partitioning-iii/description/)

```java
import java.util.Arrays;

class Solution {
    public int palindromePartition(String s, int k) {
        int n = s.length();
        int[][] f = new int[n][n]; // [i,j]变成回文串需要的操作次数
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (j - i == 0) {
                    f[i][j] = 0;
                } else if (j - i == 1) {
                    f[i][j] = s.charAt(i) == s.charAt(j) ? 0 : 1;
                } else{
                    f[i][j] = s.charAt(i) == s.charAt(j) ? f[i + 1][j - 1] : f[i + 1][j - 1] + 1;
                }
            }
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                for (int x = 0; x < i; x++) { // 分割为[0,x - 1],[x,i - 1]
                    dp[i][j] = Math.min(dp[i][j], dp[x][j - 1] + f[x][i - 1]);
                }
            }
        }
        return dp[n][k];
    }
}
```

1335\. 工作计划的最低难度
----------------

你需要制定一份 `d` 天的工作计划表。工作之间存在依赖，要想执行第 `i` 项工作，你必须完成全部 `j` 项工作（ `0 <= j < i`）。

你每天 **至少** 需要完成一项任务。工作计划的总难度是这 `d` 天每一天的难度之和，而一天的工作难度是当天应该完成工作的最大难度。

给你一个整数数组 `jobDifficulty` 和一个整数 `d`，分别代表工作难度和需要计划的天数。第 `i` 项工作的难度是 `jobDifficulty[i]`。

返回整个工作计划的 **最小难度** 。如果无法制定工作计划，则返回 **\-1** 。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/01/26/untitled.png)

**输入：**jobDifficulty = \[6,5,4,3,2,1\], d = 2
**输出：**7
**解释：**第一天，您可以完成前 5 项工作，总难度 = 6.
第二天，您可以完成最后一项工作，总难度 = 1.
计划表的难度 = 6 + 1 = 7 

**示例 2：**

**输入：**jobDifficulty = \[9,9,9\], d = 4
**输出：**\-1
**解释：**就算你每天完成一项工作，仍然有一天是空闲的，你无法制定一份能够满足既定工作时间的计划表。

**示例 3：**

**输入：**jobDifficulty = \[1,1,1\], d = 3
**输出：**3
**解释：**工作计划为每天一项工作，总难度为 3 。

**示例 4：**

**输入：**jobDifficulty = \[7,1,7,1,7,1\], d = 3
**输出：**15

**示例 5：**

**输入：**jobDifficulty = \[11,111,22,222,33,333,44,444\], d = 6
**输出：**843

**提示：**

*   `1 <= jobDifficulty.length <= 300`
*   `0 <= jobDifficulty[i] <= 1000`
*   `1 <= d <= 10`

[https://leetcode.cn/problems/minimum-difficulty-of-a-job-schedule/description/](https://leetcode.cn/problems/minimum-difficulty-of-a-job-schedule/description/)

```java
import java.util.Arrays;

class Solution {
    public int minDifficulty(int[] nums, int k) {
        int n = nums.length;
        int[][] f = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                // f[i][j] = max(f[i][j - 1], nums[j])
                if (i == j) {
                    f[i][j] = nums[j];
                }else{
                    f[i][j] = Math.max(f[i][j - 1], nums[j]);
                }
            }
        }
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i < n + 1; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                for (int x = 0; x < i; x++) {
                    dp[i][j] = Math.min(dp[i][j], dp[x][j - 1] + f[x][i - 1]);
                }
            }
        }
        return dp[n][k] == Integer.MAX_VALUE / 2 ? -1 : dp[n][k];
    }
}
```

1473\. 粉刷房子 III
---------------

在一个小城市里，有 `m` 个房子排成一排，你需要给每个房子涂上 `n` 种颜色之一（颜色编号为 `1` 到 `n` ）。有的房子去年夏天已经涂过颜色了，所以这些房子不可以被重新涂色。

我们将连续相同颜色尽可能多的房子称为一个街区。（比方说 `houses = [1,2,2,3,3,2,1,1]` ，它包含 5 个街区  `[{1}, {2,2}, {3,3}, {2}, {1,1}]` 。）

给你一个数组 `houses` ，一个 `m * n` 的矩阵 `cost` 和一个整数 `target` ，其中：

*   `houses[i]`：是第 `i` 个房子的颜色，**0** 表示这个房子还没有被涂色。
*   `cost[i][j]`：是将第 `i` 个房子涂成颜色 `j+1` 的花费。

请你返回房子涂色方案的最小总花费，使得每个房子都被涂色后，恰好组成 `target` 个街区。如果没有可用的涂色方案，请返回 **\-1** 。

**示例 1：**

**输入：**houses = \[0,0,0,0,0\], cost = \[\[1,10\],\[10,1\],\[10,1\],\[1,10\],\[5,1\]\], m = 5, n = 2, target = 3
**输出：**9
**解释：**房子涂色方案为 \[1,2,2,1,1\]
此方案包含 target = 3 个街区，分别是 \[{1}, {2,2}, {1,1}\]。
涂色的总花费为 (1 + 1 + 1 + 1 + 5) = 9。

**示例 2：**

**输入：**houses = \[0,2,1,2,0\], cost = \[\[1,10\],\[10,1\],\[10,1\],\[1,10\],\[5,1\]\], m = 5, n = 2, target = 3
**输出：**11
**解释：**有的房子已经被涂色了，在此基础上涂色方案为 \[2,2,1,2,2\]
此方案包含 target = 3 个街区，分别是 \[{2,2}, {1}, {2,2}\]。
给第一个和最后一个房子涂色的花费为 (10 + 1) = 11。

**示例 3：**

**输入：**houses = \[0,0,0,0,0\], cost = \[\[1,10\],\[10,1\],\[1,10\],\[10,1\],\[1,10\]\], m = 5, n = 2, target = 5
**输出：**5

**示例 4：**

**输入：**houses = \[3,1,2,3\], cost = \[\[1,1,1\],\[1,1,1\],\[1,1,1\],\[1,1,1\]\], m = 4, n = 3, target = 3
**输出：**\-1
**解释：**房子已经被涂色并组成了 4 个街区，分别是 \[{3},{1},{2},{3}\] ，无法形成 target = 3 个街区。

**提示：**

*   `m == houses.length == cost.length`
*   `n == cost[i].length`
*   `1 <= m <= 100`
*   `1 <= n <= 20`
*   `1 <= target <= m`
*   `0 <= houses[i] <= n`
*   `1 <= cost[i][j] <= 10^4`

[https://leetcode.cn/problems/paint-house-iii/description/](https://leetcode.cn/problems/paint-house-iii/description/)

```java
import java.util.Arrays;

class Solution {
    private static final int INF = 0x3f3f3f3f;
    public int minCost(int[] houses, int[][] cost, int m, int n, int target) {
        // dp[i][j][k] 表示将[0, i]区间的房屋分成j个街道, 并且最后一个房屋刷的颜色是第k种颜色, 此时的最小总花费
        int[][][] dp = new int[m + 1][target + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= target; j++) {
                Arrays.fill(dp[i][j], INF);
            }
        }
        Arrays.fill(dp[0][0], 0);
        // 初始化第0个房子
        // 第0个房子已经涂色
        if(houses[0] > 0){
            dp[1][1][houses[0]] = 0;
        }else{
            // 第0个房子没有涂色，初始化cost
            for(int i = 1; i <= n; i++){
                dp[1][1][i] = cost[0][i - 1];
            }
        }
        for (int i = 1; i <= m; i++) { // 前i个房子
            for (int j = 1; j <= target; j++) { // 分成j个街道
                if (houses[i - 1] == 0) { // 没有被染色
                    for (int k = 1; k <= n; k++) { // 枚举上一个房子染什么颜色[1,n]
                        for (int x = 1; x <= n; x++) { // 枚举当前房子染什么颜色
                            if (k == x) { // 颜色相同
                                dp[i][j][x] = Math.min(dp[i][j][x], dp[i - 1][j][k] + cost[i - 1][x - 1]);
                            }else{
                                dp[i][j][x] = Math.min(dp[i][j][x], dp[i - 1][j - 1][k] + cost[i - 1][x - 1]);
                            }
                        }
                    }
                }else{ // 当前房子已经被染色
                    for (int k = 1; k <= n; k++) { // 枚举上一个房子染什么颜色
                        int curColor = houses[i - 1];
                        if (k == curColor) {
                            dp[i][j][curColor] = Math.min(dp[i][j][curColor], dp[i - 1][j][k]);
                        }else{
                            dp[i][j][curColor] = Math.min(dp[i][j][curColor], dp[i - 1][j - 1][k]);
                        }
                    }
                }
            }
        }
        // System.out.println(Arrays.deepToString(dp));
        int ans = INF;
        for (int i = 1; i <= n; i++) {
            ans = Math.min(ans, dp[m][target][i]);
        }
        return ans == INF ? -1 : ans;
    }
}
```

1478. 安排邮筒 划分型DP模板题（中位数贪心，前缀和计算） 
-----------

给你一个房屋数组`houses` 和一个整数 `k` ，其中 `houses[i]` 是第 `i` 栋房子在一条街上的位置，现需要在这条街上安排 `k` 个邮筒。

请你返回每栋房子与离它最近的邮筒之间的距离的 **最小** 总和。

答案保证在 32 位有符号整数范围以内。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/13/sample_11_1816.png)

**输入：**houses = \[1,4,8,10,20\], k = 3
**输出：**5
**解释：**将邮筒分别安放在位置 3， 9 和 20 处。
每个房子到最近邮筒的距离和为 |3-1| + |4-3| + |9-8| + |10-9| + |20-20| = 5 。

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/13/sample_2_1816.png)**

**输入：**houses = \[2,3,5,12,18\], k = 2
**输出：**9
**解释：**将邮筒分别安放在位置 3 和 14 处。
每个房子到最近邮筒距离和为 |2-3| + |3-3| + |5-3| + |12-14| + |18-14| = 9 。

**示例 3：**

**输入：**houses = \[7,4,6,1\], k = 1
**输出：**8

**示例 4：**

**输入：**houses = \[3,6,14,10\], k = 4
**输出：**0

**提示：**

*   `n == houses.length`
*   `1 <= n <= 100`
*   `1 <= houses[i] <= 10^4`
*   `1 <= k <= n`
*   数组 `houses` 中的整数互不相同。

[https://leetcode.cn/problems/allocate-mailboxes/description/](https://leetcode.cn/problems/allocate-mailboxes/description/)

```java
import java.util.Arrays;

class Solution {
    public int minDistance(int[] houses, int k) {
        int n = houses.length;
        Arrays.sort(houses);
        int[][] prefixSum = new int[n][n];// prefixSum[i][j]，表示在下标范围[i,j]的房子中间放一个邮筒，距离之和最小值。中位数贪心
        for (int i = n - 2; i >= 0; i--) { // 这里真的细节，后面要用i+1,所以这里直接i = n - 2，后面J的处理同理；
            for (int j = i + 1; j < n; j++) {
                prefixSum[i][j] = prefixSum[i + 1][j - 1] + houses[j] - houses[i];
            }
        }
        int[][] dp = new int[n + 1][k + 1]; // 表示前i个房子，使用j个桶的最小距离和
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) { // 枚举可用邮筒的数量
                for (int x = 0; x < i; x++) {
                    dp[i][j] = Math.min(dp[i][j], dp[x][j - 1] + prefixSum[x][i - 1]);
                }
            }
        }
        return dp[n][k];
    }
}
```

1478\. 安排邮筒
-----------

给你一个房屋数组`houses` 和一个整数 `k` ，其中 `houses[i]` 是第 `i` 栋房子在一条街上的位置，现需要在这条街上安排 `k` 个邮筒。

请你返回每栋房子与离它最近的邮筒之间的距离的 **最小** 总和。

答案保证在 32 位有符号整数范围以内。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/13/sample_11_1816.png)

**输入：**houses = \[1,4,8,10,20\], k = 3
**输出：**5
**解释：**将邮筒分别安放在位置 3， 9 和 20 处。
每个房子到最近邮筒的距离和为 |3-1| + |4-3| + |9-8| + |10-9| + |20-20| = 5 。

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/13/sample_2_1816.png)**

**输入：**houses = \[2,3,5,12,18\], k = 2
**输出：**9
**解释：**将邮筒分别安放在位置 3 和 14 处。
每个房子到最近邮筒距离和为 |2-3| + |3-3| + |5-3| + |12-14| + |18-14| = 9 。

**示例 3：**

**输入：**houses = \[7,4,6,1\], k = 1
**输出：**8

**示例 4：**

**输入：**houses = \[3,6,14,10\], k = 4
**输出：**0

**提示：**

*   `n == houses.length`
*   `1 <= n <= 100`
*   `1 <= houses[i] <= 10^4`
*   `1 <= k <= n`
*   数组 `houses` 中的整数互不相同。

[https://leetcode.cn/problems/allocate-mailboxes/description/](https://leetcode.cn/problems/allocate-mailboxes/description/)

```java
import java.util.Arrays;

class Solution {
    public int minDistance(int[] houses, int k) {
        int n = houses.length;
        Arrays.sort(houses);
        int[][] prefixSum = new int[n][n];
        for (int i = n - 2; i >= 0; i--) { // prefixSum[i][j]，表示在下标范围[i,j]的房子中间放一个邮筒，距离之和最小值。中位数贪心
            for (int j = i + 1; j < n; j++) {
                prefixSum[i][j] = prefixSum[i + 1][j - 1] + houses[j] - houses[i];
            }
        }
        int[][] dp = new int[n + 1][k + 1]; // 表示前i个房子，使用j个桶的最小距离和
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                for (int x = 0; x < i; x++) {
                    dp[i][j] = Math.min(dp[i][j], dp[x][j - 1] + prefixSum[x][i - 1]);
                }
            }
        }
        return dp[n][k];
    }
}
```

1959\. K 次调整数组大小浪费的最小总空间
------------------------

你正在设计一个动态数组。给你一个下标从 **0** 开始的整数数组 `nums` ，其中 `nums[i]` 是 `i` 时刻数组中的元素数目。除此以外，你还有一个整数 `k` ，表示你可以 **调整** 数组大小的 **最多** 次数（每次都可以调整成 **任意** 大小）。

`t` 时刻数组的大小 `sizet` 必须大于等于 `nums[t]` ，因为数组需要有足够的空间容纳所有元素。`t` 时刻 **浪费的空间** 为 `sizet - nums[t]` ，**总** 浪费空间为满足 `0 <= t < nums.length` 的每一个时刻 `t` 浪费的空间 **之和** 。

在调整数组大小不超过 `k` 次的前提下，请你返回 **最小总浪费空间** 。

**注意：**数组最开始时可以为 **任意大小** ，且 **不计入** 调整大小的操作次数。

**示例 1：**

**输入：**nums = \[10,20\], k = 0
**输出：**10
**解释：**size = \[20,20\].
我们可以让数组初始大小为 20 。
总浪费空间为 (20 - 10) + (20 - 20) = 10 。

**示例 2：**

**输入：**nums = \[10,20,30\], k = 1
**输出：**10
**解释：**size = \[20,20,30\].
我们可以让数组初始大小为 20 ，然后时刻 2 调整大小为 30 。
总浪费空间为 (20 - 10) + (20 - 20) + (30 - 30) = 10 。

**示例 3：**

**输入：**nums = \[10,20,15,30,20\], k = 2
**输出：**15
**解释：**size = \[10,20,20,30,30\].
我们可以让数组初始大小为 10 ，时刻 1 调整大小为 20 ，时刻 3 调整大小为 30 。
总浪费空间为 (10 - 10) + (20 - 20) + (20 - 15) + (30 - 30) + (30 - 20) = 15 。

**提示：**

*   `1 <= nums.length <= 200`
*   `1 <= nums[i] <= 106`
*   `0 <= k <= nums.length - 1`

[https://leetcode.cn/problems/minimum-total-space-wasted-with-k-resizing-operations/description/](https://leetcode.cn/problems/minimum-total-space-wasted-with-k-resizing-operations/description/)

```java
import java.util.Arrays;

class Solution {
    /**
     * 难点：
     * 题意转换：给定数组 nums 以及整数 k，需要把数组完整地分成 k+1 段连续的子数组，每一段的权值是「这一段的最大值乘以这一段的长度再减去这一段的元素和」。需要最小化总权值。
     */
    public int minSpaceWastedKResizing(int[] nums, int k) {
        int n = nums.length;
        int[][] f = new int[n][n]; // 求f数组也是难点
        for (int i = 0; i < n; i++) {
            int mx = Integer.MIN_VALUE;
            int sum = 0;
            for (int j = i; j < n; j++) {
                mx = Math.max(mx, nums[j]);
                sum += nums[j];
                f[i][j] = mx * (j - i + 1) - sum;
            }
        }
        k += 1; // 划分成k+1个子数组
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, i); j++) {
                for (int x = 0; x < i; x++) {
                    dp[i][j] = Math.min(dp[i][j], dp[x][j - 1] + f[x][i - 1]);
                }
            }
        }
        return dp[n][k];
    }
}
```

2478\. 完美分割的方案数
---------------

给你一个字符串 `s` ，每个字符是数字 `'1'` 到 `'9'` ，再给你两个整数 `k` 和 `minLength` 。

如果对 `s` 的分割满足以下条件，那么我们认为它是一个 **完美** 分割：

*   `s` 被分成 `k` 段互不相交的子字符串。
*   每个子字符串长度都 **至少** 为 `minLength` 。
*   每个子字符串的第一个字符都是一个 **质数** 数字，最后一个字符都是一个 **非质数** 数字。质数数字为 `'2'` ，`'3'` ，`'5'` 和 `'7'` ，剩下的都是非质数数字。

请你返回 `s` 的 **完美** 分割数目。由于答案可能很大，请返回答案对 `109 + 7` **取余** 后的结果。

一个 **子字符串** 是字符串中一段连续字符串序列。

**示例 1：**

**输入：**s = "23542185131", k = 3, minLength = 2
**输出：**3
**解释：**存在 3 种完美分割方案：
"2354 | 218 | 5131"
"2354 | 21851 | 31"
"2354218 | 51 | 31"

**示例 2：**

**输入：**s = "23542185131", k = 3, minLength = 3
**输出：**1
**解释：**存在一种完美分割方案："2354 | 218 | 5131" 。

**示例 3：**

**输入：**s = "3312958", k = 3, minLength = 1
**输出：**1
**解释：**存在一种完美分割方案："331 | 29 | 58" 。

**提示：**

*   `1 <= k, minLength <= s.length <= 1000`
*   `s` 每个字符都为数字 `'1'` 到 `'9'` 之一。

[https://leetcode.cn/problems/number-of-beautiful-partitions/description/](https://leetcode.cn/problems/number-of-beautiful-partitions/description/)

```java
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

class Solution { // 超时
    private static HashSet<Character> set = new HashSet<>();
    private static int Mod = (int) 1e9 + 7;
    static {
        set.add('2');
        set.add('3');
        set.add('5');
        set.add('7');
    }
    public int beautifulPartitions(String s, int k, int minLength) {
        int n = s.length();
        boolean[][] f = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            if (set.contains(s.charAt(i))) {
                for (int j = i; j < n; j++) {
                    if (!set.contains(s.charAt(j)) && j - i + 1 >= minLength) {
                        f[i][j] = true;
                    }
                }
            }
        }
        int[][] dp = new int[n + 1][k + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                for (int x = 0; x < i; x++) {
                    if (f[x][i - 1]) {
                        dp[i][j] += dp[x][j - 1] ;
                        dp[i][j] %= Mod;
                    }
                }
            }
        }
        return dp[n][k];
    }
}
```

```java
class Solution { // 双指针，一边更新前面的sum，一边计算后面的dp[i][j];真的好难
    private static final int Mod = (int) 1e9 + 7;
    public int beautifulPartitions(String S, int k, int minLength) {
        char[] s = S.toCharArray();
        int n = s.length;
        if (k * minLength > n || !isPrime(s[0]) || isPrime(s[n - 1])) { // 剪枝
            return 0;
        }
        int[][] dp = new int[k + 1][n + 1];
        dp[0][0] = 1;
        for (int j = 1; j <= k; j++) { // 划分的个数
            int sum = 0;
            for (int i = j * minLength; i + (k - j) * minLength <= n; i++) {
                if (canPartition(s, i - minLength)) {
                    sum = (sum + dp[j - 1][i - minLength]) % Mod;
                }
                if (canPartition(s, i)) {
                    dp[j][i] = sum;
                }
            }
        }
        return dp[k][n];
    }

    private boolean isPrime(char c) {
        return c == '2' || c == '3' || c == '5' || c == '7';
    }

    // 判断是否可以在 j-1 和 j 之间分割（开头和末尾也算）
    private boolean canPartition(char[] s, int j) {
        return j == 0 || j == s.length || !isPrime(s[j - 1]) && isPrime(s[j]);
    }
}
```

# §6.4 不相交区间

2830\. 销售利润最大化
--------------

给你一个整数 `n` 表示数轴上的房屋数量，编号从 `0` 到 `n - 1` 。

另给你一个二维整数数组 `offers` ，其中 `offers[i] = [starti, endi, goldi]` 表示第 `i` 个买家想要以 `goldi` 枚金币的价格购买从 `starti` 到 `endi` 的所有房屋。

作为一名销售，你需要有策略地选择并销售房屋使自己的收入最大化。

返回你可以赚取的金币的最大数目。

**注意** 同一所房屋不能卖给不同的买家，并且允许保留一些房屋不进行出售。

**示例 1：**

**输入：**n = 5, offers = \[\[0,0,1\],\[0,2,2\],\[1,3,2\]\]
**输出：**3
**解释：**
有 5 所房屋，编号从 0 到 4 ，共有 3 个购买要约。
将位于 \[0,0\] 范围内的房屋以 1 金币的价格出售给第 1 位买家，并将位于 \[1,3\] 范围内的房屋以 2 金币的价格出售给第 3 位买家。
可以证明我们最多只能获得 3 枚金币。

**示例 2：**

**输入：**n = 5, offers = \[\[0,0,1\],\[0,2,10\],\[1,3,2\]\]
**输出：**10
**解释：**有 5 所房屋，编号从 0 到 4 ，共有 3 个购买要约。
将位于 \[0,2\] 范围内的房屋以 10 金币的价格出售给第 2 位买家。
可以证明我们最多只能获得 10 枚金币。

**提示：**

*   `1 <= n <= 105`
*   `1 <= offers.length <= 105`
*   `offers[i].length == 3`
*   `0 <= starti <= endi <= n - 1`
*   `1 <= goldi <= 103`

[https://leetcode.cn/problems/maximize-the-profit-as-the-salesman/description/](https://leetcode.cn/problems/maximize-the-profit-as-the-salesman/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public int maximizeTheProfit(int n, List<List<Integer>> offers) {
        List<int[]>[] groups = new List[n];
        Arrays.setAll(groups, e -> new ArrayList<>());
        for (List<Integer> offer : offers) {
            groups[offer.get(1)].add(new int[]{offer.get(0), offer.get(2)});
        }
        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            dp[i + 1] = dp[i];
            for (int[] p : groups[i]) {
                dp[i + 1] = Math.max(dp[i + 1], dp[p[0]] + p[1]);
            }
        }
        return dp[n];
    }
}
```

2008\. 出租车的最大盈利
---------------

你驾驶出租车行驶在一条有 `n` 个地点的路上。这 `n` 个地点从近到远编号为 `1` 到 `n` ，你想要从 `1` 开到 `n` ，通过接乘客订单盈利。你只能沿着编号递增的方向前进，不能改变方向。

乘客信息用一个下标从 **0** 开始的二维数组 `rides` 表示，其中 `rides[i] = [starti, endi, tipi]` 表示第 `i` 位乘客需要从地点 `starti` 前往 `endi` ，愿意支付 `tipi` 元的小费。

**每一位** 你选择接单的乘客 `i` ，你可以 **盈利** `endi - starti + tipi` 元。你同时 **最多** 只能接一个订单。

给你 `n` 和 `rides` ，请你返回在最优接单方案下，你能盈利 **最多** 多少元。

**注意：**你可以在一个地点放下一位乘客，并在同一个地点接上另一位乘客。

**示例 1：**

**输入：**n = 5, rides = \[_**\[2,5,4\]**_,\[1,5,1\]\]
**输出：**7
**解释：**我们可以接乘客 0 的订单，获得 5 - 2 + 4 = 7 元。

**示例 2：**

**输入：**n = 20, rides = \[\[1,6,1\],**_\[3,10,2\]_**,_**\[10,12,3\]**_,\[11,12,2\],\[12,15,2\],**_\[13,18,1\]_**\]
**输出：**20
**解释：**我们可以接以下乘客的订单：
- 将乘客 1 从地点 3 送往地点 10 ，获得 10 - 3 + 2 = 9 元。
- 将乘客 2 从地点 10 送往地点 12 ，获得 12 - 10 + 3 = 5 元。
- 将乘客 5 从地点 13 送往地点 18 ，获得 18 - 13 + 1 = 6 元。
  我们总共获得 9 + 5 + 6 = 20 元。

**提示：**

*   `1 <= n <= 105`
*   `1 <= rides.length <= 3 * 104`
*   `rides[i].length == 3`
*   `1 <= starti < endi <= n`
*   `1 <= tipi <= 105`

[https://leetcode.cn/problems/maximum-earnings-from-taxi/description/](https://leetcode.cn/problems/maximum-earnings-from-taxi/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution { // 和上一个题目套路一模一样
    public long maxTaxiEarnings(int n, int[][] rides) {
        List<int[]>[] groups = new List[n + 1];
        Arrays.setAll(groups, e -> new ArrayList<>());
        for (int[] ride : rides) {
            groups[ride[1]].add(new int[]{ride[0], ride[2]});
        }
        long[] dp = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1];
            for (int[] p : groups[i]) {
                dp[i] = Math.max(dp[i], dp[p[0]] + p[1] + i - p[0]);
            }
        }
        return dp[n];
    }
}
```

1235\. 规划兼职工作
-------------

你打算利用空闲时间来做兼职工作赚些零花钱。

这里有 `n` 份兼职工作，每份工作预计从 `startTime[i]` 开始到 `endTime[i]` 结束，报酬为 `profit[i]`。

给你一份兼职工作表，包含开始时间 `startTime`，结束时间 `endTime` 和预计报酬 `profit` 三个数组，请你计算并返回可以获得的最大报酬。

注意，时间上出现重叠的 2 份工作不能同时进行。

如果你选择的工作在时间 `X` 结束，那么你可以立刻进行在时间 `X` 开始的下一份工作。

**示例 1：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2019/10/19/sample1_1584.png)**

**输入：**startTime = \[1,2,3,3\], endTime = \[3,4,5,6\], profit = \[50,10,40,70\]
**输出：**120
**解释：**
我们选出第 1 份和第 4 份工作， 
时间范围是 \[1-3\]+\[3-6\]，共获得报酬 120 = 50 + 70。

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2019/10/19/sample22_1584.png)**

**输入：**startTime = \[1,2,3,4,6\], endTime = \[3,5,10,6,9\], profit = \[20,20,100,70,60\]
**输出：**150
**解释：**
我们选择第 1，4，5 份工作。 
共获得报酬 150 = 20 + 70 + 60。

**示例 3：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2019/10/19/sample3_1584.png)**

**输入：**startTime = \[1,1,1\], endTime = \[2,3,4\], profit = \[5,6,4\]
**输出：**6

**提示：**

*   `1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4`
*   `1 <= startTime[i] < endTime[i] <= 10^9`
*   `1 <= profit[i] <= 10^4`

[https://leetcode.cn/problems/maximum-profit-in-job-scheduling/description/](https://leetcode.cn/problems/maximum-profit-in-job-scheduling/description/)

```java
class Solution {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[]{startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, (a, b) -> a[1] - b[1]); // 按照结束时间排序

        int[] dp = new int[n + 1];
        for (int i = 0; i < n; i++) {
            int j = search(jobs, i, jobs[i][0]);
            dp[i + 1] = Math.max(dp[i], dp[j + 1] + jobs[i][2]);
        }
        return dp[n];
    }

    // 返回 endTime <= upper 的最大下标
    private int search(int[][] jobs, int right, int upper) {
        int left = -1;
        while (left + 1 < right) {
            int mid = (left + right) >>> 1;
            if (jobs[mid][1] <= upper) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
```

1751\. 最多可以参加的会议数目 II
---------------------

给你一个 `events` 数组，其中 `events[i] = [startDayi, endDayi, valuei]` ，表示第 `i` 个会议在 `startDayi` 天开始，第 `endDayi` 天结束，如果你参加这个会议，你能得到价值 `valuei` 。同时给你一个整数 `k` 表示你能参加的最多会议数目。

你同一时间只能参加一个会议。如果你选择参加某个会议，那么你必须 **完整** 地参加完这个会议。会议结束日期是包含在会议内的，也就是说你不能同时参加一个开始日期与另一个结束日期相同的两个会议。

请你返回能得到的会议价值 **最大和** 。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2021/02/06/screenshot-2021-01-11-at-60048-pm.png)

**输入：**events = \[\[1,2,4\],\[3,4,3\],\[2,3,1\]\], k = 2
**输出：**7
**解释：**选择绿色的活动会议 0 和 1，得到总价值和为 4 + 3 = 7 。

**示例 2：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2021/02/06/screenshot-2021-01-11-at-60150-pm.png)

**输入：**events = \[\[1,2,4\],\[3,4,3\],\[2,3,10\]\], k = 2
**输出：**10
**解释：**参加会议 2 ，得到价值和为 10 。
你没法再参加别的会议了，因为跟会议 2 有重叠。你 **不** 需要参加满 k 个会议。

**示例 3：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2021/02/06/screenshot-2021-01-11-at-60703-pm.png)**

**输入：**events = \[\[1,1,1\],\[2,2,2\],\[3,3,3\],\[4,4,4\]\], k = 3
**输出：**9
**解释：**尽管会议互不重叠，你只能参加 3 个会议，所以选择价值最大的 3 个会议。

**提示：**

*   `1 <= k <= events.length`
*   `1 <= k * events.length <= 106`
*   `1 <= startDayi <= endDayi <= 109`
*   `1 <= valuei <= 106`

[https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/description/](https://leetcode.cn/problems/maximum-number-of-events-that-can-be-attended-ii/description/)

```java
import java.util.Arrays;

class Solution {
    public int maxValue(int[][] events, int k) {
        int n = events.length;
        Arrays.sort(events, (a, b) -> a[1] - b[1]); // 按照结束时间排序
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 1; i <= n; i++) {
            int index = search(events, events[i - 1][0], i);
            for (int j = 1; j <= k; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[index + 1][j - 1] + events[i - 1][2]);
            }
        }
        return dp[n][k];
    }

    // 返回 endTime < target 的最大下标
    private int search(int[][] events, int target, int right) {
        int left = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (events[mid][1] < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left - 1;
    }
}
```

