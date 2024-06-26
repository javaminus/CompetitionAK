class Solution {
    private int nodeId, size;
    public int minMalwareSpread(int[][] graph, int[] initial) {
        int n = graph.length;
        boolean[] visited = new boolean[n];
        boolean[] isInitial = new boolean[n];
        int mn = Integer.MAX_VALUE;
        for (int x : initial) {
            isInitial[x] = true;
            mn = Math.min(mn, x);
        }
        int[] cnt = new int[n];
        for (int i = 0; i < n; i++) {
            if (visited[i] || isInitial[i]) {
                continue;
            }
            nodeId = -1;
            size = 0;
            dfs(i, graph, visited, isInitial);
            if (nodeId >= 0) {
                cnt[nodeId] += size;
            }
        }

        int maxCnt = 0;
        int minNodeId = -1;
        for (int i = 0; i < n; i++) {
            if (cnt[i] > maxCnt) {
                maxCnt = cnt[i];
                minNodeId = i;
            }
        }
        return minNodeId < 0 ? mn : minNodeId;
    }

    private void dfs(int x, int[][] graph, boolean[] visited, boolean[] isInitial) {
        visited[x] = true;
        size++;
        for (int y = 0; y < graph.length; y++) {
            if (graph[x][y] == 0) {
                continue;
            }
            if (isInitial[y]) {
                if (nodeId != -2 && nodeId != y) {
                    nodeId = nodeId == -1 ? y : -2;
                }
            } else if (!visited[y]) {
                dfs(y, graph, visited, isInitial);
            }

        }
    }
}