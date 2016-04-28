package com.yx.chinahadoop;

/**
 * 递归的方式进行 有重复和无重复的全排列
 * @author yuxuan
 * @github https://github.com/GladiusK/chinahadoop-Algorithm
 */
public class Permutation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] arr = {1,2,2,3,3,4,3};
		permutationNoDup(arr, arr.length, 0);
	}
	
	/**
	 * 交换 数组中 位置 i 和 位置 j 的值
	 * @param arr 数组
	 * @param i  位置i
	 * @param j  位置j
	 */
	public static void swap(int[] arr, int i, int j){
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	/**
	 * 该方法适用于 非重复元素数组
	 * 
	 * @param arr  待排列数组
	 * @param size 数组长度
	 * @param n   从哪个位置开始遍历
	 * 
	 * 递归的调用全排列方法
	 *   1- 当开始位置 等于 最后一个元素位置时，开始返回数据
	 *   2- 如果不满足 步骤1，从 传入的 n 值位置 开始 遍历数据
	 *   3- 交换 当前元素 和 传入的 n 的位置
	 *   4- 对数组中 从 n+1 到 size－1 位置的元素 进行全排列
	 *   5- 当 n+1 到  size-1 个元素全排列完成后，将 步骤3 中 交换后的位置 再次交换回来
	 *   
	 *   eg: 123  第一次循环 => 123,132
	 *            第二次循环 => 213,231
	 *            弟三次循环 => 321，312
	 */
	public static void permutation(int[] arr, int size, int n){
		if(n == size -1){
			Print(arr, size);
			return;
		}
		
		for(int i = n; i < size; i++){
			swap(arr, i, n);
			permutation(arr, size, n+1);
			swap(arr, i, n);
		}
	}
	
	/**
	 * 同方法 permutation ，只是增加了一个过滤重复元素，时间复杂度为 O((N+1)!)
	 * @param arr
	 * @param size
	 * @param n
	 */
	public static void permutationNoDup(int[] arr, int size, int n){
		if(n == size -1){
			Print(arr, size);
			return;
		}
		
		for(int i = n; i < size; i++){
			if(isDup(arr, n, i)){
				continue;
			}
			swap(arr, i, n);
			permutationNoDup(arr, size, n+1);
			swap(arr, i, n);
		}
	}
	
	/**
	 * 看当前元素在数组 当前位置 之前 是否重复出现
	 * @param arr    数组
	 * @param start  开始位置
	 * @param cur    当前元素
	 * @return
	 */
	public static boolean isDup(int[] arr, int start, int cur){
		while(start < cur){
			if(arr[start++] == arr[cur]){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 按照顺序打印当前数组中的 元素
	 * @param arr
	 * @param size
	 */
	public static void Print(int[] arr, int size){
		for(int i = 0; i < size; i++){
			System.out.print(arr[i]);
		}
		System.out.println();
	}

}
