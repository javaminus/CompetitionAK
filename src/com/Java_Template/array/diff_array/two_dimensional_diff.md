![img.png](img.png)
2536\. 子矩阵元素加 1(模板)
---------------

给你一个正整数 `n` ，表示最初有一个 `n x n` 、下标从 **0** 开始的整数矩阵 `mat` ，矩阵中填满了 0 。

另给你一个二维整数数组 `query` 。针对每个查询 `query[i] = [row1i, col1i, row2i, col2i]` ，请你执行下述操作：

*   找出 **左上角** 为 `(row1i, col1i)` 且 **右下角** 为 `(row2i, col2i)` 的子矩阵，将子矩阵中的 **每个元素** 加 `1` 。也就是给所有满足 `row1i <= x <= row2i` 和 `col1i <= y <= col2i` 的 `mat[x][y]` 加 `1` 。

返回执行完所有操作后得到的矩阵 `mat` 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/11/24/p2example11.png)

**输入：**n = 3, queries = \[\[1,1,2,2\],\[0,0,1,1\]\]
**输出：**\[\[1,1,0\],\[1,2,1\],\[0,1,1\]\]
**解释：**上图所展示的分别是：初始矩阵、执行完第一个操作后的矩阵、执行完第二个操作后的矩阵。
- 第一个操作：将左上角为 (1, 1) 且右下角为 (2, 2) 的子矩阵中的每个元素加 1 。
- 第二个操作：将左上角为 (0, 0) 且右下角为 (1, 1) 的子矩阵中的每个元素加 1 。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/11/24/p2example22.png)

**输入：**n = 2, queries = \[\[0,0,1,1\]\]
**输出：**\[\[1,1\],\[1,1\]\]
**解释：**上图所展示的分别是：初始矩阵、执行完第一个操作后的矩阵。
- 第一个操作：将矩阵中的每个元素加 1 。

**提示：**

*   `1 <= n <= 500`
*   `1 <= queries.length <= 104`
*   `0 <= row1i <= row2i < n`
*   `0 <= col1i <= col2i < n`

