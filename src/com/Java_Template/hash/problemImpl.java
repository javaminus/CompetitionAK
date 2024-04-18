package com.Java_Template.hash;


import java.util.*;

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


    // 100258. 最高频率的 ID
    @Override
    public long[] mostFrequentIDs(int[] nums, int[] freq) {
        /**
         * 易懂的写法
          int n = nums.length;
          long[] ans = new long[n];
          TreeMap<Long, Integer> freqMap = new TreeMap<>(Collections.reverseOrder()); // 出现次数的次数
          HashMap<Integer, Long> cnt = new HashMap<>();
          for (int i = 0; i < n; i++) {
              int x = nums[i];
              int f = freqMap.merge(cnt.get(x), -1, Integer::sum);
              if (cnt.containsKey(x) && f == 0) { // 移除一个之前的旧数据
                  freqMap.remove(cnt.get(x));
              }
              freqMap.merge(cnt.merge(x, (long) freq[i], Long::sum), 1, Integer::sum);
              ans[i] = freqMap.firstKey();
          }
          return ans;
         *
         */
        int n = nums.length;
        long[] ans = new long[n];
        TreeMap<Long, Integer> freqMap = new TreeMap<>(Collections.reverseOrder()); // 出现次数的次数
        HashMap<Integer, Long> cnt = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            if (cnt.containsKey(x) && freqMap.merge(cnt.get(x), -1, Integer::sum) == 0) { // 移除一个之前的旧数据
                freqMap.remove(cnt.get(x));
            }
            freqMap.merge(cnt.merge(x, (long) freq[i], Long::sum), 1, Integer::sum);
            ans[i] = freqMap.firstKey();
        }
        return ans;
    }

    /*  2007. 从双倍数组中还原原数组
        一个整数数组 original 可以转变成一个 双倍 数组 changed ，转变方式为将 original 中每个元素 值乘以 2 加入数组中，然后将所有元素 随机打乱 。
        给你一个数组 changed ，如果 change 是 双倍 数组，那么请你返回 original数组，否则请返回空数组。original 的元素可以以 任意 顺序返回。*/
    public int[] findOriginalArray(int[] changed) {  // 时间：0（nlogn）
        int n = changed.length;
        if (n % 2 == 1) {
            return new int[0];
        }
        int[] ans = new int[n / 2];
        int ansId = 0;
        Arrays.sort(changed);
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (cnt.containsKey(changed[i])) { // 包含双倍元素
                Integer c = cnt.merge(changed[i], -1, Integer::sum);
                if (c == 0) {
                    cnt.remove(changed[i]);
                }
            }else{
                if (ansId == ans.length) {
                    return new int[0];
                }
                ans[ansId++] = changed[i];
                cnt.merge(changed[i] * 2, 1, Integer::sum); // 标记一个双倍元素
            }
        }
        if (ansId != n / 2 || !cnt.isEmpty()) {
            return new int[0];
        }
        return ans;
    }

    public int[] findOriginalArray_plus(int[] changed) { // 时间：0（n）
        int n = changed.length;
        if (n % 2 == 1) {
            return new int[0];
        }
        int[] ans = new int[n / 2];
        int ansId = 0;
        HashMap<Integer, Integer> cnt = new HashMap<>();
        for (int c : changed) {
            cnt.merge(c, 1, Integer::sum);
        }
        int cnt0 = cnt.getOrDefault(0, 0);
        if (cnt0 % 2 == 1) {
            return new int[0];
        }
        cnt.remove(0);
        HashSet<Integer> done = new HashSet<>();
        for (int x : cnt.keySet()) {
            // 如果 x 已处理完毕，或者 x/2 在 cnt 中，则跳过
            if (done.contains(x) || x % 2 == 0 && cnt.containsKey(x / 2)) {
                continue;
            }
            // 把 x, 2x, 4x, 8x, ... 全部配对
            while (cnt.containsKey(x)) {
                int cntX = cnt.get(x);
                int cnt2X = cnt.getOrDefault(x * 2, 0);
                if (cntX > cnt2X) {
                    return new int[0];
                }
                for (int i = 0; i < cntX; i++) {
                    ans[ansId++] = x;
                }
                done.add(x);
                if (cntX < cnt2X) {
                    // 还剩下2x
                    cnt.put(x * 2, cnt2X - cntX);
                    x *= 2;
                }else{
                    // 配对完成
                    done.add(x * 2);
                    x *= 4;
                }
            }
        }
        return ans;
    }

    /*  128. 最长连续序列
        给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
        请你设计并实现时间复杂度为 O(n) 的算法解决此问题。*/
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int x : nums) {
            set.add(x);
        }
        int ans = 0;
        for (int x : set) {
            if (set.contains(x - 1)) {
                continue;
            }
            int len = 0;
            while (set.contains(x)) {
                len++;
                x++;
            }
            ans = Math.max(len, ans);
        }
        return ans;
    }
}
