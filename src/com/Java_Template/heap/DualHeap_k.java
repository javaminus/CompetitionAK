package com.Java_Template.heap;

import java.util.HashMap;
import java.util.PriorityQueue;

class Solution {
    // 求数组中的长度为dist+1的滑动窗口的最小的k个元素和
    public long minimumCost(int[] nums, int k, int dist) {
        long sum = nums[0];
        int n = nums.length;
        if (dist + 1 > n - 1) {
            dist = n - 2;
        }
        int interval = dist + 1;
        DualHeap dualHeap = new DualHeap(k - 1);
        for (int i = 1; i < interval + 1; i++) {
            dualHeap.insert(nums[i]);
        }
        long min = dualHeap.getSum();
        for (int i = interval + 1; i < n; i++) {
            dualHeap.insert(nums[i]);
            dualHeap.erase(nums[i - interval]);
            min = Math.min(min, dualHeap.getSum());
        }
        return min + nums[0];
    }
    static class DualHeap {
        PriorityQueue<Long> pq1 = new PriorityQueue<>((a, b) -> Math.toIntExact(b - a));
        PriorityQueue<Long> pq2 = new PriorityQueue<>();
        HashMap<Long, Long> map = new HashMap<>();
        int left, leftSize, rightSize;
        long sum; // 最小的left个数的和

        public DualHeap(int left) {
            this.left = left;
        }

        public long getSum() {
            return sum;
        }

        public void insert(long x) {
            if (leftSize < left || (!pq1.isEmpty() && x <= pq1.peek())) {
                pq1.offer(x);
                sum += x;
                leftSize++;
            } else {
                pq2.offer(x);
                rightSize++;
            }
            balance();
        }

        private void erase(long x) {
            map.put(x, map.getOrDefault(x, 0L) + 1);
            if (!pq1.isEmpty() && x <= pq1.peek()) {
                leftSize--;
                sum -= x;
                if (x == pq1.peek()) {
                    prune(pq1);
                }
            } else {
                rightSize--;
                if (!pq2.isEmpty() && x == pq2.peek()) {
                    prune(pq2);
                }
            }
            balance();
        }

        private void prune(PriorityQueue<Long> heap) {
            while (!heap.isEmpty()) {
                long x = heap.peek();
                if (map.containsKey(x)) {
                    map.put(x, map.get(x) - 1);
                    if (map.get(x) == 0) {
                        map.remove(x);
                    }
                    heap.poll();
                } else {
                    break;
                }
            }
        }

        private void balance() {
            if (leftSize > left) {
                sum -= pq1.peek();
                pq2.offer(pq1.poll());
                leftSize--;
                rightSize++;
                prune(pq1);
            } else if (leftSize < left && rightSize > 0) {
                sum += pq2.peek();
                pq1.offer(pq2.poll());
                leftSize++;
                rightSize--;
                prune(pq2);
            }
        }
    }
//    class DualHeap {
//        PriorityQueue<Integer> pq1 = new PriorityQueue<>((a, b) -> b - a);
//        PriorityQueue<Integer> pq2 = new PriorityQueue<>();
//        HashMap<Integer, Integer> map = new HashMap<>();
//        int k, left, leftSize, rightSize;
//        long sum; // 最小的left个数的和
//
//        public DualHeap(int k, int left) {
//            this.k = k;
//            this.left = left;
//        }
//
//        public long getSum(){
//            return sum;
//        }
//
//        public void insert(int x) {
//            if (pq1.size() < left || x <= pq1.peek()) {
//                pq1.offer(x);
//                sum += x;
//                leftSize++;
//            }else{
//                pq2.offer(x);
//                rightSize++;
//            }
//            balance();
//        }
//
//        private void erase(int x){
//            map.put(x, map.getOrDefault(x, 0) + 1);
//            if (x <= pq1.peek()) {
//                leftSize--;
//                sum -= x;
//                if (x == pq1.peek()) {
//                    prune(pq1);
//                }
//            }else{
//                rightSize++;
//                if (x == pq2.peek()) {
//                    prune(pq2);
//                }
//            }
//            balance();
//        }
//
//        private void prune(PriorityQueue<Integer> heap) { // 懒删除
//            while (!heap.isEmpty()) {
//                int x = heap.peek();
//                if (map.containsKey(x)) {
//                    map.put(x, map.get(x) - 1);
//                    if (map.get(x) == 0) {
//                        map.remove(x);
//                    }
//                    heap.poll();
//                }else{
//                    break;
//                }
//            }
//        }
//
//        private void balance(){
//            if (leftSize > left) {
//                sum -= pq1.peek();
//                pq2.offer(pq1.poll());
//                leftSize--;
//                rightSize++;
//                prune(pq1);
//            } else if (leftSize < left && rightSize > 0) {
//                sum += pq2.peek();
//                pq1.offer(pq2.poll());
//                leftSize++;
//                rightSize--;
//                left++;
//                prune(pq2);
//            }
//        }
//    }
}
