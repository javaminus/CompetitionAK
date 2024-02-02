package com.songw;
/*给你两棵二叉树的根节点 p 和 q ，编写一个函数来检验这两棵树是否相同。

如果两个树在结构上相同，并且节点具有相同的值，则认为它们是相同的。*/
public class LeetCode100 {
    public boolean isSameTree(TreeNode p, TreeNode q) {
     if(p==null&&q==null){
         return true;
     }
     if(p==null||q==null){
         return false;
     }
     if(p.val!=q.val){
         return false;
     }
        return isSameTree(q.left, p.left) && isSameTree(q.right, p.right);

    }
      public class TreeNode {
        int val;
         TreeNode left;
          TreeNode right;
          TreeNode(int x) { val = x; }
      }

}
