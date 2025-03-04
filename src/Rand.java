public class Rand {
    public static void main(String[] args) {
        System.out.println(10);
        for (int i = 0; i < 10; i++) {
            System.out.println(generate(0, (int) 1e9));
        }
    }
    public static int generate(int l, int r) {
        if (l > r) {
            throw new IllegalArgumentException("leftBound > rightBound");
        }
        return (int) (Math.random() * (r - l + 1)) + l;
    }
    
}
