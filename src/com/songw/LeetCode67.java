package com.songw;

import org.junit.Test;

public class LeetCode67 {
    @Test
    public void test(){
        String a="1011";
        String b="10";
        System.out.println(addBinary(a,b));

    }
    public String addBinary(String a, String b) {
        StringBuilder str=new StringBuilder();
        int ans=0;
        int count=0;
        for(int i=a.length()-1,j=b.length()-1;i>=0 || j>=0;i--,j--){
            count=ans;
            count+=(i>=0?a.charAt(i)-'0':0);
            count+=(j>=0?b.charAt(j)-'0':0);
            str.append(count%2);
            ans=count/2;
        }
        str.append(ans>=1?ans:"");
        return str.toString();
    }
}
