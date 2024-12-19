```java
package class139;

public class Exgcd {

	// 扩展欧几里得算法
	// 对于方程ax + by = gcd(a,b)
	// 当a和b确定，那么gcd(a,b)也确定
	// 扩展欧几里得算法可以给出a和b的最大公约数d、以及其中一个特解x、y
	// 特别注意要保证入参a和b没有负数
	public static long d, x, y, px, py;

	public static void exgcd(long a, long b) {
		if (b == 0) {
			d = a;
			x = 1;
			y = 0;
		} else {
			exgcd(b, a % b);
			px = x;
			py = y;
			x = py;
			y = px - py * (a / b);
		}
	}

	// 讲解099，费马小定理计算逆元
	public static long fermat(long num, long mod) {
		return power(num, mod - 2, mod);
	}

	public static long power(long num, long pow, long mod) {
		long ans = 1;
		while (pow > 0) {
			if ((pow & 1) == 1) {
				ans = (ans * num) % mod;
			}
			num = (num * num) % mod;
			pow >>= 1;
		}
		return ans;
	}

	public static void main(String[] args) {
		// 扩展欧几里得算法例子
		int a = 220;
		int b = 170;
		exgcd(a, b);
		System.out.println("gcd(" + a + ", " + b + ")" + " = " + d);
		System.out.println("x = " + x + ", " + " y = " + y);

		// 扩展欧几里得算法可以去求逆元
		System.out.println("求逆元测试开始");
		long mod = 1000000007;
		long test = 10000000;
		for (long num = 1; num <= test; num++) {
			exgcd(num, mod);
			x = (x % mod + mod) % mod;
			if (x != fermat(num, mod)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("求逆元测试结束");
	}

}
```

```java
package class139;

// 裴蜀定理模版题
// 给定长度为n的一组整数值[a1, a2, a3...]，你找到一组数值[x1, x2, x3...]
// 要让a1*x1 + a2*x2 + a3*x3...得到的结果为最小正整数
// 返回能得到的最小正整数是多少
// 1 <= n <= 20
// 1 <= ai <= 10^5
// 测试链接 : https://www.luogu.com.cn/problem/P4549
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code01_BezoutLemma {

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int n = (int) in.nval;
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			in.nextToken();
			ans = gcd(Math.abs((int) in.nval), ans);
		}
		out.println(ans);
		out.flush();
		out.close();
		br.close();
	}

}
```

```java
package class139;

// 修理宝塔
// 一共有编号1~n的宝塔，其中a号和b号宝塔已经修好了
// Yuwgna和Iaka两个人轮流修塔，Yuwgna先手，Iaka后手，谁先修完所有的塔谁赢
// 每次可以选择j+k号或者j-k号塔进行修理，其中j和k是任意两个已经修好的塔
// 也就是输入n、a、b，如果先手赢打印"Yuwgna"，后手赢打印"Iaka"
// 2 <= n <= 2 * 10^4
// 测试链接 : https://acm.hdu.edu.cn/showproblem.php?pid=5512
// 测试链接 : https://vjudge.net/problem/HDU-5512
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code02_Pagodas {

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		int cases = (int) in.nval;
		for (int t = 1, n, a, b; t <= cases; t++) {
			in.nextToken();
			n = (int) in.nval;
			in.nextToken();
			a = (int) in.nval;
			in.nextToken();
			b = (int) in.nval;
			out.print("Case #" + t + ": ");
			if (((n / gcd(a, b)) & 1) == 1) {
				out.println("Yuwgna");
			} else {
				out.println("Iaka");
			}
		}
		out.flush();
		out.close();
		br.close();
	}

}
```

```java
package class139;

// 均匀生成器
// 如果有两个数字step和mod，那么可以由以下方式生成很多数字
// seed(1) = 0，seed(i+1) = (seed(i) + step) % mod
// 比如，step = 3、mod = 5
// seed(1) = 0，seed(2) = 3，seed(3) = 1，seed(4) = 4，seed(5) = 2
// 如果能产生0 ~ mod-1所有数字，step和mod的组合叫  "Good Choice"
// 如果无法产生0 ~ mod-1所有数字，step和mod的组合叫 "Bad Choice"
// 根据step和mod，打印结果
// 1 <= step、mod <= 10^5
// 测试链接 : http://poj.org/problem?id=1597
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_UniformGenerator {

	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int step = (int) in.nval;
			in.nextToken();
			int mod = (int) in.nval;
			out.print(String.format("%10d", step) + String.format("%10d", mod) + "    ");
			out.println(gcd(step, mod) == 1 ? "Good Choice" : "Bad Choice");
			out.println(" ");
		}
		out.flush();
		out.close();
		br.close();
	}

}
```

