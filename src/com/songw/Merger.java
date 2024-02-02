package com.songw;

import org.junit.Test;
/**
 * @author Minus
 * 合并排序
 */
public class Merger {
    @Test
    public void Test() {
        int A[] = new int[]{2,6,9,26,27,1,25,63};
        getMergesort(A);
        System.out.println("使用合并排序获得A数组的非降序序列结果如下：");
        for(int i = 0;i < A.length;i++) {
            System.out.print(A[i]+",");
        }
    }

    /**
     * 使用合并排序，获取数组A的非降序排列
     */
    public static void getMergesort(int[] A){
        //数组A的长度
        int lenA = A.length;
        if(lenA > 1){
            //获取数组A中前一半元素
            int[] B = copyArray(A,0);
            //获取数组A中后一半元素
            int[] C = copyArray(A,1);
            //递归排序B中元素
            getMergesort(B);
            //递归排序C中元素
            getMergesort(C);
            //合并数组B和C，返回A的非降序序列
            Merge(B,C,A);
        }
    }


    public static int[] copyArray(int[] A,int a){
        int[] result;
        int len = A.length;
        //当a为0时代表返回数组A的前一半元素
        if(a == 0){
            result = new int[len/2];
            for(int i = 0;i < len/2;i++) {
                result[i] = A[i];
            }
        }
        else{
            //a不为0时代表返回数组A的后一半元素
            result = new int[len-len/2];
            for(int i = 0;i < (len-len/2);i++)
                result[i] = A[len/2+i];
        }
        return result;
    }


    public static void Merge(int[] B,int[] C,int[] A){
        int i = 0,j = 0,k = 0;
        int lenB = B.length;
        int lenC = C.length;
        while(i<lenB && j<lenC){
            if(B[i] < C[j]){
                A[k] = B[i];
                i++;
            }
            else{
                A[k] = C[j];
                j++;
            }
            k++;
        }
        if(i == lenB){
            while(j<lenC){
                A[k] = C[j];
                j++;
                k++;
            }
        }
        if(j == lenC){
            while(i<lenB){
                A[k] = B[i];
                i++;
                k++;
            }
        }
    }

}

