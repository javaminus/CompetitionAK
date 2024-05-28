```java
package com.Java_Template.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "F:\\leetcode\\src\\com\\Java_Template\\file\\test.txt"; // 指定文件名
        File file = new File(fileName);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()){
            String s = sc.next();
            System.out.println(s);
        }
    }
}

```

