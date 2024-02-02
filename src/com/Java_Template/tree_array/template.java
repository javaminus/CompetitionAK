package com.Java_Template.tree_array;

/**
 * 「树状数组」是一种可以动态维护序列前缀和的数据结构，它的功能是：
 *
 * 单点更新 update(i, v)： 把序列 i 位置的数加上一个值 v
 * 区间查询 query(i)： 查询序列 [1⋯i] 区间的区间和，即 i 位置的前缀和
 * 修改和查询的时间代价都是 O(logn)，其中 n 为需要维护前缀和的序列的长度。
 *
 * 但是我们一般不知道树状数组的N应该为多少？ 如果数据范围为10**9,那么要开辟这么大的数组，数据可能十分稀疏，这里使用离散化：
 * 离散化一个序列的前提是我们只关心这个序列里面元素的相对大小，而不关心绝对大小（即只关心元素在序列中的排名）；离散化的目的是让原来分布零散的值聚集到一起，减少空间浪费。那么如何获得元素排名呢，我们可以对原序列排序后去重，对于每一个 ai
 * 通过二分查找的方式计算排名作为离散化之后的值。当然这里也可以不去重，不影响排名。
 */

// 树状数组模板
class BitTree {
    // 最大数组长度
    private int maxN;
    // 树状数组存储结构
    private int[] treeArray;

    // 构造函数，初始化树状数组
    public BitTree(int maxN) {
        this.maxN = maxN;
        treeArray = new int[maxN + 1];
    }

    // 获取x的二进制表示中最低位的1所对应的值
    public int lowBit(int x) {
        return x & (-x);
    }

    // 更新操作，将数组中位置x的元素加1
    public void update(int x) {
        while (x <= maxN) {
            treeArray[x]++;
            x += lowBit(x);
        }
    }

    // 查询操作，获取数组前缀和，即位置1到位置x的所有元素的和
    public int query(int x) {
        int res = 0;
        while (x >= 1) {
            res += treeArray[x];
            x -= lowBit(x);
        }
        return res;
    }
}

class BIT {
    // 最大数组长度
    private int maxN;
    // 树状数组存储结构
    private int[] treeArray;

    // 构造函数，初始化树状数组
    public BIT(int maxN) {
        this.maxN = maxN;
        treeArray = new int[maxN + 1];
    }

    // 获取x的二进制表示中最低位的1所对应的值
    public int lowBit(int x) {
        return x & (-x);
    }

    // 更新操作，将数组中位置x的元素加1
    public void update(int x,int dt) {
        while (x <= maxN) {
            treeArray[x]+=dt;
            x += lowBit(x);
        }
    }

    // 查询操作，获取数组前缀和，即位置1到位置x的所有元素的和
    public int query(int x) {
        int res = 0;
        while (x >= 1) {
            res += treeArray[x];
            x -= lowBit(x);
        }
        return res;
    }
}
