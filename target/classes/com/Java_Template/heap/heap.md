



`TreeMap` 是 Java 中的一种基于红黑树（Red-Black Tree）的实现，它是 `NavigableMap` 的实现类，按照键的自然顺序（或根据提供的 `Comparator` 按排序顺序）对键进行排序。`TreeMap` 提供了良好的性能，尤其适合需要保持排序顺序的场景。以下是 `TreeMap` 的 API 介绍：

### 构造方法
- **TreeMap()**：创建一个空的树映射，使用键的自然顺序进行排序。
- **TreeMap(Comparator<? super K> comparator)**：创建一个空的树映射，使用指定的比较器来排序键。
- **TreeMap(Map<? extends K, ? extends V> m)**：创建一个包含指定映射中所有映射关系的新树映射，使用键的自然顺序进行排序。
- **TreeMap(SortedMap<K, ? extends V> m)**：创建一个包含指定有序映射中所有映射关系的新树映射，使用相同的顺序。

### 常用方法
- **V put(K key, V value)**：将指定的值与此映射中的指定键相关联。
- **void putAll(Map<? extends K, ? extends V> map)**：将指定映射中的所有映射关系复制到此映射中。
- **V get(Object key)**：返回指定键所映射的值，如果此映射不包含该键的映射关系，则返回 `null`。
- **V remove(Object key)**：如果存在，则从映射中移除指定键的映射关系。
- **void clear()**：从映射中移除所有映射关系。
- **boolean containsKey(Object key)**：如果此映射包含指定键的映射关系，则返回 `true`。
- **boolean containsValue(Object value)**：如果此映射将一个或多个键映射到指定值，则返回 `true`。
- **int size()**：返回映射中的键值对数量。
- **boolean isEmpty()**：如果此映射不包含键值对，则返回 `true`。
- **Set<K> keySet()**：返回此映射中包含的键的有序 `Set` 视图。
- **Collection<V> values()**：返回此映射中包含的值的集合视图。
- **Set<Map.Entry<K, V>> entrySet()**：返回此映射中包含的映射关系的有序 `Set` 视图。
- **K firstKey()**：返回此映射的第一个（最低）键。
- **K lastKey()**：返回此映射的最后一个（最高）键。
- **Map.Entry<K, V> firstEntry()**：返回此映射的第一个（键最低）键值对，没有则返回 `null`。
- **Map.Entry<K, V> lastEntry()**：返回此映射的最后一个（键最高）键值对，没有则返回 `null`。
- **Map.Entry<K, V> pollFirstEntry()**：移除并返回此映射的第一个（键最低）键值对，没有则返回 `null`。
- **Map.Entry<K, V> pollLastEntry()**：移除并返回此映射的最后一个（键最高）键值对，没有则返回 `null`。
- **K lowerKey(K key)**：返回严格小于给定键的最大键；如果没有这样的键，则返回 `null`。
- **K floorKey(K key)**：返回小于或等于给定键的最大键；如果没有这样的键，则返回 `null`。
- **K ceilingKey(K key)**：返回大于或等于给定键的最小键；如果没有这样的键，则返回 `null`。
- **K higherKey(K key)**：返回严格大于给定键的最小键；如果没有这样的键，则返回 `null`。
- **Map.Entry<K, V> lowerEntry(K key)**：返回严格小于给定键的最大键值对，如果没有这样的键值对，则返回 `null`。
- **Map.Entry<K, V> floorEntry(K key)**：返回小于或等于给定键的最大键值对，如果没有这样的键值对，则返回 `null`。
- **Map.Entry<K, V> ceilingEntry(K key)**：返回大于或等于给定键的最小键值对，如果没有这样的键值对，则返回 `null`。
- **Map.Entry<K, V> higherEntry(K key)**：返回严格大于给定键的最小键值对，如果没有这样的键值对，则返回 `null`。
- **NavigableSet<K> navigableKeySet()**：返回一个包含此映射中所有键的 `NavigableSet` 视图。
- **NavigableMap<K, V> descendingMap()**：返回此映射中包含的映射关系的逆序视图。
- **NavigableSet<K> descendingKeySet()**：返回包含此映射中所有键的逆序 `NavigableSet` 视图。

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

## [ACM中的CM题 ](https://ac.nowcoder.com/acm/contest/88878/E)

502\. IPO
---------

假设 力扣（LeetCode）即将开始 **IPO** 。为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。 由于资源有限，它只能在 IPO 之前完成最多 `k` 个不同的项目。帮助 力扣 设计完成最多 `k` 个不同项目后得到最大总资本的方式。

