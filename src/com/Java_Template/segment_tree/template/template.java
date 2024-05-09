package com.Java_Template.segment_tree.template;

/**
 * @author Minus
 * @date 2024/5/9 10:16
 */

class template{
    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 7, 9, 11};
        SegmentTree segmentTree = new SegmentTree(nums);
        int n = nums.length;

        // 查询区间和
        System.out.println(segmentTree.query(1, 0, n - 1, 1, 4)); // 输出：24

        // 更新单点
        segmentTree.update(1, 0, n - 1, 2, 2, 1); // 将点2加1

        // 再次查询更新后的区间和
        System.out.println(segmentTree.query(1, 0, n - 1, 1, 4)); // 输出：25

        // 区间更新
        segmentTree.update(1, 0, n - 1, 1, 3, 2); // 区间[1,3]增加2，这里是区间都增加

        // 查询更新后的区间和
        System.out.println(segmentTree.query(1, 0, n - 1, 1, 4)); // 输出：31
    }

    static class SegmentTree {
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
                int mid = start + (end - start) / 2;
                int leftChild = 2 * node;
                int rightChild = 2 * node + 1;
                buildTree(leftChild, start, mid);
                buildTree(rightChild, mid + 1, end);
                // 根据实际需求合并左右子树信息
                tree[node] = tree[leftChild] + tree[rightChild];
            }
        }

        private int query(int node, int start, int end, int left, int right) {
            if (right < start || left > end) {
                return 0; // 区间不重叠，返回合适的默认值
            }
            if (left <= start && right >= end) {
                return tree[node]; // 完全包含，返回节点的值
            }
            int mid = start + (end - start) / 2;
            int leftChild = 2 * node;
            int rightChild = 2 * node + 1;
            int leftSum = query(leftChild, start, mid, left, right);
            int rightSum = query(rightChild, mid + 1, end, left, right);
            // 根据实际需求合并左右子树信息
            return leftSum + rightSum;
        }

        private void update(int node, int start, int end, int left, int right, int delta) {
            if (start > end || left > end || right < start) {
                return;
            }
            if (start == end) {
                // 叶子节点，更新值
                nums[start] += delta;
                tree[node] += delta;
            } else {
                int mid = start + (end - start) / 2;
                int leftChild = 2 * node;
                int rightChild = 2 * node + 1;

                // 递归更新左右子树
                update(leftChild, start, mid, left, right, delta);
                update(rightChild, mid + 1, end, left, right, delta);

                // 更新当前节点的值
                tree[node] = tree[leftChild] + tree[rightChild];
            }
        }

    }
}
