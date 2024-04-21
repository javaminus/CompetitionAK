class Solution {
    // a为原来的数组，c为维护的区间最值的数组
    int[] a,c;
    int n = (int) 1e5+10;

    public int lowbit(int x) {
        return x & (-x);
    }

    public void add(int x, int y) {
        update(x);
    }

    public void update(int x) {
        while (x <= n) {
            c[x] = a[x];
            int lx = lowbit(x);
            for (int i = 1; i < lx; i <<= 1) {
                c[x] = Math.max(c[x], c[x-i]);
            }
            x += lowbit(x);
        }
    }

    public int query(int x, int y) {
        int ans = 0;
        while (x <= y) {
            ans = Math.max(ans, a[y]);
            y--;
            for(; y - lowbit(y) >= x; y -= lowbit(y)) {
                ans = Math.max(ans, c[y]);
            }
        }
        return ans;
    }

    public int lengthOfLIS(int[] nums, int k) {
        this.a = new int[n + 1];
        this.c = new int[n + 1];
        int res = 0;
        for (int num : nums) {
            int lower = Math.max(1, num - k);
            // 找到lower～(num-1)范围的最大值
            int higher = query(lower, num - 1);
            // 维护以num为结尾的最长值
            a[num] = higher + 1;
            // 将对应值更新到树状数组中
            update(num);
            res = Math.max(res, higher + 1);
        }
        return res;
    }
}