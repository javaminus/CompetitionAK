package com.Java_Template.segment_tree.template;

class SegmentTree {
    int[] tree; // 存储线段树的数组
    int[] nums; // 存储原始数组的副本
    int n; // 原始数组的长度

    public SegmentTree(int[] nums) {
        this.nums = nums;
        this.n = nums.length;
        // 线段树的大小为原始数组长度的四倍 , 这只是求前缀和的通用方法，注意定义数组中的值代表的元素
        this.tree = new int[4 * n];
        buildTree(1, 0, n - 1);
    }

    private void buildTree(int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            buildTree(leftChild, start, mid);
            buildTree(rightChild, mid + 1, end);
            // 根据实际需求合并左右子树信息
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    // 区间查询
    public int query(int left, int right) {
        return queryTree(1, 0, n - 1, left, right);
    }

    private int queryTree(int node, int start, int end, int left, int right) {
        if (right < start || left > end) {
            return 0; // 区间不重叠，返回合适的默认值
        }
        if (left <= start && right >= end) {
            return tree[node]; // 完全包含，返回节点的值
        }
        int mid = (start + end) / 2;
        int leftChild = 2 * node;
        int rightChild = 2 * node + 1;
        int leftSum = queryTree(leftChild, start, mid, left, right);
        int rightSum = queryTree(rightChild, mid + 1, end, left, right);
        // 根据实际需求合并左右子树信息
        return leftSum + rightSum;
    }

    // 单点更新
    public void update(int index, int value) {
        updateTree(1, 0, n - 1, index, value);
    }

    private void updateTree(int node, int start, int end, int index, int value) {
        if (start == end) {
            nums[index] = value;
            tree[node] = value;
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            if (index >= start && index <= mid) {
                updateTree(leftChild, start, mid, index, value);
            } else {
                updateTree(rightChild, mid + 1, end, index, value);
            }
            // 根据实际需求合并左右子树信息
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }

    // 区间更新
    public void updateRange(int left, int right, int delta) {
        updateRangeTree(1, 0, n - 1, left, right, delta);
    }

    private void updateRangeTree(int node, int start, int end, int left, int right, int delta) {
        if (start > end || left > end || right < start) {
            return;
        }

        if (start == end) {
            // 叶子节点，更新值
            nums[start] += delta;
            tree[node] += delta;
        } else {
            int mid = (start + end) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;

            // 递归更新左右子树
            updateRangeTree(leftChild, start, mid, left, right, delta);
            updateRangeTree(rightChild, mid + 1, end, left, right, delta);

            // 更新当前节点的值
            tree[node] = tree[leftChild] + tree[rightChild];
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9, 11};
        SegmentTree segmentTree = new SegmentTree(nums);

        // 查询区间和
        System.out.println(segmentTree.query(1, 4)); // 输出：24

        // 更新单点
        segmentTree.update(2, 6);

        // 再次查询更新后的区间和
        System.out.println(segmentTree.query(1, 4)); // 输出：25

        // 区间更新
        segmentTree.updateRange(1, 3, 2); // 区间[1,3]增加2

        // 查询更新后的区间和
        System.out.println(segmentTree.query(1, 4)); // 输出：31
    }
}
