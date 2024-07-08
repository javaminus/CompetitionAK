## [小红的树上删边](https://ac.nowcoder.com/acm/contest/80743/D)

![img.png](img.png)

> 技巧：建树使用无向图，然后建邻接表。
>
> ​	由于树的连通分量为1，并且没有回路，所以不需要visited数组

```java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    static int n;
    static List<Integer>[] g;
    static int[] size;
    static long ans = 0;

    public static void main(String[] args) {
        n = sc.nextInt();
        size = new int[n + 1];
        g = new ArrayList[n + 1];
        Arrays.setAll(g, e -> new ArrayList<>());
        while (sc.hasNext()) {
            int x = sc.nextInt(), y = sc.nextInt();
            g[x].add(y);
            g[y].add(x);
        }
        if ((n & 1) == 1) {
            System.out.println(-1);
        }else{
            dfs(1, 0);
            for (int i = 1; i <= n; i++) {
                if (size[i] == 0) {
                    continue;
                }
                if ((size[i] & 1) == 0) {
                    ans++;
                }
            }
            System.out.println(ans - 1);
        }
        sc.close();
    }

    private static void dfs(int x, int fa) {
        size[x] = 1;
        for (int y : g[x]) {
            if (y != fa) {
                dfs(y, x);
                size[x] += size[y];
            }
        }
    }
}
```
# 树上倍增和LCA(树上祖先)

> LCA问题
>
> 给定树上的任意两点a和b，如何快速查询出a和b的最低公共祖先，常见的有三个方法
>
> 1）树上倍增
>
> 2）tarjan算法+并查集
>
> 3）树链剖分

1483\. 树节点的第 K 个祖先（树上倍增）（链式前向星建图模板）
------------------

给你一棵树，树上有 `n` 个节点，按从 `0` 到 `n-1` 编号。树以父节点数组的形式给出，其中 `parent[i]` 是节点 `i` 的父节点。树的根节点是编号为 `0` 的节点。

树节点的第 _`k`_ 个祖先节点是从该节点到根节点路径上的第 `k` 个节点。

实现 `TreeAncestor` 类：

*   `TreeAncestor（int n， int[] parent）` 对树和父数组中的节点数初始化对象。
*   `getKthAncestor``(int node, int k)` 返回节点 `node` 的第 `k` 个祖先节点。如果不存在这样的祖先节点，返回 `-1` 。

**示例 1：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/06/14/1528_ex1.png)**

**输入：**
\["TreeAncestor","getKthAncestor","getKthAncestor","getKthAncestor"\]
\[\[7,\[-1,0,0,1,1,2,2\]\],\[3,1\],\[5,2\],\[6,3\]\]

**输出：**
\[null,1,0,-1\]

**解释：**
TreeAncestor treeAncestor = new TreeAncestor(7, \[-1, 0, 0, 1, 1, 2, 2\]);

treeAncestor.getKthAncestor(3, 1);  // 返回 1 ，它是 3 的父节点
treeAncestor.getKthAncestor(5, 2);  // 返回 0 ，它是 5 的祖父节点
treeAncestor.getKthAncestor(6, 3);  // 返回 -1 因为不存在满足要求的祖先节点

**提示：**

*   `1 <= k <= n <= 5 * 104`
*   `parent[0] == -1` 表示编号为 `0` 的节点是根节点。
*   对于所有的 `0 < i < n` ，`0 <= parent[i] < n` 总成立
*   `0 <= node < n`
*   至多查询 `5 * 104` 次

