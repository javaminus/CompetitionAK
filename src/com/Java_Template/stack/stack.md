2589\. 完成所有任务的最少时间(栈+贪心天花板)
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

[https://leetcode.cn/problems/minimum-time-to-complete-all-tasks/description/?envType=daily-question&envId=2024-05-15](https://leetcode.cn/problems/minimum-time-to-complete-all-tasks/description/?envType=daily-question&envId=2024-05-15)

```java
// 暴力写法 
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
class Solution { // 看不懂，烦，，，，，
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

316\. 去除重复字母(经典模板题)
------------

给你一个字符串 `s` ，请你去除字符串中重复的字母，使得每个字母只出现一次。需保证 **返回结果的**

**字典序最小**（要求不能打乱其他字符的相对位置）。

**示例 1：**

**输入：**`s = "bcabc"`
**输出`：`**`"abc"`

**示例 2：**

**输入：**`s = "cbacdcbc"`
**输出：**`"acdb"`

**提示：**

*   `1 <= s.length <= 104`
*   `s` 由小写英文字母组成

**注意：**该题与 1081 [https://leetcode-cn.com/problems/smallest-subsequence-of-distinct-characters](https://leetcode-cn.com/problems/smallest-subsequence-of-distinct-characters) 相同

[https://leetcode.cn/problems/remove-duplicate-letters/description/](https://leetcode.cn/problems/remove-duplicate-letters/description/)

```java
import java.util.Stack;

class Solution {
    public String removeDuplicateLetters(String s) {
        char[] cs = s.toCharArray();
        int[] cnt = new int[26];
        for (char c : cs) {
            cnt[c - 'a']++;
        }
        Stack<Character> stack = new Stack<>();
        boolean[] inStack = new boolean[26]; // 表示元素已经出现在ans中了
        for (char c : cs) {
            cnt[c - 'a']--;
            if (inStack[c - 'a']) {
                continue;
            }
            while (!stack.isEmpty() && stack.peek() > c && cnt[stack.peek() - 'a'] > 0) {
                inStack[stack.pop() - 'a'] = false;
            }
            stack.push(c);
            inStack[c - 'a'] = true;
        }
        StringBuilder ans = new StringBuilder();
        while (!stack.isEmpty()) {
            ans.append(stack.pop());
        }
        return ans.reverse().toString();
    }
}
```

