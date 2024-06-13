import java.util.HashMap;

class Solution {
    public int maximumLength(int[] nums, int k) { // 最多允许k个不同的相邻元素的子序列
        // 首先我们考虑到最多是k个不同相邻元素组成的子序列
        // 所以我们可以用hash表的key存储每个元素，然后value = new int[k+1]存储以元素key结尾的，至多包含 j 个不同相邻元素的子序列的最大长度
        // 使用一个records存储答案，new int[k+1][3]
        HashMap<Integer, int[]> fs = new HashMap<>();
        int[][] records = new int[k + 1][3];
        for (int x : nums) {
            int[] f = fs.computeIfAbsent(x, i -> new int[k + 1]);
            for (int j = k; j >= 0; j--) {
                f[j]++;
                if (j > 0) {
                    int mx = records[j - 1][0], mx2 = records[j - 1][1], num = records[j - 1][2];
                    f[j] = Math.max(f[j], (x == num ? mx2 : mx) + 1);
                }
                int v = f[j];
                int[] p = records[j];
                if (v > p[0]) {
                    if (x != p[2]) {
                        p[2] = x;
                        p[1] = p[0];
                    }
                    p[0] = v;
                }else if (x != p[2] && v > p[1]) {
                    p[1] = v;
                }
            }
        }
        return records[k][0];
    }
}