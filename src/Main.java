import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        while(T-- > 0){
            int n = Integer.parseInt(br.readLine());
            TreeMap<Long, Long> map = new TreeMap<>(Collections.reverseOrder());
            for(int i=0; i<n; i++){
                String[] parts = br.readLine().split(" ");
                long l = Long.parseLong(parts[0]);
                long a = Long.parseLong(parts[1]);
                map.put(l, map.getOrDefault(l, 0L) + a);
            }
            List<Long> lengths = new ArrayList<>(map.keySet());
            double res = -1;
            for (int i = 0; i < lengths.size(); i++) {
                long a = lengths.get(i);
                long countA = map.get(a);
                if (countA >= 2) {
                    if (countA >= 3) {
                        double area = f(a, a, a);
                        res = Math.max(res, area);
                    }
                    if (i + 1 < lengths.size()) {
                        long b = lengths.get(i + 1);
                        if (b < 2 * a) {
                            double area = f(a, a, b);
                            res = Math.max(res, area);
                        }
                    }
                }
            }
            if (res == -1) {
                System.out.println(-1);
            }else{
                System.out.println(res);
            }
        }
    }

    private static double f(long a, long a2, long b){
        if(4*a*a < b*b){
            return -1;
        }
        return (b / 4.0) * Math.sqrt(4.0*a*a - b*b);
    }
}