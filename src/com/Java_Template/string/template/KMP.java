package com.Java_Template.string.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Minus
 * @date 2024/2/11 13:10
 *
 * 不推荐
 */
public class KMP{
    private void getPrefixTable(char[] pattern, int[] prefixTable){
        prefixTable[0] = 0; // 默认第0位为0
        int pLen = pattern.length;
        if (pLen > 1){
            int i = 1;      //模式串的下标，默认从下标为1的位置开始计算，因为前1位默认为0
            int len = 0;    //记录已经匹配到的公共前后缀的长度
            while(i < pLen){    //循环次数为模式串的长度
                if(pattern[i] == pattern[len]){ //当前循环到的字符，和上次匹配到公共前后缀长度的位置进行匹配，如果匹配到了
                    len++;  //则将公共前后缀的长度+1
                    prefixTable[i] = len;   //将prefixTable数组i位置的值赋值为连续匹配到的长度
                    i++;    //进入下一轮循环
                }else {     //本次循环没有匹配上
                    if (len > 0){   //判断上次匹配到的公共前后缀的长度，如果上次已匹配成功过，len长度肯定大于0
                        len = prefixTable[len - 1]; // 则将公共前后缀的长度回朔
                    }else {     //如果上次没有匹配到，本次也没有匹配到
                        prefixTable[i] = len;   //那么当前位置的公共前后缀长度就为0（此时的len为0，直接=0也是一样的）
                        i++;    //进入下一轮循环
                    }
                }
            }
        }

        //将公共前后缀数组整体向后移一位，首位设置为-1
        int len = prefixTable.length;
        if (len > 1){
            System.arraycopy(prefixTable, 0, prefixTable, 1, len - 1);
            prefixTable[0] = -1;
        }
    }

    /**
     * 找出字符串中所有匹配项的下标
     */
    public List<Integer> search(char[] text, char[] pattern){
        int m = text.length;    //主串的长度
        int n = pattern.length; //模式串的长度
        if(m<n){
            return Collections.emptyList();
        }else if(m==n){
            return Arrays.equals(text, pattern)?Arrays.asList(0):Collections.emptyList();
        }
        List<Integer> res = new ArrayList<>();
        if (n == 1) {
            for (int i = 0; i < m; i++) {
                if (text[i] == pattern[0]) {
                    res.add(i); //将当前匹配到的索引记录下来
                }
            }
        } else {
            int i = 0;  //主串循环的索引
            int j = 0;  //模式串循环的索引
            int[] prefixTable = new int[n]; //存储公共前后缀的数组
            getPrefixTable(pattern, prefixTable);   //调用计算公共前后缀的方法
            while (i < m) {  //遍历主串的长度
                if ((j == (n - 1)) && text[i] == pattern[j]) {    //如果模式串遍历结束，且最后一位字符相等，即在主串中匹配到了一个完整的字符串
                    res.add(i - j); //将当前匹配到的索引记录下来
                    j = prefixTable[j]; //将模式串的下标移到公共前后缀指定的位置，继续匹配剩余的字符串
                }
                if (text[i] == pattern[j]) {  //匹配到了一个字符，将主串和模式串的下标同时后移一位
                    i++;
                    j++;
                } else { //单个字符不匹配
                    j = prefixTable[j]; //将模式串的下标移到公共前后缀指定的位置，继续匹配
                    if (j == -1) {    //如果模式串的下标为-1时，主串和模式串的下标同时后移一位
                        i++;
                        j++;
                    }
                }
            }
        }
        return res;
    }
}
