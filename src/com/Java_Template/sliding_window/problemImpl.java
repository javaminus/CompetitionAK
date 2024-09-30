package com.Java_Template.sliding_window;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;

/**
 * @author Minus
 * @date 2024/2/5 14:50
 */
public class problemImpl implements problem {
    @Override
    public int numberOfSubarrays(int[] nums, int k) {
        int left = 0, right = 0, oddCnt = 0, ans = 0, n = nums.length;
        while (right < n) {
            if ((nums[right++] & 1) == 1) {
                oddCnt++;
            }
            if (oddCnt == k) {
                int temp = right;
                while (right < n && (nums[right] & 1) == 0) {
                    right++;
                }
                int rightSum = right - temp;
                int leftSum = 0;
                while ((nums[left] & 1) == 0) {
                    leftSum++;
                    left++;
                }
                ans += (leftSum + 1) * (rightSum + 1);
                left++;
                oddCnt--;
            }
        }
        return ans;
    }

    @Override
    public int longestSubarray(int[] nums, int limit) { // 方法一：滑动窗口+单调队列
        int n = nums.length, ans = 1;
        ArrayDeque<Integer> queue1 = new ArrayDeque<>(); // 大 -> 小
        ArrayDeque<Integer> queue2 = new ArrayDeque<>(); // 小 -> 大
        int left = 0, right = 0;
        while (right < n) {
            while (!queue1.isEmpty() && nums[queue1.peekLast()] < nums[right]) {
                queue1.pollLast();
            }
            while (!queue2.isEmpty() && nums[queue2.peekLast()] > nums[right]) {
                queue2.pollLast();
            }
            queue1.offerLast(right);
            queue2.offerLast(right);
            while (!queue1.isEmpty() && !queue2.isEmpty() && nums[queue1.peekFirst()] - nums[queue2.peekFirst()] > limit) {
                if (nums[queue1.peekFirst()] == nums[left]) {
                    queue1.pollFirst();
                }
                if (nums[queue2.peekFirst()] == nums[left]) {
                    queue2.pollFirst();
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }

    public int longestSubarray2(int[] nums, int limit) { // 方法二：滑动窗口+有序数组
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int n = nums.length, ans = 1;
        int left = 0, right = 0;
        while (right < n) {
            map.put(nums[right], map.getOrDefault(nums[right], 0) + 1);
            while (map.lastKey() - map.firstKey() > limit) {
                map.put(nums[left], map.get(nums[left]) - 1);
                if (map.get(nums[left]) == 0) {
                    map.remove(nums[left]);
                }
                left++;
            }
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }

    @Override
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        int n = chargeTimes.length;
        int left = 0, right = 0, ans = 0;
        long sum = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>(); // 大 -> 小
        while (right < n) {
            while (!queue.isEmpty() && chargeTimes[queue.peekLast()] <= chargeTimes[right]) {
                queue.pollLast();
            }
            queue.offerLast(right);
            sum += runningCosts[right];
            while (!queue.isEmpty() && chargeTimes[queue.peekFirst()] + (right - left + 1) * sum > budget) {
                if (queue.peekFirst() == left) {
                    queue.pollFirst();
                }
                sum -= runningCosts[left++];
            }
            ans = Math.max(ans, right - left + 1);
            right++;
        }
        return ans;
    }

    @Override
    public int countCompleteSubarrays(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);
        int m = set.size();
        int ans = 0, left = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.merge(num, 1, Integer::sum);
            while (cnt.size() == m) {
                int x = nums[left++];
                if (cnt.merge(x, -1, Integer::sum) == 0) {
                    cnt.remove(x);
                }
            }
            ans += left;
        }
        return ans;
    }


    // 3306. 元音辅音字符串计数 II
    static HashMap<Character, Integer> map = new HashMap<Character, Integer>();
    static {
        map.put('a', 0);
        map.put('e', 1);
        map.put('i', 2);
        map.put('o', 3);
        map.put('u', 4);
    }
    @Override
    public long countOfSubstrings(String word, int k) {
        char[] cs = word.toCharArray();
        return f(cs, k) - f(cs, k + 1);
    }

    private long f(char[] cs, int k) { // 至少每个元音出现一次，且辅音出现大于k次
        int n = cs.length;
        int[] cnt = new int[5];
        int cntfu = 0;
        long ans = 0L;
        int left = 0;
        for (int right = 0; right < n; right++) {
            if (map.containsKey(cs[right])) {
                cnt[map.get(cs[right])]++;
            }else{
                cntfu++;
            }
            while (left <= right && judge(cnt) && cntfu >= k) {
                if (map.containsKey(cs[left])) {
                    cnt[map.get(cs[left])]--;
                }else{
                    cntfu--;
                }
                left++;
            }
            ans += left;
        }
        return ans;
    }
    private boolean judge(int[] cnt) {
        for (int i = 0; i < 5; i++) {
            if (cnt[i] <= 0) {
                return false;
            }
        }
        return true;
    }
}
