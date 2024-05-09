732\. 我的日程安排表 III（动态开点线段树+懒标记）
-----------------

当 `k` 个日程安排有一些时间上的交叉时（例如 `k` 个日程安排都在同一时间内），就会产生 `k` 次预订。

给你一些日程安排 `[start, end)` ，请你在每个日程安排添加后，返回一个整数 `k` ，表示所有先前日程安排会产生的最大 `k` 次预订。

实现一个 `MyCalendarThree` 类来存放你的日程安排，你可以一直添加新的日程安排。

*   `MyCalendarThree()` 初始化对象。
*   `int book(int start, int end)` 返回一个整数 `k` ，表示日历中存在的 `k` 次预订的最大值。

**示例：**

**输入：**
\["MyCalendarThree", "book", "book", "book", "book", "book", "book"\]
\[\[\], \[10, 20\], \[50, 60\], \[10, 40\], \[5, 15\], \[5, 10\], \[25, 55\]\]
**输出：**
\[null, 1, 1, 2, 3, 3, 3\]

**解释：**
MyCalendarThree myCalendarThree = new MyCalendarThree();
myCalendarThree.book(10, 20); // 返回 1 ，第一个日程安排可以预订并且不存在相交，所以最大 k 次预订是 1 次预订。
myCalendarThree.book(50, 60); // 返回 1 ，第二个日程安排可以预订并且不存在相交，所以最大 k 次预订是 1 次预订。
myCalendarThree.book(10, 40); // 返回 2 ，第三个日程安排 \[10, 40) 与第一个日程安排相交，所以最大 k 次预订是 2 次预订。
myCalendarThree.book(5, 15); // 返回 3 ，剩下的日程安排的最大 k 次预订是 3 次预订。
myCalendarThree.book(5, 10); // 返回 3
myCalendarThree.book(25, 55); // 返回 3

**提示：**

*   `0 <= start < end <= 109`
*   每个测试用例，调用 `book` 函数最多不超过 `400`次

