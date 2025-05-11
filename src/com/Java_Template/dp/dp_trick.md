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

# CF1875D Jellyfish and Mex【1600】

> 给出一个长度为 n 的序列 a，每次从中删除一个数直到删完，求每次**删数之后**的序列的 `mex` 的和的最小值（$∑n≤5000,a_i≤10^9$）。 
>

```java
public class Main{
    public static void solve() throws IOException {
		int n = sc.nextInt();
		ss = sc.nextLine().split(" ");
		HashMap<Integer, Integer> map = new HashMap<>();
		int[] a = new int[n];
		for(int i = 0;i<n;i++) {
			a[i] = Integer.parseInt(ss[i]);
			map.merge(a[i], 1, Integer::sum);
		}
		Arrays.sort(a);
		int p = 0; // 当前数组的mex
		for(int x:a) {
			if(x<p) {
				continue;
			}
			if(x==p) {
				p++;
			}
			if(x>p) {
				break;
			}
		}
		// dp[i]表示使mex = i的代价， dp[i] = Math.min(dp[i], dp[j] + (c[i] - 1) * j + i)  i<j
		long[] dp = new long[p+1];
		Arrays.fill(dp, Long.MAX_VALUE);
		dp[p] = 0;
		for(int i = p - 1;i>=0;i--) {
			for(int j = i+1;j<=p;j++) {
				dp[i] = Math.min(dp[i], dp[j] + (map.get(i) - 1)*j + i);
			}
		}
		sc.print(dp[0]+"\n");
	}
}
```

# CF1969C Minimizing the Sum【1700】

> 给你一个长度为 n 的整数数组 a。
>
> 你可以执行以下操作：选择数组中的一个元素，并用其邻近元素的值替换它。
>
> 你的任务是计算在执行上述操作最多 k 次的情况下，数组的总和可能达到的最小值。=

```java
import java.io.*;
import java.util.*;

/**
 * 题目描述：
 * 给定一个长度为 n 的数组 a，允许最多进行 K 次操作，每次操作选择一个
 * 元素并将它替换成其相邻元素的值。
 * 本题目要求求出经过最多 K 次操作后，数组元素和的最小值。
 *
 * 思路：  区间dp
 * 1. 使用动态规划 dp[i][j] 表示处理到位置 i（1-indexed），用掉 j 次操作时可达到的最小总代价。
 * 2. 对于每个位置 i，我们尝试将从位置 i 开始连续更新 k 个元素，其中 k 的范围是 0 到 min(K - j, n - i)。
 *    更新的过程中维护最小值 mn，代表区间 [i, i+k] 内如果全部变成 mn，所需付出的成本为 (k+1) * mn。
 * 3. 状态转移：dp[i + k][j + k] = min(dp[i + k][j + k], dp[i - 1][j] + (k + 1) * mn)
 * 4. 最终答案为 dp[n][x] x 从 0 到 K 的最小值。
 *
 * 注意：
 * 1. 本实现使用 1-indexed 数组 a，为防止访问越界，对数组长度开 n+2 的空间。
 * 2. INF 取值为 9e18，以适应题中数值范围。
 */
public class Main {
    // 定义一个足够大的数，表示无穷大（注意 9e18 作为 long 型的常量）
    static final long INF = (long) 9e18;
    
    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 和 PrintWriter 进行快速输入输出
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);
        
        int t = Integer.parseInt(br.readLine().trim());
        while(t-- > 0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            // 数组 a 使用 1-indexed，下标范围为 1 ... n
            long[] a = new long[n + 2];  // 多开一个位置，防止后续访问 a[i+k] 越界
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= n; i++){
                a[i] = Long.parseLong(st.nextToken());
            }
            
            // 初始化 dp 数组，dp[i][j] 表示处理到位置 i，花费 j 次操作的最小总代价
            long[][] dp = new long[n + 1][K + 1];
            for (int i = 0; i <= n; i++){
                Arrays.fill(dp[i], INF);
            }
            // 初始状态：处理 0 个元素且花费 0 次操作，总代价为 0
            dp[0][0] = 0;
            
            // 状态转移，i 表示当前位置 (1-indexed)
            for (int i = 1; i <= n; i++){
                // j 表示在当前位置之前花费的操作次数
                for (int j = 0; j <= K; j++){
                    if(dp[i - 1][j] == INF) continue;
                    // k 表示从位置 i 开始连续操作的个数
                    // k 的取值范围为0到min(K - j, n - i)
                    int limit = Math.min(K - j, n - i);
                    long mn = a[i]; // 初始时，区间 [i, i] 的最小值
                    for (int k = 0; k <= limit; k++){
                        // 更新区间 [i, i+k] 的最小值
                        if(k > 0){
                            mn = Math.min(mn, a[i + k]);
                        }
                        // 进行状态转移:
                        // 将区间 [i, i+k] 全部更新为 mn, 该区间操作次数为 k+1, 总代价 dp[i-1][j] + (k+1)*mn.
                        dp[i + k][j + k] = Math.min(dp[i + k][j + k], dp[i - 1][j] + (k + 1) * mn);
                    }
                }
            }
            // 答案为 dp[n][x] 中最小值，x 从 0 到 K
            long ans = INF;
            for (int x = 0; x <= K; x++){
                ans = Math.min(ans, dp[n][x]);
            }
            out.println(ans);
        }
        out.flush();
        out.close();
    }
}
```

