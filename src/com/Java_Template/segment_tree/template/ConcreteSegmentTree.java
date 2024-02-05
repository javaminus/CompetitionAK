package com.Java_Template.segment_tree.template;

/**
 * 具体的线段树类
 */
public class ConcreteSegmentTree extends SegementTree {

    /**
     * 构造方法，初始化具体线段树
     *
     * @param origin 初始数据数组
     */
    public ConcreteSegmentTree(int[] origin) {
        super(origin);
    }

    /**
     * 合并查询结果的具体实现
     *
     * @param leftRes  左子树的查询结果
     * @param rightRes 右子树的查询结果
     * @return 合并后的结果
     */
    @Override
    int queryMerge(int leftRes, int rightRes) {
        // 具体的合并逻辑，可以根据实际需求修改
        return leftRes + rightRes;
    }

    /**
     * 执行节点更新的具体实现
     *
     * @param nodeIndex 当前节点在线段树数组中的索引
     * @param value     更新的值
     */
    @Override
    void doUpdate(int nodeIndex, int value) {
        // 具体的更新逻辑，可以根据实际需求修改
        segmentTree[nodeIndex] += value;
    }

    // 可以在具体类中添加其他特定方法和属性
}
