> 树形 DP
> 	思考方向：
> 	每个节点需要计算的信息，是否只取决于邻居？
> 	如果不能，如何把子树的信息归纳到邻居上？
>
> ​	一般是从自底向上计算的，也就是根据子树返回值来计算父节点的值
> 	也有自顶向下的写法，见后面

#### [【模板】树的直径（相邻元素不同）](https://leetcode.cn/problems/longest-path-with-different-adjacent-characters/description/)

给你一棵 **树**（即一个连通、无向、无环图），根节点是节点 `0` ，这棵树由编号从 `0` 到 `n - 1` 的 `n` 个节点组成。用下标从 **0** 开始、长度为 `n` 的数组 `parent` 来表示这棵树，其中 `parent[i]` 是节点 `i` 的父节点，由于节点 `0` 是根节点，所以 `parent[0] == -1` 。

另给你一个字符串 `s` ，长度也是 `n` ，其中 `s[i]` 表示分配给节点 `i` 的字符。

请你找出路径上任意一对相邻节点都没有分配到相同字符的 **最长路径** ，并返回该路径的长度。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/03/25/testingdrawio.png)

**输入：**parent = \[-1,0,0,1,1,2\], s = "abacbe"
**输出：**3
**解释：**任意一对相邻节点字符都不同的最长路径是：0 -> 1 -> 3 。该路径的长度是 3 ，所以返回 3 。
可以证明不存在满足上述条件且比 3 更长的路径。 

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/03/25/graph2drawio.png)

**输入：**parent = \[-1,0,0,0\], s = "aabc"
**输出：**3
**解释：**任意一对相邻节点字符都不同的最长路径是：2 -> 0 -> 3 。该路径的长度为 3 ，所以返回 3 。

**提示：**

*   `n == parent.length == s.length`
*   `1 <= n <= 105`
*   对所有 `i >= 1` ，`0 <= parent[i] <= n - 1` 均成立
*   `parent[0] == -1`
*   `parent` 表示一棵有效的树
*   `s` 仅由小写英文字母组成

