package com.howliked.mashibing.algorithm.class08;

import java.util.Random;

/**
 * 计数排序【非基于比较的排序】
 * 一般来说，计数排序要求样本是整数，且范转比较较窄（因为涉及到桶的数量）
 */
public class Code03_CountSort {
    public static void main(String[] args) {
        int[] array = new int[]{23, 23, 24, 20, 25, 18, 46, 36, 34, 35};
        for (int i = 0; i < array.length; i++) {
            array[i] = new Random().nextInt(100);
            System.out.print(array[i] + " ");
        }
        System.out.println();
        countSort(array);
        printArray(array);
    }

    public static void countSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int max = Integer.MIN_VALUE;
        for (int k : arr) {
            max = Math.max(max, k);
        }

        // 因为辅助数组是限定最大值的范围，且由于下标从 0 开始，所以长度要 max + 1
        int[] bucket = new int[max + 1];
        for (int k : arr) {
            // 所有数组元素的值必是辅助数组的下标，所以 arr[i] 的计数 + 1
            // 经过这个统计之后，辅助数组中下标值（即要排序的数组元素）即为有序的
            bucket[k]++;
        }

        int j = 0; // 对于把原数组从第 1 元素开始从小到大排列
        // 把辅助数组从下标 0 开始遍历，把那些值不为 0 的下标依次放入原数组中。要注意的是每放一次对应值要自减 1
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- > 0) {
                arr[j++] = i;
            }
        }
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
}
