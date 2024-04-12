package com.Java_Template.bit_operation;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author Minus
 * @date 2024/1/20 19:54
 */
public class problemImpl implements problem {

    // LCR 190. 加密运算  位运算实现加法
    @Override
    public int encryptionCalculate(int a, int b) {
        // 非进位使用异或^ , 进位就左移<<
        while (b != 0) {
            int t = (a & b) << 1; // 求进位
            a ^= b; // 异或求非进位
            b = t;
        }
        return a;
    }

    // Problem: LCR 003. 比特位计数
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // ans[i] = Integer.bitCount(i);
            ans[i] = ans[i >> 1] + (i & 1); // 偶数ans[i] = ans[i>>1], 奇数就加1
//            if (i % 2 == 1) {
//                ans[i] = ans[i / 2] + 1;
//            }else{
//                ans[i] = ans[i / 2];
//            }
        }
        return ans;
    }


    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        // 方法1，使用api
        // return Integer.reverse(n);

        // 方法2， 每次把 res 左移，把 n 的二进制末尾数字，拼接到结果 res 的末尾。然后把 n 右移。
//        int res = 0;
//        for (int i = 0; i < 32; i++) {
//            res |= (n & 1) << (n - i);
//            n >>>= 1;
//        }
//        return res;
        // 方法3 分治
        int M1 = 0x55555555; // 01010101010101010101010101010101
        int M2 = 0x33333333; // 00110011001100110011001100110011
        int M4 = 0x0f0f0f0f; // 00001111000011110000111100001111
        int M8 = 0x00ff00ff; // 00000000111111110000000011111111

        n = n >>> 1 & M1 | (n & M1) << 1;
        n = n >>> 2 & M2 | (n & M2) << 2;
        n = n >>> 4 & M4 | (n & M4) << 4;
        n = n >>> 8 & M8 | (n & M8) << 8;
        return n >>> 16 | n << 16;
    }

    // 2401. 最长优雅子数组
    public int longestNiceSubarray(int[] nums) {
        // 方法一
//        int ans = 0;
//        for (int i = 0; i < nums.length; i++) {
//            int j = i, or = 0;
//            while (j >= 0 && (or & nums[j]) == 0) {
//                or |= nums[j--];
//            }
//            ans = Math.max(ans, i - j);
//        }
//        return ans;
        // 方法二 滑动窗口
        int ans = 0;
        for (int left = 0, right = 0, or = 0; right < nums.length; right++) {
            while ((or & nums[right]) > 0) { // 有交集
                or ^= nums[left++]; // 从 or 中去掉集合 nums[left]
            }
            or |= nums[right];
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }


    // 2680. 最大或值
    public long maximumOr(int[] nums, int k) {
        int n = nums.length;
        long[] suffix = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] | nums[i];
        }
        long ans = 0, pre = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, pre | ((long) nums[i] << k) | suffix[i + 1]);
            pre |= nums[i];
        }
        return ans;
    }


    // 2411. 按位或最大的最小子数组长度(https://leetcode.cn/problems/smallest-subarrays-with-maximum-bitwise-or/description/)
    public int[] smallestSubarrays(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        ArrayList<int[]> ors = new ArrayList<int[]>(); // (按位或的值,对应子数组的右端点的最小值)
        for (int i = n - 1; i >= 0; --i) {
            ors.add(new int[]{0, i});
            int k = 0;
            for (int[] or : ors) {
                or[0] |= nums[i];
                if (ors.get(k)[0] == or[0])
                    ors.get(k)[1] = or[1]; // 合并相同值，下标取最小的
                else ors.set(++k, or);
            }
            ors.subList(k + 1, ors.size()).clear(); // 清空 ors 列表中从索引 k + 1 到末尾的所有元素。也就是去重
            // 本题只用到了 ors[0]，如果题目改成任意给定数值，可以在 ors 中查找
            ans[i] = ors.get(0)[1] - i + 1;
        }
        return ans;
    }

    // 898. 子数组按位或操作
    public int subarrayBitwiseORs(int[] nums) {
        int n = nums.length;
        ArrayList<int[]> ors = new ArrayList<int[]>();
        HashSet<Integer> set = new HashSet<>();
        for (int i = n - 1; i >= 0; i--) {
            ors.add(new int[]{0, i});
            int k = 0;
            for (int[] or : ors) {
                or[0] |= nums[i];
                if (ors.get(k)[0] == or[0]) {
                    ors.get(k)[1] = or[1];
                }else{
                    ors.set(++k, or);
                }
                set.add(or[0]);
            }
            ors.subList(k + 1, ors.size()).clear();
        }
        return set.size();
    }

    // 1521. 找到最接近目标值的函数值
    public int closestToTarget(int[] nums, int target) {
        int n = nums.length;
        ArrayList<int[]> ands = new ArrayList<>();
        int ans = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            ands.add(new int[]{nums[i], i});
            int k = 0;
            for (int[] and : ands) {
                and[0] &= nums[i];
                if (and[0] == ands.get(k)[0]) {
                    ands.get(k)[1] = and[1];
                }else{
                    ands.set(++k, and);
                }
                ans = Math.min(ans, Math.abs(and[0] - target));
            }
            ands.subList(k + 1, ands.size()).clear();
        }
        return ans;
    }


    // 1310. 子数组异或查询
    public int[] xorQueries(int[] arr, int[][] queries) {
        int n = arr.length;
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] ^ arr[i];
        }
        int[] ans = new int[queries.length];
        int k = 0;
        for (int[] q : queries) {
            int from = q[0], to = q[1];
            ans[k++] = pre[to + 1] ^ pre[from];
        }
        return ans;
    }
}
