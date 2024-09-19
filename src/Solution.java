import java.util.HashMap;
import java.util.PriorityQueue;

class Solution {
    public double[] medianSlidingWindow(int[] nums, int k) {
        return null;
    }

    class dHeap{
        // 对顶堆
        PriorityQueue<Integer> small;
        PriorityQueue<Integer> large;
        HashMap<Integer, Integer> delayed;
        int n;
        int smallSize, largeSize;

        public dHeap(int n) {
            small = new PriorityQueue<>((a, b) -> b - a);
            large = new PriorityQueue<>();
            delayed = new HashMap<>();
            this.n = n;
            smallSize = 0;
            largeSize = 0;
        }



    }
}