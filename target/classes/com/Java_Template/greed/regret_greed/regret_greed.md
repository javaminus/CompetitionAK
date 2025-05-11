2813\. 子序列最大优雅度（反悔贪心）
---------------

给你一个长度为 `n` 的二维整数数组 `items` 和一个整数 `k` 。

`items[i] = [profiti, categoryi]`，其中 `profiti` 和 `categoryi` 分别表示第 `i` 个项目的利润和类别。

现定义 `items` 的 **子序列** 的 **优雅度** 可以用 `total_profit + distinct_categories2` 计算，其中 `total_profit` 是子序列中所有项目的利润总和，`distinct_categories` 是所选子序列所含的所有类别中不同类别的数量。

你的任务是从 `items` 所有长度为 `k` 的子序列中，找出 **最大优雅度** 。

用整数形式表示并返回 `items` 中所有长度恰好为 `k` 的子序列的最大优雅度。

**注意：**数组的子序列是经由原数组删除一些元素（可能不删除）而产生的新数组，且删除不改变其余元素相对顺序。

**示例 1：**

**输入：**items = \[\[3,2\],\[5,1\],\[10,1\]\], k = 2
**输出：**17
**解释：**
在这个例子中，我们需要选出长度为 2 的子序列。
其中一种方案是 items\[0\] = \[3,2\] 和 items\[2\] = \[10,1\] 。
子序列的总利润为 3 + 10 = 13 ，子序列包含 2 种不同类别 \[2,1\] 。
因此，优雅度为 13 + 22 = 17 ，可以证明 17 是可以获得的最大优雅度。 

**示例 2：**

**输入：**items = \[\[3,1\],\[3,1\],\[2,2\],\[5,3\]\], k = 3
**输出：**19
**解释：**
在这个例子中，我们需要选出长度为 3 的子序列。 
其中一种方案是 items\[0\] = \[3,1\] ，items\[2\] = \[2,2\] 和 items\[3\] = \[5,3\] 。
子序列的总利润为 3 + 2 + 5 = 10 ，子序列包含 3 种不同类别 \[1, 2, 3\] 。 
因此，优雅度为 10 + 32 = 19 ，可以证明 19 是可以获得的最大优雅度。

**示例 3：**

**输入：**items = \[\[1,1\],\[2,1\],\[3,1\]\], k = 3
**输出：**7
**解释：**
在这个例子中，我们需要选出长度为 3 的子序列。
我们需要选中所有项目。
子序列的总利润为 1 + 2 + 3 = 6，子序列包含 1 种不同类别 \[1\] 。
因此，最大优雅度为 6 + 12 = 7 。

**提示：**

*   `1 <= items.length == n <= 105`
*   `items[i].length == 2`
*   `items[i][0] == profiti`
*   `items[i][1] == categoryi`
*   `1 <= profiti <= 109`
*   `1 <= categoryi <= n`
*   `1 <= k <= n`

