# 乘法原理

> x/2%Mod != x%Mod/2

2063\. 所有子字符串中的元音(贡献法)
-----------------

给你一个字符串 `word` ，返回 `word` 的所有子字符串中 **元音的总数** ，元音是指 `'a'`、`'e'`_、_`'i'`_、_`'o'` 和 `'u'` _。_

**子字符串** 是字符串中一个连续（非空）的字符序列。

**注意：**由于对 `word` 长度的限制比较宽松，答案可能超过有符号 32 位整数的范围。计算时需当心。

**示例 1：**

**输入：**word = "aba"
**输出：**6
**解释：**
所有子字符串是："a"、"ab"、"aba"、"b"、"ba" 和 "a" 。
- "b" 中有 0 个元音
- "a"、"ab"、"ba" 和 "a" 每个都有 1 个元音
- "aba" 中有 2 个元音
  因此，元音总数 = 0 + 1 + 1 + 1 + 1 + 2 = 6 。

**示例 2：**

**输入：**word = "abc"
**输出：**3
**解释：**
所有子字符串是："a"、"ab"、"abc"、"b"、"bc" 和 "c" 。
- "a"、"ab" 和 "abc" 每个都有 1 个元音
- "b"、"bc" 和 "c" 每个都有 0 个元音
  因此，元音总数 = 1 + 1 + 1 + 0 + 0 + 0 = 3 。

**示例 3：**

**输入：**word = "ltcd"
**输出：**0
**解释：**"ltcd" 的子字符串均不含元音。

**示例 4：**

**输入：**word = "noosabasboosa"
**输出：**237
**解释：**所有子字符串中共有 237 个元音。

**提示：**

*   `1 <= word.length <= 105`
*   `word` 由小写英文字母组成

