import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int maxTotalReward(int[] rewardValues) {
        // `Arrays.stream(rewardValues).distinct().sorted().toArray()` 是Java中的一段代码，它的意思是：
        //
        //1. `Arrays.stream(rewardValues)`：将数组 `rewardValues` 转换为一个流（Stream）。
        //2. `.distinct()`：从流中去除重复的元素。
        //3. `.sorted()`：对流中的元素进行排序。
        //4. `.toArray()`：将流转换回数组。
        //
        //所以，这段代码的作用是对数组 `rewardValues` 进行去重和排序操作，并将结果存储在一个新的数组中。
        int mx = 0;
        for (int x : rewardValues) {
            mx = Math.max(mx, x);
        }
        Set<Integer> set = new HashSet<>();
        for (int x : rewardValues) {
            if (x == mx - 1) {
                return mx * 2 - 1;
            }
            if (set.contains(x)) {
                continue;
            }
            if (set.contains(mx - 1 - x)) {
                return mx * 2 - 1;
            }
            set.add(x);
        }
        int[] nums = Arrays.stream(rewardValues).distinct().sorted().toArray();
        BigInteger f = BigInteger.ONE;
        for (int x : nums) {
            // 条件 x <= j < x*2 ， f |= (f & ((1 << v) - 1)) << v
            BigInteger mask = BigInteger.ONE.shiftLeft(x).subtract(BigInteger.ONE);
            f = f.or(f.and(mask).shiftLeft(x));
        }
        return f.bitLength() - 1;
    }
}