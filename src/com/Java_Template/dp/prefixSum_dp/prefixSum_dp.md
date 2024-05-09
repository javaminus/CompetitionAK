2327\. 知道秘密的人数
--------------

在第 `1` 天，有一个人发现了一个秘密。

给你一个整数 `delay` ，表示每个人会在发现秘密后的 `delay` 天之后，**每天** 给一个新的人 **分享** 秘密。同时给你一个整数 `forget` ，表示每个人在发现秘密 `forget` 天之后会 **忘记** 这个秘密。一个人 **不能** 在忘记秘密那一天及之后的日子里分享秘密。

给你一个整数 `n` ，请你返回在第 `n` 天结束时，知道秘密的人数。由于答案可能会很大，请你将结果对 `109 + 7` **取余** 后返回。

**示例 1：**

**输入：**n = 6, delay = 2, forget = 4
**输出：**5
**解释：**
第 1 天：假设第一个人叫 A 。（一个人知道秘密）
第 2 天：A 是唯一一个知道秘密的人。（一个人知道秘密）
第 3 天：A 把秘密分享给 B 。（两个人知道秘密）
第 4 天：A 把秘密分享给一个新的人 C 。（三个人知道秘密）
第 5 天：A 忘记了秘密，B 把秘密分享给一个新的人 D 。（三个人知道秘密）
第 6 天：B 把秘密分享给 E，C 把秘密分享给 F 。（五个人知道秘密）

**示例 2：**

**输入：**n = 4, delay = 1, forget = 3
**输出：**6
**解释：**
第 1 天：第一个知道秘密的人为 A 。（一个人知道秘密）
第 2 天：A 把秘密分享给 B 。（两个人知道秘密）
第 3 天：A 和 B 把秘密分享给 2 个新的人 C 和 D 。（四个人知道秘密）
第 4 天：A 忘记了秘密，B、C、D 分别分享给 3 个新的人。（六个人知道秘密）

**提示：**

*   `2 <= n <= 1000`
*   `1 <= delay < forget <= n`

[https://leetcode.cn/problems/number-of-people-aware-of-a-secret/](https://leetcode.cn/problems/number-of-people-aware-of-a-secret/)

```java
import java.util.Arrays;

class Solution {
    int n,delay, forget;
    long[] memo;
    int Mod = (int) 1e9 + 7;
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        this.n = n;
        this.delay = delay;
        this.forget = forget;
        memo = new long[n + 1];
        Arrays.fill(memo, -1);
        return (int) dfs(1);
    }

    private long dfs(int i) {
        if (i + delay > n) {
            return 1; // 第i+delay天才能传播，说明只有自己知道
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int begin = i + delay, end = Math.min(i + forget, n+1); // 可以传播秘密的天数
        long ans = i + forget <= n ? 0 : 1; // 自身到[i+forget]就忘记了,在n天内忘记了取0,反之取1
        // 合法的传播时间
        for (int j = begin; j < end; j++) {
            memo[j] = dfs(j);
            ans = (ans + memo[j]) % Mod;
        }
        return ans;
    }
}
```

1997\. 访问完所有房间的第一天
------------------

你需要访问 `n` 个房间，房间从 `0` 到 `n - 1` 编号。同时，每一天都有一个日期编号，从 `0` 开始，依天数递增。你每天都会访问一个房间。

最开始的第 `0` 天，你访问 `0` 号房间。给你一个长度为 `n` 且 **下标从 0 开始** 的数组 `nextVisit` 。在接下来的几天中，你访问房间的 **次序** 将根据下面的 **规则** 决定：

*   假设某一天，你访问 `i` 号房间。
*   如果算上本次访问，访问 `i` 号房间的次数为 **奇数** ，那么 **第二天** 需要访问 `nextVisit[i]` 所指定的房间，其中 `0 <= nextVisit[i] <= i` 。
*   如果算上本次访问，访问 `i` 号房间的次数为 **偶数** ，那么 **第二天** 需要访问 `(i + 1) mod n` 号房间。

请返回你访问完所有房间的第一天的日期编号。题目数据保证总是存在这样的一天。由于答案可能很大，返回对 `109 + 7` 取余后的结果。

**示例 1：**

**输入：**nextVisit = \[0,0\]
**输出：**2
**解释：**
- 第 0 天，你访问房间 0 。访问 0 号房间的总次数为 1 ，次数为奇数。
    下一天你需要访问房间的编号是 nextVisit\[0\] = 0
- 第 1 天，你访问房间 0 。访问 0 号房间的总次数为 2 ，次数为偶数。
    下一天你需要访问房间的编号是 (0 + 1) mod 2 = 1
- 第 2 天，你访问房间 1 。这是你第一次完成访问所有房间的那天。

**示例 2：**

**输入：**nextVisit = \[0,0,2\]
**输出：**6
**解释：**
你每天访问房间的次序是 \[0,0,1,0,0,1,2,...\] 。
第 6 天是你访问完所有房间的第一天。

**示例 3：**

**输入：**nextVisit = \[0,1,2,0\]
**输出：**6
**解释：**
你每天访问房间的次序是 \[0,0,1,1,2,2,3,...\] 。
第 6 天是你访问完所有房间的第一天。

**提示：**

*   `n == nextVisit.length`
*   `2 <= n <= 105`
*   `0 <= nextVisit[i] <= i`

[https://leetcode.cn/problems/first-day-where-you-have-been-in-all-the-rooms/](https://leetcode.cn/problems/first-day-where-you-have-been-in-all-the-rooms/)

```java
import java.util.HashMap;

class Solution {
    HashMap<Integer, Integer> cnt = new HashMap<>();
    public int firstDayBeenInAllRooms(int[] nextVisit) { // 暴力解法，会爆内存
        return dfs(0, nextVisit) - 1;
    }
    
    private int dfs(int i,int[] nextVisit) { // i是房间号，dfs是天数
        if (cnt.size() == nextVisit.length) {
            return 0;
        }
        cnt.put(i, this.cnt.getOrDefault(i, 0) + 1);
        int c = cnt.get(i);
        int ans = 0;
        if (c % 2 == 1) {
            ans += dfs(nextVisit[i], nextVisit) + 1;
        }else{
            ans += dfs((i + 1) % nextVisit.length, nextVisit) + 1;
        }
        return ans;
    }
}
```

```java
class Solution {
    private int Mod = (int) 1e9 + 7;
    public int firstDayBeenInAllRooms(int[] nextVisit) {
        int n = nextVisit.length;
        long[] prefixSum = new long[n]; // 定义 f[i] 表示从「访问到房间 i 且次数为奇数」到「访问到房间 i 且次数为偶数」所需要的天数。
        for (int i = 0; i < n - 1; i++) {
            int j = nextVisit[i];
            prefixSum[i + 1] = (prefixSum[i] * 2 - prefixSum[j] + 2 + Mod) % Mod;
        }
        return (int) prefixSum[n - 1];
    }
}
```

