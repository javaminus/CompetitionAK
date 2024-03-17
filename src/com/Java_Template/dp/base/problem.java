package com.Java_Template.dp.base;

/**
 * 100205. 修改数组后最大化数组中的连续元素数目(https://leetcode.cn/problems/maximize-consecutive-elements-in-an-array-after-modification/description/)
 * 2767. 将字符串分割为最少的美丽子字符串(https://leetcode.cn/problems/partition-string-into-minimum-beautiful-substrings/description/) 5的倍数不一定是5的幂，比如75是5的倍数，但是不是5的幂
 * 大富翁游戏（https://ac.nowcoder.com/acm/contest/75771/D）
 * 2312. 卖木头块(https://leetcode.cn/problems/selling-pieces-of-wood/description/?envType=daily-question&envId=2024-03-15)
 */
public interface problem {

    public int maxSelectedElements(int[] nums);

    // 大富翁游戏
    public void niuKe1();

    // 2312. 卖木头块
    public long sellingWood(int m, int n, int[][] prices);
}
