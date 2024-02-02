package com.template.数组;

/**
 * @author Minus
 * @date 2023/11/13 17:47
 */
public class 方向数组 {

    public int numRookCaptures(char[][] board) {
        int cnt = 0, st = 0, ed = 0;
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (board[i][j] == 'R') {
                    st = i;
                    ed = j;
                    break;
                }
            }
        }

        // 引入方向数组 上右下左
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};
        for (int i = 0; i < 4; i++) {
            for (int step = 0; ; step++) {
                int tx = st + step * dx[i];
                int ty = ed + step * dy[i];
                if (tx < 0 || tx >= 8 || ty < 0 || ty >= 8 || board[tx][ty] == 'B') {
                    break;
                }
                if (board[tx][ty] == 'p') {
                    cnt++;
                    break;
                }
            }
        }
        return cnt;
    }
}
