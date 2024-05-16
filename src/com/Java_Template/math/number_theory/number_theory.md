# §1.1 判断质数

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

# §1.2 预处理质数（筛质数）

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

# §1.3 质因数分解

2521\. 数组乘积中的不同质因数数目
--------------------

给你一个正整数数组 `nums` ，对 `nums` 所有元素求积之后，找出并返回乘积中 **不同质因数** 的数目。

**注意：**

*   **质数** 是指大于 `1` 且仅能被 `1` 及自身整除的数字。
*   如果 `val2 / val1` 是一个整数，则整数 `val1` 是另一个整数 `val2` 的一个因数。

**示例 1：**

**输入：**nums = \[2,4,3,7,10,6\]
**输出：**4
**解释：**
nums 中所有元素的乘积是：2 \* 4 \* 3 \* 7 \* 10 \* 6 = 10080 = 25 \* 32 \* 5 \* 7 。
共有 4 个不同的质因数，所以返回 4 。

**示例 2：**

**输入：**nums = \[2,4,8,16\]
**输出：**1
**解释：**
nums 中所有元素的乘积是：2 \* 4 \* 8 \* 16 = 1024 = 210 。
共有 1 个不同的质因数，所以返回 1 。

**提示：**

*   `1 <= nums.length <= 104`
*   `2 <= nums[i] <= 1000`

[https://leetcode.cn/problems/distinct-prime-factors-of-product-of-array/description/](https://leetcode.cn/problems/distinct-prime-factors-of-product-of-array/description/)

```java
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
 
class Solution { // 暴力，因为数据范围小，66ms
    static int MX = (int) 1e3;
    static boolean[] pn = new boolean[MX + 1];
    static List<Integer> list = new ArrayList<Integer>();
    static {
        pn[0] = pn[1] = true;
        for (int i = 2; i <= MX; i++) {
            if (!pn[i]) {
                list.add(i);
                for (int j = i; j <= MX / i; j++) {
                    pn[j * i] = true;
                }
            }
        }
    }
    public int distinctPrimeFactors(int[] nums) {
        HashSet<Integer> cnt = new HashSet<>();
        for (int num : nums) {
            if (!pn[num]) {
                cnt.add(num);
                continue;
            }
            for (int x : list) {
                if (num % x == 0) {
                    cnt.add(x);
                }
                if (x >= num) {
                    break;
                }
            }
        }
        return cnt.size();
    }

}
```

> 这题其实根本不用算质数，从2开始计算时，遍历的num对2除完之后，后面所有的非质数4、6、8、10、12...都无法被除完，可以自动跳过，同理遇到3，后面的6、9、12、15...一样也可以被跳过。 

```java
import java.util.HashSet;

class Solution {
    public int distinctPrimeFactors(int[] nums) { // 求质因数的模板 10ms
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            for (int i = 2; i * i <= num; i++) {
                if (num % i == 0) {
                    set.add(i);
                    while (num % i == 0) {
                        num /= i;
                    }
                }
            }
            if (num > 1) {
                set.add(num);
            }
        }
        return set.size();
    }
}
```

2507\. 使用质因数之和替换后可以取到的最小值
-------------------------

给你一个正整数 `n` 。

请你将 `n` 的值替换为 `n` 的 **质因数** 之和，重复这一过程。

*   注意，如果 `n` 能够被某个质因数多次整除，则在求和时，应当包含这个质因数同样次数。

返回 `n` 可以取到的最小值。

**示例 1：**

**输入：**n = 15
**输出：**5
**解释：**最开始，n = 15 。
15 = 3 \* 5 ，所以 n 替换为 3 + 5 = 8 。
8 = 2 \* 2 \* 2 ，所以 n 替换为 2 + 2 + 2 = 6 。
6 = 2 \* 3 ，所以 n 替换为 2 + 3 = 5 。
5 是 n 可以取到的最小值。

**示例 2：**

**输入：**n = 3
**输出：**3
**解释：**最开始，n = 3 。
3 是 n 可以取到的最小值。

**提示：**

*   `2 <= n <= 105`

[https://leetcode.cn/problems/smallest-value-after-replacing-with-sum-of-prime-factors/description/](https://leetcode.cn/problems/smallest-value-after-replacing-with-sum-of-prime-factors/description/)

```java
class Solution {
    public int smallestValue(int n) { // 分解质因子模板
        while (true) {
            int x = n, s = 0;
            for (int i = 2; i * i <= x; i++) {
                while (x % i == 0) {
                    s += i;
                    x /= i;
                }
            }
            if (x > 1) {
                s += x;
            }
            if (s == n) {
                return s;
            }
            n = s;
        }
    }
}
```

2584\. 分割数组使乘积互质
----------------

给你一个长度为 `n` 的整数数组 `nums` ，下标从 **0** 开始。

如果在下标 `i` 处 **分割** 数组，其中 `0 <= i <= n - 2` ，使前 `i + 1` 个元素的乘积和剩余元素的乘积互质，则认为该分割 **有效** 。

*   例如，如果 `nums = [2, 3, 3]` ，那么在下标 `i = 0` 处的分割有效，因为 `2` 和 `9` 互质，而在下标 `i = 1` 处的分割无效，因为 `6` 和 `3` 不互质。在下标 `i = 2` 处的分割也无效，因为 `i == n - 1` 。

返回可以有效分割数组的最小下标 `i` ，如果不存在有效分割，则返回 `-1` 。

当且仅当 `gcd(val1, val2) == 1` 成立时，`val1` 和 `val2` 这两个值才是互质的，其中 `gcd(val1, val2)` 表示 `val1` 和 `val2` 的最大公约数。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/12/14/second.PNG)

**输入：**nums = \[4,7,8,15,3,5\]
**输出：**2
**解释：**上表展示了每个下标 i 处的前 i + 1 个元素的乘积、剩余元素的乘积和它们的最大公约数的值。
唯一一个有效分割位于下标 2 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/12/14/capture.PNG)

