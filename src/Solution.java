import java.util.ArrayList;
import java.util.List;

class Solution {
    List<Integer>[] g;
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        g = new ArrayList[n];
        for(int i = 0; i < n; i++){
            g[i] = new ArrayList<>();
        }
        for(int[] e : edges){
            int start = e[0];
            int end = e[1];
            g[start].add(end);
            g[end].add(start);
        }
        return dfs(0, -1, hasApple);
    }
    // 从当前节点为根开始收集苹果，最少花费多少秒
    // 参数father表示当前结点是从哪个结点递归来的，防止递归循环
    int dfs(int x, int fa, List<Boolean> hasApple){
        int res = 0;
        for(int y : g[x]){
            if(y == fa){
                continue;
            }
            res += dfs(y, x, hasApple);
        }
        // bug1：如果不添加这个if判断，退出根节点的递归会加+2
        if(fa == -1){
            return res;
        }
        return (!hasApple.get(x) && res == 0) ? 0 : res + 2;
    }
}