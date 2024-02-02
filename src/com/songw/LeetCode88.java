package com.songw;

import org.junit.Test;

import java.util.Arrays;

public class LeetCode88 {
    @Test
    public void Test() {
        int[] nums1={1,2,3,0,0,10};
        int[] nums2={2,5,6};
        merge(nums1,3,nums2,3);

        System.out.println(nums1.toString());
        System.out.println(nums2);
    }
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for(int i=0;i!=n;i++){
            nums1[m+i]=nums2[i];
        }
        Arrays.sort(nums1);
    }

}