**输入：**nums = \[4,7,15,8,3,5\]
**输出：**\-1
**解释：**上表展示了每个下标 i 处的前 i + 1 个元素的乘积、剩余元素的乘积和它们的最大公约数的值。
不存在有效分割。

**提示：**

*   `n == nums.length`
*   `1 <= n <= 104`
*   `1 <= nums[i] <= 106`

[https://leetcode.cn/problems/split-the-array-to-make-coprime-products/description/](https://leetcode.cn/problems/split-the-array-to-make-coprime-products/description/)

```java
import java.util.HashMap;

class Solution {
    public int findValidSplit(int[] nums) {
        int n = nums.length;
        HashMap<Integer, Integer> left = new HashMap<>(); // left[p] 表示质数 p 首次出现的下标
        int[] right = new int[n]; // right[i] 表示左端点为 i 的区间的右端点的最大值
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            for (int j = 2; j * j <= x; j++) { // 分解质因子模板
                if (x % j == 0) {
                    if (left.containsKey(j)) {
                        right[left.get(j)] = i;
                    }else{
                        left.put(j, i);
                    }
                    while (x % j == 0) {
                        x /= j;
                    }
                }
            }
            if (x > 1) {
                if (left.containsKey(x)) {
                    right[left.get(x)] = i;
                }else{
                    left.put(x, i);
                }
            }
        }
        int maxR = 0;
        for (int l = 0; l < n; l++) { // 左节点
            if (l > maxR) {
                return maxR;
            }
            maxR = Math.max(maxR, right[l]);
        }
        return -1;
    }
}
```

2709\. 最大公约数遍历(并查集+分解质因子模板)
--------------

给你一个下标从 **0** 开始的整数数组 `nums` ，你可以在一些下标之间遍历。对于两个下标 `i` 和 `j`（`i != j`），当且仅当 `gcd(nums[i], nums[j]) > 1` 时，我们可以在两个下标之间通行，其中 `gcd` 是两个数的 **最大公约数** 。

你需要判断 `nums` 数组中 **任意** 两个满足 `i < j` 的下标 `i` 和 `j` ，是否存在若干次通行可以从 `i` 遍历到 `j` 。

如果任意满足条件的下标对都可以遍历，那么返回 `true` ，否则返回 `false` 。

**示例 1：**

**输入：**nums = \[2,3,6\]
**输出：**true
**解释：**这个例子中，总共有 3 个下标对：(0, 1) ，(0, 2) 和 (1, 2) 。
从下标 0 到下标 1 ，我们可以遍历 0 -> 2 -> 1 ，我们可以从下标 0 到 2 是因为 gcd(nums\[0\], nums\[2\]) = gcd(2, 6) = 2 > 1 ，从下标 2 到 1 是因为 gcd(nums\[2\], nums\[1\]) = gcd(6, 3) = 3 > 1 。
从下标 0 到下标 2 ，我们可以直接遍历，因为 gcd(nums\[0\], nums\[2\]) = gcd(2, 6) = 2 > 1 。同理，我们也可以从下标 1 到 2 因为 gcd(nums\[1\], nums\[2\]) = gcd(3, 6) = 3 > 1 。

**示例 2：**

**输入：**nums = \[3,9,5\]
**输出：**false
**解释：**我们没法从下标 0 到 2 ，所以返回 false 。

**示例 3：**

**输入：**nums = \[4,3,12,8\]
**输出：**true
**解释：**总共有 6 个下标对：(0, 1) ，(0, 2) ，(0, 3) ，(1, 2) ，(1, 3) 和 (2, 3) 。所有下标对之间都存在可行的遍历，所以返回 true 。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i] <= 105`

[https://leetcode.cn/problems/greatest-common-divisor-traversal/description/](https://leetcode.cn/problems/greatest-common-divisor-traversal/description/)

```java
import java.util.Arrays;

class Solution {
    static int MX = (int) 1e5 + 10;
    public boolean canTraverseAllPairs(int[] nums) {
        int n = nums.length;
        int[] parent = new int[MX];
        Arrays.setAll(parent, i -> i);
        for (int num : nums) {
            int x = num;
            for (int i = 2; i * i <= num; i++) {
                if (num % i == 0) {
                    union(parent, i, x);
                    union(parent, i, num);
                    while (num % i == 0) {
                        num /= i;
                    }
                }
            }
            if (num > 1) {
                union(parent, num, x);
            }
        }
        for (int i = 1; i < n; i++) {
            if (nums[i] == 1 || find(parent, nums[i]) != find(parent, nums[i - 1])) {
                return false;
            }
        }
        return true;

    }

    public void union(int[] parent, int index1, int index2) {
        parent[find(parent, index1)] = find(parent, index2);
    }

    public int find(int[] parent, int index) {
        if (parent[index] != index) {
            parent[index] = find(parent, parent[index]);
        }
        return parent[index];
    }
}
```

2862\. 完全子集的最大元素和(core函数的应用)
-----------------

给你一个下标从 **1** 开始、由 `n` 个整数组成的数组。你需要从 `nums` 选择一个 **完全集**，其中每对元素下标的乘积都是一个

完全平方数

，例如选择 `ai` 和 `aj` ，`i * j` 一定是完全平方数。

返回 **完全子集** 所能取到的 **最大元素和** 。

**示例 1：**

**输入：**nums = \[8,7,3,5,7,2,4,9\]
**输出：**16
**解释：**我们选择了下标 2 和 8 的元素，并且 2 \* 8 是一个完全平方数。

**示例 2：**

**输入：**nums = \[8,10,3,8,1,13,7,9,4\]
**输出：**20
**解释：**我们选择了下标 1，4 和 9 的元素。1 \* 4，1 \* 9，4 \* 9 都是完全平方数。

**提示：**

*   `1 <= n == nums.length <= 104`
*   `1 <= nums[i] <= 109`

[https://leetcode.cn/problems/maximum-element-sum-of-a-complete-subset-of-indices/description/](https://leetcode.cn/problems/maximum-element-sum-of-a-complete-subset-of-indices/description/)

```java
import java.util.List;

