import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    private final static int INF = Integer.MAX_VALUE / 2;
    private final static int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    static class Read {
        BufferedReader bf;
        StringTokenizer st;
        BufferedWriter bw;

        public Read() {
            bf = new BufferedReader(new InputStreamReader(System.in));
            st = new StringTokenizer("");
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        public String nextLine() throws IOException {
            return bf.readLine();
        }

        public String next() throws IOException {
            while (!st.hasMoreTokens()) {
                st = new StringTokenizer(bf.readLine());
            }
            return st.nextToken();
        }

        public char nextChar() throws IOException {
            return next().charAt(0);
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        public double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        public float nextFloat() throws IOException {
            return Float.parseFloat(next());
        }

        public byte nextByte() throws IOException {
            return Byte.parseByte(next());
        }

        public short nextShort() throws IOException {
            return Short.parseShort(next());
        }

        public BigInteger nextBigInteger() throws IOException {
            return new BigInteger(next());
        }

        public void println(int a) throws IOException {
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }

        public void print(int a) throws IOException {
            bw.write(String.valueOf(a));
            return;
        }

        public void println(String a) throws IOException {
            bw.write(a);
            bw.newLine();
            return;
        }

        public void print(String a) throws IOException {
            bw.write(a);
            return;
        }

        public void println(long a) throws IOException {
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }

        public void print(long a) throws IOException {
            bw.write(String.valueOf(a));
            return;
        }

        public void println(double a) throws IOException {
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }

        public void print(double a) throws IOException {
            bw.write(String.valueOf(a));
            return;
        }

        public void print(BigInteger a) throws IOException {
            bw.write(a.toString());
            return;
        }

        public void print(char a) throws IOException {
            bw.write(String.valueOf(a));
            return;
        }

        public void println(char a) throws IOException {
            bw.write(String.valueOf(a));
            bw.newLine();
            return;
        }
    }

    static class Pair<T, U> {
        T fir;
        U sec;
        public Pair(T fir, U sec) {
            this.fir = fir;
            this.sec = sec;
        }
    }

    static Read sc = new Read();
    private static final int Mod = (int) 1e9 + 7;
    private static int T = 1;

    public static void main(String[] args) throws IOException {
        int T = sc.nextInt();
        while (T-- > 0) {
            solve();
            // sc.bw.flush();
        }
        sc.bw.flush();
        sc.bw.close();
    }

    private static String[] ss;
    private static String s;
    private static char[] cs;
    private static List<Integer>[] g;
    private static int m, n;


    private static void solve() throws IOException {
        n = sc.nextInt();
        int k = sc.nextInt();
        s = sc.next();
        cs = s.toCharArray();
        int[] cnt = new int[26];
        for (char c : cs) {
            cnt[c - 'a']++;
        }
        char c = 'a';
        int d = 0;
        for (int i = 0; i < 26; i++) {
            if (d + cnt[i] >= k) {
                c = (char) (i + 'a');
                break;
            }
            d += cnt[i];
        }
        k -= d;
        ArrayList<Integer> idx = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (cs[i] == c) {
                idx.add(i);
            }
        }
        int[] right = new int[n];
        for (int i = 0, j = 0; i < n; i = j) {
            while (j < n && cs[i] == cs[j]) {
                j++;
            }
            if (cs[i] == c) {
                for (int l = i; l < j; l++) {
                    right[l] = j;
                }
            }
        }
        System.out.println(Arrays.toString(right));
        idx.sort((i,j)->{
            int a = Math.min(i, j), b = Math.max(i, j);
            if (right[a] == n) {
                return 0;
            }
            if (cs[right[a]] < cs[b]) {
                return a == i ? -1 : 1;
            } else if (cs[right[a]] > cs[b]) {
                return a == i ? 1 : -1;
            }
            return 0;
        });
        int id = idx.get(k - 1);
        sc.println("" + cs[id] + s.substring(0, id) + s.substring(id + 1));
    }


}