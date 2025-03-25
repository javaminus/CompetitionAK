import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
public class Standard {
    static long ans=0;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out=new PrintWriter(System.out);
        int n=Integer.parseInt(br.readLine());
        while (n-->0){
            if(check(br.readLine())){
                System.out.println("Yes");
            }
            else{
                System.out.println("No");
            }
        }
        out.flush();
        out.close();
    }
    public static  boolean check(String s){
        int min=Integer.MAX_VALUE,max=Integer.MIN_VALUE;
        int n=s.length();
        char[] c=s.toCharArray();
        for (int i = 0; i < s.length(); i++) {
            if(c[i]!='l'&&c[i]!='q'&&c[i]!='b'){
                min=Math.min(min,i);
                max=Math.max(max,i);
            }
        }
        //如果没有不等于的
        if(min==Integer.MIN_VALUE) return true;
        //如果中间不是回文串
        if(!isReverse(c,min,max))return false;
        //左侧多于右侧
        if(min+1>n-max) return false;
        //如果右侧多于左侧
        //判断这里是否形成回文
        for (int i = 0; i < min; i++) {
            if(c[i]!=c[max+min-1-i]) return false;
        }
        return true;
    }
    public  static boolean isReverse(char[] c,int l,int r){
        while(l<r){
            if(c[l]!=c[r]) return false;
            l++;
            r--;
        }
        return true;
    }
}