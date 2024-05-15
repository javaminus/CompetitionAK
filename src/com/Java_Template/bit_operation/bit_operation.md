100282\. 数组最后一个元素的最小值
---------------------

给你两个整数 `n` 和 `x` 。你需要构造一个长度为 `n` 的 **正整数** 数组 `nums` ，对于所有 `0 <= i < n - 1` ，满足 `nums[i + 1]` **大于** `nums[i]` ，并且数组 `nums` 中所有元素的按位 `AND` 运算结果为 `x` 。

返回 `nums[n - 1]` 可能的 **最小** 值。

**示例 1：**

**输入：**n = 3, x = 4

**输出：**6

**解释：**

数组 `nums` 可以是 `[4,5,6]` ，最后一个元素为 `6` 。

**示例 2：**

**输入：**n = 2, x = 7

**输出：**15

**解释：**

数组 `nums` 可以是 `[7,15]` ，最后一个元素为 `15` 。

**提示：**

*   `1 <= n, x <= 108`

[https://leetcode.cn/problems/minimum-array-end/](https://leetcode.cn/problems/minimum-array-end/)

```java
import java.util.ArrayList;

class Solution {
    public long minEnd(int n, int x) {
        // 这里其实就是求x的二进制中，我们可以将其中的0变成1，但是不能将1变成0，求比x大的第n - 1个数
        n--;
        long ans = x;
        int i = 0, j = 0;
        while ((n >> j) > 0) {
            // x的第i个比特位是0
            if (((ans >> i) & 1) == 0) {
                // 空位填入n的第j个比特位
                ans |= (long) (n >> j & 1) << i;
                j++;
            }
            i++;
        }
        return ans;
    }
}
```
2588\. 统计美丽子数组数目
----------------

给你一个下标从 **0** 开始的整数数组`nums` 。每次操作中，你可以：

*   选择两个满足 `0 <= i, j < nums.length` 的不同下标 `i` 和 `j` 。
*   选择一个非负整数 `k` ，满足 `nums[i]` 和 `nums[j]` 在二进制下的第 `k` 位（下标编号从 **0** 开始）是 `1` 。
*   将 `nums[i]` 和 `nums[j]` 都减去 `2k` 。

如果一个子数组内执行上述操作若干次后，该子数组可以变成一个全为 `0` 的数组，那么我们称它是一个 **美丽** 的子数组。

请你返回数组 `nums` 中 **美丽子数组** 的数目。

子数组是一个数组中一段连续 **非空** 的元素序列。

**示例 1：**

**输入：**nums = \[4,3,1,2,4\]
**输出：**2
**解释：**nums 中有 2 个美丽子数组：\[4,_**3,1,2**_,4\] 和 \[_**4,3,1,2,4**_\] 。
- 按照下述步骤，我们可以将子数组 \[3,1,2\] 中所有元素变成 0 ：
  - 选择 \[_**3**_, 1, _**2**_\] 和 k = 1 。将 2 个数字都减去 21 ，子数组变成 \[1, 1, 0\] 。
  - 选择 \[_**1**_, _**1**_, 0\] 和 k = 0 。将 2 个数字都减去 20 ，子数组变成 \[0, 0, 0\] 。
- 按照下述步骤，我们可以将子数组 \[4,3,1,2,4\] 中所有元素变成 0 ：
  - 选择 \[_**4**_, 3, 1, 2, _**4**_\] 和 k = 2 。将 2 个数字都减去 22 ，子数组变成 \[0, 3, 1, 2, 0\] 。
  - 选择 \[0, _**3**_, _**1**_, 2, 0\] 和 k = 0 。将 2 个数字都减去 20 ，子数组变成 \[0, 2, 0, 2, 0\] 。
  - 选择 \[0, _**2**_, 0, _**2**_, 0\] 和 k = 1 。将 2 个数字都减去 21 ，子数组变成 \[0, 0, 0, 0, 0\] 。

**示例 2：**

**输入：**nums = \[1,10,4\]
**输出：**0
**解释：**nums 中没有任何美丽子数组。

**提示：**

*   `1 <= nums.length <= 105`
*   `0 <= nums[i] <= 106`

[https://leetcode.cn/problems/count-the-number-of-beautiful-subarrays/](https://leetcode.cn/problems/count-the-number-of-beautiful-subarrays/)

```java
import java.util.HashMap;

class Solution {
    public long beautifulSubarrays(int[] nums) {
        long ans = 0;
        int n = nums.length;
        int[] prefixSum = new int[n + 1]; // 异或前缀和
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] ^ nums[i];
        }
        // 求异或前缀和相同的两个值，使用hash表优化
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : prefixSum) {
            ans += cnt.getOrDefault(x, 0);
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }
}
```

