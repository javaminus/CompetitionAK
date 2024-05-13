1012\. 至少有 1 位重复的数字
-------------------

给定正整数 `n`，返回在 `[1, n]` 范围内具有 **至少 1 位** 重复数字的正整数的个数。

**示例 1：**

**输入：**n = 20
**输出：**1
**解释：**具有至少 1 位重复数字的正数（<= 20）只有 11 。

**示例 2：**

**输入：**n = 100
**输出：**10
**解释：**具有至少 1 位重复数字的正数（<= 100）有 11，22，33，44，55，66，77，88，99 和 100 。

**示例 3：**

**输入：**n = 1000
**输出：**262

**提示：**

*   `1 <= n <= 109`

[https://leetcode.cn/problems/numbers-with-repeated-digits/description/](https://leetcode.cn/problems/numbers-with-repeated-digits/description/)

> 递归中：
>
> 如果 isNum 为假，说明前面没有填数字，那么当前也可以不填数字。一旦从这里递归下去，isLimit 就可以置为 false 了，这是因为 s[0] 必然是大于 0 的，后面就不受到 n 的约束了。或者说，最高位不填数字，后面无论怎么填都比 n 小。
> 如果 isNum 为真，那么当前必须填一个数字。枚举填入的数字，根据 isNum 和 isLimit\textit{isLimit}isLimit 来决定填入数字的范围。
> 递归终点：当 i 等于 s 长度时，如果 isNum 为真，则表示得到了一个合法数字（因为不合法的不会继续递归下去），返回 1，否则返回 0。
>

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;
    public int numDupDigitsAtMostN(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][1 << 10];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return n - dfs(0, 0, true, false);
    }

    private int dfs(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length) {
            return isNum ? 1 : 0; // isNum 为 true 表示得到了一个合法数字
        }
        if (!isLimit && isNum && memo[i][mask] != -1) {
            return memo[i][mask];
        }
        int res = 0;
        if (!isNum) { // 可以跳过当前数位
            res = dfs(i + 1, mask, false, false); // 这里特判了一些前导0的数字00000000000，但是没有return,所以会继续往下面走的
        }
        int up = isLimit ? s[i] - '0' : 9; // 如果前面填的数字都和 n 的一样，那么这一位至多填数字 s[i]（否则就超过 n 啦）
        for (int d = isNum ? 0 : 1; d <= up; d++) { // 枚举要填入的数字 d,这里的isNum也会判断前导0
            if ((mask >> d & 1) == 0) { // d 不在 mask 中
                res += dfs(i + 1, mask | (1 << d), isLimit && d == up, true);
            }
        }
        if (!isLimit && isNum){ // isNum一定要为true，这样才是填入了数字 这里可以不写！isLimit
            memo[i][mask] = res;
        }
        return res; 
    }
}
```

2719\. 统计整数数目
-------------

给你两个数字字符串 `num1` 和 `num2` ，以及两个整数 `max_sum` 和 `min_sum` 。如果一个整数 `x` 满足以下条件，我们称它是一个好整数：

*   `num1 <= x <= num2`
*   `min_sum <= digit_sum(x) <= max_sum`.

请你返回好整数的数目。答案可能很大，请返回答案对 `109 + 7` 取余后的结果。

注意，`digit_sum(x)` 表示 `x` 各位数字之和。

**示例 1：**

**输入：**num1 = "1", num2 = "12", min\_num = 1, max\_num = 8
**输出：**11
**解释：**总共有 11 个整数的数位和在 1 到 8 之间，分别是 1,2,3,4,5,6,7,8,10,11 和 12 。所以我们返回 11 。

**示例 2：**

**输入：**num1 = "1", num2 = "5", min\_num = 1, max\_num = 5
**输出：**5
**解释：**数位和在 1 到 5 之间的 5 个整数分别为 1,2,3,4 和 5 。所以我们返回 5 。

**提示：**

*   `1 <= num1 <= num2 <= 1022`
*   `1 <= min_sum <= max_sum <= 400`

[https://leetcode.cn/problems/count-of-integers/description/](https://leetcode.cn/problems/count-of-integers/description/)

```java
import java.util.Arrays;

