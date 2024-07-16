class Main{
    public static void main(String[] args) {
        System.out.println(xorN(200));
        System.out.println(xorN(100));
        System.out.println(xorN(200) ^ xorN(100));
        int ans = 0;
        for (int i = 101; i <= 200; i++) {
            ans ^= i;
        }
        System.out.println(ans);
    }

    private static int xorN(int n) {
        switch (n % 4) {
            case 0 :
                return n;
            case 1 :
                return 1;
            case 2 :
                return n + 1;
            default :
                return 0;
        }
    }
}