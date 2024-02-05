package com.Java_Template.hash;


import java.util.HashMap;

public class problemImpl implements problem {

    @Override
    public long maximumSubarraySum(int[] nums, int k) {
        long ans = Long.MIN_VALUE, sum = 0;
        HashMap<Integer, Long> map = new HashMap<>(); // (元素num:num的前缀和)
        for (int num : nums) {
            long s1 = map.getOrDefault(num - k, Long.MAX_VALUE / 2);
            long s2 = map.getOrDefault(num + k, Long.MAX_VALUE / 2);
            ans = Math.max(ans, sum + num - Math.min(s1, s2));
            map.merge(num, sum, Math::min);
            sum += num;
        }
        return ans > Long.MIN_VALUE / 4 ? ans : 0;
    }

    @Override
    public int numSubarraysWithSum(int[] nums, int goal) { // 有点动态规划的感觉
        int ans = 0, sum = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.put(sum, cnt.getOrDefault(sum, 0) + 1);
            sum += num;
            ans += cnt.getOrDefault(sum - goal, 0);
        }
        return ans;
    }


    @Override
    public int numberOfSubarrays(int[] nums, int k) { // hash+前缀模板
        int ans = 0, sum = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            // 更新奇数个数的总数
            cnt.put(sum, cnt.getOrDefault(sum, 0) + 1);
            // 如果当前数字为奇数，增加奇数个数的计数
            if ((num & 1) == 1) {
                sum++;
            }
            // 更新答案，加上当前奇数个数减去k的情况的总数
            ans += cnt.getOrDefault(sum - k, 0);
        }
        return ans;
    }

    @Override
    public int subarraysDivByK(int[] nums, int k) {
        // 知识点1：(sum % K + K) % K其实就是将负余数转为正余数，但并不是简单的去掉负号，
        // 而是增加一个k的步长，比如-7%4，它的负余数为-3，加上步长4之后，就转为正余数1了；
        // 即对负数有两种计算余数的方式，第一是-7=-14+（-3）此时余数为-3，第二是-7=-24+1此时余数为1。
        // 而并不是说-3装为3，而是-3转为1；这样针对类似于数组[-7,8]，k=4的情况时，
        // 就有-7%4为1，（-7+8）%4为1，两者余数相等，所以（-7+8）-（-7）为8可以整除4，这样不会漏掉8这个数了；

        // 知识点2:同余定理：(p[i] - p[i-1])%Mod = 0  等价于 p[i] % Mod == p[i-1] % Mod
        int ans = 0, sum = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int num : nums) {
            cnt.put(sum, cnt.getOrDefault((sum % k + k) % k, 0) + 1);
            sum += num;
            ans += cnt.getOrDefault((sum % k + k) % k, 0);
        }
        return ans;
    }

    @Override
    public boolean checkSubarraySum(int[] nums, int k) {
        int sum = 0, n = nums.length;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < n; i++) {
            cnt.merge(sum % k, i, Math::min);
            sum += nums[i];
            if (cnt.containsKey(sum % k) && i - cnt.get(sum % k) > 0) { // i - cnt.get(sum % k) > 0 保证子数组大小 至少为 2
                return true;
            }
        }
        return false;
    }

    @Override
    public int numOfSubarrays(int[] arr) {
        // 动态规划+前缀和+数学
        int Mod = (int) 1e9 + 7, sum = 0, ans = 0, oddSum = 0, evenSum = 1;
        for (int a : arr) {
            sum += a;
            if ((sum & 1) == 1) {
                oddSum++;
                ans = (ans + evenSum) % Mod;
            }else{
                evenSum++;
                ans = (ans + oddSum) % Mod;
            }
        }
        return ans % Mod;
    }

    @Override
    public int findMaxLength(int[] nums) {
        int ans = 0, n = nums.length, sum = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        cnt.put(sum, -1);
        for (int i = 0; i < n; i++) {
            sum += nums[i] == 1 ? 1 : -1;
            if (cnt.containsKey(sum)) {
                ans = Math.max(ans, i - cnt.get(sum));
            }else{
                cnt.put(sum, i);
            }
        }
        return ans;
    }
}
