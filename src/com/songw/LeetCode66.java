package com.songw;

import org.junit.Test;

import java.util.Arrays;

public class LeetCode66 {
    @Test
    public void test(){
        int[] digists=new int[]{1,9,3,9};
        System.out.println(Arrays.toString(plusOne(digists)));  //Arrays.toString() 类似与重写toString()方法；
    }

    public int[] plusOne(int[] digits) {
        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            digits[i]++;
            digits[i] %= 10;
            if (digits[i] != 0) {
                return digits;
            }
        }
        digits = new int[len + 1];
        digits[0] = 1;
        return digits;
    }
}
