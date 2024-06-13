package com.Java_Template.greed.median_greed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * @author Minus
 * @date 2024/2/1 13:33
 */
public class problemImpl implements problem {
    @Override
    public int[] numsGame(int[] nums) {
        final int Mod = (int) 1e9 + 7;
        int n = nums.length;
        int[] ans = new int[n];
        // 用一个大根堆 left 维护较小的一半，其元素和为 leftSum；
        // 用一个小根堆 right 维护较大的一半，其元素和为 rightSum
        PriorityQueue<Integer> left = new PriorityQueue<>((a, b) -> b - a); // 大根堆 从大到小
        PriorityQueue<Integer> right = new PriorityQueue<>();  // 小根堆 从小到大
        long leftSum = 0L, rightSum = 0L;
        for (int i = 0; i < n; i++) {
            int b = nums[i] - i;
            if (i % 2 == 0) { // 前缀和为奇数
                // 维护两个堆 left < right
                if (!left.isEmpty() && left.peek() > b) {
                    leftSum += b - left.peek();
                    left.offer(b);
                    b = left.poll();
                }
                right.offer(b);
                rightSum += b;
                ans[i] = (int) ((rightSum - leftSum - right.peek()) % Mod);
            }else{ // 前缀和为偶数
                if (right.peek() < b) {
                    rightSum += b - right.peek();
                    right.offer(b);
                    b = right.poll();
                }
                leftSum += b;
                left.offer(b);
                ans[i] = (int) ((rightSum - leftSum) % Mod);
            }
        }
        return ans;
    }


    public long minCost(int[] nums, int[] cost) { // 把成本当作出现次数做法
        int n = nums.length;
        int[][] pairs = new int[n][2];
        for (int i = 0; i < n; i++) {
            pairs[i][0] = nums[i];
            pairs[i][1] = cost[i];
        }
        Arrays.sort(pairs, (a, b) -> a[0] - b[0]);
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += cost[i];
        }
        sum = (sum + 1) / 2;
        int target = 0;
        long cnt = 0;
        for (int i = 0; i < n; i++) {
            if (cnt + pairs[i][1] < sum) {
                cnt += pairs[i][1];
            }else{
                target = pairs[i][0];
                break;
            }
        }
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans += ((long) Math.abs(target - pairs[i][0]) * pairs[i][1]);
        }
        return ans;
    }

/*    // 二分做法
    public long minCost(int[] nums, int[] cost) {
        int l = 0, r = 1000010;
        while (l < r) {
            int mid = (l + r) >> 1;
            //每次取mid和mid+1对应的总cost，如果前者总cost小于后者，说明最优解在左边，如果大于，则在右边
            long cost1 = getCost(nums, cost, mid);
            long cost2 = getCost(nums, cost, mid + 1);
            if (cost1 < cost2) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return getCost(nums, cost, l);
    }
    long getCost(int[] nums , int[] cost , int t){
        long ret = 0;
        for(int i = 0 ; i < nums.length ; i++){
            ret += 1L*Math.abs(nums[i] - t) * cost[i];
        }
        return ret;
    }*/

    // 使子数组元素和相等 2071
    public long makeSubKSumEqual(int[] arr, int k) {
        int n = arr.length;
        k = gcd(n, k);
        long ans = 0L;
        for (int i = 0; i < k; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = i; j < n; j += k) {
                list.add(arr[j]);
            }
            Collections.sort(list);
            int target = list.get(list.size() / 2);
            for (int x : list) {
                ans += Math.abs(target - x);
            }
        }
        return ans;
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }


    private static final int[] pal = new int[109999];
    static {
        // 严格按顺序从小到大生成回文数（不用字符串转换）
        int palIdx = 0;
        for (int base = 1; base <= 10000; base *= 10) {
            // 生成奇数长度回文数
            for (int i = base; i < base * 10; i++) {
                int x = i;
                for (int t = i / 10; t > 0; t /= 10) {
                    x = x * 10 + t % 10;
                }
                pal[palIdx++] = x;
            }
            // 生成偶数长度回文数
            if (base <= 1000) {
                for (int i = base; i < base * 10; i++) {
                    int x = i;
                    for (int t = i; t > 0; t /= 10) {
                        x = x * 10 + t % 10;
                    }
                    pal[palIdx++] = x;
                }
            }
        }
        pal[palIdx++] = 1_000_000_001; // 哨兵，防止下面代码中的 i 下标越界
    }
    public long minimumCost(int[] nums) {
        // 注：排序只是为了找中位数，如果用快速选择算法，可以做到 O(n)
        Arrays.sort(nums);
        int n = nums.length;
        // 二分找中位数右侧最近的回文数
        int i = lowerBound(nums[(n - 1) / 2]);
        // 回文数在中位数范围内
        if (pal[i] <= nums[n / 2]) {
            return cost(nums, i);
        }
        // 枚举离中位数最近的两个回文数 pal[i-1] 和 pal[i]
        return Math.min(cost(nums, i - 1), cost(nums, i));
    }

    private long cost(int[] nums, int i) {
        int target = pal[i];
        long ans = 0;
        for (int x : nums) {
            ans += Math.abs(x - target);
        }
        return ans;
    }

    private int lowerBound(int target) {
        int left = 0, right = pal.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (pal[mid] < target) {
                left = mid + 1;
            }else{
                right = mid;
            }
        }
        return left;
    }


    public int maxFrequencyScore(int[] nums, long k) {
        Arrays.sort(nums);
        int n = nums.length;
        long[] prefix = new long[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        int ans = 0, left = 0;
        for (int right = 0; right < n; right++) {
            while (distanceSum(prefix, nums, left, right, (left + right) / 2) > k) {
                left++;
            }
            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    // 把 nums[l] 到 nums[r] 都变成 nums[i]的距离
    private long distanceSum(long[] prefix, int[] nums, int left, int right, int median) {
        long leftSum = (long) nums[median] * (median - left) - (prefix[median] - prefix[left]);
        long rightSum = prefix[right + 1] - prefix[median + 1] - (long) (right - median) * nums[median];
        return leftSum + rightSum;
    }

}
