package com.Java_Template.enumeration;

import java.util.HashMap;

public class problemImpl implements problem {

    /**
     * 直接枚举x的次方，不过有很多细节需要处理
     */
    @Override
    public int maximumLength(int[] nums) {
        HashMap<Long, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.merge((long) num, 1, Integer::sum);
        }
        Integer c1 = cnt.remove(1L);
        int ans = c1 != null ? c1 - (c1 % 2 ^ 1) : 0; // 1的个数为偶数，则减一，因为答案始终为奇数
        for (long x : cnt.keySet()) {
            int res = 0;
            while (cnt.getOrDefault(x, 0) > 1) {
                res += 2;
                x *= x;
            }
            ans = Math.max(ans, res + (cnt.containsKey(x) ? 1 : -1));
        }
        return ans;
    }
}