class Solution {
    int Mod = (int) 1e9 + 7;
    char[] s;
    int[][] memo;
    public int count(String num1, String num2, int min_sum, int max_sum) {
        int ans = (cal(num2, min_sum, max_sum) - cal(num1, min_sum, max_sum) + Mod) % Mod; //栽跟头的地方，这里记得加上Mod
        int t = 0;
        for (char c : num1.toCharArray()) {
            t += c - '0';
        }
        if (t >= min_sum && t <= max_sum) {
            ans++;
        }
        return ans % Mod;
    }

    private int cal(String num, int minNum, int maxNum) {
        s = num.toCharArray();
        int n = s.length;
        memo = new int[n][Math.max(n * 9, maxNum) + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, minNum, maxNum,true);
    }

    private int dfs(int i, int sum, int minNum, int maxNum,boolean isLimit) { // 这里不用担心前导0，所以去掉了isNum参数
        if (sum > maxNum) {
            return 0;
        }
        if (i == s.length) {
            return sum >= minNum ? 1 : 0;
        }
        if (!isLimit && memo[i][sum] != -1) {
            return memo[i][sum];
        }
        int ans = 0;
        int upper = isLimit ? s[i] - '0' : 9;
        for (int d = 0; d <= upper; d++) {
            ans = (ans + dfs(i + 1, sum + d, minNum, maxNum, isLimit && d == upper)) % Mod;
        }
        if (!isLimit) {
            memo[i][sum] = ans;
        }
        return ans;
    }
}
```

788\. 旋转数字
----------

我们称一个数 X 为好数, 如果它的每位数字逐个地被旋转 180 度后，我们仍可以得到一个有效的，且和 X 不同的数。要求每位数字都要被旋转。

如果一个数的每位数字被旋转以后仍然还是一个数字， 则这个数是有效的。0, 1, 和 8 被旋转后仍然是它们自己；2 和 5 可以互相旋转成对方（在这种情况下，它们以不同的方向旋转，换句话说，2 和 5 互为镜像）；6 和 9 同理，除了这些以外其他的数字旋转以后都不再是有效的数字。

现在我们有一个正整数 `N`, 计算从 `1` 到 `N` 中有多少个数 X 是好数？

**示例：**

**输入:** 10
**输出:** 4
**解释:** 
在\[1, 10\]中有四个好数： 2, 5, 6, 9。
注意 1 和 10 不是好数, 因为他们在旋转之后不变。

**提示：**

*   N 的取值范围是 `[1, 10000]`。

[https://leetcode.cn/problems/rotated-digits/description/](https://leetcode.cn/problems/rotated-digits/description/)

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;
    int[] map = {0, 0, 1, -1, -1, 1, 1, -1, 0, 1};
    public int rotatedDigits(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][2];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true);
    }

    private int dfs(int i, int mask, boolean isLimit) {
        if (i == s.length) {
            return mask == 1 ? 1 : 0;
        }
        if (!isLimit && memo[i][mask] != -1) {
            return memo[i][mask];
        }
        int ans = 0;
        int upper = isLimit ? s[i] - '0' : 9;
        for (int d = 0; d <= upper; d++) {
            if (map[d] != -1) {
                ans += dfs(i + 1, mask | map[d], d == upper && isLimit);
            }
        }
        if (!isLimit) {
            memo[i][mask] = ans;
        }
        return ans;
    }
}
```

902\. 最大为 N 的数字组合
-----------------

给定一个按 **非递减顺序** 排列的数字数组 `digits` 。你可以用任意次数 `digits[i]` 来写的数字。例如，如果 `digits = ['1','3','5']`，我们可以写数字，如 `'13'`, `'551'`, 和 `'1351315'`。

返回 _可以生成的小于或等于给定整数 `n` 的正整数的个数_ 。

**示例 1：**

