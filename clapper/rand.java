public class Rand {
    public static void main(String[] args) {
        int T = 10;
        System.out.println(T);
        for (int i = 0; i < T; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 15; j++) {
                sb.append((char) ('a' + (int) (Math.random() * 26)));
            }
            System.out.println(sb.toString());
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
