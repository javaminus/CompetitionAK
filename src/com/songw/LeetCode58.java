package com.songw;

import org.junit.Test;
/*给你一个字符串 s，由若干单词组成，单词前后用一些空格字符隔开。返回字符串中最后一个单词的长度。

        单词 是指仅由字母组成、不包含任何空格字符的最大子字符串。*/
public class LeetCode58 {
    @Test
    public void test(){
        String s="aaf fa fa fa fdsf ";
        System.out.println(lengthOfLastWord(s));

    }
    public int lengthOfLastWord(String s) {
        if(s==null || s.length()==0){
            return 0;
        }
        int n=s.length();
        int count=0;
        for(int i=n-1;i>=0;i--){
            if(s.charAt(i)==' '){
                if(count==0){
                    continue;
                }else break;
            }
            else {
                count++;
            }
        }
        return count;


    }
}
