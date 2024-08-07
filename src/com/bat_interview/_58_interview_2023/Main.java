package com.bat_interview._58_interview_2023;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Minus
 * @date 2024/8/7 17:16
 */
public class Main {
    public static void main(String[] args) {
        int i = solve(new int[][]{{34, 39}, {38, 7}, {49, 84}, {50, 11},{72, 54}, {85, 16}, {90, 30}});
        // int i = solution.solve(new int[][]{{1,1},   {1,2},   {2,2},   {1,3},   {3,3}});
        System.out.println(i);
    }
    public static int solve(int[][] order) {
        TreeMap<Integer, TreeSet<Integer>> g = new TreeMap<>();
        for (int[] edge : order) {
            int y = edge[0], x = edge[1];
            g.computeIfAbsent(x, e -> new TreeSet<>()).add(y);
        }
        int preX = 0, preY1 = 0, preY2 = 0, currY1 = 0, currY2 = 0;
        int[][] dp = new int[g.size() + 1][2];
        int i = 1;
        for (Map.Entry<Integer, TreeSet<Integer>> entry : g.entrySet()) {
            Integer x = entry.getKey();
            TreeSet<Integer> setY = entry.getValue();
            if (setY.size() == 1) {
                currY1 = currY2 = setY.pollFirst();
                dp[i][0] = Math.min(dp[i - 1][0] + cal(preX, preY1, x, currY2) + cal(x, currY1, x, currY2), dp[i - 1][1] + cal(preX, preY2, x, currY2) + cal(x, currY1, x, currY2));
                dp[i][1] = Math.min(dp[i - 1][0] + cal(preX, preY1, x, currY1) + cal(x, currY1, x, currY2), dp[i - 1][1] + cal(preX, preY2, x, currY1) + cal(x, currY1, x, currY2));
                preY1 = preY2 = currY1;
            } else if (setY.size() > 1) {
                currY1 = setY.pollFirst();
                currY2 = setY.pollLast();
                dp[i][0] = Math.min(dp[i - 1][0] + cal(preX, preY1, x, currY2) + cal(x, currY1, x, currY2), dp[i - 1][1] + cal(preX, preY2, x, currY2) + cal(x, currY1, x, currY2));
                dp[i][1] = Math.min(dp[i - 1][0] + cal(preX, preY1, x, currY1) + cal(x, currY1, x, currY2), dp[i - 1][1] + cal(preX, preY2, x, currY1) + cal(x, currY1, x, currY2));
                preY1 = currY1;
                preY2 = currY2;
            }
            preX = x;
            i++;
        }
        return Math.min(dp[g.size()][0], dp[g.size()][1]);
    }

    private static int cal(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }
}
