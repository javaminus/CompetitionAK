package com.clone;

import java.io.IOException;

/**
 * @author Minus
 * @date 2024/4/2 14:46
 */
public class Main {
    public static void main(String[] args) throws CloneNotSupportedException, IOException, ClassNotFoundException {
        Zoo zoo1 = new Zoo(1, "动物园1");
        Animal[] animals1 = new Animal[] { new Animal("老虎"), new Animal("狮子") };
        zoo1.setAnimals(animals1);

        Zoo zoo2 = new Zoo();

        System.out.println(zoo1);
        System.out.println(zoo2);
        System.out.println("----------------------");

        zoo2 = (Zoo) zoo1.deepClone();

        System.out.println(zoo1);
        System.out.println(zoo2);
        System.out.println("----------------------");

        zoo1.setName("动物园10");// 动物园1改名了
        zoo1.setNumber(10);
        System.out.println(zoo1);
        System.out.println(zoo2);
        System.out.println("----------------------");

        zoo1.getAnimals()[0] = new Animal("熊猫"); // 原动物园1将老虎换为了熊猫
        System.out.println(zoo1);
        System.out.println(zoo2);
    }
}