[https://leetcode.cn/problems/increment-submatrices-by-one/description/](https://leetcode.cn/problems/increment-submatrices-by-one/description/)

```java
class Solution {
    public int[][] rangeAddQueries(int n, int[][] queries) { // 二维差分模板
        int[][] diff = new int[n + 1][n + 1]; // diff是差分前缀和，表示（0，0）到（n,n）的和，这里是[0，n]闭区间
        int[][] ans = new int[n][n];
        for (int[] q : queries) {
            int x1 = q[0], y1 = q[1], x2 = q[2], y2 = q[3];
            diff[x1][y1]++;
            diff[x1][y2 + 1]--;
            diff[x2 + 1][y1]--;
            diff[x2 + 1][y2 + 1]++;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = diff[i][j];  // 这里主要是初始为0，不然就是ans[i][j]+=diff[i][j]
                if (i != 0) {
                    ans[i][j] += ans[i - 1][j];
                }
                if (j != 0) {
                    ans[i][j] += ans[i][j - 1];
                }
                if (i != 0 && j != 0) {
                    ans[i][j] -= ans[i - 1][j - 1];
                }
            }
        }
        return ans;
    }
}
```

850\. 矩形面积 II(扫描线模板)
-------------

给你一个轴对齐的二维数组 `rectangles` 。 对于 `rectangle[i] = [x1, y1, x2, y2]`，其中（x1，y1）是矩形 `i` 左下角的坐标， `(xi1, yi1)` 是该矩形 **左下角** 的坐标， `(xi2, yi2)` 是该矩形 **右上角** 的坐标。

计算平面中所有 `rectangles` 所覆盖的 **总面积** 。任何被两个或多个矩形覆盖的区域应只计算 **一次** 。

返回 _**总面积**_ 。因为答案可能太大，返回 `109 + 7` 的 **模** 。

**示例 1：**

![](https://s3-lc-upload.s3.amazonaws.com/uploads/2018/06/06/rectangle_area_ii_pic.png)

**输入：**rectangles = \[\[0,0,2,2\],\[1,0,2,3\],\[1,0,3,1\]\]
**输出：**6
**解释：**如图所示，三个矩形覆盖了总面积为 6 的区域。
从(1,1)到(2,2)，绿色矩形和红色矩形重叠。
从(1,0)到(2,3)，三个矩形都重叠。

**示例 2：**

**输入：**rectangles = \[\[0,0,1000000000,1000000000\]\]
**输出：**49
**解释：**答案是 1018 对 (109 + 7) 取模的结果， 即 49 。

**提示：**

*   `1 <= rectangles.length <= 200`
*   `rectanges[i].length = 4`
*   `0 <= xi1, yi1, xi2, yi2 <= 109`

[https://leetcode.cn/problems/rectangle-area-ii/description/](https://leetcode.cn/problems/rectangle-area-ii/description/)

```java
import java.util.ArrayList;
import java.util.Collections;

class Solution {
    private static int Mod = (int) 1e9 + 7;
    public int rectangleArea(int[][] rectangles) { // 扫描线
        // 先把所有的线按x排序
        ArrayList<Integer> list = new ArrayList<>();
        for (int[] rec : rectangles) {
            list.add(rec[0]);
            list.add(rec[2]);
        }
        Collections.sort(list);
        long ans = 0;
        for (int i = 1; i < list.size(); i++) {
            int a = list.get(i - 1), b = list.get(i), len = b - a;
            if (len == 0) {
                continue;
            }
            ArrayList<int[]> lines = new ArrayList<>();
            for (int[] rec : rectangles) {
                if (rec[0] <= a && rec[2] >= b) {
                    lines.add(new int[]{rec[1], rec[3]});
                }
            }
            Collections.sort(lines, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
            long sum = 0, left = -1, right = -1;
            for (int[] line : lines) {
                if (line[0] >= right) {
                    sum += right - left;
                    left = line[0];
                    right = line[1];
                } else if (line[1] > right) {
                    right = line[1];
                }
            }
            sum += right - left;
            ans = (ans + sum * len) % Mod;
        }
        return (int) ans;
    }
}
```

2132\. 用邮票贴满网格图
---------------

给你一个 `m x n` 的二进制矩阵 `grid` ，每个格子要么为 `0` （空）要么为 `1` （被占据）。

给你邮票的尺寸为 `stampHeight x stampWidth` 。我们想将邮票贴进二进制矩阵中，且满足以下 **限制** 和 **要求** ：

1.  覆盖所有 **空** 格子。
2.  不覆盖任何 **被占据** 的格子。
3.  我们可以放入任意数目的邮票。
4.  邮票可以相互有 **重叠** 部分。
5.  邮票不允许 **旋转** 。
6.  邮票必须完全在矩阵 **内** 。

如果在满足上述要求的前提下，可以放入邮票，请返回 `true` ，否则返回 `false` 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/11/03/ex1.png)

**输入：**grid = \[\[1,0,0,0\],\[1,0,0,0\],\[1,0,0,0\],\[1,0,0,0\],\[1,0,0,0\]\], stampHeight = 4, stampWidth = 3
**输出：**true
**解释：**我们放入两个有重叠部分的邮票（图中标号为 1 和 2），它们能覆盖所有与空格子。

**示例 2：**

![](https://assets.leetcode.com/uploads/2021/11/03/ex2.png)

**输入：**grid = \[\[1,0,0,0\],\[0,1,0,0\],\[0,0,1,0\],\[0,0,0,1\]\], stampHeight = 2, stampWidth = 2 
**输出：**false 
**解释：**没办法放入邮票覆盖所有的空格子，且邮票不超出网格图以外。

**提示：**

*   `m == grid.length`
*   `n == grid[r].length`
*   `1 <= m, n <= 105`
*   `1 <= m * n <= 2 * 105`
*   `grid[r][c]` 要么是 `0` ，要么是 `1` 。
*   `1 <= stampHeight, stampWidth <= 105`

[https://leetcode.cn/problems/stamping-the-grid/solutions/1199642/wu-nao-zuo-fa-er-wei-qian-zhui-he-er-wei-zwiu/](https://leetcode.cn/problems/stamping-the-grid/solutions/1199642/wu-nao-zuo-fa-er-wei-qian-zhui-he-er-wei-zwiu/)

```java
// 1.由于邮票可以互相重叠，贪心地想，能放邮票就放邮票。
// 2.遍历所有能放邮票的位置去放邮票。注意邮票不能覆盖被占据的格子，也不能出界。
// 3.放邮票的同时，记录每个空格子被多少张邮票覆盖。如果存在一个空格子没被邮票覆盖，则返回 false，否则返回 true
```

