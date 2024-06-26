2866\. 美丽塔 II
-------------

给你一个长度为 `n` 下标从 **0** 开始的整数数组 `maxHeights` 。

你的任务是在坐标轴上建 `n` 座塔。第 `i` 座塔的下标为 `i` ，高度为 `heights[i]` 。

如果以下条件满足，我们称这些塔是 **美丽** 的：

1.  `1 <= heights[i] <= maxHeights[i]`
2.  `heights` 是一个 **山脉** 数组。

如果存在下标 `i` 满足以下条件，那么我们称数组 `heights` 是一个 **山脉** 数组：

*   对于所有 `0 < j <= i` ，都有 `heights[j - 1] <= heights[j]`
*   对于所有 `i <= k < n - 1` ，都有 `heights[k + 1] <= heights[k]`

请你返回满足 **美丽塔** 要求的方案中，**高度和的最大值** 。

**示例 1：**

**输入：**maxHeights = \[5,3,4,1,1\]
**输出：**13
**解释：**和最大的美丽塔方案为 heights = \[5,3,3,1,1\] ，这是一个美丽塔方案，因为：
- 1 <= heights\[i\] <= maxHeights\[i\]  
- heights 是个山脉数组，峰值在 i = 0 处。
  13 是所有美丽塔方案中的最大高度和。

**示例 2：**

**输入：**maxHeights = \[6,5,3,9,2,7\]
**输出：**22
**解释：** 和最大的美丽塔方案为 heights = \[3,3,3,9,2,2\] ，这是一个美丽塔方案，因为：
- 1 <= heights\[i\] <= maxHeights\[i\]
- heights 是个山脉数组，峰值在 i = 3 处。
  22 是所有美丽塔方案中的最大高度和。

**示例 3：**

**输入：**maxHeights = \[3,2,5,5,2,3\]
**输出：**18
**解释：**和最大的美丽塔方案为 heights = \[2,2,5,5,2,2\] ，这是一个美丽塔方案，因为：
- 1 <= heights\[i\] <= maxHeights\[i\]
- heights 是个山脉数组，最大值在 i = 2 处。
  注意，在这个方案中，i = 3 也是一个峰值。
  18 是所有美丽塔方案中的最大高度和。

**提示：**

*   `1 <= n == maxHeights <= 105`
*   `1 <= maxHeights[i] <= 109`

