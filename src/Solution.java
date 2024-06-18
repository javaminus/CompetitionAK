import java.util.*;

class Solution {
    public long minimumWeight(int n, int[][] edges, int src1, int src2, int dest) {
        List<int[]>[] posPath = new List[n];// 正向建图
        List<int[]>[] negPath = new List[n];// 反向建图
        for (int i = 0; i < n; i++) {
            posPath[i] = new ArrayList<>();
            negPath[i] = new ArrayList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            posPath[edges[i][0]].add(new int[] { edges[i][1], edges[i][2] });
            negPath[edges[i][1]].add(new int[] { edges[i][0], edges[i][2] });
        }
        long d1[] = new long[n];// s1到各个点的最短距离
        long d2[] = new long[n];// s2到各个点的最短距离
        long d3[] = new long[n];// dest到各个点的最短距离
        Arrays.fill(d1, (long) 1e10 + 5);
        Arrays.fill(d2, (long) 1e10 + 5);
        Arrays.fill(d3, (long) 1e10 + 5);
        d1[src1] = 0;
        d2[src2] = 0;
        d3[dest] = 0;
        findShortestPath(posPath, d1, src1);
        findShortestPath(posPath, d2, src2);
        if (d1[dest] > 1e10 || d2[dest] > 1e10) {
            return -1;
        }
        findShortestPath(negPath, d3, dest);
        long ans = (long) 1e10;
        for (int i = 0; i < n; i++) {
            if (d3[i] > 1e10 || d2[i] > 1e10 || d1[i] > 1e10) {
                continue;
            }
            ans = Math.min(ans, d1[i] + d2[i] + d3[i]);
        }
        return ans;
    }

    public void findShortestPath(List<int[]>[] path, long d[], int start) {
        Queue<Pair> q = new PriorityQueue<>((a, b) -> a.d < b.d ? -1 : 1);
        q.add(new Pair(start, 0));
        while (q.size() > 0) {
            Pair a = q.poll();
            if (a.d > d[a.p]) {
                continue;
            } // 关键，不超时就靠这个了
            List<int[]> list = path[a.p];
            for (int i = 0; i < list.size(); i++) {
                int b[] = list.get(i);
                long distance = d[a.p] + b[1];
                if (distance < d[b[0]]) {
                    d[b[0]] = distance;
                    q.add(new Pair(b[0], distance));
                }
            }
        }
    }
}

class Pair {
    int p;
    long d;

    public Pair(int p, long d) {
        this.p = p;
        this.d = d;
    }
}