# CF1695C Zero Path【1700】

>  题意：给你一个 n×m (1≤n,m≤1000) 的格点图，每个格子的值要么是 −1，要么是 1，现在问你，是否有一条从 (1,1) 到 (n,m) 的路径，使得路径上经过的格点的值的和为 0。在路径中，只能从 ai,j 移动到 ai+1,j 或是 ai,j+1（向右或是向下走）。 
>
> https://www.luogu.com.cn/problem/CF1695C

```java
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Main {
	public static void solve() throws IOException {
		n = sc.nextInt();
		m = sc.nextInt();
		grid = new int[n][m];
		for(int i = 0;i<n;i++) {
			ss = sc.nextLine().split(" ");
			for(int j = 0;j<m;j++) {
				grid[i][j] = Integer.parseInt(ss[j]);
			}
		}
		if((n + m - 1)%2==1) {
			sc.print("NO\n");
			return;
		}
		int[][] dp1 = new int[n][m]; // 表示到点(i, j)的最小和
		int[][] dp2 = new int[n][m]; // 表示到点(i, j)的最大和
		for(int i = 0;i<n;i++) {
			Arrays.fill(dp1[i], Integer.MAX_VALUE/2);
			Arrays.fill(dp2[i], Integer.MIN_VALUE/2);
		}
		dp1[0][0] = dp2[0][0] = grid[0][0];
		for(int i = 0;i<n;i++) {
			for(int j = 0;j<m;j++) {
				if(i>0) {
					dp1[i][j] = dp1[i - 1][j] + grid[i][j];
					dp2[i][j] = dp2[i - 1][j] + grid[i][j];
				}
				if(j>0) {
					dp1[i][j] = Math.min(dp1[i][j], dp1[i][j - 1] + grid[i][j]);
					dp2[i][j] = Math.max(dp2[i][j], dp2[i][j - 1] + grid[i][j]);
				}
			}
		}
		if(dp1[n - 1][m - 1]<=0 && dp2[n - 1][m - 1]>=0) {
			sc.print("YES\n");
		}else {
			sc.print("NO\n");
		}
	}
}

```

# CF1151B Dima and a Bad XOR【1600】

> 题意：给你一个grid，每一行选择一个数，如果异或和大于0，则输出这些数的列；
>
> https://www.luogu.com.cn/problem/CF1151B

```java
public class Main{ // 随机化
    public static void solve() throws IOException {
		// 因为只有10位，枚举这10位，然后只要一位不等于0就行
		int n = sc.nextInt(), m = sc.nextInt();
		int[][] grid = new int[n][m];
		for(int i = 0;i<n;i++) {
			ss = sc.nextLine().split(" ");
			for(int j = 0;j<m;j++) {
				grid[i][j] = Integer.parseInt(ss[j]);
			}
		}
		int[] f = new int[n];
		Random rd = new Random();
		for(int t = 0;t<10000;t++) {
			int res = 0;
			for(int i = 0;i<n;i++) {
				f[i] = rd.nextInt(m);
				res^=grid[i][f[i]];
			}
			if(res>0) {
				sc.print("TAK\n");
				for(int i = 0;i<n;i++) {
					sc.print((f[i]+1)+" "); // 下标+1
				}
				return;
			}
		}
		sc.print("NIE");
	}
}
```

```java
public class Main{ // 构造做法，先将第一列异或出来，如果大于0就全部是1；反之枚举每一行，如果有grid[i][j]!=grid[i][0]，那么异或上这个数一定大于0，输出答案。}
```

# CF1829G Hits Different【1600】

> 题意：如上图的纸杯，若一个纸杯倒下，可以得到对应的权值，同时会使得其上方相邻的两个纸杯倒下。求让纸杯 n（对应权值为 n^2）倒下可以得到的总权值。 
>
> ![img](assets/e0a8819b39df73d7be64e1bb568b787d59d3e814.png) 

```java
public class Main{
	static long[][] a = new long[2001][2001];
	static long[] b = new long[4000001];
	
	static { // 错误代码，这里是cnt是int，那么cnt*cnt就是1e12，这里应该处理为long
        int cnt = 1;
        for (int i = 1; i < 2001; i++) {
            for (int j = 1; j <= i; j++) {
                a[i][j] = (i == 1 ? 1 : (long)cnt * cnt + a[i - 1][j - 1] + a[i - 1][j] - a[i - 2][j - 1]); // 递推式
                b[cnt] = a[i][j];
                cnt++;
            }
        }
    }
}
```

# CF1476C Longest Simple Cycle【1600】

> 现在给你N条链
>
> `T`组输入，每组数据输入一行`N`
>
> 接下来3行输入`N`个数据
>
> 第一行为第`i`条链的长度
>
> 第二行为当前链与上一条链连接的起点
>
> 第二行为当前链与上一条链连接的终点
>
> 现在需要你求出这个图上的一个最长环，输出这个最大值
>
> 保证除了第一条链以外的所有链都与上一条链连接
>
> 
>
> 题解：三种状态转移
>
> ![img](assets/rIAniPFqdxVUuWY.png) 

