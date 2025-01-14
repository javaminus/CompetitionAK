class Solution {
    public int numRookCaptures(char[][] board) {
        int n = board.length;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int r = 0, c = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'R') {
                    r = i;
                    c = j;
                    break;
                }
            }
        }
        int ans = 0;
        for (int[] d : dirs) {
            int nr = r, nc = c;
            while (nr >= 0 && nr < n && nc >= 0 && nc < n && board[nr][nc] != 'B') {
                if (board[nr][nc] == 'p') {
                    ans++;
                    break;
                }
                nr += d[0];
                nc += d[1];
            }
        }
        return ans;
    }
}