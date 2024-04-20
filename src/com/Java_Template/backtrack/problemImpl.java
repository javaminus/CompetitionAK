package com.Java_Template.backtrack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
}
