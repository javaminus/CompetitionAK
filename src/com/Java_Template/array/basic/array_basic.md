2799\. 统计完全子数组的数目
-----------------

给你一个由 **正** 整数组成的数组 `nums` 。

如果数组中的某个子数组满足下述条件，则称之为 **完全子数组** ：

*   子数组中 **不同** 元素的数目等于整个数组不同元素的数目。

返回数组中 **完全子数组** 的数目。

**子数组** 是数组中的一个连续非空序列。

**示例 1：**

**输入：**nums = \[1,3,1,2,2\]
**输出：**4
**解释：**完全子数组有：\[1,3,1,2\]、\[1,3,1,2,2\]、\[3,1,2\] 和 \[3,1,2,2\] 。

**示例 2：**

**输入：**nums = \[5,5,5,5\]
**输出：**10
**解释：**数组仅由整数 5 组成，所以任意子数组都满足完全子数组的条件。子数组的总数为 10 。

**提示：**

*   `1 <= nums.length <= 1000`
*   `1 <= nums[i] <= 2000`

[https://leetcode.cn/problems/count-complete-subarrays-in-an-array/](https://leetcode.cn/problems/count-complete-subarrays-in-an-array/)
```java
import java.util.HashMap;
import java.util.HashSet;

class Solution {
    public int countCompleteSubarrays(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for(int num:nums) set.add(num);
        int m = set.size();
        int ans = 0, left = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.merge(num, 1, Integer::sum);
            while (cnt.size() == m) {
                int x = nums[left++];
                if (cnt.merge(x, -1, Integer::sum) == 0) {
                    cnt.remove(x);
                }
            }
            ans += left; // 子数组左端点 < left 的都是合法的
        }
        return ans;
    }
}
```