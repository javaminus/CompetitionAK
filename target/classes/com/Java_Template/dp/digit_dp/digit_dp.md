

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

3007\. 价值和小于等于 K 的最大数字(二分答案 + 数位dp)
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
2827\. 范围中美丽整数的数目
-----------------

给你正整数 `low` ，`high` 和 `k` 。

如果一个数满足以下两个条件，那么它是 **美丽的** ：

*   偶数数位的数目与奇数数位的数目相同。
*   这个整数可以被 `k` 整除。

请你返回范围 `[low, high]` 中美丽整数的数目。

**示例 1：**

**输入：**low = 10, high = 20, k = 3
**输出：**2
**解释：**给定范围中有 2 个美丽数字：\[12,18\]
- 12 是美丽整数，因为它有 1 个奇数数位和 1 个偶数数位，而且可以被 k = 3 整除。
- 18 是美丽整数，因为它有 1 个奇数数位和 1 个偶数数位，而且可以被 k = 3 整除。
  以下是一些不是美丽整数的例子：
- 16 不是美丽整数，因为它不能被 k = 3 整除。
- 15 不是美丽整数，因为它的奇数数位和偶数数位的数目不相等。
  给定范围内总共有 2 个美丽整数。

**示例 2：**

**输入：**low = 1, high = 10, k = 1
**输出：**1
**解释：**给定范围中有 1 个美丽数字：\[10\]
- 10 是美丽整数，因为它有 1 个奇数数位和 1 个偶数数位，而且可以被 k = 1 整除。
  给定范围内总共有 1 个美丽整数。

**示例 3：**

**输入：**low = 5, high = 5, k = 2
**输出：**0
**解释：**给定范围中有 0 个美丽数字。
- 5 不是美丽整数，因为它的奇数数位和偶数数位的数目不相等。

**提示：**

*   `0 < low <= high <= 109`
*   `0 < k <= 20`

