class Solution {
    int N = (int) 1e5;
    static class Node{
        Node leftNode,rightNode;
        int val, add;
    }

    Node root = new Node();

    int query(Node node, int leftChild, int rightChild, int left, int right) {
        if (left <= leftChild && right >= rightChild) {
            return node.val;
        }
        pushdown(node);
        int mid = leftChild + (rightChild - leftChild) / 2, ans = 0;
        if (left <= mid) {
            ans = query(node.leftNode, leftChild, mid, left, right);
        }
        if (right > mid) {
            ans = Math.max(query(node.rightNode, mid + 1, rightChild, left, right), ans);
        }
        return ans;
    }

    void update(Node node, int leftChild, int rightChild, int left, int right, int delta) {
        // int len = rightChild - leftChild + 1;
        if (left <= leftChild && right >= rightChild) {
            node.val = delta;
            node.add = delta;
            return;
        }
        pushdown(node);
        int mid = leftChild + (rightChild - leftChild) / 2;
        if (left <= mid) {
            update(node.leftNode, leftChild, mid, left, right, delta);
        }
        if (right > mid) {
            update(node.rightNode, mid + 1, rightChild, left, right, delta);
        }
        pushon(node);
    }

    void pushdown(Node node) {
        if (node.leftNode == null) {
            node.leftNode = new Node();
        }
        if (node.rightNode == null) {
            node.rightNode = new Node();
        }
        if (node.add == 0) {
            return;
        }
        // 相同的点不行，要求严格单调递增
        int add = node.add;
        node.leftNode.val = node.rightNode.val = add; // 不要累加
        node.leftNode.add = node.rightNode.add = add; // 不要累加
        node.add = 0;
    }

    void pushon(Node node) {
        node.val = Math.max(node.leftNode.val, node.rightNode.val);
    }
    public int lengthOfLIS(int[] nums) {
        // 使用线段树进行单点更新+区间查询，线段树区间存储从0到i的最长递增子序列
        int ans = 1;
        for (int num : nums) {
            int x = num + 10005;
            int cnt = query(root, 0, N, 0, x - 1) + 1;
            update(root, 0, N, x, x, cnt);
            ans = Math.max(ans, cnt);
        }
        return ans;
    }
}