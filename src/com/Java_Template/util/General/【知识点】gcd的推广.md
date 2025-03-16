![1742139336385](assets/1742139336385.png)

```java
    private static void solve() throws IOException {
        int n = sc.nextInt(), m = sc.nextInt();
        long[] a = new long[n];
        long[] b = new long[m];
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(ss[i]);
        }
        ss = sc.nextLine().split(" ");
        for (int i = 0; i < m; i++) {
            b[i] = Long.parseLong(ss[i]);
        }
        Arrays.sort(a);
        StringBuilder res = new StringBuilder();
        long g1 = 0;
        for (int i = 1; i < n; i++) {
            g1 = gcd(g1, a[i] - a[i - 1]);
        }
        for (int i = 0; i < m; i++) {
            long g = gcd(g1, a[0] + b[i]);
            res.append(g).append(" ");
        }
        res.deleteCharAt(res.length() - 1);
        sc.println(res.toString());
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
```



![1742141234569](assets/1742141234569.png)

```java
`public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        for (int i = 1; i <= n; ++i) {
            a[i] = read(br);
        }
        for (int i = n; i >= 1; --i) { // gcd的后缀数组
            b[i] = gcd(b[i + 1], a[i]);
        }
        for (int i = 1; i <= n; ++i) {
            ans = gcd(ans, a[i] * b[i + 1]);
        }
        System.out.println(ans / b[1]);
    }

    private static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
```

