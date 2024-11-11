# 字符串哈希

## 模板

[3213. 最小代价构造字符串](https://leetcode.cn/problems/construct-string-with-minimum-cost/) 【双字符串哈希模板】

```java
import java.util.*;


class Solution {
    public int minimumCost(String target, String[] words, int[] costs) {
        char[] t = target.toCharArray();
        int n = t.length;
        final int Mod1 = 1_070_777_777;
        final int Mod2 = 1_000_000_007;
        final int Base1 = (int) 8e8 + new Random().nextInt((int) 1e8);
        final int Base2 = (int) 8e8 + new Random().nextInt((int) 1e8);
        int[] powBase1 = new int[n + 1];
        int[] powBase2 = new int[n + 1];
        int[] preHash1 = new int[n + 1];
        int[] preHash2 = new int[n + 1];
        powBase1[0] = powBase2[0] = 1;
        for (int i = 0; i < n; i++) {
            powBase1[i + 1] = (int) ((long) powBase1[i] * Base1 % Mod1);
            powBase2[i + 1] = (int) ((long) powBase2[i] * Base2 % Mod2);
            preHash1[i + 1] = (int) (((long) preHash1[i] * Base1 + t[i]) % Mod1);
            preHash2[i + 1] = (int) (((long) preHash2[i] * Base2 + t[i]) % Mod2);
        }
        HashMap<Long, Integer> map = new HashMap<>();
        for (int i = 0; i < costs.length; i++) { // 构造字符串对应的hash值
            long h1 = 0, h2 = 0;
            for (char c : words[i].toCharArray()) {
                h1 = (h1 * Base1 + c) % Mod1;
                h2 = (h2 * Base2 + c) % Mod2;
            }
            map.merge(h1 << 32 | h2, costs[i], Integer::min);
        }

        HashSet<Integer> lengthSet = new HashSet<>();
        for (String w : words) {
            lengthSet.add(w.length());
        }
        int[] sortedLens = new int[lengthSet.size()];
        int k = 0;
        for (int len : lengthSet) {
            sortedLens[k++] = len;
        }
        Arrays.sort(sortedLens);

        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j : sortedLens) {
                if (j > i) {
                    break;
                }
                long subHash1 = ((preHash1[i] - (long) preHash1[i - j] * powBase1[j]) % Mod1 + Mod1) % Mod1;
                long subHash2 = ((preHash2[i] - (long) preHash2[i - j] * powBase2[j]) % Mod2 + Mod2) % Mod2;
                long subHash = subHash1 << 32 | subHash2;
                dp[i] = Math.min(dp[i], dp[i - j] + map.getOrDefault(subHash, Integer.MAX_VALUE / 2));
            }
        }
        return dp[n] == Integer.MAX_VALUE / 2 ? -1 : dp[n];
    }
}
```

## 问题