```java
package class139;

// 同余方程
// 求关于x的同余方程 ax ≡ 1(mod b) 的最小正整数解
// 题目保证一定有解，也就是a和b互质
// 2 <= a、b <= 2 * 10^9
// 测试链接 : https://www.luogu.com.cn/problem/P1082
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code04_CongruenceEquation {

	// 扩展欧几里得算法
	public static long d, x, y, px, py;

	public static void exgcd(long a, long b) {
		if (b == 0) {
			d = a;
			x = 1;
			y = 0;
		} else {
			exgcd(b, a % b);
			px = x;
			py = y;
			x = py;
			y = px - py * (a / b);
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		long a = (long) in.nval;
		in.nextToken();
		long b = (long) in.nval;
		exgcd(a, b);
		out.println((x % b + b) % b);
		out.flush();
		out.close();
		br.close();
	}

}
```

```java
package class139;

// 洗牌
// 一共有n张牌，n一定是偶数，每张牌的牌面从1到n，洗牌规则如下
// 比如n = 6，牌面最初排列为1 2 3 4 5 6
// 先分成左堆1 2 3，右堆4 5 6，然后按照右堆第i张在前，左堆第i张在后的方式依次放置
// 所以洗一次后，得到 4 1 5 2 6 3
// 如果再洗一次，得到 2 4 6 1 3 5
// 如果再洗一次，得到 1 2 3 4 5 6
// 想知道n张牌洗m次的之后，第l张牌，是什么牌面
// 1 <= n <= 10^10，n为偶数
// 0 <= m <= 10^10
// 测试链接 : https://www.luogu.com.cn/problem/P2054
// 提交以下的code，提交时请把类名改成"Main"，可以通过所有测试用例

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code05_ShuffleCards {

	// 扩展欧几里得算法
	public static long d, x, y, px, py;

	public static void exgcd(long a, long b) {
		if (b == 0) {
			d = a;
			x = 1;
			y = 0;
		} else {
			exgcd(b, a % b);
			px = x;
			py = y;
			x = py;
			y = px - py * (a / b);
		}
	}

	// 原理来自，讲解033，位运算实现乘法
	// a * b的过程自己实现，每一个中间过程都%mod
	// 这么写目的是防止溢出，也叫龟速乘
	public static long multiply(long a, long b, long mod) {
		// 既然是在%mod的意义下，那么a和b可以都转化成非负的
		// 本题不转化无所谓，但是其他题目可能需要转化
		// 尤其是b需要转化，否则while循环会跑不完
		a = (a % mod + mod) % mod;
		b = (b % mod + mod) % mod;
		long ans = 0;
		while (b != 0) {
			if ((b & 1) != 0) {
				ans = (ans + a) % mod;
			}
			a = (a + a) % mod;
			b >>= 1;
		}
		return ans;
	}

	// 原理来自，讲解098，乘法快速幂
	// 计算a的b次方，最终 % mod 的结果
	public static long power(long a, long b, long mod) {
		long ans = 1;
		while (b > 0) {
			if ((b & 1) == 1) {
				ans = multiply(ans, a, mod);
			}
			a = multiply(a, a, mod);
			b >>= 1;
		}
		return ans;
	}

	public static long compute(long n, long m, long l) {
		long mod = n + 1;
		exgcd(power(2, m, mod), mod);
		long x0 = (x % mod + mod) % mod;
		return multiply(x0, l, mod);
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		in.nextToken();
		long n = (long) in.nval;
		in.nextToken();
		long m = (long) in.nval;
		in.nextToken();
		long l = (long) in.nval;
		out.println(compute(n, m, l));
		out.flush();
		out.close();
		br.close();
	}

}
```

