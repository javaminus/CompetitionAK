import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class e {

	static class Read {
		BufferedReader bf;
		StringTokenizer st;
		BufferedWriter bw;

		public Read() {
			bf = new BufferedReader(new InputStreamReader(System.in));
			st = new StringTokenizer("");
			bw = new BufferedWriter(new OutputStreamWriter(System.out));
		}

		public String nextLine() throws IOException {
			return bf.readLine();
		}

		public String next() throws IOException {
			while (!st.hasMoreTokens()) {
				st = new StringTokenizer(bf.readLine());
			}
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public void print(String s) throws IOException {
			sc.bw.write(s);
		}

	}

	static Read sc = new Read();
	static int T = 1;

	public static void main(String[] args) throws IOException {
		T = sc.nextInt();
		while (T-- > 0) {
			solve();
		}
		sc.bw.flush();
		sc.bw.close();
	}

	static String[] ss;

	public static void solve() throws IOException {
		long n = Long.parseLong(sc.next());
		int k = sc.nextInt();
		s = Long.toBinaryString(n);
		len = s.length();
		pow2 = new long[len];
		for(int i = 0;i<len;i++) {
			pow2[i] = qpow(2, len - i - 1, Mod);
		}
		memo = new HashMap<>();
		sc.print(dfs(0, 0, 1, n, k)[1]+"\n");
		
	}
	
	static long qpow(long a, int b, long mod) {
        long res = 1;
        a = a % mod;
        while(b > 0) {
            if ((b & 1) == 1) {
                res = (res * a) % mod;
            }
            a = (a * a) % mod;
            b >>= 1;
        }
        return res;
    }
	
	static int len;
	static String s;
	static long[] pow2;
	static HashMap<String, long[]> memo;
	
	static long[] dfs(int i, int cnt,int tight, long n, int k) {
		if(cnt>k) {
			return new long[] {0, 0};
		}
		if(i==len) {
			if(cnt==k) {
				return new long[] {1,0};
			}else {
				return new long[] {0, 0};
			}
		}
		String key = i+" "+cnt+" "+tight;
		if(memo.containsKey(key)) {
			return memo.get(key);
		}
		long[] res = new long[2];
		int limit = (tight==1?s.charAt(i) - '0':1);
		for(int j = 0;j<=limit;j++) {
			int nextTight = (tight==1 && j==limit?1:0);
			long[] temp = dfs(i+1, cnt+j, nextTight, n, k);
			if(temp[0]==0) {
				continue;
			}
			long c = (j==1?pow2[i]:0);
			res[0] = (res[0] + temp[0])%Mod;
			res[1] = (res[1] + c*temp[0]%Mod + temp[1])%Mod;
		}
		memo.put(key, res);
		return res;
	}
	
	static long Mod = 998244353;
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
