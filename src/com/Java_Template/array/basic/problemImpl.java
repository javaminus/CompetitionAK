package com.Java_Template.array.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class problemImpl implements problem{
    // 扩散元素
    public int minimumSeconds(List<Integer> nums) {
        int n = nums.size(), ans = n;
        HashMap<Integer, List<Integer>> map = new HashMap<>(); // < num, {相同num对应的下标集合} >
        for (int i = 0; i < n; i++) {
            map.computeIfAbsent(nums.get(i), k -> new ArrayList<>()).add(i);
        }
        for (List<Integer> list : map.values()) {
            int mx = n - list.get(list.size() - 1) + list.get(0); // 计算两边的长度： _________(包含当前遍历的key)__________
            for (int i = 1; i < list.size(); i++) {
                mx = Math.max(mx, list.get(i) - list.get(i - 1));
            }
            ans = Math.min(ans, mx);
        }
        return ans / 2;  // 时间复杂度：O(n)，其中 n 为 nums 的长度。 空间复杂度：O(n)
    }
}
