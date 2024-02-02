package com.songw;

import org.junit.Test;

public class LeetCode70 {
    @Test
    public void test(){
        int n=3;
        System.out.println(climbStairs(n));
    }
    public int climbStairs(int n) {
        int p=0,q=0,r=1;
        for(int i=0;i<n;i++){
            p=q;
            q=r;
            r=p+q;
        }
        return r;
    }
}