class Solution {
    public long maximumSum(List<Integer> nums) {
        long ans = 0;
        int n = nums.size();
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            int c = core(i + 1);
            sum[c] += nums.get(i);
            ans = Math.max(ans, sum[c]);
        }
        return ans;
    }

    // 定义 core(n) 为 n 除去完全平方因子后的剩余结果。
    private int core(int n) {
        int res = 1;
        for (int i = 2; i * i <= n; i++) {
            int e = 0;
            while (n % i == 0) {
                e ^= 1;
                n /= i;
            }
            if (e == 1) { // 只统计奇数的质因子
                res *= i;
            }
        }
        if (n > 1) {
            res *= n;
        }
        return res;
    }
}
```

# §1.4 阶乘分解

172\. 阶乘后的零
-----------

给定一个整数 `n` ，返回 `n!` 结果中尾随零的数量。

提示 `n! = n * (n - 1) * (n - 2) * ... * 3 * 2 * 1`

**示例 1：**

**输入：**n = 3
**输出：**0
**解释：**3! = 6 ，不含尾随 0

**示例 2：**

**输入：**n = 5
**输出：**1
**解释：**5! = 120 ，有一个尾随 0

**示例 3：**

**输入：**n = 0
**输出：**0

**提示：**

*   `0 <= n <= 104`

**进阶：**你可以设计并实现对数时间复杂度的算法来解决此问题吗？

[https://leetcode.cn/problems/factorial-trailing-zeroes/solutions/1366037/by-ac\_oier-1y6w/](https://leetcode.cn/problems/factorial-trailing-zeroes/solutions/1366037/by-ac_oier-1y6w/)

```java
class Solution {
    public int trailingZeroes(int n) { // 算一下乘法因子里有多少个5就是了
        int ans = 0;
        while (n >= 5) {
            ans += n / 5;
            n /= 5;
        }
        return ans;
    }
}
```

793\. 阶乘函数后 K 个零
----------------

 `f(x)` 是 `x!` 末尾是 0 的数量。回想一下 `x! = 1 * 2 * 3 * ... * x`，且 `0! = 1` 。

*   例如， `f(3) = 0` ，因为 `3! = 6` 的末尾没有 0 ；而 `f(11) = 2` ，因为 `11!= 39916800` 末端有 2 个 0 。

给定 `k`，找出返回能满足 `f(x) = k` 的非负整数 `x` 的数量。

**示例 1：**

**输入：**k = 0
**输出：**5
**解释：**0!, 1!, 2!, 3!, 和 4! 均符合 k = 0 的条件。

**示例 2：**

**输入：**k = 5
**输出：**0
**解释：**没有匹配到这样的 x!，符合 k = 5 的条件。

**示例 3:**

**输入:** k = 3
**输出:** 5

**提示:**

*   `0 <= k <= 109`

[https://leetcode.cn/problems/preimage-size-of-factorial-zeroes-function/description/](https://leetcode.cn/problems/preimage-size-of-factorial-zeroes-function/description/)

```java
class Solution {
    public int preimageSizeFZF(int k) {
        return (int) (help(k + 1) - help(k));
    }

    private long help(int k) {
        long left = 0, right = 5L * k;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (zeta(mid) < k) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return right + 1;
    }

