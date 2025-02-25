## [1665. 完成所有任务的最少初始能量](https://leetcode.cn/problems/minimum-initial-energy-to-finish-tasks/)（模板题目）

给你一个任务数组 `tasks` ，其中 `tasks[i] = [actuali, minimumi]` ：

- `actuali` 是完成第 `i` 个任务 **需要耗费** 的实际能量。
- `minimumi` 是开始第 `i` 个任务前需要达到的最低能量。

比方说，如果任务为 `[10, 12]` 且你当前的能量为 `11` ，那么你不能开始这个任务。如果你当前的能量为 `13` ，你可以完成这个任务，且完成它后剩余能量为 `3` 。

你可以按照 **任意顺序** 完成任务。

请你返回完成所有任务的 **最少** 初始能量。

> **最好的严格证明：**
>
> ![1740385932280](assets/1740385932280.png)
>
> 其实在`max(minimum1,minimum2+actual1)` 与 `max(minimum2,minimum1+actual2)`比大小的时候，我们可以上减下得到`mininum2 + actual1 - (minimum1 + actual2)`得到`mininum2- actual2 -(mininum1 - actual1)`然后令`(mininum2- actual2) > (mininum1 - actual1)`

> **通俗的题解**：
>
> 纯例子讲解帮助不太明白的人感受到其中的道理；
>
> 例1：【5，5】【5，7】
>
> 定义：minimum-actual越小的人称为越老实的人；反之，称为装逼人。
> 讲解： 显然【5，5】是个老实人，【5，7】是个装逼人。老实人很实在，最后剩下5给他，他就会欣然接受，不需要额外的需求，但是装逼人不一样，他明明只要5，确装逼说自己要7，这个时候怎么办呢，拿什么东西来装逼呢，只能借一下老实人的来装一下逼！
>
> 我们明白，这个答案显然是10，装逼人【5，7】可以拿老实人的5给自己来装逼，所以在不改变结果的情况下他可以乱叫6，7，8，9，10，这些情况下答案都不会改变，一旦装逼人叫了【5，11】，就会发现老实人借给他的也不够他装逼了，无可奈何，我们只能给他分配额外资源让他装逼。
>
> 一旦相通这个之后，我们解题思路变成了：倒着安排，先把大量“老实人”放在前面把需求数字垫起来，这样后面的装逼人能装更大的逼，例2有两个老实人【5，5】【5，5】，装逼人就能叫道【5，15】，只要老实人还能借，他就还能装
>
> 下面的例子是举例让大家理解装逼程度一致，起始先后顺序是一样的
> 例3：【1，3】【3，5】 他们的老实程度一致 结果： 3（给第一个装逼） + 3（因为3+3>5） = 6 = 5(给第二个先装逼) + 1（5+ 1 > 3,够装逼了）
>
> 例4： 【1，4】【3，5】
> 大家自己思考一下这个就明白结果了，结果放在评论区，希望大家都能理解，喜欢的或者觉得有趣的不妨点个赞！！！！谢谢你们！
>
> 作者：你不懂cv
> 链接：https://leetcode.cn/problems/minimum-initial-energy-to-finish-tasks/solutions/757600/chun-li-zi-jiang-jie-bang-zhu-li-jie-lao-9fgq/
> 来源：力扣（LeetCode）
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

```java
import java.util.Arrays;

class Solution {
    public int minimumEffort(int[][] tasks) {
        int ans = 0, cur = 0, n = tasks.length;
        Arrays.sort(tasks, (a, b) -> (b[1] - b[0]) - (a[1] - a[0]));
        for (int i = 0; i < n; i++) {
            if (cur <= tasks[i][1]) {
                ans += tasks[i][1] - cur;
                cur = tasks[i][1];
            }
            cur -= tasks[i][0];
        }
        return ans;
    }
}
```

## [3273. 对 Bob 造成的最少伤害](https://leetcode.cn/problems/minimum-amount-of-damage-dealt-to-bob/)

给你一个整数 `power` 和两个整数数组 `damage` 和 `health` ，两个数组的长度都为 `n` 。

Bob 有 `n` 个敌人，如果第 `i` 个敌人还活着（也就是健康值 `health[i] > 0` 的时候），每秒钟会对 Bob 造成 `damage[i]` **点** 伤害。

每一秒中，在敌人对 Bob 造成伤害 **之后** ，Bob 会选择 **一个** 还活着的敌人进行攻击，该敌人的健康值减少 `power` 。

请你返回 Bob 将 **所有** `n` 个敌人都消灭之前，**最少** 会受到多少伤害。

> 题解：https://leetcode.cn/problems/minimum-amount-of-damage-dealt-to-bob/solutions/2899709/tan-xin-ji-qi-zheng-ming-lin-xiang-jiao-7lnjf/

