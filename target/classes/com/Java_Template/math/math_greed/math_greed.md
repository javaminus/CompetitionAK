1953\. 你可以工作的最大周数(经典)
-----------------

给你 `n` 个项目，编号从 `0` 到 `n - 1` 。同时给你一个整数数组 `milestones` ，其中每个 `milestones[i]` 表示第 `i` 个项目中的阶段任务数量。

你可以按下面两个规则参与项目中的工作：

*   每周，你将会完成 **某一个** 项目中的 **恰好一个** 阶段任务。你每周都 **必须** 工作。
*   在 **连续的** 两周中，你 **不能** 参与并完成同一个项目中的两个阶段任务。

一旦所有项目中的全部阶段任务都完成，或者仅剩余一个阶段任务都会导致你违反上面的规则，那么你将 **停止工作** 。注意，由于这些条件的限制，你可能无法完成所有阶段任务。

返回在不违反上面规则的情况下你 **最多** 能工作多少周。

**示例 1：**

**输入：**milestones = \[1,2,3\]
**输出：**6
**解释：**一种可能的情形是：
​​​​- 第 1 周，你参与并完成项目 0 中的一个阶段任务。
- 第 2 周，你参与并完成项目 2 中的一个阶段任务。
- 第 3 周，你参与并完成项目 1 中的一个阶段任务。
- 第 4 周，你参与并完成项目 2 中的一个阶段任务。
- 第 5 周，你参与并完成项目 1 中的一个阶段任务。
- 第 6 周，你参与并完成项目 2 中的一个阶段任务。
  总周数是 6 。

**示例 2：**

**输入：**milestones = \[5,2,1\]
**输出：**7
**解释：**一种可能的情形是：
- 第 1 周，你参与并完成项目 0 中的一个阶段任务。
- 第 2 周，你参与并完成项目 1 中的一个阶段任务。
- 第 3 周，你参与并完成项目 0 中的一个阶段任务。
- 第 4 周，你参与并完成项目 1 中的一个阶段任务。
- 第 5 周，你参与并完成项目 0 中的一个阶段任务。
- 第 6 周，你参与并完成项目 2 中的一个阶段任务。
- 第 7 周，你参与并完成项目 0 中的一个阶段任务。
  总周数是 7 。
  注意，你不能在第 8 周参与完成项目 0 中的最后一个阶段任务，因为这会违反规则。
  因此，项目 0 中会有一个阶段任务维持未完成状态。

**提示：**

*   `n == milestones.length`
*   `1 <= n <= 105`
*   `1 <= milestones[i] <= 109`

