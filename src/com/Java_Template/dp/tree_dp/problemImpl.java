package com.Java_Template.dp.tree_dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class problemImpl implements problem {

    // 834. 树中距离之和
    List<Integer>[] g;
    int[] size; // 当前节点的子树大小，算上自己
    int[] ans;
    @Override
    public int[] sumOfDistancesInTree(int n, int[][] edges) {
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        size = new int[n];
        ans = new int[n];
        dfs(0, -1, 0);
        reRoot(0, -1);
        return ans;
    }

    private void dfs(int x, int fa, int depth) {
        ans[0] += depth;
        size[x] = 1;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x, depth + 1);
                size[x] += size[y];
            }
        }
    }


    private void reRoot(int x, int fa) {
        for (int y : g[x]) {
            if (y != fa) {
                ans[y] = ans[x] + g.length - 2 * size[y];
                reRoot(y, x);
            }
        }
    }


    // 2581. 统计可能的树根数目
    int k;
    // List<Integer>[] g;
    HashSet<Long> set;
    int cnt0;
    int ans1;
    public int rootCount(int[][] edges, int[][] guesses, int k) {
        this.k = k;
        g = new List[edges.length + 1];
        Arrays.setAll(g, e -> new ArrayList<>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        set = new HashSet<Long>();
        for (int[] guess : guesses) {
            int x = guess[0], y = guess[1];
            set.add(((long) x << 32 | y));
        }
        cnt0 = 0;
        ans1 = 0;
        dfs(0, -1);
        reRoot(0, -1, cnt0);
        return ans1;
    }

    private void dfs(int x, int fa) {
        for (int y : g[x]) {
            if (y != fa) {
                if (set.contains((long) x << 32 | y)) {
                    cnt0++;
                }
                dfs(y, x);
            }
        }
    }

    private void reRoot(int x, int fa, int cnt) {
        if (cnt >= k) {
            ans1++;
        }
        for (int y : g[x]) {
            if (y != fa) {
                int c = cnt;
                if (set.contains((long) x << 32 | y)) {
                    c--;
                }
                if (set.contains((long) y << 32 | x)) {
                    c++;
                }
                reRoot(y, x, c);
            }
        }
    }


    // 1372. 二叉树中的最长交错路径
    public int longestZigZag(TreeNode root) {
        return Math.max(dfs(root.left, 1, true), dfs(root.right, 1, false)) - 1;
    }
    /**
     * @param root 当前节点
     * @param num 已经遍历的节点数
     * @param isLeft 是否为左节点
     * @return int
     */
    private int dfs(TreeNode root, int num, boolean isLeft) {
        if (root == null) {
            return num;
        }
        if (isLeft) { // 当前是左边，那么下一个节点应该是右边或则继续左边就重新计数
            return Math.max(dfs(root.left, 1, true), dfs(root.right, num + 1, false));
        }else{
            return Math.max(dfs(root.right, 1, false), dfs(root.left, num + 1, true));
        }
    }

    // 树形dp思路：自底向上
    int maxLength = 0;
    public int longestZigZag1(TreeNode root) {
        dfs1372(root);
        return maxLength;
    }
    private int[] dfs1372(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0}; // 可以不写
        }
        int[] lengths = new int[2];
        if (root.left != null) {
            int[] left = dfs1372(root.left);
            lengths[0] = left[1] + 1;
        }
        if (root.right != null) {
            int[] right = dfs1372(root.right);
            lengths[1] = right[0] + 1;
        }
        maxLength = Math.max(maxLength, Math.max(lengths[0], lengths[1]));
        return lengths;
    }




    // 124. 二叉树中的最大路径和  后续遍历思想，也就是自底向上，dp思想
    private int ans2 = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        dfs(root);
        return ans2;
    }
    private int dfs(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = dfs(root.left), right = dfs(root.right);
        int t = root.val;
        if (left >= 0) {
            t += left;
        }
        if (right >= 0) {
            t += right;
        }
        ans2 = Math.max(ans2, t); // 以root为根节点，最大的ans
        return Math.max(Math.max(left, right) + root.val, root.val); // 这里的左右两边只能选择一边构成路径，如果left<0&&right<0，那么就返回root.val
    }


    // 98. 验证二叉搜索树
    // 先序写法，不过一定要理解后序的写法
    public boolean isValidBST(TreeNode root) {
        return isValidBSTDFS(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    private boolean isValidBSTDFS(TreeNode root, long left, long right) {
        if (root == null) {
            return true;
        }
        int x = root.val;
        return left < x && right > x && isValidBSTDFS(root.left, left, x) && isValidBSTDFS(root.right, x, right);
    }
    // 重点：后序写法
    public boolean isValidBST1(TreeNode root) {
        return isValidBSTDFS1(root)[1] != Long.MAX_VALUE;
    }
    // left[] 是当前左子树对应的两个节点，left[0] = 左， left[1] = 右
    private long[] isValidBSTDFS1(TreeNode root) {
        if (root == null) {
            return new long[]{Long.MAX_VALUE, Long.MIN_VALUE}; // 子树的（最小值，最大值）,这样写下面的判断就为true
        }
        long[] left = isValidBSTDFS1(root.left);
        long[] right = isValidBSTDFS1(root.right);
        long x = root.val;
        if (x <= left[1] || x >= right[0]) {
            return new long[]{Long.MIN_VALUE, Long.MAX_VALUE};
        }
        return new long[]{Math.min(left[0], x), Math.max(right[1], x)};
    }



    // 1373. 二叉搜索子树的最大键值和
    int maxSum = 0;
    public int maxSumBST(TreeNode root) {
        getMaxSum(root);
        return maxSum;
    }

    /**
     * @return {sum,lowerBound,upperBound,isBST}
     */
    private int[] getMaxSum(TreeNode root) {
        if (root == null) {
            return new int[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 1};
        }
        int[] left = getMaxSum(root.left);
        int[] right = getMaxSum(root.right);
        int leftSum = left[0], rightSum = right[0];
        int leftLowerBound = left[1], rightLowerBound = right[1], leftUpperBound = left[2], rightUpperBound = right[2];
        int leftIsBST = left[3], rightIsBST = right[3];
        int sum = leftSum + rightSum + root.val;
        int lowBound = Math.min(root.val, Math.min(leftLowerBound, rightLowerBound));
        int upperBound = Math.max(root.val, Math.max(leftUpperBound, rightUpperBound));
        int isBST = root.val > leftUpperBound && root.val < rightLowerBound && leftIsBST == 1 && rightIsBST == 1 ? 1 : 0;
        if (isBST == 1) {
            maxSum = Math.max(maxSum, sum);
        }
        return new int[]{sum, lowBound, upperBound, isBST};
    }
}
