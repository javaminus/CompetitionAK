## [2024/9/2](https://codeforces.com/problemset/problem/117/B)

[B. Very Interesting Game ](https://codeforces.com/problemset/problem/117/B)

```java
public class Main{
    private static void solve() throws IOException {
        int a = sc.nextInt(), b = sc.nextInt(), k = sc.nextInt();
        int p = (int) 1e9 % k;
        // a*p
        long x = 0;
        for (int i = 0; i <= Math.min(k, a); i++) {
            x = (long) i * p % k;
            if ((k - x) % k > b) {
                s = Integer.toString(i);
                StringBuilder ans = new StringBuilder();
                for (int j = 0; j < 9 - s.length(); j++) {
                    ans.append('0');
                }
                ans.append(s);
                sc.println("1 " + ans);
                return;
            }
        }
        sc.println(2);
    }
}
```

[C. Lucky Days ](https://codeforces.com/problemset/problem/1055/C)

```java
public class Main{ // 斐蜀定理
    private static void solve() throws IOException {
        ss = sc.nextLine().split(" ");
        int la = Integer.parseInt(ss[0]), ra = Integer.parseInt(ss[1]), ta = Integer.parseInt(ss[2]);
        ss = sc.nextLine().split(" ");
        int lb = Integer.parseInt(ss[0]), rb = Integer.parseInt(ss[1]), tb = Integer.parseInt(ss[2]);
        int lenA = ra - la + 1, lenB = rb - lb + 1, d = gcd(ta, tb);
        int x = la - lb; // 相对起点距离
        x = (x % d + d) % d;
        int ans = Math.max(0, Math.max(Math.min(x + lenA, lenB) - x, Math.min(x - d + lenA, lenB)));
        sc.println(ans);
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
```

## 2024/9/3

[D. Merge Sort ](https://codeforces.com/problemset/problem/873/D)

> 题意：给你两个整数n，k；构造一个n的排列，要求调用归并函数里的*mergesort* 的次数恰好为k次

知识点：分治、构造、1800

```java
public class Main{
    private static int n, k;
    
    private static void permute(List<Integer> list, int l, int r) {
        if (k == 0 || l + 1 == r) {
            return;
        }
        int mid = l + (r - l) / 2;
        k--;
        Collections.swap(list, mid, mid - 1); // 核心
        permute(list, l, mid);
        permute(list, mid, r);
    }

    private static void solve() throws IOException {
        n = sc.nextInt();
        k = sc.nextInt();
        if ((k & 1) == 0) {
            sc.println(-1);
            return;
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(i + 1);
        }
        k /= 2;
        permute(list, 0, n);
        if (k != 0) {
            sc.println(-1);
        }else{
            for (int x : list) {
                sc.print(x + " ");
            }
        }
    }
}
```

[D. Minimum path ](https://codeforces.com/contest/1031/problem/D)

> 题意：从左上角(0，0)走到右下角(n - 1, n - 1)，只能往右和往下走，路径长度为(2 * n - 1),  最多修改k个字母，构造最小字典序的路径

知识点：构造、1900、动态规划

```java
public class Main {
    public static void main(String[] args) throws IOException {
        // int T = sc.nextInt();
        while (T-- > 0) {
            solve();
            // sc.bw.flush();
        }
        sc.bw.flush();
        sc.bw.close();
    }

    private static String[] ss;
    private static String s;
    static char[][] cs;
    static int n, k;
    static ArrayList<int[]> tmp;
    static ArrayList<int[]> list;

    private static void solve() throws IOException {
        n = sc.nextInt();
        k = sc.nextInt();
        cs = new char[n][n];
        for (int i = 0; i < n; i++) {
            cs[i] = sc.next().toCharArray();
        }
        int[][] dp = new int[n][n]; // 表示到达(i,j)的最小修改次数
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], n * 2);
        }
        dp[0][0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0 && dp[i - 1][j] < dp[i][j]) {
                    dp[i][j] = dp[i - 1][j];
                }
                if (j > 0 && dp[i][j - 1] < dp[i][j]) {
                    dp[i][j] = dp[i][j - 1];
                }
                dp[i][j] += cs[i][j] == 'a' ? 0 : 1;
            }
        }
        int cur = 0;
        list = new ArrayList<>(); // 存储使用k次能到达的最远下标
        list.add(new int[]{0, 0});
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] <= k) {
                    if (i + j > cur) {
                        cur = i + j;
                        list.clear();
                        list.add(new int[]{i, j});
                    } else if (i + j == cur) {
                        list.add(new int[]{i, j});
                    }
                }
            }
        }
        for (int i = 0; i < cur; i++) {
            System.out.print('a');
        }
        int[] pos = list.get(0);
        sc.print(dp[pos[0]][pos[1]] <= k ? 'a' : cs[pos[0]][pos[1]]);
        int[][] dir = new int[][]{{0, 1}, {1, 0}};
        for (int i = cur + 1; i < 2 * n - 1; i++) {
            tmp = new ArrayList<>();
            char mc = 'z' + 1;
            for (int[] p : list) {
                for (int[] d : dir) {
                    int x = p[0] + d[0], y = p[1] + d[1];
                    if (x < n && y < n) {
                        char c = cs[x][y];
                        if (c < mc) {
                            mc = c;
                            tmp.clear();
                            tmp.add(new int[]{x, y});
                        } else if (c == mc && tmp.size() > 0 && tmp.get(tmp.size() - 1)[0] != x) { // 避免加入重复单元格
                            tmp.add(new int[]{x, y});
                        }
                    }
                }
            }
            sc.print(mc);
            list = tmp;
        }
    }
}
```

## 2024/9/4

[A. Jzzhu and Chocolate ](https://codeforces.com/problemset/problem/449/A)

[B. Obsessive String ](https://codeforces.com/problemset/problem/494/B)

## 2024/9/5

[D. k-Interesting Pairs Of Integers ](https://codeforces.com/problemset/problem/769/D)

> 给你一个数组，问有多少对数二进制位数不同个数恰好为k
>
> 提示：二进制个数不同为k：nums[i] ^ nums[j] == k

```java
public class Main{ // 解法一
    private static void solve() throws IOException {
        int n = sc.nextInt(), k = sc.nextInt();
        int[] nums = new int[n];
        int[] cnt = new int[10001];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
            cnt[nums[i]]++;
        }
        long ans = 0L;
        if (k == 0) {
            for (int i = 0; i < 10001; i++) {
                ans += (long) cnt[i] * (cnt[i] - 1) / 2;
            }
        }else{
            for (int i = 0; i < 10001; i++) {
                for (int j = i + 1; j < 10001; j++) {
                    // 看其异或二进制是否满足有k个1
                    if (Integer.bitCount(i ^ j) == k) {
                        ans += (long) cnt[i] * cnt[j];
                    }
                }
            }
        }
        sc.println(ans);
    }
}
```

**提示 1：** 注意值域很小，而数字我们只在乎出现的频率，因此 $n$ 对我们来说就没啥用了。

**提示 2：** 统计完每个数字的频率后，应该怎么找异或呢？

首先，本题 $n$ 比值域还大不少，而我们统计的对不在乎数字的顺序，只在乎其数值，因此应当选择直接统计数字出现的频率。

接下来，我们相当于只有 $10^4$ 个项。如果此时有可能可以直接使用 $\mathcal{O}(M^2)$ 直接枚举两项，看其异或二进制是否满足有 $k$ 个 $1$ ，再进行对应的统计。

但本题还有更快的做法。 $10^4$ 以内的数二进制表示不超过 $14$ 位，因此异或后的结果也不超过 $14$ 位。而 $14$ 位中，选取 $k$ 个变成 $1$ 总共有 $C_{14}^k$ 种选法，最大值为 $C_{14}^7=3432$ 数量相当少。

因此我们先得到可能的目标数值，再枚举我们选择的一个数和目标数值，进而用两者的异或得到另一个数，这样，时间复杂度就变成了 $\mathcal{O}(n+MC_{\lceil\log M\rceil}^{k})$ 。

注意，部分做法可能需要考虑 $k=0$ 的特殊情况。

```java
public class Main{ // 解法二 枚举值域y, 因为 y<<n
    private static void solve() throws IOException {
        int n = sc.nextInt(), k = sc.nextInt();
        int[] nums = new int[n];
        int[] cnt = new int[1 << 14];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(ss[i]);
            cnt[nums[i]]++;
        }
        long ans = 0L;
        if (k == 0) {
            for (int i = 0; i < (1 << 14); i++) {
                ans += (long) cnt[i] * (cnt[i] - 1) / 2;
            }
        }else{
            ArrayList<Integer> vals = new ArrayList<>();
            for (int i = 0; i < (1 << 14); i++) {
                if (Integer.bitCount(i) == k) {
                    vals.add(i);
                }
            }
            for (int i = 0; i < (1 << 14); i++) {
                for (int x : vals) {
                    ans += (long) cnt[i] * cnt[x ^ i];
                }
            }
            ans /= 2;
        }
        sc.println(ans);
    }
}
```

[D. Queue ](https://codeforces.com/problemset/problem/353/D)

**提示 1：** 我们只需要考虑 M / F 怎么移动，不需要同时考虑，因为一个走完了，另一个也到位了。

**提示 2：** 不妨考虑 F，其每次执行交换操作实际上发生了什么？

**提示 3：** 如何处理 “堵车” 的情况。

再次印证了分数和难度不完全正相关这件事。

首先，我们只需考虑 F 什么时候到最前面，因为 F 如果都到了最前面，那 M 也都在最后面了，就已经满足了要求。（只考虑 M 什么时候到最后面逻辑也是一样的）

接下来考虑交换操作做了什么。你会发现，交换操作相当于 F 前面少了个 M 。因此每个 F 要经过几次交换只跟前面有几个 M 有关。

答案是否也是只跟 F 前面有几个 M 有关呢？这件事是否定的。因为可能发生 “堵车” 。看到第二个样例，即使一个 F 前面还有 M ，仍然可能因为两个 F 连续，导致不得不多等一轮。

而如果不堵，两个 F 会同时到达目标，因此在堵的情况下，后面的 F 会需要比前面更多的时间到达目标位置，因此后面的 F 答案应至少为当前的答案 $+1$ 。

于是，只需结合我们上述两个分析即可。考虑前面 M 的数量是 $cnt$ ，当前答案是 $ans$ ，则新的一个 F 对应的结果为 $\max(ans+1,cnt)$ 。

注意，如果当前考虑 F 是在开头连续的一段，则无需移动，即无需进行上述答案的更新。

时间复杂度为 $\mathcal{O}(n)$ 。 

就是MF 与MFMF 是一样的

```java
public class Main{
	private static void solve() throws IOException {
        cs = sc.next().toCharArray();
        int n = cs.length, cntM = 0;
        long ans = 0L;
        for (int i = 0; i < n; i++) {
            if (cs[i] == 'M') {
                cntM++;
            } else if (cntM > 0) { // cs[i] == 'F', 如果前面是F拥堵，就是ans+1; 如果前面是M，就是cntM
                ans = Math.max(ans + 1, cntM);
            }
        }
        sc.println(ans);
    }
}
```

## 2024/9/6

[B. Anton and Lines ](https://codeforces.com/problemset/problem/593/B)

**提示 1：** 我们只需考虑两条直线在 $(x_1,x_2)$ 区间内长成啥样就行。

**提示 2：** 在区间内两条直线要相交需要满足什么条件？

首先，我们把我们的注意力聚焦到区间 $(x_1,x_2)$ ，我们只看直线被截取的这一个线段。

这样，这边的两条线段如果要相交，即两条线段必须交叉穿过，则 **两条线段在 $x_1$ 和 $x_2$ 两端的大小关系会发生反转** ，即其中一条直线在 $x_1$ 处更大，另一条直线在 $x_2$ 处更大。

于是，为了判断是否有交点，只需判断两侧的大小排序关系是否一致即可。

为此，我们可以用二元组 $(f(x_1),f(x_2))$ 表示直线，并将其排序，如果第二个维度仍然满足非降序，则无交点，否则有交点。

时间复杂度为 $\mathcal{O}(n\log n)$ 。

```java
public class Main{
    private static void solve() throws IOException {
        n = sc.nextInt();
        long x1 = sc.nextLong(), x2 = sc.nextLong();
        long[][] pairs = new long[n][2];
        for (int i = 0; i < n; i++) {
            int k = sc.nextInt(), b = sc.nextInt();
            pairs[i][0] = k * x1 + b;
            pairs[i][1] = k * x2 + b;
        }
        Arrays.sort(pairs, (a, b) -> a[0] == b[0] ? Long.compare(a[1], b[1]) : Long.compare(a[0], b[0]));
        for (int i = 1; i < n; i++) {
            if (pairs[i][1] < pairs[i - 1][1]) {
                sc.println("YES");
                return;
            }
        }
        sc.println("NO");
    }
}
```



