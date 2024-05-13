

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

class Solution { // isNum表示前面是否填了数字
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

    private int dfs(int i, int mask, boolean isLimit, boolean isNum) { // isNum表示前面是否填了数字
        if (i == s.length) {
            return isNum ? 1 : 0; // isNum 为 true 表示得到了一个合法数字
        }
        if (!isLimit && isNum && memo[i][mask] != -1) {
            return memo[i][mask];
        }
        int res = 0;
        if (!isNum) { // 当前位置不填数字
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

我们称一个数 X 为好数, 如果它的每位数字逐个地被旋转 180 度后，我们仍可以得到一个有效的，**且和 X 不同的数**。要求每位数字都要被旋转。

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

> 这题如果不要“**且和 X 不同的数**”，代码为：

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[] memo;
    int[] set = {1, 1, 1, 0, 0, 1, 1, 0, 1, 1};
    public int rotatedDigits(int n) {
        s = Integer.toString(n).toCharArray();
        memo = new int[s.length];
        Arrays.fill(memo, -1);
        return dfs(0, true);
    }

    private int dfs(int i, boolean isLimit) {
        if (i == s.length) {
            return 1;
        }
        if (!isLimit && memo[i] != -1) {
            return memo[i];
        }
        int res = 0;
        int up = isLimit ? s[i] - '0' : 9;
        for (int j = 0; j <= up; j++) {
            if (set[j] == 0) {
                continue;
            }
            res += dfs(i + 1, isLimit && j == up);
        }
        if (!isLimit) {
            memo[i] = res;
        }
        return res;
    }

}
```



902\. 最大为 N 的数字组合(必须要isNum)
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
        if (!isNum) { // 当前位置不填数字
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

> 然后我自己加上isNum这个参数

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.countDigitOne(13);
    }
    public int countDigitOne(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][m];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true, false);
    }

    private int dfs(int i, int preOne, boolean isLimit, boolean isNum) { // preOne前面1的数量
        if (i == s.length) {
            return isNum ? preOne : 0;
        }
        if (!isLimit && isNum && memo[i][preOne] != -1) {
            return memo[i][preOne];
        }
        int res = 0;
        if (!isNum) { // 当前位置之前不填数字
            // res = dfs(i + 1, preOne, false, false); // 就是这一行，删除就是正确的程序，加上就错误？？？
        }
        int up = isLimit ? s[i] - '0' : 9;
        for (int j = 0; j <= up; j++) {
            res += dfs(i + 1, preOne + (j == 1 ? 1 : 0), isLimit && j == up, true);
        }
        if (!isLimit && isNum) {
            memo[i][preOne] = res;
        }
        return res;
    }
}
```

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;

    public static void main(String[] args) { // 原来是这样！！！
        Solution solution = new Solution();
        solution.countDigitOne(13);
    }
    public int countDigitOne(int n) {
        s = Integer.toString(n).toCharArray();
        int m = s.length;
        memo = new int[m][m];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true, false);
    }