**输入：**digits = \["1","3","5","7"\], n = 100
**输出：**20
**解释：**
可写出的 20 个数字是：
1, 3, 5, 7, 11, 13, 15, 17, 31, 33, 35, 37, 51, 53, 55, 57, 71, 73, 75, 77.

**示例 2：**

**输入：**digits = \["1","4","9"\], n = 1000000000
**输出：**29523
**解释：**
我们可以写 3 个一位数字，9 个两位数字，27 个三位数字，
81 个四位数字，243 个五位数字，729 个六位数字，
2187 个七位数字，6561 个八位数字和 19683 个九位数字。
总共，可以使用D中的数字写出 29523 个整数。

**示例 3:**

**输入：**digits = \["7"\], n = 8
**输出：**1

**提示：**

*   `1 <= digits.length <= 9`
*   `digits[i].length == 1`
*   `digits[i]` 是从 `'1'` 到 `'9'` 的数
*   `digits` 中的所有值都 **不同** 
*   `digits` 按 **非递减顺序** 排列
*   `1 <= n <= 109`

[https://leetcode.cn/problems/numbers-at-most-n-given-digit-set/description/](https://leetcode.cn/problems/numbers-at-most-n-given-digit-set/description/)

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[] memo;
    String[] digits;
    public int atMostNGivenDigitSet(String[] digits, int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        this.digits = digits;
        memo = new int[m];
        Arrays.fill(memo, -1);
        return dfs(0, true, false);
    }

    private int dfs(int i, boolean isLimit, boolean isNum) {
        if (i == s.length) {
            return isNum ? 1 : 0;
        }
        if (!isLimit && isNum && memo[i] != -1) {
            return memo[i];
        }
        int ans = 0;
        if (!isNum) {
            ans += dfs(i + 1, false, false);
        }
        char upper = isLimit ? s[i] : '9';
        for (String d : digits) {
            if (isLimit && d.charAt(0) > s[i]) {
                break;
            }
            ans += dfs(i + 1, isLimit && d.charAt(0) == upper, true);
        }
        if (!isLimit && isNum) {
            memo[i] = ans;
        }
        return ans;
    }
}
```

233\. 数字 1 的个数
--------------

给定一个整数 `n`，计算所有小于等于 `n` 的非负整数中数字 `1` 出现的个数。

**示例 1：**

**输入：**n = 13
**输出：**6

**示例 2：**

**输入：**n = 0
**输出：**0

**提示：**

*   `0 <= n <= 109`

[https://leetcode.cn/problems/number-of-digit-one/description/](https://leetcode.cn/problems/number-of-digit-one/description/)

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;
    public int countDigitOne(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][m];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true);
    }

    private int dfs(int i, int cnt1, boolean isLimit) {
        if (i == s.length) {
            return cnt1;
        }
        if (!isLimit && memo[i][cnt1] != -1) {
            return memo[i][cnt1];
        }
        int ans = 0;
        int upper = isLimit ? s[i] - '0' : 9;
        for (int j = 0; j <= upper; j++) {
            ans += dfs(i + 1, cnt1 + (j == 1 ? 1 : 0), isLimit && upper == j);
        }
        if (!isLimit) {
            memo[i][cnt1] = ans;
        }
        return ans;
    }
}
```

600\. 不含连续1的非负整数
----------------

给定一个正整数 `n` ，请你统计在 `[0, n]` 范围的非负整数中，有多少个整数的二进制表示中不存在 **连续的 1** 。

**示例 1:**

**输入:** n = 5
**输出:** 5
**解释:** 
下面列出范围在 \[0, 5\] 的非负整数与其对应的二进制表示：
0 : 0
1 : 1
2 : 10
3 : 11
4 : 100
5 : 101
其中，只有整数 3 违反规则（有两个连续的 1 ），其他 5 个满足规则。

**示例 2:**

**输入:** n = 1
**输出:** 2

**示例 3:**

**输入:** n = 2
**输出:** 3

**提示:**

*   `1 <= n <= 109`

[https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/description/](https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/description/)

```java

```

