public class Rand {
    public static void main(String[] args) {
        System.out.println(5);
        for (int i = 0; i < 5; i++) {
            int n = generate(2, 10);
            System.out.println(n);
            printArr(n, 2, n);
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
