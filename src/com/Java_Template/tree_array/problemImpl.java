package com.Java_Template.tree_array;

import java.util.*;

public class problemImpl implements problem {

    // 2426. 满足不等式的数对数目
    @Override
    public long numberOfPairs(int[] nums1, int[] nums2, int diff) {
        int N = 40005;
        int n = nums1.length;
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = nums1[i] - nums2[i];
        }
        long ans = 0L;
        BitTree tree = new BitTree(N); // 初始化一个长度为N的树状数组
        for (int i = 0; i < n; i++) {
            int num = 20001 + nums[i];
            if (num + diff < N) {
                ans += tree.query(num + diff);
            }else{
                ans += tree.query(N);
            }
            tree.update(num);
        }
        return ans;
    }

    // LCR 170. 交易逆序对的总数
    public int reversePairs(int[] record) {
        int n = record.length;
        int[] temp = new int[n];
        System.arraycopy(record, 0, temp, 0, n);
        // 离散化
        Arrays.sort(temp);
        for (int i = 0; i < n; i++) {
            // 在一个已排序的数组（tmp）中查找特定元素（record[i]），并返回该元素在数组中的索引位置（从1开始计数）。如果元素不存在于数组中，返回的值将是一个负数，表示元素可插入的位置的负数形式（按照插入后数组仍然有序的方式计算）。
            record[i] = Arrays.binarySearch(temp, record[i]) + 1;
        }
        // 树状数组统计逆序对
        int ans = 0;
        BitTree bit = new BitTree(n);
        for (int i = n - 1; i >= 0; i--) {
            ans += bit.query(record[i] - 1); // bit.query(record[i] - 1); record[i] 是小于等于record[i]的前缀和，这里减一，就是小于record[i]的前缀和
            bit.update(record[i]);
        }

        return ans;
    }

    // 315.计算右侧小于当前元素的个数
    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        int[] temp = new int[n];
        // 离散化
        System.arraycopy(nums, 0, temp, 0, n);
        Arrays.sort(temp);
        for (int i = 0; i < n; i++) {
            nums[i] = Arrays.binarySearch(temp, nums[i]) + 1;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        BitTree tree = new BitTree(n);
        for (int i = n - 1; i >= 0; i--) {
            int t = tree.query(nums[i] - 1);
            tree.update(nums[i]);
            ans.add(t);
        }
        Collections.reverse(ans);
        return ans;
    }

    // 1409. 查询带键的排列
    public int[] processQueries(int[] queries, int m) {
        int n = queries.length;
        BIT bit = new BIT(m + n);
        int[] pos = new int[m + 1];
        for (int i = 1; i <= m; i++) {
            pos[i] = n + i;
            bit.update(n + i, 1);
        }

        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            int cur = pos[queries[i]];
            bit.update(cur, -1);
            ans[i] = bit.query(cur);
            cur = n - i;
            pos[queries[i]] = cur;
            bit.update(cur, 1);
        }
        return ans;
    }


    // 300. 最长递增子序列
    public int lengthOfLIS(int[] nums) {
        return 0;
    }


    // 1626. 无矛盾的最佳球队  需要重写query与update方法
    public int bestTeamScore(int[] scores, int[] ages) {
        int n = scores.length;
        Integer[] ids = new Integer[n];
        for (int i = 0; i < n; i++) {
            ids[i] = i;
        }
        Arrays.sort(ids, (i, j) -> scores[i] != scores[j] ? scores[i] - scores[j] : ages[i] - ages[j]);
        int len = 1000;
        BIT bit = new BIT(len);
        for (int id : ids) {
            bit.update(ages[id], bit.query(ages[id]) + scores[id]);
        }
        return bit.query(len);
    }
    /* class BIT {
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

        // 更新操作，将数组中位置x的元素更新
        public void update(int x,int dt) {
            while (x <= maxN) {
                // treeArray[x]+=dt;
                treeArray[x] = Math.max(treeArray[x], dt);
                x += lowBit(x);
            }
        }

        // 查询操作，查询最大的值
        public int query(int x) {
            int res = 0;
            while (x >= 1) {
                // res += treeArray[x];
                res = Math.max(res, treeArray[x]);
                x -= lowBit(x);
            }
            return res;
        }
    }*/


    public String minInteger(String num, int k) {
        int n = num.length();
        StringBuilder ans = new StringBuilder();
        Queue<Integer>[] position = new Queue[10];
        Arrays.setAll(position, e -> new LinkedList<Integer>()); // 这里只能使用LinkedList
        for (int i = 0; i < n; i++) {
            position[num.charAt(i) - '0'].offer(i + 1);
        }
        BitTree1 bitTree = new BitTree1(n);
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 10; j++) {
                if (!position[j].isEmpty()) {
                    int step = position[j].peek() - i + bitTree.query(n) - bitTree.query(position[j].peek());
                    if (step <= k) {
                        k -= step;
                        ans.append(j);
                        bitTree.update(position[j].poll(), 1);
                        break;
                    }
                }
            }
        }
        return ans.toString();
    }

    class BitTree1{
        int[] tree;

        public BitTree1(int n) {
            tree = new int[n + 1];
        }

        private int lowBit(int i) {
            return i & -i;
        }
        public void update(int i, int val) {
            while (i < tree.length) {
                tree[i] += val;
                i += lowBit(i);
            }
        }

        public int query(int i) {
            int ans = 0;
            while (i > 0) {
                ans += tree[i];
                i -= lowBit(i);
            }
            return ans;
        }
    }
}