[https://leetcode.cn/problems/my-calendar-iii/description/](https://leetcode.cn/problems/my-calendar-iii/description/)
```java
class MyCalendarThree {
    int N = (int) 1e9; // 设定上限为1e9
    static class Node {
        Node leftNode, rightNode; // 代表当前节点的左右子节点leftSon,rightSon
        int val, add; // val表示节点的预订次数，add表示懒惰传播时要加到子节点的值
    }
    Node root = new Node(); // 线段树的根节点

    // 更新线段树的方法
    void update(Node node, int leftChild, int rightChild, int left, int right, int delta) {
        if (left <= leftChild && rightChild <= right) {
            node.val += delta; // 更新当前节点的值
            node.add += delta; // 标识当前节点需要懒惰传播的值
            return;
        }
        pushdown(node); // 把当前节点的更新值传播到子节点
        int mid = leftChild + (rightChild - leftChild) / 2; // 计算中间点，无符号右移一位相当于除以2
        // 递归向下更新
        if (left <= mid) update(node.leftNode, leftChild, mid, left, right, delta);
        if (right > mid) update(node.rightNode, mid + 1, rightChild, left, right, delta);
        pushup(node); // 更新完成后，维护当前节点的值
    }

    // 查询线段树的方法
    int query(Node node, int leftChild, int rightChild, int left, int right) {
        if (leftChild >= left && rightChild <= right) {
            return node.val; // 如果当前节点完全覆盖查询区间，直接返回节点值
        }
        pushdown(node); // 先下推延迟标记
        int mid = leftChild + (rightChild - leftChild) / 2, ans = 0; // 初始化答案为0
        // 查询左右子树，并更新答案
        if (left <= mid) ans = query(node.leftNode, leftChild, mid, left, right);
        if (right > mid) ans = Math.max(query(node.rightNode, mid + 1, rightChild, left, right), ans);
        return ans;
    }

    // 下推延迟更新的方法
    void pushdown(Node node) {
        if (node.leftNode == null) {
            node.leftNode = new Node(); // 创建左子节点
        }
        if (node.rightNode == null) {
            node.rightNode = new Node(); // 创建右子节点
        }
        // 如果有延迟更新，则更新子节点
        if (node.add > 0) {
            // 这里是否可以改成 if (node.add != 0)
            int add = node.add;
            node.leftNode.add += add;
            node.rightNode.add += add;
            node.leftNode.val += add;
            node.rightNode.val += add;
            node.add = 0; // 清除当前节点的延迟更新标记    
        }
    }

    // 更新当前节点值的方法
    void pushup(Node node) {
        node.val = Math.max(node.leftNode.val, node.rightNode.val); // 取最大值更新当前节点
    }

    // 构造方法
    public MyCalendarThree() {
    }

    // 预订时间的方法
    public int book(int start, int end) {
        update(root, 0, N, start + 1, end, 1); // 更新线段树
        return query(root, 0, N, 0, N); // 查询最大预订数
    }
}
```

699\. 掉落的方块
-----------

在二维平面上的 x 轴上，放置着一些方块。

给你一个二维整数数组 `positions` ，其中 `positions[i] = [lefti, sideLengthi]` 表示：第 `i` 个方块边长为 `sideLengthi` ，其左侧边与 x 轴上坐标点 `lefti` 对齐。

每个方块都从一个比目前所有的落地方块更高的高度掉落而下。方块沿 y 轴负方向下落，直到着陆到 **另一个正方形的顶边** 或者是 **x 轴上** 。一个方块仅仅是擦过另一个方块的左侧边或右侧边不算着陆。一旦着陆，它就会固定在原地，无法移动。

在每个方块掉落后，你必须记录目前所有已经落稳的 **方块堆叠的最高高度** 。

返回一个整数数组 `ans` ，其中 `ans[i]` 表示在第 `i` 块方块掉落后堆叠的最高高度。

**示例 1：**

![](https://assets.leetcode.com/uploads/2021/04/28/fallingsq1-plane.jpg)

**输入：**positions = \[\[1,2\],\[2,3\],\[6,1\]\]
**输出：**\[2,5,5\]
**解释：**
第 1 个方块掉落后，最高的堆叠由方块 1 组成，堆叠的最高高度为 2 。
第 2 个方块掉落后，最高的堆叠由方块 1 和 2 组成，堆叠的最高高度为 5 。
第 3 个方块掉落后，最高的堆叠仍然由方块 1 和 2 组成，堆叠的最高高度为 5 。
因此，返回 \[2, 5, 5\] 作为答案。

**示例 2：**

**输入：**positions = \[\[100,100\],\[200,100\]\]
**输出：**\[100,100\]
**解释：**
第 1 个方块掉落后，最高的堆叠由方块 1 组成，堆叠的最高高度为 100 。
第 2 个方块掉落后，最高的堆叠可以由方块 1 组成也可以由方块 2 组成，堆叠的最高高度为 100 。
因此，返回 \[100, 100\] 作为答案。
注意，方块 2 擦过方块 1 的右侧边，但不会算作在方块 1 上着陆。

**提示：**

*   `1 <= positions.length <= 1000`
*   `1 <= lefti <= 108`
*   `1 <= sideLengthi <= 106`

[https://leetcode.cn/problems/falling-squares/](https://leetcode.cn/problems/falling-squares/)

```java
import java.util.ArrayList;
import java.util.List;

class Solution {
    int N = (int) 1e9; // 设定上限为1e9
    static class Node {
        Node leftNode, rightNode; // 代表当前节点的左右子节点leftSon,rightSon
        int val, add = -1; // val表示节点的预订次数，add表示懒惰传播时要加到子节点的值
    }
    Node root = new Node(); // 线段树的根节点

    // 更新线段树的方法
    void update(Node node, int leftChild, int rightChild, int left, int right, int delta) {
        if (left <= leftChild && rightChild <= right) {
            node.val = delta; // 更新当前节点的值
            node.add = delta; // 标识当前节点需要懒惰传播的值
            return;
        }
        pushdown(node); // 把当前节点的更新值传播到子节点
        int mid = leftChild + (rightChild - leftChild) / 2; // 计算中间点，无符号右移一位相当于除以2
        // 递归向下更新
        if (left <= mid) update(node.leftNode, leftChild, mid, left, right, delta);
        if (right > mid) update(node.rightNode, mid + 1, rightChild, left, right, delta);
        pushup(node); // 更新完成后，维护当前节点的值
    }

    // 查询线段树的方法
    int query(Node node, int leftChild, int rightChild, int left, int right) {
        if (leftChild >= left && rightChild <= right) {
            return node.val; // 如果当前节点完全覆盖查询区间，直接返回节点值
        }
        pushdown(node); // 先下推延迟标记
        int mid = leftChild + (rightChild - leftChild) / 2, ans = 0; // 初始化答案为0
        // 查询左右子树，并更新答案
        if (left <= mid) ans = query(node.leftNode, leftChild, mid, left, right);
        if (right > mid) ans = Math.max(query(node.rightNode, mid + 1, rightChild, left, right), ans);
        return ans;
    }

    // 下推延迟更新的方法
    void pushdown(Node node) {
        if (node.leftNode == null) {
            node.leftNode = new Node(); // 创建左子节点
        }
        if (node.rightNode == null) {
            node.rightNode = new Node(); // 创建右子节点
        }
        // 如果有延迟更新，则更新子节点
        if (node.add != -1) { // 不可能为负数
            // 这里是否可以改成 if (node.add != 0)
            int add = node.add;
            node.leftNode.add = add;
            node.rightNode.add = add;
            node.leftNode.val = add;
            node.rightNode.val = add;
            node.add = -1; // 清除当前节点的延迟更新标记
        }
    }

    // 更新当前节点值的方法
    void pushup(Node node) {
        node.val = Math.max(node.leftNode.val, node.rightNode.val); // 取最大值更新当前节点
    }

    public List<Integer> fallingSquares(int[][] positions) { // note 减少一次懒加载
        ArrayList<Integer> ans = new ArrayList<>();
        int maxHeight = 0; // 可以用来记录当前的最大高度
        for (int[] p : positions) {
            int left = p[0], right = left + p[1] - 1;
            int mx = query(root, 0, N, left, right); // 查找这个区间的最大值
            update(root, 0, N, left, right, mx + p[1]); // 修改这个区间所有值为0，这里我修改了模板，delta直接赋值给节点，不是累加上去
            maxHeight = Math.max(maxHeight, mx + p[1]);
            ans.add(maxHeight);
        }
        return ans;
    }
}
```

715\. Range 模块（非常特殊的树变型）
--------------

Range模块是跟踪数字范围的模块。设计一个数据结构来跟踪表示为 **半开区间** 的范围并查询它们。

**半开区间** `[left, right)` 表示所有 `left <= x < right` 的实数 `x` 。

实现 `RangeModule` 类:

*   `RangeModule()` 初始化数据结构的对象。
*   `void addRange(int left, int right)` 添加 **半开区间** `[left, right)`，跟踪该区间中的每个实数。添加与当前跟踪的数字部分重叠的区间时，应当添加在区间 `[left, right)` 中尚未跟踪的任何数字到该区间中。
*   `boolean queryRange(int left, int right)` 只有在当前正在跟踪区间 `[left, right)` 中的每一个实数时，才返回 `true` ，否则返回 `false` 。
*   `void removeRange(int left, int right)` 停止跟踪 **半开区间** `[left, right)` 中当前正在跟踪的每个实数。

**示例 1：**

**输入**
\["RangeModule", "addRange", "removeRange", "queryRange", "queryRange", "queryRange"\]
\[\[\], \[10, 20\], \[14, 16\], \[10, 14\], \[13, 15\], \[16, 17\]\]
**输出**
\[null, null, null, true, false, true\]

**解释**
RangeModule rangeModule = new RangeModule();
rangeModule.addRange(10, 20);
rangeModule.removeRange(14, 16);
rangeModule.queryRange(10, 14); 返回 true （区间 \[10, 14) 中的每个数都正在被跟踪）
rangeModule.queryRange(13, 15); 返回 false（未跟踪区间 \[13, 15) 中像 14, 14.03, 14.17 这样的数字）
rangeModule.queryRange(16, 17); 返回 true （尽管执行了删除操作，区间 \[16, 17) 中的数字 16 仍然会被跟踪）

**提示：**

*   `1 <= left < right <= 109`
*   在单个测试用例中，对 `addRange` 、  `queryRange` 和 `removeRange` 的调用总数不超过 `104` 次

[https://leetcode.cn/problems/range-module/description/](https://leetcode.cn/problems/range-module/description/)

```java
class RangeModule {
    int N = (int) 1e9 + 7;

    class Node {
        Node leftNode, rightNode;
        int val, add;
    }

    Node root = new Node();

    int query(Node node, int leftChild, int rightChild, int left, int right) {
        // int len = right - left + 1;
        int len = rightChild - leftChild + 1;
        if (left <= leftChild && right >= rightChild) {
            return node.val;
        }
        pushdown(node, len);
        int mid = leftChild + (rightChild - leftChild) / 2, ans = 0;
        if (left <= mid) {
            ans =  query(node.leftNode, leftChild, mid, left, right);
        }
        if (right > mid) {
            ans += query(node.rightNode, mid + 1, rightChild, left, right);
        }
        return ans;
    }

    void update(Node node, int leftChild, int rightChild, int left, int right, int delta) {
        // int len = right - left + 1;
        int len = rightChild - leftChild + 1;
        if (left <= leftChild && right >= rightChild) {
            node.val = delta == 1 ? len : 0;
            node.add = delta;
            return;
        }
        pushdown(node, len);
        int mid = leftChild + (rightChild - leftChild) / 2;
        if (left <= mid) {
            update(node.leftNode, leftChild, mid, left, right, delta);
        }
        if (right > mid) {
            update(node.rightNode, mid + 1, rightChild, left, right, delta);
        }
        pushup(node);
    }

    void pushdown(Node node, int len) {
        if (node.leftNode == null) {
            node.leftNode = new Node();
        }
        if (node.rightNode == null) {
            node.rightNode = new Node();
        }
        int add = node.add;
        if (add == 0) {
            return;
        }
        if (add == -1) {
            node.leftNode.val = node.rightNode.val = 0;
        }else{
            node.leftNode.val = len - len / 2; // 如果为奇数，左区间 = 右区间 + 1
            node.rightNode.val = len / 2;
        }
        node.leftNode.add = node.rightNode.add = add;
        node.add = 0;
    }

    void pushup(Node node) {
        node.val = node.leftNode.val + node.rightNode.val;
    }
    public RangeModule() {

    }

    public void addRange(int left, int right) {
        update(root, 0, N, left, right - 1, 1);
    }

    public boolean queryRange(int left, int right) {
        return query(root, 0, N, left, right - 1) == right - left;
    }

    public void removeRange(int left, int right) {
        update(root, 0, N, left, right - 1, -1);
    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
```

2276\. 统计区间中的整数数目
-----------------

给你区间的 **空** 集，请你设计并实现满足要求的数据结构：

*   **新增：**添加一个区间到这个区间集合中。
*   **统计：**计算出现在 **至少一个** 区间中的整数个数。

实现 `CountIntervals` 类：

*   `CountIntervals()` 使用区间的空集初始化对象
*   `void add(int left, int right)` 添加区间 `[left, right]` 到区间集合之中。
*   `int count()` 返回出现在 **至少一个** 区间中的整数个数。

**注意：**区间 `[left, right]` 表示满足 `left <= x <= right` 的所有整数 `x` 。

**示例 1：**

**输入**
\["CountIntervals", "add", "add", "count", "add", "count"\]
\[\[\], \[2, 3\], \[7, 10\], \[\], \[5, 8\], \[\]\]
**输出**
\[null, null, null, 6, null, 8\]

**解释**
CountIntervals countIntervals = new CountIntervals(); // 用一个区间空集初始化对象
countIntervals.add(2, 3);  // 将 \[2, 3\] 添加到区间集合中
countIntervals.add(7, 10); // 将 \[7, 10\] 添加到区间集合中
countIntervals.count();    // 返回 6
                           // 整数 2 和 3 出现在区间 \[2, 3\] 中
                           // 整数 7、8、9、10 出现在区间 \[7, 10\] 中
countIntervals.add(5, 8);  // 将 \[5, 8\] 添加到区间集合中
countIntervals.count();    // 返回 8
                           // 整数 2 和 3 出现在区间 \[2, 3\] 中
                           // 整数 5 和 6 出现在区间 \[5, 8\] 中
                           // 整数 7 和 8 出现在区间 \[5, 8\] 和区间 \[7, 10\] 中
                           // 整数 9 和 10 出现在区间 \[7, 10\] 中

**提示：**

*   `1 <= left <= right <= 109`
*   最多调用  `add` 和 `count` 方法 **总计** `105` 次
*   调用 `count` 方法至少一次

[https://leetcode.cn/problems/count-integers-in-intervals/submissions/530539526/](https://leetcode.cn/problems/count-integers-in-intervals/submissions/530539526/)

```java
class CountIntervals {

    int N = (int) 1e9 + 7;

    class Node {
        Node leftNode, rightNode;
        int val, add;
    }

    Node root = new Node();

    int query(Node node, int leftChild, int rightChild, int left, int right) {
        // int len = right - left + 1;
        int len = rightChild - leftChild + 1;
        if (left <= leftChild && right >= rightChild) {
            return node.val;
        }
        pushdown(node, len);
        int mid = leftChild + (rightChild - leftChild) / 2, ans = 0;
        if (left <= mid) {
            ans =  query(node.leftNode, leftChild, mid, left, right);
        }
        if (right > mid) {
            ans += query(node.rightNode, mid + 1, rightChild, left, right);
        }
        return ans;
    }

    void update(Node node, int leftChild, int rightChild, int left, int right, int delta) {
        // int len = right - left + 1;
        int len = rightChild - leftChild + 1;
        if (left <= leftChild && right >= rightChild) {
            node.val = delta == 1 ? len : 0;
            node.add = delta;
            return;
        }
        pushdown(node, len);
        int mid = leftChild + (rightChild - leftChild) / 2;
        if (left <= mid) {
            update(node.leftNode, leftChild, mid, left, right, delta);
        }
        if (right > mid) {
            update(node.rightNode, mid + 1, rightChild, left, right, delta);
        }
        pushup(node);
    }

    void pushdown(Node node, int len) {
        if (node.leftNode == null) {
            node.leftNode = new Node();
        }
        if (node.rightNode == null) {
            node.rightNode = new Node();
        }
        int add = node.add;
        if (add == 0) {
            return;
        }
        if (add == -1) {
            node.leftNode.val = node.rightNode.val = 0;
        }else{
            node.leftNode.val = len - len / 2; // 如果为奇数，左区间 = 右区间 + 1
            node.rightNode.val = len / 2;
        }
        node.leftNode.add = node.rightNode.add = add;
        node.add = 0;
    }

    void pushup(Node node) {
        node.val = node.leftNode.val + node.rightNode.val;
    }


    public CountIntervals() {

    }

    public void add(int left, int right) {
        update(root, 0, N, left, right, 1);
    }

    public int count() {
        return query(root, 0, N, 0, N);
    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
```

2770\. 达到末尾下标所需的最大跳跃次数
----------------------

给你一个下标从 **0** 开始、由 `n` 个整数组成的数组 `nums` 和一个整数 `target` 。

你的初始位置在下标 `0` 。在一步操作中，你可以从下标 `i` 跳跃到任意满足下述条件的下标 `j` ：

*   `0 <= i < j < n`
*   `-target <= nums[j] - nums[i] <= target`

返回到达下标 `n - 1` 处所需的 **最大跳跃次数** 。

如果无法到达下标 `n - 1` ，返回 `-1` 。

**示例 1：**

**输入：**nums = \[1,3,6,4,1,2\], target = 2
**输出：**3
**解释：**要想以最大跳跃次数从下标 0 到下标 n - 1 ，可以按下述跳跃序列执行操作：
- 从下标 0 跳跃到下标 1 。 
- 从下标 1 跳跃到下标 3 。 
- 从下标 3 跳跃到下标 5 。 
  可以证明，从 0 到 n - 1 的所有方案中，不存在比 3 步更长的跳跃序列。因此，答案是 3 。 

**示例 2：**

**输入：**nums = \[1,3,6,4,1,2\], target = 3
**输出：**5
**解释：**要想以最大跳跃次数从下标 0 到下标 n - 1 ，可以按下述跳跃序列执行操作：
- 从下标 0 跳跃到下标 1 。 
- 从下标 1 跳跃到下标 2 。 
- 从下标 2 跳跃到下标 3 。 
- 从下标 3 跳跃到下标 4 。 
- 从下标 4 跳跃到下标 5 。 
  可以证明，从 0 到 n - 1 的所有方案中，不存在比 5 步更长的跳跃序列。因此，答案是 5 。 

**示例 3：**

**输入：**nums = \[1,3,6,4,1,2\], target = 0
**输出：**\-1
**解释：**可以证明不存在从 0 到 n - 1 的跳跃序列。因此，答案是 -1 。 

**提示：**

*   `2 <= nums.length == n <= 1000`
*   `-109 <= nums[i] <= 109`
*   `0 <= target <= 2 * 109`

[https://leetcode.cn/problems/maximum-number-of-jumps-to-reach-the-last-index/description/](https://leetcode.cn/problems/maximum-number-of-jumps-to-reach-the-last-index/description/)

> 1.下面写一下我的错误版本：首先在dfs中，ans初始化为0，那么无论我们是否到达n-1这个节点，都会有memo[n - 1] = 0, 而我们初始化为-1，这样我们就没有办法判断是否到达n - 1这个节点。
>
> *解决方案：以后ans初始化最好是Integer.MIN_VALUE, 或则Integer.MAX_VALUE。*
>
> 2.再看错误版本二，我们已经解决了错误版本一中的问题，但是return ans == Integer.MIN_VALUE / 2 ? -1 : ans;如果说到达最后一个节点我们是通过一个不可到达的节点到达的。 其实我们这个算法是必须到达n - 1这个节点才会返回0，不然返回一个极小值，但是这个极小值被+1之后，就不是Integer.MIN_VALUE，所以我们不能这样判

```java
import java.util.Arrays;

class Solution { // 错误版本1
    int n;
    int[] memo;
    public int maximumJumps(int[] nums, int target) {
        n = nums.length;
        memo = new int[n];
        Arrays.fill(memo, -1);
        return dfs(0, nums, target);
    }

    private int dfs(int i, int[] nums, int target) { // 表示到达节点i需要的最大跳跃次数
        if (i == n - 1) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }   
        int res = 0;
        for (int j = i + 1; j < n; j++) {
            if (Math.abs(nums[j] - nums[i]) <= target) {
                res = Math.max(res, dfs(j, nums, target) + 1);
            }
        }
        return memo[i] = res;
    }
}
```

```java
import java.util.Arrays;

class Solution { // 错误版本2
    int n;
    int[] memo;
    public int maximumJumps(int[] nums, int target) {
        n = nums.length;
        memo = new int[n];
        Arrays.fill(memo, -1);
        int ans = dfs(0, nums, target);
        return ans == Integer.MIN_VALUE / 2 ? -1 : ans;
    }

    private int dfs(int i, int[] nums, int target) { // 表示到达节点i需要的最大跳跃次数
        if (i == n - 1) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int res = Integer.MIN_VALUE / 2;
        for (int j = n - 1; j > i; j--) {
            if (Math.abs(nums[j] - nums[i]) <= target) {
                res = Math.max(res, dfs(j, nums, target) + 1);
            }
        }
        return memo[i] = res;
    }
}
```

```java
import java.util.Arrays;

class Solution {
    int n;
    int[] memo;
    public int maximumJumps(int[] nums, int target) {
        n = nums.length;
        memo = new int[n];
        Arrays.fill(memo, -1);
        int ans = dfs(0, nums, target);
        return ans < 0 ? -1 : ans;
    }

    private int dfs(int i, int[] nums, int target) { // 表示到达节点i需要的最大跳跃次数
        if (i == n - 1) {
            return 0;
        }
        if (memo[i] != -1) {
            return memo[i];
        }
        int res = Integer.MIN_VALUE / 2;
        for (int j = n - 1; j > i; j--) {
            if (Math.abs(nums[j] - nums[i]) <= target) {
                res = Math.max(res, dfs(j, nums, target) + 1);
            }
        }
        return memo[i] = res;
    }
}
```

```java
import java.util.Arrays;

class Solution {
    public int maximumJumps(int[] nums, int target) { // 递推
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (Math.abs(nums[i] - nums[j]) > target || dp[j] == -1) {
                    continue;
                }
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        return dp[n - 1];
    }
}
```

