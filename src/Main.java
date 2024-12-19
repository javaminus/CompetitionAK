import java.io.*;
import java.util.*;

public class Main {
    // 并查集
    static class UnionFind {
        private int[] pa;

        public UnionFind(int n) {
            pa = new int[n];
            for (int i = 0; i < n; ++i) pa[i] = i;
        }

        public int find(int x) {
            if (pa[x] != x) pa[x] = find(pa[x]); // 路径压缩
            return pa[x];
        }

        public void merge(int x, int y) {
            int px = find(x);
            int py = find(y);
            pa[px] = py;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        // 读取 n, m, q
        String[] firstLine = br.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int m = Integer.parseInt(firstLine[1]);
        int q = Integer.parseInt(firstLine[2]);

        // 存储朋友关系, 以及接下来会断开关系的边 (需要删除的边)
        Set<String> sp = new HashSet<>();
        Set<String> asp = new HashSet<>();

        // 存储查询
        List<int[]> queries = new ArrayList<>();

        // 存储所有节点的编号
        Set<Integer> node = new HashSet<>();

        // 处理输入的朋友关系
        for (int i = 0; i < m; ++i) {
            String[] parts = br.readLine().split(" ");
            int u = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            node.add(u);
            node.add(v);
            sp.add(u + "," + v);
        }

        // 处理查询
        for (int i = 0; i < q; ++i) {
            String[] parts = br.readLine().split(" ");
            int op = Integer.parseInt(parts[0]);
            int u = Integer.parseInt(parts[1]);
            int v = Integer.parseInt(parts[2]);
            queries.add(new int[]{op, u, v});
            node.add(u);
            node.add(v);
            if (op == 1 && (sp.contains(u + "," + v) || sp.contains(v + "," + u))) {
                asp.add(u + "," + v);
            }
        }

        // 离散化节点编号
        Map<Integer, Integer> ump = new HashMap<>();
        int idx = 1;
        for (int t : node) {
            ump.put(t, idx++);
        }

        // 初始化并查集
        UnionFind u1 = new UnionFind(idx);
        UnionFind u2 = new UnionFind(idx);

        // 如果不删除应该会有一个怎么样的并查集存在
        for (String edge : sp) {
            String[] parts = edge.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            u1.merge(ump.get(x), ump.get(y));
        }

        // 为初始的朋友建立关系
        for (String edge : sp) {
            String[] parts = edge.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            if (asp.contains(edge)) continue;
            u2.merge(ump.get(x), ump.get(y));
        }

        // 倒序遍历queries，将删边变成添加边
        List<String> ans = new ArrayList<>();
        for (int i = q - 1; i >= 0; --i) {
            int[] query = queries.get(i);
            int op = query[0], x = query[1], y = query[2];
            if (op == 2) {
                if (u2.find(ump.get(x)) == u2.find(ump.get(y))) {
                    ans.add("Yes");
                } else {
                    ans.add("No");
                }
            } else {
                if (u1.find(ump.get(x)) == u1.find(ump.get(y))) {
                    u2.merge(ump.get(x), ump.get(y));
                }
            }
        }

        // 倒序输出答案
        for (int i = ans.size() - 1; i >= 0; --i) {
            out.write(ans.get(i));
            out.write("\n");
        }
        out.flush();
    }
}