[https://leetcode.cn/problems/kth-ancestor-of-a-tree-node/description/](https://leetcode.cn/problems/kth-ancestor-of-a-tree-node/description/)

> 链式前向星建图，方法一比较万能，方法二比较简洁

```java
import java.util.Arrays;

class TreeAncestor {
    private static int MAXN = 50001;
    private static int power;
    private static int cnt;

    // 链式前向星建图
    public static int[] head = new int[MAXN]; // 头结点数组，用于存储每个顶点的第一条边的索引。 [索引，值] = [头边号，点号]

    public static int[] next = new int[MAXN]; // 邻接边表数组，用于存储每条边的下一条边的索引。 [索引，值] = [下一条边号，边号]

    public static int[] to = new int[MAXN]; // 目标节点数组，用于存储每条边的目标节点。 [索引，值] = [边号，去往的点]

    // deep[i] : 节点i在第几层
    private static int[] deep = new int[MAXN];

    // stjump[i][p] : 节点i往上跳2的p次方步，到达的节点编号
    private static int[][] stjump = new int[MAXN][32]; // 针对这个题：这里的32修改为17可以快30ms

    public TreeAncestor(int n, int[] parent) {
        power = 32 - Integer.numberOfLeadingZeros(n);
        cnt = 1; // 初始化为1
        Arrays.fill(head, 0); // 如果head[i] = 0，表示没有边与这个点相连
        for (int i = 1; i < parent.length; i++) { // 这里下标从1开始，因为parent[0] = -1, 直接跳过
            addEdge(parent[i], i);
        }
        dfs(0, 0);
    }

    private static void addEdge(int u, int v) { // u -> v
        next[cnt] = head[u]; // 类似链表的next，这是下一条边的索引
        to[cnt] = v; // 第cnt条边指向节点v
        head[u] = cnt++;// 节点u有第cnt条边
    }

    private static void dfs(int x, int fa) {
        if (x == 0) {
            deep[x] = 1;
        }else{
            deep[x] = deep[fa] + 1;
        }
        stjump[x][0] = fa;
        for (int i = 1; i <= power; i++) {
            stjump[x][i] = stjump[stjump[x][i - 1]][i - 1];
        }
        for (int e = head[x]; e != 0; e = next[e]) {
            dfs(to[e], x);
        }
    }

    public int getKthAncestor(int node, int k) { // 节点node的第k个祖先节点
        if (deep[node] <= k) {
            return -1;
        }
        // s是想要去往的层数
        int s = deep[node] - k;
        for (int p = 32 - Integer.numberOfLeadingZeros(k); p >= 0; p--) { // 这里使用位运算也行
            if (deep[stjump[node][p]] >= s) {
                node = stjump[node][p];
            }
        }
        return node;
    }
}

/**
 * Your TreeAncestor object will be instantiated and called as such:
 * TreeAncestor obj = new TreeAncestor(n, parent);
 * int param_1 = obj.getKthAncestor(node,k);
 */
```



```java
class TreeAncestor {
    private int[][] pa;
    public TreeAncestor(int n, int[] parent) {
        // Integer.numberOfLeadingZeros(n);从左到右数n有多少个连续0
        int m = 32 - Integer.numberOfLeadingZeros(n); // n的二进制长度
        pa = new int[n][m]; // pa[x][i]表示pa的第2^i个祖先节点,pa[x][0]就是父节点,pa[x][1]=pa[pa[x][0]][0]，即爷爷节点。
        for (int i = 0; i < n; i++) {
            pa[i][0] = parent[i];
        }
        for (int i = 0; i < m - 1; i++) {
            for (int x = 0; x < n; x++) {
                int p = pa[x][i];
                pa[x][i + 1] = p < 0 ? -1 : pa[p][i]; // 厉害
            }
        }
    }

    public int getKthAncestor(int node, int k) { // 写法一
        int m = 32 - Integer.numberOfLeadingZeros(k);
        for (int i = 0; i < m; i++) {
            if (((k >> i) & 1) > 0) { // k 的二进制从低到高第 i 位是 1
                node = pa[node][i];
                if (node < 0) {
                    break;
                }
            }
        }
        return node;
    }
}

/**
 * Your TreeAncestor object will be instantiated and called as such:
 * TreeAncestor obj = new TreeAncestor(n, parent);
 * int param_1 = obj.getKthAncestor(node,k);
 */
```

