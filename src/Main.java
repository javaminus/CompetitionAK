import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> tmp = new ArrayList<>();
        for (int i = 5; i < 20; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < i; j++) {
                sb.append(9);
            }
            for (int j = 101; j < 999; j++) {
                if (check(Integer.toString(j))) {
                    sb.append(j);
                    break;
                }
            }
            for (int j = 0; j < i; j++) {
                sb.append(9);
            }
            if (new BigInteger(sb.toString()).mod(new BigInteger("6")).equals(new BigInteger("0"))) {
                tmp.add(sb.toString());
            }
        }
        Collections.sort(tmp);
        for (int i = 1; i < tmp.size(); i++) {
            String s = tmp.get(i);
            if (s.length() != tmp.get(i - 1).length()) {
                System.out.println(tmp.get(i - 1));
            }
        }
    }

    private static boolean check(String s) {
        int n = s.length();
        for (int i = 0; i < n / 2; i++) {
            if (s.charAt(i) != s.charAt(n - 1 - i)) {
                return false;
            }
        }
        return true;
    }
}
