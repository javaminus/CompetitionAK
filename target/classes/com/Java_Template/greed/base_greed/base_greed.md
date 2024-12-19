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