[https://leetcode.cn/problems/maximum-number-of-weeks-for-which-you-can-work/description/?envType=daily-question&envId=2024-05-16](https://leetcode.cn/problems/maximum-number-of-weeks-for-which-you-can-work/description/?envType=daily-question&envId=2024-05-16)

```java
class Solution {
    public long numberOfWeeks(int[] milestones) {
        long sum = 0, mx = 0;
        for (int mile : milestones) {
            sum += mile;
            mx = Math.max(mx, mile);
        }
        if (mx <= sum / 2) {
            return sum;
        }else{
            return (sum - mx) * 2 + 1;
        }
    }
}
```

3139\. 使数组中所有元素相等的最小开销(难)
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

2589\. 完成所有任务的最少时间
------------------

你有一台电脑，它可以 **同时** 运行无数个任务。给你一个二维整数数组 `tasks` ，其中 `tasks[i] = [starti, endi, durationi]` 表示第 `i` 个任务需要在 **闭区间** 时间段 `[starti, endi]` 内运行 `durationi` 个整数时间点（但不需要连续）。

当电脑需要运行任务时，你可以打开电脑，如果空闲时，你可以将电脑关闭。

请你返回完成所有任务的情况下，电脑最少需要运行多少秒。

**示例 1：**

**输入：**tasks = \[\[2,3,1\],\[4,5,1\],\[1,5,2\]\]
**输出：**2
**解释：**
- 第一个任务在闭区间 \[2, 2\] 运行。
- 第二个任务在闭区间 \[5, 5\] 运行。
- 第三个任务在闭区间 \[2, 2\] 和 \[5, 5\] 运行。
  电脑总共运行 2 个整数时间点。

**示例 2：**

**输入：**tasks = \[\[1,3,2\],\[2,5,3\],\[5,6,2\]\]
**输出：**4
**解释：**
- 第一个任务在闭区间 \[2, 3\] 运行
- 第二个任务在闭区间 \[2, 3\] 和 \[5, 5\] 运行。
- 第三个任务在闭区间 \[5, 6\] 运行。
  电脑总共运行 4 个整数时间点。

**提示：**

*   `1 <= tasks.length <= 2000`
*   `tasks[i].length == 3`
*   `1 <= starti, endi <= 2000`
*   `1 <= durationi <= endi - starti + 1`

[https://leetcode.cn/problems/minimum-time-to-complete-all-tasks/description/](https://leetcode.cn/problems/minimum-time-to-complete-all-tasks/description/)

```java
import java.util.Arrays;

class Solution {
    public int findMinimumTime(int[][] tasks) {
        Arrays.sort(tasks, (a, b) -> a[1] - b[1]);
        int mx = tasks[tasks.length - 1][1];
        int ans = 0;
        boolean[] run = new boolean[mx + 1];
        for (int[] task : tasks) {
            int start = task[0], end = task[1];
            int d = task[2];
            for (int i = start; i <= end; i++) {
                if (run[i]) {
                    d--;
                }
            }
            for (int i = end; d > 0; i--) {
                if (!run[i]) {
                    run[i] = true;
                    d--;
                    ans++;
                }
            }
        }
        return ans;
    }
}
```

```java
class Solution {
    public int findMinimumTime(int[][] tasks) {
        Arrays.sort(tasks, (a, b) -> a[1] - b[1]);
        List<int[]> stack = new ArrayList<int[]>();
        stack.add(new int[]{-1, -1, 0});
        for (int[] task : tasks) {
            int start = task[0], end = task[1], duration = task[2];
            int k = binarySearch(stack, start);
            duration -= stack.get(stack.size() - 1)[2] - stack.get(k - 1)[2];
            if (start <= stack.get(k - 1)[1]) {
                duration -= stack.get(k - 1)[1] - start + 1;
            }
            if (duration <= 0) {
                continue;
            }
            while (end - stack.get(stack.size() - 1)[1] <= duration) {
                duration += stack.get(stack.size() - 1)[1] - stack.get(stack.size() - 1)[0] + 1;
                stack.remove(stack.size() - 1);
            }
            stack.add(new int[]{end - duration + 1, end, stack.get(stack.size() - 1)[2] + duration});
        }
        return stack.get(stack.size() - 1)[2];
    }

    public int binarySearch(List<int[]> stack, int target) {
        int low = 0, high = stack.size();
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (stack.get(mid)[0] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}
```

826\. 安排工作以达到最大收益
-----------------

你有 `n` 个工作和 `m` 个工人。给定三个数组： `difficulty`, `profit` 和 `worker` ，其中:

*   `difficulty[i]` 表示第 `i` 个工作的难度，`profit[i]` 表示第 `i` 个工作的收益。
*   `worker[i]` 是第 `i` 个工人的能力，即该工人只能完成难度小于等于 `worker[i]` 的工作。

每个工人 **最多** 只能安排 **一个** 工作，但是一个工作可以 **完成多次** 。

*   举个例子，如果 3 个工人都尝试完成一份报酬为 `$1` 的同样工作，那么总收益为 `$3` 。如果一个工人不能完成任何工作，他的收益为 `$0` 。

返回 _在把工人分配到工作岗位后，我们所能获得的最大利润_ 。

**示例 1：**

**输入:** difficulty = \[2,4,6,8,10\], profit = \[10,20,30,40,50\], worker = \[4,5,6,7\]
**输出:** 100 
**解释:** 工人被分配的工作难度是 \[4,4,6,6\] ，分别获得 \[20,20,30,30\] 的收益。

**示例 2:**

**输入:** difficulty = \[85,47,57\], profit = \[24,66,99\], worker = \[40,25,25\]
**输出:** 0

**提示:**

*   `n == difficulty.length`
*   `n == profit.length`
*   `m == worker.length`
*   `1 <= n, m <= 104`
*   `1 <= difficulty[i], profit[i], worker[i] <= 105`

[https://leetcode.cn/problems/most-profit-assigning-work/description/?envType=daily-question&envId=2024-05-17](https://leetcode.cn/problems/most-profit-assigning-work/description/?envType=daily-question&envId=2024-05-17)

```java
import java.util.Arrays;

class Solution {
    public int maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int n = difficulty.length;
        Integer[] ids = new Integer[n];
        Arrays.setAll(ids, i -> i);
        Arrays.sort(ids, (i, j) -> difficulty[i] - difficulty[j]);
        Arrays.sort(worker);
        int ans = 0, j = 0;
        int maxProfit = 0;
        for (int w : worker) {
            for (; j < n && difficulty[ids[j]] <= w; j++) {
                maxProfit = Math.max(maxProfit, profit[ids[j]]);
            }
            ans += maxProfit;
        }
        return ans;
    }
}
```

