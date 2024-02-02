package com.template.字符串;

/**
 * @author Minus
 * @date 2023/11/22 17:18
 */
public class split划分 {
    public int countSegments(String s) {
        int ans = 0;
        String[] nums = s.split(" ");
        for (String num : nums) {
            if (!num.equals("")) {
                ans++;
            }
        }
        return ans;
    }
}
