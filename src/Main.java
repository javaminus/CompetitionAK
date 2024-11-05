import java.util.*;
public class Main{
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        int t=sc.nextInt();
        for(int i=0;i<t;i++){
            int n=sc.nextInt(),k=sc.nextInt(),next[]=new int[n],start=0;
            List<Integer> idx[]=new List[26];
            for(int j=0;j<26;j++){
                idx[j]=new ArrayList<>();
            }
            Arrays.fill(next,-1);
            String s=sc.next();
            for(int j=n-1;j>=0;j--){
                if(j<n-1){
                    next[j]=s.charAt(j)==s.charAt(j+1)?next[j+1]:j+1;
                }
                idx[s.charAt(j)-'a'].add(j);
            }
            for(;idx[start].size()<k;k-=idx[start].size(),start++){

            }
            Deque<Integer> deque=new ArrayDeque<>();
            for(int a:idx[start]){
                if(next[a]==-1||s.charAt(a)<s.charAt(next[a])){
                    deque.addLast(a);
                }
                else{
                    deque.addFirst(a);
                }
            }
            for(int j=0;j<k-1;j++){
                deque.removeFirst();
            }
            int ans=deque.removeFirst();
            System.out.println(s.charAt(ans)+s.substring(0,ans)+s.substring(ans+1));
        }
    }
}