    private int dfs(int i, int preOne, boolean isLimit, boolean isNum) { // preOne前面1的数量
        if (i == s.length) {
            return isNum ? preOne : 0;
        }
        if (!isLimit && isNum && memo[i][preOne] != -1) {
            return memo[i][preOne];
        }
        int res = 0;
        if (!isNum) { // 当前位置之前不填数字
            res = dfs(i + 1, preOne, false, false);
        }
        int up = isLimit ? s[i] - '0' : 9;
        for (int j = isNum ? 0 : 1; j <= up; j++) {
            res += dfs(i + 1, preOne + (j == 1 ? 1 : 0), isLimit && j == up, true);
        }
        if (!isLimit && isNum) {
            memo[i][preOne] = res;
        }
        return res;
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
import java.util.Arrays;

class Solution {
    int[][] memo;
    char[] s;
    public int findIntegers(int n) {
        s = Integer.toBinaryString(n).toCharArray();
        int m = s.length;
        memo = new int[m][2];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0,0,true);
    }

    private int dfs(int i, int k, boolean isLimit) {
        if (i == s.length) {
            return 1;
        }
        if (!isLimit && memo[i][k] != -1) {
            return memo[i][k];
        }
        int ans = 0;
        int upper = isLimit ? s[i] - '0' : 1;
        for (int d = 0; d <= upper; d++) {
            if (d == 1 && k == 1) {
                continue;
            }
            ans += dfs(i + 1, d, isLimit && d == upper);
        }
        if (!isLimit) {
            memo[i][k] = ans;
        }
        return ans;
    }
}
```

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;
    public int findIntegers(int n) {
        s = Integer.toBinaryString(n).toCharArray();
        int m = s.length;
        memo = new int[m][2];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true, false) + 1; // 然后这么写，应该是0这个点没有被统计进去
    }

    private int dfs(int i, int pre, boolean isLimit, boolean isNum) {
        if (i == s.length) {
            return isNum ? 1 : 0;
        }
        if (!isLimit && isNum && memo[i][pre] != -1) {
            return memo[i][pre];
        }
        int res = 0;
        if (!isNum) {
            res = dfs(i + 1, pre, false, false);
        }
        int up = isLimit ? s[i] - '0' : 1;
        for (int j = isNum ? 0 : 1; j <= up; j++) {
            if (j == 1 && pre == 1) {
                continue;
            }
            res += dfs(i + 1, j, isLimit && j == up, true);
        }
        if (!isLimit && isNum) {
            memo[i][pre] = res;
        }
        return res;
    }
}
```

面试题 17.06. 2出现的次数
-----------------

编写一个方法，计算从 0 到 n (含 n) 中数字 2 出现的次数。

**示例:**

**输入:** 25
**输出:** 9
**解释:** (2, 12, 20, 21, 22, 23, 24, 25)(注意 22 应该算作两次)

提示：

*   `n <= 10^9`

[https://leetcode.cn/problems/number-of-2s-in-range-lcci/](https://leetcode.cn/problems/number-of-2s-in-range-lcci/)

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;
    public int numberOf2sInRange(int n) {
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
            ans += dfs(i + 1, cnt1 + (j == 2 ? 1 : 0), isLimit && upper == j);
        }
        if (!isLimit) {
            memo[i][cnt1] = ans;
        }
        return ans;
    }
}
```

2376\. 统计特殊整数
-------------

如果一个正整数每一个数位都是 **互不相同** 的，我们称它是 **特殊整数** 。

给你一个 **正** 整数 `n` ，请你返回区间 `[1, n]` 之间特殊整数的数目。

**示例 1：**

**输入：**n = 20
**输出：**19
**解释：**1 到 20 之间所有整数除了 11 以外都是特殊整数。所以总共有 19 个特殊整数。

**示例 2：**

**输入：**n = 5
**输出：**5
**解释：**1 到 5 所有整数都是特殊整数。

**示例 3：**

**输入：**n = 135
**输出：**110
**解释：**从 1 到 135 总共有 110 个整数是特殊整数。
不特殊的部分数字为：22 ，114 和 131 。

**提示：**

*   `1 <= n <= 2 * 109`

[https://leetcode.cn/problems/count-special-integers/](https://leetcode.cn/problems/count-special-integers/)

```java
import java.util.Arrays;

class Solution {
    int[][] memo;
    char[] s;
    public int countSpecialNumbers(int n) {
        s = Integer.toString(n).toCharArray();
        int len = s.length;
        memo = new int[len][1 << 10];
        for (int i = 0; i < len; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true, false);
    }