```java
public class Main{
    public static void solve() throws IOException {
		int n = sc.nextInt();
		int[] c = new int[n];
		ss = sc.nextLine().split(" ");
		for(int i = 0;i<n;i++) {
			c[i] = Integer.parseInt(ss[i]);
		}
		int[] a = new int[n];
		ss = sc.nextLine().split(" ");
		for(int i = 0;i<n;i++) {
			a[i] = Integer.parseInt(ss[i]);
		}
		int[] b = new int[n];
		ss = sc.nextLine().split(" ");
		for(int i = 0;i<n;i++) {
			b[i] = Integer.parseInt(ss[i]);
		}
		long[] dp = new long[n]; // dp[i]表示在[0, i]中，必选第i条线的最长环
		long res =0;
		for(int i = 1;i<n;i++) {
			if(a[i]==b[i]) {
				dp[i] = c[i] + 1;
			}else {
				dp[i] = Math.max(c[i] + 1 + Math.abs(a[i] - b[i])
				, dp[i - 1] - Math.abs(a[i] - b[i]) + c[i] + 1);
			}
			res = Math.max(res, dp[i]);
		}
		sc.print(res+"\n");
	}
}
```

# CF1826D Running Miles【1700】

> 题意：给定一个长度为 n 的数列 a，请找出其中的一个区间$ [l,r]$，最大化区间内的前三大值之和与$ r−l $的差，并求出这个值。 
>
> 推理：就是求$b_{i1}+b_{i2}+b_{i3} - (r - l)$的最大值，显然$i1 = l，i3 = r$时最大，变形有$(b_l+l)+b_{l2}+(b_r - r)$，然后就是枚举中间值，预处理左右最大值。

```java
public class Main{
    public static void solve() throws IOException {
		int n = sc.nextInt();
		int[] b = new int[n];
		ss  = sc.nextLine().split(" ");
		for(int i = 0;i<n;i++) {
			b[i] = Integer.parseInt(ss[i]);
		}
		long[] prefixSum = new long[n+1];
		long[] suffixSum = new long[n+1];
		suffixSum[n] = Long.MIN_VALUE; // 这里记得初始化！
		for(int i = 0;i<n;i++) {
			prefixSum[i+1] = Math.max(prefixSum[i], b[i]+i+1);
		}
		for(int i = n - 1;i>=0;i--) {
			suffixSum[i] = Math.max(suffixSum[i+1], b[i] - i - 1); 
		}
		long res = 0;
		for(int i = 1;i<n - 1;i++) {
			res = Math.max(res, prefixSum[i] + b[i] + suffixSum[i+1]);
		}
		sc.print(res+"\n");
	}
}
```

# CF1626C Monsters And Spells【1700】

> 题意：有 n 个敌人，你需要在第 ki 秒用至少 hi 的攻击力打败这个敌人。
>
> 攻击力的计算方式如下：
>
> 1. 第一秒时，你有 1 攻击力
> 2. 对于后面的任意一秒，若前一秒你的攻击力为 x，则这一秒你的攻击力可以为 x+1 或 1
>
> 一秒内，如果你的攻击力为 x ，则你就需要消耗 x 的能量。
>
> 请问，在你打败所有敌人的情况下，最少需要消耗多少能量。
>
> 思路：这题有坑，如果只是分析当前点和上一个点的关系，然后贪心的变化攻击力，就会出问题。
>
> `1000000 1000001 1000002 `
>
>  `1000000 1 1000001 ` 
>
> 这个数据就不行，因为这样到达不了最后一个敌人。所以从后往前，$f[i]$表示当前敌人的最少攻击力。

```java
pulic class Main{
    public static void solve() throws IOException {
		n = sc.nextInt();
		k = new int[n];
		h = new int[n];
		ss = sc.nextLine().split(" ");
		for(int i = 0;i<n;i++) {
			k[i] = Integer.parseInt(ss[i]);
		}
		ss = sc.nextLine().split(" ");
		for(int i = 0;i<n;i++) {
			h[i] = Integer.parseInt(ss[i]);
		}
		long[] f = new long[n]; // 表示到达第i个点的最小攻击力；
		f[n - 1] = h[n - 1];
		for(int i = n - 2;i>=0;i--) {
			long delta = k[i+1] - k[i];
			f[i] = Math.max(f[i+1] - delta, h[i]);
		}
		long res = calc(1, f[0], f[0]);
		long pre = f[0];
		for(int i = 0;i<n - 1;i++) {
			long delta = k[i+1] - k[i];
			if(f[i+1]<=delta) {
				res+=calc(1, f[i+1], f[i+1]);
				pre = f[i+1];
			}else {
				res+=calc(pre+1, pre+delta, delta);
				pre = pre + delta;
			}
		}
		sc.print(res+"\n");
	}
	
	static long calc(long st, long end, long num) { // 等差数列求和
		return (st+end)*num/2;
	}
}
```

