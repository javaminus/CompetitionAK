import java.util.PriorityQueue;

class Solution {
    public String longestDiverseString(int a, int b, int c) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> y[1] - x[1]);
        if (a > 0) {
            pq.offer(new int[]{0, a});
        }
        if (b > 0) {
            pq.offer(new int[]{1, b});
        }
        if (c > 0) {
            pq.offer(new int[]{2, c});
        }
        StringBuilder sb = new StringBuilder();
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int n = sb.length();
            if (n >= 2 && sb.charAt(n - 1) == 'a' + cur[0] && sb.charAt(n - 2) == 'a' + cur[0]) {
                if (pq.isEmpty()) {
                    break;
                }
                int[] next = pq.poll();
                sb.append((char) (next[0] + 'a'));
                if (--next[1] != 0) {
                    pq.offer(next);
                }
                pq.add(cur);
            }else{
                sb.append((char) (cur[0] + 'a'));
                if (--cur[1] != 0) {
                    pq.offer(cur);
                }
            }
        }
        return sb.toString();
    }
}