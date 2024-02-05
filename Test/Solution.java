class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.countSubmatrices(new int[][]{{7, 6, 3}, {6, 6, 1}}, 18);
    }
    int ans = 0;
    private final int[][] direction = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public int countSubmatrices(int[][] grid, int k) {
        int m = grid.length, n = grid[0].length;
        if (grid[0][0] > k) {
            return 0;
        }
        boolean[][] visited = new boolean[m][n];
        dfs(grid, k, 0, 0, grid[0][0], visited);
        return ans;
    }


    private void dfs(int[][] grid, int k, int x, int y, int sum, boolean[][] visited) {
        int m = grid.length, n = grid[0].length;
        System.out.println(x + " " + y + " " + sum);
        visited[x][y] = true;
        ans++;
        for (int[] d : direction) {
            x = x + d[0]; y = y + d[1];
            if (x >= 0 && y >= 0 && x < m && y < n && sum + grid[x][y] <= k&&!visited[x][y] ) {
                dfs(grid, k, x, y, sum + grid[x][y], visited);
            }
            x -= d[0];
            y -= d[1];
        }
    }
}