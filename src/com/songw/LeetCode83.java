package com.songw;

import org.junit.Test;
/*
存在一个按升序排列的链表，给你这个链表的头节点 head ，请你删除所有重复的元素，使每个元素 只出现一次 。

        返回同样按升序排列的结果链表。
*/


public class LeetCode83 {
    @Test
    public void test(){
        ListNode head=new ListNode(0);

        System.out.println(deleteDuplicates(head));

    }
    public ListNode deleteDuplicates(ListNode head) {
        if(head==null){
            return head;
        }
        ListNode cur=head;
        while(cur.next!=null){
            if(cur==cur.next){
                cur.next=cur.next.next;
            }else cur=cur.next;
        }
        return head;
    }

    public class ListNode{
        int val;
        ListNode next;
        ListNode(int val){
            this.val=val;
        }
    }
}
