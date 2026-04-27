package com.qsz.demo2;

/**
 * 2048算法练习：数组零元素后置
 *
 * @author 漆胜之
 * @since 2025/11/3
 */
public class Demo1 {

    public static void main(String[] args) {
        int[] arr = {0, 2, 2, 4};

        // 将非零元素前移，等价的零自然被挤到末尾
        int[] newArr = new int[arr.length];
        int index = 0;
        for (int x = 0; x < arr.length; x++) {
            if (arr[x] != 0) {
                newArr[index] = arr[x];
                index++;
            }
        }
        arr = newArr;

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