# CF1957C How Does the Rook Move?【1600】

> 头脑风暴：
>
> # CF1957C How Does the Rook Move?
>
> ## 题目描述
>
> 你在一个 $n\times n$ 的棋盘上玩一个游戏。
>
> 你每次可以选择在 $(r,c)$ 的位置放置一个**白色的车**，使得放置后所有车无法通过水平或垂直的方向攻击到其它车（无论颜色）。如果 $r\not=c$ 则电脑在 $(c,r)$ 处放一个**黑色的车**，可以证明，如果你的操作合法，电脑操作必定合法。
>
> 现在你已经放置了 $k$ 个白色的车（显然电脑也已经进行了对应操作），如果你继续放车直到没有合法的位置放车，则游戏结束。
>
> 你希望知道游戏结束时形成的局面的可能性。
>
> 答案对 $10^9+7$ 取模。
>
> 两个局面不同当且仅当某个位置上的车颜色不同或其中一个局面放了车而另一个没有。
>
> ## 输入格式
>
> 第一行一个整数 $t$，表示数据组数。
>
> 接下来对于每组数据，第一行两个整数 $n,k$。
>
> 接下来 $k$ 行，每行两个整数 $r_i,c_i$，表示已经放置的白车的位置。
>
> ## 输出格式
>
> 共 $t$ 行，每行一个整数，表示答案。
>
> ## 输入输出样例 #1
>
> ### 输入 #1
>
> ```
> 3
> 4 1
> 1 2
> 8 1
> 7 6
> 1000 4
> 4 4
> 952 343
> 222 333
> 90 91
> ```
>
> ### 输出 #1
>
> ```
> 3
> 331
> 671968183
> ```
>
> ## 说明/提示
>
> 对于全部数据，满足 $ 1 \leq t \leq 10^4 $，$ 1 \leq n \leq 3 \times 10^5 $ , $ 0 \leq k \leq n $，$\sum n\le3\times10^5$。

![1745290919158](assets/1745290919158.png)

解释一下：当填第`i`个值，第一种情况填对角线，前`i - 1`个已经固定，所以填对角线只有一个位置可以填；第二种情况，不填对角线，就是在`i*i`的矩阵随便找个位置，但是不能填对角线，所以有`i - 1`种

```java
public class Main{
    public static void solve() throws IOException {
		int n = sc.nextInt(), k = sc.nextInt();
		for(int i = 0;i<k;i++) {
			int r = sc.nextInt(), c = sc.nextInt();
			if(r==c) {
				n--;
			}else {
				n-=2;
			}
		}
		sc.print(f[n]+"\n");
	}
	
	static int MX = (int)3e5+1;
	static long[] f = new long[MX];
	static long Mod = (long) 1e9+7;
	static {
		f[0] = f[1] = 1;
		for(int i = 2;i<MX;i++) {
			f[i] = (f[i - 1] + 2*(i - 1)*f[i - 2]%Mod)%Mod;
		}
	}
}
```

# CF1941E Rudolf and k Bridges【1600】

核心题意：有`n`个桥墩，第`1`个和第`n`个必须选择，然后没相邻两个桥墩的距离不超过` d`。其中 $(i,j1)$ 和$ (i,j2) $之间的距离为 $∣j1−j2∣−1$。 

```java
// 超时写法O(n^2)
public class Main{
    static int d;
    public static long fun(int[] arr){
        int n = arr.length;
		int[] dp = new int[n];
		Arrays.fill(dp, Integer.MAX_VALUE);
		dp[0] = 1;
		for(int i = 1;i<n;i++) {
			for(int j = i - 1;j >= 0;j--) {
				if(i - j - 1 > d) {
					break;
				}
				dp[i] = Math.min(dp[i], dp[j] + arr[i] + 1);
			}
		}
		return dp[n - 1];	
	}
}
```

```java
// 单调队列优化O(n)
public class Main{
    static int d;
    public static long fun(int[] arr){
        int n = arr.length;
		long[] dp = new long[n];
		Arrays.fill(dp, Long.MAX_VALUE);
		dp[0] = 1;
		Deque<Integer> q = new LinkedList<>();
		q.offer(0);
		for(int i = 1;i<n;i++) {
			while(!q.isEmpty() && i - q.peek() - 1>d) {
				q.poll(); // pollFirst();
			}
			dp[i] = Math.min(dp[i], dp[q.peek()]+arr[i]+1);
			while(!q.isEmpty() && dp[q.peekLast()] >= dp[i]) {
				q.pollLast();
			}
			q.offerLast(i);
		}
		return dp[n - 1];	
	}
}
```

