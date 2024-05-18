# §2.2 组合计数

1359\. 有效的快递序列数目
----------------

给你 `n` 笔订单，每笔订单都需要快递服务。

计算所有有效的 取货 / 交付 可能的顺序，使 delivery(i) 总是在 pickup(i) 之后。

由于答案可能很大，请返回答案对 10^9 + 7 取余的结果。

**示例 1：**

**输入：**n = 1
**输出：**1
**解释：**只有一种序列 (P1, D1)，物品 1 的配送服务（D1）在物品 1 的收件服务（P1）后。

**示例 2：**

**输入：**n = 2
**输出：**6
**解释：**所有可能的序列包括：
(P1,P2,D1,D2)，(P1,P2,D2,D1)，(P1,D1,P2,D2)，(P2,P1,D1,D2)，(P2,P1,D2,D1) 和 (P2,D2,P1,D1)。
(P1,D2,P2,D1) 是一个无效的序列，因为物品 2 的收件服务（P2）不应在物品 2 的配送服务（D2）之后。

**示例 3：**

**输入：**n = 3
**输出：**90

**提示：**

*   `1 <= n <= 500`

[https://leetcode.cn/problems/count-all-valid-pickup-and-delivery-options/description/](https://leetcode.cn/problems/count-all-valid-pickup-and-delivery-options/description/)

```java
class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int countOrders(int n) {
        long ans = 1;
        for (long i = 2; i <= n; i++) {
            ans = ans * (2*i - 1)  % Mod * i % Mod;
        }
        return (int) ans;
    }
}
```

2400\. 恰好移动 k 步到达某一位置的方法数目
--------------------------

给你两个 **正** 整数 `startPos` 和 `endPos` 。最初，你站在 **无限** 数轴上位置 `startPos` 处。在一步移动中，你可以向左或者向右移动一个位置。

给你一个正整数 `k` ，返回从 `startPos` 出发、**恰好** 移动 `k` 步并到达 `endPos` 的 **不同** 方法数目。由于答案可能会很大，返回对 `109 + 7` **取余** 的结果。

如果所执行移动的顺序不完全相同，则认为两种方法不同。

**注意：**数轴包含负整数**。**

**示例 1：**

**输入：**startPos = 1, endPos = 2, k = 3
**输出：**3
**解释：**存在 3 种从 1 到 2 且恰好移动 3 步的方法：
- 1 -> 2 -> 3 -> 2.
- 1 -> 2 -> 1 -> 2.
- 1 -> 0 -> 1 -> 2.
  可以证明不存在其他方法，所以返回 3 。

**示例 2：**

**输入：**startPos = 2, endPos = 5, k = 10
**输出：**0
**解释：**不存在从 2 到 5 且恰好移动 10 步的方法。

**提示：**

*   `1 <= startPos, endPos, k <= 1000`

[https://leetcode.cn/problems/number-of-ways-to-reach-a-position-after-exactly-k-steps/description/](https://leetcode.cn/problems/number-of-ways-to-reach-a-position-after-exactly-k-steps/description/)

```java
import java.util.HashMap;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    private HashMap<String, Integer> memo;
    public int numberOfWays(int startPos, int endPos, int k) { // 只会dfs哈哈哈
        if (k < Math.abs(endPos - startPos)) {
            return 0;
        }
        memo = new HashMap();
        return dfs(startPos, endPos, k);
    }

    private int dfs(int curPos, int endPos,int surplus) {
        if (curPos == endPos && surplus == 0) {
            return 1;
        }
        if (surplus == 0) {
            return 0;
        }
        String key = curPos + " " + surplus;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }
        int ans = (dfs(curPos + 1, endPos, surplus - 1) + dfs(curPos - 1, endPos, surplus - 1)) % Mod;
        memo.put(key, ans);
        return ans;
    }
}
```

> $$
> 组合数递推公式：C_{i}^{j}=C_{i-1}^{j}+C_{i-1}^{j-1}
> $$
>

