package com.Java_Template.backtrack;

import java.util.ArrayList;
import java.util.List;

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
