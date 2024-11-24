> 这个题的IO给我狠狠的上了一课
>
> ```java
> // static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));  // 过80%用例
> // static Scanner sc = new Scanner(System.in); // 过70%用例
> // 使用IO包里面的读入，输出，直接过，1368ms （2000ms的限制）
> ```

```java
import java.io.*;
import java.math.BigInteger;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Read sc = new Read();
        int n = sc.nextInt();
        TreeMap<Long, Long> cnt = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            long x = sc.nextInt();
            cnt.put(x, cnt.getOrDefault(x, 0L) + 1);
        }
        long x = sc.nextInt();
        long ans = cnt.getOrDefault(x, 0L);
        TreeMap<Long, Long> cnt1 = new TreeMap<>();
        while (true) {
            // 播种
            if (cnt.size() != 0) {
                for (Long key : cnt.keySet()) {
                    long p = (key - 1) / 3 + 1;
                    long v = cnt.get(key);
                    cnt1.put(p, cnt1.getOrDefault(p, 0L) + v * 2);
                }
                long mx = (long) cnt.getOrDefault(x, 0L);
                if (x > cnt.lastKey()) {
                    break;
                }
                ans = Math.max(mx, ans);
                cnt.clear();
            }else {
                for (Long key : cnt1.keySet()) {
                    long p = (key - 1) / 3 + 1;
                    long v = cnt1.get(key);
                    cnt.put(p, cnt.getOrDefault(p, 0L) + v * 2);
                }
                long mx = (long) cnt1.getOrDefault(x, 0L);
                if (x > cnt1.lastKey()) {
                    break;
                }
                ans = Math.max(mx, ans);
                cnt1.clear();
            }
        }
        // System.out.println(ans);
        sc.println(ans);
        //sc.print(0);
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

```

