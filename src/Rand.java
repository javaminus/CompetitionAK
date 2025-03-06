public class Rand {
    public static void main(String[] args) {
        int T = 1;
        System.out.println(T);
        for (int i = 0; i < T; i++) {
            int n = generate(5, 20);
            int k = generate(1, n - 1);
            int z = generate(0, Math.min(5, k));
            System.out.println(n + " " + k + " " + z);
            printArr(n, 0, 50);
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
