> 思考：如何获取dijkstra的路径？

2092\. 找出知晓秘密的所有专家
------------------

给你一个整数 `n` ，表示有 `n` 个专家从 `0` 到 `n - 1` 编号。另外给你一个下标从 0 开始的二维整数数组 `meetings` ，其中 `meetings[i] = [xi, yi, timei]` 表示专家 `xi` 和专家 `yi` 在时间 `timei` 要开一场会。一个专家可以同时参加 **多场会议** 。最后，给你一个整数 `firstPerson` 。

专家 `0` 有一个 **秘密** ，最初，他在时间 `0` 将这个秘密分享给了专家 `firstPerson` 。接着，这个秘密会在每次有知晓这个秘密的专家参加会议时进行传播。更正式的表达是，每次会议，如果专家 `xi` 在时间 `timei` 时知晓这个秘密，那么他将会与专家 `yi` 分享这个秘密，反之亦然。

秘密共享是 **瞬时发生** 的。也就是说，在同一时间，一个专家不光可以接收到秘密，还能在其他会议上与其他专家分享。

在所有会议都结束之后，返回所有知晓这个秘密的专家列表。你可以按 **任何顺序** 返回答案。

**示例 1：**

**输入：**n = 6, meetings = \[\[1,2,5\],\[2,3,8\],\[1,5,10\]\], firstPerson = 1
**输出：**\[0,1,2,3,5\]
**解释：**
时间 0 ，专家 0 将秘密与专家 1 共享。
时间 5 ，专家 1 将秘密与专家 2 共享。
时间 8 ，专家 2 将秘密与专家 3 共享。
时间 10 ，专家 1 将秘密与专家 5 共享。
因此，在所有会议结束后，专家 0、1、2、3 和 5 都将知晓这个秘密。

**示例 2：**

**输入：**n = 4, meetings = \[\[3,1,3\],\[1,2,2\],\[0,3,3\]\], firstPerson = 3
**输出：**\[0,1,3\]
**解释：**
时间 0 ，专家 0 将秘密与专家 3 共享。
时间 2 ，专家 1 与专家 2 都不知晓这个秘密。
时间 3 ，专家 3 将秘密与专家 0 和专家 1 共享。
因此，在所有会议结束后，专家 0、1 和 3 都将知晓这个秘密。

**示例 3：**

**输入：**n = 5, meetings = \[\[3,4,2\],\[1,2,1\],\[2,3,1\]\], firstPerson = 1
**输出：**\[0,1,2,3,4\]
**解释：**
时间 0 ，专家 0 将秘密与专家 1 共享。
时间 1 ，专家 1 将秘密与专家 2 共享，专家 2 将秘密与专家 3 共享。
注意，专家 2 可以在收到秘密的同一时间分享此秘密。
时间 2 ，专家 3 将秘密与专家 4 共享。
因此，在所有会议结束后，专家 0、1、2、3 和 4 都将知晓这个秘密。

**提示：**

*   `2 <= n <= 105`
*   `1 <= meetings.length <= 105`
*   `meetings[i].length == 3`
*   `0 <= xi, yi <= n - 1`
*   `xi != yi`
*   `1 <= timei <= 105`
*   `1 <= firstPerson <= n - 1`

[https://leetcode.cn/problems/find-all-people-with-secret/description/](https://leetcode.cn/problems/find-all-people-with-secret/description/)

```java
import java.util.*;

class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        int m = meetings.length;
        boolean[] secret = new boolean[n];
        secret[0] = secret[firstPerson] = true;
        Arrays.sort(meetings, (a, b) -> a[2] - b[2]);
        // 如果使用List[] g，我们不知道给多大的空间，所以这里用map
        Queue<Integer> queue = new LinkedList<>();
        int i = 0;
        while (i < m) {
            int j = i;
            while (j + 1 < m && meetings[j][2] == meetings[j + 1][2]) {
                j++;
            }
            HashMap<Integer, List<Integer>> g = new HashMap<>();
            HashSet<Integer> set = new HashSet<>();
            for (int k = i; k <= j; k++) {
                int x = meetings[k][0], y = meetings[k][1];
                g.computeIfAbsent(x, e -> new ArrayList<>()).add(y);
                g.computeIfAbsent(y, e -> new ArrayList<>()).add(x);
                set.add(x);
                set.add(y);
            }
            for (int x : set) {
                if (secret[x]) {
                    queue.offer(x);
                }
            }
            while (!queue.isEmpty()) {
                int poll = queue.poll();
                for (int x : g.get(poll)) {
                    if (!secret[x]) {
                        secret[x] = true;
                        queue.offer(x);
                    }
                }
            }
            i = j + 1;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int j = 0; j < n; j++) {
            if (secret[j]) {
                ans.add(j);
            }
        }
        return ans;
    }
}
```

1311\. 获取你好友已观看的视频
------------------

有 `n` 个人，每个人都有一个  `0` 到 `n-1` 的唯一 _id_ 。

给你数组 `watchedVideos`  和 `friends` ，其中 `watchedVideos[i]`  和 `friends[i]` 分别表示 `id = i` 的人观看过的视频列表和他的好友列表。

Level **1** 的视频包含所有你好友观看过的视频，level **2** 的视频包含所有你好友的好友观看过的视频，以此类推。一般的，Level 为 **k** 的视频包含所有从你出发，最短距离为 **k** 的好友观看过的视频。

给定你的 `id`  和一个 `level` 值，请你找出所有指定 `level` 的视频，并将它们按观看频率升序返回。如果有频率相同的视频，请将它们按字母顺序从小到大排列。

**示例 1：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/01/03/leetcode_friends_1.png)**

