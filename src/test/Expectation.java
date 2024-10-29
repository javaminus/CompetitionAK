package test;

public class Expectation {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3,4,5};
        System.out.println("期望值为： " + calculateExpectation(arr));
    }

    public static double calculateExpectation(int[] arr) {
        int n = arr.length;
        double sum = 0;
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += arr[i] * arr[j];
                count++;
            }
        }

        return sum / count;
    }
}
