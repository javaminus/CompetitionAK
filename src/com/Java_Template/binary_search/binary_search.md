100257\. 找出唯一性数组的中位数
--------------------

给你一个整数数组 `nums` 。数组 `nums` 的 **唯一性数组** 是一个按元素从小到大排序的数组，包含了 `nums` 的所有

非空子数组中

不同元素的个数。

换句话说，这是由所有 `0 <= i <= j < nums.length` 的 `distinct(nums[i..j])` 组成的递增数组。

其中，`distinct(nums[i..j])` 表示从下标 `i` 到下标 `j` 的子数组中不同元素的数量。

返回 `nums` **唯一性数组** 的 **中位数** 。

**注意**，数组的 **中位数** 定义为有序数组的中间元素。如果有两个中间元素，则取值较小的那个。

**示例 1：**

**输入：**nums = \[1,2,3\]

**输出：**1

**解释：**

`nums` 的唯一性数组为 `[distinct(nums[0..0]), distinct(nums[1..1]), distinct(nums[2..2]), distinct(nums[0..1]), distinct(nums[1..2]), distinct(nums[0..2])]`，即 `[1, 1, 1, 2, 2, 3]` 。唯一性数组的中位数为 1 ，因此答案是 1 。

**示例 2：**

**输入：**nums = \[3,4,3,4,5\]

**输出：**2

**解释：**

`nums` 的唯一性数组为 `[1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3]` 。唯一性数组的中位数为 2 ，因此答案是 2 。

**示例 3：**

**输入：**nums = \[4,3,5,4\]

**输出：**2

**解释：**

`nums` 的唯一性数组为 `[1, 1, 1, 1, 2, 2, 2, 3, 3, 3]` 。唯一性数组的中位数为 2 ，因此答案是 2 。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i] <= 105`

[https://leetcode.cn/problems/find-the-median-of-the-uniqueness-array/description/](https://leetcode.cn/problems/find-the-median-of-the-uniqueness-array/description/)
```java
import java.util.HashMap;

class Solution {
    public int medianOfUniquenessArray(int[] nums) {
        int n = nums.length;
        long k = ((long) (n + 1) * n / 2 + 1) / 2;
        int left = 0, right = n - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(nums, mid, k)) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return right + 1;
    }

    private boolean check(int[] nums, int upper, long target) { // 子数组的不同个数最多为upper的数组个数cnt，与中位数比较
        long cnt = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            map.merge(nums[right], 1, Integer::sum);
            while (map.size() > upper) {
                int out = nums[left++];
                if (map.merge(out, -1, Integer::sum) == 0) {
                    map.remove(out);
                }
            }
            cnt += right - left + 1;
            if (cnt >= target) {
                return true;
            }
        }
        return false;
    }
}
```
3139\. 使数组中所有元素相等的最小开销
----------------------

给你一个整数数组 `nums` 和两个整数 `cost1` 和 `cost2` 。你可以执行以下 **任一** 操作 **任意** 次：

*   从 `nums` 中选择下标 `i` 并且将 `nums[i]` **增加** `1` ，开销为 `cost1`。
*   选择 `nums` 中两个 **不同** 下标 `i` 和 `j` ，并且将 `nums[i]` 和 `nums[j]` 都 **增加** `1` ，开销为 `cost2` 。

你的目标是使数组中所有元素都 **相等** ，请你返回需要的 **最小开销** 之和。

由于答案可能会很大，请你将它对 `109 + 7` **取余** 后返回。

**示例 1：**

**输入：**nums = \[4,1\], cost1 = 5, cost2 = 2

**输出：**15

**解释：**

执行以下操作可以使数组中所有元素相等：

*   将 `nums[1]` 增加 1 ，开销为 5 ，`nums` 变为 `[4,2]` 。
*   将 `nums[1]` 增加 1 ，开销为 5 ，`nums` 变为 `[4,3]` 。
*   将 `nums[1]` 增加 1 ，开销为 5 ，`nums` 变为 `[4,4]` 。

总开销为 15 。

**示例 2：**

**输入：**nums = \[2,3,3,3,5\], cost1 = 2, cost2 = 1

**输出：**6

**解释：**

执行以下操作可以使数组中所有元素相等：

*   将 `nums[0]` 和 `nums[1]` 同时增加 1 ，开销为 1 ，`nums` 变为 `[3,4,3,3,5]` 。
*   将 `nums[0]` 和 `nums[2]` 同时增加 1 ，开销为 1 ，`nums` 变为 `[4,4,4,3,5]` 。
*   将 `nums[0]` 和 `nums[3]` 同时增加 1 ，开销为 1 ，`nums` 变为 `[5,4,4,4,5]` 。
*   将 `nums[1]` 和 `nums[2]` 同时增加 1 ，开销为 1 ，`nums` 变为 `[5,5,5,4,5]` 。
*   将 `nums[3]` 增加 1 ，开销为 2 ，`nums` 变为 `[5,5,5,5,5]` 。

总开销为 6 。

**示例 3：**

**输入：**nums = \[3,5,3\], cost1 = 1, cost2 = 3

**输出：**4

**解释：**

执行以下操作可以使数组中所有元素相等：

*   将 `nums[0]` 增加 1 ，开销为 1 ，`nums` 变为 `[4,5,3]` 。
*   将 `nums[0]` 增加 1 ，开销为 1 ，`nums` 变为 `[5,5,3]` 。
*   将 `nums[2]` 增加 1 ，开销为 1 ，`nums` 变为 `[5,5,4]` 。
*   将 `nums[2]` 增加 1 ，开销为 1 ，`nums` 变为 `[5,5,5]` 。

总开销为 4 。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i] <= 106`
*   `1 <= cost1 <= 106`
*   `1 <= cost2 <= 106`

[https://leetcode.cn/problems/minimum-cost-to-equalize-array/description/](https://leetcode.cn/problems/minimum-cost-to-equalize-array/description/)
```java
class Solution {
    long MOD = (long) 1e9 + 7;

    public int minCostToEqualizeArray(int[] nums, int cost1, int cost2) {
        int n = nums.length;
        long s = 0;
        long mx = Long.MIN_VALUE, mn = Long.MAX_VALUE;
        for (int x : nums) {
            s += x;
            mx = Math.max(mx, x);
            mn = Math.min(mn, x);
        }
        long ans = (mx * n - s) * cost1;
        if (2 * cost1 <= cost2) {
            return (int) (ans % MOD);
        }

        long left = mx - mn;
        long right = mx * (n - 1) - (s - mn);
        long t;
        while (true) {
            if (left <= right) {
                long k = left + right;
                t = k / 2 * cost2 + (k % 2) * cost1;
            } else {
                long k = right;
                t = k * cost2 + (left - right) * cost1;
            }
            if (t <= ans) {
                ans = t;
            } else {
                return (int) (ans % MOD);
            }
            right += n - 1;
            left++;
        }
    }
}
```