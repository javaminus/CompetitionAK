package com.Java_Template.Math;

/**
 * 172. 阶乘后的零(https://leetcode.cn/problems/factorial-trailing-zeroes/description/?envType=study-plan-v2&envId=top-interview-150)
 * 1017. 负二进制转换(https://leetcode.cn/problems/convert-to-base-2/)
 * TODO 483. 最小好进制(https://leetcode.cn/problems/smallest-good-base/description/)
 * 166. 分数到小数(https://leetcode.cn/problems/fraction-to-recurring-decimal/description/) 拼多多 二面算法题
 * 910. 最小差值 II(https://leetcode.cn/problems/smallest-range-ii/description/)
 * 927. 三等分(https://leetcode.cn/problems/three-equal-parts/description/)  同理可以延伸到4等分，5等分
 * 1015. 可被 K 整除的最小整数(https://leetcode.cn/problems/smallest-integer-divisible-by-k/description/)
 * 1250. 检查「好数组」(https://leetcode.cn/problems/check-if-it-is-a-good-array/)  NOTE 斐蜀定理：求最大公约数是否为1.
                                                                                 * 裴蜀定理:
                                                                                 * 若a,b是整数,且gcd(a,b)=d，那么对于任意的整数x,y,ax+by都一定是d的倍数，
                                                                                 * 特别地，一定存在整数x,y，使ax+by=d成立。
                                                                                 *  a,b互质的充要条件是存在整数x,y使ax+by=1.  也可以推广到多个，gcd(a,b,c,c) = d，则，，，，，，
 * 878. 第 N 个神奇数字（https://leetcode.cn/problems/nth-magical-number/description/）二分+容斥原理
 * 829. 连续整数求和(https://leetcode.cn/problems/consecutive-numbers-sum/description/)
 */
public interface problem {

    // 172. 阶乘后的零
    public int trailingZeroes(int n);

    // 1017. 负二进制转换
    public String baseNeg2(int n);

    // 166. 分数到小数
    public String fractionToDecimal(int numerator, int denominator);

    // 910. 最小差值 II
    public int smallestRangeII(int[] nums, int k);

    // 927. 三等分
    public int[] threeEqualParts(int[] arr);

}
