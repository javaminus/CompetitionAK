import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        Read scanner = new Read();
        int T = scanner.nextInt();
        while (T > 0) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            int[] nums = new int[n];
            for (int i = 0; i < n; i++) {
                nums[i] = scanner.nextInt();
            }
            int[][] list = new int[m][2];
            for (int i = 0; i < m; i++) {
                list[i][0] = scanner.nextInt();
                list[i][1] = scanner.nextInt();
            }
            HashMap<Integer, Integer> map = new HashMap<>();
            for (int i = m - 1; i >= 0; i--) {
                if (map.containsKey(list[i][1])) {
                    map.put(list[i][0], map.get(list[i][1]));
                }else{
                    map.put(list[i][0], list[i][1]);
                }
            }
            for (int i = 0; i < n; i++) {
                if (map.containsKey(nums[i])) {
                    System.out.print(map.get(nums[i]) + " ");
                }else{
                    System.out.print(nums[i] + " ");
                }
            }
            System.out.println();
            T--;
        }
        scanner.bw.flush();
        scanner.bw.close();
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
        //什么时候才能ak cf的div2，（div1）呢？才能打div2不计rating呢？
        //什么时候才能ak  abc  arc 不计rating呢？
        //什么时候才能ak 牛客练习赛不计rating呢?
        //什么时候才能ak 洛谷的div2呢？才能打div2不计rating呢？
    }
    public String nextLine() throws IOException {
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
    public void println() throws IOException{
        bw.newLine();
        return;
    }
    public boolean hasNext() throws IOException{
        //本地普通IDE难以使用这个方法调试，需要按照数据组flush，刷新语句:
        //sc.bw.flush()
        //调试完可删去
        return bf.ready();
    }
}