# CF2096C Wonderful City【1700】

> ## 题目描述
>
> 你是古伯兰王国一座城市的骄傲领导者。这座城市有 $n^2$ 栋建筑，排列成 $n$ 行 $n$ 列的网格。位于第 $i$ 行第 $j$ 列的建筑高度为 $h_{i,j}$。
>
> 当城市中任意两个相邻建筑的高度都不相同时，这座城市才是美丽的。换句话说，必须满足以下条件：
> - 不存在位置 $(i,j)$（$1 \leq i \leq n$，$1 \leq j \leq n-1$）使得 $h_{i,j} = h_{i,j+1}$；
> - 不存在位置 $(i,j)$（$1 \leq i \leq n-1$，$1 \leq j \leq n$）使得 $h_{i,j} = h_{i+1,j}$。
>
> A 公司有 $n$ 名工人，B 公司也有 $n$ 名工人。每名工人最多只能被雇佣一次。
>
> 雇佣 A 公司的第 $i$ 名工人需要花费 $a_i$ 枚金币。雇佣后，该工人会：
> - 将第 $i$ 行所有建筑的高度增加 $1$。即，将 $h_{i,1}, h_{i,2}, \ldots, h_{i,n}$ 都增加 $1$。
>
> 雇佣 B 公司的第 $j$ 名工人需要花费 $b_j$ 枚金币。雇佣后，该工人会：
> - 将第 $j$ 列所有建筑的高度增加 $1$。即，将 $h_{1,j}, h_{2,j}, \ldots, h_{n,j}$ 都增加 $1$。
>
> 请计算使城市变得美丽所需的最少金币数，如果不可能实现则返回 $-1$。
>
> ## 输入格式
>
> 每个测试包含多个测试用例。第一行包含测试用例的数量 $t$（$1 \le t \le 100$）。接下来是各个测试用例的描述。
>
> 每个测试用例的第一行包含一个整数 $n$（$2 \le n \le 1000$）——网格的大小。
>
> 接下来每个测试用例的 $n$ 行中，第 $i$ 行包含 $n$ 个整数 $h_{i,1}, h_{i,2}, \ldots, h_{i,n}$（$1 \le h_{i,j} \le 10^9$）——第 $i$ 行建筑的高度。
>
> 每个测试用例的下一行包含 $n$ 个整数 $a_1, a_2, \ldots, a_n$（$1 \le a_i \le 10^9$）——雇佣 A 公司工人的费用。
>
> 每个测试用例的下一行包含 $n$ 个整数 $b_1, b_2, \ldots, b_n$（$1 \le b_j \le 10^9$）——雇佣 B 公司工人的费用。
>
> 保证所有测试用例的 $n$ 之和不超过 $1000$。
>
> ## 输出格式
>
> 对于每个测试用例，输出一个整数——所需的最少金币数，如果不可能则输出 $-1$。
>
> ## 输入输出样例 #1
>
> ### 输入 #1
>
> ```
> 4
> 2
> 1 2
> 2 1
> 100 100
> 100 100
> 4
> 1 2 1 2
> 3 2 1 2
> 1 2 1 1
> 1 3 1 2
> 1 2 3 4
> 5 6 7 8
> 3
> 1 2 2
> 2 2 1
> 2 1 1
> 100 100 100
> 100 100 100
> 6
> 8 7 2 8 4 8
> 7 7 9 7 1 1
> 8 3 1 1 8 5
> 6 8 3 1 1 4
> 1 4 5 1 9 6
> 7 1 1 6 8 2
> 11 23 20 79 30 15
> 15 83 73 57 34 63
> ```
>
> ### 输出 #1
>
> ```
> 0
> 14
> -1
> 183
> ```
>
> ## 说明/提示
>
> 对于第一个测试用例，可以看到城市已经是美丽的，因此答案为 $0$。
>
> 对于第二个测试用例，我们可以雇佣 A 公司的第 $2$ 名工人、A 公司的第 $4$ 名工人和 B 公司的第 $4$ 名工人：
> - 初始状态：
> ```
> 1 2 1 2
> 3 2 1 2
> 1 2 1 1
> 1 3 1 2
> ```
> - 雇佣 A 公司第 $2$ 名工人后：
> ```
> 1 2 1 2
> 4 3 2 3
> 1 2 1 1
> 1 3 1 2
> ```
> - 雇佣 A 公司第 $4$ 名工人后：
> ```
> 1 2 1 2
> 4 3 2 3
> 1 2 1 1
> 2 4 2 3
> ```
> - 雇佣 B 公司第 $4$ 名工人后：
> ```
> 1 2 1 3
> 4 3 2 4
> 1 2 1 2
> 2 4 2 4
> ```
>
> 此时城市变得美丽，雇佣工人的总费用为 $2 + 4 + 8 = 14$，这是可能的最小费用。
>
> 对于第三个测试用例，无论如何操作都无法使城市变得美丽，因此答案为 $-1$。
>
> 翻译由 DeepSeek V3 完成

