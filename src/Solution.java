class TreeNode {
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
class Solution {
    private int ans = 0;
    public int maxAncestorDiff(TreeNode root) {
        dfs(root);
        return ans;
    }
    // 自底向上 {子树的最小值，子树最大值}
    private int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        }
        int[] left = dfs(root.left);
        int[] right = dfs(root.right);
        int minX = Math.min(root.val, Math.min(left[0], right[0]));
        int maxX = Math.max(root.val, Math.max(left[1], right[1]));
        ans = Math.max(ans, Math.max(root.val - minX, maxX - root.val));
        return new int[]{minX, maxX};
    }
}