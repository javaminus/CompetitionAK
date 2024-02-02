package test;

public class BinaryConversion {
    public static void main(String[] args) {
        int number = 42; // 例子中的整数

        // 将整数转换为二进制字符串
        String binaryString = Integer.toBinaryString(number);

        System.out.println("Decimal: " + number);
        System.out.println("Binary: " + binaryString);
    }
}