```java
import java.util.*;
import java.io.*;
// 行列不互相影响
public class Main {
    static final long INF = Long.MAX_VALUE / 2;
    
    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 读取输入
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());
        StringBuilder sb = new StringBuilder();
        for(int tc = 0; tc < t; tc++){
            int n = Integer.parseInt(br.readLine().trim());
            int[][] grid = new int[n][n];
            for (int i = 0; i < n; i++){
                String[] parts = br.readLine().split("\\s+");
                for (int j = 0; j < n; j++){
                    grid[i][j] = Integer.parseInt(parts[j]);
                }
            }
            
            long[] a = new long[n];
            long[] b = new long[n];
            {
                String[] parts = br.readLine().split("\\s+");
                for (int i = 0; i < n; i++){
                    a[i] = Long.parseLong(parts[i]);
                }
            }
            {
                String[] parts = br.readLine().split("\\s+");
                for (int j = 0; j < n; j++){
                    b[j] = Long.parseLong(parts[j]);
                }
            }
            
            long verticalCost = solveVertical(n, grid, a);
            long horizontalCost = solveHorizontal(n, grid, b);
            
            if(verticalCost == INF || horizontalCost == INF) {
                sb.append("-1\n");
            } else {
                sb.append(verticalCost + horizontalCost).append("\n");
            }
        }
        System.out.print(sb);
    }
    
    // 针对行的 DP
    static long solveVertical(int n, int[][] grid, long[] a) {
        // dp[i][r] 表示处理前 i 行，且第 i 行状态为 r 时的最小总费用
        long[][] dp = new long[n][2];
        for (int i = 0; i < n; i++){
            Arrays.fill(dp[i], INF);
        }
        // 对于第一行，0 表示不雇佣工人（不增加高度），1 表示雇佣工人（增加高度）
        dp[0][0] = 0;
        dp[0][1] = a[0];
        
        // 对每对相邻行 i 和 i+1，根据不同的转移状态来判断是否合法
        for (int i = 0; i < n - 1; i++){
            for (int cur = 0; cur < 2; cur++){
                if(dp[i][cur] == INF) continue;
                for (int nxt = 0; nxt < 2; nxt++){
                    if(isValidVerticalPair(grid, i, cur, nxt)) {
                        dp[i+1][nxt] = Math.min(dp[i+1][nxt], dp[i][cur] + (nxt == 1 ? a[i+1] : 0));
                    }
                }
            }
        }
        return Math.min(dp[n-1][0], dp[n-1][1]);
    }
    
    // 检测相邻两行的状态是否合法
    static boolean isValidVerticalPair(int[][] grid, int i, int cur, int nxt){
        int n = grid[0].length;
        if(cur == nxt){
            // 两行同状态时要求 h[i][j] != h[i+1][j] 对所有 j 成立
            for (int j = 0; j < n; j++){
                if(grid[i][j] == grid[i+1][j]) return false;
            }
        } else if(cur == 0 && nxt == 1){
            // 从未增加转为增加时要求 h[i][j] != h[i+1][j] + 1
            for (int j = 0; j < n; j++){
                if(grid[i][j] == grid[i+1][j] + 1) return false;
            }
        } else if(cur == 1 && nxt == 0){
            // 从增加转为未增加时要求 h[i][j] != h[i+1][j] - 1
            for (int j = 0; j < n; j++){
                if(grid[i][j] == grid[i+1][j] - 1) return false;
            }
        }
        return true;
    }
    
    // 针对列的 DP
    static long solveHorizontal(int n, int[][] grid, long[] b) {
        // dp[j][c] 表示处理前 j 列，且第 j 列状态为 c 时的最小费用
        long[][] dp = new long[n][2];
        for (int j = 0; j < n; j++){
            Arrays.fill(dp[j], INF);
        }
        dp[0][0] = 0;
        dp[0][1] = b[0];
        
        // 对每对相邻列 j 和 j+1，根据状态转移判断是否合法
        for (int j = 0; j < n - 1; j++){
            for (int cur = 0; cur < 2; cur++){
                if(dp[j][cur] == INF) continue;
                for (int nxt = 0; nxt < 2; nxt++){
                    if(isValidHorizontalPair(grid, j, cur, nxt)) {
                        dp[j+1][nxt] = Math.min(dp[j+1][nxt], dp[j][cur] + (nxt == 1 ? b[j+1] : 0));
                    }
                }
            }
        }
        return Math.min(dp[n-1][0], dp[n-1][1]);
    }
    
    // 检查相邻两列是否合法
    static boolean isValidHorizontalPair(int[][] grid, int j, int cur, int nxt) {
        int n = grid.length;
        if(cur == nxt) {
            // 同状态时要求：对于每一行 i，有 grid[i][j] != grid[i][j+1]
            for (int i = 0; i < n; i++){
                if(grid[i][j] == grid[i][j+1]) return false;
            }
        } else if(cur == 0 && nxt == 1){
            // (0, 1) 状态时要求：grid[i][j] != grid[i][j+1] + 1
            for (int i = 0; i < n; i++){
                if(grid[i][j] == grid[i][j+1] + 1) return false;
            }
        } else if(cur == 1 && nxt == 0){
            // (1, 0) 状态时要求：grid[i][j] != grid[i][j+1] - 1
            for (int i = 0; i < n; i++){
                if(grid[i][j] == grid[i][j+1] - 1) return false;
            }
        }
        return true;
    }
}
```

