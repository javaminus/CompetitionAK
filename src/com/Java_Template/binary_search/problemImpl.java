package com.Java_Template.binary_search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Minus
 * @date 2024/1/17 10:49
 */
public class problemImpl implements problem {
    //  4. 寻找两个正序数组的中位数
    // 经典二分寻找两个有序数组的中位数
    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        if (m > n) {
            return findMedianSortedArrays(B, A); // 交换A、B
        }
        int iMin = 0, iMax = m;
        // 二分模板
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = (m + n + 1) / 2 - i; // 推导的公式
            if (j != 0 && i != m && B[j - 1] > A[i]) { // i 需要增大
                iMin = i + 1;
            } else if (i != 0 && j != n && A[i - 1] > B[j]) { // i 需要减小
                iMax = i - 1;
            }else{ // 达到要求，并且将边界条件列出来单独考虑
                int maxLeft = 0; // 左边的最大值
                if (i == 0) {
                    maxLeft = B[j - 1];
                } else if (j == 0) {
                    maxLeft = A[i - 1];
                }else{
                    maxLeft = Math.max(A[i - 1], B[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft; // 总长度奇数，不需要考虑右半部分
                }

                // 总长度为偶数的时候，我们需要找到右边的最小值
                int minRight = 0; // 右边的最小值
                if (i == m) {
                    minRight = B[j];
                } else if (j == n) {
                    minRight = A[i];
                }else{
                    minRight = Math.min(A[i], B[j]);
                }
                return (maxLeft + minRight) / 2.0;
            }

        }
        return 0.0;
    }
    // 暴力写法
    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int m = nums1.length, n = nums2.length;
        double[] arr = new double[m + n];
        int i = 0, j = 0,k = 0;
        while (i < m && j < n) {
            if (nums1[i] <= nums2[j]) {
                arr[k++] = nums1[i++];
            }else{
                arr[k++] = nums2[j++];
            }
        }
        if (i == m) {
            while (j < n) {
                arr[k++] = nums2[j++];
            }
        } else if (j == n) {
            while (i < m) {
                arr[k++] = nums1[i++];
            }
        }
        if ((m + n) % 2 == 0) {
            return (arr[(m + n) / 2] + arr[(m + n) / 2 - 1]) / 2;
        }else{
            return arr[(m + n) / 2];
        }
    }

    // 81. 搜索旋转排序数组 II  有重复数字的二分
    public boolean search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[mid] == nums[left]) {
                left++;
                continue;
            } else if (nums[left] < nums[mid]) {// 前半部分有序
                if (nums[left] == target) {
                    return true;
                } else if (nums[mid] > target && nums[left]<target) {
                    right = mid - 1;
                }else{
                    left = mid + 1;
                }
            }else{ // 后半部分有序 nums[left] > nums[mid]
                if (target == nums[right]) {
                    return true;
                }if (target < nums[right] && nums[mid] < target) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        return false;
    }

    // 153. 寻找旋转排序数组中的最小值
    public int findMin(int[] nums) { // 只需要与最后一个元素比较
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (nums[mid] < nums[nums.length - 1]) {
                right = mid - 1;
            } else if (nums[mid] > nums[nums.length - 1]) {
                left = mid + 1;
            }else{
                break;
            }
        }
        return nums[left];
    }

    // 寻找旋转排序数组中的最小值 II ,有重复数字
    public int findMin1(int[] nums) {
        int left = 0, right = nums.length - 2; // 闭区间[0,nums.length-2]
        while (left <= right) { // 开区间不为空
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[right+1]) right = mid - 1; // 蓝色
            else if (nums[mid] > nums[right+1]) left = mid + 1; // 红色
            else --right;
        }
        return nums[left];
    }

    // 寻找峰值
    public int findPeakElement(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) { // 这里之所以不是取等号，因为我们是比较nums[mid]与nums[mid+1],如果left==right,那么我们的nums[mid+1]就会越界
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else if (nums[mid] > nums[mid + 1]) {
                right = mid;
            }
        }
        return right; // 最后返回left与right都可以，因为最后退出循环条件是Left==right
    }

    // 寻找峰值 II
    public int[] findPeakGrid(int[][] mat) {
        int m = mat.length, n = mat[0].length;
        int low = 0, high = m - 1;
        while (low <= high) {
            int mid = (low + high) >> 1;
            int j = -1, maxElement = -1;
            for (int k = 0; k < n; k++) {
                if (mat[mid][k] > maxElement) {
                    j = k;
                    maxElement = mat[mid][k];
                }
            }
            if (mid - 1 >= 0 && mat[mid][j] < mat[mid - 1][j]) {
                high = mid - 1;
                continue;
            }
            if (mid + 1 < m && mat[mid][j] < mat[mid + 1][j]) {
                low = mid + 1;
                continue;
            }
            return new int[]{mid, j};
        }
        return new int[0];
    }


    // 410. 分割数组的最大值  依次枚举分割数组的最大值
    @Override
    public int splitArray(int[] nums, int k) {
        int left = 0, right = 0; // left是nums中最大元素，right = Math.sum(nums), 代表二分的上下界
        for (int num : nums) {
            if (left < num) {
                left = num;
            }
            right += num;
        }
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (check(nums, mid, k)) {
                right = mid;
            } else {
                left = mid+1;
            }
        }
        return left;
    }
    private boolean check(int[] nums, int limit, int k) {
        int sum = 0;
        int cnt = 1; // 划分的子数组数量
        for (int num : nums) {
            if (sum + num > limit) {
                sum = num;
                cnt++;
            }else{
                sum += num;
            }
        }
        return cnt <= k; // 贪心思想
    }


    // 878. 第 N 个神奇数字
    public int nthMagicalNumber(int n, int a, int b) {
        // 左开右闭，左边往右边收
        int Mod = (int) (1e9 + 7);
        long lcm = (long) a / gcd(a, b) * b;
        long left = 0, right = (long) Math.min(a, b) * n;
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (mid / a + mid / b - mid / lcm >= n) {
                right = mid;
            } else {
                left = mid +1;
            }
        }
        return (int) ((right) % Mod);
    }
    public int nthMagicalNumber1(int n, int a, int b) {
        // 左闭右闭
        int Mod = (int) (1e9 + 7);
        long lcm = (long) a / gcd(a, b) * b;
        long left = 0, right = (long) Math.min(a, b) * n;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (mid / a + mid / b - mid / lcm >= n) {
                right = mid - 1;
            } else {
                left = mid +1;
            }
        }
        return (int) ((right+1) % Mod);
    }
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    // 2861. 最大合金数
    public int maxNumberOfAlloys(int n, int k, int budget, List<List<Integer>> composition, List<Integer> stock, List<Integer> cost) {
        int left = 1, right = Collections.min(stock) + budget, ans = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            boolean valid = false;
            for (int i = 0; i < k; i++) {
                long spend = 0L;
                for (int j = 0; j < n; j++) {
                    spend += Math.max((long) composition.get(i).get(j) * mid - stock.get(j), 0) * cost.get(j);
                }
                if (spend <= budget) {
                    valid = true;
                    break;
                }
            }
            if (valid) {
                ans = mid;
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return ans;
    }

    private static final int MX = 1000;
    private static final List<Integer> primes = new ArrayList<>(); // 质数数组
    static {
        boolean[] np = new boolean[MX + 1];
        primes.add(0);
        for (int i = 2; i <= MX; i++) {
            if (!np[i]) {
                primes.add(i);
                for (int j = i; j <= MX / i; j++) {
                    np[i * j] = true;
                }
            }
        }
    }
    public boolean primeSubOperation(int[] nums) {
        //设 pre是上一个减完后的数字，x=nums[i]为当前数字。
        //设 p是满足 x−p>pre的最大质数，换言之，p是小于 x−pre的最大质数，这可以预处理质数列表后，用二分查找得到。
        int pre = 0;
        for (int x : nums) {
            if (x <= pre) { // 如果后一个数不减都大于前一个数，直接false
                return false;
            }
            int j = lowerBound(primes, x - pre); // 查找小于target的最大数  贪心
            pre = x - primes.get(j);
        }
        return true;
    }

    private int lowerBound(List<Integer> nums, int target) { // 查找小于target的最大数  贪心
        // 这种写法不对，我们需要的是往左边走，所以当取等时，应该时left = mid - 1, 而不是left = mid + 1;
//        int left = 0, right = nums.size() - 1;
//        while (left < right) {
//            int mid = left + (right - left) / 2;
//            if (nums.get(mid) > target) {
//                right = mid - 1;
//            }else{
//                left = mid + 1;
//            }
//        }
//        return nums.get(left) < target ? left : left - 1;

        int left = 0, right = nums.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums.get(mid) < target) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return nums.get(left) < target ? left : left - 1;
    }


    public List<Long> minOperations(int[] nums, int[] queries) {
        int n = nums.length;
        long[] prefix = new long[n + 1]; // 前缀和记得留n+1的空间,开long型
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        ArrayList<Long> ans = new ArrayList<>();
        for (int q : queries) {
            int j = lowerBound(nums, q); // 找小于q的最大值下标
            long left = (long) q * j - prefix[j];
            long right = prefix[n] - prefix[j] - (long) q * (n - j);
            ans.add(left + right);
        }
        return ans;
    }

    private int lowerBound(int[] nums, int target) {
        int left = -1, right = nums.length; // 开区间 (left, right)
        while (left + 1 < right) { // 区间不为空
            // 循环不变量：
            // nums[left] < target
            // nums[right] >= target
            int mid = left + (right - left) / 2;
            if (nums[mid] < target)
                left = mid; // 范围缩小到 (mid, right)
            else
                right = mid; // 范围缩小到 (left, mid)
        }
        return right;
    }


    // 100200. 标记所有下标的最早秒数 I
    public int earliestSecondToMarkIndices(int[] nums, int[] changeIndices) {
        int n = nums.length, m = changeIndices.length;
        if (n > m) {
            return -1;
        }
        int[] tmp = new int[n];
        int left = n, right = m;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(nums, changeIndices, tmp, mid)) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return left > m ? -1 : left;
    }

    private boolean check(int[] nums, int[] changeIndices, int[] tmp, int mid) {
        Arrays.fill(tmp, -1);
        for (int i = 0; i < mid; i++) {
            tmp[changeIndices[i] - 1] = i;
        }
        for (int t : tmp) {
            if (t < 0) {
                return false;
            }
        }
        int cnt = 0;
        for (int i = 0; i < mid; i++) {
            int index = changeIndices[i] - 1;
            if (i == tmp[index]) {
                if (cnt < nums[i]) {
                    return false;
                }
                cnt -= nums[i];
            }else{
                cnt++;
            }
        }
        return true;
    }


    // 2386. 找出数组的第 K 大和
    private int cnt;
    @Override
    public long kSum(int[] nums, int k) {
        long sum = 0, right = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                sum += nums[i];
            }else{
                nums[i] = -nums[i];
            }
            right += nums[i];
        }
        Arrays.sort(nums);
        long left = 0;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            cnt = k - 1;
            dfs(0, mid, nums);
            if (cnt == 0) { // 找到 k 个元素和不超过 mid 的子序列
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return sum - right - 1;
    }

    private void dfs(int i, long s, int[] nums) {
        if (cnt == 0 || i == nums.length || s < nums[i]) {
            return;
        }
        cnt--;
        dfs(i + 1, s - nums[i], nums); // 选
        dfs(i + 1, s, nums); // 不选
    }


    // 2517. 礼盒的最大甜蜜度
    public int maximumTastiness(int[] price, int k) {
        int n = price.length;
        Arrays.sort(price);
        int left = 0, right = price[n - 1] - price[0];
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check(price, n, k, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left - 1;
    }
    private boolean check(int[] price, int n, int k, int minTarget) {
        int cnt = 1, p = price[0];
        for (int i = 1; i < n; i++) {
            if (price[i] - p >= minTarget) {
                cnt++;
                p = price[i];
            }
        }
        if (cnt < k) {
            return false;
        }
        return true;
    }


    // 2439. 最小化数组中的最大值
    public int minimizeArrayValue(int[] nums) {
        int n = nums.length;
        int left = Integer.MAX_VALUE, right = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            left = Math.min(left, nums[i]);
            right = Math.max(right, nums[i]);
        }
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (check1(nums, n, mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return right + 1;
    }
    private boolean check1(int[] nums, int n, int limit) {
        long t = 0;
        for (int i = 0; i < n; i++) {
            if (nums[i] > limit) {
                if (nums[i] - limit > t) {
                    return false;
                }
                t -= nums[i] - limit;
            }else{
                t += limit - nums[i];
            }
        }
        return true;
    }

    /*  100267. 单面值组合的第 K 小金额
        给你一个整数数组 coins 表示不同面额的硬币，另给你一个整数 k 。
        你有无限量的每种面额的硬币。但是，你 不能 组合使用不同面额的硬币。
        返回使用这些硬币能制造的 第 kth 小 金额*/
    // NOTE:遇到这种第几小/大的问题，往二分想
    public long findKthSmallest(int[] coins, int k) {
        long left = k, right = (long) Arrays.stream(coins).max().getAsInt() * k;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (check(mid, k, coins)) {
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return right + 1;
    }

    private boolean check(long m, int k, int[] coins) {
        long cnt = 0;
        next:
        for (int i = 1; i < (1 << coins.length); i++) { // 1<<coins.length这么多组合
            long lcmRes = 1;
            for (int j = 0; j < coins.length; j++) {
                if ((i >> j & 1) == 1) { // 如果选择银币j
                    lcmRes = lcm(lcmRes, coins[j]);
                    if (lcmRes > m) {
                        continue next; // 当代码执行到 continue next; 时，它会跳到标签 next 所指定的位置，也就是外层 for 循环的下一个迭代。
                    }
                }
            }
            cnt += Integer.bitCount(i) % 2 == 1 ? m / lcmRes : -m / lcmRes; // 容斥原理
        }
        return cnt >= k;
    }

    private long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

}