```java
import java.util.Arrays;

class Solution {
    public long minDamage(int power, int[] damage, int[] health) {
        int n = damage.length;
        int[][] a = new int[n][2];
        for (int i = 0; i < n; i++) {
            a[i][0] = (health[i] + power - 1) / power;
            a[i][1] = damage[i];
        }
        Arrays.sort(a, (p, q) -> p[0] * q[1] - q[0] * p[1]);
        long ans = 0, s = 0;
        for (int[] p : a) {
            s += p[0];
            ans += s * p[1];
        }
        return ans;
    }
    
}
```

## [2136. 全部开花的最早一天](https://leetcode.cn/problems/earliest-possible-day-of-full-bloom/)

你有 `n` 枚花的种子。每枚种子必须先种下，才能开始生长、开花。播种需要时间，种子的生长也是如此。给你两个下标从 **0** 开始的整数数组 `plantTime` 和 `growTime` ，每个数组的长度都是 `n` ：

- `plantTime[i]` 是 **播种** 第 `i` 枚种子所需的 **完整天数** 。每天，你只能为播种某一枚种子而劳作。**无须** 连续几天都在种同一枚种子，但是种子播种必须在你工作的天数达到 `plantTime[i]` 之后才算完成。
- `growTime[i]` 是第 `i` 枚种子完全种下后生长所需的 **完整天数** 。在它生长的最后一天 **之后** ，将会开花并且永远 **绽放** 。

从第 `0` 开始，你可以按 **任意** 顺序播种种子。

返回所有种子都开花的 **最早** 一天是第几天。

> 题解：https://leetcode.cn/problems/earliest-possible-day-of-full-bloom/description/

```java
import java.util.Arrays;

class Solution {
    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int n = plantTime.length;
        Integer[] ids = new Integer[n];
        Arrays.setAll(ids, i -> i);
        Arrays.sort(ids, (a, b) -> growTime[b] - growTime[a]);
        int ans = 0, cur = 0;
        // System.out.println(Arrays.toString(ids));
        for (int i = 0; i < n; i++) {
            int id = ids[i];
            cur += plantTime[id];
            ans = Math.max(ans, cur + growTime[id]);
        }
        return ans;
    }
}
```

## [小红的字符串重排 ](https://ac.nowcoder.com/acm/contest/92662/E)

```java
  	private static void solve() throws IOException {
        cs = sc.next().toCharArray();
        n = cs.length;
        int mx = 0;
        int[] cnt = new int[26];
        for (char c : cs) {
            mx = Math.max(mx, ++cnt[c - 'a']);
        }
        if (mx * 2 > n) {
            sc.println(-1);
        } else {
            Integer[] idx = new Integer[n];
            Arrays.setAll(idx, i -> i);
            Arrays.sort(idx, (a, b) -> cs[a] - cs[b]);
            char[] ans = new char[n];
            for (int i = 0; i < n; i++) {
                ans[idx[i]] = cs[idx[(i + mx) % n]]; // 这里特别难想，idx[(i+mx)%n]对应的字符一定与idx[i]对应的字符不一样
            }
            sc.println(new String(ans));
        }
    }
```

## [录入成绩](https://www.lanqiao.cn/problems/20092/learning/?contest_id=228)

> 脑筋急转弯
>
> 蓝桥杯全国总决赛的颁奖典礼结束后，小蓝被分配了一个任务——录入部分获奖选手的奖项信息。
>
> 他用 "G"、"G1"、"G2"、"G3"、"GG"、"1"、"2"、"3" 这些字符串分别表示国特、国一、国二、国三、国优、省一、省二、省三等级。为了提高效率，小蓝写了个 Python 脚本来自动录入这些字符串。但是，小蓝过于粗心，竟忘记在各个奖项代码之间加分隔符！这就导致运行完脚本后，所有的奖项信息都挤在一起变成了一串长长的字符串，例如 "GG123G1G2G3123G1"。
>
> 小蓝的头发都快掉光了！他知道这部分选手中，每个奖项都至少有一位获奖选手，且国特只有一位。现在，他对着这串乱糟糟的字符串 SS，想知道这部分选手中最多可能有多少位选手获得了国一("G1")。
>
> 对此，请你帮帮可怜的小蓝，找出字符串 SS 中最多可能有多少个 "G1"。

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final int INF = 0x3f3f3f3f;
    private static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        int lens = s.length();

        int ans = 0;
        for (int i = 0; i < lens; i++) {
            if (s.charAt(i) == 'G') {
                ans = Math.max(ans, check(s, lens, i));
            }
        }

        System.out.println(ans);
    }

    private static int check(String s, int lens, int idx) {
        Map<String, Integer> cnt = new HashMap<>();
        for (int i = 0; i < lens; i++) {
            if (i == idx) continue;
            if (s.charAt(i) == 'G' && (i == lens - 1 || (i + 1) == idx)) return -1; // 保证一个国特

            if (s.charAt(i) == 'G') {
                cnt.merge(s.substring(i, i + 2), 1, Integer::sum);
                i++;
            } else {
                cnt.merge(String.valueOf(s.charAt(i)), 1, Integer::sum);
            }
        }

        return cnt.size() == 7 ? cnt.getOrDefault("G1", -1) : -1;
    }
}
```

