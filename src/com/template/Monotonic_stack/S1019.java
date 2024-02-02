package com.template.Monotonic_stack;

import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * @author Minus
 * @date 2023/12/27 10:52
 */
public class S1019 {
    // 单调栈
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }}
    public int[] nextLargerNodes(ListNode head) {
        ListNode cur = head;
        ArrayList<Integer> ans = new ArrayList<>();
        ArrayDeque<int[]> stack = new ArrayDeque<>(); // new int[]{val,idx}
        int idx = -1;
        while (cur != null) {
            idx++;
            while (!stack.isEmpty() && cur.val > stack.peek()[0]) {
                ans.set(stack.pop()[1], cur.val);
            }
            stack.push(new int[]{cur.val, idx});
            cur = cur.next;
        }
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }
}
