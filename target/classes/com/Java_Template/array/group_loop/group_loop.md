3105\. 最长的严格递增或递减子数组
--------------------

给你一个整数数组 `nums` 。

返回数组 `nums` 中

**严格递增**

或**

严格递减





**的最长非空子数组的长度。

**示例 1：**

**输入：**nums = \[1,4,3,3,2\]

**输出：**2

**解释：**

`nums` 中严格递增的子数组有`[1]`、`[2]`、`[3]`、`[3]`、`[4]` 以及 `[1,4]` 。

`nums` 中严格递减的子数组有`[1]`、`[2]`、`[3]`、`[3]`、`[4]`、`[3,2]` 以及 `[4,3]` 。

因此，返回 `2` 。

**示例 2：**

**输入：**nums = \[3,3,3,3\]

**输出：**1

**解释：**

`nums` 中严格递增的子数组有 `[3]`、`[3]`、`[3]` 以及 `[3]` 。

`nums` 中严格递减的子数组有 `[3]`、`[3]`、`[3]` 以及 `[3]` 。

因此，返回 `1` 。

**示例 3：**

**输入：**nums = \[3,2,1\]

**输出：**3

**解释：**

`nums` 中严格递增的子数组有 `[3]`、`[2]` 以及 `[1]` 。

`nums` 中严格递减的子数组有 `[3]`、`[2]`、`[1]`、`[3,2]`、`[2,1]` 以及 `[3,2,1]` 。

因此，返回 `3` 。

**提示：**

*   `1 <= nums.length <= 50`
*   `1 <= nums[i] <= 50`

[https://leetcode.cn/problems/longest-strictly-increasing-or-strictly-decreasing-subarray/description/](https://leetcode.cn/problems/longest-strictly-increasing-or-strictly-decreasing-subarray/description/)

```java
class Solution {
	public int longestMonotonicSubarray(int[] nums) {
		int n = nums.length;
		int ans = 1;
		int i = 0;
		while (i < n - 1) {
			if (nums[i] == nums[i + 1]) {
				i++;
				continue;
			}
			int i0 = i;
			boolean inc = nums[i + 1] > nums[i];
			i += 2; // 这里的i是下一位，我们就判断下一位是否可以加入子数组
			while (i < n && (nums[i] > nums[i - 1] == inc) && nums[i] != nums[i - 1]) {
				i++;
			}
			ans = Math.max(ans, i - i0);
			i--;
		}
		return ans;
	}
}
```

978\. 最长湍流子数组
-------------

给定一个整数数组 `arr` ，返回 `arr` 的 _最大湍流子数组的**长度**_ 。

如果比较符号在子数组中的每个相邻元素对之间翻转，则该子数组是 **湍流子数组** 。

更正式地来说，当 `arr` 的子数组 `A[i], A[i+1], ..., A[j]` 满足仅满足下列条件时，我们称其为_湍流子数组_：

*   若 `i <= k < j` ：
    *   当 `k` 为奇数时， `A[k] > A[k+1]`，且
    *   当 `k` 为偶数时，`A[k] < A[k+1]`；
*   **或** 若 `i <= k < j` ：
    *   当 `k` 为偶数时，`A[k] > A[k+1]` ，且
    *   当 `k` 为奇数时， `A[k] < A[k+1]`。

**示例 1：**

**输入：**arr = \[9,4,2,10,7,8,8,1,9\]
**输出：**5
**解释：**arr\[1\] > arr\[2\] < arr\[3\] > arr\[4\] < arr\[5\]

**示例 2：**

**输入：**arr = \[4,8,12,16\]
**输出：**2

**示例 3：**

**输入：**arr = \[100\]
**输出：**1

**提示：**

*   `1 <= arr.length <= 4 * 104`
*   `0 <= arr[i] <= 109`

[https://leetcode.cn/problems/longest-turbulent-subarray/description/](https://leetcode.cn/problems/longest-turbulent-subarray/description/)

```java
class Solution {
	public int maxTurbulenceSize(int[] arr) {
		int n = arr.length;
		int ans = 1, i = 0;
		while (i < n - 1) {
			if (arr[i] == arr[i + 1]) {
				i++;
				continue;
			}
			int i0 = i;
			boolean inc = (arr[i + 1] > arr[i]);
			i += 2;
			while (i < n && arr[i] != arr[i - 1] && (arr[i] > arr[i - 1] != inc)) {
				i++;
				inc = !inc;
			}
			ans = Math.max(ans, i - i0);
			i--;
		}
		return ans;
	}
}
```

845\. 数组中的最长山脉
--------------

把符合下列属性的数组 `arr` 称为 **山脉数组** ：

*   `arr.length >= 3`
*   存在下标 `i`（`0 < i < arr.length - 1`），满足
    *   `arr[0] < arr[1] < ... < arr[i - 1] < arr[i]`
    *   `arr[i] > arr[i + 1] > ... > arr[arr.length - 1]`

给出一个整数数组 `arr`，返回最长山脉子数组的长度。如果不存在山脉子数组，返回 `0` 。

**示例 1：**

**输入：**arr = \[2,1,4,7,3,2,5\]
**输出：**5
**解释：**最长的山脉子数组是 \[1,4,7,3,2\]，长度为 5。

**示例 2：**

**输入：**arr = \[2,2,2\]
**输出：**0
**解释：**不存在山脉子数组。

**提示：**

*   `1 <= arr.length <= 104`
*   `0 <= arr[i] <= 104`

**进阶：**

*   你可以仅用一趟扫描解决此问题吗？
*   你可以用 `O(1)` 空间解决此问题吗？

[https://leetcode.cn/problems/longest-mountain-in-array/description/](https://leetcode.cn/problems/longest-mountain-in-array/description/)

```java

```

