package com.songw;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Minus
 * @date 2022/4/12 0:25
 * 时间复杂度O(n)
 */
public class LeetCode217 {
    class Solution {
        public boolean containsDuplicate(int[] nums) {
            Set<Integer> set=new HashSet<Integer>();
            for (int x :
                    nums) {
                if (!set.add(x)){
                    return true;
                }
            }
            return false;
        }
    }
}