# CF1509C The Sports Festival【】

> # 题解说明
>
> 题目要求我们为 n 个学生安排接力赛跑的顺序，使得每一步（即前 i 名跑者）的速度差（最大速度与最小速度之差）的累积和最小。
>
> ## 问题描述
>
> - 给定 n 个学生，每个学生的跑步速度为 $s_i$。
> - 定义第 i 阶段的差值 d_i 为：前 i 个跑过的人中，最大速度与最小速度的差，即  
>   $d_i = max(a₁, a₂, ..., aᵢ) - min(a₁, a₂, ..., aᵢ)$，其中 aⱼ 表示第 j 个参赛的学生的速度。
> - 要求我们改变学生的跑步顺序，使得总体的差值和 d₁ + d₂ + ... + dₙ 最小。
>
> ## 解题思路
>
> ### 1. 对速度排序
> - 首先将所有学生的速度排序。排序后的目的在于让速度相近的学生尽可能地连在一起，从而使每一阶段的差值尽可能小。
>
> ### 2. 动态规划（DP）求解
> - 定义状态 dp[l][r] 表示在有序数组中选取下标区间 [l, r] 内的学生作为已经安排跑步的成员时，所能达到的最小累积差值和。  
>   (注意：此时“区间”代表的是在排序后的数组中连续的一段。)
>
> - **初始状态**：当区间中只有一个学生时，即 dp[i][i] = 0，因为只有一个人时最大值和最小值相等，所以差值为 0。
>
> - **状态转移**：  
>   假设当前选取的区间为 [l, r]，那么下一步可以从左边或者右边扩展：
>   - 如果将左侧未选中的学生 speeds[l-1] 加入队列，那么新加入时的额外差值为：speeds[r] - speeds[l-1]  
>     因此更新：$dp[l-1][r] = min(dp[l-1][r], dp[l][r] + speeds[r] - speeds[l-1])$。
>   - 如果将右侧未选中的学生 speeds[r+1] 加入队列，那么新加入的额外差值为：speeds[r+1] - speeds[l]  
>     因此更新：$dp[l][r+1] = min(dp[l][r+1], dp[l][r] + speeds[r+1] - speeds[l])$。
>
> - 这里额外增加的差值实际上是由于新加入的学生使得已选的最小值或最大值发生改变所导致的。
>
> ### 3. 最终答案
> - 当所有学生都被安排好之后，意味着选取的区间为整个数组，即 [0, n-1]。此时 dp[0][n-1] 就是最优解，即所有阶段累积差值和的最小值。
>
> ## 时间复杂度与空间复杂度
>
> - 时间复杂度：由于状态 dp[l][r] 的数量为 O(n²)，在每个状态下只进行常数时间的转移，故总时间复杂度为 O(n²)。
> - 空间复杂度：DP 数组 dp 占用 O(n²) 的空间。
>
> 这一思路保证了在题目给出的 n ≤ 2000 的约束下，程序能够在合理时间内求解。
>
> 整体思路的核心在于将问题转化为在排序数组上连续区间的扩展问题，这样可以利用动态规划高效地求出最优结果。

