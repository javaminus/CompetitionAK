import java.io.*;
public class Main {
    static PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    static StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws Exception{
        //代码区
        pw.println();
        pw.flush();//使用快读快些模版最后必须刷新缓冲区
    }
    public static String nextLine() throws Exception {
        return bf.readLine();
    }
    public static int nextInt() throws Exception {
        st.nextToken();
        return (int) st.nval;
    }
    public static long nextLong() throws Exception {
        st.nextToken();
        return (long) st.nval;
    }
    public static double nextDouble() throws Exception {
        st.nextToken();
        return st.nval;
    }
}