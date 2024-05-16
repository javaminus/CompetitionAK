class Solution {
    public int mirrorReflection(int p, int q) {
        int m = p * q / gcd(p, q);
        int x = m / q;
        int y = m / p;
        if (x % 2 == 1 && y % 2 == 1) {
            return 1;
        }
        return x % 2 == 1 ? 0 : 2;
    }
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}