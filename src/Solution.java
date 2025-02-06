import java.util.Arrays;

// 巴什博弈(SG函数求解过程展示)
// 一共有n颗石子，两个人轮流拿，每次可以拿1~m颗石子
// 拿到最后一颗石子的人获胜，根据n、m返回谁赢
// 对数器验证
class Solution {
    public static void main(String[] args, int m, int n) {
        int[] sg = new int[n + 1];
        boolean[] vis = new boolean[m + 1];
        for (int i = 1; i <= n; i++) {
            Arrays.fill(vis, false);
            for (int j = 1; j <= m && i - j >= 0; j++) {
                vis[sg[i - j]] = true;
            }
            for (int s = 0; s <= m; s++) {
                if (!vis[s]) {
                    sg[s] = s;
                    break;
                }
            }
        }
    }
}