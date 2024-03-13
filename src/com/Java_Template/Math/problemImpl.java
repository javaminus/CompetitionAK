package com.Java_Template.Math;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 */
public class problemImpl implements problem {

    // 172. 阶乘后的零
    public int trailingZeroes(int n) {
        int ans = 0;
        while (n >= 5) {
            n /= 5;
            ans += n;
        }
        return ans;
    }

    // 1017. 负二进制转换 
    public String baseNeg2(int n) {
        if (n == 0) {
            return "0";
        }
        StringBuilder ans = new StringBuilder();
        while (n != 0) {
            int remain = n & 1;
            ans.append(remain);
            n -= remain;
            n /= -2;
        }
        return ans.reverse().toString();
    }


    // 166. 分数到小数
    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder sb = new StringBuilder();
        HashMap<Long, Integer> map = new HashMap<>();
        long a = numerator, b = denominator;
        if (a * b < 0) {
            sb.append('-');
        }
        a = Math.abs(a);
        b = Math.abs(b);
        sb.append(a / b);
        if (a % b == 0) {
            return sb.toString();
        }
        sb.append('.');
        while ((a = (a % b) * 10) > 0 && !map.containsKey(a)) {
            map.put(a, sb.length());
            sb.append(a / b);
        }
        if (a == 0) {
            return sb.toString();
        }
        return sb.insert((int) map.get(a), '(').append(')').toString(); // Integer转int也可以用.intValue();
    }


    // 910. 最小差值 II
    public int smallestRangeII(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        if (n == 1) {
            return 0;
        }
        int ans = nums[n - 1] - nums[0];
        for (int i = 0; i < n - 1; i++) {
            int maxNum = Math.max(nums[n - 1] - k, nums[i] + k);
            int minNum = Math.min(nums[0] + k, nums[i + 1] - k);
            ans = Math.min(ans, maxNum - minNum);
        }
        return ans;
    }


    // 927. 三等分
    public int[] threeEqualParts(int[] arr) {
        int sum = Arrays.stream(arr).sum();
        int n = arr.length;
        if (sum % 3 != 0) {
            return new int[]{-1, -1};
        }
        if (sum == 0) {
            return new int[]{0, 2};
        }
        int p = sum / 3;
        int first = 0, second = 0, third = 0, cnt = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 1) {
                if (cnt == 0) {
                    first = i;
                } else if (cnt == p) {
                    second = i;
                } else if (cnt == 2 * p) {
                    third = i;
                }
                cnt++;
            }
        }
        int len = n - third;
        if (first + len <= second && second + len <= third) {
            int i = 0;
            while (third + i < n) {
                if (arr[first + i] != arr[second + i] || arr[second + i] != arr[third + i]) {
                    return new int[]{-1, -1};
                }
                i++;
            }
            return new int[]{first + i - 1, second + i};
        }
        return new int[]{-1, -1};
    }




}
