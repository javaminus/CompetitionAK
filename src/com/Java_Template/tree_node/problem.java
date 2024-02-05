package com.Java_Template.tree_node;

import java.util.List;

/**
 * 算法：二叉树、N叉树、先序遍历、中序遍历、后序遍历、迭代实现、前序遍历
 * 功能：
 * 参考：
 * 题目：
 *
 * ===================================力扣===================================
 * 94. 二叉树的中序遍历（https://leetcode.cn/problems/binary-tree-inorder-traversal/description/）
 * 144. 二叉树的前序遍历（https://leetcode.cn/problems/binary-tree-preorder-traversal/description/）
 * 145. 二叉树的后序遍历（https://leetcode.cn/problems/binary-tree-postorder-traversal/）
 * 102. 二叉树的层序遍历(https://leetcode.cn/problems/binary-tree-level-order-traversal/description/)
 * 104. 二叉树的最大深度(https://leetcode.cn/problems/maximum-depth-of-binary-tree/description/?envType=study-plan-v2&envId=top-interview-150)
 * 100. 相同的树(https://leetcode.cn/problems/same-tree/description/?envType=study-plan-v2&envId=top-interview-150)
 * 226. 翻转二叉树(https://leetcode.cn/problems/invert-binary-tree/description/?envType=study-plan-v2&envId=top-interview-150)
 * 101. 对称二叉树(https://leetcode.cn/problems/symmetric-tree/description/?envType=study-plan-v2&envId=top-interview-150)
 * 105. 从前序与中序遍历序列构造二叉树(https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-inorder-traversal/description/?envType=study-plan-v2&envId=top-interview-150)
 * 106. 从中序与后序遍历序列构造二叉树(https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal/description/?envType=study-plan-v2&envId=top-interview-150)
 * 889. 根据前序和后序遍历构造二叉树(https://leetcode.cn/problems/construct-binary-tree-from-preorder-and-postorder-traversal/description/?envType=daily-question&envId=2024-02-22)
 * 117. 填充每个节点的下一个右侧节点指针 II(https://leetcode.cn/problems/populating-next-right-pointers-in-each-node-ii/?envType=study-plan-v2&envId=top-interview-150)
 * 114. 二叉树展开为链表(https://leetcode.cn/problems/flatten-binary-tree-to-linked-list/?envType=study-plan-v2&envId=top-interview-150)
 * 112. 路径总和(https://leetcode.cn/problems/path-sum/description/?envType=study-plan-v2&envId=top-interview-150)
 * 129. 求根节点到叶节点数字之和(https://leetcode.cn/problems/sum-root-to-leaf-numbers/description/?envType=study-plan-v2&envId=top-interview-150)
 * 124. 二叉树中的最大路径和(https://leetcode.cn/problems/binary-tree-maximum-path-sum/description/?envType=study-plan-v2&envId=top-interview-150)
 * 173. 二叉搜索树迭代器(https://leetcode.cn/problems/binary-search-tree-iterator/description/?envType=study-plan-v2&envId=top-interview-150)
 * 222. 完全二叉树的节点个数(https://leetcode.cn/problems/count-complete-tree-nodes/description/?envType=study-plan-v2&envId=top-interview-150)
 * 236. 二叉树的最近公共祖先(https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/description/?envType=study-plan-v2&envId=top-interview-150)
 * 235. 二叉搜索树的最近公共祖先(https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-search-tree/description/?envType=daily-question&envId=2024-02-25)
 * 199. 二叉树的右视图(https://leetcode.cn/problems/binary-tree-right-side-view/description/?envType=study-plan-v2&envId=top-interview-150)
 * 637. 二叉树的层平均值(https://leetcode.cn/problems/average-of-levels-in-binary-tree/description/?envType=study-plan-v2&envId=top-interview-150)
 * 103. 二叉树的锯齿形层序遍历(https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/description/?envType=study-plan-v2&envId=top-interview-150)
 * 530. 二叉搜索树的最小绝对差(https://leetcode.cn/problems/minimum-absolute-difference-in-bst/description/?envType=study-plan-v2&envId=top-interview-150)
 * 230. 二叉搜索树中第K小的元素(https://leetcode.cn/problems/kth-smallest-element-in-a-bst/description/?envType=study-plan-v2&envId=top-interview-150)
 * 98. 验证二叉搜索树(https://leetcode.cn/problems/validate-binary-search-tree/description/?envType=study-plan-v2&envId=top-interview-150)
 * LCR 047. 二叉树剪枝(https://leetcode.cn/problems/pOCWxh/description/)
 * 二叉树的堂兄弟节点 II(https://leetcode.cn/problems/cousins-in-binary-tree-ii/description/?envType=daily-question&envId=2024-02-07)
 * 2867. 统计树中的合法路径数目(https://leetcode.cn/problems/count-valid-paths-in-a-tree/description/?envType=daily-question&envId=2024-02-27)
 * 2673. 使二叉树所有路径值相等的最小代价(https://leetcode.cn/problems/make-costs-of-paths-equal-in-a-binary-tree/description/?envType=daily-question&envId=2024-02-28)
 */
public interface problem {
    // 104. 二叉树的最大深度
    public int maxDepth(TreeNode root);

    // 100. 相同的树
    public boolean isSameTree(TreeNode p, TreeNode q);

    // 226. 翻转二叉树
    public TreeNode invertTree(TreeNode root);

    // 101. 对称二叉树
    public boolean isSymmetric(TreeNode root);

    // 105. 从前序与中序遍历序列构造二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder);

    // 106. 从中序与后序遍历序列构造二叉树
    public TreeNode buildTree1(int[] inorder, int[] postorder);

    // 889. 根据前序和后序遍历构造二叉树
    public TreeNode constructFromPrePost(int[] pre, int[] post);

    // 117. 填充每个节点的下一个右侧节点指针 II
    public Node connect(Node root);

    // 114. 二叉树展开为链表
    public void flatten(TreeNode root);

    // 112. 路径总和
    public boolean hasPathSum(TreeNode root, int targetSum);

    //129. 求根节点到叶节点数字之和
    public int sumNumbers(TreeNode root);

    // 124. 二叉树中的最大路径和
    public int maxPathSum(TreeNode root);

    // 222. 完全二叉树的节点个数
    public int countNodes(TreeNode root);

    // 236. 二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q);

    // 235. 二叉搜索树的最近公共祖先
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q);

    // 199. 二叉树的右视图
    public List<Integer> rightSideView(TreeNode root);

    // 637. 二叉树的层平均值
    public List<Double> averageOfLevels(TreeNode root);

    // 103. 二叉树的锯齿形层序遍历
    public List<List<Integer>> zigzagLevelOrder(TreeNode root);

    // 530. 二叉搜索树的最小绝对差
    public int getMinimumDifference(TreeNode root);

    // 230. 二叉搜索树中第K小的元素
    public int kthSmallest(TreeNode root, int k);

    // 98. 验证二叉搜索树
    public boolean isValidBST(TreeNode root);

    // LCR 047. 二叉树剪枝
    public TreeNode pruneTree(TreeNode root);

    // 2641. 二叉树的堂兄弟节点 II
    public TreeNode replaceValueInTree(TreeNode root);
}
