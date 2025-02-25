## [1520. 最多的不重叠子字符串](https://leetcode.cn/problems/maximum-number-of-non-overlapping-substrings/)（模板）

> 题意：给你一个字符串，找最多数目的非空子字符串：
>
> ​	1、字串不重叠；
>
> ​	2、如果一个子字符串包含字符 `char` ，那么 `s` 中所有 `char` 字符都应该在这个子字符串中。

```java
// 由于只有26个字母，所以有pos[26][3] = new int[]{字母a, left, right}；然后做区间合并就解决了。

// 关键就是求pos，错误的做法：直接找字母char对应的最左与最右下标，这样显然不对，比如有 ['a', 3, 8]与['b', 5,9]这样在[3, 8]区间没有完全包含字母b。

// 正确的做法是构造有向图
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int n = s.length();
        List<Integer>[] pos = new List[26];
        Arrays.setAll(pos, e -> new ArrayList<>());
        for (int i = 0; i < n; i++) {
            pos[s.charAt(i) - 'a'].add(i);
        }
        List<Integer>[] g = new List[26];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int i = 0; i < 26; i++) {
            if (pos[i].isEmpty()) {
                continue;
            }
            int l = pos[i].get(0), r = pos[i].get(pos[i].size() - 1);
            for (int j = 0; j < 26; j++) {
                if (i == j) {
                    continue;
                }
                List<Integer> list2 = pos[j];
                int k = bs(list2, l);
                if (k < list2.size() && list2.get(k) < r) {
                    g[i].add(j);
                }
            }
        }
        List<int[]> intervals = new ArrayList<>();
        boolean[] vis = new boolean[26];
        for (int i = 0; i < 26; i++) {
            if (pos[i].isEmpty()) {
                continue;
            }
            Arrays.fill(vis, false);
            l = n;
            r = 0;
            dfs(i, g, pos, vis);
            intervals.add(new int[]{l, r});
        }
        intervals.sort((a, b) -> a[1] - b[1]);
        List<String> ans = new ArrayList<>();
        int preR = Integer.MIN_VALUE;
        for (int[] p : intervals) {
            int l = p[0], r = p[1];
            if (preR < l) {
                ans.add(s.substring(l, r + 1));
                preR = r;
            }
        }
        return ans;
    }

    
    int l, r;
    private void dfs(int x, List<Integer>[] g, List<Integer>[] pos, boolean[] vis) {
        vis[x] = true;
        l = Math.min(l, pos[x].get(0));
        r = Math.max(r, pos[x].get(pos[x].size() - 1));
        for (int y : g[x]) {
            if (!vis[y]) {
                dfs(y, g, pos, vis);
            }
        }
    }
    
    private int bs(List<Integer> list, int tar) {
        int l = 0, r = list.size() - 1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            if (list.get(mid) >= tar) {
                r = mid - 1;
            }else{
                l = mid + 1;
            }
        }
        return r + 1;
    }
}
```

