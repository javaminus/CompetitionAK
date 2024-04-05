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
            return new int[]{0, 0};
        }
        int[] left = dfs1372(root.left);
        int[] right = dfs1372(root.right);
        int[] length = new int[2];
        length[0] = left[1] + 1;
        length[1] = right[0] + 1;
        maxLength = Math.max(maxLength, Math.max(length[0], length[1]));
        return length;
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

    // 337. 打家劫舍 III
    public int rob(TreeNode root) {
        int[] res = dfs337(root);
        return Math.max(res[0], res[1]);
    }
    /**
     * {不选当前节点的sum,选择当前节点的sum}
     */
    private int[] dfs337(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs337(root.left);
        int[] right = dfs337(root.right);
        int rob = left[0] + right[0] + root.val; // 选当前节点，不可以选下一层节点
        int notRob = Math.max(left[0], left[1]) + Math.max(right[0], right[1]); // 不选当前节点，那么下一层可以选，也可以不选
        return new int[]{notRob, rob};
    }


    // 543. 二叉树的直径
    public int diameterOfBinaryTree(TreeNode root) {
        dfsPro543(root);
        return ans1 - 1; // 我的ans其实是统计的节点总数
    }

    private int[] dfs543(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        int[] left = dfs543(root.left), right = dfs543(root.right);
        ans1 = Math.max(ans1, Math.max(left[0], left[1]) + Math.max(right[0], right[1]) + 1); // 当前节点作为根节点
        return new int[]{Math.max(left[0], left[1]) + 1, Math.max(right[0], right[1]) + 1};
    }

    // 优化
    private int dfsPro543(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = dfsPro543(root.left);
        int right = dfsPro543(root.right);
        ans1 = Math.max(ans1, left + right + 1);
        return Math.max(left, right) + 1;
    }

    // 2646. 最小化旅行的价格总和
    int[] cnt, price;
    int end;
    public int minimumTotalPrice(int n, int[][] edges, int[] price, int[][] trips) {
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        cnt = new int[n];
        for (int[] trip : trips) {
            end = trip[1];
            dfs2646(trip[0], -1);
        }
        this.price = price;
        int[] ans = robDfs(0, -1);
        return Math.min(ans[0], ans[1]);
    }
    private boolean dfs2646(int x, int fa) {
        if (x == end) {
            cnt[x]++;
            return true;
        }
        for (int y : g[x]) {
            if (y != fa && dfs2646(y, x)) {
                cnt[x]++; // x 是 end 的祖先节点，也就在路径上
                return true;
            }
        }
        return false;
    }
    // 打家劫舍III {不变，变}
    private int[] robDfs(int x, int fa) {
        int notHalve = price[x] * cnt[x];
        int halve = notHalve / 2;
        for (int y : g[x]) {
            if (y != fa) {
                int[] res = robDfs(y, x);
                notHalve += Math.min(res[0], res[1]);
                halve += res[0];
            }
        }
        return new int[]{notHalve, halve};
    }


    // 2246. 相邻字符不同的最长路径  学习构造树结构以及树形dp
    String s;
    public int longestPath(int[] parent, String s) {
        int n = s.length();
        this.s = s;
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int i = 1; i < n; i++) {
            g[parent[i]].add(i);
        }
        dfs(0);
        return ans1 + 1;
    }
    private int dfs(int x) {
        int maxLen = 0; // 以当前节点为起点的最长单向路径
        for (int y : g[x]) {
            int len = dfs(y) + 1;
            if (s.charAt(y) != s.charAt(x)) {
                ans1 = Math.max(ans1, maxLen + len);
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }



    // 968. 监控二叉树
    public int minCameraCover(TreeNode root) {
        int[] ans = dfs968(root);
        return Math.min(ans[0], ans[1]);
    }
    /**
     * @return {当前节点安装摄像头sum,至少一个子节点带摄像头sum，父节点带摄像头sum}
     *
     * 动态规划需要定义状态，由于每个结点被监控的情况有三种，因此每个结点的状态值包括三项，分别对应三种情况的最小摄像头数量。
     * 每个结点的状态使用长度为 3 的数组 cameras 表示，分别对应三种情况下的以该结点作为根结点的子树被完全监控的最小摄像头数量，
     * 其中 cameras[0]表示该结点安装摄像头的最小摄像头数量(所以初始化为无穷大)，cameras[1]表示该结点的至少一个子结点安装摄像头的最小摄像头数量，cameras[2]表示该结点的父结点安装摄像头的最小摄像头数量。
     * 以下将 cameras[0]、cameras[1]、cameras[2]分别称为状态 0、状态 1、状态 2。
     * 动态规划的边界情况是空结点，空结点不能安装摄像头，因此用 +∞表示空结点安装摄像头的最小摄像头数量，用 0表示空结点不安装摄像头的最小摄像头数量，对应的状态值为 [+∞,0,0]。
     */
    private int[] dfs968(TreeNode root) {
        if (root == null) {
            return new int[]{Integer.MAX_VALUE / 2, 0, 0};
        }
        int[] leftCameras = dfs968(root.left);
        int[] rightCameras = dfs968(root.right);
        int[] cameras = new int[3];
        cameras[0] = Math.min(Math.min(leftCameras[0], leftCameras[1]), leftCameras[2]) + Math.min(Math.min(rightCameras[0], rightCameras[1]), rightCameras[2]) + 1;// 当前节点装上摄像头，数量加一
        cameras[1] = Math.min(leftCameras[0] + rightCameras[0], Math.min(leftCameras[1] + rightCameras[0], leftCameras[0] + rightCameras[1]));
        cameras[2] = Math.min(leftCameras[0], leftCameras[1]) + Math.min(rightCameras[0], rightCameras[1]);
        return cameras;
    }

    // 1026. 节点与其祖先之间的最大差值
    private int res = 0;
    public int maxAncestorDiff(TreeNode root) {
        dfs1026(root);
        return res;
    }
    // 自底向上 {子树的最小值，子树最大值}
    private int[] dfs1026(TreeNode root) {
        if (root == null) {
            return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        }
        int[] left = dfs1026(root.left);
        int[] right = dfs1026(root.right);
        int minX = Math.min(root.val, Math.min(left[0], right[0]));
        int maxX = Math.max(root.val, Math.max(left[1], right[1]));
        res = Math.max(res, Math.max(root.val - minX, maxX - root.val));
        return new int[]{minX, maxX};
    }
}
