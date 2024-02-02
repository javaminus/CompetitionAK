package com.template.记忆化搜索;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Minus
 * @date 2023/11/6 22:29
 */
public class S397 {

    /**
     * java使用记忆化搜索
     */
    Map<Integer,Integer> memo = new HashMap<Integer,Integer>();
    public int integerReplacement(int n) {
        if (n == 1) {
            return 0;
        }
        if (!memo.containsKey(n)) {
            if (n % 2 == 0) {
                memo.put(n, 1 + integerReplacement(n / 2));
            }else{
                memo.put(n, 2 + Math.min(integerReplacement(n / 2), integerReplacement(n / 2 + 1)));
            }
        }
        return memo.get(n);
    }



    /**
     * 不使用记忆化搜索
     */
    public int integerReplacement1(int n) {
        if (n == 1) {
            return 0;
        }
        if (n % 2 == 0) {
            return 1 + integerReplacement(n / 2);
        }
        return 2 + Math.min(integerReplacement(n / 2), integerReplacement(n / 2 + 1));
    }


}
