import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    static int n;
    static List<Integer> cnt = new ArrayList<>();
    static boolean[] visited = new boolean[n];
    static int c = 0;
    static List<Integer>[] g;

    public static void main(String[] args) {
        n = sc.nextInt();
        g = new ArrayList[n];
        Arrays.setAll(g, e -> new ArrayList<>());
        while (sc.hasNext()) {
            int x = sc.nextInt(), y = sc.nextInt();
            g[x].add(y);
            g[y].add(x);
        }
        for (int i = 0; i < n; i++) {
            c = 0;
            if (!visited[i]) {
                dfs(i, -1);
            }
            cnt.add(c);
        }


    }

    private static void dfs(int x, int fa) {
        visited[x] = true;
        c++;
        for (int y : g[x]) {
            if (y != fa && !visited[y]) {
                dfs(y, x);
            }
        }
    }
}