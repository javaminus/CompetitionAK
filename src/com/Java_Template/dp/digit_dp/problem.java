package com.Java_Template.dp.digit_dp;

/**
 * 数位dp的模板只能是【1，n】，不包含0
 *
 * 1012. 至少有 1 位重复的数字(https://leetcode.cn/problems/numbers-with-repeated-digits/description/)
 * 2719. 统计整数数目(https://leetcode.cn/problems/count-of-integers/description/)
 * 788. 旋转数字(https://leetcode.cn/problems/rotated-digits/description/)
 * 902. 最大为 N 的数字组合(https://leetcode.cn/problems/numbers-at-most-n-given-digit-set/description/)
 * 233. 数字 1 的个数(https://leetcode.cn/problems/number-of-digit-one/description/)
 * 600. 不含连续1的非负整数（https://leetcode.cn/problems/non-negative-integers-without-consecutive-ones/description/）
 *
 */
public interface problem {
    // 1012. 至少有 1 位重复的数字
    public int numDupDigitsAtMostN(int n);

    // 2719. 统计整数数目
    public int count(String num1, String num2, int min_sum, int max_sum);

    // 788. 旋转数字
    public int rotatedDigits(int n);
}
