package com.leetcode.weekly_contest_412;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * @author Minus
 * @date 2024/8/27 16:01
 *
 * 太疲惫了，都周二了才来补周赛的题目
 */
public class Solution {
    // https://leetcode.cn/problems/count-almost-equal-pairs-ii/ 时间瓶颈在交换数位，可以用预处理数位
    public int countPairs(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int x : nums) {
            HashSet<Integer> set = new HashSet<>();
            set.add(x);
            char[] s = Integer.toString(x).toCharArray();
            int m = s.length;
            for (int i = 0; i < m; i++) {
                for (int j = i + 1; j < m; j++) {
                    swap(s, i, j); // 交换一次
                    set.add(Integer.parseInt(new String(s)));
                    for (int p = i + 1; p < m; p++) {
                        for (int q = p + 1; q < m; q++) {
                            swap(s, p, q);
                            set.add(Integer.parseInt(new String(s)));
                            swap(s, p, q);
                        }
                    }
                    swap(s, i, j);
                }
            }
            for (int v : set) {
                ans += cnt.getOrDefault(v, 0);
            }
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }
    private void swap(char[] s, int i, int j) {
        char tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }

    // 方法二  数位公式：y = x + (s[j] - s[i]) * (POW10[i] - POW10[j]) 交换x的第i位与第j位
    private static final int[] POW10 = new int[]{1, 10, 100, 1000, 10000, 100000,1000000};
    public int countPairs2(int[] nums) {
        Arrays.sort(nums);
        int ans = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        int[] a = new int[7];
        for (int x : nums) {
            HashSet<Integer> set = new HashSet<>();
            set.add(x);
            int m = 0;
            for (int v = x; v > 0; v /= 10) {
                a[m++] = v % 10;
            }
            for (int i = 0; i < m; i++) {
                for (int j = i + 1; j < m; j++) {
                    if (a[i] == a[j]) { // 小优化
                        continue;
                    }
                    int y = x + (a[j] - a[i]) * (POW10[i] - POW10[j]);
                    set.add(y); // 交换一次
                    swap(a, i, j);
                    for (int p = i + 1; p < m; p++) {
                        for (int q = p + 1; q < m; q++) {
                            set.add(y + (a[q] - a[p]) * (POW10[p] - POW10[q])); // 交换两次
                        }
                    }
                    swap(a, i, j);
                }
            }
            for (int v : set) {
                ans += cnt.getOrDefault(v, 0);
            }
            cnt.merge(x, 1, Integer::sum);
        }
        return ans;
    }
    private void swap(int[] s, int i, int j) {
        int tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }

    // https://leetcode.cn/problems/final-array-state-after-k-multiplication-operations-ii/description/
    private static int Mod = (int) 1e9 + 7;
    public int[] getFinalState(int[] nums, int k, int multiplier) {
        if (multiplier == 1) {
            return nums;
        }
        int n = nums.length;
        int mx = 0;
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));
        for (int i = 0; i < n; i++) {
            mx = Math.max(mx, nums[i]);
            pq.offer(new long[]{nums[i], i});
        }
        // 模拟，直到堆顶是mx
        for (; k > 0 && pq.peek()[0] < mx; k--) {
            long[] poll = pq.poll();
            poll[0] *= multiplier;
            pq.offer(poll);
        }
        for (int i = 0; i < n; i++) {
            long[] poll = pq.poll();
            nums[(int) poll[1]] = (int) (poll[0] % Mod * power(multiplier, k / n + (i < k % n ? 1 : 0)) % Mod);
        }
        return nums;
    }
    private long power(long a, long b) { // 求 (a ^ b) % p
        int p = Mod;
        long ans = 1;
        while (b > 0) {
            if ((b & 1) == 1) {
                ans = (ans * a) % p;
            }
            a = (a * a) % p;
            b >>= 1;
        }
        return ans;
    }

}
