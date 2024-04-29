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