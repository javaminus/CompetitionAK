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

