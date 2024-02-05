package com.Java_Template.string;

import com.Java_Template.string.template.KMP;

import java.util.List;

/**
 * @author Minus
 * @date 2024/2/11 13:10
 */
public class ProblemImpl implements problem {

    @Override
    public int countMatchingSubarrays(int[] nums, int[] pattern) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                sb.append("1");
            }
            if (nums[i] == nums[i - 1]) {
                sb.append("0");
            }
            if (nums[i] < nums[i - 1]) {
                sb.append("2");
            }
        }
        StringBuilder pSb = new StringBuilder();
        for (int i = 0; i < pattern.length; i++) {
            pSb.append(pattern[i] == -1 ? 2 : pattern[i]);
        }
        List<Integer> res = new KMP().search(sb.toString().toCharArray(), pSb.toString().toCharArray());
        return res.size();
    }
}
