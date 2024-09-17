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