```java
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 使用 BufferedReader 加快输入速度
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 读取成员个数 n
        int n = Integer.parseInt(br.readLine().trim());
        // 读取所有成员的跑步速度
        String[] parts = br.readLine().trim().split("\\s+");
        long[] speeds = new long[n];
        for (int i = 0; i < n; i++) {
            speeds[i] = Long.parseLong(parts[i]);
        }
        
        // 对跑步速度进行排序
        Arrays.sort(speeds);
        
        // 定义 dp 数组
        // dp[l][r] 表示当前已经选取有序区间 [l, r] 内的这些元素作为已经安排跑步的成员
        // 并且已经累计的差值和的最小值
        // 选择下一个成员时，新增加的差值为 speeds[r] - speeds[l]
        long[][] dp = new long[n][n];
        // INF 表示一个极大的数，用于初始化不可达的状态
        long INF = Long.MAX_VALUE / 2;

        // 初始化 dp 数组
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], INF);
            dp[i][i] = 0; // 只有一个人跑，差值为0
        }
        for(int i = n - 1;i>=0;i--) {
        	for(int j = i+1;j<n;j++) {
        		if(i+1<n) {
            		dp[i][j] = Math.min(dp[i][j], dp[i+1][j] + speeds[j] - speeds[i]);
        		}
        		if(j - 1>=0) {
        			dp[i][j] = Math.min(dp[i][j], dp[i][j - 1] + speeds[j] - speeds[i]);
        		}
        	}
        }
        // 最终答案为 dp[0][n-1]，即区间覆盖所有成员时的最小差值和
        System.out.println(dp[0][n - 1]);
    }
}
```

