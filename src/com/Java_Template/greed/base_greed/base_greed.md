[小红的字符串重排 ](https://ac.nowcoder.com/acm/contest/92662/E)

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

