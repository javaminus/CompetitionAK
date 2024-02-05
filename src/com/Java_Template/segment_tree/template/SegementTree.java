package com.Java_Template.segment_tree.template;

/**
 * 抽象的线段树类
 */
abstract class SegementTree {
    // 数组长度
    protected int n;
    // 线段树数组
    protected int[] segmentTree;

    /**
     * 构造方法，初始化线段树
     *
     * @param origin 初始数据数组
     */
    public SegementTree(int[] origin) {
        this.n = origin.length;
        // 使用4倍的数据长度的空间来确保树的节点信息可以被放下。实际上需要的空间 =（n向上扩充到最近的2的某个次方）的两倍
        this.segmentTree = new int[n * 4];
        build(origin, 0, n - 1, 0);
    }

    /**
     * 建树方法，复杂度O(n)，n+1/2n+1/4n+...=2n
     *
     * @param origin    初始数据数组
     * @param start     当前处理范围的起始位置
     * @param end       当前处理范围的结束位置
     * @param nodeIndex 当前节点在线段树数组中的索引
     */
    public void build(int[] origin, int start, int end, int nodeIndex) {
        if (start == end) {
            segmentTree[nodeIndex] = origin[start];
            return;
        }
        int mid = (start + end) / 2;
        build(origin, start, mid, nodeIndex * 2 + 1);
        build(origin, mid + 1, end, nodeIndex * 2 + 2);
        segmentTree[nodeIndex] = queryMerge(segmentTree[nodeIndex * 2 + 1], segmentTree[nodeIndex * 2 + 2]);
    }

    /**
     * 单点更新方法，复杂度log2n
     *
     * @param targetIndex 要更新的目标位置
     * @param value       更新的值
     */
    public void update(int targetIndex, int value) {
        update(0, n - 1, targetIndex, value, 0);
    }

    /**
     * 范围查询方法，复杂度log2n
     *
     * @param rangeStart 查询范围的起始位置
     * @param rangeEnd   查询范围的结束位置
     * @return 查询结果
     */
    public int queryRange(int rangeStart, int rangeEnd) {
        return doQueryRange(0, n - 1, rangeStart, rangeEnd, 0);
    }

    /**
     * 执行范围查询的具体实现
     *
     * @param start      当前处理范围的起始位置
     * @param end        当前处理范围的结束位置
     * @param rangeStart 查询范围的起始位置
     * @param rangeEnd   查询范围的结束位置
     * @param nodeIndex  当前节点在线段树数组中的索引
     * @return 查询结果
     */
    private int doQueryRange(int start, int end, int rangeStart, int rangeEnd, int nodeIndex) {
        if (start == rangeStart && end == rangeEnd) {
            return segmentTree[nodeIndex];
        }
        int mid = (start + end) / 2;
        boolean includeLeft = mid >= rangeStart && rangeEnd >= start;
        boolean includeRight = end >= rangeStart && rangeEnd >= (mid + 1);
        if (includeLeft && includeRight) {
            return queryMerge(
                    doQueryRange(start, mid, rangeStart, mid, nodeIndex * 2 + 1),
                    doQueryRange(mid + 1, end, mid + 1, rangeEnd, nodeIndex * 2 + 2)
            );
        }
        return includeLeft ? doQueryRange(start, mid, rangeStart, rangeEnd, nodeIndex * 2 + 1) :
                doQueryRange(mid + 1, end, rangeStart, rangeEnd, nodeIndex * 2 + 2);
    }

    /**
     * 执行单点更新的具体实现
     *
     * @param start       当前处理范围的起始位置
     * @param end         当前处理范围的结束位置
     * @param targetIndex 要更新的目标位置
     * @param value       更新的值
     * @param nodeIndex   当前节点在线段树数组中的索引
     */
    private void update(int start, int end, int targetIndex, int value, int nodeIndex) {
        if (start == end) {
            doUpdate(nodeIndex, value);
            return;
        }
        int mid = (start + end) / 2;
        if (targetIndex <= mid) {
            update(start, mid, targetIndex, value, nodeIndex * 2 + 1);
        } else {
            update(mid + 1, end, targetIndex, value, nodeIndex * 2 + 2);
        }
        segmentTree[nodeIndex] = queryMerge(segmentTree[nodeIndex * 2 + 1], segmentTree[nodeIndex * 2 + 2]);
    }

    /**
     * 抽象方法，用于合并查询结果
     *
     * @param leftRes  左子树的查询结果
     * @param rightRes 右子树的查询结果
     * @return 合并后的结果
     */
    abstract int queryMerge(int leftRes, int rightRes);

    /**
     * 抽象方法，执行节点更新
     *
     * @param nodeIndex 当前节点在线段树数组中的索引
     * @param value     更新的值
     */
    abstract void doUpdate(int nodeIndex, int value);
}
