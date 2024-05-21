# §5.1 点、线

> 尽量不要用判断斜率是否相等，可以利用共线向量线性相关 
>
> 因为斜率k会涉及到除数为0的问题。而且除法涉及精度问题，没有乘法好。
>
> 其实就是  斜率式进行移项，从除法变成了乘法 

1232\. 缀点成线
-----------

给定一个数组 `coordinates` ，其中 `coordinates[i] = [x, y]` ， `[x, y]` 表示横坐标为 `x`、纵坐标为 `y` 的点。请你来判断，这些点是否在该坐标系中属于同一条直线上。

**示例 1：**

![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2019/10/19/untitled-diagram-2.jpg)

**输入：**coordinates = \[\[1,2\],\[2,3\],\[3,4\],\[4,5\],\[5,6\],\[6,7\]\]
**输出：**true

**示例 2：**

**![](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2019/10/19/untitled-diagram-1.jpg)**

**输入：**coordinates = \[\[1,1\],\[2,2\],\[3,4\],\[4,5\],\[5,6\],\[7,7\]\]
**输出：**false

**提示：**

*   `2 <= coordinates.length <= 1000`
*   `coordinates[i].length == 2`
*   `-10^4 <= coordinates[i][0], coordinates[i][1] <= 10^4`
*   `coordinates` 中不含重复的点

[https://leetcode.cn/problems/check-if-it-is-a-straight-line/description/](https://leetcode.cn/problems/check-if-it-is-a-straight-line/description/)

```java
class Solution {
    public boolean checkStraightLine(int[][] coordinates) {
        int pre_dx = coordinates[1][0] - coordinates[0][0], pre_dy = coordinates[1][1] - coordinates[0][1];
        for (int i = 1; i < coordinates.length; i++) {
            int dx = coordinates[i][0] - coordinates[i - 1][0], dy = coordinates[i][1] - coordinates[i - 1][1];
            if (pre_dx * dy != pre_dy * dx) {
                return false;
            }
        }
        return true;
    }
}
```

2280\. 表示一个折线图的最少线段数
--------------------

给你一个二维整数数组 `stockPrices` ，其中 `stockPrices[i] = [dayi, pricei]` 表示股票在 `dayi` 的价格为 `pricei` 。**折线图** 是一个二维平面上的若干个点组成的图，横坐标表示日期，纵坐标表示价格，折线图由相邻的点连接而成。比方说下图是一个例子：

![](https://assets.leetcode.com/uploads/2022/03/30/1920px-pushkin_population_historysvg.png)

请你返回要表示一个折线图所需要的 **最少线段数** 。

**示例 1：**

![](https://assets.leetcode.com/uploads/2022/03/30/ex0.png)

**输入：**stockPrices = \[\[1,7\],\[2,6\],\[3,5\],\[4,4\],\[5,4\],\[6,3\],\[7,2\],\[8,1\]\]
**输出：**3
**解释：**
上图为输入对应的图，横坐标表示日期，纵坐标表示价格。
以下 3 个线段可以表示折线图：
- 线段 1 （红色）从 (1,7) 到 (4,4) ，经过 (1,7) ，(2,6) ，(3,5) 和 (4,4) 。
- 线段 2 （蓝色）从 (4,4) 到 (5,4) 。
- 线段 3 （绿色）从 (5,4) 到 (8,1) ，经过 (5,4) ，(6,3) ，(7,2) 和 (8,1) 。
  可以证明，无法用少于 3 条线段表示这个折线图。

**示例 2：**

![](https://assets.leetcode.com/uploads/2022/03/30/ex1.png)

**输入：**stockPrices = \[\[3,4\],\[1,2\],\[7,8\],\[2,3\]\]
**输出：**1
**解释：**
如上图所示，折线图可以用一条线段表示。

**提示：**

*   `1 <= stockPrices.length <= 105`
*   `stockPrices[i].length == 2`
*   `1 <= dayi, pricei <= 109`
*   所有 `dayi` **互不相同** 。

[https://leetcode.cn/problems/minimum-lines-to-represent-a-line-chart/description/](https://leetcode.cn/problems/minimum-lines-to-represent-a-line-chart/description/)

```java
import java.util.Arrays;

class Solution {
    public int minimumLines(int[][] stockPrices) {
        int n = stockPrices.length;
        Arrays.sort(stockPrices, (a, b) -> a[0] - b[0]);
        int ans = 0;
        int pre_dx = 0, pre_dy = 1; // 可以假定第一天之前的斜率为 1/0 ，从而简化判断逻辑。
        for (int i = 1; i < n; i++) {
            int dx = stockPrices[i][0] - stockPrices[i - 1][0], dy = stockPrices[i][1] - stockPrices[i - 1][1];
            if (pre_dx * dy != pre_dy * dx) {
                ans++;
                pre_dx = dx;
                pre_dy = dy;
            }
        }
        return ans;
    }
}
```

