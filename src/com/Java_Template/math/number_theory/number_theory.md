3115\. 质数的最大距离
--------------

给你一个整数数组 `nums`。

返回两个（不一定不同的）质数在 `nums` 中 **下标** 的 **最大距离**。

**示例 1：**

**输入：** nums = \[4,2,9,5,3\]

**输出：** 3

**解释：** `nums[1]`、`nums[3]` 和 `nums[4]` 是质数。因此答案是 `|4 - 1| = 3`。

**示例 2：**

**输入：** nums = \[4,8,2,8\]

**输出：** 0

**解释：** `nums[2]` 是质数。因为只有一个质数，所以答案是 `|2 - 2| = 0`。

**提示：**

*   `1 <= nums.length <= 3 * 105`
*   `1 <= nums[i] <= 100`
*   输入保证 `nums` 中至少有一个质数。

[https://leetcode.cn/problems/maximum-prime-difference/description/](https://leetcode.cn/problems/maximum-prime-difference/description/)

```java
class Solution {
    static int MX = 100;
    static boolean[] pn = new boolean[MX + 1]; // pn[i]==false为素数，pn[i]==true不是素数
    static { // 埃氏筛，时间复杂度MX*log(logMX)不算太高
        pn[0] = pn[1] = true;
        for (int i = 2; i * i <= MX; i++) {
            if (!pn[i]) {
                for (int j = i; j <= MX / i; j++) {
                    pn[j * i] = true;
                }
            }
        }
    }
    public int maximumPrimeDifference(int[] nums) {
        int n = nums.length;
        int left = 0, right = 0;
        for (int i = 0; i < n; i++) {
            if (!pn[nums[i]]) {
                left = i;
                break;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            if (!pn[nums[i]]) {
                right = i;
                break;
            }
        }
        return right - left;
    }
}
```

866\. 回文质数
----------

给你一个整数 `n` ，返回大于或等于 `n` 的最小 **回文质数**。

一个整数如果恰好有两个除数：`1` 和它本身，那么它是 **质数** 。注意，`1` 不是质数。

*   例如，`2`、`3`、`5`、`7`、`11` 和 `13` 都是质数。

一个整数如果从左向右读和从右向左读是相同的，那么它是 **回文数** 。

*   例如，`101` 和 `12321` 都是回文数。

测试用例保证答案总是存在，并且在 `[2, 2 * 108]` 范围内。

**示例 1：**

**输入：**n = 6
**输出：**7

**示例 2：**

**输入：**n = 8
**输出：**11

**示例 3：**

**输入：**n = 13
**输出：**101

**提示：**

*   `1 <= n <= 108`

[https://leetcode.cn/problems/prime-palindrome/description/](https://leetcode.cn/problems/prime-palindrome/description/)

```java
class Solution {
    int MX = (int) 2e8;
    public int primePalindrome(int n) { // 暴力超时了
        for (int i = n; i <= MX; i++) {
            if (judgeIsPrime(i) && judge(i)) {
                return i;
            }
        }
        return -1;
    }

    public boolean judge(int n) { 
        char[] s = Integer.toString(n).toCharArray();
        int len = s.length;
        int i = 0;
        while (i < len - 1 - i) {
            if (s[i] != s[len - 1 - i]) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean judgeIsPrime(int n) {
        if (n == 1) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
```

> 先说结论：长度是偶数的回文数一定是 11 的倍数，所有是 11 的倍数的整数中只有 11 是质数，其他数都是合数。因此 11 是唯一的长度是偶数的回文质数，其余回文质数的长度都是奇数。

```java
class Solution {
    public int primePalindrome(int n) {
        if (n <= 11) {
            int num = n;
            while (!isPrime(num)) {
                num++;
            }
            return num;
        }
        int ans = 0;
        int left = 10; // left是左侧一半数位，但是因为是奇数，所以是包括中间数位的，101，111，121，131，141，，，，202，，，，10001，只会是奇数
        while (ans == 0) {
            int x = generateNum(left);
            if (x >= n && isPrime(x)) {
                ans = x;
            }
            left++;
        }
        return ans;
    }

    private boolean isPrime(int x) { // 快速的求是否是质数
        if (x == 1) {
            return false;
        }
        if (x % 2 == 0) {
            return x == 2;
        }
        for (int i = 3; i * i <= x; i += 2) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    private int generateNum(int left) {
        int res = left;
        left /= 10; // 先退一位
        while (left > 0) {
            res = res * 10 + left % 10;
            left /= 10;
        }
        return res;
    }
}
```

2601\. 质数减法运算
-------------

给你一个下标从 **0** 开始的整数数组 `nums` ，数组长度为 `n` 。

你可以执行无限次下述运算：

*   选择一个之前未选过的下标 `i` ，并选择一个 **严格小于** `nums[i]` 的质数 `p` ，从 `nums[i]` 中减去 `p` 。

如果你能通过上述运算使得 `nums` 成为严格递增数组，则返回 `true` ；否则返回 `false` 。

**严格递增数组** 中的每个元素都严格大于其前面的元素。

**示例 1：**

**输入：**nums = \[4,9,6,10\]
**输出：**true
**解释：**
在第一次运算中：选择 i = 0 和 p = 3 ，然后从 nums\[0\] 减去 3 ，nums 变为 \[1,9,6,10\] 。
在第二次运算中：选择 i = 1 和 p = 7 ，然后从 nums\[1\] 减去 7 ，nums 变为 \[1,2,6,10\] 。
第二次运算后，nums 按严格递增顺序排序，因此答案为 true 。

**示例 2：**

**输入：**nums = \[6,8,11,12\]
**输出：**true
**解释：**nums 从一开始就按严格递增顺序排序，因此不需要执行任何运算。

**示例 3：**

**输入：**nums = \[5,8,3\]
**输出：**false
**解释：**可以证明，执行运算无法使 nums 按严格递增顺序排序，因此答案是 false 。

**提示：**

*   `1 <= nums.length <= 1000`
*   `1 <= nums[i] <= 1000`
*   `nums.length == n`

[https://leetcode.cn/problems/prime-subtraction-operation/](https://leetcode.cn/problems/prime-subtraction-operation/)

```java
import java.util.ArrayList; // 2ms
import java.util.List;

class Solution {
    private static final int MX = 1000;
    private static final List<Integer> primes = new ArrayList<Integer>();

    static {
        primes.add(0);
        boolean[] visited = new boolean[MX + 1];
        for (int i = 2; i < MX; i++) {
            if (!visited[i]) {
                primes.add(i);
                for (int j = i; j <= MX / i; j++) {
                    visited[i * j] = true;
                }
            }
        }
    }

    public boolean primeSubOperation(int[] nums) {
        int pre = 0;
        for (int num : nums) {
            if (num <= pre) {
                return false;
            }
            int idx = lowerBound(primes, num - pre);
            pre = num - primes.get(idx);
        }
        return true;
    }

    private int lowerBound(List<Integer> nums, int target) {
        int left = 0, right = nums.size()-1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums.get(mid) >= target) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return primes.get(left) < target ? left : left - 1;
    }

}
```

```java
import java.util.ArrayList;  // 30ms

class Solution {
    static int MX = (int) 1e6;
    static boolean[] pn = new boolean[MX + 1]; // pn[i]==false为素数，pn[i]==true不是素数
    static ArrayList<Integer> list = new ArrayList<Integer>(); // 预处理质数表
    static { // 埃氏筛，时间复杂度MX*log(logMX)不算太高
        pn[0] = pn[1] = true;
        for (int i = 2; i * i <= MX; i++) {
            if (!pn[i]) {
                for (int j = i; j <= MX / i; j++) {
                    pn[j * i] = true;
                }
            }
        }
        for (int i = 2; i <= MX; i++) {
            if (!pn[i]) {
                list.add(i);
            }
        }
    }
    public boolean primeSubOperation(int[] nums) {
        int pre = 0; // pre 是上一个减完后的数字
        for (int num : nums) {
            if (num <= pre) {
                return false;
            }
            int idx = binarySearch(num - pre);
            if (idx < 0) {
                pre = num;
                continue;
            }
            pre = num - list.get(idx);
        }
        return true;
    }

    private int binarySearch(int target) {
        int left = 0, right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left - 1;
    }


}
```

