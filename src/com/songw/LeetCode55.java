package com.songw;

public class LeetCode55 {
    public boolean canJump(int[] nums) {
        int length = nums.length;
        int maxPosition=0;
        for(int i=0;i<=length-1;i++){
            if(i<=maxPosition){
                maxPosition=Math.max(maxPosition,nums[i]+i);
                if(maxPosition>=length-1){
                    return true;
                }
            }
        }
        return false;
    }
}
