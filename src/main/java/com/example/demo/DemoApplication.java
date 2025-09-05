package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	public static void bubbleSort(int[] array) {
		// 边界条件判断
		if (array == null || array.length <= 1) {
			return;
		}
		int n = array.length;
		// 外层循环控制排序轮数
		for (int i = 0; i < n - 1; i++) {
			// 标记本轮是否发生交换，优化排序效率
			boolean swapped = false;

			// 内循环进行相邻元素比较和交换
			// 每轮结束后，最大的元素会"浮"到数组末尾，因此边界逐渐减小
			for (int j = 0; j < n - 1 - i; j++) {
				// 如果当前元素大于下一个元素，则交换它们
				if (array[j] > array[j + 1]) {
					int temp = array[j];
					array[i] = array[j];
					array[j + 1] = temp;
					swapped = true;
				}
			}

			// 如果本轮没有发生交换，说明数组已经有序，提前退出
			if (!swapped) {
				break;
			}
		}
	}

	public static Map<String,Object> convertMap(Map<String,String> map)
	{
		Map<String,Object> ret=new HashMap<>();
		map.forEach(new BiConsumer<String, String>() {
			@Override
			public void accept(String s, String s2) {
				Object o=s2;
				ret.put(s,o);
			}
		});
		return ret;
	}



}
