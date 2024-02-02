package com.songw;

public class LeetCode45 {
    public int jump(int[] nums) {
        int length=nums.length;
        int end=0;
        int maxPosition=0;
        int steps=0;
        for(int i=0;i<length-1;i++){
            maxPosition=Math.max(maxPosition,nums[i]+i);
            if(i==end){
                end=maxPosition;
                steps++;
            }
        }
        return steps;
    }
}
