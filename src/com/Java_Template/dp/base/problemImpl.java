package com.Java_Template.dp.base;

import java.util.Arrays;
import java.util.HashMap;

/**
 *
 */
public class problemImpl implements problem {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num + 1, map.getOrDefault(num, 0) + 1); // 当前数加一dp的结果
            map.put(num, map.getOrDefault(num - 1, 0) + 1); // 当前数不变dp的结果
        }
        int ans = 0;
        for (int x : map.values()) {
            ans = Math.max(ans, x);
        }
        return ans;
    }
}