[https://leetcode.cn/problems/vowels-of-all-substrings/](https://leetcode.cn/problems/vowels-of-all-substrings/)

```java
import java.util.HashSet;

class Solution {
    static HashSet<Character> set = new HashSet<>();
    static {
        set.add('a');
        set.add('e');
        set.add('i');
        set.add('o');
        set.add('u');
    }
    public long countVowels(String word) { // 遍历 word，若 word[i] 是元音，我们考察它能出现在多少个子字符串中。
        long ans = 0;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            if (set.contains(word.charAt(i))) {
                ans += (long) (i + 1) * (n - i);
            }
        }
        return ans;
    }
}
```

2963\. 统计好分割方案的数目
-----------------

给你一个下标从 **0** 开始、由 **正整数** 组成的数组 `nums`。

将数组分割成一个或多个 **连续** 子数组，如果不存在包含了相同数字的两个子数组，则认为是一种 **好分割方案** 。

返回 `nums` 的 **好分割方案** 的 **数目**。

由于答案可能很大，请返回答案对 `109 + 7` **取余** 的结果。

**示例 1：**

**输入：**nums = \[1,2,3,4\]
**输出：**8
**解释：**有 8 种 **好分割方案** ：(\[1\], \[2\], \[3\], \[4\]), (\[1\], \[2\], \[3,4\]), (\[1\], \[2,3\], \[4\]), (\[1\], \[2,3,4\]), (\[1,2\], \[3\], \[4\]), (\[1,2\], \[3,4\]), (\[1,2,3\], \[4\]) 和 (\[1,2,3,4\]) 。

**示例 2：**

**输入：**nums = \[1,1,1,1\]
**输出：**1
**解释：**唯一的 **好分割方案** 是：(\[1,1,1,1\]) 。

**示例 3：**

**输入：**nums = \[1,2,1,3\]
**输出：**2
**解释：**有 2 种 **好分割方案** ：(\[1,2,1\], \[3\]) 和 (\[1,2,1,3\]) 。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i] <= 109`

[https://leetcode.cn/problems/count-the-number-of-good-partitions/description/](https://leetcode.cn/problems/count-the-number-of-good-partitions/description/)

```java
import java.util.ArrayList;
import java.util.HashMap;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int numberOfGoodPartitions(int[] nums) { // O(nlogn)
        HashMap<Integer, int[]> map = new HashMap<>(); // <nums[i], {left,right}>
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            if (map.containsKey(x)) {
                map.get(x)[1] = i; // 更新区间右节点
            }else{
                map.put(x, new int[]{i, i});
            }
        }
        ArrayList<int[]> list = new ArrayList<>(map.values()); // map转list
        // 区间合并
        list.sort((a, b) -> a[0] - b[0]);
        int ans = 1;
        int maxR = list.get(0)[1];
        for (int i = 1; i < list.size(); i++) {
            int[] interval = list.get(i);
            int left = interval[0];
            int right = interval[1];
            if (left > maxR) { // 无法合并
                ans = ans * 2 % Mod;
            }
            maxR = Math.max(right, maxR);
        }
        return ans;
    }
}
```

```java
import java.util.HashMap;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int numberOfGoodPartitions(int[] nums) { // O（n）
        HashMap<Integer, Integer> right = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            right.put(nums[i], i);
        }
        int ans = 1, maxR = 0;
        for (int i = 0; i < nums.length - 1; i++) { // 少统计最后一段区间
            maxR = Math.max(maxR, right.get(nums[i]));
            if (maxR == i) {
                ans = ans * 2 % Mod;
            }
        }
        return ans;
    }
}
```

2867\. 统计树中的合法路径数目
------------------

给你一棵 `n` 个节点的无向树，节点编号为 `1` 到 `n` 。给你一个整数 `n` 和一个长度为 `n - 1` 的二维整数数组 `edges` ，其中 `edges[i] = [ui, vi]` 表示节点 `ui` 和 `vi` 在树中有一条边。

请你返回树中的 **合法路径数目** 。

如果在节点 `a` 到节点 `b` 之间 **恰好有一个** 节点的编号是质数，那么我们称路径 `(a, b)` 是 **合法的** 。

**注意：**

*   路径 `(a, b)` 指的是一条从节点 `a` 开始到节点 `b` 结束的一个节点序列，序列中的节点 **互不相同** ，且相邻节点之间在树上有一条边。
*   路径 `(a, b)` 和路径 `(b, a)` 视为 **同一条** 路径，且只计入答案 **一次** 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2023/08/27/example1.png)

**输入：**n = 5, edges = \[\[1,2\],\[1,3\],\[2,4\],\[2,5\]\]
**输出：**4
**解释：**恰好有一个质数编号的节点路径有：
- (1, 2) 因为路径 1 到 2 只包含一个质数 2 。
- (1, 3) 因为路径 1 到 3 只包含一个质数 3 。
- (1, 4) 因为路径 1 到 4 只包含一个质数 2 。
- (2, 4) 因为路径 2 到 4 只包含一个质数 2 。
  只有 4 条合法路径。

**示例 2：**

![](https://assets.leetcode.com/uploads/2023/08/27/example2.png)

**输入：**n = 6, edges = \[\[1,2\],\[1,3\],\[2,4\],\[3,5\],\[3,6\]\]
**输出：**6
**解释：**恰好有一个质数编号的节点路径有：
- (1, 2) 因为路径 1 到 2 只包含一个质数 2 。
- (1, 3) 因为路径 1 到 3 只包含一个质数 3 。
- (1, 4) 因为路径 1 到 4 只包含一个质数 2 。
- (1, 6) 因为路径 1 到 6 只包含一个质数 3 。
- (2, 4) 因为路径 2 到 4 只包含一个质数 2 。
- (3, 6) 因为路径 3 到 6 只包含一个质数 3 。
  只有 6 条合法路径。

**提示：**

*   `1 <= n <= 105`
*   `edges.length == n - 1`
*   `edges[i].length == 2`
*   `1 <= ui, vi <= n`
*   输入保证 `edges` 形成一棵合法的树。

[https://leetcode.cn/problems/count-valid-paths-in-a-tree/description/](https://leetcode.cn/problems/count-valid-paths-in-a-tree/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    private final static int mx = (int) 1e5;
    private final static boolean[] pn = new boolean[mx + 1];
    static {
        pn[1] = true;
        for (int i = 2; i * i <= mx; i++) {
            if (!pn[i]) {
                for (int j = i * i; j <= mx; j += i) {
                    pn[j] = true;
                }
            }
        }
    }

    public long countPaths(int n, int[][] edges) {
        List<Integer>[] g = new List[n + 1];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        long ans = 0;
        int[] size = new int[n + 1];
        ArrayList<Integer> nodes = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            // 从质数点出发
            if (pn[i]) {
                continue;
            }
            int sum = 0;
            for (int j : g[i]) {
                if (!pn[j]) {
                    continue;
                }
                if (size[j] == 0) {
                    nodes.clear();
                    dfs(g, nodes,j, -1);
                    for (int x : nodes) {
                        size[x] = nodes.size();
                    }
                }
                ans += (long) size[j] * sum;
                sum += size[j];
            }
            ans += sum;
        }
        return ans;
    }

    private void dfs(List<Integer>[] g, List<Integer> nodes, int x, int fa) {
        nodes.add(x);
        for (int y : g[x]) {
            if (y != fa && pn[y]) {
                dfs(g, nodes, y, x);
            }
        }
    }
}
```

