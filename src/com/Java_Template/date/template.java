/*
package com.Java_Template.date;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

*/
/**
 * @author Minus
 * @date 2024/4/10 10:09
 *//*

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
class 日期转星期 {
    */
/**
     SimpleDateFormat函数语法：

     G 年代标志符
     y 年
     M 月
     d 日
     h 时 在上午或下午 (1~12)
     H 时 在一天中 (0~23)
     m 分
     s 秒
     S 毫秒
     E 星期
     D 一年中的第几天
     F 一月中第几个星期几
     w 一年中第几个星期
     W 一月中第几个星期
     a 上午 / 下午 标记符
     k 时 在一天中 (1~24)
     K 时 在上午或下午 (0~11)
     z 时区
     G: 公元 时代，例如AD公元
     yy: 年的后2位
     yyyy: 完整年
     MM: 月，显示为1-12
     MMM: 月，显示为英文月份简写,如 Jan
     MMMM: 月，显示为英文月份全称，如 Janualy
     dd: 日，2位数表示，如02
     d: 日，1-2位显示，如 2
     EEE: 简写星期几，如Sun
     EEEE: 全写星期几，如Sunday
     aa: 上下午，AM/PM
     H: 时，24小时制，0-23
     K：时，12小时制，0-11
     m: 分，1-2位
     mm: 分，2位
     s: 秒，1-2位
     ss: 秒，2位
     S: 毫秒
     *//*

    public String dayOfTheWeek(int day, int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        Date time = calendar.getTime();
        SimpleDateFormat fomat = new SimpleDateFormat("EEEE");
        return fomat.format(time);
    }

    public String dayOfTheWeek1(int day, int month, int year) {
        String[] weeks = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
        int[] months = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        int days = (year - 1971) * 365 + day + (year - 1969) / 4;
        for (int i = 0; i < month - 1; i++) {
            days += months[i];
        }
        if ((year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) && month >= 3) {
            days += 1;
        }
        int idx = (days+3) % 7;
        return weeks[idx];
    }
}
*/