给你 `n` 个项目。对于每个项目 `i` ，它都有一个纯利润 `profits[i]` ，和启动该项目需要的最小资本 `capital[i]` 。

最初，你的资本为 `w` 。当你完成一个项目时，你将获得纯利润，且利润将被添加到你的总资本中。

总而言之，从给定项目中选择 **最多** `k` 个不同项目的列表，以 **最大化最终资本** ，并输出最终可获得的最多资本。

答案保证在 32 位有符号整数范围内。

**示例 1：**

**输入：**k = 2, w = 0, profits = \[1,2,3\], capital = \[0,1,1\]
**输出：**4
**解释：**
由于你的初始资本为 0，你仅可以从 0 号项目开始。
在完成后，你将获得 1 的利润，你的总资本将变为 1。
此时你可以选择开始 1 号或 2 号项目。
由于你最多可以选择两个项目，所以你需要完成 2 号项目以获得最大的资本。
因此，输出最后最大化的资本，为 0 + 1 + 3 = 4。

**示例 2：**

**输入：**k = 3, w = 0, profits = \[1,2,3\], capital = \[0,1,2\]
**输出：**6

**提示：**

*   `1 <= k <= 105`
*   `0 <= w <= 109`
*   `n == profits.length`
*   `n == capital.length`
*   `1 <= n <= 105`
*   `0 <= profits[i] <= 104`
*   `0 <= capital[i] <= 109`

