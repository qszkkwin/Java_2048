package com.qsz.demo2;

/**
 * 2048算法练习：相邻元素合并
 *
 * @author 漆胜之
 * @since 2025/11/17
 */
public class Demo2 {

    public static void main(String[] args) {
        int[] arr = {2, 2, 4, 0};
        int len = arr.length;

        // 相邻相同元素合并：前一个翻倍，后续元素前移
        for (int x = 0; x < len - 1; x++) {
            if (arr[x] != 0 && arr[x] == arr[x + 1]) {
                arr[x] *= 2;
                for (int y = x + 1; y < len - 1; y++) {
                    arr[y] = arr[y + 1];
                }
                arr[len - 1] = 0;
            }
        }

        for (int i = 0; i < len; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
