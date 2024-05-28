> ```
> 单调队列就是滑动窗口  双端队列一般保存数组的下标，而不是数组的值
> ```

1438\. 绝对差不超过限制的最长连续子数组
-----------------------

给你一个整数数组 `nums` ，和一个表示限制的整数 `limit`，请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于 `limit` _。_

如果不存在满足条件的子数组，则返回 `0` 。

**示例 1：**

**输入：**nums = \[8,2,4,7\], limit = 4
**输出：**2 
**解释：**所有子数组如下：
\[8\] 最大绝对差 |8-8| = 0 <= 4.
\[8,2\] 最大绝对差 |8-2| = 6 > 4. 
\[8,2,4\] 最大绝对差 |8-2| = 6 > 4.
\[8,2,4,7\] 最大绝对差 |8-2| = 6 > 4.
\[2\] 最大绝对差 |2-2| = 0 <= 4.
\[2,4\] 最大绝对差 |2-4| = 2 <= 4.
\[2,4,7\] 最大绝对差 |2-7| = 5 > 4.
\[4\] 最大绝对差 |4-4| = 0 <= 4.
\[4,7\] 最大绝对差 |4-7| = 3 <= 4.
\[7\] 最大绝对差 |7-7| = 0 <= 4. 
因此，满足题意的最长子数组的长度为 2 。

**示例 2：**

**输入：**nums = \[10,1,2,4,7,2\], limit = 5
**输出：**4 
**解释：**满足题意的最长子数组是 \[2,4,7,2\]，其最大绝对差 |2-7| = 5 <= 5 。

**示例 3：**

**输入：**nums = \[4,2,2,2,4,4,2,2\], limit = 0
**输出：**3

**提示：**

*   `1 <= nums.length <= 10^5`
*   `1 <= nums[i] <= 10^9`
*   `0 <= limit <= 10^9`

