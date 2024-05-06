2462\. 雇佣 K 位工人的总代价
-------------------

给你一个下标从 **0** 开始的整数数组 `costs` ，其中 `costs[i]` 是雇佣第 `i` 位工人的代价。

同时给你两个整数 `k` 和 `candidates` 。我们想根据以下规则恰好雇佣 `k` 位工人：

*   总共进行 `k` 轮雇佣，且每一轮恰好雇佣一位工人。
*   在每一轮雇佣中，从最前面 `candidates` 和最后面 `candidates` 人中选出代价最小的一位工人，如果有多位代价相同且最小的工人，选择下标更小的一位工人。
    *   比方说，`costs = [3,2,7,7,1,2]` 且 `candidates = 2` ，第一轮雇佣中，我们选择第 `4` 位工人，因为他的代价最小 `[_3,2_,7,7,_**1**,2_]` 。
    *   第二轮雇佣，我们选择第 `1` 位工人，因为他们的代价与第 `4` 位工人一样都是最小代价，而且下标更小，`[_3,**2**_,7,_7,2_]` 。注意每一轮雇佣后，剩余工人的下标可能会发生变化。
*   如果剩余员工数目不足 `candidates` 人，那么下一轮雇佣他们中代价最小的一人，如果有多位代价相同且最小的工人，选择下标更小的一位工人。
*   一位工人只能被选择一次。

返回雇佣恰好 `k` 位工人的总代价。

**示例 1：**

**输入：**costs = \[17,12,10,2,7,2,11,20,8\], k = 3, candidates = 4
**输出：**11
**解释：**我们总共雇佣 3 位工人。总代价一开始为 0 。
- 第一轮雇佣，我们从 \[**_17,12,10,2_**,7,**_2,11,20,8_**\] 中选择。最小代价是 2 ，有两位工人，我们选择下标更小的一位工人，即第 3 位工人。总代价是 0 + 2 = 2 。
- 第二轮雇佣，我们从 \[**_17,12,10,7_**,**_2,11,20,8_**\] 中选择。最小代价是 2 ，下标为 4 ，总代价是 2 + 2 = 4 。
- 第三轮雇佣，我们从 \[**_17,12,10,7,11,20,8_**\] 中选择，最小代价是 7 ，下标为 3 ，总代价是 4 + 7 = 11 。注意下标为 3 的工人同时在最前面和最后面 4 位工人中。
  总雇佣代价是 11 。

**示例 2：**

**输入：**costs = \[1,2,4,1\], k = 3, candidates = 3
**输出：**4
**解释：**我们总共雇佣 3 位工人。总代价一开始为 0 。
- 第一轮雇佣，我们从 \[**_1,2,4,1_**\] 中选择。最小代价为 1 ，有两位工人，我们选择下标更小的一位工人，即第 0 位工人，总代价是 0 + 1 = 1 。注意，下标为 1 和 2 的工人同时在最前面和最后面 3 位工人中。
- 第二轮雇佣，我们从 \[**_2,4,1_**\] 中选择。最小代价为 1 ，下标为 2 ，总代价是 1 + 1 = 2 。
- 第三轮雇佣，少于 3 位工人，我们从剩余工人 \[**_2,4_**\] 中选择。最小代价是 2 ，下标为 0 。总代价为 2 + 2 = 4 。
  总雇佣代价是 4 。

**提示：**

*   `1 <= costs.length <= 105`
*   `1 <= costs[i] <= 105`
*   `1 <= k, candidates <= costs.length`