    private int dfs(int i, int mask, boolean isLimit, boolean isNum) {
        if (i == s.length) {
            return isNum ? 1 : 0;
        }
        if (!isLimit && isNum && memo[i][mask] != -1) {
            return memo[i][mask];
        }
        int ans = 0;
        if (!isNum) {
            ans += dfs(i + 1, mask, false, false);
        }
        int upper = isLimit ? s[i] - '0' : 9;
        for (int d = isNum ? 0 : 1; d <= upper; d++) {
            if ((mask >> d & 1) == 0) {
                ans += dfs(i + 1, mask | (1 << d), isLimit && d == upper, true);
            }
        }
        if (!isLimit && isNum) {
            memo[i][mask] = ans;
        }
        return ans;
    }
}
```

357\. 统计各位数字都不同的数字个数
--------------------

给你一个整数 `n` ，统计并返回各位数字都不同的数字 `x` 的个数，其中 `0 <= x < 10n` 。

**示例 1：**

**输入：**n = 2
**输出：**91
**解释：**答案应为除去 `11、22、33、44、55、66、77、88、99` 外，在 0 ≤ x < 100 范围内的所有数字。 

**示例 2：**

**输入：**n = 0
**输出：**1

**提示：**

*   `0 <= n <= 8`

[https://leetcode.cn/problems/count-numbers-with-unique-digits/](https://leetcode.cn/problems/count-numbers-with-unique-digits/)

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][] memo;
    public int countNumbersWithUniqueDigits(int n) {
        s = Integer.toString((int) Math.pow(10, n) - 1).toCharArray();
        int m = s.length;
        memo = new int[m][1 << 10];
        for (int i = 0; i < m; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true, false) + 1;
    }

    private int dfs(int i,int mask, boolean isLimit ,boolean isNum) {
        if (i == s.length) {
            return isNum ? 1 : 0;
        }
        if (!isLimit && isNum && memo[i][mask] != -1) {
            return memo[i][mask];
        }
        int ans = 0;
        if (!isNum) {
            ans += dfs(i + 1, mask, false, false);
        }
        int upper = isLimit ? s[i] - '0' : 9;
        for (int d = isNum ? 0 : 1; d <= upper; d++) {
            if ((mask >> d & 1) == 0) {
                ans += dfs(i + 1, mask | 1 << d, isLimit && d == upper, true);
            }
        }
        if (!isLimit && isNum) {
            memo[i][mask] = ans;
        }
        return ans;
    }
}
```

3007\. 价值和小于等于 K 的最大数字
----------------------

![1715603388455](F:\leetcode\src\com\Java_Template\dp\digit_dp\digit_dp.assets\1715603388455.png)

**提示：**

*   `1 <= k <= 1015`
*   `1 <= x <= 8`

[https://leetcode.cn/problems/maximum-number-that-sum-of-the-prices-is-less-than-or-equal-to-k/description/](https://leetcode.cn/problems/maximum-number-that-sum-of-the-prices-is-less-than-or-equal-to-k/description/)

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int x;
    long[][] memo;
    public long findMaximumNumber(long k, int x) {
        long left = 0, right = (k + 1) << x; // 这里我写的 1e15 哈哈哈哈，时间上面差不多
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (cal(mid, x) > k) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return left - 1;
    }

    private long cal(long k, int x) {
        s = Long.toBinaryString(k).toCharArray();
        this.x = x;
        memo = new long[s.length][s.length];
        for (int i = 0; i < s.length; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true);
    }

    private long dfs(int i, int preCnt, boolean isLimit) {
        if (i == s.length) {
            return preCnt;
        }
        if (!isLimit && memo[i][preCnt] != -1) {
            return memo[i][preCnt];
        }
        long res = 0;
        int up = isLimit ? s[i] - '0' : 1;
        for (int j = 0; j <= up; j++) {
            res += dfs(i + 1, preCnt + (j == 1 && (s.length - i) % x == 0 ? 1 : 0), isLimit && j == up);
        }
        if (!isLimit) {
            memo[i][preCnt] = res;
        }
        return res;
    }
}
```

