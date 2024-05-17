import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<Integer> replaceNonCoprimes(int[] nums) {
        ArrayList<Integer> s = new ArrayList<Integer>();
        for (int num : nums) {
            s.add(num);
            while (s.size() > 1) {
                int x = s.get(s.size() - 1);
                int y = s.get(s.size() - 2);
                int g = gcd(x, y);
                if (g == 1) break;
                s.remove(s.size() - 1);
                s.set(s.size() - 1, x / g * y);
            }
        }
        return s;
    }

    int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
