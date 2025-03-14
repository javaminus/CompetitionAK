public class Rand {
    public static void main(String[] args) {
        int T = 1;
        // System.out.println(T);
        for (int i = 0; i < T; i++) {
            int n = generate(5, 50);
            int k = generate(1, 100000);
            System.out.println(n + " " + k);
        }
    }
    public static int generate(int l, int r) {
        if (l > r) {
            throw new IllegalArgumentException("leftBound > rightBound");
        }
        return (int) (Math.random() * (r - l + 1)) + l;
    }

    public static void printArr(int n, int l, int r) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(generate(l, r) + " ");
        }
        System.out.println(sb.toString());
    }
    
}
