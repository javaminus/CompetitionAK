class MajorityChecker {

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

    public MajorityChecker(int[] arr) {

    }

    public int query(int left, int right, int threshold) {

    }
}

/**
 * Your MajorityChecker object will be instantiated and called as such:
 * MajorityChecker obj = new MajorityChecker(arr);
 * int param_1 = obj.query(left,right,threshold);
 */