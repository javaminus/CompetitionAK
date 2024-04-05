package com.Java_Template.array.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class problemImpl implements problem{
    // 扩散元素
    public int minimumSeconds(List<Integer> nums) { // 最终所有元素一定变成了一个在 nums 中的数。枚举这个数。
        int n = nums.size(), ans = n;
        HashMap<Integer, List<Integer>> map = new HashMap<>(); // < num, {相同num对应的下标集合} >
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums.get(i), k -> new ArrayList<>()).add(i);
        }
        for (List<Integer> list : map.values()) {
            int mx = n - list.get(list.size() - 1) + list.get(0); // 计算两边的长度： _________(包含当前遍历的key)__________
            for (int i = 1; i < list.size(); i++) {
                mx = Math.max(mx, list.get(i) - list.get(i - 1)); // 计算中间位置，其实就是求最大“间隔”
            }
            ans = Math.min(ans, mx);
        }
        return ans / 2;  // 时间复杂度：O(n)，其中 n 为 nums 的长度。 空间复杂度：O(n)
    }

    // 2952. 需要添加的硬币的最小数量
    public int minimumAddedCoins(int[] coins, int target) {
        // 填充法
        Arrays.sort(coins);
        int ans = 0, s = 0, i = 0;
        while (s < target) {
            if (i < coins.length && coins[i] <= s + 1) {
                s += coins[i++];
            }else{
                s += s + 1;
                ans++;
            }
        }
        return ans;
    }

    // 330. 按要求补齐数组
    public int minPatches(int[] nums, int n) {
        Arrays.sort(nums);
        long s = 0;
        int  ans = 0, i = 0;
        while (s < n) {
            if (i < nums.length && nums[i] <= s + 1) {
                s += nums[i++];
            }else{
                s += s + 1;
                ans++;
            }
        }
        return ans;
    }


    // 1798. 你能构造出连续值的最大数目
    public int getMaximumConsecutive(int[] coins) {
        Arrays.sort(coins);
        int i = 0, s = 0;
        while (true) {
            if (i < coins.length && coins[i] <= s + 1) {
                s += coins[i++];
            } else {
                break;
            }
        }
        return s + 1;
    }
}
