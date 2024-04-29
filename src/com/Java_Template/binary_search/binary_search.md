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