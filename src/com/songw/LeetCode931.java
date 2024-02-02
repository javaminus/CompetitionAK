package com.songw;

/**
 * @author Minus
 * @date 2023/4/2 10:54
 */

/**
 * replace(oldStr,newStr);
 *
 * replaceAll(oldStr,newStr);
 *
 * 相同点：是将一个字符串的所有oldStr都替换成newStr
 *
 * 不同点：replace的oldStr只能是字符和字符串，replaceAll指的是正则表达式，比如'\r'回车 '\n'空格
 *
 * 好文要顶 关注我 收藏该文
 */
public class LeetCode931 {
    public static void main(String[] args) {
        LeetCode931 l = new LeetCode931();
        l.maskPII("1(234)567-890");
    }

    String[] county={"","+*-","+**-","+***-"};
    public String maskPII(String s) {
        int at = s.indexOf("@");
        if (at > 0) {
            s = s.toLowerCase();
            return (s.charAt(0) + "*****" + s.substring(at - 1)).toLowerCase();
        }
        s = s.replaceAll("[^0-9]", "");
        System.out.println(s);
        return county[s.length() - 10] + "***-***-" + s.substring(s.length() - 4);
    }
}