[https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/description/](https://leetcode.cn/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/description/)

```java
import java.util.Deque;
import java.util.LinkedList;

class Solution {
    public int longestSubarray(int[] nums, int limit) {
        Deque<Integer> queMax = new LinkedList<>();
        Deque<Integer> queMin = new LinkedList<>();
        int n = nums.length;
        int left = 0, right = 0;
        int ans = 0;
        while (right < n) {
            while (!queMax.isEmpty() && queMax.peekLast() < nums[right]) {
                queMax.pollLast();
            }
            while (!queMin.isEmpty() && queMin.peekLast() > nums[right]) {
                queMin.pollLast();
            }
            queMax.offerLast(nums[right]);
            queMin.offerLast(nums[right]);
            while (!queMax.isEmpty() && !queMin.isEmpty() && queMax.peekFirst() - queMin.peekFirst() > limit) {
                if (nums[left] == queMax.peekFirst()) {
                    queMax.pollFirst();
                }
                if (nums[left] == queMin.peekFirst()) {
                    queMin.pollFirst();
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }
}
```

239\. 滑动窗口最大值
-------------

给你一个整数数组 `nums`，有一个大小为 `k` 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 `k` 个数字。滑动窗口每次只向右移动一位。

返回 _滑动窗口中的最大值_ 。

**示例 1：**

**输入：**nums = \[1,3,-1,-3,5,3,6,7\], k = 3
**输出：**\[3,3,5,5,6,7\]
**解释：**
滑动窗口的位置                最大值
---------------               -----
\[1  3  -1\] -3  5  3  6  7       **3**
 1 \[3  -1  -3\] 5  3  6  7       **3**
 1  3 \[-1  -3  5\] 3  6  7      ** 5**
 1  3  -1 \[-3  5  3\] 6  7       **5**
 1  3  -1  -3 \[5  3  6\] 7       **6**
 1  3  -1  -3  5 \[3  6  7\]      **7**

**示例 2：**

**输入：**nums = \[1\], k = 1
**输出：**\[1\]

**提示：**

*   `1 <= nums.length <= 105`
*   `-104 <= nums[i] <= 104`
*   `1 <= k <= nums.length`

[https://leetcode.cn/problems/sliding-window-maximum/description/](https://leetcode.cn/problems/sliding-window-maximum/description/)

```java
import java.util.ArrayDeque;

class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        int right = 0;
        int[] ans = new int[n - k + 1];
        ArrayDeque<Integer> queue = new ArrayDeque<>(); // 大 -> 小
        while (right < n) {
            while (!queue.isEmpty() && nums[right] >= nums[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.offerLast(right);
            if (right - queue.peekFirst() >= k) {
                queue.pollFirst();
            }
            if (right - k + 1 >= 0) {
                ans[right - k + 1] = nums[queue.peekFirst()];
            }
            right++;
        }
        return ans;
    }
}
```

1696\. 跳跃游戏 VI
--------------

给你一个下标从 **0** 开始的整数数组 `nums` 和一个整数 `k` 。

一开始你在下标 `0` 处。每一步，你最多可以往前跳 `k` 步，但你不能跳出数组的边界。也就是说，你可以从下标 `i` 跳到 `[i + 1， min(n - 1, i + k)]` **包含** 两个端点的任意位置。

你的目标是到达数组最后一个位置（下标为 `n - 1` ），你的 **得分** 为经过的所有数字之和。

请你返回你能得到的 **最大得分** 。

**示例 1：**

**输入：**nums = \[**1**,**\-1**,-2,**4**,-7,**3**\], k = 2
**输出：**7
**解释：**你可以选择子序列 \[1,-1,4,3\] （上面加粗的数字），和为 7 。

**示例 2：**

**输入：**nums = \[**10**,-5,-2,**4**,0,**3**\], k = 3
**输出：**17
**解释：**你可以选择子序列 \[10,4,3\] （上面加粗数字），和为 17 。

**示例 3：**

**输入：**nums = \[1,-5,-20,4,-1,3,-6,-3\], k = 2
**输出：**0

**提示：**

*    `1 <= nums.length, k <= 105`
*    `-104 <= nums[i] <= 104`

[https://leetcode.cn/problems/jump-game-vi/description/?envType=daily-question&envId=2024-02-05](https://leetcode.cn/problems/jump-game-vi/description/?envType=daily-question&envId=2024-02-05)

```java
import java.util.ArrayDeque;

class Solution {
    public int maxResult(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        ArrayDeque<Integer> deque = new ArrayDeque<>(); // 大 -->小
        for (int i = 1; i < n; i++) {
            while (!deque.isEmpty() && dp[i - 1] >= dp[deque.peekLast()]) {
                deque.pollLast();
            }
            deque.offerLast(i - 1);
            if (deque.peekFirst() < i - k) {
                deque.pollFirst();
            }
            dp[i] = dp[deque.peekFirst()] + nums[i];
        }
        return dp[n - 1];

    }
}
```

862\. 和至少为 K 的最短子数组
-------------------

给你一个整数数组 `nums` 和一个整数 `k` ，找出 `nums` 中和至少为 `k` 的 **最短非空子数组** ，并返回该子数组的长度。如果不存在这样的 **子数组** ，返回 `-1` 。

**子数组** 是数组中 **连续** 的一部分。

**示例 1：**

**输入：**nums = \[1\], k = 1
**输出：**1

**示例 2：**

**输入：**nums = \[1,2\], k = 4
**输出：**\-1

**示例 3：**

**输入：**nums = \[2,-1,2\], k = 3
**输出：**3

**提示：**

*   `1 <= nums.length <= 105`
*   `-105 <= nums[i] <= 105`
*   `1 <= k <= 109`

[https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/description/](https://leetcode.cn/problems/shortest-subarray-with-sum-at-least-k/description/)

```java
import java.util.ArrayDeque;

class Solution {
        public int shortestSubarray(int[] nums, int k) {
        int n = nums.length;
        ArrayDeque<Integer> queue = new ArrayDeque<>(); // 小 ->大
        int right = 0, ans = n + 1;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        while (right <= n) {
            while (!queue.isEmpty() && prefix[right] <= prefix[queue.peekLast()]) {
                queue.pollLast();
            }
            queue.offerLast(right);
            while (!queue.isEmpty() && prefix[right] - prefix[queue.peekFirst()] >= k) {
                ans = Math.min(ans, right - queue.pollFirst());
            }
            right++;
        }
        return ans == n + 1 ? -1 : ans;
    }
}
```

1425\. 带限制的子序列和
---------------

给你一个整数数组 `nums` 和一个整数 `k` ，请你返回 **非空** 子序列元素和的最大值，子序列需要满足：子序列中每两个 **相邻** 的整数 `nums[i]` 和 `nums[j]` ，它们在原数组中的下标 `i` 和 `j` 满足 `i < j` 且 `j - i <= k` 。

数组的子序列定义为：将数组中的若干个数字删除（可以删除 0 个数字），剩下的数字按照原本的顺序排布。

**示例 1：**

**输入：**nums = \[10,2,-10,5,20\], k = 2
**输出：**37
**解释：**子序列为 \[10, 2, 5, 20\] 。

**示例 2：**

**输入：**nums = \[-1,-2,-3\], k = 1
**输出：**\-1
**解释：**子序列必须是非空的，所以我们选择最大的数字。

**示例 3：**

**输入：**nums = \[10,-2,-10,-5,20\], k = 2
**输出：**23
**解释：**子序列为 \[10, -2, -5, 20\] 。

**提示：**

*   `1 <= k <= nums.length <= 10^5`
*   `-10^4 <= nums[i] <= 10^4`

[https://leetcode.cn/problems/constrained-subsequence-sum/description/](https://leetcode.cn/problems/constrained-subsequence-sum/description/)

```java
import java.util.ArrayDeque;

class Solution {
    public int constrainedSubsetSum(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        int ans = dp[0];
        ArrayDeque<int[]> queue = new ArrayDeque<>(); // 大 -> 小
        for (int i = 1; i < n; i++) {
            while (!queue.isEmpty() && queue.peekLast()[1] <= dp[i - 1]) {
                queue.pollLast();
            }
            queue.offerLast(new int[]{i - 1, dp[i - 1]});
            while (!queue.isEmpty() && queue.peekFirst()[0] < i - k) {
                queue.pollFirst();
            }
            dp[i] = Math.max(0, queue.peekFirst()[1]) + nums[i];
            ans = Math.max(dp[i], ans);
        }
        return ans;
    }
}
```