**输入：**watchedVideos = \[\["A","B"\],\["C"\],\["B","C"\],\["D"\]\], friends = \[\[1,2\],\[0,3\],\[0,3\],\[1,2\]\], id = 0, level = 1
**输出：**\["B","C"\] 
**解释：**
你的 id 为 0（绿色），你的朋友包括（黄色）：
id 为 1 -> watchedVideos = \["C"\] 
id 为 2 -> watchedVideos = \["B","C"\] 
你朋友观看过视频的频率为：
B -> 1 
C -> 2

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/01/03/leetcode_friends_2.png)**

**输入：**watchedVideos = \[\["A","B"\],\["C"\],\["B","C"\],\["D"\]\], friends = \[\[1,2\],\[0,3\],\[0,3\],\[1,2\]\], id = 0, level = 2
**输出：**\["D"\]
**解释：**
你的 id 为 0（绿色），你朋友的朋友只有一个人，他的 id 为 3（黄色）。

**提示：**

*   `n == watchedVideos.length == friends.length`
*   `2 <= n <= 100`
*   `1 <= watchedVideos[i].length <= 100`
*   `1 <= watchedVideos[i][j].length <= 8`
*   `0 <= friends[i].length < n`
*   `0 <= friends[i][j] < n`
*   `0 <= id < n`
*   `1 <= level < n`
*   如果 `friends[i]` 包含 `j` ，那么 `friends[j]` 包含 `i`

