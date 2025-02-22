package com.Java_Template.date;

import java.time.LocalDate;
 

public class template {

}
class Main1{
    public static void main(String[] args) {
        LocalDate from = LocalDate.of(1900, 1, 1);
        LocalDate to = LocalDate.of(9999, 12, 31);
        long ans = 0;
        for (LocalDate i = from; i.compareTo(to) <= 0; i = i.plusDays(1)) {
            int y = i.getYear();
            int m = i.getMonthValue();
            int d = i.getDayOfMonth();
            int a = 0, b = 0;
            while (y > 0) {
                a += y % 10;
                y /= 10;
            }
            while (m > 0) {
                b += m % 10;
                m /= 10;
            }
            while (d > 0) {
                b += d % 10;
                d /= 10;
            }
            if (a == b) {
                System.out.println(i.getYear() + " " + i.getMonthValue() + " " + i.getDayOfMonth());
                ans++;
            }
        }
        System.out.println(ans);
    }
}
