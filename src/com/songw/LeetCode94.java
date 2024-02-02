package com.songw;

import org.junit.Test;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.List;

/*
94. 二叉树的中序遍历
给定一个二叉树的根节点 root ，返回它的 中序 遍历。
*/
public class LeetCode94 {
    public List<Integer> inorderTraversal(TreeNode root){
        ArrayList<Integer> res=new ArrayList<Integer>();
        inorder(root,res);
        return res;
    }
    public void inorder(TreeNode root,ArrayList<Integer> res){
        if(root==null){
            return;
        }
        inorder(root.left,res);
        res.add(root.val);
        inorder(root.right,res);
    }

     public class TreeNode {
      int val;
     TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
         this.val = val;
          this.left = left;
          this.right = right;
    }
  }
}
