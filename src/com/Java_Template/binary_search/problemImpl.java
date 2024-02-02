package com.Java_Template.binary_search;

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

}
