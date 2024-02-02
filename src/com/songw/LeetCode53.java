package com.songw;

import org.junit.Test;

public class LeetCode53 {
    @Test
    public void test(){
        int[] nums=new int[]{5,-9,2,3,6,-9,5,4};
        System.out.println(maxSubArray(nums));
    }
    public int maxSubArray(int[] nums) {
        int ans=nums[0];
        int sum=0;
        for(int num:nums){
            if(sum>0){
                sum+=num;
            }else{
                sum=num;
            }
            ans=Math.max(sum,ans);
        }
        return ans;
    }
}
