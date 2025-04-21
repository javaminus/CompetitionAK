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

