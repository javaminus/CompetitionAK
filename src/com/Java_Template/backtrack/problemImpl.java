package com.Java_Template.backtrack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 */
public class problemImpl implements problem{
    private boolean[][] visited;
    private final int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    private char[][] board;
    private String word;
    @Override
    public boolean exist(char[][] board, String word) {
        this.board = board;
        this.word = word;
        int m = board.length, n = board[0].length;
        visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean dfs(int i, int j, int index) {
        if (word.charAt(index) != board[i][j]) {
            return false;
        }
        if (index == word.length() - 1) {
            return true;
        }
        visited[i][j] = true;
        for (int[] d : directions) {
            int newi = i + d[0], newj = j + d[1];
            if (newi >= 0 && newi < board.length && newj >= 0 && newj < board[0].length && !visited[newi][newj]) {
                if (dfs(newi, newj, index + 1)) {
                    return true;
                }
            }
        }
        // 不选这个点进  回溯
        visited[i][j] = false;
        return false;
    }


    // 494. 目标和
    int ans = 0;
    public int findTargetSumWays(int[] nums, int target) {
        backtrack(nums, target, 0, 0);
        return ans;
    }
    private void backtrack(int[] nums, int target, int index, int sum) {
        if (index == nums.length) {
            if (sum == target) {
                ans++;
            }
        }else{
            backtrack(nums, target, index + 1, sum + nums[index]);
            backtrack(nums, target, index + 1, sum - nums[index]);
        }
    }


    // 78.子集
    int[] nums;
    List<List<Integer>> ans1;
    List<Integer> path;
    public List<List<Integer>> subsets(int[] nums) {
        this.nums = nums;
        ans1 = new ArrayList<>();
        path = new ArrayList<>();
        dfs(0);
        return ans1;
    }
    private void dfs(int i) {
        ans1.add(new ArrayList<>(path));
        if (i == nums.length) {
            return;
        }
        for (int j = i; j < nums.length; j++) {
            path.add(nums[j]);
            dfs(j + 1);
            path.remove(path.size() - 1);
        }
    }


    // 894. 所有可能的真二叉树
    public List<TreeNode> allPossibleFBT(int n) {
        ArrayList<TreeNode> ans = new ArrayList<>();
        if (n % 2 == 0) {
            return ans;
        }
        if (n == 1) {
            ans.add(new TreeNode(0));
            return ans;
        }
        for (int i = 1; i < n; i += 2) {
            List<TreeNode> left = allPossibleFBT(i);
            List<TreeNode> right = allPossibleFBT(n - i - 1);
            for (TreeNode l : left) {
                for (TreeNode r : right) {
                    TreeNode node = new TreeNode(0, l, r);
                    ans.add(node);
                }
            }
        }
        return ans;
    }




}

// 蓝桥杯十四届java研究生组第二题(https://www.lanqiao.cn/problems/3552/learning/)
class Main1 {
    static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    private static int[][] nums = new int[5][5];
    private static int ans = 0;
    public static void main(String[] args) {
        nums[0][0] = 1;
        nums[0][1] = 0;
        nums[0][2] = 1;
        nums[0][3] = 0;
        nums[0][4] = 1;
        dfs(1, 0);
        System.out.println(ans);
        sc.close();
    }

    private static void dfs(int i, int j) { // 行列
        if (i == 5) {
            if (nums[4][0] == 1) {
                ans++;
            }
            return;
        }

        for (int k = 0; k < 3; k++) {
            if (k == 0) {
                nums[i][j] = nums[i - 1][j] & nums[i - 1][j + 1];
            }
            if (k == 1) {
                nums[i][j] = nums[i - 1][j] | nums[i - 1][j + 1];
            }
            if (k == 2) {
                nums[i][j] = nums[i - 1][j] ^ nums[i - 1][j + 1];
            }
            if (j == 4 - i) {
                dfs(i + 1, 0);
            }else{
                dfs(i, j + 1);
            }
        }
    }

    /*  39. 组合总和
        给你一个 无重复元素 的整数数组 candidates 和一个目标整数 target ，找出 candidates 中可以使数字和为目标数 target 的 所有 不同组合 ，并以列表形式返回。你可以按 任意顺序 返回这些组合。
        candidates 中的 同一个 数字可以 "无限制重复"被选取 。如果至少一个数字的被选数量不同，则两种组合是不同的。
        对于给定的输入，保证和为 target 的不同组合数少于 150 个。*/
    // 这里主要因为每个数可以无限制重复的选取，所以dfs(i,j)
/*    List<List<Integer>> ans;
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        ans = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        dfs(0, 0, list, candidates, target);
        return ans;
    }

    private void dfs(int i, int sum, ArrayList<Integer> list, int[] candidates, int target) {
        if (sum == target) {
            ans.add(new ArrayList<>(list));
            return;
        }
        if (i == candidates.length || sum > target) {
            return;
        }
        for (int j = i; j < candidates.length; j++) {
            list.add(candidates[j]);
            dfs(j, sum + candidates[j], list, candidates, target); // 这里主要因为每个数可以无限制重复的选取，如果只能选一次就dfs(i+1,,,,,)
            list.remove(list.size() - 1);
        }
    }*/

