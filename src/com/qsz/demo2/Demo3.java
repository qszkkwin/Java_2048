package com.qsz.demo2;

/**
 * 2048算法练习：二维数组向左移动（零后置 + 合并）
 *
 * @author 漆胜之
 * @since 2025/11/17
 */
public class Demo3 {

    public static void main(String[] args) {
        int[][] data = {
                {0, 2, 2, 4},
                {2, 2, 4, 4},
                {0, 8, 2, 4},
                {0, 32, 0, 64}
        };
        int colCount = data[0].length;

        for (int i = 0; i < data.length; i++) {
            // 第一步：去零，非零元素前移
            int[] compacted = new int[colCount];
            int index = 0;
            for (int x = 0; x < colCount; x++) {
                if (data[i][x] != 0) {
                    compacted[index++] = data[i][x];
                }
            }

            // 第二步：合并相邻相同元素
            for (int x = 0; x < colCount - 1; x++) {
                if (compacted[x] != 0 && compacted[x] == compacted[x + 1]) {
                    compacted[x] *= 2;
                    for (int j = x + 1; j < colCount - 1; j++) {
                        compacted[j] = compacted[j + 1];
                    }
                    compacted[colCount - 1] = 0;
                }
            }
            data[i] = compacted;
        }

        // 打印结果
        for (int[] row : data) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