[https://leetcode.cn/problems/ipo/solutions/985099/gong-shui-san-xie-noxiang-xin-ke-xue-xi-fk1ra/?envType=study-plan-v2&envId=top-interview-150](https://leetcode.cn/problems/ipo/solutions/985099/gong-shui-san-xie-noxiang-xin-ke-xue-xi-fk1ra/?envType=study-plan-v2&envId=top-interview-150)

373\. 查找和最小的 K 对数字
------------------

给定两个以 **非递减顺序排列** 的整数数组 `nums1` 和 `nums2` , 以及一个整数 `k` 。

定义一对值 `(u,v)`，其中第一个元素来自 `nums1`，第二个元素来自 `nums2` 。

请找到和最小的 `k` 个数对 `(u1,v1)`,  `(u2,v2)`  ...  `(uk,vk)` 。

**示例 1:**

**输入:** nums1 = \[1,7,11\], nums2 = \[2,4,6\], k = 3
**输出:** \[1,2\],\[1,4\],\[1,6\]
**解释:** 返回序列中的前 3 对数：
     \[1,2\],\[1,4\],\[1,6\],\[7,2\],\[7,4\],\[11,2\],\[7,6\],\[11,4\],\[11,6\]

**示例 2:**

**输入:** nums1 = \[1,1,2\], nums2 = \[1,2,3\], k = 2
**输出:** \[1,1\],\[1,1\]
**解释:** 返回序列中的前 2 对数：
     \[1,1\],\[1,1\],\[1,2\],\[2,1\],\[1,2\],\[2,2\],\[1,3\],\[1,3\],\[2,3\]

**提示:**

*   `1 <= nums1.length, nums2.length <= 105`
*   `-109 <= nums1[i], nums2[i] <= 109`
*   `nums1` 和 `nums2` 均为 **升序排列**
*   `1 <= k <= 104`
*   `k <= nums1.length * nums2.length`

[https://leetcode.cn/problems/find-k-pairs-with-smallest-sums/solutions/?envType=study-plan-v2&envId=top-interview-150](https://leetcode.cn/problems/find-k-pairs-with-smallest-sums/solutions/?envType=study-plan-v2&envId=top-interview-150)

[Cidoai的可乐 ](https://ac.nowcoder.com/acm/contest/98241/E)

> 它有 n 个点，每个点都有点权 ai 和一个度数限制 di。它想将它们连成一棵树，其中一条边的边权为它的两个端点的点权较小值。它希望这棵树中，所有点的度数都不大于度数限制，且边权和最小。你需要输出这个边权和。 

```java
	 private static void solve() throws IOException {
        n = sc.nextInt();
        int[] a = new int[n];
        ss = sc.nextLine().split(" ");
        PriorityQueue<Integer> pq = new PriorityQueue<>((x, y) -> a[x] - a[y]);
        for (int i = 0; i < n; i++) {
            a[i] = Integer.parseInt(ss[i]);
            pq.offer(i);
        }
        int[] d = new int[n];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            d[i] = Integer.parseInt(ss[i]);
        }
        long ans = 0L;
        for (int i = 0; i < n - 1; i++) {
            Integer poll = pq.poll();
            ans += a[poll];
            d[poll]--;
            if (d[poll] > 0) {
                pq.add(poll);
            }
        }
        sc.print(ans);
    }

```

## [3266. K 次乘运算后的最终数组 II](https://leetcode.cn/problems/final-array-state-after-k-multiplication-operations-ii/description/?envType=daily-question&envId=2024-12-14)

给你一个整数数组 `nums` ，一个整数 `k`  和一个整数 `multiplier` 。

你需要对 `nums` 执行 `k` 次操作，每次操作中：

- 找到 `nums` 中的 **最小** 值 `x` ，如果存在多个最小值，选择最 **前面** 的一个。
- 将 `x` 替换为 `x * multiplier` 。

`k` 次操作以后，你需要将 `nums` 中每一个数值对 `109 + 7` 取余。

请你返回执行完 `k` 次乘运算以及取余运算之后，最终的 `nums` 数组。

```java
import java.util.PriorityQueue;

class Solution { // Long.compare()学会了！！！
    private static int Mod = (int) 1e9 + 7;
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        if (multiplier == 1) {
            return nums;
        }
        int n = nums.length;
        int mx = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));
        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, nums[i]);
            pq.offer(new long[]{nums[i], i});
        }
        // 模拟，直到堆顶是mx
        for (; k > 0 && pq.peek()[0] < mx; k--) {
            long[] poll = pq.poll();
            poll[0] *= multiplier;
            pq.offer(poll);
        }
        for (int i = 0; i < n; i++) {
            long[] poll = pq.poll();
            nums[(int) poll[1]] = (int) (poll[0] % Mod * power(multiplier, k / n + (i < k % n ? 1 : 0)) % Mod);
        }
        return nums;
    }

    private long power(long a, long b) { // 求 (a ^ b) % p
        int p = Mod;
        long ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans * a) % p;
            }
            a = (a * a) % p;
            b >>= 1;
        }
        return ans;
    }

}
```

## [855. 考场就座](https://leetcode.cn/problems/exam-room/) 

> 题意：每个学生都是社恐，希望坐在离其他人最远的位置 

```java
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

// 考虑到每次 seat() 时都需要找到最大距离的座位，我们可以使用有序集合来保存座位区间。有序集合的每个元素为一个二元组 (l,r)，表示 l 和 r 之间（不包括 l 和 r）的座位可以坐学生。初始时有序集合中只有一个元素 (−1,n)，表示 (−1,n) 之间的座位可以坐学生。另外，我们使用两个哈希表 left 和 right 来维护每个有学生的座位的左右邻居学生，方便我们在 leave(p) 时合并两个座位区间。

class ExamRoom {
    int n;
    private TreeSet<int[]> ts = new TreeSet<>((a, b) -> {
        int d1 = getDist(a), d2 = getDist(b);
        return d1 == d2 ? a[0] - b[0] : d2 - d1;
    });
    private Map<Integer, Integer> left = new HashMap<>();
    private Map<Integer, Integer> right = new HashMap<>();

    public ExamRoom(int n) {
        this.n = n;
        add(new int[]{-1, n});
    }

    public int seat() {
        int[] s = ts.first();
        int p = (s[0] + s[1]) >> 1;
        if (s[0] == -1) {
            p = 0;
        } else if (s[1] == n) {
            p = n - 1;
        }
        del(s);
        add(new int[]{s[0], p});
        add(new int[]{p, s[1]});
        return p;
    }

    public void leave(int p) {
        int l = left.get(p), r = right.get(p);
        del(new int[]{l, p});
        del(new int[] {l, p});
        del(new int[] {p, r});
        add(new int[] {l, r});
    }

    private void add(int[] d) {
        ts.add(d);
        left.put(d[1], d[0]);
        right.put(d[0], d[1]);
    }

    private void del(int[] d) {
        ts.remove(d);
        left.remove(d[1]);
        right.remove(d[0]);
    }
    
    private int getDist(int[] d) {
        int l = d[0], r = d[1];
        return l == -1 || r == n ? r - l - 1 : (r - l) >> 1;
    }
}

/**
 * Your ExamRoom object will be instantiated and called as such:
 * ExamRoom obj = new ExamRoom(n);
 * int param_1 = obj.seat();
 * obj.leave(p);
 */
```