[https://leetcode.cn/problems/number-of-beautiful-integers-in-the-range/description/](https://leetcode.cn/problems/number-of-beautiful-integers-in-the-range/description/)

> ![1715615950594](F:\leetcode\src\com\Java_Template\dp\digit_dp\digit_dp.assets\1715615950594.png)

```java
import java.util.Arrays;

class Solution {
    char[] s;
    int[][][] memo;
    int k;
    int len;
    public int numberOfBeautifulIntegers(int low, int high, int k) {
        this.k = k;
        return cal(high) - cal(low - 1);
    }

    private int cal(int n) {
        s = Integer.toString(n).toCharArray();
        len = s.length;
        memo = new int[len][len * 2 + 1][k];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len * 2 + 1; j++) {
                Arrays.fill(memo[i][j], -1);
            }
        }
        return dfs(0, len, 0, true, false); // 这里初始化diff为len,防止数组-1溢出
    }

    private int dfs(int i, int diff, int mod, boolean isLimit, boolean isNum) { // diff 奇数 - 偶数
        if (i == len) {
            return isNum && mod == 0 && diff == len ? 1 : 0;
        }
        if (isNum && !isLimit && memo[i][diff][mod] != -1) {
            return memo[i][diff][mod];
        }
        int res = 0;
        if (!isNum) {
            res = dfs(i + 1, diff, mod, false, false);
        }
        int up = isLimit ? s[i] - '0' : 9;
        for (int j = isNum ? 0 : 1; j <= up; j++) {
            res += dfs(i + 1, diff + (j % 2 == 1 ? 1 : -1), (mod * 10 + j) % k, isLimit && j == up, true);
        }
        if (isNum && !isLimit) {
            memo[i][diff][mod] = res;
        }
        return res;
    }
}
```

2999\. 统计强大整数的数目
----------------

给你三个整数 `start` ，`finish` 和 `limit` 。同时给你一个下标从 **0** 开始的字符串 `s` ，表示一个 **正** 整数。

如果一个 **正** 整数 `x` 末尾部分是 `s` （换句话说，`s` 是 `x` 的 **后缀**），且 `x` 中的每个数位至多是 `limit` ，那么我们称 `x` 是 **强大的** 。

请你返回区间 `[start..finish]` 内强大整数的 **总数目** 。

如果一个字符串 `x` 是 `y` 中某个下标开始（**包括** `0` ），到下标为 `y.length - 1` 结束的子字符串，那么我们称 `x` 是 `y` 的一个后缀。比方说，`25` 是 `5125` 的一个后缀，但不是 `512` 的后缀。

**示例 1：**

**输入：**start = 1, finish = 6000, limit = 4, s = "124"
**输出：**5
**解释：**区间 \[1..6000\] 内的强大数字为 124 ，1124 ，2124 ，3124 和 4124 。这些整数的各个数位都 <= 4 且 "124" 是它们的后缀。注意 5124 不是强大整数，因为第一个数位 5 大于 4 。
这个区间内总共只有这 5 个强大整数。

**示例 2：**

**输入：**start = 15, finish = 215, limit = 6, s = "10"
**输出：**2
**解释：**区间 \[15..215\] 内的强大整数为 110 和 210 。这些整数的各个数位都 <= 6 且 "10" 是它们的后缀。
这个区间总共只有这 2 个强大整数。

**示例 3：**

**输入：**start = 1000, finish = 2000, limit = 4, s = "3000"
**输出：**0
**解释：**区间 \[1000..2000\] 内的整数都小于 3000 ，所以 "3000" 不可能是这个区间内任何整数的后缀。

**提示：**

*   `1 <= start <= finish <= 1015`
*   `1 <= limit <= 9`
*   `1 <= s.length <= floor(log10(finish)) + 1`
*   `s` 数位中每个数字都小于等于 `limit` 。
*   `s` 不包含任何前导 0 。

[https://leetcode.cn/problems/count-the-number-of-powerful-integers/](https://leetcode.cn/problems/count-the-number-of-powerful-integers/)

```java
import java.util.Arrays;

class Solution {
    char[] s;
    String s1;
    long[] memo;
    int limit;
    String t;
    public long numberOfPowerfulInt(long start, long finish, int limit, String t) { // 全部开Long
        this.limit = limit;
        this.t = t;
        return cal(finish) - cal(start - 1);
    }

    private long cal(long x) {
        s = Long.toString(x).toCharArray();
        s1 = Long.toString(x);
        if (s.length < t.length() || x < Long.parseLong(t)) {
            return 0;
        }
        memo = new long[s.length];
        Arrays.fill(memo, -1);
        return dfs(0, true, false);
    }

    private long dfs(int i, boolean isLimit, boolean isNum) {
        if (i == s.length - t.length()) { // 这里的细节比较难
            long ss = Long.parseLong(s1.substring(i));
            long tt = Long.parseLong(t);
            if (ss >= tt) {
                return 1;
            }else{
                if (!isLimit) {
                    return 1;
                }
                return 0;
            }
        }
        if (isNum && !isLimit && memo[i] != -1) {
            return memo[i];
        }
        long res = 0;
        if (!isNum) {
            res += dfs(i + 1, false, false);
        }
        int up = isLimit ? s[i] - '0' : 9;
        for (int j = isNum ? 0 : 1; j <= Math.min(limit, up); j++) {
            res += dfs(i + 1, isLimit && j == up, true);
        }
        if (isNum && !isLimit) {
            memo[i] = res;
        }
        return res;
    }
}
```

2801\. 统计范围内的步进数字数目(做差取余一定要加Mod)
-------------------

给你两个正整数 `low` 和 `high` ，都用字符串表示，请你统计闭区间 `[low, high]` 内的 **步进数字** 数目。

如果一个整数相邻数位之间差的绝对值都 **恰好** 是 `1` ，那么这个数字被称为 **步进数字** 。

请你返回一个整数，表示闭区间 `[low, high]` 之间步进数字的数目。

由于答案可能很大，请你将它对 `109 + 7` **取余** 后返回。

**注意：**步进数字不能有前导 0 。

**示例 1：**

**输入：**low = "1", high = "11"
**输出：**10
**解释：**区间 \[1,11\] 内的步进数字为 1 ，2 ，3 ，4 ，5 ，6 ，7 ，8 ，9 和 10 。总共有 10 个步进数字。所以输出为 10 。

**示例 2：**

**输入：**low = "90", high = "101"
**输出：**2
**解释：**区间 \[90,101\] 内的步进数字为 98 和 101 。总共有 2 个步进数字。所以输出为 2 。

**提示：**

*   `1 <= int(low) <= int(high) < 10100`
*   `1 <= low.length, high.length <= 100`
*   `low` 和 `high` 只包含数字。
*   `low` 和 `high` 都不含前导 0 。

[https://leetcode.cn/problems/count-stepping-numbers-in-range/description/](https://leetcode.cn/problems/count-stepping-numbers-in-range/description/)

```java
import java.util.Arrays;

class Solution {
    private static long Mod = (long) 1e9 + 7;
    char[] s;
    long[][] memo;
    public int countSteppingNumbers(String low, String high) {
        int add = 1;
        for (int i = 1; i < low.length(); i++) {
            if (Math.abs(low.charAt(i) - low.charAt(i - 1)) != 1) {
                add = 0;
                break;
            }
        }
        return (int) ((int) (cal(high) - cal(low) + Mod + add) % Mod); // NOTE 做差取余一定要加MOD
    }

    private long cal(String x) {
        s = x.toCharArray();
        memo = new long[s.length][11];
        for (int i = 0; i < s.length; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 10, true, false);
    }

    private long dfs(int i, int pre, boolean isLimit, boolean isNum) {
        if (i == s.length) {
            return isNum ? 1 : 0;
        }
        if (isNum && !isLimit && memo[i][pre] != -1) {
            return memo[i][pre];
        }
        long res = 0;
        if (!isNum) {
            res = (res + dfs(i + 1, 10, false, false)) % Mod;
        }
        int up = isLimit ? s[i] - '0' : 9;
        for (int j = isNum ? 0 : 1; j <= up; j++) {
            if (pre == 10) {
                res = (res + dfs(i + 1, j, isLimit && j == up, true)) % Mod;
            } else if (Math.abs(j - pre) == 1) {
                res = (res + dfs(i + 1, j, isLimit && j == up, true)) % Mod;
            }
        }
        if (!isLimit) {
            memo[i][pre] = res % Mod;
        }
        return res % Mod;
    }
}
```

1397\. 找到所有好字符串(数位dp+KMP)
---------------

> 本题来源于一家印度公司的面试题，其难度远高于国内和北美的面试难度。如果你觉得此题无从下手、对下一节的「预备知识」一无所知、或者是看不懂这篇题解，都是很正常的。这道题已经达到了竞赛的难度，即使是非常优秀的竞赛选手，也要花至少 10 分钟的时间写出完整（但不一定可读）的代码。竞赛选手有大量的做题基础，因此在遇到这题时几乎不需要思考的时间，可以直接上手编写代码。而对于普通的面试准备者来说，在 40 分钟内理清思路、写出代码、编写测试并给面试官讲解清楚是几乎不可能的。
>
> 作者：力扣官方题解
> 链接：https://leetcode.cn/problems/find-all-good-strings/solutions/186424/zhao-dao-suo-you-hao-zi-fu-chuan-by-leetcode-solut/
> 来源：力扣（LeetCode）
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

> Carl_Czerny锐评：这道题难度确实是比较高，在大家积分普遍不高的远古年代难度分都能达到2666，天坑转码人比赛期间过不了很正常，但我之前也确实高估了，这道题的真实定位应该是VeryHard的守门员，而不是2647题和2699题出现之前主站的天花板……
>
> 作者：Carl_Czerny
> 链接：https://leetcode.cn/problems/find-all-good-strings/solutions/2308661/ji-yi-hua-sou-suo-shi-xian-shu-wei-dpbu-g0no3/
> 来源：力扣（LeetCode）
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

给你两个长度为 `n` 的字符串 `s1` 和 `s2` ，以及一个字符串 `evil` 。请你返回 **好字符串** 的数目。

**好字符串** 的定义为：它的长度为 `n` ，字典序大于等于 `s1` ，字典序小于等于 `s2` ，且不包含 `evil` 为子字符串。

由于答案可能很大，请你返回答案对 10^9 + 7 取余的结果。

**示例 1：**

**输入：**n = 2, s1 = "aa", s2 = "da", evil = "b"
**输出：**51 
**解释：**总共有 25 个以 'a' 开头的好字符串："aa"，"ac"，"ad"，...，"az"。还有 25 个以 'c' 开头的好字符串："ca"，"cc"，"cd"，...，"cz"。最后，还有一个以 'd' 开头的好字符串："da"。

**示例 2：**

**输入：**n = 8, s1 = "leetcode", s2 = "leetgoes", evil = "leet"
**输出：**0 
**解释：**所有字典序大于等于 s1 且小于等于 s2 的字符串都以 evil 字符串 "leet" 开头。所以没有好字符串。

**示例 3：**

**输入：**n = 2, s1 = "gx", s2 = "gz", evil = "x"
**输出：**2

**提示：**

*   `s1.length == n`
*   `s2.length == n`
*   `s1 <= s2`
*   `1 <= n <= 500`
*   `1 <= evil.length <= 50`
*   所有字符串都只包含小写英文字母。

[https://leetcode.cn/problems/find-all-good-strings/description/](https://leetcode.cn/problems/find-all-good-strings/description/)

```java
import java.util.Arrays;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    int n;
    char[] s;
    String evil;
    int[][] memo;
    int[] next;
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        this.evil = evil;
        this.n = n;
        next = getNext(evil);
        int add = 1;
        if (find(s1, evil) != -1) {
            add = 0;
        }
        return (cal(s2) - cal(s1) + Mod + add) % Mod;
    }

    private int cal(String s) {
        this.s = s.toCharArray();
        memo = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }
        return dfs(0, 0, true);
    }

    private int dfs(int i, int j, boolean isLimit) {
        if (j == evil.length()) { // 表示完全匹配
            return 0;
        }
        if (i == n) {
            return 1;
        }
        if (!isLimit && memo[i][j] != -1) {
            return memo[i][j];
        }
        int res = 0;
        char up = isLimit ? s[i] : 'z';
        for (char d = 'a'; d <= up; d++) {
            int nj = j;
            while (nj > 0 && d != evil.charAt(nj)) {
                nj = next[nj - 1];
            }
            // 此处要注意，当 nj == 0 的时候，会存在 d != evil.charAt(nj) 的情况
            // 若直接 nj + 1 进入递归，是认为此时的两个字符一定是匹配上了，实际上可能并没有
            if (nj == 0 && d != evil.charAt(nj)) {
                nj = -1;
            }
            res = (res + dfs(i + 1, nj + 1, isLimit && d == up)) % Mod;
        }
        if (!isLimit) {
            memo[i][j] = res;
        }
        return res;
    }

    private int[] getNext(String pattern) { // 求next数组
        int n = pattern.length();
        int[] next = new int[n];
        for (int i = 1, j = 0; i < n; i++) {
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    private int find(String s, String pattern) { // 返回主串匹配模式串的第一个下标
        int n = s.length();
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && s.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1]; //下一个匹配位为next数组的第j-1位
            }
            if (s.charAt(i) == pattern.charAt(j)) {
                j++; //主串通过i进行加1，模式串通过j加1
            }
            if (j == pattern.length()) {
                return i - j + 1; //返回匹配位置
            }
        }
        return -1;
    }
}
```

