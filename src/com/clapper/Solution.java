package com.clapper;

import org.junit.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class Solution {
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

    private int[] generateCase(){
        Random random = new Random();
        int n = random.nextInt(1001);
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = random.nextInt((int) 200 + 1) - (int) 100;
        }
        return ans;
    }
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        Stack<Integer> stack = new Stack<>(); // stack存储没有更新的元素下标，单调递减存储
        for (int i = 0; i < n * 2; i++) {
            while (!stack.isEmpty() && nums[stack.peek()] < nums[i % n]) {
                int d = stack.pop();
                ans[d] = nums[i % n];
            }
            stack.push(i % n);
        }
        return ans;
    }

    public int[] nextGreaterElements1(int[] nums) {
        int n = nums.length;
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < 2 * n; j++) {
                if (nums[j % n] > nums[i]) {
                    ans[i] = nums[j % n];
                    break;
                }
            }
        }
        if (ans[n - 1] == -1) {
            ans[n - 1] = -2;
        }
        return ans;
    }
}