    private long zeta(long n) {
        long ans = 0;
        while (n >= 5) {
            n /= 5;
            ans += n;
        }
        return ans;
    }
}
```

# §1.5 因子

2427\. 公因子的数目
-------------

给你两个正整数 `a` 和 `b` ，返回 `a` 和 `b` 的 **公** 因子的数目。

如果 `x` 可以同时整除 `a` 和 `b` ，则认为 `x` 是 `a` 和 `b` 的一个 **公因子** 。

**示例 1：**

**输入：**a = 12, b = 6
**输出：**4
**解释：**12 和 6 的公因子是 1、2、3、6 。

**示例 2：**

**输入：**a = 25, b = 30
**输出：**2
**解释：**25 和 30 的公因子是 1、5 。

**提示：**

*   `1 <= a, b <= 1000`

[https://leetcode.cn/problems/number-of-common-factors/submissions/532214510/](https://leetcode.cn/problems/number-of-common-factors/submissions/532214510/)

```java
class Solution {
    /*
     * 枚举因子，挨个判断能否整除 a 和 b。改进方案是枚举 a 和 b 的最大公因数的因子。
     */
    public int commonFactors(int a, int b) {
        int g = gcd(a, b);
        int ans = 0;
        for (int i = 1; i * i <= g; i++) {
            if (g % i == 0) {
                ans++; // i 是公因子
                if (i * i < g) {
                    ans++; // g/i 是公因子
                }
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

1492\. n 的第 k 个因子
-----------------

给你两个正整数 `n` 和 `k` 。

如果正整数 `i` 满足 `n % i == 0` ，那么我们就说正整数 `i` 是整数 `n` 的因子。

考虑整数 `n` 的所有因子，将它们 **升序排列** 。请你返回第 `k` 个因子。如果 `n` 的因子数少于 `k` ，请你返回 `-1` 。

**示例 1：**

**输入：**n = 12, k = 3
**输出：**3
**解释：**因子列表包括 \[1, 2, 3, 4, 6, 12\]，第 3 个因子是 3 。

**示例 2：**

**输入：**n = 7, k = 2
**输出：**7
**解释：**因子列表包括 \[1, 7\] ，第 2 个因子是 7 。

**示例 3：**

**输入：**n = 4, k = 4
**输出：**\-1
**解释：**因子列表包括 \[1, 2, 4\] ，只有 3 个因子，所以我们应该返回 -1 。

**提示：**

*   `1 <= k <= n <= 1000`

**进阶：**

你可以设计时间复杂度小于 O(n) 的算法来解决此问题吗？

[https://leetcode.cn/problems/the-kth-factor-of-n/description/](https://leetcode.cn/problems/the-kth-factor-of-n/description/)

```java
class Solution {
    public int kthFactor(int n, int k){ // o(n)
        int ans = -1, i = 1;
        while (k > 0 && i <= n) {
            if (n % i == 0) {
                k--;
                ans = i;
            }
            i++;
        }
        return k == 0 ? ans : -1;
    }
}
```

```java
class Solution {
    public int kthFactor(int n, int k) { // o(n^(1/2))
        int factor = 1, count = 0;
        while (factor * factor < n) {
            if (n % factor == 0) {
                count++;
                if (count == k) {
                    return factor;
                }
            }
            factor++;
        }
        int total = count * 2;
        if (factor * factor == n) {
            total++;
        }
        factor = 1;
        count = 0;
        while (factor * factor <= n) {
            if (n % factor == 0) {
                count++;
                if (count == total - k + 1) {
                    return n / factor;
                }
            }
            factor++;
        }
        return -1;
    }
}
```

1390\. 四因数
----------

给你一个整数数组 `nums`，请你返回该数组中恰有四个因数的这些整数的各因数之和。如果数组中不存在满足题意的整数，则返回 `0` 。

**示例 1：**

**输入：**nums = \[21,4,7\]
**输出：**32
**解释：**
21 有 4 个因数：1, 3, 7, 21
4 有 3 个因数：1, 2, 4
7 有 2 个因数：1, 7
答案仅为 21 的所有因数的和。

**示例 2:**

**输入:** nums = \[21,21\]
**输出:** 64

**示例 3:**

**输入:** nums = \[1,2,3,4,5\]
**输出:** 0

**提示：**

*   `1 <= nums.length <= 104`
*   `1 <= nums[i] <= 105`

[https://leetcode.cn/problems/four-divisors/](https://leetcode.cn/problems/four-divisors/)

```java
class Solution {
    public int sumFourDivisors(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            int cnt = 0, sum = 0;
            for (int i = 1; i * i <= num; i++) { // 这里不能省掉 i*i == num, 我一直以为完全平方数不行，但是你看625，由1，625，25，5组成！！！
                if (num % i == 0) {
                    cnt++;
                    sum += i;
                    if (i * i != num) {
                        cnt++;
                        sum += num / i;
                    }
                }
            }
            if (cnt == 4) {
                ans += sum;
            }
        }
        return ans;
    }
}
```

1362\. 最接近的因数
-------------

给你一个整数 `num`，请你找出同时满足下面全部要求的两个整数：

*   两数乘积等于  `num + 1` 或 `num + 2`
*   以绝对差进行度量，两数大小最接近

你可以按任意顺序返回这两个整数。

**示例 1：**

**输入：**num = 8
**输出：**\[3,3\]
**解释：**对于 num + 1 = 9，最接近的两个因数是 3 & 3；对于 num + 2 = 10, 最接近的两个因数是 2 & 5，因此返回 3 & 3 。

**示例 2：**

**输入：**num = 123
**输出：**\[5,25\]

**示例 3：**

**输入：**num = 999
**输出：**\[40,25\]

**提示：**

*   `1 <= num <= 10^9`

[https://leetcode.cn/problems/closest-divisors/submissions/532230155/](https://leetcode.cn/problems/closest-divisors/submissions/532230155/)

```java
class Solution {
    public int[] closestDivisors(int num) {
        int[] ans = new int[2];
        int diff = Integer.MAX_VALUE;
        for (int i = 1; i * i <= num + 2; i++) {
            if ((num + 1) % i == 0) {
                if (diff > Math.abs((num + 1) / i - i)) {
                    ans = new int[]{(num + 1) / i, i};
                    diff = Math.abs((num + 1) / i - i);
                }
            }
            if ((num + 2) % i == 0) {
                if (diff > Math.abs((num + 2) / i - i)) {
                    ans = new int[]{(num + 2) / i, i};
                    diff = Math.abs((num + 2) / i - i);
                }
            }
        }
        return ans;
    }

}
```

829\. 连续整数求和
------------

给定一个正整数 `n`，返回 _连续正整数满足所有数字之和为 `n` 的组数_ 。 

**示****例 1:**

**输入:** n = 5
**输出:** 2
**解释:** 5 = 2 + 3，共有两组连续整数(\[5\],\[2,3\])求和后为 5。

**示例 2:**

**输入:** n = 9
**输出:** 3
**解释:** 9 = 4 + 5 = 2 + 3 + 4

**示例 3:**

**输入:** n = 15
**输出:** 4
**解释:** 15 = 8 + 7 = 4 + 5 + 6 = 1 + 2 + 3 + 4 + 5

**提示:**

*   `1 <= n <= 109`​​​​​​​

[https://leetcode.cn/problems/consecutive-numbers-sum/description/](https://leetcode.cn/problems/consecutive-numbers-sum/description/)

```java
class Solution {
    public int consecutiveNumbersSum(int n) {
        int ans = 0;
        n *= 2;
        for (int i = 1; i * i < n; i++) {
            if (n % i != 0) {
                continue;
            }
            if ((n / i - (i - 1)) % 2 == 0) {
                ans++;
            }
        }
        return ans;
    }
}
```

952\. 按公因数计算最大组件大小
------------------

给定一个由不同正整数的组成的非空数组 `nums` ，考虑下面的图：

*   有 `nums.length` 个节点，按从 `nums[0]` 到 `nums[nums.length - 1]` 标记；
*   只有当 `nums[i]` 和 `nums[j]` 共用一个大于 1 的公因数时，`nums[i]` 和 `nums[j]`之间才有一条边。

返回 _图中最大连通组件的大小_ 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2018/12/01/ex1.png)

**输入：**nums = \[4,6,15,35\]
**输出：**4

**示例 2：**

![](https://assets.leetcode.com/uploads/2018/12/01/ex2.png)

**输入：**nums = \[20,50,9,63\]
**输出：**2

**示例 3：**

![](https://assets.leetcode.com/uploads/2018/12/01/ex3.png)

**输入：**nums = \[2,3,6,7,4,12,21,39\]
**输出：**8

**提示：**

*   `1 <= nums.length <= 2 * 104`
*   `1 <= nums[i] <= 105`
*   `nums` 中所有值都 **不同**

[https://leetcode.cn/problems/largest-component-size-by-common-factor/](https://leetcode.cn/problems/largest-component-size-by-common-factor/)

```java
import java.util.Arrays;
import java.util.HashMap;

class Solution {
    private static int MX = (int) 1e5 + 10;
    public int largestComponentSize(int[] nums) {
        int[] parent = new int[MX];
        Arrays.setAll(parent, i -> i);
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int g = nums[i];
            if (g > 1) {
                for (int j = 2; j*j <= g; j++) { // 这里如果写j<g直接超时
                    if (g % j == 0) {
                        union(parent, j, g);
                        union(parent, g / j, g);
                    }
                    // while (g % j == 0) { 加上这一段直接报错，确实！
                    //    g /= j;
                    //}
                }
                // if (g > 1) {
                //    union(parent, g, nums[i]);
                // }
            }
        }
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.merge(find(parent, num), 1, Integer::sum);
        }
        int ans = 1;
        for (int c : cnt.values()) {
            ans = Math.max(ans, c);
        }
        return ans;
    }

    private int find(int[] parent, int index) {
        if (parent[index] != index) {
            parent[index] = find(parent, parent[index]);
        }
        return parent[index];
    }

    private void union(int[] parent, int index1, int index2) {
        parent[find(parent, index1)] = find(parent, parent[index2]);
    }
}
```

1627\. 带阈值的图连通性
---------------

有 `n` 座城市，编号从 `1` 到 `n` 。编号为 `x` 和 `y` 的两座城市直接连通的前提是： `x` 和 `y` 的公因数中，至少有一个 **严格大于** 某个阈值 `threshold` 。更正式地说，如果存在整数 `z` ，且满足以下所有条件，则编号 `x` 和 `y` 的城市之间有一条道路：

*   `x % z == 0`
*   `y % z == 0`
*   `z > threshold`

给你两个整数 `n` 和 `threshold` ，以及一个待查询数组，请你判断每个查询 `queries[i] = [ai, bi]` 指向的城市 `ai` 和 `bi` 是否连通（即，它们之间是否存在一条路径）。

返回数组 `answer` ，其中`answer.length == queries.length` 。如果第 `i` 个查询中指向的城市 `ai` 和 `bi` 连通，则 `answer[i]` 为 `true` ；如果不连通，则 `answer[i]` 为 `false` 。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/10/18/ex1.jpg)

**输入：**n = 6, threshold = 2, queries = \[\[1,4\],\[2,5\],\[3,6\]\]
**输出：**\[false,false,true\]
**解释：**每个数的因数如下：
1:   1
2:   1, 2
3:   1, **3**
4:   1, 2, **4**
5:   1, **5**
6:   1, 2, **3**, **6**
所有大于阈值的的因数已经加粗标识，只有城市 3 和 6 共享公约数 3 ，因此结果是： 
\[1,4\]   1 与 4 不连通
\[2,5\]   2 与 5 不连通
\[3,6\]   3 与 6 连通，存在路径 3--6

**示例 2：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/10/18/tmp.jpg)

**输入：**n = 6, threshold = 0, queries = \[\[4,5\],\[3,4\],\[3,2\],\[2,6\],\[1,3\]\]
**输出：**\[true,true,true,true,true\]
**解释：**每个数的因数与上一个例子相同。但是，由于阈值为 0 ，所有的因数都大于阈值。因为所有的数字共享公因数 1 ，所以所有的城市都互相连通。

**示例 3：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/10/16/ex3.jpg)

**输入：**n = 5, threshold = 1, queries = \[\[4,5\],\[4,5\],\[3,2\],\[2,3\],\[3,4\]\]
**输出：**\[false,false,false,false,false\]
**解释：**只有城市 2 和 4 共享的公约数 2 严格大于阈值 1 ，所以只有这两座城市是连通的。
注意，同一对节点 \[x, y\] 可以有多个查询，并且查询 \[x，y\] 等同于查询 \[y，x\] 。

**提示：**

*   `2 <= n <= 104`
*   `0 <= threshold <= n`
*   `1 <= queries.length <= 105`
*   `queries[i].length == 2`
*   `1 <= ai, bi <= cities`
*   `ai != bi`

[https://leetcode.cn/problems/graph-connectivity-with-threshold/description/](https://leetcode.cn/problems/graph-connectivity-with-threshold/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<Boolean> areConnected(int n, int threshold, int[][] queries) {
        ArrayList<Boolean> ans = new ArrayList<>();
        int[] parent = new int[n + 1];
        Arrays.setAll(parent, i -> i);
        for (int i = threshold + 1; i <= n; i++) {
            for (int j = 2; i * j <= n; j++) {
                union(parent, i, i * j);
            }
        }
        for (int[] q : queries) {
            ans.add(find(parent, q[0]) == find(parent, q[1]));
        }
        return ans;
    }

    private int find(int[] p, int i) {
        if (p[i] != i) {
            p[i] = find(p, p[i]);
        }
        return p[i];
    }

    private void union(int[] p, int i1, int i2) {
        p[find(p, i2)] = find(p, i1);
    }


//    private int find(int[] parent, int index) {
//        if (parent[index] != index) {
//            parent[index] = find(parent, parent[index]);
//        }
//        return parent[index];
//    }
//
//    private void union(int[] parent, int index1, int index2) {
//        parent[find(parent, index1)] = find(parent, parent[index2]);
//    }
}
```

2183\. 统计可以被 K 整除的下标对数目（预处理：统计每个数的因子模板）
-----------------------

给你一个下标从 **0** 开始、长度为 `n` 的整数数组 `nums` 和一个整数 `k` ，返回满足下述条件的下标对 `(i, j)` 的数目：

*   `0 <= i < j <= n - 1` 且
*   `nums[i] * nums[j]` 能被 `k` 整除。

**示例 1：**

**输入：**nums = \[1,2,3,4,5\], k = 2
**输出：**7
**解释：**
共有 7 对下标的对应积可以被 2 整除：
(0, 1)、(0, 3)、(1, 2)、(1, 3)、(1, 4)、(2, 3) 和 (3, 4)
它们的积分别是 2、4、6、8、10、12 和 20 。
其他下标对，例如 (0, 2) 和 (2, 4) 的乘积分别是 3 和 15 ，都无法被 2 整除。    

**示例 2：**

**输入：**nums = \[1,2,3,4\], k = 5
**输出：**0
**解释：**不存在对应积可以被 5 整除的下标对。

**提示：**

*   `1 <= nums.length <= 105`
*   `1 <= nums[i], k <= 105`

[https://leetcode.cn/problems/count-array-pairs-divisible-by-k/description/](https://leetcode.cn/problems/count-array-pairs-divisible-by-k/description/)

![1715854096139](assets/1715854096139-1715858746726.png)

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {
    private static int mx = (int) 1e5 + 1;
    private static ArrayList<List<Integer>> list = new ArrayList<>(mx);
    static { // 预处理：统计每个数的因子模板
        for (int i = 0; i < mx; i++) {
            list.add(new ArrayList<>());
        }
        // 预处理每个数的所有因子，时间复杂度 O(MlogM)，M=1e5
        for (int i = 1; i < mx; i++) {
            for (int j = i; j < mx; j += i) {
                list.get(j).add(i); // j的因子是i
            }
        }
    }

    public long countPairs(int[] nums, int k) {
        long ans = 0L;
        HashMap<Integer, Integer> cnt = new HashMap<>(); // {一个因子，比如2：num种2的倍数出现次数}
        for (int num : nums) {
            ans += cnt.getOrDefault(k / gcd(num, k), 0);
            for (int x : list.get(num)) { // num的所有因子
                cnt.put(x, cnt.getOrDefault(x, 0) + 1);
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

```java
import java.util.ArrayList;
import java.util.HashMap;

class Solution {
    public long countPairs(int[] nums, int k) {
        // 预处理k的所有因子
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i * i <= k; i++) {
            if (k % i == 0) {
                list.add(i);
                if (i * i < k) {
                    list.add(k / i);
                }
            }
        }
        long ans = 0L;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            ans += cnt.getOrDefault(k / gcd(k, num), 0);
            for (int d : list) {
                if (num % d == 0) {
                    cnt.put(d, cnt.getOrDefault(d, 0) + 1);
                }
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

# §1.6 最大公约数（GCD）

914\. 卡牌分组
----------

给定一副牌，每张牌上都写着一个整数。

此时，你需要选定一个数字 `X`，使我们可以将整副牌按下述规则分成 1 组或更多组：

*   每组都有 `X` 张牌。
*   组内所有的牌上都写着相同的整数。

仅当你可选的 `X >= 2` 时返回 `true`。

**示例 1：**

**输入：**deck = \[1,2,3,4,4,3,2,1\]
**输出：**true
**解释：**可行的分组是 \[1,1\]，\[2,2\]，\[3,3\]，\[4,4\]

**示例 2：**

**输入：**deck = \[1,1,1,2,2,2,3,3\]
**输出：**false
**解释：**没有满足要求的分组。


**提示：**

*   `1 <= deck.length <= 104`
*   `0 <= deck[i] < 104`

[https://leetcode.cn/problems/x-of-a-kind-in-a-deck-of-cards/description/](https://leetcode.cn/problems/x-of-a-kind-in-a-deck-of-cards/description/)

```java
import java.util.HashMap;

class Solution {
    public boolean hasGroupsSizeX(int[] deck) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int d : deck) {
            cnt.merge(d, 1, Integer::sum);
        }
        int pre = -1;
        for (int x : cnt.values()) {
            if (x < 2) {
                return false;
            }
            if (pre == -1) {
                pre = x;
            }else{
                pre = gcd(pre, x);
            }
        }
        return pre > 1;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

2001\. 可互换矩形的组数
---------------

用一个下标从 **0** 开始的二维整数数组 `rectangles` 来表示 `n` 个矩形，其中 `rectangles[i] = [widthi, heighti]` 表示第 `i` 个矩形的宽度和高度。

如果两个矩形 `i` 和 `j`（`i < j`）的宽高比相同，则认为这两个矩形 **可互换** 。更规范的说法是，两个矩形满足 `widthi/heighti == widthj/heightj`（使用实数除法而非整数除法），则认为这两个矩形 **可互换** 。

计算并返回 `rectangles` 中有多少对 **可互换** 矩形。

**示例 1：**

**输入：**rectangles = \[\[4,8\],\[3,6\],\[10,20\],\[15,30\]\]
**输出：**6
**解释：**下面按下标（从 0 开始）列出可互换矩形的配对情况：
- 矩形 0 和矩形 1 ：4/8 == 3/6
- 矩形 0 和矩形 2 ：4/8 == 10/20
- 矩形 0 和矩形 3 ：4/8 == 15/30
- 矩形 1 和矩形 2 ：3/6 == 10/20
- 矩形 1 和矩形 3 ：3/6 == 15/30
- 矩形 2 和矩形 3 ：10/20 == 15/30

**示例 2：**

**输入：**rectangles = \[\[4,5\],\[7,8\]\]
**输出：**0
**解释：**不存在成对的可互换矩形。

**提示：**

*   `n == rectangles.length`
*   `1 <= n <= 105`
*   `rectangles[i].length == 2`
*   `1 <= widthi, heighti <= 105`

[https://leetcode.cn/problems/number-of-pairs-of-interchangeable-rectangles/description/](https://leetcode.cn/problems/number-of-pairs-of-interchangeable-rectangles/description/)

```java
import java.util.HashMap;

class Solution {
    public long interchangeableRectangles(int[][] rectangles) {
        HashMap<Integer, Integer> cnt = new HashMap<>();
        long ans = 0;
        for (int[] r : rectangles) {
            int g = gcd(r[0], r[1]);
            int k = r[0] / g * 100000 + r[1] / g;
            ans += cnt.getOrDefault(k, 0);
            cnt.merge(k, 1, Integer::sum);
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

2344\. 使数组可以被整除的最少删除次数
----------------------

给你两个正整数数组 `nums` 和 `numsDivide` 。你可以从 `nums` 中删除任意数目的元素。

请你返回使 `nums` 中 **最小** 元素可以整除 `numsDivide` 中所有元素的 **最少** 删除次数。如果无法得到这样的元素，返回 `-1` 。

如果 `y % x == 0` ，那么我们说整数 `x` 整除 `y` 。

**示例 1：**

**输入：**nums = \[2,3,2,4,3\], numsDivide = \[9,6,9,3,15\]
**输出：**2
**解释：**
\[2,3,2,4,3\] 中最小元素是 2 ，它无法整除 numsDivide 中所有元素。
我们从 nums 中删除 2 个大小为 2 的元素，得到 nums = \[3,4,3\] 。
\[3,4,3\] 中最小元素为 3 ，它可以整除 numsDivide 中所有元素。
可以证明 2 是最少删除次数。

**示例 2：**

**输入：**nums = \[4,3,6\], numsDivide = \[8,2,6,10\]
**输出：**\-1
**解释：**
我们想 nums 中的最小元素可以整除 numsDivide 中的所有元素。
没有任何办法可以达到这一目的。

**提示：**

*   `1 <= nums.length, numsDivide.length <= 105`
*   `1 <= nums[i], numsDivide[i] <= 109`

[https://leetcode.cn/problems/minimum-deletions-to-make-array-divisible/description/](https://leetcode.cn/problems/minimum-deletions-to-make-array-divisible/description/)

```java
import java.util.Arrays;

class Solution {
    public int minOperations(int[] nums, int[] numsDivide) {
        int g = 0;
        for (int num : numsDivide) { // gcd(0,x) = x
            g = gcd(g, num);
        }
        Arrays.sort(nums);
        int ans = 0;
        for (int num : nums) {
            if (g % num == 0) { // 只要是 g 的因子就行
                return ans;
            } else {
                ans++;
            }
        }
        return -1;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

858\. 镜面反射
----------

有一个特殊的正方形房间，每面墙上都有一面镜子。除西南角以外，每个角落都放有一个接受器，编号为 `0`， `1`，以及 `2`。

正方形房间的墙壁长度为 `p`，一束激光从西南角射出，首先会与东墙相遇，入射点到接收器 `0` 的距离为 `q` 。

返回光线最先遇到的接收器的编号（保证光线最终会遇到一个接收器）。

 

**示例 1：**

![](https://s3-lc-upload.s3.amazonaws.com/uploads/2018/06/18/reflection.png)

**输入：**p = 2, q = 1
**输出：**2
**解释：**这条光线在第一次被反射回左边的墙时就遇到了接收器 2 。

**示例 2：**

**输入：**p = 3, q = 1
**输入：**1

**提示：**

*   `1 <= q <= p <= 1000`

[https://leetcode.cn/problems/mirror-reflection/solutions/2076565/by-stormsunshine-wja9/](https://leetcode.cn/problems/mirror-reflection/solutions/2076565/by-stormsunshine-wja9/)

![1715851031392](assets/1715851031392.png)

![1715851054945](assets/1715851054945.png)

```java
class Solution {
    public int mirrorReflection(int p, int q) {
        int m = p * q / gcd(p, q);
        int x = m / q;
        int y = m / p;
        if (x % 2 == 1 && y % 2 == 1) {
            return 1;
        }
        return x % 2 == 1 ? 0 : 2;
    }
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

1250. 检查「好数组」（裴蜀定理）
--------------

> 裴蜀定理得名于法国数学家艾蒂安·裴蜀，是一个关于最大公约数的定理。
>
> 裴蜀定理的内容是：对于不全为零的任意整数 a 和 b，记 g=gcd⁡(a,b)，则对于任意整数 x 和 y 都满足 a*x+b*y 是 g 的倍数，特别地，存在整数 x 和 y 满足 a*x+b*y=g。

给你一个正整数数组 `nums`，你需要从中任选一些子集，然后将子集中每一个数乘以一个 **任意整数**，并求出他们的和。

假如该和结果为 `1`，那么原数组就是一个「**好数组**」，则返回 `True`；否则请返回 `False`。

**示例 1：**

**输入：**nums = \[12,5,7,23\]
**输出：**true
**解释：**挑选数字 5 和 7。
5\*3 + 7\*(-2) = 1

**示例 2：**

**输入：**nums = \[29,6,10\]
**输出：**true
**解释：**挑选数字 29, 6 和 10。
29\*1 + 6\*(-3) + 10\*(-1) = 1

**示例 3：**

**输入：**nums = \[3,6\]
**输出：**false

**提示：**

*   `1 <= nums.length <= 10^5`
*   `1 <= nums[i] <= 10^9`

[https://leetcode.cn/problems/check-if-it-is-a-good-array/description/](https://leetcode.cn/problems/check-if-it-is-a-good-array/description/)

```java
class Solution {
    public boolean isGoodArray(int[] nums) {
        int div = 0;
        for (int num : nums) {
            div = gcd(num, div);
            if (div == 1) {
                return true;
            }
        }
        return false;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

2543\. 判断一个点是否可以到达
------------------

给你一个无穷大的网格图。一开始你在 `(1, 1)` ，你需要通过有限步移动到达点 `(targetX, targetY)` 。

**每一步** ，你可以从点 `(x, y)` 移动到以下点之一：

*   `(x, y - x)`
*   `(x - y, y)`
*   `(2 * x, y)`
*   `(x, 2 * y)`

给你两个整数 `targetX` 和 `targetY` ，分别表示你最后需要到达点的 X 和 Y 坐标。如果你可以从 `(1, 1)` 出发到达这个点，请你返回`true` ，否则返回 `false` 。

**示例 1：**

**输入：**targetX = 6, targetY = 9
**输出：**false
**解释：**没法从 (1,1) 出发到达 (6,9) ，所以返回 false 。

**示例 2：**

**输入：**targetX = 4, targetY = 7
**输出：**true
**解释：**你可以按照以下路径到达：(1,1) -> (1,2) -> (1,4) -> (1,8) -> (1,7) -> (2,7) -> (4,7) 。

**提示：**

*   `1 <= targetX, targetY <= 109`

[https://leetcode.cn/problems/check-if-point-is-reachable/solutions/2073036/wen-ti-zhuan-huan-gcdju-ti-gou-zao-fang-0plx0/](https://leetcode.cn/problems/check-if-point-is-reachable/solutions/2073036/wen-ti-zhuan-huan-gcdju-ti-gou-zao-fang-0plx0/)

![1715854096139](assets/1715854096139.png)

```java
class Solution {
    public boolean isReachable(int targetX, int targetY) {
        int g = gcd(targetX, targetY);
        return (g & (g - 1)) == 0;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

2607\. 使子数组元素和相等(好好学)
----------------

给你一个下标从 **0** 开始的整数数组 `arr` 和一个整数 `k` 。数组 `arr` 是一个循环数组。换句话说，数组中的最后一个元素的下一个元素是数组中的第一个元素，数组中第一个元素的前一个元素是数组中的最后一个元素。

你可以执行下述运算任意次：

*   选中 `arr` 中任意一个元素，并使其值加上 `1` 或减去 `1` 。

执行运算使每个长度为 `k` 的 **子数组** 的元素总和都相等，返回所需要的最少运算次数。

**子数组** 是数组的一个连续部分。

**示例 1：**

**输入：**arr = \[1,4,1,3\], k = 2
**输出：**1
**解释：**在下标为 1 的元素那里执行一次运算，使其等于 3 。
执行运算后，数组变为 \[1,3,1,3\] 。
- 0 处起始的子数组为 \[1, 3\] ，元素总和为 4 
- 1 处起始的子数组为 \[3, 1\] ，元素总和为 4 
- 2 处起始的子数组为 \[1, 3\] ，元素总和为 4 
- 3 处起始的子数组为 \[3, 1\] ，元素总和为 4 

**示例 2：**

**输入：**arr = \[2,5,5,7\], k = 3
**输出：**5
**解释：**在下标为 0 的元素那里执行三次运算，使其等于 5 。在下标为 3 的元素那里执行两次运算，使其等于 5 。
执行运算后，数组变为 \[5,5,5,5\] 。
- 0 处起始的子数组为 \[5, 5, 5\] ，元素总和为 15
- 1 处起始的子数组为 \[5, 5, 5\] ，元素总和为 15
- 2 处起始的子数组为 \[5, 5, 5\] ，元素总和为 15
- 3 处起始的子数组为 \[5, 5, 5\] ，元素总和为 15

**提示：**

*   `1 <= k <= arr.length <= 105`
*   `1 <= arr[i] <= 109`

[https://leetcode.cn/problems/make-k-subarray-sums-equal/description/](https://leetcode.cn/problems/make-k-subarray-sums-equal/description/)

```java
import java.util.ArrayList;
import java.util.Collections;

class Solution {
    public long makeSubKSumEqual(int[] arr, int k) {
        int n = arr.length;
        k = gcd(n, k);
        long ans = 0;
        for (int i = 0; i < k; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = i; j < n; j += k) {
                list.add(arr[j]);
            }
            Collections.sort(list);
            int target = list.get(list.size() / 2);
            for (int x : list) {
                ans += Math.abs(target - x);
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
```