    /*  216. 组合总和 III
        只使用数字1到9
        每个数字 最多使用一次
        返回 所有可能的有效组合的列表 。该列表不能包含相同的组合两次，组合可以以任何顺序返回。*/
/*    private List<List<Integer>> ans;
    private List<Integer> path;
    public List<List<Integer>> combinationSum3(int k, int n) {
        ans = new ArrayList<>();
        path = new ArrayList<>();
        dfs(1, 0, 0, k, n);
        return ans;
    }
    private void dfs(int i, int sum, int digitSum, int k, int n) {
        if (sum == n && digitSum == k) {
            ans.add(new ArrayList<>(path));
        }
        if (i > 9 || digitSum > k || sum > n) {
            return;
        }
        for (int j = i; j < 10; j++) {
            path.add(j);
            dfs(j + 1, sum + j, digitSum + 1, k, n); // 选择这个数
            path.remove(path.size() - 1);
        }
    }*/

    /*  LCR 082. 组合总和 II
        给定一个可能有重复数字的整数数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
        candidates 中的每个数字在每个组合中只能使用一次，解集不能包含重复的组合。*/
    /*  import java.util.*;

        class Solution {
            List<List<Integer>> ans;
            List<Integer> path;
            public List<List<Integer>> combinationSum2(int[] candidates, int target) {
                ans = new ArrayList<>();
                path = new ArrayList<>();
                Arrays.sort(candidates);
                dfs(0, 0, candidates, target);
                // res.addAll(ans);
                return ans;
            }
            private void dfs(int index, int sum, int[] candidates, int target) {
                if (sum == target) {
                    ans.add(new ArrayList<>(path));
                    return;
                }
                if (index == candidates.length || sum > target) {
                    return;
                }
                for (int i = index; i < candidates.length; i++) {
                    if (i > index && candidates[i] == candidates[i- 1]) { // i>index证明i与index在同一层 去重
                        continue;
                    }
                    path.add(candidates[i]);
                    dfs(i + 1, sum + candidates[i], candidates, target);
                    path.remove(path.size() - 1);
                }
            }
        }*/

    /*  LCR 050. 路径总和 III
        给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
        路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。*/
    public int pathSum(TreeNode root, long targetSum) { // 暴力写法
        if (root == null) {
            return 0;
        }
        long ans = dfs(root, targetSum, 0);
        ans += pathSum(root.left, targetSum);
        ans += pathSum(root.right, targetSum);
        return (int) ans;
    }
    private long dfs(TreeNode root, long targetSum, long sum) {
        if (root == null) {
            return 0;
        }
        long ret = 0;
        sum += root.val;
        if (sum == targetSum) {
            ret++;
        }
        ret += dfs(root.left, targetSum, sum);
        ret += dfs(root.right, targetSum, sum);
        return ret;
    }

    // 优化成O(n)的时间复杂度
    Map<Long, Integer> sumCountMap = new HashMap<>(); // HashMap的key是前缀和， value是该前缀和的节点数量，记录数量是因为有出现复数路径的可能。
    public int pathSum1(TreeNode root, long targetSum) {
        if (root == null) {
            return 0;
        }
        sumCountMap.put(0L, 1);
        return getCounts(root, targetSum, root.val);
    }
    private int getCounts(TreeNode root, long targetSum, long sum) {
        // 当前前缀和减去目标值 如果Map中有记录，则说明从某一点到当前节点的和等于目标值target，比如target = 10, 然后1 -> 1 -> 5 -> 4 -> 9 ->1 这一组的前缀和为【1，2，7，11，20，21】，当我们来到点4时，有11 - target = 1,则刚好有前缀和为1的一个节点
        int cnt = sumCountMap.getOrDefault(sum - targetSum, 0); // 两节点间的路径和 = 两节点的前缀和之差
        sumCountMap.merge(sum, 1, Integer::sum);
        if (root.left != null) {
            cnt += getCounts(root.left, targetSum, sum + root.left.val);
        }
        if (root.right != null) {
            cnt += getCounts(root.right, targetSum, sum + root.right.val);
        }
        sumCountMap.merge(sum, -1, Integer::sum); // 状态恢复
        return cnt;
    }


}
