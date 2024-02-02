package com.songw;

import org.junit.Test;

public class LeetCode69 {
    @Test
    public void test(){
        int x=19;
        System.out.println(mySqrt(x));
    }
    public int mySqrt(int x){
        if(x==0){
            return 0;
        }
        if(x==1){
            return 1;
        }
        int left=1,right=x/2;
        while(left<right){
            int mid=(left+right)/2+1;
            if(mid>x/mid){
                right=mid-1;
            }else if(mid<x/mid){
               left=mid;
            }else {
                return mid;
            }
        }
        return left;
    }
}