[https://leetcode.cn/problems/total-cost-to-hire-k-workers/description/?envType=daily-question&envId=2024-05-01](https://leetcode.cn/problems/total-cost-to-hire-k-workers/description/?envType=daily-question&envId=2024-05-01)

```java
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;

class Solution {
    public long totalCost(int[] costs, int k, int candidates) { // 复杂的解法
        int n = costs.length;
        long ans = 0;
        if (candidates * 2 + k > n) {
            Arrays.sort(costs);
            for (int i = 0; i < k; i++) {
                ans += costs[i];
            }
            return ans;
        }
        PriorityQueue<int[]> before = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        PriorityQueue<int[]> after = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        int i = 0, j = n - 1;
        for (; i < candidates; i++, j--) {
            before.offer(new int[]{costs[i], i});
            after.offer(new int[]{costs[n - 1 - i], n - 1 - i});
        }
        i--;
        j++;
        HashSet<Integer> removeSet = new HashSet<>();
        while (k > 0) {
            if (removeSet.contains(before.peek()[1])) {
                before.poll();
            }
            if (removeSet.contains(after.peek()[1])) {
                after.poll();
            }
            if (i < n && before.size() < candidates) {
                before.offer(new int[]{costs[i], i});
            }
            if (j >= 0 && after.size() < candidates) {
                after.offer(new int[]{costs[j], j});
            }
            if (before.peek()[0] <= after.peek()[0]) {
                ans += before.peek()[0];
                removeSet.add(before.peek()[1]);
                i++;
            }else{
                ans += after.peek()[0];
                removeSet.add(after.peek()[1]);
                j--;
            }
            k--;
        }
        return ans;
    }
}
```

```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public long totalCost(int[] costs, int k, int candidates) {
        int n = costs.length;
        long ans = 0;
        if (candidates * 2 + k > n) {
            Arrays.sort(costs);
            for (int i = 0; i < k; i++) {
                ans += costs[i];
            }
            return ans;
        }
        PriorityQueue<Integer> prefix = new PriorityQueue<>();
        PriorityQueue<Integer> suffix = new PriorityQueue<>();
        for (int i = 0; i < candidates; i++) {
            prefix.offer(costs[i]);
            suffix.offer(costs[n - 1 - i]);
        }
        int i = candidates;
        int j = n - 1 - candidates;
        while (k-- > 0) {
            if (prefix.peek() <= suffix.peek()) {
                ans += prefix.poll();
                prefix.offer(costs[i++]);
            }else{
                ans += suffix.poll();
                suffix.offer(costs[j--]);
            }
        }
        return ans;
    }
}
```

857\. 雇佣 K 名工人的最低成本
-------------------

有 `n` 名工人。 给定两个数组 `quality` 和 `wage` ，其中，`quality[i]` 表示第 `i` 名工人的工作质量，其最低期望工资为 `wage[i]` 。

现在我们想雇佣 `k` 名工人组成一个_工资组。_在雇佣 一组 `k` 名工人时，我们必须按照下述规则向他们支付工资：

1.  对工资组中的每名工人，应当按其工作质量与同组其他工人的工作质量的比例来支付工资。
2.  工资组中的每名工人至少应当得到他们的最低期望工资。

给定整数 `k` ，返回 _组成满足上述条件的付费群体所需的最小金额_ 。在实际答案的 `10-5` 以内的答案将被接受。。

**示例 1：**

**输入：** quality = \[10,20,5\], wage = \[70,50,30\], k = 2
**输出：** 105.00000
**解释：** 我们向 0 号工人支付 70，向 2 号工人支付 35。

**示例 2：**

**输入：** quality = \[3,1,10,10,1\], wage = \[4,8,2,2,7\], k = 3
**输出：** 30.66667
**解释：** 我们向 0 号工人支付 4，向 2 号和 3 号分别支付 13.33333。

**提示：**

*   `n == quality.length == wage.length`
*   `1 <= k <= n <= 104`
*   `1 <= quality[i], wage[i] <= 104`

[https://leetcode.cn/problems/minimum-cost-to-hire-k-workers/description/?envType=daily-question&envId=2024-05-02](https://leetcode.cn/problems/minimum-cost-to-hire-k-workers/description/?envType=daily-question&envId=2024-05-02)

```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    public double mincostToHireWorkers(int[] quality, int[] wage, int k) {
        int n = quality.length;
        Integer[] ids = new Integer[n];
        Arrays.setAll(ids, i -> i);
        // Arrays.sort(ids, (a, b) -> wage[a] / quality[a] - wage[b] / quality[b]);
        Arrays.sort(ids, (a, b) -> wage[a] * quality[b] - wage[b] * quality[a]);
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int sumQ = 0;
        for (int i = 0; i < k; i++) {
            pq.offer(quality[ids[i]]);
            sumQ += quality[ids[i]];
        }
        // 选择r值最小的k个人
        double ans = sumQ * ((double) wage[ids[k - 1]] / quality[ids[k - 1]]);
        // 后面的工人 r 值更大
        // 但是 sumQ 可以变小，从而可能得到更优的答案
        for (int i = k; i < n; i++) {
            int q = quality[ids[i]];
            if (q < pq.peek()) {
                sumQ = sumQ - pq.poll() + q;
                pq.offer(q);
                ans = Math.min(ans, sumQ * ((double) wage[ids[i]] / q));
            }
        }
        return ans;
    }
}
```