[https://leetcode.cn/problems/longest-path-with-different-adjacent-characters/description/](https://leetcode.cn/problems/longest-path-with-different-adjacent-characters/description/)

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    int ans;
    List<Integer>[] g;
    String s;
    public int longestPath(int[] parent, String s) {
        // 建图
        int n = parent.length;
        this.s = s;
        g = new List[n];
        Arrays.setAll(g, e -> new ArrayList<Integer>());
        for (int i = 1; i < n; i++) {
            g[parent[i]].add(i); // 建单边，建双边也可以
        }
        dfs(0);
        return ans + 1; // 由于本题求的是点的个数，所以答案为最长路径的长度加一。
    }

    private int dfs(int x) {
        int maxLen = 0; // 以当前节点为根的最长路径
        for (int y : g[x]) {
            int len = dfs(y) + 1;
            if (s.charAt(x) != s.charAt(y)) { // 要求相邻节点不同
                ans = Math.max(ans, maxLen + len);
                maxLen = Math.max(maxLen, len);
            }
        }
        return maxLen;
    }
}
```

LCP 34. 二叉树染色
-------------

小扣有一个根结点为 `root` 的二叉树模型，初始所有结点均为白色，可以用蓝色染料给模型结点染色，模型的每个结点有一个 `val` 价值。小扣出于美观考虑，希望最后二叉树上每个蓝色相连部分的结点个数不能超过 `k` 个，求所有染成蓝色的结点价值总和最大是多少？

**示例 1：**

> 输入：`root = [5,2,3,4], k = 2`
>
> 输出：`12`
>
> 解释：`结点 5、3、4 染成蓝色，获得最大的价值 5+3+4=12` ![image.png](https://pic.leetcode-cn.com/1616126267-BqaCRj-image.png)

**示例 2：**

> 输入：`root = [4,1,3,9,null,null,2], k = 2`
>
> 输出：`16`
>
> 解释：结点 4、3、9 染成蓝色，获得最大的价值 4+3+9=16 ![image.png](https://pic.leetcode-cn.com/1616126301-gJbhba-image.png)

**提示：**

*   `1 <= k <= 10`
*   `1 <= val <= 10000`
*   `1 <= 结点数量 <= 10000`

[https://leetcode.cn/problems/er-cha-shu-ran-se-UGC/solutions/713521/c-yi-wei-shu-xing-dp-by-mandaloo-e1ip/](https://leetcode.cn/problems/er-cha-shu-ran-se-UGC/solutions/713521/c-yi-wei-shu-xing-dp-by-mandaloo-e1ip/)

```java
class TreeNode { 
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
class Solution { // 封神之作
    public int maxValue(TreeNode root, int k) {
        int[] dp = dfs(root, k);
        int ans = 0;
        for (int i = 0; i <= k; i++) {
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    private int[] dfs(TreeNode root, int k) {
        int[] dp = new int[k + 1];
        if (root == null) {
            return dp;
        }
        int[] ldp = dfs(root.left, k);
        int[] rdp = dfs(root.right, k);
        int lmax = 0;
        int rmax = 0;
        for (int i = 0; i <= k; i++) {
            lmax = Math.max(lmax, ldp[i]);
            rmax = Math.max(rmax, rdp[i]);
        }
        dp[0] = lmax + rmax;
        for (int i = 1; i <= k; i++) { // 当前节点染色i个, 取左右子节点染色个数和为(i-1)的所有情况的最大值
            for (int j = 0; j < i; j++) {
                dp[i] = Math.max(dp[i], root.val + ldp[j] + rdp[i - 1 - j]);
            }
        }
        return dp;
    }
}
```

1443\. 收集树上所有苹果的最少时间
--------------------

给你一棵有 `n` 个节点的无向树，节点编号为 `0` 到 `n-1` ，它们中有一些节点有苹果。通过树上的一条边，需要花费 1 秒钟。你从 **节点 0** 出发，请你返回最少需要多少秒，可以收集到所有苹果，并回到节点 0 。

无向树的边由 `edges` 给出，其中 `edges[i] = [fromi, toi]` ，表示有一条边连接 `from` 和 `toi` 。除此以外，还有一个布尔数组 `hasApple` ，其中 `hasApple[i] = true` 代表节点 `i` 有一个苹果，否则，节点 `i` 没有苹果。

**示例 1：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/05/10/min_time_collect_apple_1.png)**

**输入：**n = 7, edges = \[\[0,1\],\[0,2\],\[1,4\],\[1,5\],\[2,3\],\[2,6\]\], hasApple = \[false,false,true,false,true,true,false\]
**输出：**8 
**解释：**上图展示了给定的树，其中红色节点表示有苹果。一个能收集到所有苹果的最优方案由绿色箭头表示。

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2020/05/10/min_time_collect_apple_2.png)**

**输入：**n = 7, edges = \[\[0,1\],\[0,2\],\[1,4\],\[1,5\],\[2,3\],\[2,6\]\], hasApple = \[false,false,true,false,false,true,false\]
**输出：**6
**解释：**上图展示了给定的树，其中红色节点表示有苹果。一个能收集到所有苹果的最优方案由绿色箭头表示。

**示例 3：**

**输入：**n = 7, edges = \[\[0,1\],\[0,2\],\[1,4\],\[1,5\],\[2,3\],\[2,6\]\], hasApple = \[false,false,false,false,false,false,false\]
**输出：**0

**提示：**

*   `1 <= n <= 10^5`
*   `edges.length == n - 1`
*   `edges[i].length == 2`
*   `0 <= ai < bi <= n - 1`
*   `hasApple.length == n`

[https://leetcode.cn/problems/minimum-time-to-collect-all-apples-in-a-tree/solutions/](https://leetcode.cn/problems/minimum-time-to-collect-all-apples-in-a-tree/solutions/)

```java
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
```