# CF1282B2 K for the Price of One (Hard Version)【1600】

你有 `p` 元钱，店里有 `n` 个商品，每个商品价值 `ai` 元。当你购买一个商品时，你可以免费得到 `k−1` 个价值小于等于它的商品 `(k≤n)`，但如果商品数量不足 `k−1` 个时则无法免费得到它们。

请求出你能得到商品数量的最大值。

```java
public static void solve() throws IOException {
		int n = sc.nextInt(), p = sc.nextInt(), k = sc.nextInt();
		int[] arr = new int[n+1];
		ss = sc.nextLine().split(" ");
		for(int i = 0;i<n;i++) {
			arr[i+1] = Integer.parseInt(ss[i]);
		}
		Arrays.sort(arr);
		long[] dp = new long[n+1]; // dp[i]表示买前i个物品的钱，这里难想，我定义的是到第i个位置买的最多物品数，哎 >_<
		Arrays.fill(dp, Long.MAX_VALUE/2);
		dp[0] = 0;
		for(int i = 1;i<=n;i++) {
			if(i>=k) {
				dp[i] = Math.min(dp[i], dp[i - k] + arr[i]);
			}else {
				dp[i] = Math.min(dp[i], dp[i - 1] + arr[i]);
			}
		}
		int res = 0;
		for(int i = n;i>=0;i--) {
			if(dp[i]<=p) {
				res = i;
				break;
			}
		}
		sc.print(res+"\n");
	}
}
```

# CF1498C Planar Reflections【1600】

> 有 n 个平面，从它们的最左端向右发射一个能量级别为 k 的粒子，问你最后有多少个粒子，答案对 $10^9+7$ 取模。
>
> 若一个能级为 k 的粒子穿过一个平面，则它自己会继续原来的方向飞行，能级不改变，平面会生成一个能级为 k−1 的粒子，飞向这个粒子的反方向。

```java
public class Main{
    public static void solve() throws IOException {
		int n = sc.nextInt(), k = sc.nextInt();
		long[][] dp = new long[k+1][n+1]; // 表示能量为i的粒子，还需要经过j个屏障
		long Mod = (long) 1e9+7;
		for(int i = 1;i<=k;i++) {
			dp[i][0] = 1;
		}
		for(int i = 1;i<=n;i++) {
			dp[1][i] = 1;
		}
		for(int i = 1;i<=k;i++) {
			for(int j = 1;j<=n;j++) {
				dp[i][j] = (dp[i][j - 1] + dp[i - 1][n - j])%Mod;
			}
		}
		sc.print(dp[k][n]+"\n");
	}
}
```

# CF219D Choosing Capital for Treeland【1700】

> Treeland 国有 $n$ 个城市，有些城市间存在 **单向** 道路。这个国家一共有 $n - 1$ 条路。我们知道，如果把边视作双向的，那么从任意城市出发能到达任意城市。
>
> 城市的委员会最近决定为 Treeland 国选择一个首都，显然首都会是国中的一个城市。委员会将在首都开会，并经常去其他城市（这里不考虑从其他城市回到首都）。因此，如果城市 $a$ 被选为首都，那么所有的道路应该被定向，以使得我们能从城市 $a$ 到达其他城市。所以，有些路可能需要反转方向。
>
> 帮助委员会选择首都使得他们需要反转道路的次数最小。

![1745377560145](assets/1745377560145.png)

```java
public class Main{
    public static void solve() throws IOException {
		int n = sc.nextInt();
		g = new List[n+1];
		Arrays.setAll(g, e->new ArrayList<>());
		for(int i = 0;i<n - 1;i++) {
			int x = sc.nextInt(), y = sc.nextInt();
			g[x].add(new int[]{y, 0});
			g[y].add(new int[] {x, 1});
		}
		dp = new int[n+1];
		dp[1] = dfs1(1,  0);
		dfs2(1, 0);
		int mn = Integer.MAX_VALUE;
		for(int i = 1;i<=n;i++) {
			mn = Math.min(mn, dp[i]);
		}
		sc.print(mn+"\n");
		for(int i = 1;i<=n;i++) {
			if(dp[i]==mn) {
				sc.print(i+" ");
			}
		}
	}
	static int[] dp;
	
	static int dfs1(int x, int fa) {
		int res = 0;
		for(int[] y:g[x]) {
			if(y[0]!=fa) {
				res+=dfs1(y[0], x)+y[1];
			}
		}
		return res;
	}
	
	static void dfs2(int x, int fa) {
		for(int[] y:g[x]) {
			if(y[0]!=fa) {
				dp[y[0]] += dp[x] + (y[1]==1?-1:1); // 先序遍历
				dfs2(y[0], x);
			}
		}
	}
}
```

