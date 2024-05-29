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
}
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

