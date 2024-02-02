package com.template.数组;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Minus
 * @date 2023/11/14 23:45
 */
public class 方向数组2 {
    private final static int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
    public List<List<Integer>> queensAttacktheKing(int[][] queens, int[] king) {
        boolean[][] isQueen = new boolean[8][8];
        for (int[] q : queens) {
            isQueen[q[0]][q[1]] = true;
        }
        List<List<Integer>> resList = new ArrayList<>();
        for (int[] direction : directions) {
            int x = king[0] + direction[0];
            int y = king[1] + direction[1];
            while (x >= 0 && y >= 0 && x < 8 && y < 8) {
                if (isQueen[x][y]) {
                    resList.add(Arrays.asList(x, y));
                    break;
                }
                x += direction[0];
                y += direction[1];
            }
        }
        return resList;
    }
}
