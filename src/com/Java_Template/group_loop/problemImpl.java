package com.Java_Template.group_loop;

/**
 * @author Minus
 * @date 2024/1/23 13:55
 */
public class problemImpl implements problem{

    // 最长交替子数组
    @Override
    public int alternatingSubarray(int[] nums) {
        int i = 0, n = nums.length, ans = -1;
        while (i < n - 1) {
            if (nums[i + 1] - nums[i] != 1) {
                i++;
                continue;
            }
            int start = i;
            i += 2;
            while (i < n && (nums[i] - nums[i - 1]) * (nums[i - 1] - nums[i - 2]) == -1) {
                i++;
            }
            ans = Math.max(ans, i - start);
            i--;
        }
        return ans;
    }


    // 1759. 统计同质子字符串的数目
    @Override
    public int countHomogenous(String s) {
        int Mod = 1000000007;
        char[] chars = s.toCharArray();
        int i = 1, n = s.length(), cnt = 1;
        long ans = 0;
        while (i < n) {
            if (chars[i] == chars[i - 1]) {
                cnt++;
            }else{
                ans += ((long) cnt * (cnt + 1)) / 2;
                cnt = 1;
            }
            i++;
        }
        ans += ((long) cnt * (cnt + 1)) / 2;
        return (int) (ans % Mod);
    }
}