[https://leetcode.cn/problems/maximum-elegance-of-a-k-length-subsequence/description/?envType=daily-question&envId=2024-06-13](https://leetcode.cn/problems/maximum-elegance-of-a-k-length-subsequence/description/?envType=daily-question&envId=2024-06-13)

```java
import java.util.*;

class Solution {
    public long findMaximumElegance(int[][] items, int k) {
        // 把利润从大到小排序
        Arrays.sort(items, (a, b) -> b[0] - a[0]);
        long ans = 0;
        long totalProfit = 0;
        Set<Integer> vis = new HashSet<>();
        Deque<Integer> duplicate = new ArrayDeque<>(); // 重复类别的利润
        for (int i = 0; i < items.length; i++) {
            int profit = items[i][0];
            int category = items[i][1];
            if (i < k) {
                totalProfit += profit; // 累加前 k 个项目的利润
                if (!vis.add(category)) { // 重复类别
                    duplicate.offerFirst(profit);
                }
            } else if (!duplicate.isEmpty() && vis.add(category)) { // 之前没有的类别
                totalProfit += profit - duplicate.pollFirst(); // 选一个重复类别中的最小利润替换
            } // else：比前面的利润小，而且类别还重复了，选它只会让 totalProfit 变小，vis.size() 不变，优雅度不会变大
            ans = Math.max(ans, totalProfit + (long) vis.size() * vis.size()); // 注意 1e5*1e5 会溢出
        }
        return ans;
    }
}

```

## [小红的双生数](https://ac.nowcoder.com/acm/contest/99784/D)

> 题意：小红定义一个正整数是“双生数”，当且仅当该正整数的每个数位的相邻数位中，恰好有一个和该数位的数字相同。   现在小红拿到了一个正整数 x，她希望你求出不小于 x 的最小“双生数”。 
>

```java
    private static int idx;
    private static int[] a, ans;
    static void solve() throws IOException {
        String s = sc.next();
        n = s.length();
        if ((n & 1) == 1) {
            for (int i = 0; i <= n / 2; i++) {
                if ((i & 1) == 1) {
                    sc.print("00");
                } else {
                    sc.print("11");
                }
            }
        } else {
            a = new int[n + 1];
            ans = new int[n + 1];
            for (int i = 1; i <= n; i++) {
                a[i] = s.charAt(i - 1) - '0';
            }
            dfs(1);
            if (idx == 0) {
                sc.print("11");
            }
            for (int i = 1; i <= idx; i++) {
                sc.print(String.valueOf(ans[i]));
            }
            for (int i = idx + 1, j = 0; i <= n; i += 2, j ^= 1) {
                sc.print(j + String.valueOf(j));
            }
        }
    }
    static void dfs(int pos) {
        int val = a[pos] * 10 + a[pos + 1];
        for (int i = a[pos]; i <= 9; i++) {
            ans[pos] = ans[pos + 1] = i;
            if (i == ans[pos - 1] || i * 11 < val) continue;
            if (i * 11 == val) {
                if (pos == n - 1) {
                    idx = n;
                    return;
                }
                dfs(pos + 2);
            }
            if (idx != 0) return;
            if (i * 11 > val) {
                idx = pos + 1;
                return;
            }
        }
    }
```



## [1642. 可以到达的最远建筑](https://leetcode.cn/problems/furthest-building-you-can-reach/)(贪心)

> 题意：有一堆建筑`height`，你有`a`个梯子，`b`个砖块，求最远可以到达的建筑下标。
>
> 梯子：可以从任意`height[i]` 跳到`height[i - 1]`。
>
> 砖块：需要往上跳多少就要垫多少砖块。

```java
class Solution {
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        // 尽量让梯子作用于落差最大的地方
        int n = heights.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long sum = 0;
        for (int i = 1; i < n; i++) {
            int d = heights[i] - heights[i - 1];
            if (d > 0) {
                pq.offer(d);
                if (pq.size() > ladders) {
                    sum += pq.poll();
                }
                if (sum > bricks) {
                    return i - 1;
                }
            }
        }
        return n - 1;
    }
}
```

## [630. 课程表 III](https://leetcode.cn/problems/course-schedule-iii/) （反悔贪心）

> 题意：给你一个数组 `courses` ，其中 `courses[i] = [durationi, lastDayi]` 表示第 `i` 门课将会 **持续** 上 `durationi` 天课，并且必须在不晚于 `lastDayi` 的时候完成。 
>
> 返回你最多可以修读的课程数目。

```java
class Solution {
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (a, b) -> a[1] - b[1]); // 按照 lastDay 从小到大排序
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a); // 最大堆
        int day = 0; // 已消耗时间
        for (int[] c : courses) {
            int duration = c[0];
            int lastDay = c[1];
            if (day + duration <= lastDay) { // 没有超过 lastDay，直接学习
                day += duration;
                pq.offer(duration);
            } else if (!pq.isEmpty() && duration < pq.peek()) { // 该课程的时间比之前的最长时间要短
                // 反悔，撤销之前 duration 最长的课程，改为学习该课程
                // 节省出来的时间，能在后面上完更多的课程
                day -= pq.poll() - duration;
                pq.offer(duration);
            }
        }
        return pq.size();
    }
}
```

# CF1526C2 Potions (Hard Version)【1600】

> 给你一个长度为 n 的序列 A，要求你找出最长的一个子序列使得这个`子序列`任意前缀和都非负。 

```java
public class Main{
    public static void solve() throws IOException {
		int n = sc.nextInt();;
		int[] a = new int[n];
		ss = sc.nextLine().split(" ");
		for(int i = 0;i<n;i++) {
			a[i]  =Integer.parseInt(ss[i]);
		}
		PriorityQueue<Integer> pq = new PriorityQueue<>();
		long sum = 0;
		int res = 0, len = 0;
		for(int i = 0;i<n;i++) {
			sum+=a[i];
			pq.offer(a[i]);
			while(sum<0 && !pq.isEmpty()) {
				sum-=pq.poll();
				len--;
			}
			len++;
			res = Math.max(res, len);
		}
		sc.print(res+"\n");
	}
}
```

