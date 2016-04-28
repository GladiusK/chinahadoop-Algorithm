package com.yx.chinahadoop;

/**
 * 不用递归的方式进行全排列
 * @author yuxuan
 * @github https://github.com/GladiusK/chinahadoop-Algorithm
 */
public class NorecurPermutation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {3,2,1};
		Print(arr);
		while(getNextPermutation(arr)){
			Print(arr);
		}
	}
	
	/**
	 * 从 start 到 end 反转数组元素
	 * @param arr
	 * @param start
	 * @param end
	 */
	public static void reverse(int[] arr, int start, int end){
		int tmp;
		while(end > start){
			tmp = arr[start];
			arr[start++] = arr[end];
			arr[end--] = tmp;
		}
	}
	
	/**
	 * 交换数组中位置 i 和 j 元素的值
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static void swap(int[] arr, int i, int j){
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	/**
	 * 辅助打印数组元素
	 * @param arr
	 */
	public static void Print(int[] arr){
		int i = 0;
		while(i < arr.length){
			System.out.print(arr[i++]);
		}
		System.out.println();
	}
	
	/**
	 * 得到下一个全排列
	 *   1－从后往前找，找到第一个不符合降序排列的元素的位置
	 *   2－找到 从［1］中获取的位置 后面所有元素中最小的一个元素的位置
	 *   3-交换 ［1］和［2］的位置 （此后 得到一个 从［1］+1 位置 到结束位置 的降序数字组合)
	 *   4-反转数组，从［1］中元素位置的后一位 开始，数组最后一个元素结束 (此后 得到一个 [1] +1 位置 到结束位置 的升序数字组合)
	 *   
	 * 说明： 该算法中数组元素必须为一个升序排列的数组，
	 *       算法的核心思想为，找到一个最小的 比当前 数字组合 大的数字组合。
	 * eg:  123 => 132 => 213 => 231 => 312 => 321
	 * 
	 * 反转数组的目的是为了 保证下一个全排列的数组 是以 从某个元素位置开始，之后数字为升序的 数组。
	 * @param arr
	 * @return
	 */
	public static boolean getNextPermutation(int[] arr){
		int i = arr.length -2;
		// 后找
		while(i >= 0 && arr[i] >= arr[i+1]){
			i--;
		}
		if(i < 0){
			return false;
		}
		
		int j = arr.length -1;
		// 小大
		while(arr[j] <= arr[i]){
			j--;
		}
		
		// 交换
		swap(arr, i, j);
		// 反转
		reverse(arr,i+1, arr.length -1);
		return true;
	}

}
