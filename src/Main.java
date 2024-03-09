import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;
public class Main{
    public static void main(String args[]) throws IOException{
        Read sc = new Read();
        int n = sc.nextInt();
        int m = sc.nextInt();
        int[] steps = new int[m];
        for (int i = 0; i < m; i++) {
            steps[i] = sc.nextInt() % n;
        }
        boolean[] dp = new boolean[n];
        dp[0] = true;
        for (int i = 0; i < m; i++) {
            boolean[] arrived = new boolean[n];
            for (int j = 0; j < n; j++) {
                if (dp[j]) {
                    arrived[(j + steps[i]) % n] = true;
                    arrived[(j - steps[i] + n) % n] = true;
                }
            }
            dp = arrived;
        }
        System.out.println(dp[0] ? "YES" : "NO");
        sc.bw.flush();
        sc.bw.close();
    }

}
class Read{
    BufferedReader bf;
    StringTokenizer st;
    BufferedWriter bw;
    public Read(){
        bf=new BufferedReader(new InputStreamReader(System.in));
        st=new StringTokenizer("");
        bw=new BufferedWriter(new OutputStreamWriter(System.out));
        //什么时候才能持续稳定ak力扣、AcWing呢？
        //什么时候才能ak cf的div2，div1呢？才能打div2不计rating呢？
        //什么时候才能ak  abc  不计rating呢？当然arc不计分更好
        //什么时候才能ak 牛客练习赛不计rating呢?
    }
    public String nextLine() throws IOException{
        return bf.readLine();
    }
    public String next() throws IOException{
        while(!st.hasMoreTokens()){
            st=new StringTokenizer(bf.readLine());
        }
        return st.nextToken();
    }
    public char nextChar() throws IOException{
        //确定下一个token只有一个字符的时候再用
        return next().charAt(0);
    }
    public int nextInt() throws IOException{
        return Integer.parseInt(next());
    }
    public long nextLong() throws IOException{
        return Long.parseLong(next());
    }
    public double nextDouble() throws IOException{
        return Double.parseDouble(next());
    }
    public float nextFloat() throws IOException{
        return Float.parseFloat(next());
    }
    public byte nextByte() throws IOException{
        return Byte.parseByte(next());
    }
    public short nextShort() throws IOException{
        return Short.parseShort(next());
    }
    public BigInteger nextBigInteger() throws IOException{
        return new BigInteger(next());
    }
    public void println(int a) throws IOException{
        bw.write(String.valueOf(a));
        bw.newLine();
        return;
    }
    public void print(int a) throws IOException{
        bw.write(String.valueOf(a));
        return;
    }
    public void println(String a) throws IOException{
        bw.write(a);
        bw.newLine();
        return;
    }
    public void print(String a) throws IOException{
        bw.write(a);
        return;
    }
    public void println(long a) throws IOException{
        bw.write(String.valueOf(a));
        bw.newLine();
        return;
    }
    public void print(long a) throws IOException{
        bw.write(String.valueOf(a));
        return;
    }
    public void println(double a) throws IOException{
        bw.write(String.valueOf(a));
        bw.newLine();
        return;
    }
    public void print(double a) throws IOException{
        bw.write(String.valueOf(a));
        return;
    }
    public void print(BigInteger a) throws IOException{
        bw.write(a.toString());
        return;
    }
    public void print(char a) throws IOException{
        bw.write(String.valueOf(a));
        return;
    }
    public void println(char a) throws IOException{
        bw.write(String.valueOf(a));
        bw.newLine();
        return;
    }
}