package com.template.Monotonic_stack;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author Minus
 * @date 2023/11/7 17:05
 */
public class S402 {
    // 贪心 + 单调栈 这是递增构建
    public String removeKdigits(String num, int k) {
        Deque<Character> deque = new LinkedList<>();
        int len = num.length();
        for (int i = 0; i < len; i++) {
            char digit = num.charAt(i);
            while (!deque.isEmpty() && k > 0 && deque.peekLast() > digit) {
                deque.pollLast();
                k--;
            }
            deque.offerLast(digit);
        }

        for (int i = 0; i < k; i++) {
            deque.pollLast();
        }
        StringBuilder ret = new StringBuilder();
        boolean leadingZero = true;
        while (!deque.isEmpty()) {
            char digit = deque.pollFirst();
            if (leadingZero && digit == '0') {
                continue;
            }
            leadingZero = false;
            ret.append(digit);
        }
        return ret.length() == 0 ? "0" : ret.toString();
    }
}
