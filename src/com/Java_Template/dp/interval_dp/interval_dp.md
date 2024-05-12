3040\. 相同分数的最大操作数目 II
---------------------

给你一个整数数组 `nums` ，如果 `nums` **至少** 包含 `2` 个元素，你可以执行以下操作中的 **任意** 一个：

*   选择 `nums` 中最前面两个元素并且删除它们。
*   选择 `nums` 中最后两个元素并且删除它们。
*   选择 `nums` 中第一个和最后一个元素并且删除它们。

一次操作的 **分数** 是被删除元素的和。

在确保 **所有操作分数相同** 的前提下，请你求出 **最多** 能进行多少次操作。

请你返回按照上述要求 **最多** 可以进行的操作次数。

**示例 1：**

**输入：**nums = \[3,2,1,2,3,4\]
**输出：**3
**解释：**我们执行以下操作：
- 删除前两个元素，分数为 3 + 2 = 5 ，nums = \[1,2,3,4\] 。
- 删除第一个元素和最后一个元素，分数为 1 + 4 = 5 ，nums = \[2,3\] 。
- 删除第一个元素和最后一个元素，分数为 2 + 3 = 5 ，nums = \[\] 。
  由于 nums 为空，我们无法继续进行任何操作。

**示例 2：**

**输入：**nums = \[3,2,6,1,4\]
**输出：**2
**解释：**我们执行以下操作：
- 删除前两个元素，分数为 3 + 2 = 5 ，nums = \[6,1,4\] 。
- 删除最后两个元素，分数为 1 + 4 = 5 ，nums = \[6\] 。
  至多进行 2 次操作。

**提示：**

*   `2 <= nums.length <= 2000`
*   `1 <= nums[i] <= 1000`

[https://leetcode.cn/problems/maximum-number-of-operations-with-the-same-score-ii/description/](https://leetcode.cn/problems/maximum-number-of-operations-with-the-same-score-ii/description/)

```java
private int[] nums;
    private int[][] memo;
    @Override
    public int maxOperations(int[] nums) {
        int n = nums.length;
        this.nums = nums;
        memo = new int[n][n];
        int res1 = helper(2, n - 1, nums[0] + nums[1]); // 最前面两个
        int res2 = helper(0, n - 3, nums[n - 2] + nums[n - 1]); // 最后两个
        int res3 = helper(1, n - 2, nums[0] + nums[n - 1]); // 第一个和最后一个
        return Math.max(Math.max(res1, res2), res3) + 1; // 加上第一次操作
    }
    private int helper(int i, int j, int target) {
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return dfs(i, j, target);
    }
    private int dfs(int i, int j, int target) {
        if (i >= j) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        int res = 0;
        if (nums[i] + nums[i + 1] == target) {
            res = Math.max(res, dfs(i + 2, j, target) + 1);
        }
        if (nums[j - 1] + nums[j] == target) {
            res = Math.max(res, dfs(i, j - 2, target) + 1);
        }
        if (nums[i] + nums[j] == target) {
            res = Math.max(res, dfs(i + 1, j - 1, target) + 1);
        }
        return memo[i][j] = res;
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

