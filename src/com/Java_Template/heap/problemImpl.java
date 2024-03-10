package com.Java_Template.heap;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Minus
 * @date 2024/3/9 14:59
 */
public class problemImpl {

    // 2386. 找出数组的第 K 大和
    public long kSum(int[] nums, int k) {
        long sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= 0) {
                sum += nums[i];
            }else{
                nums[i] = -nums[i];
            }
        }
        Arrays.sort(nums);
        PriorityQueue<Pair<Long, Integer>> pq = new PriorityQueue<>((a, b) -> Long.compare(a.getKey(), b.getKey()));
        pq.offer(new Pair<>(0L, 0));
        while (--k > 0) {
            Pair<Long, Integer> p = pq.poll();
            long s = p.getKey();
            int i = p.getValue();
            if (i < nums.length) {
                pq.offer(new Pair<>(s + nums[i], i + 1));
                if (i > 0) {
                    pq.offer(new Pair<>(s + nums[i] - nums[i - 1], i + 1));
                }
            }
        }
        return sum - pq.peek().getKey();
    }
}
