import java.io.*;

public class Main {
    static class Read {
        BufferedReader bf;
        BufferedWriter bw;
        public Read() {
            bf = new BufferedReader(new InputStreamReader(System.in));
            bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }
        public String nextLine() throws IOException {
            return bf.readLine();
        }

        public void print(String s) throws IOException {
            sc.bw.write(s);
        }
    }
    static Read sc = new Read();
    private static int T = 1;
    public static void main(String[] args) throws IOException {
        // int T = sc.nextInt();
        while (T-- > 0) {
            solve();
        }
        sc.bw.flush();
        sc.bw.close();
    }
    private static void solve() throws IOException {

    }
}