[https://leetcode.cn/problems/beautiful-towers-II/description/](https://leetcode.cn/problems/beautiful-towers-II/description/)

```java
class Solution {
    public long maximumSumOfHeights(List<Integer> maxHeights) {
        // 如果i就是峰值，最大的前后缀  前后缀+动态规划
        int n = maxHeights.size();
        long ans = 0;
        long[] prefix = new long[n];
        long[] suffix = new long[n];
        ArrayDeque<Integer> stack1 = new ArrayDeque<>();
        ArrayDeque<Integer> stack2 = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack1.isEmpty() && maxHeights.get(i) < maxHeights.get(stack1.peek())) {
                stack1.pop();
            }
            if (stack1.isEmpty()) { // 思考什么时候栈为空？ 答：当前元素比它前面的元素逗笑的时候或则i==0时，例如"5，4，3，2，1"
                prefix[i] = (long) (i + 1) * maxHeights.get(i);
            }else{ // !stack1.isEmpty()
                prefix[i] = prefix[stack1.peek()] + (long) (i - stack1.peek()) * maxHeights.get(i);
            }
            stack1.push(i);
        }
        for (int i = n - 1; i >= 0; i--) {
            while (!stack2.isEmpty() && maxHeights.get(i) < maxHeights.get(stack2.peek())) {
                stack2.pop();
            }
            if (stack2.isEmpty()) {
                suffix[i] = (long) (n - i) * maxHeights.get(i);
            }else{
                suffix[i] = suffix[stack2.peek()] + (long) (stack2.peek() - i) * maxHeights.get(i);
            }
            stack2.push(i);
            ans = Math.max(ans, prefix[i] + suffix[i] - maxHeights.get(i));
        }
        return ans;
    }
}
```

739\. 每日温度
----------

给定一个整数数组 `temperatures` ，表示每天的温度，返回一个数组 `answer` ，其中 `answer[i]` 是指对于第 `i` 天，下一个更高温度出现在几天后。如果气温在这之后都不会升高，请在该位置用 `0` 来代替。

**示例 1:**

**输入:** `temperatures` = \[73,74,75,71,69,72,76,73\]
**输出:** \[1,1,4,2,1,1,0,0\]

**示例 2:**

**输入:** temperatures = \[30,40,50,60\]
**输出:** \[1,1,1,0\]

**示例 3:**

**输入:** temperatures = \[30,60,90\]
**输出:** \[1,1,0\]

**提示：**

*   `1 <= temperatures.length <= 105`
*   `30 <= temperatures[i] <= 100`

[https://leetcode.cn/problems/daily-temperatures/description/](https://leetcode.cn/problems/daily-temperatures/description/)

```java
class Solution {
        public int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        ArrayDeque<Integer> stack = new ArrayDeque<>(); // 维护一个单调递减的栈
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && temperatures[i] >= temperatures[stack.peek()]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                ans[i] = 0;
            } else {
                ans[i] = stack.peek() - i;
            }
            stack.push(i);
        }
        return ans;
    }
}
```

901\. 股票价格跨度
------------

设计一个算法收集某些股票的每日报价，并返回该股票当日价格的 **跨度** 。

当日股票价格的 **跨度** 被定义为股票价格小于或等于今天价格的最大连续日数（从今天开始往回数，包括今天）。

*   例如，如果未来 7 天股票的价格是 `[100,80,60,70,60,75,85]`，那么股票跨度将是 `[1,1,1,2,1,4,6]` 。


实现 `StockSpanner` 类：

*   `StockSpanner()` 初始化类对象。
*   `int next(int price)` 给出今天的股价 `price` ，返回该股票当日价格的 **跨度** 。

**示例：**

**输入**：
\["StockSpanner", "next", "next", "next", "next", "next", "next", "next"\]
\[\[\], \[100\], \[80\], \[60\], \[70\], \[60\], \[75\], \[85\]\]
**输出**：
\[null, 1, 1, 1, 2, 1, 4, 6\]

**解释：**
StockSpanner stockSpanner = new StockSpanner();
stockSpanner.next(100); // 返回 1
stockSpanner.next(80);  // 返回 1
stockSpanner.next(60);  // 返回 1
stockSpanner.next(70);  // 返回 2
stockSpanner.next(60);  // 返回 1
stockSpanner.next(75);  // 返回 4 ，因为截至今天的最后 4 个股价 (包括今天的股价 75) 都小于或等于今天的股价。
stockSpanner.next(85);  // 返回 6

 

**提示：**

*   `1 <= price <= 105`
*   最多调用 `next` 方法 `104` 次

[https://leetcode.cn/problems/online-stock-span/description/](https://leetcode.cn/problems/online-stock-span/description/)

```java
import java.util.ArrayDeque;

class StockSpanner {
    ArrayDeque<int[]> stack = new ArrayDeque<int[]>();
    int curDay = -1;
    public StockSpanner() {
        stack.push(new int[]{-1, Integer.MAX_VALUE});
    }
    public int next(int price) {
        while (price >= stack.peek()[1]) {
            stack.pop();
        }
        int ans = ++curDay - stack.peek()[0];
        stack.push(new int[]{curDay, price});
        return ans;
    }
}

/**
 * Your StockSpanner object will be instantiated and called as such:
 * StockSpanner obj = new StockSpanner();
 * int param_1 = obj.next(price);
 */
```

1124\. 表现良好的最长时间段
-----------------

给你一份工作时间表 `hours`，上面记录着某一位员工每天的工作小时数。

我们认为当员工一天中的工作小时数大于 `8` 小时的时候，那么这一天就是「**劳累的一天**」。

所谓「表现良好的时间段」，意味在这段时间内，「劳累的天数」是严格 **大于**「不劳累的天数」。

请你返回「表现良好时间段」的最大长度。

**示例 1：**

**输入：**hours = \[9,9,6,0,6,6,9\]
**输出：**3
**解释：**最长的表现良好时间段是 \[9,9,6\]。

**示例 2：**

**输入：**hours = \[6,6,6\]
**输出：**0

**提示：**

*   `1 <= hours.length <= 104`
*   `0 <= hours[i] <= 16`

[https://leetcode.cn/problems/longest-well-performing-interval/description/](https://leetcode.cn/problems/longest-well-performing-interval/description/)

```java
import java.util.ArrayDeque;

class Solution {
    public int longestWPI(int[] hours) {
        int n = hours.length, ans = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int[] t = new int[n + 1];
        stack.push(0);
        for (int i = 1; i <= n; i++) {
            t[i] += t[i - 1] + (hours[i-1] > 8 ? 1 : -1);
            if (t[i] < t[stack.peek()]) {
                stack.push(i);
            }
        }
        for (int i = n; i >=1; i--) {
            while (!stack.isEmpty() && t[stack.peek()] < t[i]) {
                ans = Math.max(ans, i - stack.pop());
            }
        }
        return ans;
    }
}
```

42\. 接雨水
--------

给定 `n` 个非负整数表示每个宽度为 `1` 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/10/22/rainwatertrap.png)

**输入：**height = \[0,1,0,2,1,0,1,3,2,1,2,1\]
**输出：**6
**解释：**上面是由数组 \[0,1,0,2,1,0,1,3,2,1,2,1\] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 

**示例 2：**

**输入：**height = \[4,2,0,3,2,5\]
**输出：**9

**提示：**

*   `n == height.length`
*   `1 <= n <= 2 * 104`
*   `0 <= height[i] <= 105`

[https://leetcode.cn/problems/trapping-rain-water/description/](https://leetcode.cn/problems/trapping-rain-water/description/)

```java
import java.util.ArrayDeque;

class Solution {
    public int trap(int[] height) {
        int n = height.length, ans = 0;
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                Integer pop = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int curWidth = i - stack.peek() - 1;
                int curHeight = Math.min(height[i], height[stack.peek()]) - height[pop];
                ans += curHeight * curWidth;
            }
            stack.push(i);
        }
        return ans;
    }
}
```

3113\. 边界元素是最大值的子数组数目
---------------------

给你一个 **正** 整数数组 `nums` 。

请你求出 `nums` 中有多少个子数组，满足子数组中 **第一个** 和 **最后一个** 元素都是这个子数组中的 **最大** 值。

**示例 1：**

**输入：**nums = \[1,4,3,3,2\]

**输出：**6

**解释：**

总共有 6 个子数组满足第一个元素和最后一个元素都是子数组中的最大值：

*   子数组 `[_**1**_,4,3,3,2]` ，最大元素为 1 ，第一个和最后一个元素都是 1 。
*   子数组 `[1,_**4**_,3,3,2]` ，最大元素为 4 ，第一个和最后一个元素都是 4 。
*   子数组 `[1,4,_**3**_,3,2]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。
*   子数组 `[1,4,3,_**3**_,2]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。
*   子数组 `[1,4,3,3,_**2**_]` ，最大元素为 2 ，第一个和最后一个元素都是 2 。
*   子数组 `[1,4,_**3,3**_,2]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。

所以我们返回 6 。

**示例 2：**

**输入：**nums = \[3,3,3\]

**输出：**6

**解释：**

总共有 6 个子数组满足第一个元素和最后一个元素都是子数组中的最大值：

*   子数组 `[_**3**_,3,3]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。
*   子数组 `[3,_**3**_,3]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。
*   子数组 `[3,3,_**3**_]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。
*   子数组 `[_**3,3**_,3]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。
*   子数组 `[3,_**3,3**_]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。
*   子数组 `[_**3,3,3**_]` ，最大元素为 3 ，第一个和最后一个元素都是 3 。

所以我们返回 6 。

**示例 3：**

**输入：**nums = \[1\]

**输出：**1

**解释：**

`nums` 中只有一个子数组 `[_**1**_]` ，最大元素为 1 ，第一个和最后一个元素都是 1 。

所以我们返回 1 。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i] <= 109`

[https://leetcode.cn/problems/find-the-number-of-subarrays-where-boundary-elements-are-maximum/description/](https://leetcode.cn/problems/find-the-number-of-subarrays-where-boundary-elements-are-maximum/description/)

```java
import java.util.ArrayDeque;

class Solution {
    public long numberOfSubarrays(int[] nums) {
        int n = nums.length;
        long ans = n;
        ArrayDeque<int[]> stack = new ArrayDeque<>();
        stack.offerLast(new int[]{Integer.MAX_VALUE, 1}); // 添加哨兵，可以不用判空
        for (int x : nums) {
            while (x > stack.peekLast()[0]) { // 如果当前元素大于栈底元素，那么前面的元素就没用了
                stack.pollLast();
            }
            if (x == stack.peekLast()[0]) {
                ans += stack.peekLast()[1]++;
            }else{ // 小于栈顶
                stack.offerLast(new int[]{x, 1});
            }
        }
        return ans;
    }
}
```

503\. 下一个更大元素 II(纯单调栈模板)
----------------

给定一个循环数组 `nums` （ `nums[nums.length - 1]` 的下一个元素是 `nums[0]` ），返回 _`nums` 中每个元素的 **下一个更大元素**_ 。

数字 `x` 的 **下一个更大的元素** 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 `-1` 。

**示例 1:**

**输入:** nums = \[1,2,1\]
**输出:** \[2,-1,2\]
**解释:** 第一个 1 的下一个更大的数是 2；
数字 2 找不到下一个更大的数； 
第二个 1 的下一个最大的数需要循环搜索，结果也是 2。

**示例 2:**

**输入:** nums = \[1,2,3,4,3\]
**输出:** \[2,3,4,-1,4\]

**提示:**

*   `1 <= nums.length <= 104`
*   `-109 <= nums[i] <= 109`

[https://leetcode.cn/problems/next-greater-element-ii/?envType=daily-question&envId=2024-06-24](https://leetcode.cn/problems/next-greater-element-ii/?envType=daily-question&envId=2024-06-24)

```java
import java.util.Arrays;
import java.util.Stack;

class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Stack<Integer> stack = new Stack<>(); // stack存储没有更新的元素下标，单调递减存储
        for (int i = 0; i < n * 2; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % n]) {
                int d = stack.pop();
                ans[d] = nums[i % n];
            }
            stack.push(i % n);
        }
        return ans;
    }
}
```

