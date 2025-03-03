import java.io.*;
import java.math.*;
public class Rand{
	public static void main(String[] args){
		int x = generate(0, 100);
		System.out.println(x + " " + x);
	}

	public static int generate(int l, int r) {
		if (l > r) {
			throw new IllegalArgumentException("leftBound > rightBound");
		}
		return (int) (Math.random() * (r - l + 1)) + l;
	}
}