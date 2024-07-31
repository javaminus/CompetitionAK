package com.clapper;

import org.junit.Test;

import java.util.*;

class Solution {
    @Test
    public void test(){
       for (int i = 0; i < 100; i++) {
           int[] date = generateCase();
           int[] ans1 = nextGreaterElements(date);
           int[] ans2 = nextGreaterElements1(date);
           if (!Arrays.equals(ans1, ans2)) {
               System.out.println(Arrays.toString(date));
               System.out.println(Arrays.toString(ans1));
               System.out.println(Arrays.toString(ans2));
           }
       }
   }

    private int[] nextGreaterElements1(int[] date) {
        return null;
    }

    private int[] nextGreaterElements(int[] date) {
        return null;
    }

    private int[] generateCase(){
        Random random = new Random();
        int n = random.nextInt(1001);
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = random.nextInt((int) 200 + 1) - (int) 100;
        }
        return ans;
    }


}