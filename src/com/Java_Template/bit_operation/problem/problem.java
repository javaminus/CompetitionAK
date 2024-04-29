package com.Java_Template.bit_operation.problem;

/**
 * 算法：位运算相关技巧（也叫bitmasks）
 * 功能：进行二进制上的位操作，包括与、异或、或、取反，通常使用按位思考与举例的方式寻找规律
 * 题目：经典问题abc_121d 求 1^2^...^n
 * 异或经典性质：(4*i)^(4*i+1)^(4*i+2)^(4*i+3)=0  (2*n)^(2*n+1)=1
 * 异或经典性质：(duplicate&b)^(duplicate&c) = duplicate&(b^c)
 * ===================================力扣===================================
 * <p> 与或
 * 2401. 最长优雅子数组(https://leetcode.cn/problems/longest-nice-subarray/description/)
 * 2680. 最大或值(https://leetcode.cn/problems/maximum-or/description/)
 * 2411. 按位或最大的最小子数组长度(https://leetcode.cn/problems/smallest-subarrays-with-maximum-bitwise-or/description/) OR模板秒杀
 * 898. 子数组按位或操作(https://leetcode.cn/problems/bitwise-ors-of-subarrays/description/) OR模板秒杀
 * 1521. 找到最接近目标值的函数值(https://leetcode.cn/problems/find-a-value-of-a-mysterious-function-closest-to-target/description/) OR模板秒杀
 * <p>
 * <p> 异或
 * 1310. 子数组异或查询(https://leetcode.cn/problems/xor-queries-of-a-subarray/description/) 模板题：区间值查询
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * LCR 190. 加密运算(https://leetcode.cn/problems/bu-yong-jia-jian-cheng-chu-zuo-jia-fa-lcof/description/) 位运算实现加法
 * Problem: LCR 003. 比特位计数(https://leetcode.cn/problems/w3tCBm/description/)
 * 190. 颠倒二进制位(https://leetcode.cn/problems/reverse-bits/description/) return Integer.reverse(n);
 * 可结合的元素对【算法赛】(https://www.lanqiao.cn/problems/17021/learning/?contest_id=174) 蓝桥杯
 * <p>
 * <p>
 * 2354. 优质数对的数目（https://leetcode.cn/problems/number-of-excellent-pairs/）需要脑筋急转弯确定位 1 的规律进行哈希计数枚举即可
 * 260. 只出现一次的数字 III（https://leetcode.cn/problems/single-number-iii/）利用位运算两个相同元素异或和为0的特点，以及lowbit进行分组确定两个只出现一次的元素
 * 6365. 将整数减少到零需要的最少操作数（https://leetcode.cn/problems/minimum-operations-to-reduce-an-integer-to-0/）n 加上或减去 2 的某个幂使得 n 变为 0 的最少操作数
 * 6360. 最小无法得到的或值（https://leetcode.cn/problems/minimum-impossible-or/）利用贪心思想，类似硬币凑金额推理得出结论
 * 2564. 子字符串异或查询（https://leetcode.cn/problems/substring-xor-queries/）利用二进制字符串无前置0时长度不超过10的特点进行查询
 * 1238. 循环码排列（https://leetcode.cn/problems/circular-permutation-in-binary-representation/）生成格雷码，使得循环数组相邻数字二进制位只有一位不同
 * 89. 格雷编码（https://leetcode.cn/problems/gray-code/）生成 0 开头的 n 位格雷码序列
 * 137. 只出现一次的数字 II（https://leetcode.cn/problems/single-number-ii/）使用位运算按位计数
 * 剑指 Offer 56 - I. 数组中数字出现的次数（https://leetcode.cn/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof/）使用位运算按位计数
 * 260. 只出现一次的数字 III（https://leetcode.cn/problems/single-number-iii/）使用位运算按位计数
 * 2546. 执行逐位运算使字符串相等（https://leetcode.cn/problems/apply-bitwise-operations-to-make-strings-equal/）按照异或特点脑筋急转弯
 * 1486. 数组异或操作（https://leetcode.cn/problems/xor-operation-in-an-array/）经典异或公式计算
 * 1734. 解码异或后的排列（https://leetcode.cn/problems/decode-xored-permutation/）经典变换公式，解码相邻异或值编码，并利用奇数排列的异或性质
 * 1787. 使所有区间的异或结果为零（https://leetcode.cn/problems/make-the-xor-of-all-segments-equal-to-zero/）经典按照异或特性分组并利用值域枚举DP
 * 1835. 所有数对按位与结果的异或和（https://leetcode.cn/problems/find-xor-sum-of-all-pairs-bitwise-and/）按位操作模拟
 * 1611. 使整数变为 0 的最少操作次数（https://leetcode.cn/problems/minimum-one-bit-operations-to-make-integers-zero/）格雷码的操作，直接计算格雷码对应的二进制数字
 * 2275. 按位与结果大于零的最长组合（https://leetcode.cn/problems/largest-combination-with-bitwise-and-greater-than-zero/）求按位与不为0的最长子序列，不要求连续
 * 2527. 查询数组 Xor 美丽值（https://leetcode.cn/problems/find-xor-beauty-of-array/description/）按位枚举脑筋急转弯
 * 2680. 最大或值（https://leetcode.cn/problems/maximum-or/description/）贪心枚举，前后缀或分解
 * 100087. 对数组执行操作使平方和最大（https://leetcode.cn/problems/apply-operations-on-array-to-maximize-sum-of-squares/description/）经典按位贪心
 * <p>
 * ===================================洛谷===================================
 * P5657 格雷码（https://www.luogu.com.cn/problem/P5657）计算编号为 k 的二进制符，并补前缀 0 为 n 位
 * P6102 [EER2]谔运算（https://www.luogu.com.cn/problem/P6102）经典位运算加和题目，按位计算，按照位0与位1的数量进行讨论
 * P7442 「EZEC-7」维护序列（https://www.luogu.com.cn/problem/P7442）观察操作规律，使用位运算模拟操作
 * P7617 [COCI2011-2012#2] KOMPIĆI（https://www.luogu.com.cn/problem/P7617）使用位运算枚举
 * P7627 [COCI2011-2012#1] X3（https://www.luogu.com.cn/problem/P7627）经典按位操作枚举计算个数
 * P7649 [BalticOI 2004 Day 1] SCALES（https://www.luogu.com.cn/problem/P7649）三进制计算，贪心模拟砝码放置
 * P1582 倒水（https://www.luogu.com.cn/problem/P1582）进制题脑经急转弯
 * P2114 [NOI2014] 起床困难综合症（https://www.luogu.com.cn/problem/P2114）按位操作计算模拟，贪心选取最大结果
 * P2326 AKN’s PPAP（https://www.luogu.com.cn/problem/P2326）按位模拟贪心选取与值最大的数值对，最大与值对
 * P4144 大河的序列（https://www.luogu.com.cn/problem/P4144）按位思考贪心脑筋急转弯
 * P4310 绝世好题（https://www.luogu.com.cn/problem/P4310）线性 DP 使用按位转移
 * P5390 [Cnoi2019]数学作业（https://www.luogu.com.cn/problem/P5390）按位操作
 * P6824 「EZEC-4」可乐（https://www.luogu.com.cn/problem/P6824）经典按位操作计算异或不等式在使用差分作用域计数
 * P8842 [传智杯 #4 初赛] 小卡与质数2（https://www.luogu.com.cn/problem/P8842）经典质数个数前缀和与异或不等式区间计数
 * P8965 坠梦 | Falling into Dream（https://www.luogu.com.cn/problem/P8965）树形 DP 与异或计算
 * <p>
 * ================================CodeForces================================
 * C. Ivan and Powers of Two（https://codeforces.com/problemset/problem/305/C）利用二进制加减的思想进行解题
 * A. Short Program（https://codeforces.com/problemset/problem/878/A）位运算的操作理解
 * C. XOR and OR（http://codeforces.com/problemset/problem/282/C）利用位运算的特性进行判断
 * C. Mikasa（https://codeforces.com/problemset/problem/1554/C）经典位运算操作贪心计算
 * F. Dasha and Nightmares（https://codeforces.com/contest/1800/problem/F）位运算枚举计数
 * D. Little Girl and Maximum XOR（https://codeforces.com/problemset/problem/276/D）范围[l,r]区间的最大异或和
 * G. Orray（https://codeforces.com/contest/1742/problem/G）重排数组使得前缀或值的字典序最大
 * F. Lisa and the Martians（https://codeforces.com/contest/1851/problem/F）经典数组的最小异或对，一定是排序后相邻的数
 * D. Sum of XOR Functions（https://codeforces.com/contest/1879/problem/D）经典按位计算贡献与前缀和计数与前缀下标加和
 * D. AND, OR and square sum（https://codeforces.com/problemset/problem/1368/D）经典按位模拟贪心
 * C. The Very Beautiful Blanket（https://codeforces.com/contest/1802/problem/C）construction with the property of xor
 * <p>
 * ================================AtCoder================================
 * D - XXOR（https://atcoder.jp/contests/abc117/tasks/abc117_d）从高位到低位按位贪心，思维题
 * D - XOR World（https://atcoder.jp/contests/abc121/tasks/abc121_d）正解为(2*n)^(2*n+1)=1的性质，可使用数位DP计算 1^2^...^num的值
 * D - Xor Sum 4（https://atcoder.jp/contests/abc147/tasks/abc147_d）典型按位异或和计算贡献
 * <p>
 * ================================AcWing===================================
 * 998. 起床困难综合症（https://www.acwing.com/problem/content/1000/）按位进行或、异或、与操作后贪心选取最大值
 * 4614. 匹配价值（https://www.acwing.com/problem/content/4617/）位运算枚举与前缀和预处理
 * <p>
 * <p>
 * 参考：OI WiKi（xx）
 * https://blog.csdn.net/qq_35473473/article/details/106320878
 */
public interface problem {
    // LCR 190. 加密运算  位运算实现加法
    public int encryptionCalculate(int a, int b);

    // LCR 003. 比特位计数
    public int[] countBits(int n);

    // 190. 颠倒二进制位
    public int reverseBits(int n);

    // 2401. 最长优雅子数组
    public int longestNiceSubarray(int[] nums);

    // 2680. 最大或值
    public long maximumOr(int[] nums, int k);

    // 2411. 按位或最大的最小子数组长度
    public int[] smallestSubarrays(int[] nums);

    // 898. 子数组按位或操作
    public int subarrayBitwiseORs(int[] nums);

    // 1521. 找到最接近目标值的函数值
    public int closestToTarget(int[] nums, int target);

    // ------------------------------------------------异或----------------------------------------------------------
    // 1310. 子数组异或查询
    public int[] xorQueries(int[] arr, int[][] queries);

}
