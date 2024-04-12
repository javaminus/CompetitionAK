package com.Java_Template.array.group_loop;

import java.util.Arrays;

/**
 * @author Minus
 * @date 2024/4/8 12:41
 */
public class problemImpl implements problem {

    // 3105. 最长的严格递增或递减子数组
    @Override
    public int longestMonotonicSubarray(int[] nums) {
        int n = nums.length, ans = 1, i = 0;
        while (i < n - 1) {
            if (nums[i] == nums[i + 1]) {
                i++;
                continue;
            }
            int i0 = i;
            boolean inc = nums[i + 1] > nums[i];
            i += 2;
            while (i < n &&( nums[i] > nums[i - 1] == inc) && nums[i] != nums[i - 1]) {
                i++;
            }
            ans = Math.max(ans, i - i0);
            i--;
        }
        return ans;
    }

    // 978. 最长湍流子数组
    @Override
    public int maxTurbulenceSize(int[] a) {
        int i = 0, n = a.length, ans = 1;
        while (i < n - 1) {
            if (a[i] == a[i + 1]) {
                i++;
                continue;
            }
            boolean inc = a[i + 1] > a[i];
            int i0 = i;
            i += 2;
            inc = !inc;
            while (i < n && (a[i] > a[i - 1] == inc) && a[i] != a[i - 1]) {
                inc = !inc;
                i++;
            }
            ans = Math.max(ans, i - i0);
            i--;
        }
        return ans;
    }


    // 845. 数组中的最长山脉
    public int longestMountain(int[] nums) {
        int i = 0, ans = 0, n = nums.length;
        while (i < n - 1) {
            if (nums[i] >= nums[i + 1]) {
                i++;
                continue;
            }
            boolean inc = nums[i + 1] > nums[i];
            boolean down = false; // 有无下山
            int i0 = i;
            i += 2;
            while (i < n && nums[i] != nums[i - 1] && (nums[i] > nums[i - 1] == inc)) {
                i++;
            }
            if (inc) {
                inc = false;
                while (i < n && nums[i] != nums[i - 1] && (nums[i] > nums[i - 1] == inc)) {
                    i++;
                    if (!down) {
                        down = true;
                    }
                }
                if (down) { // 必须要有下山的路径
                    ans = Math.max(ans, i - i0);
                }
            }
            i--;
        }
        return ans > 2 ? ans : 0;
    }


    // 2948. 交换得到字典序最小的数组
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        int[] ans = new int[n];
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        Arrays.sort(ids, (i, j) -> nums[i] - nums[j]);
        int i = 0;
        while (i < n) {
            int i0 = i;
            i++;
            while (i < n && nums[ids[i]] - nums[ids[i - 1]] <= limit) {
                i++;
            }
            Integer[] subIds = Arrays.copyOfRange(ids, i0, i); // 分组：下标数组
            Arrays.sort(subIds); // 把 nums[i]及其下标i绑在一起排序（也可以单独排序下标），然后把 nums 分成若干段，每一段都是递增的且相邻元素之差不超过 limit，那么这一段可以随意排序。
            for (int j = 0; j < subIds.length; j++) {
                ans[subIds[j]] = nums[ids[i0 + j]];
            }
        }
        return ans;
    }
}
