package com.songw;

import java.util.Arrays;

public class Demo01 {
    private static int multipleNumbers=0;//重数
    private static String mode;//众数
    private static int medianNumber;//中位数
    private static String [] element={"1","2","2","2","3","5"};
    private static void a(int start,int end){
        int mid=(start+end)/2;
        for(int i=start;i<end;i++){
            if(element[i].equals(element[mid])){
                medianNumber++;
                if(medianNumber>multipleNumbers){
                    multipleNumbers=medianNumber;
                    mode=element[mid];
                }
            }
        }
        if(mid>medianNumber){
            medianNumber=0;
            a(0,mid);
        }
        if(end-mid-1>medianNumber){
            medianNumber=0;
            a(mid+1,end);
        }

    }
    public static void main(String[] args) {
        a(0,element.length);
        System.out.println("数组为:"+Arrays.toString(element));
        System.out.println("重数: "+multipleNumbers);
        System.out.println(" 众数："+mode);
    }
}