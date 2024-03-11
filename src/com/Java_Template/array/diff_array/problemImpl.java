package com.Java_Template.array.diff_array;

import java.util.TreeMap;

/**
 * @author Minus
 * @date 2024/3/10 15:24
 */
public class problemImpl implements problem {

    // 1094. 拼车
    public boolean carPooling(int[][] trips, int capacity) {
        int[] diff = new int[1001];
        for (int[] trip : trips) {
            int num = trip[0], from = trip[1], to = trip[2];
            diff[from] += num;
            diff[to] -= num;
        }
        int result = 0;
        for (int d : diff) {
            result += d;
            if (result > capacity) {
                return false;
            }
        }
        return true;
    }

/*  平衡树写法：TreeMap会自动帮我们从小到达排序
    public boolean carPooling(int[][] trips, int capacity) {
        TreeMap<Integer, Integer> d = new TreeMap<>();
        for (int[] t : trips) {
            int num = t[0], from = t[1], to = t[2];
            d.merge(from, num, Integer::sum);
            d.merge(to, -num, Integer::sum);
        }
        int s = 0;
        for (int v : d.values()) {
            s += v;
            if (s > capacity) {
                return false;
            }
        }
        return true;
    }*/


    // 1109. 航班预订统计
    public int[] corpFlightBookings(int[][] bookings, int n) {
        int[] diff = new int[n];
        for (int[] booking : bookings) {
            int first = booking[0] - 1, last = booking[1] - 1, seating = booking[2];
            diff[first] += seating;
            if (last + 1 == n) {
                continue;
            }
            diff[last + 1] -= seating;
        }
        for (int i = 1; i < n; i++) {
            diff[i] = diff[i] + diff[i - 1];
        }
        return diff;
    }

    // 2406. 将区间分为最少组数
    public int minGroups(int[][] intervals) {
        TreeMap<Integer, Integer> diff = new TreeMap<>();
        for (int[] interval : intervals) {
            int a = interval[0], b = interval[1];
            diff.merge(a, 1, Integer::sum);
            diff.merge(b + 1, -1, Integer::sum);
        }
        int ans = 0, cnt = 0;
        for (int d : diff.values()) {
            cnt += d;
            ans = Math.max(ans, cnt);
        }
        return ans;
    }


    // 2381. 字母移位 II
    public String shiftingLetters(String s, int[][] shifts) {
        int n = s.length();
        int[] diff = new int[n + 1];
        for (int[] shift : shifts) {
            int a = shift[0], b = shift[1], c = shift[2];
            diff[a] += c == 1 ? 1 : -1;
            diff[b + 1] += c == 1 ? -1 : 1;
        }
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                diff[i] += diff[i - 1];
            }
            char c = (char) ('a' + (s.charAt(i) + diff[i] % 26 - 'a' + 26) % 26);
            ans.append(c);

        }
        return ans.toString();
    }

    // 2772. 使数组中的所有元素都等于零
    public boolean checkArray(int[] nums, int k) {
        int n = nums.length;
        int[] diff = new int[n + 1];
        diff[0] = nums[0];
        // 计算差分数组
        for (int i = 1; i < n; i++) {
            diff[i] = nums[i] - nums[i - 1];
        }
        // 从左到右对差分数组的每一个元素操作
        for (int i = 0; i + k < n; i++) {
            if (diff[i] > 0) {
                diff[i + k] += diff[i];
                diff[i] = 0;
            }
            else if (diff[i] < 0) {
                return false;
            }
        }
        // 检查差分数组中不能区间修改的元素是否均为0
        for (int i = n - k + 1; i < n; i++) {
            if (diff[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // 2528. 最大化城市的最小电量
    public long maxPower(int[] stations, int r, int k) {
        int n = stations.length;
        long[] sum = new long[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = stations[i] + sum[i];
        }
        long[] power = new long[n];
        long mx = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            power[i] = sum[Math.min(n, i + r + 1)] - sum[Math.max(0, i - r)]; // 妙妙屋
            mx = Math.min(mx, power[i]);
        }
        long left = mx, right = mx + k;
        while (left <= right) {
            long mid = left + (right - left) / 2;
            if (check(power, n, k, r, mid)) {
                left = mid + 1;
            }else{
                right = mid - 1;
            }
        }
        return left - 1;
    }
    private boolean check(long[] power, int n, int k, int r, long minPower) {
        long cntD = 0, need = 0;
        long[] diff = new long[n + 1]; // 差分数组, 与cntD是同时一套出现的
        for (int i = 0; i < n; i++) {
            cntD += diff[i];
            long m = minPower - power[i] - cntD;
            if (m > 0) {
                need += m;
                if (need > k) { // 如果需要的电量 > 可提供的电量
                    return false;
                }
                cntD += m;
                if (i + 2 * r + 1 < n) {
                    diff[i + 2 * r + 1] -= m;
                }
            }
        }
        return true;
    }


}