[https://leetcode.cn/problems/get-watched-videos-by-your-friends/description/](https://leetcode.cn/problems/get-watched-videos-by-your-friends/description/)

```java
import java.util.*;

class Solution {
    public List<String> watchedVideosByFriends(List<List<String>> watchedVideos, int[][] friends, int id, int level) {
        int n = friends.length;
        boolean[] visited = new boolean[n];
        visited[id] = true;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(id);
        while (!queue.isEmpty() && level > 0) {
            level--;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int cur = queue.poll();
                for (int f : friends[cur]) {
                    if (!visited[f]) {
                        visited[f] = true;
                        queue.offer(f);
                    }
                }
            }
        }

        HashMap<String, Integer> cnt = new HashMap<>();
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            List<String> list = watchedVideos.get(cur);
            for (String x : list) {
                cnt.put(x, cnt.getOrDefault(x, 0) + 1);
            }
        }

        ArrayList<String> ans = new ArrayList<>(cnt.keySet());
        Collections.sort(ans, (a,b)->{
            int cnt1 = cnt.get(a), cnt2 = cnt.get(b);
            if (cnt1 != cnt2) {
                return cnt1 - cnt2;
            }else{
                return a.compareTo(b); // 字符串顺序排序
            }
        });
        return ans;
    }
}
```

1129\. 颜色交替的最短路径
----------------

给定一个整数 `n`，即有向图中的节点数，其中节点标记为 `0` 到 `n - 1`。图中的每条边为红色或者蓝色，并且可能存在自环或平行边。

给定两个数组 `redEdges` 和 `blueEdges`，其中：

*   `redEdges[i] = [ai, bi]` 表示图中存在一条从节点 `ai` 到节点 `bi` 的红色有向边，
*   `blueEdges[j] = [uj, vj]` 表示图中存在一条从节点 `uj` 到节点 `vj` 的蓝色有向边。

返回长度为 `n` 的数组 `answer`，其中 `answer[X]` 是从节点 `0` 到节点 `X` 的红色边和蓝色边交替出现的最短路径的长度。如果不存在这样的路径，那么 `answer[x] = -1`。

**示例 1：**

**输入：**n = 3, red\_edges = \[\[0,1\],\[1,2\]\], blue\_edges = \[\]
**输出：**\[0,1,-1\]

**示例 2：**

**输入：**n = 3, red\_edges = \[\[0,1\]\], blue\_edges = \[\[2,1\]\]
**输出：**\[0,1,-1\]

**提示：**

*   `1 <= n <= 100`
*   `0 <= redEdges.length, blueEdges.length <= 400`
*   `redEdges[i].length == blueEdges[j].length == 2`
*   `0 <= ai, bi, uj, vj < n`

[https://leetcode.cn/problems/shortest-path-with-alternating-colors/description/](https://leetcode.cn/problems/shortest-path-with-alternating-colors/description/)

```java
import java.util.*;

class Solution {
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        final int RED = 0, BLUE = 1;
        List<Integer>[][] g = new List[n][2];
        for (int i = 0; i < n; i++) {
            g[i][RED] = new ArrayList<Integer>();
            g[i][BLUE] = new ArrayList<Integer>();
        }
        for (int[] edge : redEdges) {
            int x = edge[0], y = edge[1];
            g[x][RED].add(y);
        }
        for (int[] edge : blueEdges){
            int x = edge[0], y = edge[1];
            g[x][BLUE].add(y);
        }
        int[][] dist = new int[n][2];
        for (int i = 1; i < n; i++) { // 从1开始
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, RED});
        queue.offer(new int[]{0, BLUE});
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int node = poll[0], color = poll[1];
            int d = dist[node][color];
            int nextColor = color ^ 1;
            int nextDist = d + 1;
            for (int next : g[node][nextColor]) {
                if (nextDist < dist[next][nextColor]) {
                    dist[next][nextColor] = nextDist;
                    queue.offer(new int[]{next, nextColor});
                }
            }
        }
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int d = Math.min(dist[i][RED], dist[i][BLUE]);
            ans[i] = d == Integer.MAX_VALUE ? -1 : d;
        }
        return ans;
    }
}
```
1298\. 你能从盒子里获得的最大糖果数
---------------------

给你 `n` 个盒子，每个盒子的格式为 `[status, candies, keys, containedBoxes]` ，其中：

*   状态字 `status[i]`：整数，如果 `box[i]` 是开的，那么是 **1** ，否则是 **0** 。
*   糖果数 `candies[i]`: 整数，表示 `box[i]` 中糖果的数目。
*   钥匙 `keys[i]`：数组，表示你打开 `box[i]` 后，可以得到一些盒子的钥匙，每个元素分别为该钥匙对应盒子的下标。
*   内含的盒子 `containedBoxes[i]`：整数，表示放在 `box[i]` 里的盒子所对应的下标。

给你一个 `initialBoxes` 数组，表示你现在得到的盒子，你可以获得里面的糖果，也可以用盒子里的钥匙打开新的盒子，还可以继续探索从这个盒子里找到的其他盒子。

请你按照上述规则，返回可以获得糖果的 **最大数目** 。

**示例 1：**

**输入：**status = \[1,0,1,0\], candies = \[7,5,4,100\], keys = \[\[\],\[\],\[1\],\[\]\], containedBoxes = \[\[1,2\],\[3\],\[\],\[\]\], initialBoxes = \[0\]
**输出：**16
**解释：**
一开始你有盒子 0 。你将获得它里面的 7 个糖果和盒子 1 和 2。
盒子 1 目前状态是关闭的，而且你还没有对应它的钥匙。所以你将会打开盒子 2 ，并得到里面的 4 个糖果和盒子 1 的钥匙。
在盒子 1 中，你会获得 5 个糖果和盒子 3 ，但是你没法获得盒子 3 的钥匙所以盒子 3 会保持关闭状态。
你总共可以获得的糖果数目 = 7 + 4 + 5 = 16 个。

**示例 2：**

**输入：**status = \[1,0,0,0,0,0\], candies = \[1,1,1,1,1,1\], keys = \[\[1,2,3,4,5\],\[\],\[\],\[\],\[\],\[\]\], containedBoxes = \[\[1,2,3,4,5\],\[\],\[\],\[\],\[\],\[\]\], initialBoxes = \[0\]
**输出：**6
**解释：**
你一开始拥有盒子 0 。打开它你可以找到盒子 1,2,3,4,5 和它们对应的钥匙。
打开这些盒子，你将获得所有盒子的糖果，所以总糖果数为 6 个。

**示例 3：**

**输入：**status = \[1,1,1\], candies = \[100,1,100\], keys = \[\[\],\[0,2\],\[\]\], containedBoxes = \[\[\],\[\],\[\]\], initialBoxes = \[1\]
**输出：**1

**示例 4：**

**输入：**status = \[1\], candies = \[100\], keys = \[\[\]\], containedBoxes = \[\[\]\], initialBoxes = \[\]
**输出：**0

**示例 5：**

**输入：**status = \[1,1,1\], candies = \[2,3,2\], keys = \[\[\],\[\],\[\]\], containedBoxes = \[\[\],\[\],\[\]\], initialBoxes = \[2,1,0\]
**输出：**7

**提示：**

*   `1 <= status.length <= 1000`
*   `status.length == candies.length == keys.length == containedBoxes.length == n`
*   `status[i]` 要么是 `0` 要么是 `1` 。
*   `1 <= candies[i] <= 1000`
*   `0 <= keys[i].length <= status.length`
*   `0 <= keys[i][j] < status.length`
*   `keys[i]` 中的值都是互不相同的。
*   `0 <= containedBoxes[i].length <= status.length`
*   `0 <= containedBoxes[i][j] < status.length`
*   `containedBoxes[i]` 中的值都是互不相同的。
*   每个盒子最多被一个盒子包含。
*   `0 <= initialBoxes.length <= status.length`
*   `0 <= initialBoxes[i] < status.length`

[https://leetcode.cn/problems/maximum-candies-you-can-get-from-boxes/description/](https://leetcode.cn/problems/maximum-candies-you-can-get-from-boxes/description/)
```java
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int maxCandies(int[] status, int[] candies, int[][] keys, int[][] containedBoxes, int[] initialBoxes) {
        int n = status.length;
        Queue<Integer> queue = new LinkedList<>();
        HashSet<Integer> keySet = new HashSet<>(); // 钥匙集合
        HashSet<Integer> notKeyBox = new HashSet<>(); // 没有钥匙的盒子的集合
        boolean[] visited = new boolean[n]; // 能打开的盒子集合
        for (int x : initialBoxes) {
            queue.offer(x);
            for (int y : keys[x]) {
                keySet.add(y);
            }
            if (status[x] == 1 || keySet.contains(x)) {
                visited[x] = true;
            }else{
                notKeyBox.add(x);
            }
        }
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            for (int p : containedBoxes[poll]) {
                if(!visited[p]){
                    if (status[p] == 1 || keySet.contains(p)) {
                        visited[p] = true;
                        for (int k : keys[p]) {
                            keySet.add(k);
                        }
                        queue.offer(p);
                    }else{
                        notKeyBox.add(p);
                    }
                }
            }
//            for (int x : notKeyBox) {
//                if (keySet.contains(x)) {
//                    notKeyBox.remove(x);
//                    queue.offer(x);
//                    visited[x] = true;
//                }
//            }
            Iterator<Integer> iterator = notKeyBox.iterator(); // 使用迭代器避免遍历时删除导致的报错
            while (iterator.hasNext()) {
                int x = iterator.next();
                if (keySet.contains(x)) {
                    iterator.remove();
                    queue.offer(x);
                    visited[x] = true;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                ans += candies[i];
            }
        }
        return ans;


    }
}
```
2608\. 图中的最短环(找最短环模板)
-------------

现有一个含 `n` 个顶点的 **双向** 图，每个顶点按从 `0` 到 `n - 1` 标记。图中的边由二维整数数组 `edges` 表示，其中 `edges[i] = [ui, vi]` 表示顶点 `ui` 和 `vi` 之间存在一条边。每对顶点最多通过一条边连接，并且不存在与自身相连的顶点。

返回图中 **最短** 环的长度。如果不存在环，则返回 `-1` 。

**环** 是指以同一节点开始和结束，并且路径中的每条边仅使用一次。

**示例 1：**

![](https://assets.leetcode.com/uploads/2023/01/04/cropped.png)

**输入：**n = 7, edges = \[\[0,1\],\[1,2\],\[2,0\],\[3,4\],\[4,5\],\[5,6\],\[6,3\]\]
**输出：**3
**解释：**长度最小的循环是：0 -> 1 -> 2 -> 0

**示例 2：**

![](https://assets.leetcode.com/uploads/2023/01/04/croppedagin.png)

**输入：**n = 4, edges = \[\[0,1\],\[0,2\]\]
**输出：**\-1
**解释：**图中不存在循环

**提示：**

*   `2 <= n <= 1000`
*   `1 <= edges.length <= 1000`
*   `edges[i].length == 2`
*   `0 <= ui, vi < n`
*   `ui != vi`
*   不存在重复的边

[https://leetcode.cn/problems/shortest-cycle-in-a-graph/description/](https://leetcode.cn/problems/shortest-cycle-in-a-graph/description/)

![b101_t4_cut.png](assets/1680363054-UnoCDM-b101_t4_cut-1717573207375.png) 

```java
import java.util.*;

class Solution {
    List<Integer>[] g;
    int[] dist;
    public int findShortestCycle(int n, int[][] edges) {
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1];
            g[x].add(y);
            g[y].add(x);
        }
        dist = new int[n];
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, bfs(i));
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private int bfs(int start) {
        int ans = Integer.MAX_VALUE;
        Arrays.fill(dist, -1);
        dist[start] = 0;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{start, -1});
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int x = poll[0], fa = poll[1];
            for (int y : g[x]) {
                if (dist[y] < 0) { // 第一次遇到
                    dist[y] = dist[x] + 1;
                    queue.offer(new int[]{y, x});
                } else if (y != fa) { // 第二次遇到
                    ans = Math.min(ans, dist[x] + dist[y] + 1);
                }
            }
        }
        return ans;
    }

}
```

1284\. 转化为全零矩阵的最少反转次数(位运算+bfs)
---------------------

给你一个 `m x n` 的二进制矩阵 `mat`。每一步，你可以选择一个单元格并将它反转（反转表示 `0` 变 `1` ，`1` 变 `0` ）。如果存在和它相邻的单元格，那么这些相邻的单元格也会被反转。相邻的两个单元格共享同一条边。

请你返回将矩阵 `mat` 转化为全零矩阵的_最少反转次数_，如果无法转化为全零矩阵，请返回 `-1` 。

**二进制矩阵** 的每一个格子要么是 `0` 要么是 `1` 。

**全零矩阵** 是所有格子都为 `0` 的矩阵。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2019/12/13/matrix.png)

**输入：**mat = \[\[0,0\],\[0,1\]\]
**输出：**3
**解释：**一个可能的解是反转 (1, 0)，然后 (0, 1) ，最后是 (1, 1) 。

**示例 2：**

**输入：**mat = \[\[0\]\]
**输出：**0
**解释：**给出的矩阵是全零矩阵，所以你不需要改变它。

**示例 3：**

**输入：**mat = \[\[1,0,0\],\[1,0,0\]\]
**输出：**\-1
**解释：**该矩阵无法转变成全零矩阵

**提示：**

*   `m == mat.length`
*   `n == mat[0].length`
*   `1 <= m <= 3`
*   `1 <= n <= 3`
*   `mat[i][j]` 是 0 或 1 。

[https://leetcode.cn/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/description/](https://leetcode.cn/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/description/)

```java
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    static final int TARGET = 0;
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    int m, n;
    public int minFlips(int[][] mat) {
        m = mat.length;
        n = mat[0].length;
        int startState = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                startState += mat[i][j] << getIndex(i, j);
            }
        }
        HashSet<Integer> visited = new HashSet<>();
        visited.add(startState);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(startState);
        int flips = -1;
        while (!queue.isEmpty()) {
            flips++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int state = queue.poll();
                if (startState == TARGET) {
                    return flips;
                }
                for (int j = 0; j < m; j++) {
                    for (int k = 0; k < n; k++) {
                        int adjacentState = getAdjacentState(state, j, k);
                        if (visited.add(adjacentState)) {
                            queue.offer(adjacentState);
                        }
                    }
                }
            }
        }
        return -1;
    }

    public int getAdjacentState(int state, int row, int col) {
        state ^= 1 << getIndex(row, col);
        for (int[] dir : dirs) {
            int newRow = row + dir[0], newCol = col + dir[1];
            if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n) {
                state ^= 1 << getIndex(newRow, newCol);
            }
        }
        return state;
    }

    public int getIndex(int row, int col) {
        return row * n + col;
    }
}
```

882\. 细分图中的可到达节点
----------------

给你一个无向图（**原始图**），图中有 `n` 个节点，编号从 `0` 到 `n - 1` 。你决定将图中的每条边 **细分** 为一条节点链，每条边之间的新节点数各不相同。

图用由边组成的二维数组 `edges` 表示，其中 `edges[i] = [ui, vi, cnti]` 表示原始图中节点 `ui` 和 `vi` 之间存在一条边，`cnti` 是将边 **细分** 后的新节点总数。注意，`cnti == 0` 表示边不可细分。

要 **细分** 边 `[ui, vi]` ，需要将其替换为 `(cnti + 1)` 条新边，和 `cnti` 个新节点。新节点为 `x1`, `x2`, ..., `xcnti` ，新边为 `[ui, x1]`, `[x1, x2]`, `[x2, x3]`, ..., `[xcnti-1, xcnti]`, `[xcnti, vi]` 。

现在得到一个 **新的细分图** ，请你计算从节点 `0` 出发，可以到达多少个节点？如果节点间距离是 `maxMoves` 或更少，则视为 **可以到达** 。

给你原始图和 `maxMoves` ，返回 _新的细分图中从节点 `0` 出发_ **_可到达的节点数_** 。

**示例 1：**

![](https://s3-lc-upload.s3.amazonaws.com/uploads/2018/08/01/origfinal.png)

**输入：**edges = \[\[0,1,10\],\[0,2,1\],\[1,2,2\]\], maxMoves = 6, n = 3
**输出：**13
**解释：**边的细分情况如上图所示。
可以到达的节点已经用黄色标注出来。

**示例 2：**

**输入：**edges = \[\[0,1,4\],\[1,2,6\],\[0,2,8\],\[1,3,1\]\], maxMoves = 10, n = 4
**输出：**23

**示例 3：**

**输入：**edges = \[\[1,2,4\],\[1,4,5\],\[1,3,1\],\[2,3,4\],\[3,4,5\]\], maxMoves = 17, n = 5
**输出：**1
**解释：**节点 0 与图的其余部分没有连通，所以只有节点 0 可以到达。

**提示：**

*   `0 <= edges.length <= min(n * (n - 1) / 2, 104)`
*   `edges[i].length == 3`
*   `0 <= ui < vi < n`
*   图中 **不存在平行边**
*   `0 <= cnti <= 104`
*   `0 <= maxMoves <= 109`
*   `1 <= n <= 3000`

[https://leetcode.cn/problems/reachable-nodes-in-subdivided-graph/description/](https://leetcode.cn/problems/reachable-nodes-in-subdivided-graph/description/)

![lc882-3.png](assets/1712301565-JfITbH-lc882-3.png) 

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

class Solution {
    public int reachableNodes(int[][] edges, int maxMoves, int n) {
        List<int[]>[] g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<int[]>());
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], cnt = edge[2];
            g[x].add(new int[]{y, cnt + 1});
            g[y].add(new int[]{x, cnt + 1});
        }
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE / 2);
        // dijkstra
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{0, 0});
        dist[0] = 0;
        while (!pq.isEmpty()) {
            int[] p = pq.poll();
            int x = p[0], d = p[1];
            if (dist[x] < d) {
                continue;
            }
            for (int[] y : g[x]) {
                int yi = y[0];
                int newDist = d + y[1];
                if (newDist < dist[yi]) {
                    dist[yi] = newDist;
                    pq.offer(new int[]{yi, dist[yi]});
                }
            }
        }
        int ans = 0;
        for (int d : dist) {
            if (d <= maxMoves) {
                ans++;
            }
        }
        for (int[] edge : edges) {
            int x = edge[0], y = edge[1], cnt = edge[2];
            int a = Math.max(maxMoves - dist[x], 0);
            int b = Math.max(maxMoves - dist[y], 0);
            ans += Math.min(cnt, a + b);
        }
        return ans;
    }
}
```

2203\. 得到要求路径的最小带权子图
--------------------

给你一个整数 `n` ，它表示一个 **带权有向** 图的节点数，节点编号为 `0` 到 `n - 1` 。

同时给你一个二维整数数组 `edges` ，其中 `edges[i] = [fromi, toi, weighti]` ，表示从 `fromi` 到 `toi` 有一条边权为 `weighti` 的 **有向** 边。

最后，给你三个 **互不相同** 的整数 `src1` ，`src2` 和 `dest` ，表示图中三个不同的点。

请你从图中选出一个 **边权和最小** 的子图，使得从 `src1` 和 `src2` 出发，在这个子图中，都 **可以** 到达 `dest` 。如果这样的子图不存在，请返回 `-1` 。

**子图** 中的点和边都应该属于原图的一部分。子图的边权和定义为它所包含的所有边的权值之和。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/02/17/example1drawio.png)

**输入：**n = 6, edges = \[\[0,2,2\],\[0,5,6\],\[1,0,3\],\[1,4,5\],\[2,1,1\],\[2,3,3\],\[2,3,4\],\[3,4,2\],\[4,5,1\]\], src1 = 0, src2 = 1, dest = 5
**输出：**9
**解释：**
上图为输入的图。
蓝色边为最优子图之一。
注意，子图 \[\[1,0,3\],\[0,5,6\]\] 也能得到最优解，但无法在满足所有限制的前提下，得到更优解。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/02/17/example2-1drawio.png)

**输入：**n = 3, edges = \[\[0,1,1\],\[2,1,1\]\], src1 = 0, src2 = 1, dest = 2
**输出：**\-1
**解释：**
上图为输入的图。
可以看到，不存在从节点 1 到节点 2 的路径，所以不存在任何子图满足所有限制。

**提示：**

*   `3 <= n <= 105`
*   `0 <= edges.length <= 105`
*   `edges[i].length == 3`
*   `0 <= fromi, toi, src1, src2, dest <= n - 1`
*   `fromi != toi`
*   `src1` ，`src2` 和 `dest` 两两不同。
*   `1 <= weight[i] <= 105`

[https://leetcode.cn/problems/minimum-weighted-subgraph-with-the-required-paths/description/](https://leetcode.cn/problems/minimum-weighted-subgraph-with-the-required-paths/description/)

```java
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
```

2577\. 在网格图中访问一个格子的最少时间
-----------------------

给你一个 `m x n` 的矩阵 `grid` ，每个元素都为 **非负** 整数，其中 `grid[row][col]` 表示可以访问格子 `(row, col)` 的 **最早** 时间。也就是说当你访问格子 `(row, col)` 时，最少已经经过的时间为 `grid[row][col]` 。

你从 **最左上角** 出发，出发时刻为 `0` ，你必须一直移动到上下左右相邻四个格子中的 **任意** 一个格子（即不能停留在格子上）。每次移动都需要花费 1 单位时间。

请你返回 **最早** 到达右下角格子的时间，如果你无法到达右下角的格子，请你返回 `-1` 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2023/02/14/yetgriddrawio-8.png)

**输入：**grid = \[\[0,1,3,2\],\[5,1,2,5\],\[4,3,8,6\]\]
**输出：**7
**解释：**一条可行的路径为：
- 时刻 t = 0 ，我们在格子 (0,0) 。
- 时刻 t = 1 ，我们移动到格子 (0,1) ，可以移动的原因是 grid\[0\]\[1\] <= 1 。
- 时刻 t = 2 ，我们移动到格子 (1,1) ，可以移动的原因是 grid\[1\]\[1\] <= 2 。
- 时刻 t = 3 ，我们移动到格子 (1,2) ，可以移动的原因是 grid\[1\]\[2\] <= 3 。
- 时刻 t = 4 ，我们移动到格子 (1,1) ，可以移动的原因是 grid\[1\]\[1\] <= 4 。
- 时刻 t = 5 ，我们移动到格子 (1,2) ，可以移动的原因是 grid\[1\]\[2\] <= 5 。
- 时刻 t = 6 ，我们移动到格子 (1,3) ，可以移动的原因是 grid\[1\]\[3\] <= 6 。
- 时刻 t = 7 ，我们移动到格子 (2,3) ，可以移动的原因是 grid\[2\]\[3\] <= 7 。
  最终到达时刻为 7 。这是最早可以到达的时间。

**示例 2：**

![](https://assets.leetcode.com/uploads/2023/02/14/yetgriddrawio-9.png)

**输入：**grid = \[\[0,2,4\],\[3,2,1\],\[1,0,4\]\]
**输出：**\-1
**解释：**没法从左上角按题目规定走到右下角。

**提示：**

*   `m == grid.length`
*   `n == grid[i].length`
*   `2 <= m, n <= 1000`
*   `4 <= m * n <= 105`
*   `0 <= grid[i][j] <= 105`
*   `grid[0][0] == 0`

[https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/description/](https://leetcode.cn/problems/minimum-time-to-visit-a-cell-in-a-grid/description/)

```java
import java.util.Arrays;
import java.util.PriorityQueue;

class Solution {
    private static int[][] dirs = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    public int minimumTime(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        if (grid[0][1] > 1 && grid[1][0] > 1) { // 无法「等待」
            return -1;
        }
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // {d,i,j}
        int[][] dist = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE / 2);
        }
        dist[0][0] = 0;
        pq.offer(new int[]{0, 0, 0});
        while (true) { // 可以等待，就一定可以到达终点
            int[] poll = pq.poll();
            int d = poll[0], i = poll[1], j = poll[2];
            if (dist[i][j] < d) {
                continue;
            }
            if (i == m - 1 && j == n - 1) { // 找到终点，此时 d 一定是最短路
                return dist[i][j];
            }
            for (int[] dir : dirs) { // 枚举周围四个格子
                int newI = i + dir[0], newJ = j + dir[1];
                if (newI >= 0 && newI < m && newJ >= 0 && newJ < n) {
                    int newD = Math.max(d + 1, grid[newI][newJ]);
                    newD += (newD - newI - newJ) % 2; // nd 必须和 x+y 同奇偶, 注意：(nd - x - y) % 2就是1或者0
                    if (newD < dist[newI][newJ]) {
                        dist[newI][newJ] = newD; // 更新最短路
                        pq.offer(new int[]{newD, newI, newJ});
                    }
                }

            }
        }
    }
}
```

2699\. 修改图中的边权
--------------

给你一个 `n` 个节点的 **无向带权连通** 图，节点编号为 `0` 到 `n - 1` ，再给你一个整数数组 `edges` ，其中 `edges[i] = [ai, bi, wi]` 表示节点 `ai` 和 `bi` 之间有一条边权为 `wi` 的边。

部分边的边权为 `-1`（`wi = -1`），其他边的边权都为 **正** 数（`wi > 0`）。

你需要将所有边权为 `-1` 的边都修改为范围 `[1, 2 * 109]` 中的 **正整数** ，使得从节点 `source` 到节点 `destination` 的 **最短距离** 为整数 `target` 。如果有 **多种** 修改方案可以使 `source` 和 `destination` 之间的最短距离等于 `target` ，你可以返回任意一种方案。

如果存在使 `source` 到 `destination` 最短距离为 `target` 的方案，请你按任意顺序返回包含所有边的数组（包括未修改边权的边）。如果不存在这样的方案，请你返回一个 **空数组** 。

**注意：**你不能修改一开始边权为正数的边。

**示例 1：**

**![](https://assets.leetcode.com/uploads/2023/04/18/graph.png)**

**输入：**n = 5, edges = \[\[4,1,-1\],\[2,0,-1\],\[0,3,-1\],\[4,3,-1\]\], source = 0, destination = 1, target = 5
**输出：**\[\[4,1,1\],\[2,0,1\],\[0,3,3\],\[4,3,1\]\]
**解释：**上图展示了一个满足题意的修改方案，从 0 到 1 的最短距离为 5 。

**示例 2：**

**![](https://assets.leetcode.com/uploads/2023/04/18/graph-2.png)**

**输入：**n = 3, edges = \[\[0,1,-1\],\[0,2,5\]\], source = 0, destination = 2, target = 6
**输出：**\[\]
**解释：**上图是一开始的图。没有办法通过修改边权为 -1 的边，使得 0 到 2 的最短距离等于 6 ，所以返回一个空数组。

**示例 3：**

**![](https://assets.leetcode.com/uploads/2023/04/19/graph-3.png)**

**输入：**n = 4, edges = \[\[1,0,4\],\[1,2,3\],\[2,3,5\],\[0,3,-1\]\], source = 0, destination = 2, target = 6
**输出：**\[\[1,0,4\],\[1,2,3\],\[2,3,5\],\[0,3,1\]\]
**解释：**上图展示了一个满足题意的修改方案，从 0 到 2 的最短距离为 6 。

**提示：**

*   `1 <= n <= 100`
*   `1 <= edges.length <= n * (n - 1) / 2`
*   `edges[i].length == 3`
*   `0 <= ai, bi < n`
*   `wi = -1` 或者 `1 <= wi <= 107`
*   `ai != bi`
*   `0 <= source, destination < n`
*   `source != destination`
*   `1 <= target <= 109`
*   输入的图是连通图，且没有自环和重边。

[https://leetcode.cn/problems/modify-graph-edge-weights/description/](https://leetcode.cn/problems/modify-graph-edge-weights/description/)

```java

```

